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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tao
 */
public class ServerThread implements Runnable{
    private ServerSocket server;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private Socket client;
    public ServerThread(ServerSocket server)
    {
        this.server = server;
    }

    @Override
    public void run() {
        while(!server.isClosed())
        {
            try {
                System.out.println("Wait for client to connect");
                
                client = server.accept();
                
                System.out.println("Someone is connected");
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }






    }
}
