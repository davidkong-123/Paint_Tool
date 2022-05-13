package ca.uwaterloo.cs349;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// HelloMVC
// A simple MVC example inspired by Joseph Mack, http://www.austintek.com/mvc/
// This version uses MVC: two views coordinated with the observer pattern, but no separate controller.

public class SketchIt extends Application {

	static double WINDOW_WIDTH = 1300;
	static double WINDOW_HEIGHT = 900;

	@Override
	public void start(Stage stage) throws Exception {
		// create and initialize the Model to hold our counter
		Model model = new Model();

		// create each view, and tell them about the model
		// the views will register themselves with the model
		View1 menue_bar = new View1(model);
		View2 tool_bar = new View2(model);
		View3 property_bar = new View3(model);
		View4 canvas = new View4(model);

		BorderPane grid = new BorderPane ();//holding all parts
		GridPane tools = new GridPane();//only holding the left part
		GridPane temp = new GridPane();
		GridPane main = new GridPane();

		grid.setTop(menue_bar);      // top-view

		tools.add(tool_bar, 0, 0);
		tool_bar.setMinSize(10,15);
		tool_bar.setVisible(true);
		tools.add(property_bar, 0, 1);
		property_bar.setMinSize(50,50);
		property_bar.setVisible(true);
		tools.setVgap(10);
		tools.setMinSize(50,50);
		grid.setLeft(tools);
		grid.setCenter(canvas);
		grid.setOnKeyPressed(event -> {

			if (event.getCode() == KeyCode.ESCAPE) {
				System.out.println("Exit selection");
				model.selected = null;
				model.selected_string = null;
				model.start_drawing = false;
				model.start_selecting = false;
				model.start_removing = false;
				model.ready_to_move = false;
				model.ready_to_fill = false;
				model.update_property();
			} else if (event.getCode() == KeyCode.DELETE) {
				System.out.println("Exit selection");
				System.out.println("start remove");
				model.current_shape.get(model.shape_index).setFill(Color.TRANSPARENT);
				model.current_shape.get(model.shape_index).setStroke(Color.TRANSPARENT);
				model.current_shape.get(model.shape_index).setTranslateX(+1000);
				model.current_shape.get(model.shape_index).setTranslateY(+1000);
			}
		});

		// Add grid to a scene (and the scene to the stage)
		Scene scene = new Scene(grid, WINDOW_WIDTH, WINDOW_HEIGHT);
		grid.setMinSize(640.0,480.0);
		grid.setMaxSize(1920.0,1440.0);

		stage.setMaxHeight(1920);
		stage.setMaxWidth(1440);
		stage.setMinHeight(480);
		stage.setMinHeight(640);
		stage.setTitle("SketchIt");
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show();
	}
}
