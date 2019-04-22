using System;
using System.Data;
using System.Collections;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.ComponentModel;

using System.IO;
namespace Common

{
	/// <summary>
	/// SystemCommon
	/// </summary>
	public class SystemCommon
	{
		private const int MAX_NUMBER = 10000;

		#region ��ʾ��ʾ��Ϣ
        /// <summary>
        /// ����������ʾ��Ϣ������Ϊ������
        /// </summary>
        /// <param name="errorInfoString">������Ϣ</param>
        public static void ShowErrorMessageBox(string errorInfoString)
        {
            MessageBox.Show(errorInfoString, "����", MessageBoxButtons.OK, MessageBoxIcon.Hand, MessageBoxDefaultButton.Button1);
            // MessageBox.Show(errorInfoString, "����", MessageBoxButtons.OK, MessageBoxIcon.Hand, MessageBoxDefaultButton.Button1);
        }


        /// <summary>
        /// ������ʾ��Ϣ������Ϊ����ʾ��
        /// </summary>
        /// <param name="errorInfoString">��ʾ��Ϣ</param>
        public static void ShowInfoMessageBox(string infoString)
        {
            MessageBox.Show(infoString, "ϵͳ��ʾ", MessageBoxButtons.OK, MessageBoxIcon.Information);
            //MessageBox.Show(infoString, "ϵͳ��ʾ", MessageBoxButtons.OK, MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button1);
        }

        /// <summary>
        /// ���������������Ϣ������Ϊ��ȷ�ϡ�
        /// </summary>
        /// <param name="errorInfoString">��ʾ��Ϣ</param>
        public static DialogResult ShowMessageBoxResult(string infoString)
        {
            return MessageBox.Show(infoString, "ȷ��", MessageBoxButtons.YesNo, MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button2);
            //MessageBox.Show(infoString, "ϵͳ��ʾ", MessageBoxButtons.OK, MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button1);
        }
        /// <summary>
        /// ���������Ƿ��Լ�ȡ����Ϣ������Ϊ��ȷ�ϡ�
        /// </summary>
        /// <param name="errorInfoString">��ʾ��Ϣ</param>
        public static DialogResult ShowMessageBoxResultCancel(string infoString)
        {
            return MessageBox.Show(infoString, "ȷ��", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button2);
            //MessageBox.Show(infoString, "ϵͳ��ʾ", MessageBoxButtons.OK, MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button1);
        }
		#endregion

		#region ����ListView�е���
		/// <summary>
		/// ����ListView�е���
		/// </summary>
		/// <param name="sortString">
		///  �������
		/// </param>
        public static void SortListViewItem(DataTable table, string column, object sender, string[] columnItems)
		{
             
            ListView lv = (ListView)sender;
 
            if (lv.Items.Count == 0)
            {
                return;
            } 
           
            if (lv.Tag == null)
            {
                lv.Tag = "ASC";
            }
            else if (lv.Tag.ToString().Trim() == "ASC")
            {
                lv.Tag = "DESC";
            }
            else if (lv.Tag.ToString().Trim() == "DESC")
            {
                lv.Tag = "ASC";
            }
            else
            {
                lv.Tag = "ASC";
            }
            string sort = lv.Tag.ToString().Trim();

            DataView view = new DataView(table, "", column + " " + sort, DataViewRowState.CurrentRows);
            
       
            BindListViewData(lv, view.ToTable(), columnItems);
 
		}
		#endregion

		#region ��ʼ��ListView
		/// <summary>
		/// ��ʼ��ListView
		/// </summary>
		/// <param name="listView">ListView</param>
		public static void InitListView(ListView listView)
		{
			listView.View = View.Details;
      
			listView.FullRowSelect = true;
			//listView.ColumnClick += new ColumnClickEventHandler(listView_ColumnClick);
		}
		#endregion

		#region ��ListView������
		/// <summary>
		/// ��ListView������
		/// </summary>
		/// <param name="listView">ListView</param>
		/// <param name="dataTable">����Դ</param>
		/// <param name="columnItems">ListView��Ҫ��������</param>
		public static void BindListViewData(ListView listView, DataTable dataTable, string [] columnItems)
		{
			listView.Items.Clear();
			 AddListViewItem(listView, dataTable, columnItems);
		}

		/// <summary>
		/// ��ListView������
		/// </summary>
		/// <param name="listView">ListView</param>
		/// <param name="dataRows">����Դ</param>
		/// <param name="columnItems">ListView��Ҫ��������</param>
		public static void BindListViewData(ListView listView, DataRow [] dataRows, string [] columnItems)
		{
			listView.Items.Clear();
			AddListViewItem(listView, dataRows, columnItems);
		}
		#endregion

