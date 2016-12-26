/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sriramhariharan.cyfallsapp2016;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User1
 */
public class ClssPkg implements Serializable{
    int[] abs = new int[8];
    String absString = "";
    String name;
    String lunch;
    ArrayList<Course> classes = new ArrayList<Course>();

    public ClssPkg(String n){
        name = n;
    }

    public Course get(String n){
        for(Course c : classes){
            if((c.coursenum+" "+c.name).equals(n))return c;
        }
        return null;
    }

    public void addAssign(String n,Assignment a){
        for(Course c : classes){
            if(c.name.equals(n))c.addAssign(a);
        }
    }

    public void addType(String n,Type a){
        for(Course c : classes){
            if(c.name.equals(n))c.addType(a);
        }
    }

    public void addAbsences(String a){
        absString = a;
        String[] arr = a.split(" ");
        for(int i=0;i<arr.length;i++){
            abs[i] = Integer.parseInt(arr[i].split("-")[1]);
        }
    }

    public int[] getAbs() {
        return abs;
    }

    public String getAbsString() {
        return absString;
    }

    public String toString(){
        String ret = "{"+name+"}";
        ret += classes.size();
        for(Course c : classes){
            ret+=c.toString();
        }
        ret += "{"+absString+"}"+"{"+lunch+"}";
        return ret;
    }

    public static ClssPkg parse(String str){
        Scanner s = new Scanner(str);
        s.useDelimiter("\\}\\{|\\}|\\{");
        ClssPkg clss = new ClssPkg(s.next());
        int iter1 = s.nextInt();
        for(int i=0;i<iter1;i++){
            Course c = new Course(s.next(),s.next(),s.next(),s.next(),s.next(),s.nextInt(),s.next(),s.next(),s.next(),s.next(),s.next(),s.next(),s.next(),s.next(),s.next(),s.next());
            int iter2 = s.nextInt();
            for(int j=0;j<iter2;j++){
                Assignment a = new Assignment(s.next(),s.next(),s.next(),s.next(),s.next());
                c.addAssign(a);
            }
            iter2 = s.nextInt();
            for(int j=0;j<iter2;j++){
                Type a = new Type(s.next(),s.nextDouble(),s.nextDouble(),s.nextDouble());
                c.addType(a);
            }
            clss.classes.add(c);
        }
        clss.addAbsences(s.next());
        clss.lunch = s.next();
        Log.e("hello",clss.lunch);

        return clss;
    }

    public static ClssPkg getFromServer(String user, String pass){
        String s = "";
        try {
            Log.e("TEST","STARTING");
            Socket clientSocket = new Socket("99.8.234.29", 6789);
            Log.e("Test","Successfull");
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(user+" "+pass+'\n');
            Log.e("TEST","wroteBytes");
            String rec = inFromServer.readLine();
            Log.e("TEST","readLine");
            s = rec;
            clientSocket.close();
        } catch (IOException ex) {

        }
        return parse(s);
    }


}