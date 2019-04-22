/**
 * 
 * SQLSERVER辅助类,封装对SQLSERVER的各种操作.
 * 
 * Author FeiLong Wang (hustemb@gmail.com)
 * Copyright (c) 2006. All Rights Reserved.
 * LastModifiedDate:2008-1-26
 * 
 * 
 */
using System;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Collections;

namespace DataCommon
{
    public class SqlServerHelper : IDisposable
    {
         
        #region Varibales
        // 适配器
       // private static SqlDataAdapter m_adapter=new SqlDataAdapter();
        // SQL命令对象
      //  private static SqlCommand m_command=new SqlCommand();
        // 数据库连接字符串
        private static readonly string m_connectionString = "server=(local);Initial Catalog=shunfeng;User Id = sa;Password =sa123;Integrated Security=false";
        private static Hashtable m_paramcache = Hashtable.Synchronized(new Hashtable());

        public static string ConnectionString
        {
            get { return SqlServerHelper.m_connectionString; }
        } 

        #endregion        

        #region Contructors
        /// <summary>
        /// 释放对象资源
        /// </summary>
        public void Dispose( )
        {
          //  Dispose(m_adapter,true);
            GC.SuppressFinalize(true);
        }

        /// <summary>
        ///	释放对象的实例



        /// </summary>
        protected virtual void Dispose(SqlDataAdapter m_adapter, bool disposing)
        {
            if (!disposing)
                return;

            if (m_adapter != null)
            {
                if (m_adapter.SelectCommand != null)
                {
                    if (m_adapter.SelectCommand.Connection != null)
                    {
                        m_adapter.SelectCommand.Connection.Dispose();
                    }
                    m_adapter.SelectCommand.Dispose();
                }
                m_adapter.Dispose();
                m_adapter = null;
            }
        }
        #endregion        

        #region ExecuteNonQuery
        /// <summary>
        /// 直接执行SQL语句
        /// 示例:
        ///  int result = ExecuteNonQuery("SELECT * FROM [table123]");
        /// </summary>
        /// <param name="commandText">SQL语句</param>
        /// <returns>返回命令影响的行数</returns>
        public static int ExecuteNonQuery(string commandText)
        {
            lock (locks)
            {
                return ExecuteNonQuery(CommandType.Text, commandText);
            }
        }

        /// <summary>
        /// 执行指定连接字符串,类型的SqlCommand.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int result = ExecuteNonQuery(connString, CommandType.StoredProcedure, "PublishOrders");
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本, 其它.)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <returns>返回命令影响的行数</returns>
        public static int ExecuteNonQuery(CommandType commandType, string commandText)
        {
            lock (locks)
            {
                return ExecuteNonQuery(commandType, commandText, (SqlParameter[])null);
            }
        }

        /// <summary>
        /// 执行指定连接字符串,类型的SqlCommand.如果没有提供参数,不返回结果.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int result = ExecuteNonQuery(CommandType.StoredProcedure, "PublishOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本, 其它.)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <param name="commandParameters">SqlParameter参数数组</param>
        /// <returns>返回命令影响的行数</returns>
        public static int ExecuteNonQuery(CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (m_connectionString == null || m_connectionString.Length == 0) throw new ArgumentNullException("ConnectionString");

            using (SqlConnection connection = new SqlConnection(m_connectionString))
            {
                connection.ConnectionString = m_connectionString;
                if (connection.State == ConnectionState.Closed) 
               connection.Open();

                return ExecuteNonQuery(connection, commandType, commandText, commandParameters);
            }
        }