		#region ��DataTable�е�������ӵ�ListView��
		/// <summary>
		/// ��DataTable�е�������ӵ�ListView��(����������)
		/// </summary>
		/// <param name="listView">ListView</param>
		/// <param name="dataTable">����Դ</param>
		/// <param name="columnsItems">ListView��Ҫ��������</param>
		public static void AddListViewItem(BackgroundWorker work ,ListView listView, DataTable dataTable, string [] columnItems)
		{
			if((dataTable == null) || (dataTable.Rows.Count == 0))
			{
				return;
			}
			int columnCount = columnItems.Length;
            listView.Items.Clear();
            int j = 0;
			foreach(DataRow dr in dataTable.Rows)
			{
            
				
                string [] items = new string [columnCount]; 
                for(int i = 0; i < columnCount; i ++)
                {
                  
                    items[i] = dr[columnItems[i]].ToString().Trim();  
				
                } 
                
                listView.Items.Add(new ListViewItem(items));
             
                work.ReportProgress(j); 
                j++;
			
            }
            //for (int i = 0; i < listView.Items.Count; i++)
            //{
            //    if (i % 2 == 0)
            //    {
            //        listView.Items[i].BackColor = System.Drawing.Color.WhiteSmoke;
            //    }
            //    else
            //    {
            //        listView.Items[i].BackColor = System.Drawing.Color.Transparent;
            //    }
            //}
   
		}
        /// <summary>
        /// ��DataTable�е�������ӵ�ListView��
        /// </summary>
        /// <param name="listView">ListView</param>
        /// <param name="dataTable">����Դ</param>
        /// <param name="columnsItems">ListView��Ҫ��������</param>
        public static void AddListViewItem(ListView listView, DataTable dataTable, string[] columnItems)
        {
            if ((dataTable == null) || (dataTable.Rows.Count == 0))
            {
                return;
            }
            int columnCount = columnItems.Length;

            int j = 0;
        
            foreach (DataRow dr in dataTable.Rows)
            {
                try
                {

                    string[] items = new string[columnCount];
                    for (int i = 0; i < columnCount; i++)
                    {
                        items[i] = dr[columnItems[i]].ToString().Trim();

                    }
                    listView.Items.Add(new ListViewItem(items));
                  
                }
                catch (Exception xe)
                { }
         
                

            }
            //for (int i = 0; i < listView.Items.Count; i++)
            //{
            //    if (i % 2 == 0)
            //    {
            //        listView.Items[i].BackColor = System.Drawing.Color.DarkSeaGreen;
            //    }
            //    else
            //    {
            //        listView.Items[i].BackColor = System.Drawing.Color.Transparent;
            //    }
            //}

        }
		public static void AddListViewItem(ListView listView, DataRow[] drs, string [] columnItems)
		{
			if((drs == null) || (drs.Length == 0))
			{
				return;
			}
            listView.Items.Clear();
			int columnCount = columnItems.Length;
			foreach(DataRow dr in drs)
			{
				string [] items = new string [columnCount];
				for(int i = 0; i < columnCount; i ++)
				{
					items[i] = dr[columnItems[i]].ToString().Trim();
				}

				listView.Items.Add(new ListViewItem(items));
			}
            //for (int i = 0; i < listView.Items.Count; i++)
            //{
            //    if (i % 2 == 0)
            //    {
            //        listView.Items[i].BackColor = System.Drawing.Color.DarkSeaGreen;
            //    }
            //    else
            //    {
            //        listView.Items[i].BackColor = System.Drawing.Color.Transparent;
            //    }
            //}
		}
		#endregion

		#region ɾ��ListView�е���
		/// <summary>
		/// ɾ��ListView�е���
		/// </summary>
		/// <param name="listView"></param>
		/// <param name="deleteItem"></param>
		public static void DeleteListViewItem(ListView listView, ListViewItem deleteItem)
		{
			listView.Items.Remove(deleteItem);
		}

		/// <summary>
		/// ɾ��ListView�е���
		/// </summary>
		/// <param name="listView"></param>
		/// <param name="deleteItems"></param>
		public static void DeleteListViewItem(ListView listView, ArrayList deleteItems)
		{
			foreach(object aItem in deleteItems)
			{
				DeleteListViewItem(listView, (ListViewItem)aItem);
			}
		}
		#endregion

