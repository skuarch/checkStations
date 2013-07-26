package common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import util.IOUtilities;

/**
 * this class contains some methods for check the station.
 *
 * @author skuarch
 */
public class StationChecker {

    //==========================================================================
    /**
     * create a instance.
     */
    public StationChecker() {
    } // end StationChecker

    //==========================================================================
    /**
     * this method checks a station using sockets.<br/> the main purpose is
     * connect with the server and to consume some bytes.<br/> on any error
     * return false.
     *
     * @param host String
     * @param port int
     * @param stopSeconds int
     * @return boolean
     */
    public boolean checkStationSocket(String host, int port, int stopSeconds, int maxBytes) throws UnknownHostException, IOException {

        if (host == null || host.length() < 1) {
            throw new NullPointerException("host is null or empty");
        }

        if (port <= 0) {
            throw new NullPointerException("port is invalid");
        }

        int bytes = 0;
        int bytesPlus = 0;
        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        String request = null;
        byte[] reqByte = null;
        boolean stationIsActive = false;
        long endTime = System.currentTimeMillis() + stopSeconds;

        try {

            //open the socket and output
            socket = new Socket(host, port);
            socket.setKeepAlive(false);
            outputStream = socket.getOutputStream();

            //configure the output
            stringBuilder.append("GET / HTTP 1.1\r\n");
            stringBuilder.append("User-Agent: Ambient\r\n");
            stringBuilder.append("icy-metadata:1\r\n");
            stringBuilder.append("\r\n");
            request = stringBuilder.toString();
            reqByte = request.getBytes("UTF-8");
            outputStream.write(reqByte); // send data to server

            //open the inputstream for read the data
            inputStream = socket.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);

            //consume some bytes
            while (((bytes = bufferedInputStream.read()) != -1)) {

                bytesPlus += bytes;

                if (bytesPlus >= maxBytes) {
                    stationIsActive = true;
                    break;
                }

                if (System.currentTimeMillis() == endTime) {
                    stationIsActive = false;
                    break;
                }

            }

        } catch (UnknownHostException uhe) {
            throw uhe;
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtilities.closeInputStream(bufferedInputStream);
            IOUtilities.closeInputStream(inputStream);
            IOUtilities.closeOutputStream(outputStream);
            IOUtilities.closeSocket(socket);
        }

        return stationIsActive;

    } // end checkStation

    //==========================================================================
    /**
     * this method use a
     * <code>URLConnection</code> for check the station.<br/>
     *
     * @param stringUrl String
     * @return boolean
     */
    public boolean checkStationUrl(String stringUrl, int stopSeconds, int maxBytes) throws MalformedURLException, IOException {

        if (stringUrl == null || stringUrl.length() < 1) {
            throw new NullPointerException("stringUrl is null or empty");
        }

        URL url = null;
        URLConnection urlc = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        int bytes = 0;
        int bytesPlus = 0;
        boolean stationIsActive = false;
        long endTime = System.currentTimeMillis() + stopSeconds;

        try {

            //set up the connection
            url = new URL(stringUrl);
            urlc = url.openConnection();
            urlc.setDoOutput(true);
            urlc.setDoInput(true);
            urlc.setConnectTimeout(stopSeconds);

            //open the input for save the data
            inputStream = urlc.getInputStream();

            //consume some bytes
            while ((bytes = inputStream.read()) != -1) {

                bytesPlus += bytes;

                if (bytesPlus >= maxBytes) {
                    stationIsActive = true;
                    break;
                }

                if (System.currentTimeMillis() == endTime) {
                    stationIsActive = false;
                    break;
                }

            }

        } catch (MalformedURLException murle) {
            throw murle;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            IOUtilities.closeInputStream(inputStream);
            IOUtilities.closeOutputStream(outputStream);
        }

        return stationIsActive;

    } // end checkStationUrl
    
} // end class
