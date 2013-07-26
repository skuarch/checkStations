package util;

import java.net.URI;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 * class for help 
 * @author skuarch
 */
public class StationUtilities {

    private static final Logger logger = Logger.getLogger(StationUtilities.class);

    //==========================================================================
    public static String getIPAddress(String stationUrl) {

        if (stationUrl == null || stationUrl.length() < 1 || stationUrl.equals("")) {
            logger.error("stationurl is null or empty");
            return null;
        }

        String[] parts = null;
        String ip = null;

        try {

            parts = stationUrl.split(":");
            ip = parts[1];
            ip = ip.replace("/", "");
            ip = ip.replace("/", "");

        } catch (Exception e) {
            logger.error(e);
        } finally {
            stationUrl = null;
            parts = null;
        }

        return ip;
    }

    //==========================================================================
    public static int getPort(String stringUrl) {

        if (stringUrl == null || stringUrl.length() < 1 || stringUrl.equals("")) {
            logger.error("stationurl is null or empty");
            return 0;
        }
        
        int port = 0;
        String[] parts = null;

        try {
            
            parts = stringUrl.split(":");
            parts[2] = parts[2].replace("/", "");
            port = Integer.parseInt(parts[2]);

        } catch (Exception e) {            
            return -1;
        } finally {
            stringUrl = null;            
        }

        return port;
    } // end getPort
    
    //==========================================================================
    public static int getUrlPort(String stringUrl) {

        if (stringUrl == null || stringUrl.length() < 1 || stringUrl.equals("")) {
            logger.error("stationurl is null or empty");
            return 0;
        }

        URL url = null;
        int port = 0;        

        try {

            url = new URL(stringUrl);
            port = url.getPort();

        } catch (Exception e) {
            logger.error(e);
        } finally {
            stringUrl = null;            
        }

        return port;
    } // end getPort

    //==========================================================================
    public static boolean urlHasPath(String stringUrl) {

        URL url = null;
        URI uri = null;
        String path = null;
        boolean flag = false;

        try {

            url = new URL(stringUrl);
            uri = url.toURI();

            path = uri.getPath();
            
            if (path != null && path.length() > 0) {
                flag = true;
            }

        } catch (Exception e) {
            logger.error(e);
        } finally {
            url = null;
            uri = null;
            path = null;
            stringUrl = null;
        }

        return flag;

    } // end urlHasUri
} // end class