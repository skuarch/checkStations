package common;


import java.util.Timer;
import java.util.TimerTask;
import org.jboss.logging.Logger;

/**
 *
 * @author skuarch
 */
public class Scheduler {
    
    private static final Logger logger = Logger.getLogger(Scheduler.class);
    
    //==========================================================================
    public Scheduler(){        
    } // end Scheduler
    
    //==========================================================================
    public void schedulerProcessor(long miliseconds) {
    
        if(miliseconds < 0){
            logger.error(new Exception("miliseconds is less than 0"));
            return;
        }
        
        TimerTask timerTask = null;
        Timer timer = new Timer();
        
        try {
            
            timerTask = new TimerTask() {

                @Override
                public void run() {
                    System.out.println("**************************************************************************************************");
                    new Thread(new Processor()).start();
                }
            };
            
            timer.schedule(timerTask, 0,miliseconds);            
            
        } catch (Exception e) {
            logger.error(e);
        }        
    
    } // end schedulerProcessor
    
} // end class
 