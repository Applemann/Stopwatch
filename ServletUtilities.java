
public class ServletUtilities {
  public static final String DOCTYPE =
    "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\">";
 
  public static String headWithTitle(String title) {
    return(DOCTYPE + "\n" +
           "<html>\n" +
           "<head>\n"+
           "<meta http-equiv=\"Content-Language\" content=\"cs\"> \n" +
           "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' /> \n" +
           "<title>" + title + "</title>\n" +
           "<style type='text/css'>\n"+
                "h1  {color: blue; font-style: italic;}\n"+
                "td {text-align: center;}\n"+
            "</style>\n"+
           "</head>\n"
           );
  }
  
  public static String timer(int day, int hour, int minute, int second) {
      String id = day+"" + hour+"" + minute+"" + second+"";
        return (
        "                                                           \n" +
        "<form name='zobraz'>                                       \n" +
        "   <input type='text' size='5' id='timer_"+id+"'>          \n" +
        "</form>                                                    \n" +
        "<script language='javascript' type='text/javascript'>      \n" +
        "   <!--                                                    \n" +
        "       var den_"+id+"="+ day +"                            \n" +
        "       var hodina_"+id+"="+ hour +"                        \n" +
        "       var minuta_"+id+"="+ minute +"                      \n" +
        "       var sekunda_"+id+"="+ second +"                     \n" +
        "                                                           \n" +
        "                                                           \n" +
        "       function tiktak_"+id+"(){                           \n" +
        "       sekunda_"+id+" += 1                                 \n" +
        "       if(sekunda_"+id+" >= 60){                           \n" +
        "           sekunda_"+id+"=0                                \n" +
        "           minuta_"+id+"+=1                                \n" +
        "       }                                                   \n" +
        "       if(minuta_"+id+" >= 60){                            \n" +
        "           minuta_"+id+"=0                                 \n" +
        "           hodina_"+id+"+=1                                \n" +
        "       }                                                   \n" +
        "       if(hodina_"+id+" >= 24){                            \n" +
        "           hodina_"+id+"=0                                 \n" +
        "           den_"+id+"+=1                                   \n" +
        "       }                                                   \n" +
        "       var timer = document.getElementById('timer_"+id+"');\n" +
        "       timer.value= den_"+id+"+'d '+hodina_"+id+"+'h '+minuta_"+id+"+'m '+sekunda_"+id+"+'s'       \n" +
        "                       \n" +       
        "       setTimeout('tiktak_"+id+"()',1000)                  \n" +
        "   }                                                       \n" +
        "   tiktak_"+id+"()                                         \n" +
        "   //-->                                                   \n" +
        "                                                           \n" +
        "</script>"
        
        );
    }
  
    
    
    
    
    
    
  
}
