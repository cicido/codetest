package com.meizu.algo.distribute;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MachineInfo {
    public static String getIP() throws UnknownHostException {
        InetAddress addr=null;
        String ip="";
        String address="";
        try{
            addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString(); //获得机器IP　　
            address = addr.getHostName().toString(); //获得机器名称
        }catch(Exception e){
            e.printStackTrace();
        }
        return ip + "|" + address;
    }
    public static void main(String[] args) throws UnknownHostException {
        System.out.println(getIP());
    }
}