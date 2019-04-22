using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using DataCommon;
using Common;
using System.Data;
using System.Data.SqlClient;

namespace DataComon
{
    public class LogData
    {
        public void deleteOldData()
        {
            //删除t_code表中 大于当前时间7天的所有数据
            String sqlStr1 = "DELETE FROM t_code WHERE DATEDIFF(DAY,sj,getdate()) > 7";
            String sqlStr2 = "DELETE FROM t_djmx WHERE DATEDIFF(DAY,djsj,getdate()) > 7";

            try
            {
                SqlServerHelper.ExecuteNonQuery(sqlStr1);
                SqlServerHelper.ExecuteNonQuery(sqlStr2);
            }
            catch (Exception e)
            {
                return;
            }
        }

        // 根据 快递单号模糊查询
        public DataTable queryByExpressNo(string expressNo, string start, string end)
        {
            string sql = "select * from t_djmx where ExpressNo like '%" + expressNo + "' " +
                " and (djsj between '" + start + "' and '" + end +
                "') order by djsj desc";
            return SqlServerHelper.ExecuteDataTable(sql);
        }

        // 根据plcid查询endsts状态
        public string GetEndstaByPlcid(string plcId)
        {
            string sqlStr = "select top 1 * from t_djmx where plcid =" + plcId + " ORDER BY djsj DESC;";
            try
            {
                DataTable djmx = SqlServerHelper.ExecuteDataTable(sqlStr);
                return djmx.Rows[0]["endsts"].ToString();
            }
            catch (Exception e)
            {
                return null;
            }
        }

