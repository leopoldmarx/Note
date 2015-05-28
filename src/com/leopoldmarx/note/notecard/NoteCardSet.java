package com.leopoldmarx.note.notecard;

import java.io.Serializable;
import java.util.ArrayList;

public class NoteCardSet implements Serializable {
	private static final long serialVersionUID = 3351436566647812252L;
	
	private String name;
	private ArrayList<NoteCard> stack;
	private long dayCreated;
	private int pos;
	
	public NoteCardSet() {
		name = "";
		stack = new ArrayList<>();
		dayCreated = System.currentTimeMillis();
		pos = 0;
	}
	
	public NoteCardSet(ArrayList<NoteCard> stack, String name) {
		this.stack = stack;
		this.name  =  name;
		dayCreated = System.currentTimeMillis();
		pos = 0;
	}
	
	public ArrayList<NoteCard> getStack() {
		return stack;
	}
	
	public void setStack(ArrayList<NoteCard> stack) {
		this.stack = stack;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getDayCreated() {
		return dayCreated;
	}
	
	public void setDayCreated(long dayCreated) {
		this.dayCreated = dayCreated;
	}
	
	public int getPos() {
		return pos;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
}