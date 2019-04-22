using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using DevExpress.XtraSplashScreen;

namespace WCS
{
    public partial class SplashScreen1 : SplashScreen
    {
        public SplashScreen1()
        {
            InitializeComponent();
        }

        #region Overrides

        public override void ProcessCommand(Enum cmd, object arg)
        {
            base.ProcessCommand(cmd, arg);
            if ( cmd.GetType() == SplashScreenCommand.labelControl2.GetType())
            {
                labelControl2.Text = arg.ToString();
            }
        }

        #endregion

        public enum SplashScreenCommand
        {
            labelControl2
            

        }

        private void SplashScreen1_TextChanged(object sender, EventArgs e)
        {
             
            labelControl2.Text = this.Text;
        }
    }
}