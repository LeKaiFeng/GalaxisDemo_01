/**
 * 添加引用  using .....
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using WCS;
using DataComon;
using Common;
using System.Threading;
using System.Runtime.Serialization.Formatters.Binary;
using System.IO;
using System.Net;
using System.Net.Sockets;
using DevExpress.Data.Async.Helpers;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Text.RegularExpressions;
using NPOI.SS.Formula.Functions;
using Log = WCS.Log;

namespace Wcs
{
    public partial class FrmMain : Form
    {
        public FrmMain()
        {
            InitializeComponent();
            //界面显示 扫描记录  ip  prot
            DataColumn cl1 = new DataColumn("ip");
            DataColumn clprot = new DataColumn("prot");

            DataColumn clsts = new DataColumn("sts");
            DataColumn clcarnum = new DataColumn("carnum");
            iptb.Columns.Add(cl1);
            iptb.Columns.Add(clprot);

            ThreadExceptionDialog.CheckForIllegalCrossThreadCalls = false;    //跨线程限制取消，默认不能跨线程
            DevExpress.Data.CurrencyDataController.DisableThreadingProblemsDetection = true;  //DevExpress跨线程操作数据

            //datalog.deleteOldData();     //删除超过7天的数据
            createdata.deleteOldData();
        }

        DataTable iptb = new DataTable();
        public List<string> codeCar = new List<string>();

        public void BindData()
        {
            #region 原先查询
            //DataSet ds = datalog.GetTaskData();
            //if (ds != null)
            //{
            //    if (ds.Tables.Count > 0)
            //    {
            //        //查询格口状态  0 解锁； 1 锁定
            //        if (ds.Tables[1] != null)
            //        {
            //            gdcProt.DataSource = ds.Tables[1];
            //        }
            //        //查询前200条数据按时间降序
            //        if (ds.Tables[0] != null)
            //        {
            //            gdc_data.DataSource = ds.Tables[0];
            //        }
            //    }
            //}
            #endregion
            DataTable ta = createdata.GetTaskData();
            if (ta != null)
            {
                if (ta.Rows.Count > 0)
                {
                    //查询格口状态  0 解锁； 1 锁定
                    gdcProt.DataSource = ta;
                }
            }
            DataTable th = createdata.TwohundredData();
            if (th != null)
            {
                if (th.Rows.Count > 0)
                {
                    //查询前200条数据按时间降序
                    gdc_data.DataSource = th;
                }
            }
        }
        private void FrmMain_Load(object sender, EventArgs e)        //事件 的 委托   
        {
            if (WmsCommon.Instance().WcsServerOpen())
            {
                tb_message.Text = "端口号为 " + RfConfig.Create().WcsPort + "的服务已经开启!\r\n";
                timer1.Start();
                sts_wms.StateIndex = 3;
                sts_plc.StateIndex = 3;
            }
            else
            {
                sts_wms.StateIndex = 1;
                sts_plc.StateIndex = 1;
                timer1.Start();
            }
            BindData();
            selectold();
            Thread tr = new Thread(new ThreadStart(ListenData));
            tr.Start();
            Thread t1 = new Thread(new ThreadStart(BagCount));
            t1.Start();
        }
        public void BagCount()
        {
            while (true)
            {
                Thread.Sleep(20);
                try
                {
                    string errText = string.Empty;
                    for (int i = 0; i <= 18; i++)
                    {
                        string plcid = PlcFactory.Instance().ReadPlcDbValue("EndBag" + i + "%1", ref errText);
                        //Log.WriteLog(DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss:ffff")+" 读取到PLCID：" + plcid);
                        //string mouthid = PlcFactory.Instance().ReadPlcDbValue("满袋" + i + "%", ref errText);
                        //Console.WriteLine(mouthid);
                        HashForeach.HashAdd(plcid, i);
                        //HashForeach.HashStateAdd(i, mouthid);
                    }
                    Dictionary<string, int> openWith1 = HashForeach.openWith;
                    Dictionary<int, string> openWith2 = HashForeach.openstate;

                    DataTable tb = createdata.MatchingData();
                    if (tb != null)
                    {
                        if (tb.Rows.Count > 0)
                        {
                            foreach (DataRow row in tb.Rows)
                            {
                                string plcid = row["plcid"].ToString();
                                string seqId = string.Empty;
                                string code = row["ExpressNo"].ToString();

                                if (code == "ERROR\r")
                                {
                                    // 更改实际落袋格口 endport
                                    //createdata.UpdateEndprot(19, code);
                                    //更改格口数量
                                    createdata.UpdateCount(19);
                                    createdata.UpdateMatching(plcid);
                                    createdata.UpdateEndprot(19, plcid);
                                    Log.WriteLog(DateTime.Now + " 条码为空。 条码：" + code);
                                    break;
                                }
                                //格口锁定情况下
                                bool st = createdata.SelectState(plcid, code);
                                if (st)
                                {
                                    createdata.UpdateCount(19);
                                    createdata.UpdateMatching(plcid);
                                    createdata.UpdateEndprot(19, plcid);
                                    Log.WriteLog(DateTime.Now + " 格口锁定。 条码：" + code);
                                    break;
                                }
                                else
                                {
                                    try
                                    {
                                        int i = openWith1[plcid];
                                        openWith1.Remove(plcid);
                                        createdata.UpdateCount(i);
                                        createdata.UpdateMatching(plcid);
                                        createdata.UpdateEndprot(i, plcid);
                                        break;
                                    }
                                    catch (Exception ex)
                                    {
                                        Log.WriteLog2(DateTime.Now + "更改正常条码落袋数据异常：" + ex.Message.ToString());
                                    }
                                }
                                DateTime time1 = Convert.ToDateTime(row["begindate"]);
                                DateTime d1 = Convert.ToDateTime(DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));//当前日期
                                int unusualtime = Convert.ToInt32(d1.Subtract(time1).TotalSeconds.ToString());
                                if (unusualtime > 20)
                                {
                                    createdata.UpdateCount(19);
                                    createdata.UpdateMatching(plcid);
                                    createdata.UpdateEndprot(19, plcid);
                                    Log.WriteLog(DateTime.Now + " 条码异常。 条码：" + code);
                                }
                            }
                            BindData();
                        }
                    }
                }
                catch (Exception ex)
                {
                    Log.WriteLog(DateTime.Now + "读取或对比PLC DB异常：" + ex.Message.ToString());
                }
            }
        }

        private string codeOld = String.Empty;
        private string seqOld = String.Empty;

        private void ListenData()
        {
            while (true)
            {
                Thread.Sleep(20);
                string errText = string.Empty;
                string seq = PlcFactory.Instance().ReadPlcDbValue("FromPlc_seq%", ref errText);
                string code = PlcFactory.Instance().ReadPlcDbValue("FromPlc_barcode%", ref errText);

                //string code = code0.Replace("&", "");
                if (seq.Trim().Equals("") || int.Parse(seq) <= 10000 || code.Trim().Equals("") || seqOld.Equals(seq))
                {
                    Thread.Sleep(200);
                    continue;
                }

                seqOld = seq;
                codeOld = code;
                if (oldExpressNo != code || oldplcid != seq)
                {
                    DataChange(code, int.Parse(seq));
                }
            }
        }

        string oldExpressNo = "";
        string oldplcid = "";
        public void selectold()
        {
            DataTable dt = createdata.SelectOldData();
            if (dt.Rows.Count > 0)
            {
                oldExpressNo = dt.Rows[0]["ExpressNo"].ToString();
                oldplcid = dt.Rows[0]["plcid"].ToString();
            }
        }
        
        CreateData createdata = new CreateData();
        object DataReadLock = new object();
        private void DataChange(string Data, int seq)
        {
            try
            {
                #region  快递路向分拣
                lock (DataReadLock)    //多线程避免同时运行造成数据混乱。lock的一般是对象，不是数值和字符串。
                {
                    string code = Data;
                    string twoDimencode = string.Empty; //二维码
                    string linercode = string.Empty;    //一维码
                    string seqOlcId = string.Empty;
                    List<byte> wmsSeq = new List<byte>();
                    Log.WriteLog("扫描条码" + code);
                    var time0 = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss:ffff");

                    tb_message.AppendText(DateTime.Now.ToString() + "扫描条码" + code + "\r\n");
                    byte lendata = 0;
                    if (code == "noread")
                    {
                        tb_message.AppendText(DateTime.Now.ToString() + "扫码失败 包裹区异常口" + "\r\n");
                        code = "noread";
                        lendata = 0X0B;
                        linercode = "noread";
                    }
                    else
                    {
                        if (code.Contains("&"))
                        {
                            string[] codelist = code.Split('&');
                            twoDimencode = codelist[1];
                            linercode = codelist[0];

                            if (twoDimencode.Contains("http"))
                            {
                                code =  linercode;
                            }
                            else
                            {
                                // http://ucmp.sf-express.com/service/weixin/activity/wx_b2sf_order?p1=439038437579
                                // 处理异常条码，如： 43100002%MMMMMMMMMMMMMMMMMMMMMMMM={'k1':'574W','k2':'574TF','k3':'017','k4':'T4','k5':'049842774482','k6':''}
                                if (linercode.Length < 12)
                                {
                                    // 一维码长度小于12时，从二维码中取K5值
                                    try
                                    {
                                        string jsonStr = code.Substring(twoDimencode.IndexOf("M=") + 2);
                                        JObject jo = (JObject)JsonConvert.DeserializeObject(jsonStr);
                                        linercode = jo["k5"].ToString();
                                        code = twoDimencode + "&" + linercode + "&*&*&*&*&*&*&";
                                    }
                                    catch (Exception e)
                                    {
                                        // 如果转二维码转json失败，说明二维码也不正确，此时code判定为noread
                                        linercode = "noread";
                                        code = "noread";
                                    }
                                }
                                else
                                {
                                    // 正常情况下直接拼接一维码和二维码
                                    code = twoDimencode + "&" + linercode + "&*&*&*&*&*&*&";
                                }
                            }
                        }
                        else if (code.Length < 15)
                        {
                            linercode = code;
                            // code = "&" + code;
                            code = code;
                            //PlcFactory.Instance().WritePlcDataDB("ToPlc_seq%", new object[] { seq });
                            //PlcFactory.Instance().WritePlcDataDB("ToPlc_barcode%", new object[] { Data });
                            //if (i == 18)
                            //{
                            //    i = 0;
                            //    PlcFactory.Instance().WritePlcDataDB("ToPlc_bag%", new object[] { 19 });
                            //}
                            //else
                            //{
                            //    i++;
                            //    PlcFactory.Instance().WritePlcDataDB("ToPlc_bag%", new object[] { i });
                            //}
                            //datalog.UpdateDjmx(Data, seq.ToString(), i.ToString());
                            //Log.WriteLog2(DateTime.Now.ToString() + "----------￥￥￥￥￥￥￥￥￥-------" + (i.ToString()));

                            //int sum = 0;
                            //DataTable tb = datalog.SelectSum(i.ToString());
                            //if (tb.Rows.Count > 0)
                            //{
                            //    int test = Convert.ToInt32(tb.Rows[0]["sts"]);
                            //    if (DBNull.Value != tb.Rows[0]["count"])
                            //    {
                            //        int a = Convert.ToInt32(tb.Rows[0]["count"]);
                            //        sum = a + 1;
                            //    }
                            //    else
                            //    {
                            //        sum = 0;
                            //    }
                            //    datalog.UpdateProtStsSum(i.ToString(), sum);
                            //}
                            //BindData();
                        }
                        else
                        {
                            //只有二维码
                            //{'k1':'574W''k2':'574JF''k3':'008''k4':'T4''k5':'049842738896''k6':''}
                            try
                            {
                                string linecode = code.Substring(code.IndexOf("M=") + 2);
                                JObject jo = (JObject)JsonConvert.DeserializeObject(linecode);
                                code = code + "&" + jo["k5"].ToString() + "&";
                                linercode = jo["k5"].ToString();
                            }
                            catch (Exception ex)
                            {
                                code = "noread";
                                lendata = 0X0B;
                                linercode = "noread";
                            }
                        }
                        lendata = byte.Parse((code.Length + 5).ToString());
                    }
                    if (code.Trim().Length == 0)
                    {
                        Log.WriteLog("scan error" + linercode);
                        return;
                    }
                    byte[] array = System.Text.Encoding.ASCII.GetBytes(code);
                    foreach (byte letter in array)
                    {
                        wmsSeq.Add(letter);
                    }
                    seqOlcId = seq.ToString();
                    WmsCommon.Instance().ErrWmsSendData(0X83, lendata, wmsSeq, ref seqOlcId);
                    WmsCommon.Instance().setCode(linercode, seqOlcId);
                    WmsCommon.Instance().map[seqOlcId] = linercode;

                    //datalog.InsertTask(linercode);

                    Log.WriteLog("发送给WMS：  " + linercode);
                    createdata.InsertTask(linercode, DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"));
                }

                #endregion
            }
            catch (Exception ex)
            {
                Log.WriteLog("DataChange事件异常：" + ex.Message.ToString() + ":" + Data + ":");
                return;
            }

        }

        private void FrmMain_FormClosing(object sender, FormClosingEventArgs e)
        {
            //终止当前进程并为基础操作系统提供指定的退出代码。
            System.Environment.Exit(System.Environment.ExitCode);
            //强制退出
            Application.Exit();
        }

        object pt = new object();

        private void timer1_Tick(object sender, EventArgs e)
        {
            lock (this)
            {
                try
                {
                    ConnectState();
                    PocketSend();
                    MouthState();
                    //暂时注释
                    //string plcBacode = PlcFactory.Instance().ReadPlcDbValue("PLCBarcode%", ref errText);
                    //string plcId = PlcFactory.Instance().ReadPlcDbValue("PLCNo%", ref errText);
                    //if (plcId == "01")
                    //{
                    //    sts_plc.StateIndex = 1;
                    //}
                    //else
                    //{
                    //    sts_plc.StateIndex = 3;
                    //}

                    //if (plcId != "0" && plcId != "01")
                    //{
                    //    string EndProt = PlcFactory.Instance().ReadPlcDbValue("WCSBag%", ref errText);
                    //    //int i = datalog.UpDataPlcData(plcId, plcBacode, EndProt);
                    //    int i = createdata.UpDataPlcData(plcId, plcBacode, EndProt);
                    //    if (i > 0 || wmsld > 2)
                    //    {
                    //        Log.WriteLog(DateTime.Now.ToString() + "落袋信息写入DB" + plcId + " -> " + EndProt + " ; i = " + i);
                    //        object[] values = new object[2];
                    //        values[0] = "0";
                    //        values[1] = "0";
                    //        PlcFactory.Instance().WritePlcDataDB("清除落袋%", values);
                    //        wmsld = 0;
                    //    }
                    //    else
                    //    {
                    //        wmsld++;
                    //    }
                    //}
                }
                catch (Exception ex)
                {
                    Log.WriteLog("定时器1异常" + ex.Message);
                }
            }
        }
        /// <summary>
        /// 监控WCS跟WMS连接状态
        /// </summary>
        public void ConnectState()
        {
            iptb.Rows.Clear();        //清空表数据
            try
            {
                for (int i = 0; i < WmsCommon.Instance().clients.Count; i++)
                {
                    if (i < WmsCommon.Instance().clients.Count)
                    {
                        SocketStatus wmssocket = WmsCommon.Instance().clients[i];
                        if (wmssocket != null)
                        {
                            if (wmssocket.scocKet.Connected)
                            {
                                i++;
                                DataRow r = iptb.NewRow();
                                r["ip"] = wmssocket.Ip;
                                r["prot"] = wmssocket.Port;
                                iptb.Rows.Add(r);
                            }
                            else
                            {
                                wmssocket.scocKet.Close();
                                WmsCommon.Instance().clients.Remove(wmssocket);
                            }
                        }
                    }
                }
                if (iptb != null)
                    gridControl1.DataSource = iptb;
            }
            catch (Exception ex)
            {
                tb_message.AppendText("连接异常"+ex.Message + "\r\n");
                Log.WriteLog2("连接异常" + ex.Message);
                return;
            }
        }
        

        /// <summary>
        /// 实际落袋发送   WCS->WMS
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void PocketSend()
        {
            try
            {
                DataTable tb = createdata.GetPlcSednCode();
                if (tb != null)
                {
                    if (tb.Rows.Count > 0)
                    {
                        foreach (DataRow row in tb.Rows)
                        {
                            string seqId = string.Empty;
                            List<byte> wmsSeq = new List<byte>();
                            string code = row["ExpressNo"].ToString();
                            if (code.Trim().Length == 0)
                            {
                                continue;
                            }
                            // 如果code为noread,以12个0作为单号发送
                            if ("noread\r" == code)
                            {
                                code = "000000000000";
                            }
                            byte[] arrayL = System.Text.Encoding.ASCII.GetBytes(code);
                            if (row["endprot"].ToString().Trim().Length == 0)
                            {
                                return;
                            }
                            short endprot = short.Parse(row["endprot"].ToString());
                            byte[] array;
                            //// 如果数据库查到的落袋格口为14,实际是因为格口锁定而落入13格口
                            //if (endprot == 14)
                            //{
                            //    short readport = 13;
                            //    array = BitConverter.GetBytes(readport);
                            //}
                            //else
                            //{
                            array = BitConverter.GetBytes(endprot); //数据域 格口 2字节 
                            //}
                            wmsSeq.Add(array[1]);
                            wmsSeq.Add(array[0]);
                            wmsSeq.Add(byte.Parse(code.Length.ToString()));

                            foreach (char letter in arrayL)
                            {
                                byte value = Convert.ToByte(letter);
                                wmsSeq.Add(value);  //数据域 运单号 动态长度 
                            }

                            short prot = short.Parse(row["prot"].ToString());

                            if (prot == 19)
                            {
                                wmsSeq.Add(0XF0);
                            }
                            else
                            {
                                if (endprot == 19)
                                {
                                    wmsSeq.Add(0XF0);
                                }
                                // 如果因为满袋而未分拣,plc会返回落袋格口99
                                else if (endprot == 99)
                                {
                                    wmsSeq.Add(0x02);
                                }
                                else
                                {
                                    wmsSeq.Add(0X00); //数据域 异常码 1字节
                                }
                            }
                            wmsSeq.Add(0X02);  //数据域 TAG 1字节 
                            byte lenData = byte.Parse((5 + wmsSeq.Count).ToString());
                            bool sendSts = WmsCommon.Instance().ErrWmsSendData(0X84, lenData, wmsSeq, ref seqId);
                            if (sendSts)
                            {
                                string plcId = row["plcid"].ToString();
                                createdata.UpDataWmsPlcData(plcId);
                                createdata.UpDataWmsseqIdData(seqId, plcId);
                                Log.WriteLog2(DateTime.Now + " 发送落袋信息成功。 条码：" + code + "  实际格口：" + plcId);
                                tb_message.AppendText(DateTime.Now.ToString() + code + " 落袋发送消息成功\r\n");
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Log.WriteLog2("发送WMS落袋信息 ex" + ex.Message);
            }

        }

        /// <summary>
        ///格口状态改变发送  WCS->WMS
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void MouthState()
        {
            try
            {
                //查询格口号状态为 1或0的格口
                DataTable tbProt = createdata.GetProtSend();
                if (tbProt != null)
                {
                    if (tbProt.Rows.Count > 0)
                    {
                        string seqId = string.Empty;
                        List<byte> wmsSeq = new List<byte>();
                        foreach (DataRow row in tbProt.Rows)
                        {
                            wmsSeq.Clear();     //清空byte集合
                            byte stsPort = 0X01;//锁定
                            if (row["sts"].ToString() == "0")
                            {
                                stsPort = 0X02;///解锁
                            }
                            short prot = short.Parse(row["prot"].ToString());
                            byte[] array = BitConverter.GetBytes(prot);    //以字节数组的形式返回返回指定的 16 位有符号整数值
                            wmsSeq.Add(array[1]);
                            wmsSeq.Add(array[0]);
                            wmsSeq.Add(stsPort);

                            bool sendSts = WmsCommon.Instance().ErrWmsSendData(0X86, 0X08, wmsSeq, ref seqId); //bool 布尔类型
                            if (sendSts)
                            {
                                createdata.UpDataWcsWms(prot.ToString());
                                if (stsPort == 0x01)
                                {
                                    Log.WriteLog2(prot + "发送格口状态成功: 锁定");
                                }
                                else
                                {
                                    Log.WriteLog2(prot + "发送格口状态成功: 解锁");
                                }
                                tb_message.AppendText("格口 "+ prot + " 发送状态成功\r\n");
                            }
                            //发送给WMS 要求WMS打印
                            if (stsPort == 0X01)
                            {
                                wmsSeq.Clear();
                                array = BitConverter.GetBytes(1);
                                wmsSeq.Add(array[1]);
                                wmsSeq.Add(array[0]);
                                WmsCommon.Instance().ErrWmsSendData(0X85, 0X07, wmsSeq, ref seqId);
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Log.WriteLog("发送WMS格口状态 ex" + ex.Message);
            }
        }

        /// <summary>
        /// 格口状态查询
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void SelectState() {
            string errText = string.Empty;
            try
            {
                for (short prot = 1; prot < 19; prot++)
                {
                    string val = PlcFactory.Instance().ReadPlcDbValue("满袋" + prot.ToString() + "%", ref errText);
                    //1 锁定  0 解锁
                    if (val == "-1")
                    {
                        createdata.UpdateErrProtSts(prot.ToString(), "", "1");
                        createdata.clearCount(prot.ToString());
                    }
                    else if (val == "0")
                    {
                        createdata.UpdateErrProtSts(prot.ToString(), "", "0");
                    }
                }
            }
            catch (Exception ex)
            {
                Log.WriteLog2("袋子满了 ex" + ex.Message);
            }
        }
        //监测格18个格口状态-----------------------------------------------
        Dictionary<int, string > map = new Dictionary<int, string>();
        public Dictionary<int, string > Endbag_State(string db)
        {
            string errText = string.Empty;
            string[] sArray=Regex.Split(db,"&",RegexOptions.IgnoreCase);
            for (int k = 0;k<= sArray.Length;k++)
            {
                string state=PlcFactory.Instance().ReadPlcDbValue(sArray[k], ref errText);
                if (state!=""||state!=null)
                {
                    byte b=Convert.ToByte(state);
                    byte[] bytes = toBits(b);
                    //倒叙
                    byte[] byteNew = new byte[bytes.Length];
                    for(int i=bytes.Length-1;i>=0;i--)
                    {
                        byteNew[bytes.Length-i+1]=bytes[i];
                    }
                    //将格口转态以键值对的形式添加到map
                    for (int m=0;m<byteNew.Length;m++)
                    {
                        map.Add(m, byteNew[m].ToString());
                    }
                }
                
            }

            return map;
        }
        public static byte[] toBits(byte num) {
            return toBits(num, 8);
        }

        public static byte[] toBits(long num, int size) {
            byte[] bits = new byte[size];
            for (int i = 0; i < bits.Length; i++) {
                bits[bits.Length - i - 1] = (byte) (((num & (0x01 << i)) == 0) ? 0 : 1);
            }
            return bits;
        }
        //---------------------------------------------------------------------
        //详细查询
        private void simpleButton1_Click(object sender, EventArgs e)
        {
            var formQuery = new FormQuery();
            formQuery.Show();
        }
    }
}