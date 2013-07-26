package common;

import java.io.File;
import model.beans.Stations;
import model.dao.DAO;
//import common.report.Report;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import util.PropertieUtilities;
import util.StationUtilities;

/**
 * this is the backbone of the program.
 *
 * @author skuarch
 */
public class Processor implements Runnable {

    private static final Logger logger = Logger.getLogger(Processor.class);

    //==========================================================================
    /**
     * create a instance.
     */
    public Processor() {
    } // end Processors    

    //==========================================================================
    /**
     * start the thread an check one by one station.<br/> 1.- get all stations
     * and validate if there some stations<br/> 2.- depends of the type of
     * streaming the stations is checked<br/> 3.- create a text plain
     * report<br/> this method needs of
     * <code>StationChecker</code>.
     */
    @Override
    public void run() {

        ArrayList<Stations> stations = null;
        Stations station = null;        
        String ip = null;
        int port = 0;
        String stringUrl = null;
        String name = null;
        long id = 0;
        boolean isActive = false;
        String tmp = null;
        int fails = 0;
        int actives = 0;
        int stopSeconds;
        int maxBytes;
        File file;
        String path;

        try {


            path = PropertieUtilities.getStringPropertie("configuration/configuration.properties", "path.save.report");
            file = new File(path + "report " + new Date() + ".txt");

            //get all stations
            logger.info("getting all stations");
            stations = (ArrayList<Stations>) new DAO().getAll("Stations");

            if (stations == null || stations.size() < 1) {
                logger.info("doesn't exists stations");
                return;
            }

            //check one by one station
            logger.info("checking stations");
            for (int i = 0; i < stations.size(); i++) {

                station = stations.get(i);
                id = stations.get(i).getId();
                stringUrl = stations.get(i).getUrl();
                name = stations.get(i).getName();
                ip = StationUtilities.getIPAddress(stringUrl);
                port = StationUtilities.getUrlPort(stringUrl);
                stopSeconds = PropertieUtilities.getIntPropertie("configuration/configuration.properties", "seconds.stop") * 1000;
                maxBytes = PropertieUtilities.getIntPropertie("configuration/configuration.properties", "seconds.stop");

                try {

                    //some urls doesn't have port 
                    if (port == -1) {//-----------------------------------------

                        isActive = new StationChecker().checkStationUrl(stringUrl, stopSeconds, maxBytes);
                        setActiveStation(station, isActive);
                        updateStation(station);
                        tmp = id + "\t" + name + "\t connected " + isActive + "\t[url]\n";                        

                    } else if (StationUtilities.urlHasPath(stringUrl)) {//-------

                        isActive = new StationChecker().checkStationUrl(stringUrl, stopSeconds, maxBytes);
                        setActiveStation(station, isActive);
                        updateStation(station);
                        tmp = id + "\t" + name + "\t connected " + isActive + "\t[url]\n";                        

                    } else {//--------------------------------------------------

                        isActive = new StationChecker().checkStationSocket(ip, port, stopSeconds, maxBytes);
                        setActiveStation(station, isActive);
                        updateStation(station);
                        tmp = id + "\t" + name + "\t connected " + isActive + "\t[port]\n";                        

                    }
                    
                    FileUtils.writeStringToFile(file, tmp, true);
                    actives++;

                } catch (Exception e) {
                    fails++;
                    tmp = e.getMessage() + "  " + id + " " + name + "\n";                    
                    //logger.error(id + "\t" + name + "\t\t\t\t\t\t\t" + e.getMessage());
                }

                System.out.print(tmp);

            } // end for  
            
            FileUtils.writeStringToFile(file, "fails " + fails + " actives " + actives, true);            

        } catch (Exception e) {
            logger.error(e);
        }

    } // end run

    //==========================================================================
    private void setActiveStation(Stations station, boolean isActive) {

        if (station == null) {
            logger.error("station is null");
            return;
        }

        try {

            if (isActive) {
                station.setActive(1);
            } else {
                station.setActive(0);
            }

        } catch (Exception e) {
            logger.error(e);
        }

    } // end setActiveStation

    //==========================================================================
    private void updateStation(Stations station) {

        if (station == null) {
            logger.error("station is null");
            return;
        }

        try {
            new DAO().update(station);
        } catch (Exception e) {
            logger.error(e);
        }
    } // end updateStation
    
} // end class 
