/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdslagmelt.class_io;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Formatter;
import java.util.Scanner;
import mdslagmelt.interface_io.MSMInterfaceObjectIO;
/**
 *
 * @author dmitry
 */
public class MSMFileIO implements MSMInterfaceObjectIO{
    
    @Override
    public void open(String fileName, Scanner scn) {
        try {
            scn = new Scanner(new File(fileName));
        } catch (Exception e) {e.printStackTrace();}
    }
        @Override
    public void write(Object object, String fileName) {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            BufferedOutputStream bout = new BufferedOutputStream(out);
            ObjectOutputStream dout = new ObjectOutputStream(bout);
            dout.writeObject(object);
            dout.flush();
        } catch (IOException e){
            e.printStackTrace();
        }    
    }

    @Override
    public Object read(String fileName) {
            try (FileInputStream out = new FileInputStream(fileName)) {
            BufferedInputStream bout = new BufferedInputStream(out);
            ObjectInputStream dout = new ObjectInputStream(bout);
            return dout.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void close() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public void scnReadFile(String [][] m, Scanner scn) {
        while (scn.hasNext()) {
            for (int row=0;row<m.length;row++){
                for (int col=0;col<m[row].length;col++){
                    m[row][col]=scn.next();
                }
            }
        }
    }
    
    public void formatWriteFile(String fileName, String string, String param1, String param2) {
	try {
                Formatter x = new Formatter(fileName);
		x.format(string, param1, param2);
		x.close();
            } catch(Exception e) {System.out.print("File not found");}
    }
    
}
