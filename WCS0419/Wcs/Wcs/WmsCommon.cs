using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.IO;
using System.Data;
using System.Threading;
using System.Windows.Forms;
using System.Collections;
using System.Net.NetworkInformation;
using System.Reflection;
using System.Drawing;
using WCS.socket;
using Wcs;
using DataComon;
using NPOI.SS.Formula.Functions;

namespace WCS
{
    /// <summary>
    /// wms接口类
    /// </summary>  
    public class WmsCommon
    {
        FrmMain fr = new FrmMain();

        /// <summary>
        /// 称重前扫描的条码
        /// </summary>
        public string codeWeigh = string.Empty;

        private object l = new object();
        private static WmsCommon m_instance;
        private static object m_lock = new object();

        /// <summary>
        /// 扫描的数据集
        /// </summary>
        public DataTable codeScanData = new DataTable();

        /// <summary>
        /// wms数据
        /// </summary>
        public DataTable codeWmsData = new DataTable();

        /// <summary>
        /// wms对于关系
        /// </summary>
        public List<NumWms> Dictionaryaddress = new List<NumWms>();

        /// <summary>
        ///
        /// </summary>
        public string TextWms = string.Empty;

        public DataSet packageWms = new DataSet();

        #region 通过Singletonle模式返回当前实例

        /// <summary>
        /// 通过Singleton模式返回当前实例
        /// </summary>
        public static WmsCommon Instance()
        {
            if (m_instance == null)
            {
                lock (m_lock)
                {
                    if (m_instance == null)
                    {
                        m_instance = new WmsCommon();
                    }
                }
            }

            return m_instance;
        }

        public WmsCommon()
        {
            Random rand1 = new Random();
            id = rand1.Next(30);
        }

        public object ob = new object();

        #endregion

        #region 获取操作系统已用的端口号

        /// <summary>
        /// 获取操作系统已用的端口号
        /// </summary>
        /// <returns></returns>
        public static IList PortIsUsed()
        {
            //获取本地计算机的网络连接和通信统计数据的信息
            IPGlobalProperties ipGlobalProperties = IPGlobalProperties.GetIPGlobalProperties();

            //返回本地计算机上的所有Tcp监听程序
            IPEndPoint[] ipsTCP = ipGlobalProperties.GetActiveTcpListeners();

            //返回本地计算机上的所有UDP监听程序
            IPEndPoint[] ipsUDP = ipGlobalProperties.GetActiveUdpListeners();

            //返回本地计算机上的Internet协议版本4(IPV4 传输控制协议(TCP)连接的信息。
            TcpConnectionInformation[] tcpConnInfoArray = ipGlobalProperties.GetActiveTcpConnections();

            IList allPorts = new ArrayList();
            foreach (IPEndPoint ep in ipsTCP)
                allPorts.Add(ep.Port);
            foreach (IPEndPoint ep in ipsUDP)
                allPorts.Add(ep.Port);
            foreach (TcpConnectionInformation conn in tcpConnInfoArray)
                allPorts.Add(conn.LocalEndPoint.Port);

            return allPorts;
        }

        #endregion

        #region 检查指定端口是否已用

        /// <summary>
        /// 检查指定端口是否已用
        /// </summary>
        /// <param name="port"></param>
        /// <returns></returns>
        public bool PortIsAvailable(int port)
        {
            bool isAvailable = true;

            IList portUsed = PortIsUsed();

            foreach (int p in portUsed)
            {
                if (p == port)
                {
                    isAvailable = false;
                    break;
                }
            }

            return isAvailable;
        }

        #endregion

        int csSeq = 110;

        public string SeqValue
        {
            get
            {
                try
                {
                    string errText = string.Empty;


                    return csSeq.ToString();
                }
                catch (Exception ex)
                {
                    csSeq++;
                    return csSeq.ToString();
                }
            }
        }

        /// <summary>
        /// 当前服务器的socket
        /// </summary>
        public Socket serverSocket;

        public Thread myThread;
        string appPath = Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName);

        #region 1)启服 开务器端

