/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdslagmelt.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


/**
 *
 * @author dmitry
 */
public class main {

    public static double dx,dy,r,potential,pe,f,dt2,ke;    
    public static int Lx = 32, Ly = 9;
    public static double dt = 0.5;
     
    //число шагов по времени между снимками
    public static int nsnap = 100;
    public static double vmax = 10;
    public static int pos_row;
    public static int pos_col;
    public static double vxsum;
    public static double vysum;
    public static int N = 12;
    public static void initial(double[] x, double[] y, double[] vx, double[] vy,double[] ax, double[] ay) {
        Random rand = new Random();
        pos_col = Lx/8;
        pos_row = Ly/3;
        dt2 = Math.pow(dt, 2);
        int k = 0;
        for(int i = 0; i < pos_row;i++) {
            for(int j = 0; j < pos_col; j++){
                x[k] = pos_col*(j + 1 - 0.5);
                y[k] = pos_row*(i + 1 - 0.5);
                vx[k] = vmax*(2*rand.nextDouble() - 1);
                vy[k] = vmax*(2*rand.nextDouble() - 1);
                k++;
            }
        }
        
        for(int i = 0; i < N; i++) {
            vxsum += vx[i];
            vysum += vy[i];  
        }
            vxsum /= N;
            vysum /= N;
        for(int i = 0; i < N; i++) {
            vx[i] -= vxsum;
            vy[i] -= vysum;  
        }
        accel(x,y,ax,ay);
    }
    
    public static JsonObject verlet() {
        double[] x   = new double[N];
        double[] vx  = new double[N];
        double[] y   = new double[N];
        double[] vy  = new double[N];
        double[] ax  = new double[N];
        double[] ay  = new double[N];
        double xnew,ynew;
        JsonObject jsonObj;
        initial(x,y,vx,vy,ax,ay);
        for(int i = 0; i < N; i++){
            xnew = x[i] + vx[i]*dt + 0.5*ax[i]*dt2;
            ynew = y[i] + vy[i]*dt + 0.5*ay[i]*dt2;
            //при необхдимости возвращаем частицу в центральную ячейку
            x[i] = periodic_x(xnew);
            y[i] = periodic_y(ynew);
        }
        for(int i = 0; i < N; i++){
            //частично изменяем скорость, используя старое ускорение
            vx[i] += 0.5*ax[i]*dt;
            vy[i] += 0.5*ay[i]*dt;
        }
        //вычисляем новое ускорение
        accel(x,y,ax,ay);
        for(int i = 0; i < N; i++){
            //частично изменяем скорость, используя новое ускорение
            vx[i] = vx[i] + 0.5*ax[i]*dt;
            vy[i] = vy[i] + 0.5*ay[i]*dt;
            ke = ke + 0.5*(Math.pow(vx[i], 2) + Math.pow(vy[i], 2));
        }

        jsonObj = writeToJsonObject(x,y,vx,vx,ax,ay,ke,pe,f);
        return jsonObj;
    }

    public static double periodic_x(double xnew) {
        double xtemp = xnew;
        if (xtemp < 0)  { xnew += Lx; } 
        if (xtemp > Lx) { xnew -= Lx; }
        return xnew;
    }
    
    public static double periodic_y(double ynew){
        double ytemp = ynew;
        if (ytemp < 0)  { ynew += Ly; }
        if (ytemp > Lx) { ynew -= Ly; }
        return ynew;
    }
    
    public static void accel(double[] x, double[] y, double[] ax, double[] ay) {
 
         //вычисляем полную силу, действующую на частицу i
        for(int i = 0; i < N-1; i++) {
            for(int j= i+1; j < N; j++) {
                dx = x[i] - x[j];
                dy = y[i] - y[j];
                separation();
                r = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                force();
                ax[i] = ax[i] + f*dx;
                ay[i] = ay[i] + f*dy;
                ax[j] = ax[j] - f*dx;
                ay[j] = ay[j] - f*dy;
                pe += potential; 
            }
        }
    }
    
    public static void separation() {
        if (Math.abs(dx) > 0.5*Lx){
         dx = dx - Math.signum(dx)*Lx;   
        }
        if (Math.abs(dy) > 0.5*Ly){
         dy = dy - Math.signum(dy)*Ly;   
        }
    }
    public static void force() {
       double ri = 1/r;
       double ri3 = Math.pow(ri, 3);
       double ri6 = Math.pow(ri3, 2);
       double g = 24*ri*ri6*(2*ri6-1);
       f = g/r;
       potential = 4*ri6*(ri6-1);
    }
    
    public static JsonObject writeToJsonObject(double[]x, double[]y, double[]vx, double[]vy, double[]ax, double[]ay,
     double ke, double pe, double f){  
        Gson gson = new GsonBuilder().setPrettyPrinting().create();//для читабельности 
        String jsonX = gson.toJson(x);
        String jsonY = gson.toJson(y);
        String jsonVX = gson.toJson(vx);
        String jsonVY = gson.toJson(vy);
        String jsonAX = gson.toJson(ax);
        String jsonAY = gson.toJson(ay);
        String jsonke = gson.toJson(ke);
        String jsonpe = gson.toJson(pe);
        String jsonf = gson.toJson(f);
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("X", jsonX);
        jsonObj.addProperty("Y", jsonY);
        jsonObj.addProperty("VX", jsonVX);
        jsonObj.addProperty("VY", jsonVY);
        jsonObj.addProperty("AX", jsonAX);
        jsonObj.addProperty("AY", jsonAY);
        jsonObj.addProperty("ke", jsonke);
        jsonObj.addProperty("pe", jsonpe);
        jsonObj.addProperty("f", jsonf);
        return jsonObj;
    }

    public static void writeToJsonFile(JsonArray arrayOfPoints) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();//для читабельности 
        String json = gson.toJson(arrayOfPoints);
        try(FileWriter writer = new FileWriter("points.json", true)) {
           writer.write(json);
           writer.flush();   
        }
        catch(IOException ex){    
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args) {     
        //массив  с результатами после вызова всех verlet()
        JsonArray arrayOfPoints= new JsonArray();
        //JsonObject jsonObj = new JsonObject();//содержит результаты работы verlet() 
        for(int i=0;i<nsnap;i++){
           arrayOfPoints.add(verlet());
        }
        writeToJsonFile(arrayOfPoints);

    }

   
    
    
}
   
