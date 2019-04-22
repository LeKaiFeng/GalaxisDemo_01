using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using OpcRcw.Da;
using OpcRcw.Comn;
namespace WCS 
{
    
    /// <summary>
    /// 同步读写plc
    /// </summary>
    public class PLClock : IPLCAbstract
    {
        #region 参数值
        public object[] textValue = new object[2];
        /// <summary>
        /// OPCServer 
        /// </summary>
        public IOPCServer ServerObj;//OPCServer 
        /// <summary>
        /// 同步步读写对象   
        /// </summary>
        public IOPCSyncIO IOPCSyncIO2Obj = null;

        /// <summary>
        /// 组管理对象
        /// </summary>
        public IOPCGroupStateMgt IOPCGroupStateMgtObj = null;


        public const int LOCALE_ID = 0x407;

        public int pSvrGroupHandle = 0;
        /// <summary> 
        /// 返回增加的group 
        /// </summary>
        public Object MyobjGroup1 = null;

        public string itemDvalue = "0";


        /// <summary>
        /// item的数组句柄
        /// </summary> 
        public  int[] ItemServerHandle;
        /// <summary>
        /// item的值。
        /// </summary>
        public OPCITEMDEF[] ItemArray;
        #endregion

        #region plc的连接
        /// <summary>
        /// plc的连接
        /// </summary>
        /// <param name="remoteServerName">连接的名字（）</param>
        /// <param name="remoteServerIP">连接的ip地址</param>
        /// <returns></returns>
        public   string ConnectRemoteServer()
        {
            string errText = string.Empty;
            try
            {
                //建议一个连接      
              Type svrComponenttyp = Type.GetTypeFromCLSID(Guid.Parse("7BC0CC8E-482C-47CA-ABDC-0FE7F9C6E729"), "localhost");
 
    //            Type svrComponenttyp = Type.GetTypeFromCLSID(Guid.Parse("B6EACB30-42D5-11D0-9517-0020AFAA4B3C"), "localhost");
//                Type svrComponenttyp = Type.GetTypeFromProgID("OPC.SimaticNet", "localhost");
//                Type svrComponenttyp = Type.GetTypeFromProgID("KEPware.KEPServerEx.V6", "localhost");
                
                ServerObj = (IOPCServer)Activator.CreateInstance(svrComponenttyp);
                
            }

            catch (Exception ex)
            {

                errText = "连接远程服务器出现错误:" + ex.Message.ToString();
            }
       
            return errText;
        }

        #endregion
    
        #region 增加group
        /// <summary>
        /// 增加group
        /// </summary>
        /// <param name="Form_Main"></param>
        /// <returns></returns>
        public string PLCGroupAdd()
        {
            string errText = string.Empty;
            Int32 dwRequestedUpdateRate = 1000;
            //Int32 hClientGroup;
            Int32 pRevUpdateRate;

            float deadband = 0;

            int TimeBias = 0;
            GCHandle hTimeBias, hDeadband;
            hTimeBias = GCHandle.Alloc(TimeBias, GCHandleType.Pinned);
            hDeadband = GCHandle.Alloc(deadband, GCHandleType.Pinned);
            Guid iidRequiredInterface = typeof(IOPCItemMgt).GUID;
            try
            {
                    ServerObj.AddGroup("ShearerInfoGroup",//组对象  
                1,
                    dwRequestedUpdateRate,
                    1,
                    hTimeBias.AddrOfPinnedObject(),
                    hDeadband.AddrOfPinnedObject(),
                    0,
                    out pSvrGroupHandle,
                    out pRevUpdateRate,
                    ref iidRequiredInterface,
                    out MyobjGroup1);

                IOPCSyncIO2Obj = (IOPCSyncIO)MyobjGroup1;
                //Query interface for Async calls on group object  

                IOPCGroupStateMgtObj = (IOPCGroupStateMgt)MyobjGroup1;

            }
            
            catch (Exception ex)
            {
                errText = ex.Message.ToString();
            }
            return errText;
        }
        #endregion  

