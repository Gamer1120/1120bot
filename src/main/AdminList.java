package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class AdminList {
	private LinkedList<String> adminList;

	public AdminList() {
		try {
			this.adminList = loadList();
			adminList.add("gamer1120");
		} catch (IOException e) {
			adminList = new LinkedList<String>();
			adminList.add("gamer1120");
		}
	}

	public void add(String name) throws AdminAlreadyExistsException,
			AdminAlreadyExistsException {
		System.out.println("Trying to add " + name);
		if (!adminList.contains(name)) {
			adminList.add(name);
			this.saveList();
		} else {
			throw new AdminAlreadyExistsException();
		}
	}

	public void remove(String name) throws NoSuchAdminException,
			NoSuchAdminException {
		if (adminList.contains(name)) {
			adminList.remove(name);
			this.saveList();
		} else {
			throw new NoSuchAdminException();
		}
	}

	private void saveList() {
		System.out.println("Saving list...");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("adminlist.txt", "UTF-8");
		} catch (IOException e) {
			System.out.println("Couldn't write to adminlist.txt");
		}
		for (int i = 0; i < adminList.size(); i++) {
			writer.println(adminList.get(i));
		}
		writer.close();
	}

	private LinkedList<String> loadList() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("adminlist.txt"));
		LinkedList retList = new LinkedList<String>();
		String line = null;
		while ((line = reader.readLine()) != null) {
		    retList.add(line);
		}
		reader.close();
		return retList;
	}

	public String getList() {
		String retString = "";
		for (int i = 0; i < adminList.size(); i++) {
			retString += adminList.get(i);
			retString += " ";
		}
		return retString;
	}
}
