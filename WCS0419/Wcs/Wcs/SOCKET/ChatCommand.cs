using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WCS.socket
{
    public enum ChatCommand : int
    {
        // Request commands
        RequestLogin,
        RequestUsers,

        // Server commands
        UserAuthenticated,
        UserConnected,      // sent by the server when a user connects
        UserDisconnected,   // sent by the server when a user disconnects
        ServerDisconnected, // sent by the server when it is stopped

        PublicMessage,      // broadcast message
        PrivateMessage,     // private message

        Error               // error message
    } 
}
