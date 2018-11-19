package DTO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CommonTestDataDTO {

	public static Properties propertiesTestData= new Properties();;

	public static Properties propertiesConfig= new Properties();;
	
	
		public static void initializeProperties() {

				try {
					propertiesConfig.load(new FileReader(System.getProperty("user.dir") + "/Resources/config.properties"));
					propertiesTestData.load(new FileReader(System.getProperty("user.dir") + "/Resources/TestData.properties"));

				} catch (FileNotFoundException e) {
					throw new RuntimeException("Properties file not found ");
				} catch (IOException e) {
					throw new RuntimeException("Unable to load properties file: ");
				}

			} 
		 

		public  static String getConfigPropertyValue(String Key) {
			String value = propertiesConfig.getProperty(Key);
			return value;
		}

		public static String getTestDataPropertyValue(String Key) {
			String value = propertiesTestData.getProperty(Key);
			return value;
		}
}
