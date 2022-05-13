package ca.uwaterloo.cs349;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class View1 extends Pane implements IView {
	private Button button = new Button("?");
	private Model model; // reference to the model

	 MenuBar menubar = new MenuBar();

	 Menu fileMenu = new Menu("File");
	 MenuItem New = new MenuItem("New");
	 MenuItem Load = new MenuItem("Load");
	 MenuItem Save = new MenuItem("Save");
	 MenuItem Quit = new MenuItem("Quit");

	 Menu HelpMenu = new Menu("Help");
	 MenuItem About = new MenuItem("About");



	View1(Model model) {
		// keep track of the model
		this.model = model;

		// Put menus together
		fileMenu.getItems().addAll(New, Load, Save,Quit);
		HelpMenu.getItems().add(About);
		menubar.getMenus().addAll(fileMenu, HelpMenu);
		menubar.setMinSize(100, 30);
		menubar.setPrefSize(1200, 10);
		// setup the view (i.e. group+widget)
		//this.setMinSize(HelloMVC3.WINDOW_WIDTH, HelloMVC3.WINDOW_HEIGHT/30);
		/*button.setMinSize(75, 25);
		button.setMaxSize(100, 50);
   		*/

		New.setOnAction(actionEvent -> {
			// tell the model t clear all the shape on the canvas
			model.clear_canvas();

		});

		Quit.setOnAction(actionEvent -> {
			System.out.println("exit program");
			System.exit(1); //non zero value to exit says abnormal termination of JVM

		});



		Save.setOnAction(actionEvent -> {
			// tell the model t clear all the shape on the canvas
			model.save_image();
		});

		About.setOnAction(actionEvent -> {
			// tell the model t clear all the shape on the canvas
			new diaglogwindow(200, 200);
		});



		// add button widget to the pane
		this.getChildren().add(menubar);

		// register with the model when we're ready to start receiving data
		model.addView(this);
	}

	public void updateProperty_view(){}
	// When notified by the model that things have changed,
	// update to display the new value
	public void updateView() {
		System.out.println("View: updateView");
		//button.setText(Integer.toString(model.getCounterValue()));
	}
	public Color return_color() {return null;}




	public void deattach() {}
	public WritableImage getImage() {return null;}

	private class StandardWindow extends Stage {
		StandardWindow() {
			this(0f, 0f);
		}

		StandardWindow(float x, float y) {
			super();
			this.setX(x);
			this.setY(y);
			this.setWidth(500);
			this.setHeight(300);
			this.setResizable(true);
		}
	}

	class diaglogwindow extends StandardWindow {
		diaglogwindow() {
			this(300, 300);
		}

		diaglogwindow(float x, float y) {
			super(x, y);
			TextArea text = new TextArea("Program: Data Science \n Name: David Kong \n WatID: d9kong");
			text.setWrapText(true);
			text.setPrefWidth(280);
			text.setPrefHeight(125);
			text.relocate(10, 10);
			text.setEditable(false);



			Scene scene = new Scene(new Pane(
					text), 300, 200);
			this.setScene(scene);
			this.setTitle("Dialog Box");
			this.setResizable(false);
			this.setAlwaysOnTop(true);
			this.show();
		}
	}
}
