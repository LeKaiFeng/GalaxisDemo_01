using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using DataComon;

namespace Wcs
{
    public partial class FormQuery : Form
    {
        //private LogData logdata = new LogData();
        CreateData createdata = new CreateData();
        public FormQuery()
        {
            InitializeComponent();
            this.dateTimePicker1.Value =Convert.ToDateTime( DateTime.Now.ToString("yyyy-MM-dd 00:00:00"));
            this.dateTimePicker2.Value =Convert.ToDateTime( DateTime.Now.ToString("yyyy-MM-dd 23:59:59"));
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string eno = textBox1.Text;
            var start = dateTimePicker1.Text;
            var end = dateTimePicker2.Text;

            if (eno.Length < 4)
            {
                MessageBox.Show("请输入4位以上的尾号！");
            }
            else
            {
                //var dataTable = logdata.queryByExpressNo(eno, start, end);
                var dataTable = createdata.queryByExpressNo(eno, start, end);
                if (dataTable.Rows.Count == 0)
                {
                    MessageBox.Show("没有搜索到相关条码！");
                }
                else
                {
                    gdc_data.DataSource = dataTable;
                }
            }
        }
    }
}
