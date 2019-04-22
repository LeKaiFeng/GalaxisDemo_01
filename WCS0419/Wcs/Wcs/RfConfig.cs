using System;
using System.Data;
using System.IO;
using System.Reflection;
using System.Collections.Generic;


	/// <summary>
	///Rf信息配置类
	/// </summary>
	public class RfConfig
	{
        
		private const string CONFIG_XML_FILE = "config.xml";
		private const string SYSTEM_SET_NODE = "SystemSet";
		private static RfConfig _RfConfig;		
		private static string _ServerAddress; 
		/// <summary>
		/// 服务器地址
		/// </summary>
		public string ServerAddress
		{
			get { return _ServerAddress; }
		}
  
		protected RfConfig()
		{
	    	GetConfigInfo();
            GetWmsInfo();
		}  
		public static  RfConfig Create()
		{
			if (_RfConfig == null)
			{
                _RfConfig = new RfConfig(); 
			}

			return _RfConfig;
		}
        private static string _ScanIp1;
        private static string _ScanProt1;
        /// <summary>
        ///服务器地址
        /// </summary>
        public string ScanIp1
        {
            get { return _ScanIp1; }
        }
        /// <summary>
        /// 服务器地址
        /// </summary>
        public string ScanProt1
        {
            get { return _ScanProt1; }
        }

        private static string _ScanIp2;
        private static string _ScanProt2;
        /// <summary>
        /// 服务器地址
        /// </summary>
        public string ScanIp2
        {
            get { return _ScanIp2; }
        }
        /// <summary>
        /// 服务器地址
        /// </summary>
        public string ScanProt2
        {
            get { return _ScanProt2; }
        }

        private static string _ScanIp3;
        private static string _ScanProt3;
        /// <summary>
        /// ��������ַ
        /// </summary>
        public string ScanIp3
        {
            get { return _ScanIp3; }
        }
        /// <summary>
        /// ��������ַ
        /// </summary>
        public string ScanProt3
        {
            get { return _ScanProt3; }
        }
        /// <summary>
        /// wms ip��ַ
        /// </summary>
        private string _wmsSendIp = string.Empty;

        /// <summary>
        /// wms ip��ַ
        /// </summary>
        public string WmsSendIP
        {
            get { return _wmsSendIp; }
        }

        private string _wmsprot = string.Empty;
        /// <summary>
        /// wms prot�˿�����
        /// </summary>
        public string WmsPort
        {
            get { return _wmsprot; }
        }

        /// <summary>
        /// wcs ip��ַ
        /// </summary>
        private string _wcsIp = string.Empty;

        /// <summary>
        /// wcs ip��ַ
        /// </summary>
        public string WcsIP
        {
            get { return _wcsIp; }
        }
        /// <summary>
        /// wcs �˿�
        /// </summary>
        private string _wcsPort = string.Empty;

        /// <summary>
        /// wcs�˿�
        /// </summary>
        public string WcsPort
        {
            get { return _wcsPort; }
        }
        private string _requestt = string.Empty;
        /// <summary>
        ///3请求的等待时间
        /// </summary>
        public string Requestt
        {
            get { return _requestt; }
        }
        private string _requestNumber = string.Empty;
        /// <summary>
        ///清除数据
        /// </summary>
        public string RequestNumber
        {
            get { return _requestNumber; }
        }
        
        private string _requestBool = string.Empty;
        /// <summary>
        /// 清除数据
        /// </summary>
        public string RequestBool
        {
            get { return _requestBool; }
        }



        private string _requestf = string.Empty;
        /// <summary>
        /// 5等待的时间
        /// </summary>
        public string Requestf
        {
            get { return _requestf; }
        }


        private string _clearlog = string.Empty;
        /// <summary>
        /// 清除日志־
        /// </summary>
        public string Clearlog
        {
            get { return _clearlog; }
        }


        private string _clearData = string.Empty;
        /// <summary>
        /// 清除数据
        /// </summary>
        public string ClearData
        {
            get { return _clearData; }
        }

        /// <summary>
        /// comm1端口
        /// </summary>
        private string _commProt1 = string.Empty;
        /// <summary>
        /// comm1端口
        /// </summary>
        public string CommProt1
        {
            get { return _commProt1; }
        }
        /// <summary>
        /// comm2端口
        /// </summary>
        private string _commProt2 = string.Empty;
        /// <summary>
        /// comm2端口
        /// </summary>
        public string CommProt2
        {
            get { return _commProt2; }
        }
        /// <summary>
        /// comm3端口
        /// </summary>
        private string _commProt3 = string.Empty;
        /// <summary>
        /// comm3端口
        /// </summary>
        public string CommProt3
        {
            get { return _commProt3; }
        }
        /// <summary>
        /// comm4端口
        /// </summary>
        private string _commProt4 = string.Empty;
        /// <summary>
        /// comm4端口
        /// </summary>
        public string CommProt4
        {
            get { return _commProt4; }
        }

        /// <summary>
        /// comm4端口
        /// </summary>
        private string _commProt5= string.Empty;
        /// <summary>
        /// comm4端口
        /// </summary>
        public string CommProt5
        {
            get { return _commProt5; }
        }


        private string _baudRate1 = string.Empty;

        /// <summary>
        /// 波特率 
        /// </summary>
        public string BaudRate1
        {
            get { return _baudRate1; }
        }
        private string _baudRate2 = string.Empty;

        /// <summary>
        /// 波特率 
        /// </summary>
        public string BaudRate2
        {
            get { return _baudRate2; }
        }

        private string _baudRate3 = string.Empty;

        /// <summary>
        /// 波特率  
        /// </summary>
        public string BaudRate3
        {
            get { return _baudRate3; }
        }

        private string _baudRate4 = string.Empty;

        /// <summary>
        /// 波特率 
        /// </summary>
        public string BaudRate4
        {
            get { return _baudRate4; }
        }
        private string _baudRate5 = string.Empty;

        /// <summary>
        /// 波特率  
        /// </summary>
        public string BaudRate5
        {
            get { return _baudRate5; }
        }

        #region 扫描消息格式 
        private string _heartbeat = string.Empty;
        /// <summary>
        /// 心跳
        /// </summary>
        public string Heartbeat
        {
            get { return _heartbeat; }
        }

        #endregion
        public Dictionary<string, string> wmsDevice = new Dictionary<string, string>();
		/// <summary>
		/// 读配置信息
		/// </summary>
        public void GetConfigInfo()
		{
			DataSet ds = new DataSet();
			string appPath = Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName);
			ds.ReadXml(appPath + "\\" + CONFIG_XML_FILE);
            _wmsSendIp = ds.Tables[SYSTEM_SET_NODE].Rows[0]["wmsip"].ToString();
            _wmsprot = ds.Tables[SYSTEM_SET_NODE].Rows[0]["wmsprot"].ToString();
            _wcsIp = ds.Tables[SYSTEM_SET_NODE].Rows[0]["wcsip"].ToString();
            _wcsPort = ds.Tables[SYSTEM_SET_NODE].Rows[0]["wcsprot"].ToString();

            //_ScanIp1 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["scanip"].ToString();
            //_ScanProt1 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["prot"].ToString();
            //_ScanIp1 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["scanip"].ToString();
            // _ScanProt1 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["prot"].ToString();
            // _ScanIp2 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["scanip2"].ToString();
            // _ScanProt2 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["prot2"].ToString();
            // _ScanIp3 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["scanip3"].ToString();
            // _ScanProt3 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["prot3"].ToString();
        
            //_commProt1 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["commprot1"].ToString();
            //_commProt2 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["commprot2"].ToString();
            //_commProt3 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["commprot3"].ToString();
            //_commProt4 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["commprot4"].ToString();
            //_commProt5 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["commprot5"].ToString(); 
            //_baudRate1 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["baudrate1"].ToString(); 
            //_baudRate2 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["baudrate2"].ToString();
            //_baudRate3 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["baudrate3"].ToString();
            //_baudRate4 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["baudrate4"].ToString();
            //_baudRate5 = ds.Tables[SYSTEM_SET_NODE].Rows[0]["baudrate5"].ToString();

            //_clearlog = ds.Tables[SYSTEM_SET_NODE].Rows[0]["clearlog"].ToString();
            //_clearData = ds.Tables[SYSTEM_SET_NODE].Rows[0]["clearData"].ToString();
		}
        public DataSet plcds = new DataSet();
        /// <summary>
        /// 读配置信息
        /// </summary>
        private void GetWmsInfo()
        {
            string appPath = Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName);
            plcds.ReadXml(appPath + "\\" + "PLCData.xml");

        }

        public DataSet SysonPlcData = new DataSet();
        private void GetSysonPlcData()
        {
            string appPath = Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName);
            SysonPlcData.ReadXml(appPath + "\\" + "SysonPLC.xml");
        }
       
	}

