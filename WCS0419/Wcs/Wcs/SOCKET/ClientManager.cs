using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.Net;

namespace WCS.socket
{
    public delegate void TimedEventHandler(object sender, TimedEventArgs ea);
    public class ClientManager
    {
        public event TimedEventHandler Disconnected;
        public event TimedEventHandler CommandReceived;

        #region private members
        private Socket socket;
        private IPEndPoint endPoint;
        private ChatSocket chatSocket;
        private string username;
        //private UserData userdata;
        private bool authenticated = false;
        #endregion

        #region properties
        public IPEndPoint IPEndPoint
        {
            get { return endPoint; }
        }

        public IPAddress IPAddress
        {
            get { return endPoint.Address; }
        }

        public int Port
        {
            get { return endPoint.Port; }
        }

        public ChatSocket ChatSocket
        {
            get { return chatSocket; }
        }

        //public ClientKey ClientKey
        //{
        //    get
        //    {
        //        SocketAddress clientKey = new SocketAddress(endPoint.Address, endPoint.Port);
        //        return clientKey;
        //    }
        //}

        public string UserName
        {
            get { return username; }
            set { username = value; }
        }

        //public UserData UserData
        //{
        //    get { return userdata; }
        //    set { userdata = value; }
        //}

        /// <summary>
        /// Set to true if the client was authenticated in the system.
        /// </summary>
        public bool Authenticated
        {
            get { return authenticated; }
            set { authenticated = value; }
        }
        #endregion properties

        #region constructor
        /// <summary>
        /// A ClientManager is responsible for managing a connection from a client.
        /// For each client connected there will be one separate ClientManager object.
        /// The socket used by the ClientManager uses asynchronous call to Receive and 
        /// send data.
        /// </summary>
        public ClientManager(ref Socket clientSocket)
        {
            if (clientSocket == null)
                throw new Exception("Client socket is not initialized!");

            // the endpoint stores information from the client side
            socket = clientSocket;
            endPoint = (IPEndPoint)socket.RemoteEndPoint;

            // create the ChatSocket
            chatSocket = new ChatSocket(ref socket);
            chatSocket.Received += new EventHandler(OnChatSocketReceived);
            chatSocket.Disconnected += new EventHandler(OnChatSocketDisconnected);

            chatSocket.Receive();
        }

        void OnChatSocketReceived(object sender, EventArgs e)
        {
            if (chatSocket == null || !chatSocket.Connected)
                return;

            if (CommandReceived != null)
                CommandReceived(this, new TimedEventArgs());

            chatSocket.Receive();
        }

        void OnChatSocketDisconnected(object sender, EventArgs e)
        {
            // socket disconnected
            // save the user data before firing the Disconnected event
            //if (userdata != null)
            //{
            //    userdata.LastIp = this.IPAddress;
            //    userdata.LastSeen = DateTime.Now;
            //    UserManager.Instance.Users[username] = userdata;
            //    UserManager.Instance.Save();
            //}

            if (Disconnected != null)
            {
                // only fire the Disconnected event if the user was authenticated
                if (this.authenticated)
                    Disconnected(this, new TimedEventArgs());
            }

            Disconnect();
        }
        #endregion constructor

        #region public Disconnect
        /// <summary>
        /// Disconnect gracefully from the server. 
        /// </summary>
        public void Disconnect()
        {
            if (this.socket != null)
            {
                if (socket.Connected) socket.Shutdown(SocketShutdown.Both);
                socket.Close();
            }
        }
        #endregion
    } 
}
