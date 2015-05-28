package com.leopoldmarx.note.program;

import javafx.application.Application;

import com.leopoldmarx.note.view.ViewMain;

public class Program {
	
	private static Program instance;
	
	public static Program getInstance()
	{
		if (instance == null) instance = new Program();
		return instance;
	}
	
	private Program() {
		
		Application.launch(ViewMain.class, "");
	}
	/*
	public FileManager getManager() {
		return manager;
	}
	
	public ArrayList<NoteCardSet> getProjects() {
		return allProjects;
	}
	*/
}