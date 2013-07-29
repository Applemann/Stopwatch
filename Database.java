

import java.sql.*;
 
public class Database {
    
    /*
    public static final String EXTRACT_SECOND = "EXTRACT(SECOND FROM @konec)-EXTRACT(SECOND FROM cas_startu) ";
    public static final String EXTRACT_MINUTE = "EXTRACT(MINUTE FROM @konec)-EXTRACT(MINUTE FROM cas_startu) ";
    public static final String EXTRACT_HOUR = "EXTRACT(HOUR FROM @konec)-EXTRACT(HOUR FROM cas_startu) ";
    public static final String EXTRACT_DAY = "EXTRACT(DAY FROM @konec)-EXTRACT(DAY FROM cas_startu)";
    
     
    public static final String EXTRACT_DATUM = 
        EXTRACT_SECOND +" AS sekunda, "+
        EXTRACT_MINUTE + "+("+EXTRACT_SECOND+")/60 AS minuta, "+
        EXTRACT_HOUR + "+("+EXTRACT_MINUTE+" + ("+EXTRACT_SECOND+")/60)/60 AS hodina, "+
        "DATEDIFF(@konec,cas_startu) AS den ";
    
    public static final String SELECT_RUNNING_TASKS = 
        "SET @konec=NOW(); "+
        "SELECT *, "+ EXTRACT_DATUM +
        "FROM Ukoly WHERE cas_konce IS NULL ORDER BY den, hodina, minuta, sekunda ASC;";
    
    public static final String SELECT_FINISHED_TASKS = 
        "SET @konec=cas_konce; "+
        "SELECT *, "+ EXTRACT_DATUM +
        "FROM Ukoly WHERE cas_konce IS NOT NULL ORDER BY den, hodina, minuta, sekunda ASC;";
    */
    
    public static final String SELECT_RUNNING_TASKS = 
        "SELECT *, EXTRACT(SECOND FROM NOW())-EXTRACT(SECOND FROM cas_startu) AS sekunda, "+
        "EXTRACT(MINUTE FROM NOW())-EXTRACT(MINUTE FROM cas_startu) +(EXTRACT(SECOND FROM NOW())-EXTRACT(SECOND FROM cas_startu) )/60 AS minuta, "+
        "EXTRACT(HOUR FROM NOW())-EXTRACT(HOUR FROM cas_startu) +(EXTRACT(MINUTE FROM NOW())-EXTRACT(MINUTE FROM cas_startu) + (EXTRACT(SECOND FROM NOW())-EXTRACT(SECOND FROM cas_startu) )/60)/60 AS hodina, "+
        "EXTRACT(DAY FROM NOW())-EXTRACT(DAY FROM cas_startu)+(EXTRACT(HOUR FROM NOW())-EXTRACT(HOUR FROM cas_startu) +(EXTRACT(MINUTE FROM NOW())-EXTRACT(MINUTE FROM cas_startu) + (EXTRACT(SECOND FROM NOW())-EXTRACT(SECOND FROM cas_startu) )/60)/60)/24 AS den "+
        "FROM Ukoly WHERE cas_konce IS NULL ORDER BY den, hodina, minuta, sekunda ASC;";
    
    public static final String SELECT_FINISHED_TASKS = 
        "SELECT *, EXTRACT(SECOND FROM cas_konce)-EXTRACT(SECOND FROM cas_startu) AS sekunda, "+
        "EXTRACT(MINUTE FROM cas_konce)-EXTRACT(MINUTE FROM cas_startu) +(EXTRACT(SECOND FROM cas_konce)-EXTRACT(SECOND FROM cas_startu) )/60 AS minuta, "+
        "EXTRACT(HOUR FROM cas_konce)-EXTRACT(HOUR FROM cas_startu) +(EXTRACT(MINUTE FROM cas_konce)-EXTRACT(MINUTE FROM cas_startu) + (EXTRACT(SECOND FROM cas_konce)-EXTRACT(SECOND FROM cas_startu) )/60)/60 AS hodina, "+
        "EXTRACT(DAY FROM cas_konce)-EXTRACT(DAY FROM cas_startu)+(EXTRACT(HOUR FROM cas_konce)-EXTRACT(HOUR FROM cas_startu) +(EXTRACT(MINUTE FROM cas_konce)-EXTRACT(MINUTE FROM cas_startu) + (EXTRACT(SECOND FROM cas_konce)-EXTRACT(SECOND FROM cas_startu) )/60)/60)/24 AS den "+
        "from Ukoly WHERE cas_konce IS NOT NULL ORDER BY den, hodina, minuta, sekunda ASC;";
    
    
        
    public static final String SELECT_SUBTASKS = 
        "SELECT podukol, datum " +
        "FROM Podukoly " +
        "where ukol='%s'"+
        "order by datum asc;";
        
   
    public static final String FINISH_TASK = "UPDATE Ukoly SET cas_konce=? WHERE jmeno=?;"; 
    public static final String CREATE_TASK = "INSERT INTO Ukoly(cas_startu, jmeno) VALUES (?, ?);";
    public static final String CREATE_SUBTASK = "INSERT INTO Podukoly(datum, ukol, podukol) VALUES (?, ?, ?);";
    public static final String SELECT_ALL_ABOUT_TASK = "SELECT * FROM Ukoly WHERE jmeno='%s';";
    
    private static final String jdbcDriver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/Stopky?useUnicode=true&characterEncoding=utf-8";
    private static final String name = "root";
    private static final String password = "password";
    
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    //String sql;
    
    public Database() {
    }
    public void connect() {
  
        try{
            // Register JDBC driver
            Class.forName(jdbcDriver);

            // Open a connection
            connection = DriverManager.getConnection(url, name, password);
            statement = connection.createStatement();
             
            
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    
    public ResultSet getResult(String sql){
        try{
            // Execute SQL query
            result = statement.executeQuery(sql);
            
            
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return result;
    }
    
    
    
    public void sendCommand(String sql, String date, String task, String subtask){
        try{
            // Execute SQL query
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,date);
            ps.setString(2,task);
            if (subtask != ""){
                ps.setString(3,subtask);
            }
            ps.executeUpdate();
            ps.close();
        
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }
    
    public void close(){
        try{
            // Clean-up environment
            if(result != null){ result.close(); }
            statement.close();
            connection.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }
} 
