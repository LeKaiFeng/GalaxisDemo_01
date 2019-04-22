using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Reflection;
using System.Net.Sockets;

namespace WCS.socket
{
    public class ChatSocket : IDisposable
    {
        public event EventHandler Sent;
        public event EventHandler Received;
        public event EventHandler Disconnected;

        #region private members
        private Socket socket;
        private IPAddress target = IPAddress.None;
        private ChatCommand command;                // a command to be sent
        private string metatype;                    // will contain the metadata type for reflection
        private object metadata;
        private ulong sentBytes;
        private ulong receivedBytes;
        #endregion

        #region properties
        public bool Connected
        {
            get
            {
                if (socket != null && socket.Connected) return true;
                else return false;
            }
        }

        public IPEndPoint RemoteEndPoint
        {
            get
            {
                if (socket != null && socket.Connected) return (IPEndPoint)socket.RemoteEndPoint;
                else return null;
            }
        }

        public IPEndPoint LocalEndPoint
        {
            get
            {
                if (socket != null && socket.Connected) return (IPEndPoint)socket.LocalEndPoint;
                else return null;
            }
        }

        public IPAddress Target
        {
            get { return target; }
            set { target = value; }
        }

        public ChatCommand Command
        {
            get { return command; }
            set { command = value; }
        }

        public string Metatype
        {
            get { return metatype; }
            set { metatype = value; }
        }

        public object Metadata
        {
            get { return metadata; }
            set { metadata = value; }
        }

        public ulong SentBytes
        {
            get { return sentBytes; }
        }

        public ulong ReceivedBytes
        {
            get { return receivedBytes; }
        }
        #endregion

        #region IDisposable Members
        protected virtual void Dispose(bool disposing)
        {
            if (disposing)
            {
                // dispose managed resources
                if (socket != null)
                {
                    if (socket.Connected) socket.Shutdown(SocketShutdown.Both);
                    socket.Close();
                    socket = null;
                }
            }
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);  // remove from finalization queue
        }
        #endregion

        #region constructors
        public ChatSocket(
            ref Socket socket,
            IPAddress target, ChatCommand command,
            string metatype, object metadata)
            : this(ref socket)
        {
            this.target = target;
            this.command = command;
            this.metatype = metatype;
            this.metadata = metadata;
        }

        public ChatSocket(ref Socket socket)
        {
            this.socket = socket;
        }
        #endregion

        #region copy constructor
        /// <summary>
        /// Deep clone of a ChatSocket object without the network stream cloned.
        /// It is used when a new ChatSocket object is passed on to methods, events that 
        /// do not need a network stream.
        /// </summary>
        public ChatSocket(ChatSocket cp)
        {
            this.target = cp.Target;
            this.command = cp.Command;
            this.metatype = cp.Metatype;
            this.metadata = cp.Metadata;
            this.sentBytes = cp.SentBytes;
            this.receivedBytes = cp.ReceivedBytes;
        }
        #endregion

        #region Send
        /// <summary>
        /// Write out this ChatSocket object to the networkStream asynchronously.
        /// </summary>
        public void Send()
        {
            if (socket == null)
                return;

            try
            {
                byte[] buffer = new byte[4];
                buffer = BitConverter.GetBytes((int)command);
                if (socket.Connected)
                {
                    socket.Send(Encoding.ASCII.GetBytes(metatype.ToString()));
                    if (WmsCommon.Instance().clients.Count(r => r.scocKet == socket) > 0)
                    {
                        WmsCommon.Instance().clients.Find(r => r.scocKet == socket).statusSocket = "1";
                    }

                }
               
            }
            catch (Exception exc)
            {
                WmsCommon.Instance().CloseSocket(this);
                if (SockUtils.HandleSocketError(exc))
                {
                    if (Disconnected != null)
                        Disconnected(this, EventArgs.Empty);
                }
                //else
                //{
                //    throw;
                //}
            }
        }

