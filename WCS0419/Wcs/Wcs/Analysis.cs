using System;
using System.Collections.Generic;
using System.Text;

namespace Wcs
{
    /**
     * 通讯报文
     */
    public class Post
    {
        // 起始字符 0xAA(顺丰)/0xCC(供应商) 1
        private string stx;

        // 数据信息域总长度 +1(指令码) +4(SEQ) 16进制 2
        private string len;

        // 请求序列号(循环+1)，标识同一条请求 4
        private string seq;

        // 指令码 1
        private string ins;

        // 数据信息域
        private string data;

        // 前面所有字节的异或(包括起始字符) 16进制 1
        private string xor;

        // 结束字符 1
        private string etx = "55";

        // 接收的时候使用的构造方法 AA001A000000418300000012650200030C3034393631323031383537390255
        public Post(string returnStr)
        {
            stx = MyUtils.BytesToStr(MyUtils.Sub(1, ref returnStr));
            len = MyUtils.BytesToStr(MyUtils.Sub(2, ref returnStr));
            seq = MyUtils.BytesToStr(MyUtils.Sub(4, ref returnStr));
            ins = MyUtils.BytesToStr(MyUtils.Sub(1, ref returnStr));
            var dataLen = Convert.ToInt32(len, 16) - 5;
            data = MyUtils.BytesToStr(MyUtils.Sub(dataLen, ref returnStr));
            xor = MyUtils.BytesToStr(MyUtils.Sub(1, ref returnStr));
            etx = MyUtils.BytesToStr(MyUtils.Sub(1, ref returnStr));
        }

        public override string ToString()
        {
            return stx + len + seq + ins + data + xor + etx;
        }

        public string Stx
        {
            get { return stx; }
            set { stx = value; }
        }

        public int Len
        {
            get { return Convert.ToInt32(len, 16); }
            set { len = Convert.ToString(value, 16); }
        }

        public int Seq
        {
            get { return Convert.ToInt32(seq, 16); }
            set { seq = Convert.ToString(value, 16); }
        }

        public string Ins
        {
            get { return ins; }
            set { ins = value; }
        }

        public string Data
        {
            get { return data; }
            set { data = value; }
        }

        public string Xor
        {
            get { return xor; }
            set { xor = value; }
        }

        public string Etx
        {
            get { return etx; }
            set { etx = value; }
        }
    }

    /**
     * 解析83反馈
     */
    public class Data83Rec
    {
        // 状态码 1
        private string status;

        // 请求消息的SEQ 4
        private string seq;

        // 格口号长度 当格口号只有一个或者异常的情况下格口号长度为0x02 1
        private string portLen;

        // 格口号或异常码 每2个字节表示一个格口号 
        private string port;

        // 运单长度 1
        private string codeLen;

        // 运单号
        private string code;

        public Data83Rec(string data)
        {
            status = MyUtils.BytesToStr(MyUtils.Sub(1, ref data));
            seq = MyUtils.BytesToStr(MyUtils.Sub(4, ref data));
            portLen = MyUtils.BytesToStr(MyUtils.Sub(1, ref data));
            port = MyUtils.BytesToStr(MyUtils.Sub(Convert.ToInt32(portLen, 16), ref data));
            codeLen = MyUtils.BytesToStr(MyUtils.Sub(1, ref data));
            code = MyUtils.BytesToStr(MyUtils.Sub(Convert.ToInt32(codeLen, 16), ref data));
        }

        public override string ToString()
        {
            return status + seq + portLen + port + codeLen + code;
        }

        public string Status
        {
            get { return status; }
            set { status = value; }
        }

        public int Seq
        {
            get { return Convert.ToInt32(seq, 16); }
            set { seq = Convert.ToString(value, 16); }
        }

        public int PortLen
        {
            get { return Convert.ToInt32(portLen, 16); }
            set { portLen = Convert.ToString(value, 16); }
        }

        public string Port
        {
            get { return port; }
            set { port = value; }
        }

        public int CodeLen
        {
            get { return Convert.ToInt32(codeLen, 16); }
            set { codeLen = Convert.ToString(value, 16); }
        }

        public string Code
        {
            get { return MyUtils.AsciiToStr(code); }
            set { code = MyUtils.StrToAscii(value); }
        }
    }


    public class MyUtils
    {
        // 把字符串转为16进制字节数组, 添加到list中
        public static void AddToByte16List(string str, ref List<byte> list)
        {
            var bytes = StrToBytes(str);
            for (int n = bytes.Length - 1; n >= 0; n--)
            {
                list.Add(bytes[n]);
            }
        }

        // 把字符串转为16进制字节数组
        public static byte[] StrToBytes(string str)
        {
            var length = str.Length / 2;
            var bytes = new byte[length];
            for (var i = 0; i < length; i++)
            {
                var x = Convert.ToInt32(str.Substring(i * 2, 2), 16);
                bytes[i] = (byte)x;
            }

            return bytes;
        }

        // 从接收的字符串中, 截取字符串, 转为字节数组
        public static byte[] Sub(int length, ref string returnStr)
        {
            var bytes = new byte[length];
            for (var i = 0; i < length; i++)
            {
                var x = Convert.ToInt32(returnStr.Substring(i * 2, 2), 16);
                bytes[i] = (byte)x;
            }

            returnStr = returnStr.Substring(length * 2);
            return bytes;
        }

        // 将16进制字节数组转为字符串
        public static string BytesToStr(byte[] bytes)
        {
            return BitConverter.ToString(bytes, 0).Replace("-", string.Empty);
        }

        // 将16进制Ascii编码的字符串, 进行解码
        public static string AsciiToStr(string ascii16Str)
        {
            var strToByte16Arr = StrToBytes(ascii16Str);
            return Encoding.ASCII.GetString(strToByte16Arr);
        }

        // 将常规字符串, 进行编码为16进制Ascii码
        public static string StrToAscii(string str)
        {
            var bytes = Encoding.ASCII.GetBytes(str);
            return BytesToStr(bytes);
        }

        // int值转为bytes
        public static byte[] IntToBytes(int value, int len = 4)
        {
            byte[] bytes = new byte[len];
            for (int i = 0; i < len; i++)
            {
                bytes[i] = (byte)((value >> (i * 8)) & 0xFF);
            }

            return bytes;
        }
    }

}