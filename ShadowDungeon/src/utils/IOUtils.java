package utils;

import bagel.util.Point;
import java.io.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;


/**
 * A utility class that provides methods to read and write files.
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

    public static Point parseCoords(String coords) {
        String[] coordinates = coords.split(",");
        return new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }

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

    // split a string into an array of strings using the given regex
    public static String[] parseContents(String contents, String regex) {
        return contents.split(regex);
    }

    // retrieve coordinate from a property and converts it to a point
    public static Point getPointProperty(Properties gameProps, String x) {
        return IOUtils.parseCoords(gameProps.getProperty(x));
    }
}
