package org.example;

import org.example.server.MyTomcatServer;
import org.example.ThreadPool.CustomThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        try {
            Class.forName("org.example.ThreadPool.CustomThreadPool");
            Class.forName("org.example.URL");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        ServerSocket serverSocket = new ServerSocket(8080);


        while (true) {
            Socket clientSocket = serverSocket.accept();

            CustomThreadPool.submit(new MyTomcatServer(clientSocket));

        }
    }

}