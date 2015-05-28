package com.leopoldmarx.note.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewDeletionConfirmation {
	
	private boolean answer;
	
	public void display() {
		
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Note");
		
		HBox hBox = new HBox();
		VBox vBox = new VBox();
		
		vBox.setPadding(new Insets(10, 10, 10, 10));
		
		Label l1 = new Label("Are you sure you want to delete?");
		
		Button yes = new Button("Yes");
		Button no = new Button("No");

		hBox.getChildren().addAll(yes, no);
		hBox.setSpacing(20);
		
		vBox.getChildren().addAll(l1, hBox);

		window.setOnCloseRequest(e -> {
			answer = false;
		});
		
		yes.setOnAction(e -> {
			answer = true;
			window.close();
		});
		
		no.setOnAction(e -> {
			answer = false;
			window.close();
		});
				
		Scene scene = new Scene(vBox, 190, 70);
		window.setScene(scene);
		window.showAndWait();
	}
	
	public boolean getAnswer() {
		return answer;
	}
}
