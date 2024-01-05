package net.casinovoyage.server.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvUtils {

    public static String getProperty(String key){
        Properties prop = new Properties();
        InputStream input;
        try {
            input = new FileInputStream(".env");
            prop.load(input);
        } catch (IOException e) {
            return null;
        }
        return prop.getProperty(key);
    }
}
