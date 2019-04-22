using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using WCS;
using System.Text;
using System.Data;
using System.Runtime.Serialization.Formatters.Binary;
using System.IO;
using System.Threading;
using Common;
using DataComon;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;

namespace Wcs
{
    static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        static void Main()
       {
            #region 初始化PLC

            try
            {
                System.Threading.Thread.Sleep(300);
                Dictionary<string, List<string>> typeClass = new Dictionary<string, List<string>>();

                DataTable table = RfConfig.Create().plcds.Tables[0];
                foreach (DataRow row in table.Rows)
                {
                    string[] plcstr = row["plcvalaue"].ToString().Split('%');
                    List<string> listPlc = new List<string>();
                    for (int i = 0; i < plcstr.Length; i++)
                    {
                        if (plcstr[i].ToString().Trim().Length > 0)
                            listPlc.Add(plcstr[i].ToString());
                    }

                    typeClass.Add(row["vlaue"].ToString(), listPlc);
                }

                PlcFactory.Instance().typeClass = typeClass;



            }
            catch (Exception ex)
            {
                // SystemParam.ErrText = ex.Message.ToString();
                DevExpress.XtraSplashScreen.SplashScreenManager.Default.SendCommand(
                    SplashScreen1.SplashScreenCommand.labelControl2, "初始化PLC失败");
                //  SystemParam.plcStatus = false;
            }

            #endregion
            CreateData.createDataBase();
            Application.Run(new FrmMain());
           
        }
    }
}