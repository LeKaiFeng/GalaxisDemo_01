using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace WCS
{
    public class Log
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
                    strLog = "时间:" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") + Environment.NewLine + strLog + Environment.NewLine;
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

        public static void WriteLog2(string strLog)
        {
            lock (writelog)
            {
                try
                {
                    strLog = "时间:" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") + Environment.NewLine + strLog + Environment.NewLine;
                    DirectoryInfo di = new DirectoryInfo(AppDomain.CurrentDomain.BaseDirectory + "\\Log");
                    if (di.Exists == false)
                    {
                        di.Create();
                    }
                    string logFileName;
                    logFileName = AppDomain.CurrentDomain.BaseDirectory + "\\Log\\" + DateTime.Now.ToString("yyyyMMdd") + "code.log";
                    //超过10M覆盖原文件
                    if (File.Exists(logFileName))
                    {
                        FileInfo fi = new FileInfo(logFileName);

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
