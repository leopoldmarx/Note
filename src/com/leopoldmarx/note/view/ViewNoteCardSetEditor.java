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

public class ViewNoteCardSetEditor {
	
	private FileManager manager;
	private Stage window;
	private BorderPane borderPane;
	
	private NoteCardSet note;
	private boolean deleted = false;
	
	public void display(NoteCardSet noteCardSet) {
		
		note = noteCardSet;
		note.setPos(0);
		
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
		Button deleteButton = new Button ("Delete");
		Label nameLabel = new Label("Name:");
		TextField nameTextField = new TextField();
		
		//Left
		textAreaLeft.setMaxWidth(290);
		textAreaLeft.setMinWidth(290);
		textAreaLeft.setWrapText(true);
		textAreaLeft.setText(note.getStack().get(note.getPos()).getFront());
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
		textAreaRight.setMaxWidth(290);
		textAreaRight.setMinWidth(290);
		textAreaRight.setWrapText(true);
		textAreaRight.setText(note.getStack().get(note.getPos()).getBack());
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
		hBox1.getChildren().addAll(saveButton, newNoteCardButton, deleteButton);
		hBox1.setPadding(new Insets(1, 1, 1, 1));
		nameLabel.setPadding(new Insets(0, 5, 0, 5));
		nameTextField.setText(note.getName());
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
		
		deleteButton.setOnAction(e -> {
			
			ViewDeletionConfirmation vdc = new ViewDeletionConfirmation();
			vdc.display();
			
			if(vdc.getAnswer()) {
			
				manager.delete(note);
				deleted = true;
				window.close();
			}
		});
		
		nameTextField.setOnAction(e -> {
			
			note.getStack().get(0).setEdited(true);
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
				
				if(vsc.getAnswer() == "yes") {
					
					for(int i = 0; i < note.getStack().size(); i++) {
						
						note.getStack().get(i).setEdited(false);
					}
					
					if(note.getStack().size() != note.getPos()) {
						
						note.getStack().get(note.getPos()).setFront(textAreaLeft.getText());
						note.getStack().get(note.getPos()).setBack(textAreaRight.getText());
					}
					
					note.setPos(0);
					manager.save(note);
					
					window.close();
				}
				
				if(vsc.getAnswer() == "no") {
					
					window.close();
				}
				
				if(vsc.getAnswer() == "cancel") {
					
					//does not do anything
				}
			}
			
			else {
				
				window.close();
			}
		});
		
		Scene s = new Scene(borderPane, 650, 400);
		window.setScene(s);
		window.showAndWait();
	}
	
	public NoteCardSet getNoteCardset() {
		return note;
	}
	
	public void setNoteCardSet(NoteCardSet noteCardSet) {
		this.note = noteCardSet;
	}
	
	public boolean getDeleted() {
		return deleted;
	}
}