        /// <summary>
        /// End asynchronous send of command to the network, 
        /// and send out the rest of the packet.
        /// </summary>
        private void InternalSend(IAsyncResult ar)
        {
            try
            {
                if (socket == null)
                    return;
                if (socket.Connected)
                {
                    socket.Send(Encoding.ASCII.GetBytes(metatype.ToString()));
                }
                //int sent = socket.EndSend(ar);
                //if (sent > 0)
                //{
                //    sentBytes += (ulong)sent;

                //  //  InternalSendCommandTarget();
                //    InternalSendMetadataType();
                //   // InternalSendMetaBuffer();

                //    // fire Sent event only if all the Send routines 
                //    // completed succesfully
                //    if (Sent != null)
                //        Sent(this, EventArgs.Empty);
                //}
                //else
                //{
                //    Disconnect();
                //}
            }
            catch (Exception exc)
            {
                if (SockUtils.HandleSocketError(exc))
                {
                    if (Disconnected != null)
                        Disconnected(this, EventArgs.Empty);
                }
                else
                {
                    throw;
                }
            }
        }

        #region Send internals
        /// <summary>
        /// Send command target (ip address). The first 4 bytes contain the lentgh of the address.
        /// </summary>
        private void InternalSendCommandTarget()
        {
            byte[] buffer = new byte[4];
            byte[] ipBuffer = Encoding.ASCII.GetBytes(target.ToString());

            buffer = BitConverter.GetBytes((int)ipBuffer.Length);

            sentBytes += (ulong)socket.Send(buffer, 0, 4, SocketFlags.None);
            sentBytes += (ulong)socket.Send(ipBuffer, 0, ipBuffer.Length, SocketFlags.None);
        }

        /// <summary>
        /// Send metadata type (as a string). The first 4 bytes contain the length of the 
        /// metadata type.
        /// </summary>
        private void InternalSendMetadataType()
        {
            byte[] buffer = new byte[4];

            if (metatype != null)
            {
                if (socket.Connected)
                {
                    //byte[] typeBuffer = Encoding.ASCII.GetBytes(metatype.ToString());
                    //buffer = BitConverter.GetBytes((int)typeBuffer.Length);
                    //socket.Send(Encoding.ASCII.GetBytes(heartString));
                    //sentBytes += (ulong)socket.Send(buffer, 0, 4, SocketFlags.None);
                    //  sentBytes += (ulong)socket.Send(typeBuffer, 0, typeBuffer.Length, SocketFlags.None);
                    socket.Send(Encoding.ASCII.GetBytes(metatype.ToString()));
                }
            }
            else
            {
                buffer = BitConverter.GetBytes(0);
                sentBytes += (ulong)socket.Send(buffer, 0, 4, SocketFlags.None);
            }
        }

        /// <summary>
        /// Send metadata of type metatype. The first 4 bytes contain the length of 
        /// the metadata (which is an object that derives from BasePacket).
        /// </summary>
        private void InternalSendMetaBuffer()
        {
            byte[] buffer = new byte[4];

            if (metadata != null)
            {
                byte[] metaBuffer = null;
                Type metaType = metadata.GetType();
                MethodInfo mi = metaType.GetMethod("GetBytes");

                // get the byte representation of the metadata
                metaBuffer = (byte[])mi.Invoke(metadata, null);

                // length of metadata
                buffer = BitConverter.GetBytes((int)metaBuffer.Length);

                // write out the data to the network stream
                sentBytes += (ulong)socket.Send(buffer, 0, 4, SocketFlags.None);
                sentBytes += (ulong)socket.Send(metaBuffer, 0, metaBuffer.Length, SocketFlags.None);
            }
            else
            {
                // there isn't any metadata, so send a 0 as an array of bytes
                buffer = BitConverter.GetBytes((int)0);
                sentBytes += (ulong)socket.Send(buffer, 0, 4, SocketFlags.None);
            }
        }
        #endregion Send internals
        #endregion

