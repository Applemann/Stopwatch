import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import java.sql.*;

public class Table {
    private String table;
    private Type type;
    private String sql;
    private String title;
    private String[] column = new String[5];
    //private int testInt;
    //private String testString;
    
    Task[] tasks;
    public enum Type {RUNNING_TASKS, FINISHED_TASKS;}
    
    
    
    public Table(Type type){
        column[0] = "<th>Délka trvání</th>";
        column[1] = "<th>Jméno úkolu</th>";
        
        if (type == Type.RUNNING_TASKS){
            this.type = type;
            sql = Database.SELECT_RUNNING_TASKS;
            title = "Právě probíhající úkoly:";
            column[2] = "<th colspan=3>Akce</th>";
        }
        if (type == Type.FINISHED_TASKS){
            this.type = type;
            sql = Database.SELECT_FINISHED_TASKS;
            title = "Již dokončené úkoly:";
            column[2] = "<th>Akce</th>";
        }
        tasks = new Task[0];
        sendRequest();
        table = createTable();
    }
    
    private void sendRequest(){
        Database dtb = new Database();
        dtb.connect();
        ResultSet result = dtb.getResult(sql);
        createListTasks(result);
        dtb.close();
    }
    
        
    public static Task[] append(Task[] array, Task task){
        Task[] newArray = new Task[array.length+1];
        for(int i=0; i<array.length; i++){
            newArray[i] = array[i];
        }
        newArray[array.length] = task;
        return newArray;
    }
    
    private void createListTasks(ResultSet result){
        
        try{
            while(result.next()){
                
                Task task = new Task();
                task.setName( result.getString("jmeno") );
                task.setStart( result.getString("cas_startu") );
                task.setEnd( result.getString("cas_konce") );
                task.setTimer();
                tasks = append(tasks, task);
                
            }
         }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }
    
    private String headTable(){
        return(
            "<h2> "+ title +" </h2>\n"+
            "<center>\n"+
            "<table border=1 width=1000px>\n"+
            "<tr> "+ column[0] + column[1] + column[2]+" </tr>\n"
            );
    }
    
    private String contentTable(Timer time, String nameTask){
        if(type == Type.RUNNING_TASKS){
            return("<tr> <td>"+ time +"</td> <td>"+ nameTask +
            "<td><a href='task?name_task="+ nameTask +"' target='_blank' >Detaily úkolu</a> </td> "+
            "<td><a href='stopwatch?end_task="+ nameTask +"'>Ukončit</a> </td> "
            );
        }
        if(type == Type.FINISHED_TASKS){
            return ("<tr> <td>"+ time +"</td> <td>"+ nameTask +
            "<td><a href='task?name_task="+ nameTask +"' target='_blank' >Detaily úkolu</a> </td> "
            );
        }
        
        return "";
    }
    
    public String createTable(){
        StringBuffer text = new StringBuffer();
        text.append( headTable() );
        
        for(int i=0; i<tasks.length; i++){
            String row = contentTable(tasks[i].getTimer(), tasks[i].getName());
            text.append( row );
        }
        
        text.append("</table></center>\n");
        
        String table = text.toString();
        return table;
    }
    
    
    
    
    @Override
    public String toString(){
        return table;
    }
}
