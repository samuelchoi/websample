package learning.base.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SystemConfig {

    private static Properties properties;
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        properties = new Properties();
        FileInputStream fin = null;
        try {

            fin = new FileInputStream("d://config.properties");
            properties.load(fin);
            // logger.debug(config.get("key");
            System.out.print(properties.get("key"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) try {
                fin.close();
            } catch (IOException e) {
            }
        }

    }
}
