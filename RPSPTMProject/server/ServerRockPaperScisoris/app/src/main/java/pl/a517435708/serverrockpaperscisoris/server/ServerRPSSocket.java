package pl.a517435708.serverrockpaperscisoris.server;

import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRPSSocket
{
    private ServerSocket socket;

    public ServerRPSSocket(@Nullable String ip)
    {
        try
        {
            if(ip != null && !ip.isEmpty())
            {
                this.socket = new ServerSocket(1234,1, InetAddress.getByName(ip));
            }else
            {
                this.socket = new ServerSocket(1234,1,InetAddress.getLocalHost());
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public String listen()
    {
        String data = null;

        try
        {
            Socket client = this.socket.accept();

            String clientAddress = client.getInetAddress().getHostAddress();

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            if((data = in.readLine()) != null)
            {
                return data;
            }

        }catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " ERROR ! ");
            return "Error : ";
        }

        return "No Message";
    }

}
