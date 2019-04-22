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
//using WCS.socket;

namespace SocketHelper
{
    public class sockethe
    {

        private static sockethe m_instance;
        private static object m_lock = new object();
        /// <summary>

        #region 通过Singletonle模式返回当前实例
        /// <summary>
        /// 通过Singleton模式返回当前实例
        /// </summary>
        public static sockethe Instance()
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

        public sockethe()
        { 
        }
        public object ob = new object();
 
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
    }
}
