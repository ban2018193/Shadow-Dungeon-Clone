package utils;

import bagel.util.Point;
import java.io.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;


/**
 * A utility class that provides methods to read and write files
 */
public class IOUtils {

    /***
     * Read a properties file and return a Properties object
     * @param configFile: the path to the properties file
     * @return Properties object
     */
    public static Properties readPropertiesFile(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return appProps;
    }


    /**
     * Converts a string representation of coordinates into a Point object
     *
     * @param coords a string in the format "x,y"
     * @return a Point object with the parsed x and y coordinates
     */
    public static Point parseCoords(String coords) {
        String[] coordinates = coords.split(",");
        return new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }


    /**
     * Converts a semicolon-separated list of coordinate strings into an array of Point objects
     * For example: "1,2;3,4" becomes an array with points (1,2) and (3,4).\
     *
     * @param coords the string containing multiple coordinate pairs
     * @return an array of Point objects
     */
    public static Point[] parseMultipleCoords(String coords) {
        if (coords.equals("0")) {
            return new Point[0];
        }

        String[] coordPairs = parseContents(coords, ";");
        List<Point> points = new ArrayList<>();

        for (String pair : coordPairs) {
            if (!pair.trim().isEmpty()) {
                points.add(parseCoords(pair.trim()));
            }
        }

        return points.toArray(new Point[0]);
    }


    /**
     * Splits a string into an array of strings using the given delimiter
     *
     * @param contents the string to split
     * @param regex the delimiter or regex to use for splitting
     * @return an array of strings resulting from the split
     */
    public static String[] parseContents(String contents, String regex) {
        return contents.split(regex);
    }


    /**
     * Retrieves a property value from a Properties object and converts it to a Point
     *
     * @param gameProps the Properties object containing the key-value pairs
     * @param x the property key whose value represents coordinates
     * @return a Point object corresponding to the coordinate string value of the property
     */
    public static Point getPointProperty(Properties gameProps, String x) {
        return IOUtils.parseCoords(gameProps.getProperty(x));
    }

}