		#region �жϵ�ǰ�����Ƿ�Ϊ���֣���󳤶�����Ϊ9��
        public static string ReplaceString(string input)
        {
            return Regex.Replace(input,@"[^0-9]+","");
        }
		/// <summary>
		/// �жϵ�ǰ�����Ƿ�Ϊ������
		/// </summary>
		/// <param name="input">Input</param>
		/// <returns></returns>
		public static bool IsPositiveInteger(string input)
		{
			return Regex.IsMatch(input, @"^\d{1,9}$");
		}

		/*
		/// <summary>
		/// �жϵ�ǰ�����Ƿ�Ϊ����������Ϊ������
		/// </summary>
		/// <param name="input">Input</param>
		/// <returns></returns>
		public static bool IsInteger(string input)
		{
			return Regex.IsMatch(input, @"^-?\d{1,9}$");
		}*/

		/// <summary>
		/// �жϵ�ǰ�����Ƿ�Ϊ����������������
		/// </summary>
		/// <param name="input">Input</param>
		/// <returns></returns>
		public static bool IsPositiveFloat(string input)
		{
			return Regex.IsMatch(input, @"^(0|[1-9]\d{0,8})(.\d{0,4})?$");
			// ������ 1.00
			// @"^(\+|-)?(0|[1-9]\d*)(.\d*)?$" �� ����ʵ��
			// @"^(0|[1-9]\d*)(.\d*[1-9])?$"   �� ��������1.00
		}
		#endregion

		#region �жϵ�ǰ�����Ƿ�Ϊ��ȷ�ĳ������£���ʽ��yyyy.MM��
		/// <summary>
		/// �жϵ�ǰ�����Ƿ�Ϊ��ȷ�ĳ������£���ʽ��yyyy.MM��
		/// </summary>
		/// <param name="input">Input</param>
		/// <returns></returns>
		public static bool IsRightPublishDate(string input)
		{
			return Regex.IsMatch(input, @"^[1-9]\d{3}.(0[1-9]|1[0-2])$");
		}
		#endregion

		#region �ϲ���ͬ�ṹ������DataTable
		/// <summary>
		/// �ϲ���ͬ�ṹ������DataTable
		/// </summary>
		/// <param name="originalDt">ԭDataTable</param>
		/// <param name="aimDt">Ŀ��DataTable</param>
		/// <returns></returns>
		public static DataTable DataTableAdd(DataTable originalDt, DataTable aimDt)
		{
			foreach(DataRow dr in originalDt.Rows)
			{
				aimDt.ImportRow(dr);
			}
			aimDt.AcceptChanges();
			return aimDt;
		}
		#endregion

		#region ��10λISBNת��Ϊ13λISBN
		/// <summary>
		/// ��10λISBNת��Ϊ13λISBN
		/// </summary>
		/// <param name="inputValue">10λISBN</param>
		/// <returns></returns>
		public static string ConvertIsbn(string inputValue)
		{
			string outValue = inputValue.Trim().Replace("X", "x");
			if(!Regex.IsMatch(outValue, @"^[7]\d{8}([x]|\d{1})$"))
			{
				return inputValue;
			}

			int flag1;
			if(outValue.EndsWith("x"))
			{
				flag1 = 10;
			}
			else if(outValue.EndsWith("0"))
			{
				flag1 = 11;
			}
			else
			{
				flag1 = int.Parse(outValue.Substring(9, 1));
			}

			char [] valueChar = outValue.Substring(0, 9).ToCharArray();
			int flag2 = 0; 
			int i = 0;
			foreach(char a in valueChar)
			{
				i ++;
				flag2 += int.Parse(a.ToString()) * (11 - i);
			}

			if(flag1 != (11 - (flag2 % 11)))
			{
				return inputValue;
			}

			//����У����
			int bit1 = 0;
			int bit2 = 0;
			outValue = "978" + outValue.Substring(0, 9);
			valueChar = outValue.ToCharArray();
			i = 0;
			foreach(char a in valueChar)
			{
				if((i % 2) == 0)
				{
					//����λ
					bit2 += int.Parse(a.ToString());
				}
				else
				{
					//ż��λ
					bit1 += int.Parse(a.ToString());
				}
				i ++;
			}

			int bit = bit1 * 3 + bit2;
			bit = 10 - (bit % 10);
			if(bit == 10)
			{
				bit = 0;
			}
			outValue += bit;
			return outValue;
		}
		#endregion

