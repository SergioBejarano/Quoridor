package domain;    

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

/**
 * It is a mechanism for logging events such as exceptions and errors.
 */
public class Log{
    
    public static String name="Quoripoob";
    
    /**
     * Records an exception in the log file.
     * 
     * @param e The exception to be recorded.
     */
    public static void record(Exception e){
        try{
            Logger logger=Logger.getLogger(name);
            logger.setUseParentHandlers(false);
            FileHandler file=new FileHandler(name+".log",true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE,e.toString(),e);
            file.close();
        }catch (Exception oe){
            oe.printStackTrace();
            //System.exit(0);
        }
    }
}
    
