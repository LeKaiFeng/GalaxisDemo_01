using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Data.SqlClient;
using System.Data;
using System.Windows.Forms;
using System.Configuration;

namespace WCS
{

    public class SocketStatus
    {
        public string Ip
        {
            get { return ((System.Net.IPEndPoint)_scocKet.RemoteEndPoint).Address.ToString(); }
        }
        public string Port
        {
            get { return ((System.Net.IPEndPoint)_scocKet.RemoteEndPoint).Port.ToString(); }
        }
        private System.Net.Sockets.Socket _scocKet;
        /// <summary>
        /// 所有链接的
        /// </summary>
        public System.Net.Sockets.Socket scocKet
        {
            get { return _scocKet; }
            set { _scocKet = value; }
        }

        private string _statusSocket = "0";
        /// <summary>
        /// socket的状态 
        /// 0：标识 已经成功接受客户端的心跳测试，可以使用
        /// 1：标识 已经发送心跳测试，但是没有收到
        /// 3：标识 已经删除的心跳
        /// </summary>
        public string statusSocket
        {
            get { return _statusSocket; }
            set { _statusSocket = value; }
        }
        private int _divertnumber = 0;
        public int heartbeatnumber
        {
            get { return _divertnumber; }
            set { _divertnumber = value; }
        }
    }
    public partial class wmsRequest
    {
        private string _barcode;
        private string _requestNumber;
        private string _address;
        public string AddRess
        {
            set { _address = value; }
            get { return _address; }
        }

        public string RequestNumber
        {
            set { _requestNumber = value; }
            get { return _requestNumber; }
        }
        public string Barcode
        {
            set { _barcode = value; }
            get { return _barcode; }
        }
        private string _requestTime;
        public string RequestTime
        {
            set { _requestTime = value; }
            get { return _requestTime; }
        }
    }
    public class ErrorPlc
    {
        private Button _btCtl;
        /// <summary>
        /// 错误的按钮
        /// </summary>
        public Button btCtl
        {
            set { _btCtl = value; }
            get { return _btCtl; }
        }
        private string _errText;
        /// <summary>
        /// 错误内容
        /// </summary>
        public string ErrText
        {
            set { _errText = value; }
            get { return _errText; }
        }
        private string _btId;
        /// <summary>
        /// 标识
        /// </summary>
        public string BtID
        {
            set { _btId = value; }
            get { return _btId; }
        }
    }
    public class BaseBox
    {
        private string _boxType = string.Empty;
        /// <summary>
        /// 箱子的类型
        /// </summary>
        public string BoxType
        {
            set { _boxType = value; }
            get { return _boxType; }
        }

        private string _boxdevice = string.Empty;
        /// <summary>
        /// 开箱机
        /// </summary>
        public string BoxDevice
        {
            set { _boxdevice = value; }
            get { return _boxdevice; }
        }


        private string _channel = string.Empty;
        /// <summary>
        /// 通道号
        /// </summary>
        public string Channel
        {
            set { _channel = value; }
            get { return _channel; }
        }

        private string _codeLen = string.Empty;
        /// <summary>
        /// 类型的条码的大小
        /// </summary>
        public string CodeLen
        {
            set { _codeLen = value; }
            get { return _codeLen; }
        }

    }
    public class NumWms
    {

        private string _num = string.Empty;
        public string Num
        {
            set { _num = value; }
            get { return _num; }
        }
        private string _qy;
        public string Qy
        {
            set { _qy = value; }
            get { return _qy; }
        }
        private string _wmsnum;
        public string Wmsnum
        {
            set { _wmsnum = value; }
            get { return _wmsnum; }
        }
        private string _stsdps;
        public string StsDps
        {
            set { _stsdps = value; }
            get { return _stsdps; }
        }

    }
    /// <summary>
    /// 类t_wmsdata。
    /// </summary>
    [Serializable]
    public partial class t_wmsdata
    {
        private static t_wmsdata m_instance;
        private static object m_lock = new object();
        /// <summary>
        /// 通过Singleton模式返回当前实例
        /// </summary>
        public static t_wmsdata Instance()
        {
            if (m_instance == null)
            {
                lock (m_lock)
                {
                    if (m_instance == null)
                    {
                        m_instance = new t_wmsdata();
                    }
                }
            }

            return m_instance;
        }

        
    }
}



         
  
