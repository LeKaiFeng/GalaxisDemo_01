 
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using OpcRcw.Da;
using System.ComponentModel;

namespace WCS
{
    /// <summary>
    /// 对db模块的 写操作
    /// </summary>
    public interface IPLCAbstract
    {
        

        object  WriteDataValue
        {
            get;
            set;
        }
        object ReadDataValue
        {
            get;
        }
        // /// <summary>
        // /// 1)写操作的入口
        // /// </summary>
        // /// <param name="dataPLC">算法生成的数据</param>
        // /// <param name="ServerOPC">plc的类</param>
        // /// <param name="Form_Main">当前的窗体类</param>
        // /// <param name="work">用于多线程的类控件</param>
        //void DataTableBill(DataTable dataPLC, string remoteServerName, string remoteServerIP, object Form_Main, BackgroundWorker work);

        /// <summary>
        /// plc的链接
        /// </summary>
        /// <param name="remoteServerName">链接的名字（）</param>
        /// <param name="remoteServerIP">链接的ip地址</param>
        /// <returns></returns>
          string ConnectRemoteServer();

        /// <summary>
        /// 增加一个group
        /// </summary>
          /// <param name="Form_Main"></param>
        /// <returns></returns>
          string PLCGroupAdd(object Form_Main);

        /// <summary>
        /// 给group增加item
        /// </summary>
        /// <param name="Group"></param>
        /// <returns></returns>
          string PLCItemAdd(OPCITEMDEF[] items);

        /// <summary>
        /// 进行写plc数据
        /// </summary>
        /// <param name="valueData">要写入的数据(可以是数据集，也可以是数组)</param>
        /// <returns></returns>
          string WirtePlc(object[] values);
        /// <summary>
        /// 对plc进行读操作
        /// </summary>
        /// <returns></returns>
        string ReadPlc();
        /// <summary>
        /// 检测plc数据改变时，触发事件
        /// </summary>
        /// <returns></returns>
        string DataChange();
        /// <summary>
        /// 对外提供的plc读或者写的方法
        /// </summary>
        /// <param name="remoteServerName"></param>
        /// <param name="remoteServerIP"></param>
        /// <param name="formClass"></param>
        /// <returns></returns>
        string ReadWritePlcData();

        /// <summary>
        /// 断开连接
        /// </summary>
        /// <returns></returns>
        string DisConnection();
    }
}