        // 根据格口查询格口锁定状态
        public string GetStsByProt(string prot)
        {
            string sqlStr = "select sts from t_prot where prot = " + prot;
            try
            {
                DataTable djmx = SqlServerHelper.ExecuteDataTable(sqlStr);
                return djmx.Rows[0]["sts"].ToString();
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public int InsertTask(string code)
        {
            string sqlStr = "insert into t_code (ExpressNo)values('" + code + "')";// set endprot='" + endprot + "',endsts='1',endate =getdate() where plcid='" + plcid + "' and endsts='0'";

            try
            {

                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);

                return i;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }
        public DataSet GetTaskData()
        {
//            string sqlStr = "select top 200 * from t_djmx order by djsj desc ;select prot,(case sts when '0' then'解锁' when '1' then '锁定' end) sts from t_prot  ";
            string sqlStr = "select top 200 * from t_djmx order by djsj desc ;select prot,(case sts when '0' then'解锁' when '1' then '锁定' end) sts,count from t_prot   ";
            try
            {
               DataSet data = SqlServerHelper.ExecuteDataSet(sqlStr);

                return data;

            }
            catch (Exception ex)
            {

                return null;
            }
           
        }
   
        public DataTable SelectSum(string prot)
        {
            DataTable tb = new DataTable();
            try
            {
                string sqlStr = "select * from  t_prot where prot='" + prot + "'";
                tb = SqlServerHelper.ExecuteDataTable(sqlStr);
                return tb;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return null;
            }
        }
        
        public int UpdateProtStsSum(string prot, int sum)
        {
            string sqlStr = "update t_prot set count='" + sum + " where prot='" + prot + "'";
            try
            {
                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);
                return i;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }
        public int UpdateEndprot(int endprot, string plcid)
        {
                //update t_djmx set endprot=4  where plcid=10417
            string sqlStr = "update t_djmx set endprot=" + endprot + " where plcid=" + plcid ;
            try
            {
                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);
                return i;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }
        public int UpdateCount(int prot)
        {
            //update t_prot set count=(select ifnull(count,'0') from t_prot where prot=111)+1 where prot=111
            string sqlStr = "update t_prot set count=count+1 where prot=" + prot ;
            try
            {
                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);
                return i;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }
        
        public int UpDataPlcData(string plcid, string expressNo,string endprot)
        {
            //string sqlStr = "update t_djmx set endprot='" + endprot + "',endsts='1',endate =getdate() where "
            //               + "  plcid=(selec top 1 plcid  from t_djmx plcid='" + plcid + "' and endsts='0' order by djsj desc) ";
//            string sqlStr = "update t_djmx set endprot='" + endprot + "',endsts='1',endate =getdate() " +
//                            "where plcid='" +plcid +"' and endsts='0' and ExpressNo = (select top 1 ExpressNo from t_djmx where plcid='" +
//                            plcid + "' and endsts='0' order by djsj desc)";



            //select top 1 ExpressNo from t_djmx where plcid=2 and endsts=1 order by djsj desc
            //                update t_djmx set endprot=9,endsts=1,endate =getdate() where plcid=4 and ExpressNo = 049158651324
            String sqlStr = " update t_djmx set endprot="+endprot+",endsts=1,endate =getdate() where plcid="+plcid+" and ExpressNo = "+expressNo;
            try
            {

                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);

                return i;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }

        public int UpdateProtData(string prot, string sts)
        {
            string sqlStr = "update t_prot set sts='" + sts + "',datasts='0'  where prot='" + prot +"'";
            try
            {
                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);

                return i;
            }
            catch (Exception ex)
            {
                
                return 0;
            }
        }
        public DataTable GetwmsSeqData(string wmsseq)
        {
            lock (this)
            {
                string sqlStr = "Select plcid from t_djmx where wmsseq='" + wmsseq + "' ";
                try
                {
                    return SqlServerHelper.ExecuteDataTable(sqlStr);

                }
                catch (Exception ex)
                {
                    SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                    return null;
                }
            }
        }
        public int UpDataWmsseqIdData(string seqId, string plcid)
        {
            lock (this)
            {
                string sqlStr = "update t_djmx set  wmsseq='" + seqId + "' where plcid='" + plcid + "'";

                try
                {

                    int i = SqlServerHelper.ExecuteNonQuery(sqlStr);

                    return i;
                }
                catch (Exception ex)
                {
                    SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                    return 0;
                }
            }
        }
        public int UpDataWmsPlcData(string plcid)
        {
            lock (this)
            {
                string sqlStr = "update t_djmx set  endsts='2' where plcid='" + plcid + "'";

                try
                {

                    int i = SqlServerHelper.ExecuteNonQuery(sqlStr);

                    return i;
                }
                catch (Exception ex)
                {
                    SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                    return 0;
                }
            }
        }
    
        public DataTable GetPlcSednCode()
        {
            lock (this)
            {
                string sqlStr = "Select * from t_djmx where endsts='1'  ";///and ExpressNo!='noread'
                try
                {
                    
                    return SqlServerHelper.ExecuteDataTable(sqlStr);

                }
                catch (Exception ex)
                {
                    SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                    return null;
                }
            }
        }
        /// <summary>
        /// 更新端口的状态
        /// </summary>
        /// <param name="prot"></param>
        /// <param name="sts"></param>
        /// <returns></returns>
        public int UpdateProtSts(string prot,string sts)
        {
            string sqlStr = "update t_prot set sts='"+sts+ "',datasts='1' where prot='"+prot+"'";

            try
            {

                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);

                return i;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }

    
        public int UpdateProtSendSts(string prot)
        {
            string sqlStr = "update t_prot set datasts='0' where prot='" + prot + "'";

            try
            {

                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);

                return i;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }
        /// <summary>
        /// 更新端口的状态
        /// </summary>
        /// <param name="prot"></param>
        /// <param name="sts"></param>
        /// <returns></returns>
        public int UpdateErrProtSts(string prot, string text,string sts)
        {
            string sqlStr = "update t_prot set sts='" + sts + "', text='"+ text + "' where prot='" + prot + "'";

            try
            {

                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);

                return i;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }
        public DataTable GetProtSend()
        {
            DataTable tb = new DataTable();
            try
            {
                string sqlStr = "select * from  t_prot where datasts='1' or datasts='2'  ";
                tb = SqlServerHelper.ExecuteDataTable(sqlStr);
            }
            catch (Exception ex)
            {
                return null;
            }
            return tb;
        }
        public string  GetWmsSeq()
        {
            DataTable tb = new DataTable();
            try
            {
                string sqlStr = "select (NEXT VALUE FOR seqID )as id ";
                tb = SqlServerHelper.ExecuteDataTable(sqlStr);
                if (tb != null)
                {
                    if (tb.Rows.Count > 0)
                    {
                        return tb.Rows[0]["id"].ToString();
                    }
                }
            }
            catch (Exception ex)
            {
          
            
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
               // return "0;
            }
            Random ran = new Random(); 
            return (ran.Next(1, 100)).ToString();
        }

        public int AddDjmx(string ExpressNo, string plcid, string prot)
        {
            

            string sqlStr = " insert into   t_djmx  (ExpressNo,plcid,prot )values('" + ExpressNo + "','" + plcid + "','" + prot + "');update t_code set sts='1' where ExpressNo='"+ExpressNo+"'";

            try{
                int i = SqlServerHelper.ExecuteNonQuery(sqlStr);

                return i;
            }
            catch (Exception ex)
            { return 0; }
    
        }
          
        public DataTable GetLogDaTable(string userid,string date,string enddate)
        {
            DataTable table = new DataTable();
            string sqlStr = " select * from [dbo].[t_mes] t"


        

                +" left join [dbo].[t_goods] d on t.goodsid =d.goodid "

                + " left join [dbo].[t_user] u on t.userid = t.userid  where  convert(varchar(100),begdate,23) >='" + date + "' and convert(varchar(100),begdate,23)<='" + enddate + "'";
            if (userid.Trim().Length > 0)
            {
                sqlStr = sqlStr + " and username ='" + userid + "'";
 
            }


            try
            {

                table = SqlServerHelper.ExecuteDataTable(sqlStr);
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return null;
            }
            return table;
        }

        // 执行dml
        private static int execDml(string sqlStr, SqlParameter[] commandParameters = null)
        {
            try
            {
                int i = SqlServerHelper.ExecuteNonQuery(CommandType.Text, sqlStr, commandParameters);
                return i;
            }
            catch (Exception ex)
            {
                SystemCommon.WriteLog("异常:" + ex.Message);
                return 0;
            }
        }

        public string encoding(string src)
        {
            var bytes = Encoding.Default.GetBytes(src);
            return Convert.ToBase64String(bytes);
        }

        public string decoding(string target)
        {
            var fromBase64String = Convert.FromBase64String(target);
            return Encoding.Default.GetString(fromBase64String);
        }

        private DateTime getTime0(string sql0)
        {
            var dt = SqlServerHelper.ExecuteDataTable(sql0);
            return (DateTime)dt.Rows[0]["time0"];
        }

        public string dateFormat(DateTime dateTime)
        {
            return dateTime.ToString("yyyy-MM-dd HH:mm:ss.fff");
        }

        private long span(DateTime start, DateTime end)
        {
            var timeSpan = end - start;
            return timeSpan.Seconds * 1000 + timeSpan.Milliseconds;
        }

        public int insertTime01(string barcode, string ExpressNo, DateTime time0)
        {
            var code = encoding(barcode);
            var time1 = DateTime.Now;
            var time1Span = span(time0, time1);

            string sql = "insert into t_time (barcode,ExpressNo,time0,time1) " +
                         "values(@barcode,@ExpressNo,@time0,@time1)";
            return execDml(sql, new[]
            {
                new SqlParameter {ParameterName = "@barcode", Value = code},
                new SqlParameter {ParameterName = "@ExpressNo", Value = ExpressNo},
                new SqlParameter {ParameterName = "@time0", Value = dateFormat(time0)},
                new SqlParameter {ParameterName = "@time1", Value = time1Span}
            });
        }

        public int updateTime23(string ExpressNo, string plcid, string port, DateTime time2)
        {
            var time3 = DateTime.Now;

            string sql0 = "SELECT TOP 1 time0 FROM t_time WHERE ExpressNo = '" + ExpressNo +
                          "' ORDER BY time0 DESC";
            var time0 = getTime0(sql0);

            var time2Span = span(time0, time2);
            var time3Span = span(time0, time3);

            string sql = "UPDATE t_time SET plcid = @plcid,port = @port,time2 = @time2,time3 = @time3" +
                         " where time0 = @time0";
            return execDml(sql, new[]
            {
                new SqlParameter {ParameterName = "@plcid", Value = plcid},
                new SqlParameter {ParameterName = "@port", Value = port},
                new SqlParameter {ParameterName = "@time2", Value = time2Span},
                new SqlParameter {ParameterName = "@time3", Value = time3Span},
                new SqlParameter {ParameterName = "@time0", Value = dateFormat(time0)}
            });
        }

        // 收到plc反馈落袋信息
        public int updateTime4(string plcid, string endport)
        {
            string sql0 = "SELECT TOP 1 time0 FROM t_time WHERE plcid = '" + plcid + "' ORDER BY time0 DESC";
            var time0 = getTime0(sql0);
            var time4Span = span(time0, DateTime.Now);

            string sql =
                "UPDATE t_time SET endport = " + endport + ","
                + " time4 = " + time4Span + " WHERE time0 = '" + dateFormat(time0) + "'";
            return execDml(sql);
        }

        // 向wms发送落袋信息
        public int updateTime5(string plcid)
        {
            string sql0 = "SELECT TOP 1 time0 FROM t_time WHERE plcid = '" + plcid + "' ORDER BY time0 DESC";
            var time0 = getTime0(sql0);
            var time5Span = span(time0, DateTime.Now);

            string sql =
                "UPDATE t_time SET time5 = " + time5Span + " WHERE time0 = '" + dateFormat(time0) + "'";
            return execDml(sql);
        }
    }
   
    public class ModeUser
    {
        

        public string userid { get; set; } 
        public string username { get; set; }
        public string password { get; set; }
        public string admin { get; set; }
    }

    public class ModeOrder
    {
        public string orderid { get; set; }
        public string code { get; set; }
        /// <summary>
        /// 路向
        /// </summary>
        public string prot { get; set; }
        public string user { get; set; }


        public string addtime { get; set; }
        /// <summary>
        /// 实际路向
        /// </summary>
        public string route { get; set; }


        public string sts { get; set; }
       public string rtetime { get; set; }
        /// <summary>
        /// 数据新增方式
        /// </summary>
        public string type { get; set; }
   }
}
