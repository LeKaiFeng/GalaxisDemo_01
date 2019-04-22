using Common;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace DataComon
{
    public class CreateData
    {
        //创建表单
        public static void createDataBase()
        {
            //条码临时表
            try
            {
                //条码记录表
                string str = "select count(1) as c from sqlite_master where type='table' and name='t_djmx'";
                string result = SqliteDbHelp.QueryReString(str, null).ToString();
                if (result.Equals("0"))
                {
                    SqliteDbHelp.ExecuteNonQuery(@"CREATE TABLE t_djmx(id integer primary key autoincrement,
ExpressNo varchar(255),routeId varchar(255),
prot varchar(255),sts varchar(255),djsj datetime,plcid varchar(255),
endprot varchar(255),endsts varchar(255),wgh varchar(255),
prtData varchar(255),result varchar(255),endate datetime,wmsseq varchar(255))", null);
                }
                //格口状态及数量表
                str = "select count(1) as c from sqlite_master where type='table' and name='t_prot'";
                result = SqliteDbHelp.QueryReString(str, null).ToString();
                if (result.Equals("0"))
                {
                    SqliteDbHelp.ExecuteNonQuery(@"CREATE TABLE t_prot(prot integer primary key autoincrement,
sts varchar(255),datasts varchar(255),text varchar(255),count int)", null);

                    List<string> stringsql = new List<string>();
                    for (int i = 1; i < 20; i++)
                    {
                        string sql = @"insert into   t_prot  (prot,sts,count,datasts )values('" + i + "','0','0','1')  ";

                        stringsql.Add(sql);
                    }
                    int a = SqliteDbHelp.ExecuteSqlTran(stringsql);
                }
                //实际反馈格口状态
                str = "select count(1) as c from sqlite_master where type='table' and name='t_protstate'";
                result = SqliteDbHelp.QueryReString(str, null).ToString();
                if (result.Equals("0"))
                {
                    SqliteDbHelp.ExecuteNonQuery(@"CREATE TABLE t_protstate(id integer primary key autoincrement,
ExpressNo varchar(255),plcid varchar(255),begindate datetime,overdate datetime,state int)", null);
                }
            }
            catch (Exception e1)
            {
                return;
            }
        }

        //删除超过7天数据
        public void deleteOldData()
        {
            //删除t_code表中 大于当前时间7天的所有数据
            //String sqlStr1 = "DELETE FROM t_code WHERE DATEDIFF(DAY,sj,getdate()) > 7";
            String sqlStr2 = "DELETE FROM t_djmx WHERE DATEDIFF(DAY,djsj,getdate()) > 7";
//            String sqlStr2 = "DELETE FROM t_djmx WHERE count(*)>10";


            try
            {
                //SqliteDbHelp.ExecuteNonQuery(sqlStr1,null);
                SqliteDbHelp.ExecuteNonQuery(sqlStr2, null);
            }
            catch (Exception e)
            {
                return;
            }
        }

        //查询格口状态、数量 
        public DataTable GetTaskData()
        {
            string selectStr = "select prot,(case sts when '0' then'解锁' when '1' then '锁定' end) sts,count from t_prot ";
            //string sqlStr = "select top 200 * from t_djmx order by djsj desc ;select prot,(case sts when '0' then'解锁' when '1' then '锁定' end) sts,count from t_prot   ";
            try
            {
                //DataSet data = SqliteDbHelp.ExecuteDataSet(sqlStr);
                DataTable currentDt = SqliteDbHelp.Query(selectStr, null);
                return currentDt;
            }
            catch (Exception ex)
            {
                return null;
            }
        }
      public DataTable SelectOldData()
        {
            string selectStr = "select    ExpressNo,plcid  from t_djmx  order by djsj desc limit 0,5 " ;
           try
            {
                //DataSet data = SqliteDbHelp.ExecuteDataSet(sqlStr);
                DataTable currentDt = SqliteDbHelp.Query(selectStr, null);
                return currentDt;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        //查询前200条条码数据 
        public DataTable TwohundredData()
        {
            string selectStr = "select * from t_djmx order by djsj desc limit 0,100  ";
            //string sqlStr = "select top 200 * from t_djmx order by djsj desc ;select prot,(case sts when '0' then'解锁' when '1' then '锁定' end) sts,count from t_prot   ";
            try
            {
                DataTable currentDt = SqliteDbHelp.Query(selectStr, null);
                return currentDt;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        //添加扫描到的条码记录
        public int InsertTask(string code,string time)
        {
            string sqlStr = "insert into t_djmx (ExpressNo,djsj)values('" + code + "','"+time+"')  ";

            try
            {
                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }
        // 更新端口的状态
        public int UpdateProtSts(string prot, string state, string sts)
        {
            string sqlStr = "update t_prot set sts='" + sts + "',datasts='" + state + "' where prot='" + prot + "'  ";
            try
            {

                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }

        public int UpDataPlcData(string plcid, string expressNo, string endprot)
        {
            String sqlStr = " update t_djmx set endprot=" + endprot + ",endsts=1,endate =getdate() where" +
                " plcid=" + plcid + " and ExpressNo ='" + expressNo + "'   ";
            try
            {
                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }

        public DataTable GetPlcSednCode()
        {
            lock (this)
            {
                //1代表还没传送给WMS   2代表已经传送给WMS
                string sqlStr = "Select * from t_djmx where endsts='1'  ";
                try
                {
                    DataTable currentDt = SqliteDbHelp.Query(sqlStr, null);
                    return currentDt;
                }
                catch (Exception ex)
                {
                    //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                    return null;
                }
            }
        }

        //查询实际格口是否写入
        public DataTable MatchingData()
        {
            lock (this)
            {
                //1代表还没匹配到DB块的值   2代表已经匹配到值
                string sqlStr = "Select * from t_protstate where state='1'  ";
                try
                {
                    DataTable currentDt = SqliteDbHelp.Query(sqlStr, null);
                    return currentDt;
                }
                catch (Exception ex)
                {
                 
                    return null;
                }
            }
        }

        public int UpDataWmsPlcData(string plcid)
        {
            lock (this)
            {
                string sqlStr = "update t_djmx set  endsts='2' where plcid='" + plcid + "'  ";
                try
                {
                    int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                    return i;
                }
                catch (Exception ex)
                {
                    //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                    return 0;
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
                    int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                    return i;
                }
                catch (Exception ex)
                {
                    //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                    return 0;
                }
            }
        }

        //查询格口号状态为 1或2的格口
        public DataTable GetProtSend()
        {
            //1代表还没传送给WMS   2代表已经传送给WMS
            string sqlStr = "select * from  t_prot where  datasts='1'  ";
            try
            {
                DataTable currentDt = SqliteDbHelp.Query(sqlStr, null);
                return currentDt;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public int UpDataWcsWms(string prot)
        {
            lock (this)
            {
                string sqlStr = "update t_prot set  datasts='2' where prot='" + prot + "'  ";
                try
                {
                    int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                    return i;
                }
                catch (Exception ex)
                {
                    //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                    return 0;
                }
            }
        }

        //更改实际落袋口
        public int UpdateEndprot(int endprot, string plcid)
        {
            string sqlStr = "update t_djmx set endprot=" + endprot + " where id=(select id " +
                "from t_djmx where plcid = '" + plcid + "' order by id desc limit 0,1) ";
            try
            {
                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }

        //查格口状态
        public bool SelectState(string plcid, string code)
        {
            try
            {
                DataTable tb = new DataTable();
                string sqlStr = "SELECT  * FROM t_prot where prot=(select prot from t_djmx WHERE ExpressNo='" + code + "'and plcid='" + plcid + "') ";
                tb = SqliteDbHelp.Query(sqlStr, null);
                if (tb != null)
                {
                    if (tb.Rows.Count > 0)
                    {
                        if (tb.Rows[0]["sts"].ToString() == "0")
                        {
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
                return false;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        //更改格口数量
        public int UpdateProtStsSum(string prot, int sum)
        {
            string sqlStr = "update t_prot set count='" + sum + " where prot='" + prot + "'";
            try
            {
                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }

        //更改格口数量
        public int UpdateCount(int prot)
        {
//            string sqlStr = "update t_prot set count=(select ifnull(count,'0') from t_prot where prot='" + prot + "')+1 where prot='" + prot + "'   ";
            string sqlStr = "update t_prot set count=count+1 where prot=" + prot ;
            try
            {
                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }

        // 更新端口的状态
        public int UpdateErrProtSts(string prot, string text, string sts)
        {
            string sqlStr = "update t_prot set sts='" + sts + "', text='" + text + "' where prot='" + prot + "'   ";
            try
            {
                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }
        //格口锁定清空数量
        public int clearCount(string prot)
        {
            string sqlStr = "update t_prot set count=0 where prot='" + prot + "' ";
            try
            {
                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return 0;
            }
        }
        public int UpdateDjmx(string ExpressNo, string plcid, string prot)
        {
            string time = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
            //string sqlStr = " insert into   t_djmx  (ExpressNo,plcid,prot )values('" + ExpressNo + "','" + plcid + "','" + prot + "') " ;
            //string sqlStr = "update t_djmx set plcid='" + plcid + "', prot='" + prot + "' where ExpressNo='" + ExpressNo + "'   ";
            string sqlStr = " update t_djmx set plcid = '" + plcid + "',endsts='1', prot ='" + prot + "' where id = (select id " +
                "from t_djmx where ExpressNo = '" + ExpressNo + "' order by id desc limit 0,1);  insert into   t_protstate  (ExpressNo,begindate,state,plcid )values('" + ExpressNo + "','"+ time + "','1', '" + plcid + "')  ";
            try
            {
                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                return 0;
            }
        }

        //更改实际格口表对应的条码状态
        public int UpdateMatching(string plcid)
        {
            string time = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
            string sqlStr = "update t_protstate set state='2', overdate ='"+time+"' " +
                "where id = (select id from t_protstate where plcid = '"+plcid+ "' and state='1' order by id desc limit 0,1) ";
            try
            {
                int i = SqliteDbHelp.ExecuteNonQuery(sqlStr, null);
                return i;
            }
            catch (Exception ex)
            {
                return 0;
            }
        }

        // 根据 快递单号模糊查询
        public DataTable queryByExpressNo(string expressNo, string start, string end)
        {
            DataTable tb = new DataTable();
            try
            {
                string sqlStr = "select * from t_djmx where ExpressNo like '%" + expressNo + "' " +
                " and (djsj between '" + start + "' and '" + end +
                "') order by djsj desc";
                tb = SqliteDbHelp.Query(sqlStr, null);
                return tb;
            }
            catch (Exception ex)
            {
                //SystemCommon.WriteLog("异常:" + ex.Message.ToString());
                return null;
            }
        }
    }
}
