package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Utilities for Properties.
 *
 * @author skuarch
 */
public class PropertieUtilities {

    //==========================================================================
    /**
     * this class is an utilities doesn't need a constructor.
     */
    private PropertieUtilities() {
    } // end PropertieUtilities

    //==========================================================================
    /**
     * you need specify a path in order to load a file with FileinputStream.
     *
     * @param path String
     * @return Properties
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Properties getProperties(String path) throws FileNotFoundException, IOException {

        if (path == null || path.length() < 1) {
            throw new NullPointerException("path is null");
        }

        FileInputStream fis = null;
        Properties properties = null;

        try {

            fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);

        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            IOUtilities.closeInputStream(fis);
            fis = null;
        }

        return properties;

    } // end getProperties

    //==========================================================================
    /**
     * return String with the value of the key.
     *
     * @param path String .properties file path
     * @param key String
     * @return String with the value of the key
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String getStringPropertie(String path, String key) throws FileNotFoundException, IOException {

        if (path == null || path.length() < 1) {
            throw new NullPointerException("path is null or empty");
        }

        if (key == null || key.length() < 1) {
            throw new NullPointerException("key is null or empty");
        }

        Properties p = getProperties(path);
        return p.getProperty(key);

    } // end getStringPropertie

    //==========================================================================
    /**
     * return an int with the value of the key.
     *
     * @param path String .properties file path
     * @param key String
     * @return an int with the value of the key
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static int getIntPropertie(String path, String key) throws FileNotFoundException, IOException {

        if (path == null || path.length() < 1) {
            throw new NullPointerException("path is null or empty");
        }

        if (key == null || key.length() < 1) {
            throw new NullPointerException("key is null or empty");
        }

        int value;

        value = Integer.parseInt(getStringPropertie(path, key));

        return value;

    } // end getIntPropertie
    
} // end class