package util;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author skuarch
 */
public class IOUtilities {

    //==========================================================================
    /**
     * this class doesn't need a constructor it's a utilities.
     */
    private IOUtilities() {
    } // end IOUtilities

    //==========================================================================
    /**
     * close the socket.
     *
     * @param socket
     */
    public static void closeSocket(Socket socket) {

        try {

            if (socket != null) {
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    } // end closeSocket

    //==========================================================================
    /**
     * close the inputStream.
     *
     * @param inputStream
     */
    public static void closeInputStream(InputStream inputStream) {

        try {

            if (inputStream != null) {
                inputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    } // end closeInputStream

    //==========================================================================
    /**
     * close the outputStream.
     *
     * @param outputStream
     */
    public static void closeOutputStream(OutputStream outputStream) {

        try {

            if (outputStream != null) {
                outputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    } // end closeOutputStream

    //==========================================================================
    /**
     * close the fileWriter.
     *
     * @param fileWriter
     */
    public static void closeFileWriter(FileWriter fileWriter) {

        try {

            if (fileWriter != null) {
                fileWriter.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    } // end closeFileWriter

    //==========================================================================
    /**
     * close the printWriter.
     *
     * @param fileWriter
     */
    public static void closePrintWriter(PrintWriter printWriter) {

        try {

            if (printWriter != null) {
                printWriter.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    } // end closeFileWriter    

    //==========================================================================
    /**
     * close ServerSocket.
     */
    public static void closeServerSocket(ServerSocket serverSocket) {

        try {

            if (serverSocket != null) {
                serverSocket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    } //closeServerSocket

    //==========================================================================
    /**
     * disconnect URL.
     *
     * @param httpURLConnection
     */
    public static void disconnectURL(HttpURLConnection httpURLConnection) {

        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }

    } // end disconnectURL
    
} // end class 