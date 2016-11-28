/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdslagmelt.interface_io;

import java.util.Scanner;

/**
 *
 * @author dmitry
 */
public interface MSMInterfaceObjectIO {
    public void open(String fileName, Scanner scn);
    public Object read(String fileName);
    public void write(Object object, String fileName);
    public void close() throws Exception;
}