        #region Receive
        /// <summary>
        /// Read a ChatSocket from the network stream asynchronously.
        /// </summary>
        public void Receive()
        {
            if (socket == null)
                return;

            try
            {
                if (socket.Connected)
                {
                    // stores the received data
                  //  byte[] result = new byte[1024];
                 //   int receiveNumber = socket.Receive(result);
                  //  Metatype = Encoding.ASCII.GetString(result, 0, receiveNumber);

                  byte[] buffer = new byte[4];
                   socket.BeginReceive(
                     buffer, 0, 4, SocketFlags.None,
                   new AsyncCallback(InternalReceive), buffer);
                }
                else
                {
                    WmsCommon.Instance().CloseSocket(this);
 
                }
            }
            catch (Exception exc)
            {
                WmsCommon.Instance().CloseSocket(this);
                if (SockUtils.HandleSocketError(exc))
                {
                    if (Disconnected != null)
                        Disconnected(this, EventArgs.Empty);
                }
                //else
                //{
                //    throw exc;
                //}
            }
        }

        /// <summary>
        /// End asynchronous receive of command from the network, 
        /// and continues to read the rest of the packet.
        /// </summary>
        private void InternalReceive(IAsyncResult ar)
        {
            try
            {
                if (socket == null)
                    return;
                if(socket.Connected)
                {
                int received = socket.EndReceive(ar);
                if (received > 0)
                {
                    byte[] buffer = (byte[])ar.AsyncState;


                    Metatype = Encoding.ASCII.GetString(buffer, 0, received);
                    byte[] result = new byte[1024];
                    int receiveNumber = socket.Receive(result);

                    Metatype = Metatype + Encoding.ASCII.GetString(result, 0, receiveNumber);


                    //receivedBytes += (ulong)received; 

                    //byte[] result = new byte[1024];
                    //result = (byte[])ar.AsyncState;
                    //int bufferVal = BitConverter.ToInt32(buffer, 0);
                    //Encoding.ASCII.GetString(result, 0, receiveNumber);
                    // check if it is a know command 

                    command = (ChatCommand)7;
                    //command = (ChatCommand)bufferVal;
                    //Trace.Write(string.Format("ChatSocket RECEIVE from {1} -> {0} {2}",
                    //    socket.LocalEndPoint.ToString(), socket.RemoteEndPoint.ToString(), command.ToString()));

                    ////  InternalReceiveCommandTarget();
                    //  InternalReceiveMetadataType();
                    // InternalReceiveMetaBuffer();

                    // fire the Received event only if all the Receive routines
                    // completed succesfully
                    if (Received != null)
                        Received(this, EventArgs.Empty);
                }
               
                    //else
                    //{
                    //    // it is an unknown command so close the socket
                    //    Disconnect();
                    //}
                }
                else // received == 0
                {
                    Disconnect();
                }

            }
            catch (Exception exc)
            {
                WmsCommon.Instance().CloseSocket(this);
             
                if (SockUtils.HandleSocketError(exc))
                {
                    if (Disconnected != null)
                        Disconnected(this, EventArgs.Empty);
                }
                else
                {
                  //  throw exc;
                }
            }
        }

        #region Receive internals
        /// <summary>
        /// Read command target from the network stream.
        /// </summary>
        private void InternalReceiveCommandTarget()
        {
            if (socket.Connected)
            {
                byte[] buffer = new byte[4];

                // Read command target
                receivedBytes += (ulong)socket.Receive(buffer, 0, 4, SocketFlags.None);
                int readSizeOfIp = 0;
                int sizeOfIp = BitConverter.ToInt32(buffer, 0);
                byte[] ipBuffer = new byte[sizeOfIp];

                readSizeOfIp = socket.Receive(ipBuffer, 0, sizeOfIp, SocketFlags.None);
                if (readSizeOfIp != sizeOfIp)
                    throw new Exception(string.Format(
                        "InternalReceiveCommandTarget target ip read {0} bytes instead of {1} bytes!",
                        readSizeOfIp, sizeOfIp));

                if (readSizeOfIp > 0)
                {
                    receivedBytes += (ulong)readSizeOfIp;
                    if (!IPAddress.TryParse(Encoding.ASCII.GetString(ipBuffer), out target))
                        target = IPAddress.Parse("127.0.0.1");
                }
            }
        }

