package com.leopoldmarx.note.view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.leopoldmarx.note.file.FileManager;
import com.leopoldmarx.note.notecard.NoteCardSet;

public class ViewMain extends Application {
	
	private ArrayList<NoteCardSet> allProjects;
	private FileManager manager;
	private Stage window;
	private BorderPane borderPane;
	private Image icon = new Image(getClass().getResourceAsStream("/com/leopoldmarx/note/pictures/icon.png"));
	
	private int amountPerRow = 4;
	private int current = 0;
	private int row = 0;
	private static Display display = Display.FRONT;
	
	private enum Display {
		FRONT, BACK
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		
		window = arg0;
		
		manager = new FileManager();
		manager.generateLocation();
		
		allProjects = manager.read();
		
		window.setTitle("Note");
		window.setHeight(450);
		window.setWidth(700);
		//window.setMaxWidth(700);
		//window.setMinWidth(700);
		//window.setMaxHeight(450);
		//window.setMinHeight(450);

		//Center Menu
		this.borderPane = allNoteCardSets();
		ImageView iv = new ImageView(icon);
		//this.borderPane.setCenter(iv);
		
		window.setOnCloseRequest(e -> {
			e.consume();
			ViewExitConfirmation vec = new ViewExitConfirmation();

			vec.display();
			
			if(vec.getAnswer()) {
				
				for(int i = 0; i < (allProjects.size() - 1); i++) {
					
					manager.save(allProjects.get(i));
				}
				
				window.close();
			}
		});
		
		Scene s = new Scene(borderPane, 700, 450);
		s.getStylesheets().add("/com/leopoldmarx/note/css/Style.css");
		window.getIcons().add(new Image("/com/leopoldmarx/note/pictures/icon.png"));
		window.setScene(s);
		window.show();
	}
	
	public BorderPane allNoteCardSets() {
		
		BorderPane border = new BorderPane();
		
		GridPane centerMenu = new GridPane();
		centerMenu.setPadding(new Insets(10, 10, 10, 10));
		centerMenu.setVgap(8);
		centerMenu.setHgap(8);
		
		//Top
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("_File");
		MenuItem newMenuItem = new MenuItem("New...");
		fileMenu.getItems().add(newMenuItem);
		menuBar.getMenus().add(fileMenu);
		border.setTop(menuBar);
		
		for (NoteCardSet noteCardSet : allProjects) {
			
			Button b = new Button(noteCardSet.getName());

			//b.setMaxWidth(window.getWidth());
			b.setPrefWidth(window.getWidth() / 4);
			//b.setMaxHeight(b.getWidth());
			b.setPrefHeight(window.getWidth() / 8);

			GridPane.setConstraints(b, current, row);
			centerMenu.getChildren().add(b);
			
			b.setOnAction(e -> {
				
				border.setCenter(noteCardSetLayout(noteCardSet));
			});
			
			if (current < amountPerRow - 1)
			{
				current++;
			}
			else if (current >= amountPerRow - 1)
			{
				current = 0;
				row++;
			}
		}
		
		newMenuItem.setOnAction(e -> {

			ViewNewNoteCardSet vnncs = new ViewNewNoteCardSet();
			
			NoteCardSet noteCardSet = vnncs.display();
			
			if(vnncs.getWillReturn()){
				
				Button b = new Button(noteCardSet.getName());
				
				GridPane.setConstraints(b, current, row);
				centerMenu.getChildren().add(b);
				
				b.setOnAction(event -> {

					border.setCenter(noteCardSetLayout(noteCardSet));
				});
				
				if (current < amountPerRow - 1)
				{
					current++;
				}
				else if (current >= amountPerRow - 1)
				{
					current = 0;
					row++;
				}
			}
		});
		
		border.setCenter(centerMenu);
		return border;
	}
	
