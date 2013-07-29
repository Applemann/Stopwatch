
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import java.sql.*;

public class Stopwatch extends HttpServlet {
    
    PrintWriter out;
    String title = "Stopky";
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        out = response.getWriter();
        
        catchAction(request);
        
        Table nowRunning = new Table(Table.Type.RUNNING_TASKS);
        Table finished = new Table(Table.Type.FINISHED_TASKS);
        
        out.println(ServletUtilities.headWithTitle(title) +
                "<body>\n" +
                "<h1 align=center>" + title + "</h1>\n" +
                "<form  action='stopwatch' method='POST'>" +
                "Nový úkol: <input type='text' name='new_task'> " +
                "<input type='submit' value='Start'>" +
                "</form>" +
                "<br><br>"+
                nowRunning.toString()+"\n"+
                "<br><br><br><br>"+
                finished.toString()+"\n"+
                "</body></html>"
            );
        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            doGet(request, response);
    }
    
    
    private void catchAction(HttpServletRequest request){
        Enumeration paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            
            if(paramValues.length != 0){
                runAction(paramName, paramValues[0]);
            }
        }
    }
    
    private void runAction(String paramName, String name){
        Task task = new Task(name);
            
        switch(paramName){
            case "end_task":
                task.finish(); break;
            case "new_task":
                task.create(); break;
        }
    }
    
    
}
        
    

