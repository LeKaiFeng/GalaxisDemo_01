using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using OpcRcw.Da;
using System.Data;
  

using System.Threading.Tasks;
using System.Threading;

namespace WCS
{

    /// <summary>
    /// plc实现工厂类
    /// </summary>
    public class PlcFactory
    {
        /// <summary>
        /// 读取plc时，存储已经读取的plc点的类
        /// </summary>
        public Dictionary<string, PLClock> plcCollection = new Dictionary<string, PLClock>();

        private static PlcFactory m_instance;
        private static object m_lock = new object();
        #region 通过Singletonle模式返回当前实例
        /// <summary>
        /// 通过Singleton模式返回当前实例
        /// </summary>
        public static PlcFactory Instance()
        {
            if (m_instance == null)
            {
                lock (m_lock)
                {
                    if (m_instance == null)
                    {
                        m_instance = new PlcFactory();
                    }
                }
            }
            return m_instance;
        }

        #endregion

        public Dictionary<string, List<string>> typeClass = new Dictionary<string, List<string>>();
        /// <summary>
        /// 同步读取plc 返回数据
        /// </summary>
        /// <param name="plcNmae"></param>
        /// <returns></returns>
        /// 
        object plcLock = new object();
        public PLClock plcClass(string plcName, ref string errText)
        {
            lock (plcLock)
            {
                try
                {
                    errText = string.Empty;
                    PLClock plcRead;
                    if (plcCollection.Keys.Count(r => r == plcName) > 0)
                    {
                        plcRead = plcCollection[plcName];
                        return plcRead;
                    }
                    plcRead = new PLClock();
                    plcRead.ConnectRemoteServer();
                    plcRead.PLCGroupAdd();

                    foreach (KeyValuePair<string, List<string>> item in typeClass)
                    {
                        string xmlName = item.Key;
                        if (xmlName == plcName)
                        {
                            OpcRcw.Da.OPCITEMDEF[] Item = new OPCITEMDEF[item.Value.Count];
                            for (int i = 0; i < item.Value.Count; i++)
                            {
                                Item[i].szAccessPath = "";
                                Item[i].szItemID = item.Value[i].ToString();
                                Item[i].bActive = 1;//是否激活  
                                Item[i].hClient = i + 1;//表示ID   
                                Item[i].dwBlobSize = 0;
                                Item[i].pBlob = IntPtr.Zero;
                                Item[i].vtRequestedDataType = 8;

                                plcRead.PLCItemAdd(Item);

                            }
                            break;
                        }
                    }
                    plcCollection.Add(plcName, plcRead);
                    return plcRead;
                }
                catch (Exception ex)
                {
                    errText = ex.Message.ToString();
                    return null;
                }
            }
        }

        public string ReadPlcDbValue(string plcName, ref string errText)
        {
            lock (plcName)
            {
                try
                {
                    errText = string.Empty;
                    PLClock plcRead = plcClass(plcName, ref errText);
                    if (errText.Trim().Length > 0)
                    {
                        return "01aaa";
                    }

                    List<object> read = plcRead.ReadPlc();

                    if (read == null)
                    {
                        return "01bbb";
                    }
                    if (read.Count == 0)
                    {
                        return "01ccc";
                    }
                    return read[0].ToString();

                }
                catch (Exception ex)
                {
                    errText = ex.Message.ToString();
                    return "01ddd";// throw ex;
                }
            }
        }
        /// <summary>
        /// 写plc的值
        /// </summary>
        public string WritePlcDataDB(string plcName, object[] value)
        {
            string errText = string.Empty;

            PLClock plcRead = plcClass(plcName, ref errText);
            if (errText.Trim().Length > 0)
            {
                return errText;
            }
            plcRead.WirtePlc(value);
            return errText;
        }
    }
}