        /// <summary>
        /// 1) 开启服务器端
        /// </summary>
        public bool WcsServerOpen()
        {
            try
            {
                IPAddress ip = IPAddress.Parse(RfConfig.Create().WcsIP);
                serverSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                serverSocket.Bind(new IPEndPoint(ip, int.Parse(RfConfig.Create().WcsPort)));
                serverSocket.Listen(100);
                Log.WriteLog("本地的服务器端开启 " + RfConfig.Create().WcsPort);
                serverSocket.BeginAccept(new AsyncCallback(ListenClientConnect), null);
                return true;
            }
            catch (Exception ex)
            {
                Log.WriteLog("本地的服务器端 " + ex.Message.ToString());
                return false;
            }
        }

        #endregion


        /**
         * 回调函数
         * IAsyncResult  是接口，里面有几个属性常看异步状态
         */
        public void ReadCallback(IAsyncResult ar)
        {
            String content = String.Empty;

            // Retrieve the state object and the handler socket

            // from the asynchronous state object.


            StateObject state = (StateObject) ar.AsyncState; //AsyncState	获取用户定义的对象，它限定或包含关于异步操作的信息。

            Socket handler = state.workSocket;

            // Read data from the client socket.

            int bytesRead = handler.EndReceive(ar);

            if (bytesRead > 0)
            {
                // There    might be more data, so store the data received so far.

                state.sb.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));

                // Check for end-of-file tag. If it is not there, read

                // more data.

                content = state.sb.ToString();

                if (content.IndexOf("<EOF>") > -1)
                {
                    // All the data has been read from the

                    // client. Display it on the console.
                    Log.WriteLog("Read" + content.Length + " bytes from socket. \n Data :" + content);

                    // Echo the data back to the client.

                    Send(handler, "Server return :" + content);
                }