        /// <summary>
        /// Read metadata type from the network stream.
        /// </summary>
        private void InternalReceiveMetadataType()
        {
            //byte[] buffer = new byte[4];

            //// read metatype
            //receivedBytes += (ulong)socket.Receive(buffer, 0, 4, SocketFlags.None);
             
            
                //byte[] result = new byte[1024];

            byte[] buffer = new byte[4];

            // read metatype
          receivedBytes += (ulong)socket.Receive(buffer, 0, 4, SocketFlags.None);
      //      int sizeOfType = BitConverter.ToInt32(buffer, 0);

            //if (sizeOfType != 0)
            //{
                byte[] typeBuffer = new byte[1024];
                int readSizeOfType = 0;

                readSizeOfType = socket.Receive(typeBuffer);
                //if (readSizeOfType != sizeOfType)
                //    throw new Exception("InternalReceiveMetadataType metatype not read!");

                receivedBytes += (ulong)readSizeOfType;
                metatype = Encoding.ASCII.GetString(typeBuffer);
         //   }


             //   byte[] typeBuffer = new byte[1024];
             //   int readSizeOfType = 0;

             //   readSizeOfType = socket.Receive(typeBuffer);
             //   //if (readSizeOfType != sizeOfType)
             //   //    throw new Exception("InternalReceiveMetadataType metatype not read!");

             ////   receivedBytes += (ulong)readSizeOfType;
             //   metatype = Encoding.ASCII.GetString(typeBuffer, 0, readSizeOfType);
               // metatype = Encoding.ASCII.GetString(typeBuffer);
            
        }

        /// <summary>
        /// Read metabuffer (the actual data).
        /// </summary>
        private void InternalReceiveMetaBuffer()
        {
            if (socket.Connected)
            {
                byte[] buffer = new byte[4];

                // Read metadata
                receivedBytes += (ulong)socket.Receive(buffer, 0, 4, SocketFlags.None);

                int sizeOfMeta = BitConverter.ToInt32(buffer, 0);
                int readSizeOfMeta = 0;

                if (sizeOfMeta != 0)
                {
                    byte[] metaBuffer = new byte[1024];

                    readSizeOfMeta = socket.Receive(metaBuffer, 0, sizeOfMeta, SocketFlags.None);
                    while (readSizeOfMeta < sizeOfMeta)
                    {
                        int i = socket.Receive(metaBuffer, readSizeOfMeta, sizeOfMeta - readSizeOfMeta, SocketFlags.None);
                        readSizeOfMeta += i;
                    }

                    receivedBytes += (ulong)readSizeOfMeta;

                    if (readSizeOfMeta != sizeOfMeta)
                    {
                        throw new Exception(
                            "InternalReceiveMetaBuffer metadata length: " + readSizeOfMeta.ToString() +
                            " instead of: " + sizeOfMeta.ToString());
                    }
                    else
                    {
                        Type typeToCreate = Type.GetType(metatype);
                        IBasePacket packet = (IBasePacket)Activator.CreateInstance(typeToCreate);

                       packet.Initialize(metaBuffer);
                        metadata = packet;
                    }
                }
            }
        }
        #endregion Receive internals
        #endregion

        #region Close
        public void Close()
        {
            if (socket != null)
            {
                if (socket.Connected) socket.Shutdown(SocketShutdown.Both);
                socket.Close();
                socket.Dispose();
            }
     
        }
        #endregion

        #region Disconnect
        private void Disconnect()
        {
            this.Close();
            if (Disconnected != null)
                Disconnected(this, EventArgs.Empty);
        }
        #endregion
    }
    interface IBasePacket
    {
        /// <summary>
        /// Initialize the class members using the byte array passed in as parameter.
        /// </summary>
        void Initialize(byte[] metadata);

        /// <summary>
        /// Return metadata (a byte array) constructed from the members of the class.
        /// </summary>
        byte[] GetBytes();
    } 
}
