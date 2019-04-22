using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WCS.socket
{
    public class TimedEventArgs : EventArgs
    {
        #region protected members
        protected DateTime eventTime;
        #endregion

        #region properties
        public DateTime EventTime
        {
            get { return eventTime; }
        }
        #endregion

        #region constructor
        public TimedEventArgs()
        {
            eventTime = DateTime.Now;
        }
        #endregion
    }
}