	public BorderPane noteCardSetLayout(NoteCardSet noteCardSet) {
		
		window.setTitle("Note - " + noteCardSet.getName());
		BorderPane borderPane2 = new BorderPane();
		
		Button   nextButton = new Button("→");
		Button   previousButton = new Button("←");
		HBox     hBox = new HBox();
		Button   saveButton = new Button("Save");
		Button   editButton = new Button("Edit");
		Button   backButton = new Button("Back");
		TextArea textArea = new TextArea();
		
		//Right
		//nextButton.setMinHeight(450);
		borderPane2.setRight(nextButton);
		
		nextButton.setOnAction(e -> {
			
			if((noteCardSet.getStack().size() - 1) == (noteCardSet.getPos())) {
				
				noteCardSet.setPos(0);
				textArea.setText(noteCardSet.getStack().get(noteCardSet.getPos()).getFront());
				borderPane2.setCenter(textArea);
			}
			
			else {
				
				noteCardSet.setPos(noteCardSet.getPos() + 1);
				textArea.setText(noteCardSet.getStack().get(noteCardSet.getPos()).getFront());
				borderPane2.setCenter(textArea);
			}
			
			display = Display.FRONT;
		});
		
		//Left
		//previousButton.setMinHeight(450);
		borderPane2.setLeft(previousButton);
		
		previousButton.setOnAction(e -> {
			
			if(noteCardSet.getPos() == 0) {
				
				noteCardSet.setPos(noteCardSet.getStack().size() - 1);
				textArea.setText(noteCardSet.getStack().get(noteCardSet.getPos()).getFront());
				borderPane2.setCenter(textArea);
			}
			
			else {
				
				noteCardSet.setPos(noteCardSet.getPos() - 1);
				textArea.setText(noteCardSet.getStack().get(noteCardSet.getPos()).getFront());
				borderPane2.setCenter(textArea);
			}
			
			display = Display.FRONT;
		});
		
		//Top
		saveButton.setOnAction(e -> {
			
			manager.save(noteCardSet);
		});
		
		editButton.setOnAction(e -> {
			
			ViewNoteCardSetEditor vncse = new ViewNoteCardSetEditor();
			
			vncse.display(noteCardSet);
			
			allProjects = manager.read();
			
			if(vncse.getDeleted()) {
				
				window.setTitle("Note");
				
				GridPane centerMenu = new GridPane();
				centerMenu.setPadding(new Insets(10, 10, 10, 10));
				centerMenu.setVgap(8);
				centerMenu.setHgap(8);
						
				amountPerRow = 4;
				current = 0;
				row = 0;
				
				for (NoteCardSet note : allProjects) {
					
					Button b = new Button(note.getName());
					
					GridPane.setConstraints(b, current, row);
					centerMenu.getChildren().add(b);
					
					b.setOnAction(event -> {
						
						this.borderPane.setCenter(noteCardSetLayout(note));
					});
					
					if (current < amountPerRow - 1)
					{
						current++;
					}
					else if (current >= amountPerRow - 1)
					{
						current = 0;
						row++;
					}
				}
				
				this.borderPane.setCenter(centerMenu);
			}
			else {
				
				textArea.setText(noteCardSet.getStack().get(noteCardSet.getPos()).getFront());
				borderPane2.setCenter(textArea);
			}
		});
		
		backButton.setOnAction(e -> {
			
			window.setTitle("Note");
			
			GridPane centerMenu = new GridPane();
			centerMenu.setPadding(new Insets(10, 10, 10, 10));
			centerMenu.setVgap(8);
			centerMenu.setHgap(8);
			
			allProjects = manager.read();
			
			amountPerRow = 4;
			current = 0;
			row = 0;
			
			for (NoteCardSet note : allProjects) {
				
				Button b = new Button(note.getName());
				
				GridPane.setConstraints(b, current, row);
				centerMenu.getChildren().add(b);
				
				b.setOnAction(event -> {
					
					this.borderPane.setCenter(noteCardSetLayout(note));
				});
				
				if (current < amountPerRow - 1)
				{
					current++;
				}
				else if (current >= amountPerRow - 1)
				{
					current = 0;
					row++;
				}
			}
			
			this.borderPane.setCenter(centerMenu);
		});
		
		hBox.getChildren().addAll(saveButton, editButton, backButton);
		borderPane2.setTop(hBox);
		
		//Center
		textArea.setEditable(false);
		textArea.setWrapText(true);
		noteCardSet.setPos(0);
		textArea.setText(noteCardSet.getStack().get(noteCardSet.getPos()).getFront());
		borderPane2.setCenter(textArea);
		
		textArea.setOnMouseClicked(e -> {
			
			if(display == Display.FRONT) {
				
				textArea.setText(noteCardSet.getStack().get(noteCardSet.getPos()).getBack());
				borderPane2.setCenter(textArea);
				display = Display.BACK;
			}
			
			else {
				
				textArea.setText(noteCardSet.getStack().get(noteCardSet.getPos()).getFront());
				borderPane2.setCenter(textArea);
				display = Display.FRONT;
			}
		});
		
		return borderPane2;
	}
}