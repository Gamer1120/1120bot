package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class CommandList {
	private HashMap<String, String> commandMap;

	public CommandList() {
		try {
			this.commandMap = loadMap();
		} catch (IOException e) {
			commandMap = new HashMap<String, String>();
		}
	}

	public void add(String command, String text)
			throws CommandAlreadyExistsException {
		if (!commandMap.containsKey(command)) {
			commandMap.put(command, text);
			this.saveMap();
		} else {
			throw new CommandAlreadyExistsException();
		}
	}

	public void edit(String command, String text) throws NoSuchCommandException {
		if (commandMap.containsKey(command)) {
			commandMap.put(command, text);
			this.saveMap();
		} else {
			throw new NoSuchCommandException();
		}
	}

	public void remove(String command) throws NoSuchCommandException {
		if (commandMap.containsKey(command)) {
			commandMap.remove(command);
			this.saveMap();
		} else {
			throw new NoSuchCommandException();
		}
	}

	public String getCommand(String command) throws NoSuchCommandException {
		if (commandMap.containsKey(command)) {
			return commandMap.get(command);
		} else {
			throw new NoSuchCommandException();
		}
	}

	private void saveMap() {
		Properties prop = new Properties();
		prop.putAll(commandMap);
		try {
			prop.store(new FileOutputStream("commands.properties"), null);
		} catch (IOException e) {
			//FIXME
		}
	}

	private HashMap<String, String> loadMap() throws IOException {
		HashMap<String, String> retMap = new HashMap<String, String>();
		Properties properties = new Properties();
		properties.load(new FileInputStream("commands.properties"));

		for (String key : properties.stringPropertyNames()) {
			retMap.put(key, properties.get(key).toString());
		}

		return retMap;
	}

	public String getList() {
		String retString = "";
		for (String command : commandMap.keySet()) {
			retString += command;
			retString += " ";
		}
		return retString;
	}
}