        /// <summary>
        /// 执行指定数据库连接对象的命令 
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int result = ExecuteNonQuery(conn, CommandType.StoredProcedure, "PublishOrders");
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型(存储过程,命令文本或其它.)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <returns>返回影响的行数</returns>
        public static int ExecuteNonQuery(SqlConnection connection, CommandType commandType, string commandText)
        {
            return ExecuteNonQuery(connection, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行带事务的SqlCommand.
        /// </summary>
        /// <remarks>
        /// 示例.:  
        ///  int result = ExecuteNonQuery(trans, CommandType.StoredProcedure, "PublishOrders");
        /// </remarks>
        /// <param name="transaction">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型(存储过程,命令文本或其它.)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <returns>返回影响的行数/returns>
        public static int ExecuteNonQuery(SqlTransaction transaction, CommandType commandType, string commandText)
        {
            return ExecuteNonQuery(transaction, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定数据库连接对象的命令
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int result = ExecuteNonQuery(conn, CommandType.StoredProcedure, "PublishOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型(存储过程,命令文本或其它.)</param>
        /// <param name="commandText">T存储过程名称或SQL语句</param>
        /// <param name="commandParameters">SqlParamter参数数组</param>
        /// <returns>返回影响的行数</returns>
        public static int ExecuteNonQuery(SqlConnection connection, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (connection == null) throw new ArgumentNullException("connection");

            // 创建SqlCommand命令,并进行预处理
            SqlCommand cmd =new SqlCommand();
            bool mustCloseConnection = false;
            PrepareCommand(cmd, connection, (SqlTransaction)null, commandType, commandText, commandParameters, out mustCloseConnection);

            // Finally, execute the command
            int retval = cmd.ExecuteNonQuery();

            // 清除参数,以便再次使用.
            cmd.Parameters.Clear();
            if (mustCloseConnection)
                connection.Close();
            return retval;
        }

        /// <summary>
        /// 执行带事务的SqlCommand(指定参数).
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int result = ExecuteNonQuery(trans, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="transaction">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型(存储过程,命令文本或其它.)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <param name="commandParameters">SqlParamter参数数组</param>
        /// <returns>返回影响的行数</returns>
        public static int ExecuteNonQuery(SqlTransaction transaction, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (transaction == null) throw new ArgumentNullException("transaction");
            if (transaction != null && transaction.Connection == null) throw new ArgumentException("The transaction was rollbacked or commited, please provide an open transaction.", "transaction");

            // 预处理



            SqlCommand cmd = new SqlCommand();
            bool mustCloseConnection = false;
            PrepareCommand(cmd, transaction.Connection, transaction, commandType, commandText, commandParameters, out mustCloseConnection);

            // 执行
            int retval = cmd.ExecuteNonQuery();

            // 清除参数集,以便再次使用.
            cmd.Parameters.Clear();
            return retval;
        }

        #endregion
  
        #region ExecuteDataTable
        private static string locks = string.Empty;
        /// <summary>
        /// 执行指定数据库连接字符串的命令,返回DataTable.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataTable dt = ExecuteDataTable("SELECT * FROM [table1]");
        /// </remarks>
        /// <param name="commandText">SQL语句</param>
        /// <returns>返回一个包含结果集的DataTable</returns>
        public static DataTable ExecuteDataTable(string commandText)
        {
            lock (locks)
            {
                return ExecuteDataTable(CommandType.Text, commandText, (SqlParameter[])null);
            }
        }

        /// <summary>
        /// 执行指定数据库连接字符串的命令,返回DataTable.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataTable dt = ExecuteDataTable(connString, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <returns>返回一个包含结果集的DataTable</returns>
        public static DataTable ExecuteDataTable(CommandType commandType, string commandText)
        {
            lock (locks)
            {
                return ExecuteDataTable(commandType, commandText, (SqlParameter[])null);
            }
        }

        /// <summary>
        /// 执行指定数据库连接字符串的命令,返回DataTable.
        /// </summary>
        /// <remarks>
        /// 示例: 
        ///  DataTable dt = ExecuteDataTable(connString, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <param name="commandParameters">SqlParamters参数数组</param>
        /// <returns>返回一个包含结果集的DataTable</returns>
        public static DataTable ExecuteDataTable(CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            lock (locks)
            {
                if (m_connectionString == null || m_connectionString.Length == 0) throw new ArgumentNullException("ConnectionString");
                // 创建并打开数据库连接对象,操作完成释放对象.
                using (SqlConnection connection = new SqlConnection(m_connectionString))
                {
                    connection.ConnectionString = m_connectionString;
                    if (connection.State == ConnectionState.Closed)
                        connection.Open();
                    // 调用指定数据库连接字符串重载方法.
                    return ExecuteDataTable(connection, commandType, commandText, commandParameters);
                }
            }
        }

        /// <summary>
        /// 执行指定数据库连接对象的命令,返回DataTable.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataTable dt = ExecuteDataTable(conn, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <returns>返回一个包含结果集的DataTable</returns>
        public static DataTable ExecuteDataTable(SqlConnection connection, CommandType commandType, string commandText)
        {
            return ExecuteDataTable(connection, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定数据库连接对象的命令,指定存储过程参数,返回DataTable.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataTable dt = ExecuteDataTable(conn, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <param name="commandParameters">SqlParamter参数数组</param>
        /// <returns>返回一个包含结果集的DataTable</returns>
        public static DataTable ExecuteDataTable(SqlConnection connection, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (connection == null) throw new ArgumentNullException("connection");
            // 预处理
            SqlCommand cmd = new SqlCommand(); ;
            bool mustCloseConnection = false;
            PrepareCommand(cmd, connection, (SqlTransaction)null, commandType, commandText, commandParameters, out mustCloseConnection);
            // 创建SqlDataAdapter和DataTable.
            using (SqlDataAdapter da = new SqlDataAdapter())
            {
                da.SelectCommand = cmd;
                DataTable dt = new DataTable();
                // 填充DataTable.
                da.Fill(dt);
              
                cmd.Parameters.Clear();
                if (mustCloseConnection)
                    connection.Close();
                return dt;
            }
        }

        /// <summary>
        /// 执行指定事务的命令,返回DataTable.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataTable dt = ExecuteDataTable(trans, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="transaction">事务</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <returns>返回一个包含结果集的DataTable</returns>
        public static DataTable ExecuteDataTable(SqlTransaction transaction, CommandType commandType, string commandText)
        {
            return ExecuteDataTable(transaction, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定事务的命令,指定参数,返回DataTable.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataTable dt = ExecuteDataTable(trans, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="transaction">事务</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <param name="commandParameters">SqlParamter参数数组</param>
        /// <returns>返回一个包含结果集的DataTable</returns>
        public static DataTable ExecuteDataTable(SqlTransaction transaction, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (transaction == null) throw new ArgumentNullException("transaction");
            if (transaction != null && transaction.Connection == null) throw new ArgumentException("The transaction was rollbacked or commited, please provide an open transaction.", "transaction");
            // 预处理
            SqlCommand cmd = new SqlCommand(); ;
            bool mustCloseConnection = false;
            PrepareCommand(cmd, transaction.Connection, transaction, commandType, commandText, commandParameters, out mustCloseConnection);
            // 创建 DataAdapter & DataTable
            using (SqlDataAdapter da  =new SqlDataAdapter() )
            {
                da.SelectCommand = cmd;
                DataTable dt = new DataTable();
                da.Fill(dt);
                cmd.Parameters.Clear();
                return dt;
            }
        }
        #endregion

        #region ExecuteDataSet
        /// <summary>
        /// 执行指定数据库连接字符串的命令,返回DataSet.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataSet ds = ExecuteDataSet("SELECT * FROM [table1]");
        /// </remarks>
        /// <param name="commandText">SQL语句</param>
        /// <returns>返回一个包含结果集的DataSet</returns>
        public static DataSet ExecuteDataSet(string commandText)
        {
            return ExecuteDataSet(CommandType.Text, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定数据库连接字符串的命令,返回DataSet.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataSet ds = ExecuteDataSet(connString, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <returns>返回一个包含结果集的DataSet</returns>
        public static DataSet ExecuteDataSet(CommandType commandType, string commandText)
        {
            return ExecuteDataSet(commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定数据库连接字符串的命令,返回DataSet.
        /// </summary>
        /// <remarks>
        /// 示例: 
        ///  DataSet ds = ExecuteDataSet(connString, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <param name="commandParameters">SqlParamters参数数组</param>
        /// <returns>返回一个包含结果集的DataSet</returns>
        public static DataSet ExecuteDataSet(CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (m_connectionString == null || m_connectionString.Length == 0) throw new ArgumentNullException("ConnectionString");

            // 创建并打开数据库连接对象,操作完成释放对象.
            using (SqlConnection connection = new SqlConnection(m_connectionString))
            {
                connection.ConnectionString = m_connectionString;
                if (connection.State == ConnectionState.Closed) 
                connection.Open();

                // 调用指定数据库连接字符串重载方法.
                return ExecuteDataSet(connection, commandType, commandText, commandParameters);
            }
        }

        /// <summary>
        /// 执行指定数据库连接对象的命令,返回DataSet.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataSet ds = ExecuteDataSet(conn, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <returns>返回一个包含结果集的DataSet</returns>
        public static DataSet ExecuteDataSet(SqlConnection connection, CommandType commandType, string commandText)
        {
            return ExecuteDataSet(connection, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定数据库连接对象的命令,指定存储过程参数,返回DataSet.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataSet ds = ExecuteDataSet(conn, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <param name="commandParameters">SqlParamter参数数组</param>
        /// <returns>返回一个包含结果集的DataSet</returns>
        public static DataSet ExecuteDataSet(SqlConnection connection, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            try
            {
                if (connection == null) throw new ArgumentNullException("connection");
                // 预处理
                SqlCommand cmd = new SqlCommand(); ;
                bool mustCloseConnection = false;
                PrepareCommand(cmd, connection, (SqlTransaction)null, commandType, commandText, commandParameters, out mustCloseConnection);
                // 创建SqlDataAdapter和DataSet.
                using (SqlDataAdapter da = new SqlDataAdapter())
                {
                    da.SelectCommand = cmd;
                    DataSet ds = new DataSet();
                    // 填充DataSet.
                    da.Fill(ds);
                    cmd.Parameters.Clear();
                    if (mustCloseConnection)
                        connection.Close();
                    return ds;
                }
            }
            catch (Exception exp)
            {
                throw exp;
            }
        }

        /// <summary>
        /// 执行指定事务的命令,返回DataSet.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataSet ds = ExecuteDataSet(trans, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="transaction">事务</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <returns>返回一个包含结果集的DataSet</returns>
        public static DataSet ExecuteDataSet(SqlTransaction transaction, CommandType commandType, string commandText)
        {
            return ExecuteDataSet(transaction, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定事务的命令,指定参数,返回DataSet.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  DataSet ds = ExecuteDataSet(trans, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="transaction">事务</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <param name="commandParameters">SqlParamter参数数组</param>
        /// <returns>返回一个包含结果集的DataSet</returns>
        public static DataSet ExecuteDataSet(SqlTransaction transaction, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (transaction == null) throw new ArgumentNullException("transaction");
            if (transaction != null && transaction.Connection == null) throw new ArgumentException("The transaction was rollbacked or commited, please provide an open transaction.", "transaction");
            // 预处理



            SqlCommand cmd = new SqlCommand();
            bool mustCloseConnection = false;
            PrepareCommand(cmd, transaction.Connection, transaction, commandType, commandText, commandParameters, out mustCloseConnection);

            // 创建 DataAdapter & DataSet
            using (SqlDataAdapter da =new SqlDataAdapter())
            {
                da.SelectCommand = cmd;
                DataSet ds = new DataSet();
                da.Fill(ds);
                cmd.Parameters.Clear();
                return ds;
            }
        }
        #endregion

        #region ExecuteScalar
        /// <summary>
        /// 执行指定数据库连接字符串的命令,返回结果集中的第一行第一列.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int orderCount = (int)ExecuteScalar("SELECT MAX(PK_ID) FROM [table123]");
        /// </remarks>
        /// <param name="commandText">SQL语句</param>
        /// <returns>返回结果集中的第一行第一列</returns>
        public static object ExecuteScalar(string commandText)
        {
            // 执行参数为空的方法



            return ExecuteScalar(CommandType.Text, commandText);
        }

        /// <summary>
        /// 执行指定数据库连接字符串的命令,返回结果集中的第一行第一列.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int orderCount = (int)ExecuteScalar(connString, CommandType.StoredProcedure, "GetOrderCount");
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <returns>返回结果集中的第一行第一列</returns>
        public static object ExecuteScalar(CommandType commandType, string commandText)
        {
            // 执行参数为空的方法



            return ExecuteScalar(commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定数据库连接字符串的命令,指定参数,返回结果集中的第一行第一列.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int orderCount = (int)ExecuteScalar(connString, CommandType.StoredProcedure, "GetOrderCount", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <param name="commandParameters">分配给命令的SqlParamter参数数组</param>
        /// <returns>返回结果集中的第一行第一列</returns>
        public static object ExecuteScalar(CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (ConnectionString == null || ConnectionString.Length == 0) throw new ArgumentNullException("ConnectionString");
            // 创建并打开数据库连接对象,操作完成释放对象.
            using (SqlConnection connection = new SqlConnection(m_connectionString))
            {
                connection.ConnectionString = ConnectionString;
                    if (connection.State == ConnectionState.Closed) 
                connection.Open();

                // 调用指定数据库连接字符串重载方法.
                return ExecuteScalar(connection, commandType, commandText, commandParameters);
            }
        }
       

        /// <summary>
        /// 执行指定数据库连接对象的命令,返回结果集中的第一行第一列.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int orderCount = (int)ExecuteScalar(conn, CommandType.StoredProcedure, "GetOrderCount");
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <returns>返回结果集中的第一行第一列</returns>
        public static object ExecuteScalar(SqlConnection connection, CommandType commandType, string commandText)
        {
            // 执行参数为空的方法



            return ExecuteScalar(connection, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定数据库连接对象的命令,指定参数,返回结果集中的第一行第一列.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int orderCount = (int)ExecuteScalar(conn, CommandType.StoredProcedure, "GetOrderCount", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <param name="commandParameters">分配给命令的SqlParamter参数数组</param>
        /// <returns>返回结果集中的第一行第一列</returns>
        public static object ExecuteScalar(SqlConnection connection, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (connection == null) throw new ArgumentNullException("connection");

            // 创建SqlCommand命令,并进行预处理
            SqlCommand cmd = new SqlCommand(); ;

            bool mustCloseConnection = false;
            PrepareCommand(cmd, connection, (SqlTransaction)null, commandType, commandText, commandParameters, out mustCloseConnection);

            // 执行SqlCommand命令,并返回结果.
            object retval = cmd.ExecuteScalar();

            // 清除参数,以便再次使用.
            cmd.Parameters.Clear();

            if (mustCloseConnection)
                connection.Close();

            return retval;
        }

        /// <summary>
        /// 执行指定数据库事务的命令,返回结果集中的第一行第一列.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int orderCount = (int)ExecuteScalar(trans, CommandType.StoredProcedure, "GetOrderCount");
        /// </remarks>
        /// <param name="transaction">一个有效的连接事务</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <returns>返回结果集中的第一行第一列</returns>
        public static object ExecuteScalar(SqlTransaction transaction, CommandType commandType, string commandText)
        {
            // 执行参数为空的方法



            return ExecuteScalar(transaction, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定数据库事务的命令,指定参数,返回结果集中的第一行第一列.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  int orderCount = (int)ExecuteScalar(trans, CommandType.StoredProcedure, "GetOrderCount", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="transaction">一个有效的连接事务</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <param name="commandParameters">分配给命令的SqlParamter参数数组</param>
        /// <returns>返回结果集中的第一行第一列</returns>
        public static object ExecuteScalar(SqlTransaction transaction, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (transaction == null) throw new ArgumentNullException("transaction");
            if (transaction != null && transaction.Connection == null) throw new ArgumentException("The transaction was rollbacked or commited, please provide an open transaction.", "transaction");

            // 创建SqlCommand命令,并进行预处理
            SqlCommand cmd =new SqlCommand();
            bool mustCloseConnection = false;
            PrepareCommand(cmd, transaction.Connection, transaction, commandType, commandText, commandParameters, out mustCloseConnection);

            // 执行SqlCommand命令,并返回结果.
            object retval = cmd.ExecuteScalar();

            // 清除参数,以便再次使用.
            cmd.Parameters.Clear();
            return retval;
        }

        #endregion

        #region ExecuteReader
        /// <summary>
        /// 执行指定数据库连接字符串的数据阅读器.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  SqlDataReader dr = ExecuteReader(connString, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <returns>返回包含结果集的SqlDataReader</returns>
        public static SqlDataReader ExecuteReader(string commandText)
        {
            return ExecuteReader(CommandType.Text, commandText);
        }
        /// <summary>
        /// 执行指定数据库连接字符串的数据阅读器.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  SqlDataReader dr = ExecuteReader(connString, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <returns>返回包含结果集的SqlDataReader</returns>
        public static SqlDataReader ExecuteReader(CommandType commandType, string commandText)
        {
            return ExecuteReader(commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// 执行指定数据库连接字符串的数据阅读器,指定参数.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  SqlDataReader dr = ExecuteReader(connString, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <param name="commandParameters">SqlParamter参数数组(new SqlParameter("@prodid", 24))</param>
        /// <returns>返回包含结果集的SqlDataReader</returns>
        public static SqlDataReader ExecuteReader(CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (m_connectionString == null || m_connectionString.Length == 0) throw new ArgumentNullException("ConnectionString");
            SqlConnection connection = null;
            try
            {
                connection = new SqlConnection(m_connectionString);
                connection.ConnectionString = m_connectionString;
                if (connection.State == ConnectionState.Closed) 
                connection.Open();

                return ExecuteReader(connection, null, commandType, commandText, commandParameters, SqlConnectionOwnership.Internal);
            }
            catch(Exception exp)
            {
                // If we fail to return the SqlDatReader, we need to close the connection ourselves
                if (connection != null) connection.Close();
                throw exp;
            }

        }

        /// <summary>
        /// 执行指定数据库连接对象的数据阅读器.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  SqlDataReader dr = ExecuteReader(conn, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <returns>返回包含结果集的SqlDataReader</returns>
        public static SqlDataReader ExecuteReader(SqlConnection connection, CommandType commandType, string commandText)
        {
            return ExecuteReader(connection, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// [调用者方式]执行指定数据库连接对象的数据阅读器,指定参数.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  SqlDataReader dr = ExecuteReader(conn, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandParameters">SqlParamter参数数组</param>
        /// <returns>返回包含结果集的SqlDataReader</returns>
        public static SqlDataReader ExecuteReader(SqlConnection connection, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            return ExecuteReader(connection, (SqlTransaction)null, commandType, commandText, commandParameters, SqlConnectionOwnership.External);
        }

        /// <summary>
        /// [调用者方式]执行指定数据库事务的数据阅读器,指定参数值.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///  SqlDataReader dr = ExecuteReader(trans, CommandType.StoredProcedure, "GetOrders");
        /// </remarks>
        /// <param name="transaction">一个有效的连接事务</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <returns>返回包含结果集的SqlDataReader</returns>
        public static SqlDataReader ExecuteReader(SqlTransaction transaction, CommandType commandType, string commandText)
        {
            return ExecuteReader(transaction, commandType, commandText, (SqlParameter[])null);
        }

        /// <summary>
        /// [调用者方式]执行指定数据库事务的数据阅读器,指定参数.
        /// </summary>
        /// <remarks>
        /// 示例:  
        ///   SqlDataReader dr = ExecuteReader(trans, CommandType.StoredProcedure, "GetOrders", new SqlParameter("@prodid", 24));
        /// </remarks>
        /// <param name="transaction">一个有效的连接事务</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名称或SQL语句</param>
        /// <param name="commandParameters">分配给命令的SqlParamter参数数组</param>
        /// <returns>返回包含结果集的SqlDataReader</returns>
        public static SqlDataReader ExecuteReader(SqlTransaction transaction, CommandType commandType, string commandText, params SqlParameter[] commandParameters)
        {
            if (transaction == null) throw new ArgumentNullException("transaction");
            if (transaction != null && transaction.Connection == null) throw new ArgumentException("The transaction was rollbacked or commited, please provide an open transaction.", "transaction");

            return ExecuteReader(transaction.Connection, transaction, commandType, commandText, commandParameters, SqlConnectionOwnership.External);
        }

        /// <summary>
        /// 枚举,标识数据库连接是由BaseSqlHelper提供还是由调用者提供



        /// </summary>
        private enum SqlConnectionOwnership
        {
            /// <summary>由BaseSqlHelper提供连接</summary>
            Internal,
            /// <summary>由调用者提供连接</summary>
            External
        }

        /// <summary>
        /// 执行指定数据库连接对象的数据阅读器.
        /// </summary>
        /// <remarks>
        /// 如果是BaseSqlHelper打开连接,当连接关闭DataReader也将关闭.
        /// 如果是调用都打开连接,DataReader由调用都管理.
        /// </remarks>
        /// <param name="connection">一个有效的数据库连接对象</param>
        /// <param name="transaction">一个有效的事务,或者为 'null'</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本或其它)</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <param name="commandParameters">SqlParameters参数数组,如果没有参数则为'null'</param>
        /// <param name="connectionOwnership">标识数据库连接对象是由调用者提供还是由BaseSqlHelper提供</param>
        /// <returns>返回包含结果集的SqlDataReader</returns>
        private static SqlDataReader ExecuteReader(SqlConnection connection, SqlTransaction transaction, CommandType commandType, string commandText, SqlParameter[] commandParameters, SqlConnectionOwnership connectionOwnership)
        {
            if (connection == null) throw new ArgumentNullException("connection");
            bool mustCloseConnection = false;
            // 创建命令
            SqlCommand cmd = new SqlCommand(); ;
            try
            {
                PrepareCommand(cmd, connection, transaction, commandType, commandText, commandParameters, out mustCloseConnection);
                // 创建数据阅读器



                SqlDataReader dataReader;
                if (connectionOwnership == SqlConnectionOwnership.External)
                {
                    dataReader = cmd.ExecuteReader();
                }
                else
                {
                    dataReader = cmd.ExecuteReader(CommandBehavior.CloseConnection);
                }
                // 清除参数,以便再次使用..
                // HACK There is a problem here, the output parameter values are fletched 
                // when the reader is closed, so if the parameters are detached from the command
                // then the SqlReader can set its values. 
                // When this happen, the parameters can be used again in other command.
                bool canClear = true;
                foreach (SqlParameter commandParameter in cmd.Parameters)
                {
                    if (commandParameter.Direction != ParameterDirection.Input)
                        canClear = false;
                }

                if (canClear)
                {
                    cmd.Parameters.Clear();
                }
                return dataReader;
            }
            catch(Exception exp)
            {
                if (mustCloseConnection)
                    connection.Close();
                throw exp;
            }
        }

        #endregion

        #region Common Utility
        /// <summary>
        /// 预处理用户提供的命令,数据库连接/事务/命令类型/参数
        /// </summary>
        /// <param name="command">要处理的SqlCommand</param>
        /// <param name="connection">数据库连接</param>
        /// <param name="transaction">一个有效的事务或者是null值</param>
        /// <param name="commandType">命令类型 (存储过程,命令文本, 其它.)</param>
        /// <param name="commandText">存储过程名或都SQL命令文本</param>
        /// <param name="commandParameters">和命令相关联的SqlParameter参数数组,如果没有参数为'null'</param>
        /// <param name="mustCloseConnection"><c>true</c> 如果连接是打开的,则为true,其它情况下为false.</param>
        private static void PrepareCommand(SqlCommand command, SqlConnection connection, SqlTransaction transaction, CommandType commandType, string commandText, SqlParameter[] commandParameters, out bool mustCloseConnection)
        {
            if (command == null) throw new ArgumentNullException("command");
            if (commandText == null || commandText.Length == 0) throw new ArgumentNullException("commandText");
            // 如果连接没有打开则打开连接
            if (connection.State != ConnectionState.Open)
            {
                mustCloseConnection = true;
                if (connection.State == ConnectionState.Closed) 
                connection.Open();
            }
            else
            {
                mustCloseConnection = false;
            }
            // 给命令分配一个数据库连接.
            command.Connection = connection;
            // 设置命令文本(存储过程名或SQL语句)
            command.CommandText = commandText;
            // 分配事务
            if (transaction != null)
            {
                if (transaction.Connection == null) throw new ArgumentException("The transaction was rollbacked or commited, please provide an open transaction.", "transaction");
                command.Transaction = transaction;
            }
            // 设置命令类型.
            command.CommandType = commandType;
            // 分配命令参数
            if (commandParameters != null)
            {
                command.Parameters.Clear();
                foreach (SqlParameter parm in commandParameters)
                    command.Parameters.Add(parm);
            }
            return;
        }
        
        /// <summary>
        /// 将DataReader转化为DataSet
        /// </summary>
        /// <param name="reader">DataReader数据接口</param>
        /// <returns>DataSet格式的数据</returns>
        private static DataSet Reader2Set(IDataReader reader)
        {
            DataTable table = new DataTable();
            int fieldCount = reader.FieldCount;
            for (int i = 0; i < fieldCount; i++)
            {
                table.Columns.Add(reader.GetName(i), reader.GetFieldType(i));
            }
            table.BeginLoadData();
            object[] values = new object[fieldCount];
            while (reader.Read())
            {
                reader.GetValues(values);
                table.LoadDataRow(values, true);
            }
            table.EndLoadData();
            DataSet ds = new DataSet();
            ds.Tables.Add(table);
            return ds;
        }

        /// <summary>
        /// 将DataReader转化为DataTable
        /// </summary>
        /// <param name="reader">DataReader数据接口</param>
        /// <returns>DataTable格式的数据</returns>
        private static DataTable Reader2Table(IDataReader reader)
        {
            DataTable table = new DataTable();
            int fieldCount = reader.FieldCount;
            for (int i = 0; i < fieldCount; i++)
            {
                table.Columns.Add(reader.GetName(i), reader.GetFieldType(i));
            }
            table.BeginLoadData();
            object[] values = new object[fieldCount];
            while (reader.Read())
            {
                reader.GetValues(values);
                table.LoadDataRow(values, true);
            }
            table.EndLoadData();
            return table;
        }
        #endregion

        #region Cache

        /// <summary>
        /// 追加参数数组到缓存.
        /// </summary>
        /// <param name="ConnectionString">一个有效的数据库连接字符串</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <param name="commandParameters">要缓存的参数数组</param>
        public static void CacheParameterSet(string commandText, params SqlParameter[] commandParameters)
        {
            if (ConnectionString == null || ConnectionString.Length == 0) throw new ArgumentNullException("ConnectionString");
            if (commandText == null || commandText.Length == 0) throw new ArgumentNullException("commandText");

            string hashKey = ConnectionString + ":" + commandText;

            m_paramcache[hashKey] = commandParameters;
        }

        /// <summary>
        /// 从缓存中获取参数数组.
        /// </summary>
        /// <param name="ConnectionString">一个有效的数据库连接字符</param>
        /// <param name="commandText">存储过程名或SQL语句</param>
        /// <returns>参数数组</returns>
        public static SqlParameter[] GetCachedParameterSet(string commandText)
        {
            if (ConnectionString == null || ConnectionString.Length == 0) throw new ArgumentNullException("ConnectionString");
            if (commandText == null || commandText.Length == 0) throw new ArgumentNullException("commandText");

            string hashKey = ConnectionString + ":" + commandText;

            SqlParameter[] cachedParameters = m_paramcache[hashKey] as SqlParameter[];
            if (cachedParameters == null)
            {
                return null;
            }
            else
            {
                return CloneParameters(cachedParameters);
            }
        }
        /// <summary>
        /// SqlParameter参数数组的深层拷贝.
        /// </summary>
        /// <param name="originalParameters">原始参数数组</param>
        /// <returns>返回一个同样的参数数组</returns>
        private static SqlParameter[] CloneParameters(SqlParameter[] originalParameters)
        {
            SqlParameter[] clonedParameters = new SqlParameter[originalParameters.Length];

            for (int i = 0, j = originalParameters.Length; i < j; i++)
            {
                clonedParameters[i] = (SqlParameter)((ICloneable)originalParameters[i]).Clone();
            }

            return clonedParameters;
        }
        #endregion Cache
    }

}
