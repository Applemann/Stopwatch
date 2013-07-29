import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import java.sql.*;


public class Timer {    
    private String start;
    private String end;
    private String timer;
    private String type;
        
    public Timer(String start, String end){
        this.start = start;
        this.end = end;
        //timer = start;
        splitAndCreate();
    }
    
    private int subtract(String num1, String num2){
        int result = Integer.parseInt(num1) - Integer.parseInt(num2);
        return result;
    }
    
    
    private void createTimer(String[] sd, String[] st, String[] ed, String[] et){
        int day = subtract(ed[2], sd[2]);
        int hour = subtract(et[0], st[0]);
        int minute = subtract(et[1], st[1]);
        
        int second = subtract( et[2].substring(0, 2), st[2].substring(0, 2) );
        
        if (second<0){ minute-=1; second+=60; }
        if (minute<0){ hour-=1; minute+=60; }
        if (hour<0){ day-=1; hour+=24; }
        
        
        
        if(type=="stopped"){
            timer = day+"d "+hour+"h "+minute+"m "+second+"s ";
        }else{
            timer = ServletUtilities.timer(day, hour, minute, second);
        }
    }
    
    
    private void splitAndCreate(){
        if (end == null){
            String[] startDate = start.split(" ");
            String[] sd = startDate[0].split("-");
            String[] st = startDate[1].split(":");
            String[] ed = getNowDate().split("-");
            String[] et = getNowTime().split(":");
            type = "running";
            createTimer(sd, st, ed, et);
            
        }else{
            String[] startDate = start.split(" ");
            String[] endDate = end.split(" ");
            
            String[] sd = startDate[0].split("-");
            String[] st = startDate[1].split(":");
            String[] ed = endDate[0].split("-");
            String[] et = endDate[1].split(":");
            type = "stopped";
            createTimer(sd, st, ed, et);
        }
    }
    
    public static String getNowDate(){
        java.util.Date dateNow = new java.util.Date();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateNow)+"";
        return date;
    }
    
    public static String getNowTime(){
        java.util.Date dateNow = new java.util.Date();
        String time = new SimpleDateFormat("HH:mm:ss").format(dateNow)+"";
        return time;
    }
    
    public String get(){
        return timer;
    }
    
    @Override
    public String toString(){
        return timer;
    }
}
