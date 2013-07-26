package checkstations;

import common.Processor2;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author skuarch
 */
public class Main {
    
    private static final Logger logger = Logger.getLogger(Main.class);
    
    //==========================================================================
    /**
     * create a instance.
     */
    public Main(){
        PropertyConfigurator.configure("configuration/log4j.properties");
    } // end Main
    
    //==========================================================================
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new Main().startProgram();
            
        
    } // end main
    
    //==========================================================================
    /**
     * start the programs.
     */
    private void startProgram(){
    
        logger.info("** program start **");
        
        //new Scheduler().schedulerProcessor(7200000);
        //new Scheduler().schedulerProcessor(3600000);
        //new Scheduler().schedulerProcessor(1500000);
        
        new Thread(new Processor2()).start();
        
    
    } // end startProgram
    
} // end class
