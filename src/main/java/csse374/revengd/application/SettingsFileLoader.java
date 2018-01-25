package csse374.revengd.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.management.RuntimeErrorException;

public class SettingsFileLoader {
	
	public void loadSettings(Map<String, String> configMap) {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		rootPath = "";
		 
		String defaultConfigPath = rootPath + "default.properties";
		Properties defaultProps = new Properties();
		InputStream fin;
		try {
			fin = new FileInputStream(defaultConfigPath);
			defaultProps.load(fin);
			fin.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
		String userPath = null;
		if (configMap.containsKey("settings")) {
			userPath = configMap.get("settings");
		}
		if (null != userPath) {
			String appConfigPath = rootPath + userPath;
			Properties appProps = new Properties(defaultProps);
			
			try {
				fin = new FileInputStream(appConfigPath);
				appProps.load(fin);
				fin.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			this.loadFromProp(appProps, configMap);
		} else {
			this.loadFromProp(defaultProps, configMap);
		}
	}
	
	private void loadFromProp(Properties prop, Map<String, String> configMap) {
		prop.stringPropertyNames().forEach(name -> {
			if (!configMap.containsKey(name)) {
				configMap.put(name, prop.getProperty(name));
			}
		});
	}
	
}