		#region ���ListView ColumnHeaderʱ����
		private static void listView_ColumnClick(object sender, System.Windows.Forms.ColumnClickEventArgs e)
		{
			ListView lv = (ListView)sender;
			ShowInfoMessageBox(lv.Columns[e.Column].Text);
			if(lv.Items.Count == 0)
			{
				return;
			}

			DataTable dt = new DataTable();
			int colCount = lv.Columns.Count;
			string[] columns = new string[colCount];
			for(int i = 0; i < colCount; i ++)
			{
				string colText = lv.Columns[i].Text.Trim();
				columns[i] = colText;
				dt.Columns.Add(colText);
			}
			foreach(ListViewItem lvi in lv.Items)
			{
				string[] row = new string[colCount];
				for(int i = 0; i < colCount; i ++)
				{
					row[i] = lvi.SubItems[i].Text.Trim();
				}
				dt.Rows.Add(row);
			}
			DataRow[] drs = dt.Select(string.Empty, lv.Columns[e.Column] + " asc");
			BindListViewData(lv, drs, columns);
		}
		#endregion

		#region ��ԴDataTable��ָ�����е����������µ����ظ���DataTable
		/*
		/// <summary>
		/// ��ԴDataTable��ָ�����е����������µ����ظ���DataTable������ָ���ֶ�˳������
		/// </summary>
		/// <param name="originalDt">ԴDataTable</param>
		/// <param name="needColumns">��Ҫ����</param>
		/// <returns>���ظ���DataTable</returns>
		public static DataTable GetDistinctSubTable(DataTable originalDt, string[] needColumns)
		{
			int colCount = needColumns.Length;
			if((originalDt == null) || (colCount == 0))
			{
				return null;
			}

			string sortColumns = string.Empty;
			DataTable dt = new DataTable();
			foreach(string aCol in needColumns)
			{
				dt.Columns.Add(aCol);
				sortColumns += "," + aCol;
			}
			sortColumns = sortColumns.Substring(1);

			int rowCount = 0;
			string[] lastValue = new string[colCount];
			foreach(DataRow dr in originalDt.Select(string.Empty, sortColumns))
			{
				string[] rowValues = new string[colCount];
				for(int i = 0; i < colCount; i ++)
				{
					rowValues[i] = dr[needColumns[i]].ToString().Trim();
				}

				if((rowCount == 0) || (rowValues != lastValue))
				{
					dt.Rows.Add(rowValues);
					lastValue = rowValues;
					rowCount ++;
				}
			}
			dt.AcceptChanges();

			return dt;
		}*/
		#endregion

		#region �ж�����������Ƿ����MAX_NUMBER
		public static bool IsMoreThanMaxNumber(int number)
		{
			return number > MAX_NUMBER;
		}
		#endregion


        #region ���table ������
        /// <summary>
        ///  ���table ������
        /// </summary>
        /// <param name="table"></param>
        public static void ClearTableRows(DataSet table)
        {
            if (table != null)
            {
                if (table.Tables.Count > 0)
                {
                    if (table.Tables[0].Rows.Count > 0)
                    {
                        table.Tables[0].Rows.Clear();

                    }
                }

            }
        }
        #endregion

		#region DataTable ��ҳ
		public static DataTable GetDataTableFT(int from, int length,DataTable dt,out bool flag)
		{
			flag = false;
			  
			DataTable newdt = new DataTable();		 
			newdt = dt.Clone();
			if (from<dt.Rows.Count)
			{
				int i;
				for (i = from; i < from+length; i++)
				{
					if (i < dt.Rows.Count)
					{
						newdt.ImportRow(dt.Rows[i]);
					}
					else
					{
						flag = true;
						break;
					}

				}
				if (i == dt.Rows.Count)
				{
					flag = true;
				}
			}
	 
			return newdt;
		}
		#endregion 

        #region ��־��¼
        private static object writelog = new object();
        /// <summary>
        /// д��־
        /// </summary>
        /// <param name="strLog"></param>
        public static void WriteLog(string strLog)
        {
            lock (writelog)
            {
                try
                {
                    strLog = "ʱ��:" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + Environment.NewLine + strLog + Environment.NewLine;
                    DirectoryInfo di = new DirectoryInfo(AppDomain.CurrentDomain.BaseDirectory + "\\Log");
                    if (di.Exists == false)
                    {
                        di.Create();
                    }
                    string logFileName;
                    logFileName = AppDomain.CurrentDomain.BaseDirectory + "\\Log\\" + DateTime.Now.ToString("yyyyMMdd") + ".log";
                    //����10M����ԭ�ļ�
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

