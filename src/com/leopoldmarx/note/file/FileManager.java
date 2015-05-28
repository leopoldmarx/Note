package com.leopoldmarx.note.file;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.leopoldmarx.note.notecard.NoteCardSet;

public class FileManager {

	private Path location;
	
	public FileManager() {
		
		generateLocation();
	}
	
	public void generateLocation() {
		System.out.println(System.getProperty("user.dir"));
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {

			location = Paths.get(System.getenv("APPDATA") + "\\Note");
		}

		else if (os.contains("mac")) {

			location = Paths.get(System.getProperty("user.home") + "/Library/Application Support/Note");
		}

		else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {

			location = Paths.get(System.getProperty("user.home"));
		}

		else if (os.contains("sunos")) {

			//location for solaris
		}
	}
	
	public void save(NoteCardSet noteCardSet) {
		
		if(Files.exists(location)) {

			try {

				XMLEncoder e = new XMLEncoder (new BufferedOutputStream(new FileOutputStream(location.toString() + "\\" + noteCardSet.getName() + ".XML")));
				e.writeObject(noteCardSet);
				e.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else {
			
			File file = location.toFile();
			file.mkdir();
			
			save(noteCardSet);
		}
	}
	
	public ArrayList<NoteCardSet> read() {
		
		if(Files.exists(location)) {
			
			File folder = location.toFile();
			File[] listOfFiles = folder.listFiles();
			
			ArrayList<NoteCardSet> noteCardSet = new ArrayList<>();
			try {

				for (File listOfFile : listOfFiles) {

					XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(listOfFile.toString())));
					noteCardSet.add((NoteCardSet) d.readObject());
					d.close();
				}
			}
			
			catch(Exception e) {
				
				e.printStackTrace();
			}
		
			return noteCardSet;
		}

		else {
			
			File file = location.toFile();
			file.mkdir();

			return new ArrayList<>();
		}
	}
	
	public void delete(NoteCardSet noteCardSet) {
		
		Path temp = Paths.get(location.toString() + "\\" + noteCardSet.getName() + ".xml");
		try {
			
			Files.delete(temp);
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}