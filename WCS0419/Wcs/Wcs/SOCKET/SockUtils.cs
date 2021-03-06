﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;

namespace WCS
{
    public enum WsaError
    {
        WSAEINTR = 10004,           // call interrupted
        WSAEADDRNOTAVAIL = 10049,   // address not available
        WSAEADDRINUSE = 10048,      // address already in use
        WSACONNABORTED = 10053,     // connection aborted
        WSAECONNRESET = 10054,      // connection reset by peer
        WSAECONNREFUSED = 10061     // connection refused
    }
    public class SockUtils
    {
        public static string LastError = string.Empty;

        /// <summary>
        /// Turn on keep alive on a socket.</summary>
        /// <param name="turnOnAfter">
        /// Specifies the timeout, in milliseconds, with no activity until the first keep-alive packet is sent
        /// <param name="keepAliveInterval">
        /// Specifies the interval in milliseconds to send the keep alive packet.</param>
        /// <remarks>The keepAliveInternal doesn't seem to do any difference!</remarks>
        public static bool SetKeepAlive(Socket socket, ulong turnOnAfter, ulong keepAliveInterval)
        {
            int bytesperlong = 4;   // in c++ a long is four bytes long
            int bitsperbyte = 8;

            try
            {
                // Enables or disables the per-connection setting of the TCP keep-alive option which 
                // specifies the TCP keep-alive timeout and interval. The argument structure for 
                // SIO_KEEPALIVE_VALS is specified in the tcp_keepalive structure defined in the Mstcpip.h 
                // header file. This structure is defined as follows: 
                // /* Argument structure for SIO_KEEPALIVE_VALS */
                // struct tcp_keepalive {
                //    u_long  onoff;
                //    u_long  keepalivetime;
                //    u_long  keepaliveinterval;
                //};
                // SIO_KEEPALIVE_VALS is supported on Windows 2000 and later.
                byte[] SIO_KEEPALIVE_VALS = new byte[3 * bytesperlong];
                ulong[] input = new ulong[3];

                // put input arguments in input array
                if (turnOnAfter == 0 || keepAliveInterval == 0) // enable disable keep-alive
                    input[0] = (0UL); // off
                else
                    input[0] = (1UL); // on

                input[1] = (turnOnAfter);
                input[2] = (keepAliveInterval);

                // pack input into byte struct
                for (int i = 0; i < input.Length; i++)
                {
                    SIO_KEEPALIVE_VALS[i * bytesperlong + 3] = (byte)(input[i] >> ((bytesperlong - 1) * bitsperbyte) & 0xff);
                    SIO_KEEPALIVE_VALS[i * bytesperlong + 2] = (byte)(input[i] >> ((bytesperlong - 2) * bitsperbyte) & 0xff);
                    SIO_KEEPALIVE_VALS[i * bytesperlong + 1] = (byte)(input[i] >> ((bytesperlong - 3) * bitsperbyte) & 0xff);
                    SIO_KEEPALIVE_VALS[i * bytesperlong + 0] = (byte)(input[i] >> ((bytesperlong - 4) * bitsperbyte) & 0xff);
                }
                // create bytestruct for result (bytes pending on server socket)
                byte[] result = BitConverter.GetBytes(0);

                // write SIO_VALS to Socket IOControl
                socket.IOControl(IOControlCode.KeepAliveValues, SIO_KEEPALIVE_VALS, result);
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }

        public static bool HandleSocketError(Exception exc)
        {
            bool handled = false;

            SocketException socketExc = exc as SocketException;
            if (socketExc != null)
            {
                switch (socketExc.ErrorCode)
                {
                    case (int)WsaError.WSAEINTR:
                        LastError = string.Format("Socket call interrupted [code {0}].", socketExc.ErrorCode);
                        break;
                    case (int)WsaError.WSAEADDRINUSE:
                        LastError = string.Format("The address is already in use [code {0}].", socketExc.ErrorCode);
                        break;
                    case (int)WsaError.WSACONNABORTED:
                        LastError = string.Format("The connection was aborted [code {0}].", socketExc.ErrorCode);
                        break;
                    case (int)WsaError.WSAECONNRESET:
                        LastError = string.Format("Connection reset by peer [code {0}].", socketExc.ErrorCode);
                        break;
                    case (int)WsaError.WSAECONNREFUSED:
                        LastError = string.Format("The connection was refused by the remote host [code {0}].", socketExc.ErrorCode);
                        break;
                    case (int)WsaError.WSAEADDRNOTAVAIL:
                        LastError = string.Format("The requested address is not valid [code {0}].", socketExc.ErrorCode);
                        break;
                    default:
                        LastError = string.Format("Socket error [code {0}].", socketExc.ErrorCode);
                        break;
                }
                handled = true;
            }

            ObjectDisposedException disposedExc = exc as ObjectDisposedException;
            if (disposedExc != null)
            {
                LastError = "The socket has been closed.";
                handled = true;
            }

            if (LastError != string.Empty)
             Log.WriteLog(LastError);

            //Trace.Write(exc.Message);
            //Trace.Write(exc.StackTrace);

#if DEBUG
            Log.WriteLog(LastError);
            Log.WriteLog(exc.Message+exc.StackTrace);
#endif

            return handled;
        }
    }   // SockUtils
}
