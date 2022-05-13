package ca.uwaterloo.cs349;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.PrintStream;

class View2 extends Pane implements IView {
	Image mouse = new Image("mouse.png", 80, 80, false, true);
	Image rubber = new Image("rubber.png", 80, 80, false, true);
	Image line = new Image("line.png", 80, 80, false, true);
	Image circle = new Image("circle.png", 80, 80, false, true);
	Image rectangle = new Image("rectangle.png", 80, 80, false, true);
	Image fill = new Image("fill.png", 80, 80, true, false);



	//private TextArea text = new TextArea("");
	private Model model; // reference to the model
	ToggleButton select = new ToggleButton("",new ImageView(mouse));
	ToggleButton rubber_button = new ToggleButton("",new ImageView(rubber));
	ToggleButton line_button = new ToggleButton("",new ImageView(line));
	ToggleButton circle_button = new ToggleButton("",new ImageView(circle));
	ToggleButton rectangle_button = new ToggleButton("",new ImageView(rectangle));
	ToggleButton fill_button = new ToggleButton("",new ImageView(fill));
	ToggleGroup group = new ToggleGroup();
	GridPane tool_bar = new GridPane();






	View2(Model model) {
		int BUTTON_MIN_WIDTH = 5;
		// keep track of the model
		this.model = model;
		select.setMinSize(BUTTON_MIN_WIDTH,BUTTON_MIN_WIDTH);
		rubber_button.setMinSize(BUTTON_MIN_WIDTH,BUTTON_MIN_WIDTH);
		line_button.setMinSize(BUTTON_MIN_WIDTH,BUTTON_MIN_WIDTH);
		circle_button.setMinSize(BUTTON_MIN_WIDTH,BUTTON_MIN_WIDTH);
		rectangle_button.setMinSize(BUTTON_MIN_WIDTH,BUTTON_MIN_WIDTH);
		fill_button.setMinSize(BUTTON_MIN_WIDTH,BUTTON_MIN_WIDTH);
		select.setPrefSize(100, 100);
		rubber_button.setPrefSize(100, 100);
		line_button.setPrefSize(100, 100);
		circle_button.setPrefSize(100, 100);
		rectangle_button.setPrefSize(100, 100);
		fill_button.setPrefSize(100, 100);
		select.setToggleGroup(group);
		rubber_button.setToggleGroup(group);
		line_button.setToggleGroup(group);
		circle_button.setToggleGroup(group);
		rectangle_button.setToggleGroup(group);
		fill_button.setToggleGroup(group);


		tool_bar.add(select, 0, 0);
		tool_bar.add(rubber_button, 1, 0);
		tool_bar.add(line_button, 0, 1);
		tool_bar.add(circle_button, 1, 1);
		tool_bar.add(rectangle_button, 0, 2);
		tool_bar.add(fill_button, 1, 2);
		//tool_bar.setAlignment(Pos.BASELINE_LEFT);

		select.setOnMouseClicked(e -> {
			for (int i = 0; i < model.current_shape.size(); ++i) {
				model.current_shape.get(i).setEffect(null);
			}
			model.disable_button = true;
			model.selected = null;
			model.selected_string = null;
			model.start_drawing = false;
			model.start_selecting = true;
			model.start_removing = false;
			model.ready_to_move = false;
			model.ready_to_fill = false;
			model.update_property();
			e.consume();   // this will stop the event from being passed along
		});

		rubber_button.setOnMouseClicked(e -> {
			for (int i = 0; i < model.current_shape.size(); ++i) {
				model.current_shape.get(i).setEffect(null);
			}
			model.disable_color_shape = true;
			model.disable_color_line = true;
			model.disable_button = true;
			System.out.println("Scene clicked (" + e.getX() + "," + e.getY() + ")");
			model.selected = null;
			model.selected_string = null;
			model.start_drawing = false;
			model.start_selecting = false;
			model.start_removing = true;
			model.ready_to_move = false;
			model.ready_to_fill = false;
			model.update_property();
			e.consume();   // this will stop the event from being passed along
		});

		line_button.setOnMouseClicked(e -> {
			for (int i = 0; i < model.current_shape.size(); ++i) {
				model.current_shape.get(i).setEffect(null);
			}
			model.disable_color_shape = true;
			model.disable_color_line = false;
			model.disable_button = false;
			model.selected = new Line();
			model.selected_string = "Line";
			model.start_drawing = true;
			model.start_selecting = false;
			model.start_removing = false;
			model.ready_to_move = false;
			model.ready_to_fill = false;
			model.update_property();
			System.out.println("line_button clicked (" + e.getX() + "," + e.getY() + ")");
			e.consume();   // this will stop the event from being passed along
		});

		circle_button.setOnMouseClicked(e -> {
			for (int i = 0; i < model.current_shape.size(); ++i) {
				model.current_shape.get(i).setEffect(null);
			}
			model.disable_color_shape = false;
			model.disable_color_line = false;
			model.disable_button = false;
			model.selected = new Circle();
			model.selected.setFill(Color.BLACK);
			model.selected.setStroke(Color.BLACK);
			model.selected_string = "Circle";
			model.start_drawing = true;
			model.start_selecting = false;
			model.start_removing = false;
			model.ready_to_move = false;
			model.ready_to_fill = false;
			model.update_property();
			System.out.println("circle_button clicked (" + e.getX() + "," + e.getY() + ")");
			e.consume();   // this will stop the event from being passed along
		});

		rectangle_button.setOnMouseClicked(e -> {
			for (int i = 0; i < model.current_shape.size(); ++i) {
				model.current_shape.get(i).setEffect(null);
			}
			model.disable_color_shape = false;
			model.disable_color_line = false;
			model.disable_button = false;
			model.selected = new Rectangle();
			model.selected_string = "Rectangle";
			model.selected.setFill(Color.BLACK);
			model.selected.setStroke(Color.BLACK);
			model.start_drawing = true;
			model.start_selecting = false;
			model.start_removing = false;
			model.ready_to_move = false;
			model.ready_to_fill = false;
			model.update_property();
			System.out.println("rectangle_button clicked (" + e.getX() + "," + e.getY() + ")");
			e.consume();   // this will stop the event from being passed along
		});

		fill_button.setOnMouseClicked(e -> {
			for (int i = 0; i < model.current_shape.size(); ++i) {
				model.current_shape.get(i).setEffect(null);
			}
			model.disable_color_shape = false;
			model.disable_color_line = false;
			model.disable_button = false;
			model.selected = null;
			model.selected_string = null;
			model.start_drawing = false;
			model.start_selecting = false;
			model.start_removing = false;
			model.ready_to_move = false;
			model.ready_to_fill = true;
			model.update_property();
			System.out.println("Scene clicked (" + e.getX() + "," + e.getY() + ")");
			e.consume();   // this will stop the event from being passed along
		});


		// set label properties
		/*text.setMaxSize(HelloMVC3.WINDOW_WIDTH, HelloMVC3.WINDOW_HEIGHT/2);
		text.setWrapText(true);*/

		// the previous controller code will just be handled here
		// we don't need always need a separate controller class!
		/*text.setOnMouseClicked(mouseEvent -> {
			System.out.println("Controller: changing Model (actionPerformed)");
			model.incrementCounter();
		});*/

		// add label widget to the pane
		this.getChildren().add(tool_bar);

		// register with the model when we're ready to start receiving data
		model.addView(this);
	}

	// When notified by the model that things have changed,
	// update to display the new value
	public void updateView() {
		System.out.println("View2: updateView");
	}
	public void updateProperty_view(){ }
	public void deattach() {}
	public WritableImage getImage() {return null;}
	public Color return_color() {return null;}
}
