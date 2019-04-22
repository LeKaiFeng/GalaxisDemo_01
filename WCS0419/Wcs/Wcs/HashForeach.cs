using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WCS;

namespace Wcs
{
    public class HashForeach
    {
        public static Dictionary<string, int> openWith = new Dictionary<string, int>();
        public static Dictionary<int, string> openstate = new Dictionary<int, string>();
        public static void HashAdd(string plcid, int Mouthid)
        {
            openWith.Remove(plcid);
            openWith.Add(plcid, Mouthid);
            Log.WriteLog(DateTime.Now + " 添加plcid："+plcid+"格口："+Mouthid );
        }

        public static void HashStateAdd(int i, string state)
        {
            openstate.Remove(i);
            openstate.Add(i, state);
        }
    }
}
