/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdslagmelt.begin;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Shape;
import mdslagmelt.class_io.MSMFileIO;
import mdslagmelt.initialdata.InitialData;

/**
 *
 * @author dmitry
 */
public class begin {
    
    public static void main(String[] args) {
        
        MSMFileIO io = new MSMFileIO();
        InitialData ini = new InitialData();
        Path dir = Paths.get("MD_DAT/SIONA/0740/");
        try {  
            Files.createDirectories(dir);
        } catch (IOException ex) {
            Logger.getLogger(begin.class.getName()).log(Level.SEVERE, null, ex);
        }
        io.write(ini,dir+ File.separator+"point.json");
        InitialData fromFile = (InitialData)io.read("point.json");
        System.out.println(fromFile.MSD_x);
    }
    
}
