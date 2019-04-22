using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using OpcRcw.Da;
using OpcRcw.Comn;
using System.Runtime.InteropServices;
namespace WCS
{
    public class PlcRead
    { 

        /// <summary>
        /// 进行写的数据
        /// </summary>
        public object writeDataValue;
        /// <summary>
        /// 进行读的数据
        /// </summary>
        public object readDataValue;
        /// <summary>
        /// 生成的server对象，主要用于异步时调用
        /// </summary>
        public IOPCServer ObjectServerPlc
        {
            get { return ServerObj; }
        }

        /// <summary>
        /// 进行写的数据
        /// </summary>
        public object WriteDataValue
        {
            get { return writeDataValue; }
            set { writeDataValue = value; }
        }

        /// <summary>
        /// 进行读的数据
        /// </summary>
        public object ReadDataValue
        {
            get { return readDataValue; }
            set { readDataValue = value; }
        }


        private bool boolValueChang = true;

        /// <summary>
        /// 该属性用于是否检测plc数据改成，异步订阅
        /// </summary>
        public bool ValueChang
        {
            set { boolValueChang = value; }
        }

        #region 参数值

        /// <summary>
        /// 设置db模块的值
        /// </summary>
        public string itemDvalue = "0";
        /// <summary>
        /// 异步读写对象   
        /// </summary>
        public IOPCAsyncIO2 IOPCAsyncIO2Obj = null;

        /// <summary>
        /// 组管理对象
        /// </summary>
        public IOPCGroupStateMgt IOPCGroupStateMgtObj = null;

        /// <summary>
        /// 表示item的id
        /// </summary>
        public int hClient = 1;
        public const int LOCALE_ID = 0x407;
        /// <summary>
        /// OPCServer 
        /// </summary>
        public IOPCServer ServerObj;//OPCServer 
        public int pSvrGroupHandle = 0;
        /// <summary> 
        /// 返回增加的group 
        /// </summary>
        public Object MyobjGroup1 = null;


        /// <summary>
        /// plc是否读写
        /// </summary>
        public bool Write = true;
        /// <summary>
        /// 此处应该是返回连接的cookie值
        /// </summary>
        public Int32 dwCookie = 0;

        public IConnectionPointContainer pIConnectionPointContainer = null;
        public IConnectionPoint pIConnectionPoint = null;
        /// <summary>
        /// item的数组句柄
        /// </summary> 
        public int[] ItemServerHandle;
        /// <summary>
        /// item的值。
        /// </summary>
        public OpcRcw.Da.OPCITEMDEF[] ItemArray;
        #endregion

        /// <summary>
        /// 连接服务器的名字
        /// </summary>
        public string remoteServerName = "OPC.SimaticNet";

        /// <summary>
        /// 连接服务器的名字
        /// </summary>
        public string ServerName
        {
            set { remoteServerName = value; }
        }

