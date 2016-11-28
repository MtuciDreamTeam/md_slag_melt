/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdslagmelt.class_io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mdslagmelt.interface_io.MSMInterfaceObjectIO;

/**
 *
 * @author dmitry
 */
public class MSMSocketIO implements MSMInterfaceObjectIO, Runnable{
    
    static private Socket connection;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;
    
    @Override
    public void open(String fileName, Scanner scn) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Object read(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void write(Object obj, String fileName) {
        try {
            output.flush();
            output.writeObject(obj);
        } catch (IOException ex) {
            Logger.getLogger(MSMSocketIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  

    @Override
    public void close() throws Exception {
        close();
    }

    @Override
    public void run() {
        try {
            connection = new Socket(InetAddress.getByName("127.0.0.1"),5678);
        } catch (IOException ex) {
            Logger.getLogger(MSMSocketIO.class.getName()).log(Level.SEVERE, null, ex);
        }
            while(true){
            try {
                output = new ObjectOutputStream(connection.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(MSMSocketIO.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                input = new ObjectInputStream(connection.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(MSMSocketIO.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }

}  
