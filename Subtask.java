
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import java.sql.*;

public class Subtask {
    
    private String name;
    private String date;
    private String parentName;
    private Timer timer;
    
    public Subtask(){
        this("", "");
    }
    
    public Subtask(String name, String parentName){
        this.name = name;
        this.parentName = parentName;
        create(name);
    }
    
    private void update(String sql){
        String date = Timer.getNowDate() +" "+ Timer.getNowTime();
        
        Database dtb = new Database();
        dtb.connect();
        dtb.sendCommand(sql, date, parentName, name);
        dtb.close();
    }
    
    public void create(String name){
        if (name != null || name != ""){
            update(Database.CREATE_SUBTASK);
        }
    }
    
    public String getName(){ 
        return name; 
    }
    public Timer getTimer(){ 
        return timer; 
    }
    
    public void setTimer(String startTask){ 
        timer = new Timer(startTask, date);
    }
    
    
    public void setparentName(String name){ 
        this.parentName = parentName; 
    }
    
    public void setName(String name){ 
        this.name = name; 
    }
    public void setDate(String date){ 
        this.date = date;  
    }
    
    public String getRow(){
        return "<tr> <td>"+ timer +"</td> <td>"+ name +"<td></tr>";
    }
    
    @Override
    public String toString(){
        return name;
    }
}