        public string remoteServerIP = "localhost";
        /// <summary>
        /// 连接服务器的ip地址
        /// </summary>
        public string ServerIP
        {
            set { remoteServerIP = value; }
        }
          /// <summary>
        /// 错误内容
        /// </summary>
        protected string errText = string.Empty;
        /// <summary>
        /// 错误内容
        /// </summary>
        public string ErrText
        {
            get { return errText; }
            set { errText = value; }
        }
        /// <summary>
        /// 该属性用于传触发 plc的form窗体（）
        /// </summary>
        public object formthis;
        #region  plc的链接
        /// <summary>
        /// plc的链接
        /// </summary>
        /// <param name="remoteServerName">链接的名字（）</param>
        /// <param name="remoteServerIP">链接的ip地址</param>
        /// <returns></returns>
        public string ConnectRemoteServer()
        {
          
            try
            {
                ////建议一个连接 
                Type svrComponenttyp = Type.GetTypeFromProgID(remoteServerName, remoteServerIP);

                ServerObj = (IOPCServer)Activator.CreateInstance(svrComponenttyp);
            }

            catch (Exception ex)
            {

                errText = "连接远程服务器出现错误:" + ex.Message.ToString();
            }
            return errText;
        }
        #endregion
        public int hClientGroup =1;
        #region 增加一个group
        /// <summary>
        /// 增加一个group
        /// </summary>
        /// <param name="Form_Main"></param>
        /// <returns></returns>
        public string PLCGroupAdd(object Form_Main)
        {

            string errText = string.Empty;
            Int32 dwRequestedUpdateRate = 250; 
            Int32 pRevUpdateRate; 
            float deadband = 0; 
            int TimeBias = 0;
            GCHandle hTimeBias, hDeadband;
            hTimeBias = GCHandle.Alloc(TimeBias, GCHandleType.Pinned);
            hDeadband = GCHandle.Alloc(deadband, GCHandleType.Pinned);
            Guid iidRequiredInterface = typeof(IOPCItemMgt).GUID;
            try
            {
                ServerObj.AddGroup("MyOPCGroup1",//组对象  
                    0,
                    dwRequestedUpdateRate,
                    hClientGroup,
                    hTimeBias.AddrOfPinnedObject(),
                    hDeadband.AddrOfPinnedObject(),
                    LOCALE_ID,
                    out pSvrGroupHandle,
                    out pRevUpdateRate,
                    ref iidRequiredInterface,
                    out MyobjGroup1);

                IOPCAsyncIO2Obj = (IOPCAsyncIO2)MyobjGroup1;
                //Query interface for Async calls on group object  
     
                IOPCGroupStateMgtObj = (IOPCGroupStateMgt)MyobjGroup1;

                pIConnectionPointContainer = (IConnectionPointContainer)MyobjGroup1;
                //定义特定组的异步调用连接  

                Guid iid = typeof(IOPCDataCallback).GUID;
                // Establish Callback for all async operations  
                pIConnectionPointContainer.FindConnectionPoint(ref iid, out  
                                   pIConnectionPoint);

                // Creates a connection between the OPC servers's connection point and this client's sink (the callback object)  
                pIConnectionPoint.Advise(Form_Main, out dwCookie);
                

            }
            catch (Exception ex)
            {
                errText = ex.Message.ToString();
            }
            return errText;
        }

        #endregion

        #region 添加一个读写的Items数组对象
        /// <summary>

        /// 添加一个读写的Items数组对象 

        /// </summary>

        /// <param name="items">Items读写对象数组</param>

        /// <returns>Items是否执行成功</returns> 
        public string PlcItemAdd(OPCITEMDEF[] items)
        {

            string errText = string.Empty;
            IntPtr pResults = IntPtr.Zero;
            IntPtr pErrors = IntPtr.Zero;
            try
            {

                ((IOPCItemMgt)MyobjGroup1).AddItems(items.Length, items, out  pResults, out pErrors);

                int[] errors = new int[items.Length];
                Marshal.Copy(pErrors, errors, 0, items.Length);
                ItemServerHandle = new int[items.Length];
                IntPtr pos = pResults;
                OPCITEMRESULT result;
                if (errors[0] == 0)
                {
                    result = (OPCITEMRESULT)Marshal.PtrToStructure(pos, typeof(OPCITEMRESULT));
                    ItemServerHandle[0] = result.hServer;
                }

                for (int i = 1; i < errors.Length; i++)
                {

                    if (errors[i] == 0)
                    {

                        pos = new IntPtr(pos.ToInt32() + Marshal.SizeOf(typeof(OPCITEMRESULT)));
                        result = (OPCITEMRESULT)Marshal.PtrToStructure(pos, typeof(OPCITEMRESULT));

                        ItemServerHandle[i] = result.hServer;

                    }

                }

                return errText;

            }

            catch (System.Exception ex) // catch for add item  
            {

                return ex.Message.ToString().Trim();

            }

            finally
            {

                // Free the memory 

                if (pResults != IntPtr.Zero)
                {

                    Marshal.FreeCoTaskMem(pResults);

                    pResults = IntPtr.Zero;

                }

                if (pErrors != IntPtr.Zero)
                {

                    Marshal.FreeCoTaskMem(pErrors);

                    pErrors = IntPtr.Zero;

                }

            }

        } 

        #endregion

      
      
