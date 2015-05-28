package com.leopoldmarx.note.notecard;

import java.io.Serializable;

public class NoteCard implements Serializable {
	private static final long serialVersionUID = -2256996058406148797L;
	
	private String   front;
	private String    back;
	private boolean edited;
	
	public NoteCard() {
		front = "";
		back  = "";
		edited = true;
	}
	
	public NoteCard(String front, String back) {
		this.front  = front;
		this.back   =  back;
		this.edited = true;
	}
	
	public String getFront() {
		return front;
	}
	
	public void setFront(String front) {
		this.front = front;
	}
	
	public String getBack() {
		return back;
	}
	
	public void setBack(String back) {
		this.back = back;
	}
	
	public boolean getEdited() {
		return edited;
	}
	
	public void setEdited(boolean edited) {
		this.edited = edited;
	}
}