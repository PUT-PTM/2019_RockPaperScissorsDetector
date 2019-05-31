package pl.a517435708.serverrockpaperscisoris;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class Server {
    MainActivity activity;
    ServerSocket serverSocket;
    String message = "";


    int player1Set = -1;
    int player2Set = -1;

    static final int socketServerPORT = 1337;
    String msgReply;

    public Server(MainActivity activity) {
        this.activity = activity;
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
    }

    public int getPort() {
        return socketServerPORT;
    }

    public void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread
    {

        int count = 0;

        @Override
        public void run() {
            try {
                // create ServerSocket using specified port
                serverSocket = new ServerSocket(socketServerPORT);

                while (true) {
                    // block the call until connection is created and return
                    // Socket object
                    Socket socket = serverSocket.accept();
                    count++;


                    message += "Welcome Player nr. " + count + "\n";

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.msg.append(message);
                        }
                    });

                    SocketServerListenThread socketServerListenThread =
                            new SocketServerListenThread(socket, count);
                    socketServerListenThread.start();

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class SocketServerListenThread extends Thread {

        private Socket hostThreadSocket;
        int dataSize = 0;
        int cnt;

        private String byteToString(byte[] data, int size)
        {
            int number;
            StringBuilder gener = new StringBuilder();

            for(int i=0; i<size; i++)
            {
                while(data[i]>0)
                {
                    number = (data[i]&0xff)%10;
                    gener.append((char)(number+48));
                    data[i] /= 10;
                }
            }

            gener.reverse();

            return gener.toString();
        }

        SocketServerListenThread(Socket socket, int c) {
            hostThreadSocket = socket;
            cnt = c;
        }

        @Override
        public void run() {
            OutputStream outputStream;
            InputStream inputStream;
            msgReply = "";



            while (!hostThreadSocket.isClosed())
            {
                try {
                    inputStream = hostThreadSocket.getInputStream();


                    dataSize = inputStream.available();
                    if (dataSize > 0)
                    {
                        byte[] data = new byte[dataSize];
                        inputStream.read(data,0,dataSize);

                        if(cnt == 1)
                            player1Set = data[0];
                        if(cnt == 2)
                            player2Set = data[0];

                        msgReply = "Data from player number ["+ cnt + "] : " + byteToString(data,dataSize);



                        activity.runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                activity.msg.append(msgReply + '\n');
                            }
                        });

                        if(player2Set != -1 && player1Set != -1)
                        {

                            activity.runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    activity.msg.append("Calculate result:" + '\n');
                                }
                            });

                            player1Set -= 16;
                            player2Set -= 16;

                            if(player1Set == 7)
                            {
                                switch (player2Set)
                                {

                                    case 1:
                                    {
                                        message = "Gracz 1 Wygrywa!";
                                    }break;

                                    case 7:
                                    {
                                        message = "Remis!";
                                    }break;

                                    case 0:
                                    {
                                        message = "Gracz 2 Wygrywa!";
                                    }break;
                                }
                            }
                            if(player1Set == 0)
                            {
                                switch (player2Set)
                                {

                                    case 1:
                                    {
                                        message = "Gracz 2 Wygrywa!";
                                    }break;

                                    case 7:
                                    {
                                        message = "Gracz 1 Wygrywa!";
                                    }break;

                                    case 0:
                                    {
                                        message = "Remis!";
                                    }break;
                                }
                            }

                            if(player1Set == 1)
                            {
                                switch (player2Set)
                                {

                                    case 1:
                                    {
                                        message = "Remis!";
                                    }break;

                                    case 7:
                                    {
                                        message = "Gracz 2 Wygrywa!";
                                    }break;

                                    case 0:
                                    {
                                        message = "Gracz 1 Wygrywa!";
                                    }break;
                                }
                            }

                            player2Set = -1;
                            player1Set = -1;


                            activity.runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    activity.msg.append(message + '\n');
                                }
                            });

                        }

                    }



                }catch (Exception ex)
                {

                }
            }


            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.msg.setText("AAAAAAND DEAD!!!");
                }
            });




        }

    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements())
                {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Server running at : "
                                + inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}