        #region 写plc的值
        /// <summary>
        /// 写plc的值
        /// </summary>
        /// <param name="values">写的值</param>
        /// <returns></returns>
        public string WirtePlc(object[] values)
        {
            string errText = string.Empty;

            int nCancelid;
            IntPtr pErrors = IntPtr.Zero;
            if (IOPCAsyncIO2Obj != null)
            {

                try
                {

                    IOPCAsyncIO2Obj.Write(ItemServerHandle.Length, ItemServerHandle,
                        values, ItemServerHandle.Length, out nCancelid, out pErrors);

                    int[] errors = new int[ItemServerHandle.Length];

                    Marshal.Copy(pErrors, errors, 0, ItemServerHandle.Length);



                    if (errors[0] != 0)//Error in reading item
                    {

                        Marshal.FreeCoTaskMem(pErrors);

                        pErrors = IntPtr.Zero;

                        return errText;

                    }

                    return errText;

                }

                catch (Exception ex)
                {
                    errText = ex.Message.ToString().Trim();
                    return errText;

                }

            }
            return errText;

        }
        #endregion


        /// <summary>
        /// 读取plc的值
        /// </summary>
        /// <returns></returns>
        public string ReadPlc()
        {
            string errText = string.Empty;

            int nCancelid;

            IntPtr pErrors = IntPtr.Zero;

            if (IOPCAsyncIO2Obj != null)
            {

                try
                {
                    IOPCAsyncIO2Obj.Read(ItemServerHandle.Length, ItemServerHandle,
                        ItemServerHandle.Length, out nCancelid, out pErrors);

                    int[] errors = new int[ItemServerHandle.Length];

                    Marshal.Copy(pErrors, errors, 0, ItemServerHandle.Length);
                    return errText;

                }

                catch (Exception ex)
                {

                    errText = ex.Message.ToString().Trim();

                }

            }
            return errText;

        }
 

        #region  检测plc数据改变时，触发事件
        /// <summary>
        /// 检测plc数据改变时，触发事件
        /// </summary>
        /// <returns></returns>
        public string DataChange()
        {
             

            string errText = string.Empty; 
            IntPtr pRequestedUpdateRate = IntPtr.Zero;
            int nRevUpdateRate = 0;
            IntPtr hClientGroup = IntPtr.Zero;
            IntPtr pTimeBias = IntPtr.Zero;
            IntPtr pDeadband = IntPtr.Zero;
            IntPtr pLCID = IntPtr.Zero;
            int nActive = 0;

            // activates or deactivates group according to checkbox status  
            GCHandle hActive = GCHandle.Alloc(nActive, GCHandleType.Pinned);
            hActive.Target = 1;

            try
            {
                IOPCGroupStateMgtObj.SetState(pRequestedUpdateRate, out nRevUpdateRate,
                           hActive.AddrOfPinnedObject(), pTimeBias, pDeadband, pLCID,
                           hClientGroup);
            }
            catch (System.Exception error)
            {
                errText = error.Message.ToString();
            }

            finally
            {
                hActive.Free();
            }
            return errText;
        }

        #endregion

        #region  断开PLC的连接

        /// <summary>
        /// 断开PLC的连接
        /// </summary>
        /// <returns></returns>
        public string DisConnection()
        {
            string errText = string.Empty;
            try
            {
                if (dwCookie != 0)
                {
                    pIConnectionPoint.Unadvise(dwCookie);
                    dwCookie = 0;
                }
                // Free unmanaged code  
                Marshal.ReleaseComObject(pIConnectionPoint);
                pIConnectionPoint = null;

                Marshal.ReleaseComObject(pIConnectionPointContainer);
                pIConnectionPointContainer = null;

                if (IOPCAsyncIO2Obj != null)
                {
                    Marshal.ReleaseComObject(IOPCAsyncIO2Obj);
                    IOPCAsyncIO2Obj = null;
                }
                
                ServerObj.RemoveGroup(pSvrGroupHandle, 0);
                if (IOPCGroupStateMgtObj != null)
                {
                    Marshal.ReleaseComObject(IOPCGroupStateMgtObj);
                    IOPCGroupStateMgtObj = null;
                }
                if (MyobjGroup1 != null)
                {
                    Marshal.ReleaseComObject(MyobjGroup1);
                    MyobjGroup1 = null;
                }
                if (ServerObj != null)
                {
                    Marshal.ReleaseComObject(ServerObj);
                    ServerObj = null;
                }
            }
            catch (System.Exception error)
            {
                errText = error.Message.ToString().Trim();

            }
            return errText;

        }
        #endregion


    }
}
