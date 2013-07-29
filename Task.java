import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import java.sql.*;

public class Task extends HttpServlet{
    
    private String name;
    private String start;
    private String end;
    private Timer timer;
    private Subtask[] subtasks;
    //private String testString;
    
    public Task(){
        this("");
    }
    
    public Task(String name){
        this.name = name;
        subtasks = new Subtask[0];
    }
    
    private void update(String sql){
        String date = Timer.getNowDate() +" "+ Timer.getNowTime();
        
        Database dtb = new Database();
        dtb.connect();
        dtb.sendCommand(sql, date, name, "");
        
        dtb.close();
    }
    
    private static Subtask[] append(Subtask[] array, Subtask subtask){
        Subtask[] newArray = new Subtask[array.length+1];
        for(int i=0; i<array.length; i++){
            newArray[i] = array[i];
        }
        newArray[array.length] = subtask;
        return newArray;
    }
    
    
    private void createListSubtasks(ResultSet result){
        try{
            
            while(result.next()){
                
                Subtask subtask = new Subtask();
                subtask.setName( result.getString("podukol") );
                subtask.setDate( result.getString("datum") );
                subtask.setTimer(start);
                subtasks = append(subtasks, subtask);
                
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }
    
    private void loadInfo(ResultSet result){
        
        try{
            result.next();
            start = result.getString("cas_startu");
            end = result.getString("cas_konce");
            
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }
    
    private void sendRequestForInfo(){
        Database dtb = new Database();
        dtb.connect();
        
        String sql = Database.SELECT_ALL_ABOUT_TASK;
        sql = String.format(sql, name);
        
        ResultSet result = dtb.getResult(sql);
        loadInfo(result);
        dtb.close();
    }
    
    private void loadSubtasks(){
        Database dtb = new Database();
        dtb.connect();
        
        String sql = Database.SELECT_SUBTASKS;
        sql = String.format(sql, name);
        
        ResultSet result = dtb.getResult(sql);
        createListSubtasks(result);
        
        dtb.close();
    }
    
    
    
    public void finish(){
        if (name != null || name != ""){
            update(Database.FINISH_TASK);
        }
    }
    
    public void create(){
        if (name != null || name != ""){
            update(Database.CREATE_TASK);
        }
    }
    
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        name = request.getParameter("name_task");
        String nameSubtask = request.getParameter("new_subtask");
        
        if (request.getParameter("new_subtask") != "null"){
            Subtask subtask = new Subtask(nameSubtask, name);
        }
        
        if (name != "null"){
            this.name = name;
        }
        sendRequestForInfo();
        loadSubtasks();
        
        printPage(out);
        
        subtasks = new Subtask[0];
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            doGet(request, response);
    }
    
    private void printPage(PrintWriter out){
        out.println(
            ServletUtilities.headWithTitle(name) +
            "<body>\n" +
            "<br><br><body>\n" +
            "<h2> <center> Úkol: "+ name +" </center> </h2>\n"
            );
        if (end == null){
            out.println(
                "<form  action='task' method='POST'>" +
                "<input style='visibility: hidden' type='text' name='name_task' value='"+name+"'>" +
                "<br> Nový podúkol: <input type='text' name='new_subtask'> " +
                "<input type='submit' value='LAP'>" +
                "</form>" );
        }
        out.println(
            "<br><br>"+
            "<center>\n"+
            "<table border=1 width=1000px>\n"+
            "<tr> <th> Čas zachycení </th> <th> Název podúkolu</th> </tr>\n"
        );
        
        for (int i = 0; i < subtasks.length; i++)
        {
            out.println(subtasks[i].getRow());
        }
        out.println("</table></center>\n</body></html>");
    }
    
    
    
    public String getName(){ 
        return name; 
    }
    public Timer getTimer(){ 
        return timer; 
    }
    
    public void setTimer(){ 
        timer = new Timer(start, end);
    }
    
    public void setName(String name){ 
        this.name = name; 
    }
    
    public void setStart(String start){ 
        this.start = start; 
    }
    
    public void setEnd(String end){ 
        this.end = end; 
    }
    
    @Override
    public String toString(){
        return name;
    }
}


