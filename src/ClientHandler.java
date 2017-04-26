

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapplication2.Server;
import javaapplication2.UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tao
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    String msg;
    public static int choice;
    
    public ClientHandler(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run() {
            try {
                setupStream();
                linking();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        


    }

    private void setupStream() throws IOException {
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(o);
        oo.writeObject(UI.voteNum);
        dos.write(o.toByteArray());
        oo.writeObject(UI.voteText);
        dos.write(o.toByteArray());
        o.close();
        oo.close();
        
        
        dos.flush();
        
    }

    private void linking() {
        Thread getInfo = new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isClosed())
                {
                    try {
                        msg = dis.readUTF();
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    choice = Integer.parseInt(msg);
                }
            }
        }
        );
        getInfo.start();
    }
}
