package ca.uwaterloo.cs349;

import javafx.geometry.Bounds;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import org.xml.sax.ErrorHandler;

import javax.imageio.ImageIO;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Model {

	// the data in the model, just a counter
	private int counter;	
	
	// all views of this model
	private ArrayList<IView> views = new ArrayList<IView>();
	ArrayList<Integer> list_of_thickness = new ArrayList<Integer>();
	ArrayList<Integer> list_of_line_type = new ArrayList<Integer>();
	ArrayList<Shape> current_shape = new ArrayList<Shape>();
	ArrayList<String> list_of_shape_name = new ArrayList<String>();
	ArrayList<Double> list_translate_x = new ArrayList<Double>();
	ArrayList<Double> list_translate_y = new ArrayList<Double>();
	ArrayList<Double> list_scale_x = new ArrayList<Double>();
	ArrayList<Double> list_scale_y = new ArrayList<Double>();
	ArrayList<Rectangle> list_of_rect = new ArrayList<Rectangle>();
	ArrayList<Circle> list_of_circle= new ArrayList<Circle>();
	ArrayList<Line> list_of_line = new ArrayList<Line>();
	Color shape_color = Color.BLACK;
	Color line_color = Color.BLACK;
	Shape selected = null;
	int shape_index;

	String selected_string = null;
	boolean start_drawing = false;
	boolean start_selecting = false;
	boolean start_removing = false;
	boolean ready_to_move = false;
	boolean ready_to_fill = false;
	boolean disable_button = false;
	boolean canvas_selected = false;
	boolean disable_color_shape = false;
	boolean disable_color_line = false;
	boolean ready_to_scale = true;

	double origin_x_cor = 0;
	double origin_y_cor = 0;
	double current_x_cor = 0;
	double current_y_cor = 0;

	int strokeWidth = 1;
	double line_type = 0;

	// method that the views can use to register themselves with the Model
	// once added, they are told to update and get state from the Model
	public void addView(IView view) {
		views.add(view);
		view.updateView();
	}

	// simple accessor method to fetch the current state
	public int getCounterValue() {
		return counter;
	}

	// method that the Controller uses to tell the Model to change state
	// in a larger application there would probably be multiple entry points like this
	public void setOrigin(double x,double y) {
		origin_x_cor = x;
		origin_y_cor = y;
		System.out.println("rect should created (");
		list_translate_x.add(0.0);
		list_translate_y.add(0.0);
		list_scale_x.add(1.0);
		list_scale_y.add(1.0);
		if (selected_string.equals("Rectangle")) {
			selected = new Rectangle( origin_x_cor,  origin_y_cor, 5, 5);
			selected.setFill(shape_color);
			selected.setStrokeWidth(strokeWidth);
			selected.setStroke(line_color);
			stroke_type();
			list_of_line_type.add((int) line_type);
			list_of_thickness.add(strokeWidth);

			current_shape.add(selected);//add the new shape
			list_of_shape_name.add("Rectangle");
			System.out.println("Rectangle X"+ ((Rectangle) selected).getX() + "Rectangle Y" + ((Rectangle) selected).getY());
			System.out.println("rect created (" + shape_color);
			notifyObservers();
		} else if (selected_string.equals("Circle")) {
			selected = new Circle( origin_x_cor,  origin_y_cor, 1, shape_color);
			selected.setStrokeWidth(strokeWidth);
			selected.setStroke(line_color);
			stroke_type();
			list_of_line_type.add((int) line_type);
			list_of_thickness.add(strokeWidth);
			current_shape.add(selected);//add the new shape
			list_of_shape_name.add("Circle");
			System.out.println("Circle radius"+ ((Circle) selected).getRadius());
			System.out.println("circle created (" + shape_color);
			notifyObservers();
		} else if (selected_string.equals("Line")) {
			selected = new Line(origin_x_cor,  origin_y_cor, origin_x_cor+1, origin_y_cor);
			selected.setFill(shape_color);
			list_of_line_type.add((int) line_type);
			list_of_thickness.add(strokeWidth);
			current_shape.add(selected);//add the new shape
			list_of_shape_name.add("Line");
			System.out.println("Line start x"+ ((Line) selected).getStartX() + "Line start x"+ ((Line) selected).getStartY());
			notifyObservers();
		}

		selected.setOnMouseClicked(e -> {
			System.out.println("we have found the  selcted shape (" + e.getX() + "," + e.getY() + ")");
			e.consume();   // this will stop the event from being passed along
			if (start_selecting == true && selected != null) {
				origin_x_cor = selected.getLayoutX();
				origin_y_cor = selected.getLayoutY();
			}
		});



		/*selected.get().setOnMouseDragged(e -> {
			System.out.println("we have found the  selcted shape (" + e.getX() + "," + e.getY() + ")");
			e.consume();   // this will stop the event from being passed along
			if (start_selecting == true) {
				current_x_cor = e.getX();
				current_y_cor = e.getY();
				selected.setTranslateX(current_x_cor-origin_x_cor);
				selected.setTranslateY(current_y_cor-origin_y_cor);
			}
		});*/


	}

	public void setCurrent_cor(double x,double y) {
		current_x_cor = x;
		current_y_cor = y;

		if (selected_string == "Rectangle") {
			views.get(views.size()-1).deattach();
			selected = new Rectangle(origin_x_cor,origin_y_cor,current_x_cor-origin_x_cor,current_y_cor-origin_y_cor);
			selected.setFill(shape_color);
			selected.setStroke(line_color);
			selected.setStrokeWidth(strokeWidth);
			stroke_type();
			current_shape.set(current_shape.size()-1,selected);
			notifyObservers();
		} else if (selected_string == "Circle"){
			views.get(views.size()-1).deattach();
			selected = new Circle(current_x_cor,current_y_cor,sqrt(abs(current_x_cor-origin_x_cor)*abs(current_x_cor-origin_x_cor)
					+ abs(current_y_cor-origin_y_cor) * abs(current_y_cor-origin_y_cor)));
			selected.setFill(shape_color);
			selected.setStroke(line_color);
			selected.setStrokeWidth(strokeWidth);
			//selected.getStrokeDashArray().addAll(80.0, 70.0, 60.0, 50.0, 40.0);
			stroke_type();
			current_shape.set(current_shape.size()-1,selected);
			notifyObservers();
		} else if (selected_string == "Line"){
			selected = new Line(origin_x_cor,origin_y_cor,current_x_cor,current_y_cor);
			stroke_type();
			selected.setStrokeWidth(strokeWidth);
			selected.setStroke(line_color);
			views.get(views.size()-1).deattach();
			current_shape.set(current_shape.size()-1,selected);
			notifyObservers();
		}


	}

	public void stroke_type() {
		selected.setStroke(Color.TRANSPARENT);
		selected.getStrokeDashArray().clear();
		if (line_type == 0) {
			selected.getStrokeDashArray().addAll(1.0);
			selected.setStrokeDashOffset(0);
			selected.setStroke(line_color);
		} else if (line_type == 1) {
			//empty
		} else if (line_type == 2) {
			selected.getStrokeDashArray().addAll(50.0, 50.0);
			selected.setStrokeDashOffset(25);
			selected.setStroke(line_color);
		} else {
			selected.getStrokeDashArray().addAll(10.0);
			selected.setStrokeDashOffset(10);
			selected.setStroke(line_color);
		}
	}

	public void clear_canvas() {
		int size = current_shape.size();
		for (int i = 0; i < size; ++i) {
			views.get(views.size()-1).deattach();
		}
	}

	public void save_image() {
		WritableImage image = views.get(views.size()-1).getImage();
		WritableImage writableImage = new WritableImage((int) 1000, (int)790);
	}

	public void update_property() {
		System.out.println("trying to update property");
		for (IView view : this.views) {
			view.updateProperty_view();
		}
	}
	public Color help_get_color() {
		System.out.println("trying to udpate property");
		return views.get(2).return_color();
	}

	
	// the model uses this method to notify all of the Views that the data has changed
	// the expectation is that the Views will refresh themselves to display new data when appropriate
	 void notifyObservers() {
		for (IView view : this.views) {
			System.out.println("Model: notify View");
			view.updateView();
		}
	}
}
