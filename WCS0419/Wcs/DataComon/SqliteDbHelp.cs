using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.Linq;
using System.Text;

namespace DataComon
{
    class SqliteDbHelp
    {
        public static String GetAppRunPath()
        {
            string sr = Environment.CurrentDirectory;
            return sr;
        }

        public static string FilePath = GetAppRunPath() + "\\Galaxis.db";

        private static string DBFilePath = "Data Source=" + FilePath;
        public static string QueryReString(string sql, SQLiteParameter[] parameters)
        {
            using (SQLiteConnection connection = new SQLiteConnection(DBFilePath))
            {
                using (SQLiteCommand command = new SQLiteCommand(sql, connection))
                {
                    if (parameters != null)
                    {
                        command.Parameters.AddRange(parameters);
                    }
                    try
                    {
                        using (SQLiteDataAdapter adapter = new SQLiteDataAdapter(command))
                        {
                            DataTable data = new DataTable();
                            adapter.Fill(data);
                            if (data.Rows.Count == 0)
                            {
                                return null;
                            }
                            else
                            {
                                return data.Rows[0][0].ToString();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        throw e;
                        return null;
                    }
                }
            }
        }
        /// <summary> 
        /// 对SQLite数据库执行增删改操作，返回受影响的行数。 
        /// </summary> 
        /// <param name="sql">要执行的增删改的SQL语句</param> 
        /// <param name="parameters">执行增删改语句所需要的参数，参数必须以它们在SQL语句中的顺序为准</param> 
        /// <returns></returns> 
        public static int ExecuteNonQuery(string sql, SQLiteParameter[] parameters)
        {
            int affectedRows = 0;
            using (SQLiteConnection connection = new SQLiteConnection(DBFilePath))
            {
                connection.Open();
                using (SQLiteTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        using (SQLiteCommand command = new SQLiteCommand(connection))
                        {
                            command.CommandText = sql;
                            if (parameters != null)
                            {
                                command.Parameters.AddRange(parameters);
                            }
                            affectedRows = command.ExecuteNonQuery();
                        }
                        transaction.Commit();
                    }
                    catch (Exception e)
                    {
                        transaction.Rollback();
                        throw e;
                        return 0;
                    }
                }
            }
            return affectedRows;
        }
        /// <summary> 
        /// 执行一个查询语句，返回一个包含查询结果的DataTable 
        /// </summary> 
        /// <param name="sql">要执行的查询语句</param> 
        /// <param name="parameters">执行SQL查询语句所需要的参数，参数必须以它们在SQL语句中的顺序为准</param> 
        /// <returns></returns> 
        public static DataTable Query(string sql, SQLiteParameter[] parameters)
        {
            using (SQLiteConnection connection = new SQLiteConnection(DBFilePath))
            {
                using (SQLiteCommand command = new SQLiteCommand(sql, connection))
                {
                    if (parameters != null)
                    {
                        command.Parameters.AddRange(parameters);
                    }
                    try
                    {
                        using (SQLiteDataAdapter adapter = new SQLiteDataAdapter(command))
                        {
                            DataTable data = new DataTable();
                            adapter.Fill(data);
                            return data;
                        }
                    }
                    catch (Exception e)
                    {
                        throw e;
                        return null;
                    }
                }
            }
        }
        //执行sql数组
        public static int ExecuteSqlTran(List<String> SQLStringList)
        {
            using (SQLiteConnection conn = new SQLiteConnection(DBFilePath))
            {
                conn.Open();
                using (SQLiteCommand cmd = new SQLiteCommand())
                {
                    cmd.Connection = conn;
                    using (SQLiteTransaction tx = conn.BeginTransaction())
                    {
                        cmd.Transaction = tx;
                        try
                        {
                            int count = 0;
                            for (int n = 0; n < SQLStringList.Count; n++)
                            {
                                string strsql = SQLStringList[n];
                                if (strsql.Trim().Length > 1)
                                {
                                    cmd.CommandText = strsql;
                                    count += cmd.ExecuteNonQuery();
                                }
                            }
                            tx.Commit();
                            return count;
                        }
                        catch (Exception e)
                        {
                            tx.Rollback();
                            throw e;
                            return 0;
                        }
                    }
                }
            }
        }
    }
}