                else
                {
                    // Not all data received. Get more.

                    handler.BeginReceive(state.buffer, 0, StateObject.BufferSize, 0,
                        new AsyncCallback(ReadCallback), state);
                }
            }
        }

        int hear = 0;

        private void Send(Socket handler, String data)
        {
            // Convert the string data to byte data using ASCII encoding.

            byte[] byteData = Encoding.ASCII.GetBytes(data);

            // Begin sending the data to the remote device.

            handler.BeginSend(byteData, 0, byteData.Length, 0, new AsyncCallback(SendCallback), handler);
        }

        public bool SendWmsData(byte[] data)
        {
            lock (data)
            {
                try
                {
                    for (int i = 0; i < WmsCommon.Instance().clients.Count;)
                    {
                        if (i < WmsCommon.Instance().clients.Count)
                        {
                            SocketStatus wmssocket = WmsCommon.Instance().clients[i];
                            if (wmssocket != null)
                            {
                                if (wmssocket.scocKet.Connected)
                                {
                                    wmssocket.scocKet.Send(data);
                                    // Log.WriteLog(DateTime.Now + "WCS --> WMS：" + data);
                                    i++;
                                }
                                else
                                {
                                    wmssocket.scocKet.Close();
                                    WmsCommon.Instance().clients.Remove(wmssocket);
                                }
                            }
                        }
                    }

                    return true;
                }
                catch (Exception ex)
                {
                    Log.WriteLog("发送消息异常" + ex.Message.ToString());
                    return false;
                }

                // return false;
            }
        }

        private void SendCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the socket from the state object.

                Socket handler = (Socket) ar.AsyncState;

                // Complete sending the data to the remote device.

                int bytesSent = handler.EndSend(ar);

                Log.WriteLog("Sent" + bytesSent + " bytes to client.");
                // Console.WriteLine("Sent {0} bytes to client.", bytesSent);

                handler.Shutdown(SocketShutdown.Both);

                handler.Close();
            }

            catch (Exception e)
            {
                Log.WriteLog(e.Message.ToString());
            }
        }

        #region 2)监听客户端连接

        public List<SocketStatus> clients = new List<SocketStatus>();

        /// <summary>  
        /// 2)监听客户端连接  
        /// </summary>
        public void ListenClientConnect(IAsyncResult aResult)
        {
            try
            {
                lock (clients)
                {
                    SocketStatus classSocket = new SocketStatus();
                    if (serverSocket == null)
                    {
                        return;
                    }

                    Socket lientSocket = serverSocket.EndAccept(aResult);
                    if (serverSocket == null)
                    {
                        return;
                    }

                    /// 数据格式 
                    string ipClient = ((IPEndPoint) lientSocket.RemoteEndPoint).Address.ToString();
                    string protClient = ((IPEndPoint) lientSocket.RemoteEndPoint).Port.ToString();
                    Log.WriteLog("客户端连接成功！" + ipClient + ": " + protClient);

                    //FrmMain.globalFrm.tb_message.AppendText("客户端连接成功！" + ipClient + ": " + protClient + "\r\n"); 
                    ClientManager cm = new ClientManager(ref lientSocket);
                    lientSocket.Poll(100, SelectMode.SelectError);
                    classSocket.heartbeatnumber = 0;
                    classSocket.scocKet = lientSocket;
                    classSocket.statusSocket = "0";
                    if (clients.Count > 0)
                    {
                        if (clients[0].scocKet.Connected)
                            clients[0].scocKet.Close();
                    }

                    clients.Add(classSocket);
                    cm.CommandReceived += OnClientManagerCommandReceived;
                    serverSocket.BeginAccept(new AsyncCallback(ListenClientConnect), null);
                }
            }
            catch (Exception exc)
            {
                if (!SockUtils.HandleSocketError(exc))
                    Log.WriteLog(exc.InnerException + exc.Source + exc.Message + exc.TargetSite);

                return;
            }
        }

        void sockAcys_Completed(object sender, SocketAsyncEventArgs e)
        {
            throw new NotImplementedException();
        }

        #endregion

        #region 3) 接收消息  ---路由电报

        /// <summary>
        /// 请求标记
        /// 默认为0
        /// </summary>
        public int markRequest = 0;

        /// <summary>
        /// 发送次数
        /// </summary>
        public int divertnumber = 0;

        public bool m_StopListen = true;


        public void CloseSocket(ChatSocket sc)
        {
            try
            {
                if (clients.Count == 0) return;
                for (int i = 0; i < clients.Count; i++)
                {
                    SocketStatus s = clients[i];
                    if (s.scocKet.Connected)
                    {
                        if (s.Ip == sc.RemoteEndPoint.Address.ToString() && s.Port == sc.RemoteEndPoint.Port.ToString())
                        {
                            clients.Remove(s);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Log.WriteLog("wms连接不稳定");
            }

            //  FormMain.globalFrm.gdvWmsConn.RefreshData();
            // sc.Close(); 
        }


        private object heartSock = new object();


        #region PLC流水号

        object pclseq = new object();

        /// <summary>
        /// 读取PLC流水号
        /// </summary>
        /// <param name="val">箱号</param>
        /// <returns>流水号</returns>
        public string PlcSeqData(string val)
        {
            lock (pclseq)
            {
                string seqlData = string.Empty;
                string errText = string.Empty;
                try
                {
                }
                catch (Exception ex)
                {
                    Log.WriteLog("读取PLC流水号错误" + ex.Source + ex.Message + ex.TargetSite);
                }

                return "1000";
            }
        }

        #endregion

        /// <summary>
        /// 获取异或
        /// </summary>
        /// <param name="buffer"></param>
        /// <returns></returns>
        public byte xor(byte[] buffer)
        {
            if (buffer.Length < 2)
            {
                Log.WriteLog("长度过短 ");
            }

            byte xor = buffer[0];
            for (int i = 1; i < buffer.Length; i++)
            {
                xor ^= buffer[i];
            }

            return xor;
        }
        //private byte[] getlengthByte(ushort datalength)
        //{ 
        //    if(!BitConverter.IsLittleEndian)
        //    {
        //        return BitConverter.GetBytes(datalength);
        //    }
        //    byte[] bytes = BitConverter.GetBytes(datalength);
        //    return revers

        //}
        int idPlc;

        public string GetPlcId()
        {
            lock (idPlc.ToString())
            {
                string errText = string.Empty;
//                string plcId = PlcFactory.Instance().ReadPlcDbValue("读取任务号%", ref errText);
                string plcId = PlcFactory.Instance().ReadPlcDbValue("FromPlc_seq%", ref errText);

                if (plcId == "01")
                {
                    idPlc = idPlc + 1;
                    plcId = idPlc.ToString();
                }

                if (int.Parse(plcId) > 99999)
                {
                    plcId = "1";
                    idPlc = 1;
                }
                else
                {
                    plcId = int.Parse(plcId).ToString();
                }

                return plcId;
            }
        }

        public void setCode(string data, string seqid)
        {
            lock (data)
            {
                codeE = data;
                sendId = seqid;
            }
        }

        string sendId = "";
        string codeE = "";
        object lckdata = new object();
      public static  ArrayList codeArray = new ArrayList();
      public static  CreateData createdata = new CreateData();
        //LogData datalog = new LogData();
        public Hashtable map = new Hashtable();

        //  public string SeqId = string.Empty;
        private void HandleMessageReceived(ClientManager cm, TimedEventArgs ea) //处理接收信息
        {
            ChatSocket chatSocket = cm.ChatSocket;


            if (chatSocket.Command == ChatCommand.PublicMessage)
            {
                // SendResponsePacketTo(aClient, chatSocket.Command, pck); if (chatSocket.Command == ChatCommand.PrivateMessage)
            }
            else
            {
                string returnStr = "";
                ChatSocket chatSockets = cm.ChatSocket;

                byte[] recby = chatSocket.ReceiveByte;
                string pck = chatSockets.Metatype;
                //数据判断及解析
                if (recby != null)
                {
                    if (recby.Length < 3)
                    {
                        return;
                    }

                    for (int i = 0; i < recby.Length; i++)
                    {
                        returnStr += recby[i].ToString("X2"); //ToString("X2") 为C#中的字符串格式控制符 
                    }
                }

                if (returnStr.Trim().Length < 5)
                {
                    return;
                }

                // 开始逻辑判断
                returnStr = returnStr.Substring(2);
                if (returnStr.Trim().Length == 0)
                {
                    return;
                }
                if (returnStr.Length < 12)
                {
                    return;
                }

                string seqWms = returnStr.Substring(4, 8);
                string flag = returnStr.Substring(12, 2);

                if (flag == "84")
                {
                    Log.WriteLog(DateTime.Now + "接受顺丰落袋反馈消息：" + returnStr);
                    return;
                }

                if (flag == "85")
                {
                    Log.WriteLog(DateTime.Now + "接受顺丰打印反馈消息：" + returnStr);
                    return;
                }

                if (flag == "86")
                {
                    Log.WriteLog(DateTime.Now + "接受顺丰满袋反馈消息：" + returnStr);
                    return;
                }

                if (flag == "83")
                {
                    lock (lckdata)
                    {
                        try
                        {
                            string postStr = "AA" + returnStr;
                            Post post = new Post(postStr);
                            Data83Rec data83Rec = new Data83Rec(post.Data);
                            string codeProt = data83Rec.Code;
                            string sendIdt = "" + data83Rec.Seq;
                            // noread的情况下,code长度为0
                            if (0 == data83Rec.CodeLen)
                            {
                                codeProt = map[sendIdt].ToString();
                            }

                            map.Remove(sendIdt);
                            setCode("", "");
                            object[] values = new object[3];
                            values[0] = GetPlcId();

                            Log.WriteLog("接受顺丰格口返回消息：" + returnStr);
                            string port1 = data83Rec.Port;
                            Log.WriteLog(port1 + "   wms return ");
                            int truePort = 0;
                            //if (port1.Contains("F") || port1.Contains("E") || port1.Contains("D"))
                            //{
                            //    values[1] = 13;
                            //}
                            //else
                            //{
                                truePort = Convert.ToInt32(port1, 16);
                                values[1] = truePort;
                            //}
                            values[2] = sendIdt; 
                            
                            PlcFactory.Instance().WritePlcDataDB("ToPlc_seq%", new[] {values[2]});
                            PlcFactory.Instance().WritePlcDataDB("ToPlc_barcode%",new object[]{codeProt});

                            //格口状态检测，异常则去19
                            string errText = String.Empty;
                            //string SL_State = PlcFactory.Instance().ReadPlcDbValue("满袋" + values[1].ToString() + "%", ref errText);
                            //if (SL_State.Equals("-1"))
                            //{
                            //    values[1] = 19;
                            //}
                            PlcFactory.Instance().WritePlcDataDB("ToPlc_bag%", new object[] {values[1]});
                            Log.WriteLog(values[0].ToString() + "写入PLC成功" + port1 + " plc" + sendIdt +"WMS反馈落袋格口：");
                            if (codeProt.Trim() != "")
                            {
                                //原先是从记录表开始存储  然后现在添加到正式表并更改记录表里条码状态字段
                                //datalog.AddDjmx(codeProt, values[0].ToString(), values[1].ToString());
                                //现在的思路 在开始存储的时候就存在正式表里 然后在这里更改条码的一些字段
                                createdata.UpdateDjmx(codeProt, values[0].ToString(), values[1].ToString());
                            }

                            //孙 添加数量
                            //int sum = 0;
                            //DataTable tb = createdata.SelectSum(values[1].ToString());
                            //if (tb.Rows.Count > 0)
                            //{
                            //    int test = Convert.ToInt32(tb.Rows[0]["sts"]);
                            //    if (DBNull.Value != tb.Rows[0]["count"])
                            //    {
                            //        int i = Convert.ToInt32(tb.Rows[0]["count"]);
                            //        sum = i + 1;
                            //    }
                            //    else
                            //    {
                            //        sum = 1;
                            //    }
                            //    createdata.UpdateProtStsSum(values[1].ToString(), sum);
                            //}
                        }
                        catch (Exception ex)
                        {
                            Log.WriteLog(DateTime.Now + "接受顺丰格口返回消息异常 ：" + returnStr);
                            setCode("", "");
                        }
                    }
                    return;
                }

                if (flag == "88")
                {
                    string port = returnStr.Substring(26, 4); //格口 
                    string error = returnStr.Substring(30, 1); //格口  
                    string ss = returnStr.Substring(30, 1);
                    System.Text.ASCIIEncoding asciiEncoding = new System.Text.ASCIIEncoding();
                    byte[] byteArray = new byte[] {byte.Parse(ss)};
                    string strCharacter = asciiEncoding.GetString(byteArray);
                    //datalog.UpdateErrProtSts(port, strCharacter, "2");
                    createdata.UpdateErrProtSts(port, strCharacter, "2");
                    List<byte> wmsSeq = new List<byte>();
                    wmsSeq.Add(Convert.ToByte(seqWms.Substring(0, 2), 16));
                    wmsSeq.Add(Convert.ToByte(seqWms.Substring(2, 2), 16));
                    wmsSeq.Add(Convert.ToByte(seqWms.Substring(4, 2), 16));
                    wmsSeq.Add(Convert.ToByte(seqWms.Substring(6, 2), 16));
                    string seqId = string.Empty;
                    ErrWmsSendData(0x88, 0x0A, wmsSeq, ref seqId);
                    return;
                }

                if (flag == "89")
                {
                    string seqId = string.Empty;
                    string port = returnStr.Substring(26, 4); //格口  
                    //datalog.UpdateErrProtSts(port, "", "0");
                     createdata.UpdateErrProtSts(port, "", "0");
                    List<byte> wmsSeq = new List<byte>();
                    wmsSeq.Add(Convert.ToByte(seqWms.Substring(0, 2), 16));
                    wmsSeq.Add(Convert.ToByte(seqWms.Substring(2, 2), 16));
                    wmsSeq.Add(Convert.ToByte(seqWms.Substring(4, 2), 16));
                    wmsSeq.Add(Convert.ToByte(seqWms.Substring(6, 2), 16));

                    ErrWmsSendData(0x89, 0x0A, wmsSeq, ref seqId);
                    return;
                }

                #region 心跳回复

                if (flag == "80") //心跳回复 
                {
                    new Thread((ThreadStart) delegate
                    {
                        try
                        {
                            List<byte> wmsSeq = new List<byte>();
                            wmsSeq.Add(0x00);
                            wmsSeq.Add(Convert.ToByte(seqWms.Substring(0, 2), 16));
                            wmsSeq.Add(Convert.ToByte(seqWms.Substring(2, 2), 16));
                            wmsSeq.Add(Convert.ToByte(seqWms.Substring(4, 2), 16));
                            wmsSeq.Add(Convert.ToByte(seqWms.Substring(6, 2), 16));
                            string data = string.Empty;
                            byte[] array = System.Text.Encoding.ASCII.GetBytes("LS01");
                            for (int j = 0; j < array.Length; j++)
                            {
                                wmsSeq.Add(array[j]);
                            }

                            string seqId = string.Empty;
                            ErrWmsSendData(0x80, 0x0E, wmsSeq, ref seqId);
                        }
                        catch (Exception ex)
                        {
                            Log.WriteLog(seqWms);
                        }
                    }).Start();
                    return;
                    ;
                }

                #endregion

                #region 自检消息回复

                if (flag == "81") //自检消息回复 
                {
                    new Thread((ThreadStart) delegate
                    {
                        string errtext = string.Empty;

                        List<byte> wmsSeq = new List<byte>();
                        wmsSeq.Add(0x00);
                        wmsSeq.Add(Convert.ToByte(seqWms.Substring(0, 2), 16));
                        wmsSeq.Add(Convert.ToByte(seqWms.Substring(2, 2), 16));
                        wmsSeq.Add(Convert.ToByte(seqWms.Substring(4, 2), 16));
                        wmsSeq.Add(Convert.ToByte(seqWms.Substring(6, 2), 16));
                        string seqId = string.Empty;
                        ErrWmsSendData(0x81, 0x0A, wmsSeq, ref seqId);

                        #region 检测设备

                        string sts = PlcFactory.Instance().ReadPlcDbValue("读取序号%", ref errtext).ToString();
                        List<byte> wcsData = new List<byte>();
                        wmsSeq.Clear();
                        if (sts != "01") //Plc故障
                        {
                            // List<byte> wmsSeq = new List<byte>();
                            wmsSeq.Add(0x00);

                            ErrWmsSendData(0x82, 0x06, wmsSeq, ref seqId);
                        }
                        else
                        {
                            wmsSeq.Add(0x01);

                            ErrWmsSendData(0x82, 0x06, wmsSeq, ref seqId);
                        }
                    }).Start();

                    #endregion
                }

                #endregion
            }
        }
        
       
        int id = 10;
        private object senda = new object();

        public bool ErrWmsSendData(byte ins, byte len, List<byte> seqWms, ref string idseq)
        {
            lock (senda)
            {
                List<byte> SendBuf = new List<byte>();
                SendBuf.Add(0xCC);
                SendBuf.Add(0X00);
                SendBuf.Add(len);
                if (idseq.Trim().Length == 0)
                {
                    if (id > 3000)
                    {
                        id = 10;
                    }
                    idseq = id.ToString();
                    id++;
                }
                byte[] by = WmsCommon.Instance().intToBytes(int.Parse(idseq));
                for (int n = by.Length - 1; n >= 0; n--)
                {
                    SendBuf.Add(by[n]);
                }

                SendBuf.Add(ins);
                for (int i = 0; i < seqWms.Count; i++)
                {
                    SendBuf.Add(seqWms[i]);
                }
                byte xor = SendBuf[0];
                for (int i = 1; i < SendBuf.Count; i++)
                {
                    xor ^= SendBuf[i];
                }
                SendBuf.Add(xor);
                SendBuf.Add(0x55);
                if (ins == 0x84)
                {
                    Log.WriteLog("发送落袋信息给WMS：" + string.Join(",", SendBuf.ToArray()));
                }
                return SendWmsData(SendBuf.ToArray());
            }
        }

        private void OnClientManagerCommandReceived(object sender, TimedEventArgs ea)
        {
            ClientManager cm = sender as ClientManager;
            if (cm == null)
            {
                return;
            }
            HandleMessageReceived(cm, ea);
        }

        public static byte[] HexStrTobyte(string hexString)
        {
            hexString = hexString.Replace(" ", "");
            if ((hexString.Length % 2) != 0)
            {
                hexString += " ";
            }

            byte[] returnBytes = new byte[hexString.Length / 2];
            for (int i = 0; i < returnBytes.Length; i++)
            {
                returnBytes[i] = Convert.ToByte(hexString.Substring(i * 2, 2).Trim(), 16);
            }
            return returnBytes;
        }

        public string bytetoHexStr(byte bytes)
        {
            string returnStr = "";
            if (bytes != null)
            {
                returnStr = bytes.ToString("X2");
            }

            return returnStr;
        }

        public string bytestoHexStr(byte[] bytes, int st, int end)
        {
            string returnStr = "";
            if (bytes != null)
            {
                for (int i = st; i < end; i++)
                {
                    returnStr += bytes[i].ToString("X2");
                }
            }

            return returnStr;
        }

        public int MAX_PACKET_LENGTH = 1024;
        public byte PACKET_PREFIX = 0xCC;
        public byte PACKET_ENDFIX = 0x55;

        public byte[] packData(byte[] seq, byte[] len, byte flag, byte[] userData)
        {
            if (userData == null)
            {
                return null;
            }

            byte[] data = new byte[MAX_PACKET_LENGTH];
            int offset = 0;

            // 打包数据
            //添加起始字符
            data[offset++] = PACKET_PREFIX;
            //添加数字信息域长度+1
            System.Array.Copy(len, 0, data, offset, len.Length);
            offset = offset + 2;
            //添加指令码
            data[offset++] = flag;


            if (data.Length < offset + userData.Length)
            {
                //数组大小不够，则需要扩容
                byte[] tmp = new byte[offset + userData.Length];
                System.Array.Copy(data, 0, tmp, 0, offset);
                data = tmp;
            }

            //添加数据信息域
            System.Array.Copy(userData, 0, data, offset, userData.Length);
            offset += userData.Length;


            //添加XOR异或
            data[offset++] = xor(data);

            //添加起始字符
            data[offset++] = PACKET_ENDFIX;

            byte[] result = new byte[offset];
            System.Array.Copy(data, 0, result, 0, offset);

            return result;
        }

        public void addInt(byte[] src, int offset, int value)
        {
            byte[] seqBytes = intToBytes(value);
            System.Array.Copy(seqBytes, 0, src, offset, 4);
        }

        public byte[] intToBytes(int value)
        {
            byte[] src = new byte[4];
            src[0] = (byte) (value & 0xFF);
            src[1] = (byte) ((value >> 8) & 0xFF);
            src[2] = (byte) ((value >> 16) & 0xFF);
            src[3] = (byte) ((value >> 24) & 0xFF);
            return src;
        }

        public void addFiledBytes(byte[] src, int offset, byte[] filedBytes)
        {
            addInt(src, offset, filedBytes.Length);


            System.Array.Copy(filedBytes, 0, src, offset, filedBytes.Length);
        }


        public DataTable checkData = new DataTable();
        private object hreatObject = new object();


        /// <summary>  
        /// 3) 接收消息  ---路由电报
        /// </summary>
        /// <param name="clientSocket"></param>
        public void ReceiveMessage(byte[] clientSocket, ChatSocket soket)
        {
            try
            {
            }
            catch (Exception ex)
            {
                Log.WriteLog("异常3" + ex.Message + ex.TargetSite);
            }
        }

        #endregion


        /// <summary>
        /// Send ServerDisconnected command to each connected client, and disconnects 
        /// the listening socket.
        /// Events: Stopped
        /// </summary>
        public void Stop()
        {
            serverSocket.Close();
            serverSocket.Dispose();

            GC.Collect(); //强制对所有代进行即时垃圾回收
            GC.WaitForPendingFinalizers();
            GC.Collect();
        }

        private object messAdd = new object();

        ///// <summary>
        ///// 起始 结束符
        ///// </summary>
        //public List<SocketStatus> ListclientSocket = new List<SocketStatus>();
        public string beginstx = "0xCC"; //供应商起始符号
        public string endetx = "0x55"; //结束符号
        public string spacekey = " "; //空格
    }
}