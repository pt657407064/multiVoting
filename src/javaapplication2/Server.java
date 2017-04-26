/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Tao
 */
public class Server {
    private boolean existedVote; //Check too see if there is a vote going on
    public static ServerSocket server;
    public Server() throws IOException
    {
        initServer();
    }

    private void initServer() throws IOException {
       server = new ServerSocket(10000);
       System.out.println("Server starts");
       ServerThread st = new ServerThread(server);
       Thread t = new Thread(st);
       t.start();
    }


}
