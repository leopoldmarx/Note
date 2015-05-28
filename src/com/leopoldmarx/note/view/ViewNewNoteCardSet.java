package com.leopoldmarx.note.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.leopoldmarx.note.file.FileManager;
import com.leopoldmarx.note.notecard.NoteCard;
import com.leopoldmarx.note.notecard.NoteCardSet;

import java.util.Objects;

public class ViewNewNoteCardSet {
	
	private FileManager manager;
	private Stage window;
	private BorderPane borderPane;

	private NoteCardSet note;
	private boolean willReturn;
	
	public NoteCardSet display() {
		
		note = new NoteCardSet();
		note.setPos(-1);
		
		window = new Stage();
		borderPane = new BorderPane();
		manager = new FileManager();
		
		window.setTitle("Note - " + note.getName());
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMaxWidth(650);
		window.setMinWidth(650);
		window.setMaxHeight(400);
		window.setMinHeight(400);
		
		TextArea textAreaLeft  = new TextArea();
		BorderPane borderPaneLeft = new BorderPane();
		Button previousButton = new Button("←");
		
		TextArea textAreaRight = new TextArea();
		BorderPane borderPaneRight = new BorderPane();
		Button nextButton = new Button("→");
		
		HBox hBox1 = new HBox();
		HBox hBox2 = new HBox();
		VBox vBox = new VBox();
		Button saveButton = new Button("Save");
		Button newNoteCardButton = new Button("New Note Card");
		Label nameLabel = new Label("Name:");
		TextField nameTextField = new TextField();
		
		//Left
		textAreaLeft.setPromptText("This is the front of the note card. \n"
								 + "The front will be displayed first.");
		textAreaLeft.setMaxWidth(290);
		textAreaLeft.setMinWidth(290);
		textAreaLeft.setWrapText(true);
		borderPaneLeft.setRight(textAreaLeft);
		//previousButton.setDisable(true);
		borderPaneLeft.setLeft(previousButton);
		borderPane.setLeft(borderPaneLeft);
		
		previousButton.setOnAction(e -> {
			
			if(note.getPos() == -1) {
				
				note.setPos(note.getStack().size());
				note.getStack().add(new NoteCard(textAreaLeft.getText(), textAreaRight.getText()));
			}
			
			else if(note.getStack().get(note.getPos()).getEdited()) {
				
				note.getStack().get(note.getPos()).setFront(textAreaLeft.getText());
				note.getStack().get(note.getPos()).setBack(textAreaRight.getText());
			}
			
			if(note.getPos() == 0) {
				
				note.setPos(note.getStack().size() - 1);
				textAreaLeft.setText(note.getStack().get(note.getPos()).getFront());
				textAreaRight.setText(note.getStack().get(note.getPos()).getBack());
				borderPaneLeft.setRight(textAreaLeft);
				borderPaneRight.setLeft(textAreaRight);
			}
			
			else {
				
				note.setPos(note.getPos() - 1);
				textAreaLeft.setText(note.getStack().get(note.getPos()).getFront());
				textAreaRight.setText(note.getStack().get(note.getPos()).getBack());
				borderPaneLeft.setRight(textAreaLeft);
				borderPaneRight.setLeft(textAreaRight);
			}
		});
		
		textAreaLeft.setOnKeyPressed(e -> {
			
			if(note.getPos() != -1){
				
				note.getStack().get(note.getPos()).setEdited(true);
			}
		});
		
		//Right
		textAreaRight.setPromptText("This is the back of the note card.\n"
								  + "The back will be displayed when the front is clicked.");
		textAreaRight.setMaxWidth(290);
		textAreaRight.setMinWidth(290);
		textAreaRight.setWrapText(true);
		borderPaneRight.setLeft(textAreaRight);
		//nextButton.setDisable(true);
		borderPaneRight.setRight(nextButton);
		borderPane.setRight(borderPaneRight);
		
		nextButton.setOnAction(e -> {
			
			if(note.getPos() == -1) {
				
				note.setPos(note.getStack().size());
				note.getStack().add(new NoteCard(textAreaLeft.getText(), textAreaRight.getText()));
			}
			
			else if(note.getStack().get(note.getPos()).getEdited()) {
				
				note.getStack().get(note.getPos()).setFront(textAreaLeft.getText());
				note.getStack().get(note.getPos()).setBack(textAreaRight.getText());
			}
			
			if((note.getStack().size() - 1) == note.getPos()) {
				
				note.setPos(0);
				textAreaLeft.setText(note.getStack().get(note.getPos()).getFront());
				textAreaRight.setText(note.getStack().get(note.getPos()).getBack());
				borderPaneLeft.setRight(textAreaLeft);
				borderPaneRight.setLeft(textAreaRight);
			}
			
			else {
				
				note.setPos(note.getPos() + 1);
				textAreaLeft.setText(note.getStack().get(note.getPos()).getFront());
				textAreaRight.setText(note.getStack().get(note.getPos()).getBack());
				borderPaneLeft.setRight(textAreaLeft);
				borderPaneRight.setLeft(textAreaRight);
			}
		});
		
		textAreaRight.setOnKeyPressed(e -> {
			
			if(note.getPos() != -1){
				
				note.getStack().get(note.getPos()).setEdited(true);
			}
		});
		
		//Top
		hBox1.getChildren().addAll(saveButton, newNoteCardButton);
		hBox1.setPadding(new Insets(1, 1, 1, 1));
		nameLabel.setPadding(new Insets(0, 5, 0, 5));
		hBox2.getChildren().addAll(nameLabel, nameTextField);
		hBox2.setPadding(new Insets(1, 1, 1, 1));
		vBox.getChildren().addAll(hBox1, hBox2);
		vBox.setPadding(new Insets(3, 3, 3, 3));
		borderPane.setTop(vBox);
		
		saveButton.setOnAction(e -> {
			
			if(note.getPos() == -1) {
				
				note.setPos(note.getStack().size());
				note.getStack().add(new NoteCard(textAreaLeft.getText(), textAreaRight.getText()));
			}
			
			for(int i = 0; i < note.getStack().size(); i++) {
				
				note.getStack().get(i).setEdited(false);
			}
			
			note.setName(nameTextField.getText());
			
			note.getStack().get(note.getPos()).setFront(textAreaLeft.getText());
			note.getStack().get(note.getPos()).setBack(textAreaRight.getText());
			
			manager.save(note);
		});
		
		newNoteCardButton.setOnAction(e -> {
			
			if(note.getPos() == -1) {
				
				note.setPos(note.getStack().size());
				note.getStack().add(new NoteCard(textAreaLeft.getText(), textAreaRight.getText()));
			}
			
			else if(note.getStack().get(note.getPos()).getEdited()){
				
				note.getStack().get(note.getPos()).setFront(textAreaLeft.getText());
				note.getStack().get(note.getPos()).setBack(textAreaRight.getText());
				note.getStack().get(note.getPos()).setEdited(false);
			}
			
			textAreaLeft.setText("");
			textAreaRight.setText("");
			
			note.setPos(-1);
		});
		
		window.setOnCloseRequest(e -> {
			
			e.consume();
			
			if(note.getPos() == -1) {
				
				note.setPos(note.getStack().size());
				note.getStack().add(new NoteCard(textAreaLeft.getText(), textAreaRight.getText()));
			}
			
			boolean edited = false;
			
			for(int i = 0; i < note.getStack().size(); i++) {
				
				if(note.getStack().get(i).getEdited()) {
					edited = true;
				}
			}
			
			if(edited) {
				
				ViewSaveConfirmation vsc = new ViewSaveConfirmation();
				
				vsc.display();
				
				if(Objects.equals(vsc.getAnswer(), "yes")) {
					
					note.setName(nameTextField.getText());
					for(int i = 0; i < note.getStack().size(); i++) {
						
						note.getStack().get(i).setEdited(false);
					}
					
					if(note.getStack().size() != note.getPos()) {
						
						note.getStack().get(note.getPos()).setFront(textAreaLeft.getText());
						note.getStack().get(note.getPos()).setBack(textAreaRight.getText());
					}
					
					note.setPos(0);
					manager.save(note);
					
					willReturn = true;
					window.close();
				}
				
				if(Objects.equals(vsc.getAnswer(), "no")) {
					
					willReturn = false;
					window.close();
				}
				
				if(Objects.equals(vsc.getAnswer(), "cancel")) {
					
					//does not do anything
				}
			}
			
			else {
				
				willReturn = true;
				window.close();
			}
		});
		
		Scene s = new Scene(borderPane, 650, 400);
		window.setScene(s);
		window.showAndWait();
		
		return note;
	}
	
	public NoteCardSet getNoteCardSet() {
		return note;
	}
	
	public void setNoteCardSet(NoteCardSet noteCardSet) {
		this.note = noteCardSet;
	}
	
	public boolean getWillReturn() {
		return willReturn;
	}
	
	public void setWillReturn(boolean willReturn) {
		this.willReturn = willReturn;
	}
}