package common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import model.beans.Stations;
import model.dao.DAO;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import util.PropertieUtilities;
import util.StationUtilities;

/**
 *
 * @author skuarch
 */
public class Processor2 implements Runnable {

    private static final Logger logger = Logger.getLogger(Processor2.class);
    private File file = null;
    private String path = null;
    private int actives = 0;
    private int inactives = 0;

    //==========================================================================
    /**
     * create a instance.
     */
    public Processor2() {
    } // end 

    //==========================================================================
    @Override
    public void run() {

        ArrayList<Stations> stations = null;

        try {

            path = PropertieUtilities.getStringPropertie("configuration/configuration.properties", "path.save.report");
            file = new File(path + "report " + new Date() + ".txt");


            stations = getStations();

            if (stations == null || stations.size() < 1) {
                logger.info("doesn't exists stations");
                return;
            }

            for (Iterator<Stations> it = stations.iterator(); it.hasNext();) {
                Stations s = it.next();
                processStation(s);
            }

            FileUtils.writeStringToFile(file, "inactives " + inactives + " actives " + actives, true);

        } catch (Exception e) {
            logger.error(e);
        }

    } // end run

    //==========================================================================
    private void processStation(Stations station) throws IOException {

        int port;
        boolean isActive;
        String tmp = null;
        String name;
        long id;
        String stringUrl;
        int stopSeconds;
        int maxBytes;
        String ip;

        try {

            id = station.getId();
            stringUrl = station.getUrl();
            name = station.getName();
            ip = StationUtilities.getIPAddress(stringUrl);
            port = StationUtilities.getUrlPort(stringUrl);
            stopSeconds = PropertieUtilities.getIntPropertie("configuration/configuration.properties", "seconds.stop") * 1000;
            maxBytes = PropertieUtilities.getIntPropertie("configuration/configuration.properties", "max.bytes");


            //some urls doesn't have port 
            if (port == -1) {//-----------------------------------------

                isActive = new StationChecker().checkStationUrl(stringUrl, stopSeconds, maxBytes);
                setActiveStation(station, isActive);
                updateStation(station);
                tmp = id + "\t" + name + "\t connected " + isActive + "\t[url]\n";
                actives++;

            } else if (StationUtilities.urlHasPath(stringUrl)) {//-------

                isActive = new StationChecker().checkStationUrl(stringUrl, stopSeconds, maxBytes);
                setActiveStation(station, isActive);
                updateStation(station);
                tmp = id + "\t" + name + "\t connected " + isActive + "\t[url]\n";
                actives++;

            } else {//--------------------------------------------------

                isActive = new StationChecker().checkStationSocket(ip, port, stopSeconds, maxBytes);
                setActiveStation(station, isActive);
                updateStation(station);
                tmp = id + "\t" + name + "\t connected " + isActive + "\t[port]\n";
                actives++;

            }

            System.out.println(tmp);

            FileUtils.writeStringToFile(file, tmp, true);

        } catch (Exception e) {
            FileUtils.writeStringToFile(file, tmp + " " + e.getMessage(), true);
            logger.error(e);
            setActiveStation(station, false);
            updateStation(station);
            inactives++;
        }



    } // end proccessStation

    //==========================================================================
    private ArrayList<Stations> getStations() throws Exception {

        ArrayList<Stations> stations = null;
        stations = (ArrayList<Stations>) new DAO().getAll("Stations");

        return stations;

    } // end getStations

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