        #region  增加item
        /// <summary>
        /// 增加item
        /// </summary>
        /// <param name="items"></param>
        /// <returns></returns>
        public   string PLCItemAdd(OPCITEMDEF[] items)
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
 

        /// <summary>
        /// 读plc
        /// </summary>
        /// <returns></returns>
        public List<object> ReadPlc()
        {
           //Console.WriteLine("--------------开始读取plc数据！");
            object[] redValue = new object[ItemServerHandle.Length];  
            IntPtr pItemValues = IntPtr.Zero; 
            IntPtr pErrors = IntPtr.Zero; 
            IOPCSyncIO2Obj.Read(OPCDATASOURCE.OPC_DS_DEVICE, 5, ItemServerHandle, out pItemValues, out pErrors);
            int[] errors = new int[ItemServerHandle.Length];

            Marshal.Copy(pErrors, errors, 0, ItemServerHandle.Length);
           
            OPCITEMSTATE[] pItemState = new OPCITEMSTATE[ItemServerHandle.Length];
            List<object> value = new List<object>();
            
            for (int i = 0; i < ItemServerHandle.Length; i++)
            {
                if (errors[i] == 0)
                {
                   
                    pItemState[0] = (OPCITEMSTATE)Marshal.PtrToStructure(pItemValues, typeof(OPCITEMSTATE));
                    string ptrToStringUni = Marshal.PtrToStringUni(pItemValues);
                    

                    pItemValues = new IntPtr(pItemValues.ToInt64() + Marshal.SizeOf(typeof(OPCITEMSTATE)));
                    OPCITEMSTATE opcitemstate = pItemState[0];
                    value.Add((object)pItemState[0].vDataValue);
                   
                }
            }

           
            return value;
           
        }

        #region 写plc
        /// <summary>
        /// 写plc
        /// </summary>
        /// <param name="values"></param>
        /// <returns></returns>
        public string WirtePlc(object[] values)
        {
            string errText = string.Empty;
            try
            {
             
                //int nCancelid;
                IntPtr pErrors = IntPtr.Zero;
                if (IOPCSyncIO2Obj != null)
                {
                    try
                    {

                        IOPCSyncIO2Obj.Write(ItemServerHandle.Length, ItemServerHandle, values, out pErrors);
                        int[] errors = new int[ItemServerHandle.Length];
                        Marshal.Copy(pErrors, errors, 0, ItemServerHandle.Length);
                        
                    }

                    catch (Exception ex)
                    {
                       // PlcFactory.Instance().plcCollection.Clear();
                        errText = ex.Message.ToString().Trim();
                        Log.WriteLog("写 plc异常" + ex.Message.ToString());

                    }

                }
            }
            catch (Exception ex)
            {
                 
            }

            return errText;
        }
        #endregion

        #region  断开PLC的连接

        /// <summary>
        /// 断开PLC的连接
        /// </summary>
        /// <returns></returns>
        public   string DisConnection()
        {
            string errText = string.Empty;
            try
            {

                if (IOPCSyncIO2Obj != null)
                {
                    Marshal.ReleaseComObject(IOPCSyncIO2Obj);
                    IOPCSyncIO2Obj = null;
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

        #region IPLCAbstract 成员
        public object wirteDataValue;
        /// <summary>
        /// 写入的值
        /// </summary>
        public object WriteDataValue
        {
            get
            {
                return wirteDataValue;
            }
            set
            {
               wirteDataValue =value;
            }
        }
        public object readDataValue;
        /// <summary>
        /// 读出的值
        /// </summary>
        public object ReadDataValue
        {
            get { return  readDataValue;}
        }
    

    

      
        public   string DataChange()
        {
            throw new NotImplementedException();
        }

        public virtual string ReadWritePlcData()
        {
            throw new NotImplementedException();
        }

        #endregion


        public string PLCGroupAdd(object Form_Main)
        {
            throw new NotImplementedException();
        }

        string IPLCAbstract.ReadPlc()
        {
            throw new NotImplementedException();
        }
    }
}
