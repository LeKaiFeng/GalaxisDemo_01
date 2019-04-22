using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace Common
{
     public  class LogWrite
    {
         #region 日志记录
        private static object writelog = new object();
        /// <summary>
        /// 写日志
        /// </summary>
        /// <param name="strLog"></param>
        public static void WriteLog(string strLog)
        {
            lock (writelog)
            {
                try
                {
                    strLog = "时间:" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + Environment.NewLine + strLog + Environment.NewLine;
                    DirectoryInfo di = new DirectoryInfo(AppDomain.CurrentDomain.BaseDirectory + "\\Log");
                    if (di.Exists == false)
                    {
                        di.Create();
                    }
                    string logFileName;
                    logFileName = AppDomain.CurrentDomain.BaseDirectory + "\\Log\\" + DateTime.Now.ToString("yyyyMMdd") + ".log";
                    //超过10M覆盖原文件
                    if (File.Exists(logFileName))
                    {
                        FileInfo fi = new FileInfo(logFileName);
                        if (fi.Length > 5024000)
                        {
                            fi.Delete();
                        }
                    }
                    StreamWriter sw = null;
                    FileStream fs = new FileStream(logFileName, FileMode.Append, FileAccess.Write, FileShare.ReadWrite);
                    sw = new StreamWriter(fs);
                    sw.WriteLine(strLog);
                    sw.Close();
                }
                catch
                {
                }
            }
        }


        public static void WriteDataLog(string strLog)
        {
            lock (writelog)
            {
                try
                {
                    strLog = "时间:" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + Environment.NewLine + strLog + Environment.NewLine;
                    DirectoryInfo di = new DirectoryInfo(AppDomain.CurrentDomain.BaseDirectory + "\\Log");
                    if (di.Exists == false)
                    {
                        di.Create();
                    }
                    string logFileName;
                    logFileName = AppDomain.CurrentDomain.BaseDirectory + "\\订单导入\\" + DateTime.Now.ToString("yyyyMMdd") + ".log";
                    //超过10M覆盖原文件
                    if (File.Exists(logFileName))
                    {
                        FileInfo fi = new FileInfo(logFileName);
                        if (fi.Length > 5024000)
                        {
                            fi.Delete();
                        }
                    }
                    StreamWriter sw = null;
                    FileStream fs = new FileStream(logFileName, FileMode.Append, FileAccess.Write, FileShare.ReadWrite);
                    sw = new StreamWriter(fs);
                    sw.WriteLine(strLog);
                    sw.Close();
                }
                catch
                {
                }
            }
        }
        #endregion
    }
}
