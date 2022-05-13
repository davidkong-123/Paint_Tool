package ca.uwaterloo.cs349;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

class View4 extends Pane implements IView {

    //private TextArea text = new TextArea("");
    private Model model; // reference to the model


    GridPane tool_bar = new GridPane();

    Canvas canvas = new Canvas(1000,790);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    DropShadow selected_shadow = new DropShadow(40, Color.NAVY);
    DropShadow no_effect = null;
    Image scale = new Image("scale.png", 80, 80, true, false);
    Circle scale_button = new Circle(40,Color.BLUE);
    boolean scale_start = false;


    View4(Model model) {
        // keep track of the model
        this.model = model;
        scale_button.setFill(new ImagePattern(scale));
        scale_button.setCenterX(5000.0);
        this.getChildren().add(scale_button);

        scale_button.setOnMouseClicked(e -> {

            System.out.println("!!!!!!scale_button selected (" + e.getX() + "," + e.getY() + ")");
            System.out.println("Scale x" + model.current_shape.get(model.shape_index).getScaleX() + "" +
                    "scaley: " + model.current_shape.get(model.shape_index).getScaleY());
            model.origin_x_cor = e.getX();
            model.origin_y_cor = e.getY();

        });
        scale_button.setOnMouseDragged(e -> {
            System.out.println("!!!!!!scale_button selected (" + e.getX() + "," + e.getY() + ")");
            System.out.println("Scale x" + model.current_shape.get(model.shape_index).getScaleX() + "" +
                    "scaley: " + model.current_shape.get(model.shape_index).getScaleY());
            if (scale_start) {
                if (model.ready_to_scale == true && (-model.origin_x_cor
                        + model.current_shape.get(model.shape_index).getTranslateX() + e.getX() > 0) && ((-model.origin_y_cor
                        + model.current_shape.get(model.shape_index).getTranslateY() + e.getY() > 0))) {
                    model.current_shape.get(model.shape_index).setScaleX((-model.origin_x_cor
                            + model.current_shape.get(model.shape_index).getTranslateX() + e.getX())/100);
                    model.current_shape.get(model.shape_index).setScaleY((-model.origin_y_cor
                            + model.current_shape.get(model.shape_index).getTranslateY() + e.getY())/100);
                    model.list_scale_x.set(model.shape_index,(-model.origin_x_cor
                            + model.current_shape.get(model.shape_index).getTranslateX() + e.getX())/100);
                    model.list_scale_y.set(model.shape_index,(-model.origin_y_cor
                            + model.current_shape.get(model.shape_index).getTranslateY() + e.getY())/100);

                    String shape_name = model.list_of_shape_name.get(model.shape_index);
                    System.out.println("shape name" + shape_name);
                    if (shape_name == "Line") {
                        System.out.println("Line start x"+ ((Line) model.selected).getStartX() + "Line start x"+ ((Line) model.selected).getStartY());
                        scale_button.setCenterX(((Line) model.selected).getEndX() + model.list_translate_x.get(model.shape_index));
                        scale_button.setCenterY(((Line) model.selected).getEndY() + model.list_translate_y.get(model.shape_index));
                    } else if (shape_name == "Rectangle") {
                        System.out.println("Rectangle X"+ ((Rectangle) model.selected).getX() + "Rectangle Y" + ((Rectangle) model.selected).getY());
                        scale_button.setCenterX(((Rectangle) model.selected).getX() + model.list_scale_x.get(model.shape_index)*
                                ((Rectangle) model.selected).getWidth()+ model.list_translate_x.get(model.shape_index));
                        scale_button.setCenterY(((Rectangle) model.selected).getY() + model.list_scale_y.get(model.shape_index)*
                                ((Rectangle) model.selected).getHeight()+ model.list_translate_y.get(model.shape_index));
                    } else if (shape_name == "Circle") {
                        System.out.println("Circle radius"+ ((Circle) model.selected).getRadius());
                        scale_button.setCenterX(((Circle) model.selected).getCenterX() + model.list_scale_x.get(model.shape_index)*
                                ((Circle) model.selected).getRadius()+ model.list_translate_x.get(model.shape_index));
                        scale_button.setCenterY(((Circle) model.selected).getCenterY() + model.list_scale_y.get(model.shape_index)*
                                ((Circle) model.selected).getRadius()+ model.list_translate_y.get(model.shape_index));
                    }
                }
            }
            scale_start =true;
            e.consume();   // this will stop the event from being passed along
        });

        scale_button.setOnMouseReleased(e -> {

            model.ready_to_scale = false;
            model.ready_to_move = false;
            scale_start =false;
            scale_button.setCenterX(5000.0);
        });


        this.setOnMousePressed(e -> {
            System.out.println("canvas pressed (" + e.getX() + "," + e.getY() + ")");
            System.out.println("model.selected_string :" + model.selected_string);
            if (!model.start_selecting) {
                if (model.selected_string != null) {
                    model.setOrigin(e.getX(),e.getY());
                }
            } else {
                model.canvas_selected = true;
                model.disable_button = true;
                scale_button.setCenterX(5000.0);
                System.out.println("disable pressed (" + e.getX() + "," + e.getY() + ")");
                for (int i = 0; i < model.current_shape.size(); ++i) {
                    model.current_shape.get(i).setEffect(no_effect);
                }
                model.update_property();
            }

        });



        this.setOnMouseDragged(e -> {
            System.out.println("canvas moved (" + e.getX() + "," + e.getY() + ")");
            if (model.start_drawing == true) {
                model.setCurrent_cor(e.getX(),e.getY());
            } else if (model.start_selecting == true) {

            }

        });

        this.setOnMouseDragReleased(e -> {
            if (!model.selected_string.equals(null) && model.start_drawing == true) {
                System.out.println("canvas moved (" + e.getX() + "," + e.getY() + ")");
                model.setCurrent_cor(e.getX(),e.getY());
                model.start_drawing = false;
                model.selected = null;
                scale_button.setCenterX(5000.0);
            }
           // e.consume();   // this will stop the event from being passed along
        });




        // add label widget to the pane
        //this.getChildren().add(canvas);

        // register with the model when we're ready to start receiving data
        model.addView(this);
    }
    public void updateProperty_view(){
        if (model.canvas_selected || model.start_removing || model.ready_to_fill || model.start_drawing) {
            for (int i = 0; i < model.current_shape.size(); ++i) {
                    model.current_shape.get(i).setEffect(null);
            }
        } else {
            for (int i = 0; i < model.current_shape.size(); ++i) {
                if (i == model.shape_index && model.selected != null) {
                    model.current_shape.get(i).setEffect(selected_shadow);
                } else {
                    model.current_shape.get(i).setEffect(null);
                }
            }
        }
        if (!scale_start) {
            scale_button.setCenterX(5000.0);
        }


    }
    // When notified by the model that things have changed,
    // update to display the new value
    public void updateView() {
        System.out.println("View4: updateView" + model.current_shape.size() );

        if (model.current_shape.size() != 0) {
            this.getChildren().add(model.current_shape.get(model.current_shape.size()-1));
            int index = model.current_shape.size()-1;

            //select shape


            model.current_shape.get(index).setOnMouseClicked(e -> {
                model.canvas_selected = false;
                //for the movement later
                if (model.start_selecting) {
                    model.disable_button = false;
                    model.origin_x_cor = e.getX();
                    model.origin_y_cor = e.getY();
                    model.selected = model.current_shape.get(index);
                    model.shape_index = index;
                    model.update_property();
                    String shape_name = model.list_of_shape_name.get(index);
                    System.out.println("shape name" + shape_name);
                    if (shape_name == "Line") {
                        System.out.println("Line start x"+ ((Line) model.selected).getStartX() + "Line start x"+ ((Line) model.selected).getStartY());
                        scale_button.setCenterX(((Line) model.selected).getEndX() + model.list_translate_x.get(index));
                        scale_button.setCenterY(((Line) model.selected).getEndY() + model.list_translate_y.get(index));
                    } else if (shape_name == "Rectangle") {
                        System.out.println("Rectangle X"+ ((Rectangle) model.selected).getX() + "Rectangle Y" + ((Rectangle) model.selected).getY());
                        scale_button.setCenterX(((Rectangle) model.selected).getX() + model.list_scale_x.get(model.shape_index)*
                                ((Rectangle) model.selected).getWidth()+ model.list_translate_x.get(index));
                        scale_button.setCenterY(((Rectangle) model.selected).getY() + model.list_scale_y.get(model.shape_index)*
                                ((Rectangle) model.selected).getHeight()+ model.list_translate_y.get(index));
                    } else if (shape_name == "Circle") {
                        System.out.println("Circle radius"+ ((Circle) model.selected).getRadius());
                        scale_button.setCenterX(((Circle) model.selected).getCenterX() + model.list_scale_x.get(model.shape_index)*
                                ((Circle) model.selected).getRadius()+ model.list_translate_x.get(index));
                        scale_button.setCenterY(((Circle) model.selected).getCenterY() + model.list_scale_y.get(model.shape_index)*
                                ((Circle) model.selected).getRadius()+ model.list_translate_y.get(index));
                    }

                    model.ready_to_scale = true;
                    System.out.println("selecting a shape");
                    System.out.println("shape index" + index);
                } else if (model.ready_to_fill) {
                    model.disable_button = false;
                    model.current_shape.get(index).setFill(model.help_get_color());
                    model.update_property();
                } else if (model.start_removing) {
                    System.out.println("start remove");
                    model.current_shape.get(index).setFill(Color.TRANSPARENT);
                    model.current_shape.get(index).setStroke(Color.TRANSPARENT);
                    model.current_shape.get(index).setTranslateX(+1000);
                    model.current_shape.get(index).setTranslateY(+1000);
                }
            });
            model.current_shape.get(index).setOnMouseDragged(e -> {

                if (model.start_selecting && model.ready_to_move) {
                    //If drage, update the movement of the shape if the object is selected and ready to move
                    model.current_shape.get(index).setTranslateX(-model.origin_x_cor  + model.current_shape.get(index).getTranslateX()+ e.getX());
                    model.current_shape.get(index).setTranslateY(-model.origin_y_cor  + model.current_shape.get(index).getTranslateY() + e.getY());
                    model.list_translate_x.set(index,-model.origin_x_cor  + model.current_shape.get(index).getTranslateX()+ e.getX());
                    model.list_translate_y.set(index,-model.origin_y_cor  + model.current_shape.get(index).getTranslateY() + e.getY());
                    String shape_name = model.list_of_shape_name.get(index);
                    System.out.println("shape name" + shape_name);
                    if (shape_name == "Line") {
                        System.out.println("Line start x"+ ((Line) model.selected).getStartX() + "Line start x"+ ((Line) model.selected).getStartY());
                        scale_button.setCenterX(((Line) model.selected).getEndX() + model.list_translate_x.get(index));
                        scale_button.setCenterY(((Line) model.selected).getEndY() + model.list_translate_y.get(index));
                    } else if (shape_name == "Rectangle") {
                        System.out.println("Rectangle X"+ ((Rectangle) model.selected).getX() + "Rectangle Y" + ((Rectangle) model.selected).getY());
                        scale_button.setCenterX(((Rectangle) model.selected).getX() + model.list_scale_x.get(model.shape_index)*
                                ((Rectangle) model.selected).getWidth()+ model.list_translate_x.get(index));
                        scale_button.setCenterY(((Rectangle) model.selected).getY() + model.list_scale_y.get(model.shape_index)*
                                ((Rectangle) model.selected).getHeight()+ model.list_translate_y.get(index));
                    } else if (shape_name == "Circle") {
                        System.out.println("Circle radius"+ ((Circle) model.selected).getRadius());
                        scale_button.setCenterX(((Circle) model.selected).getCenterX() + model.list_scale_x.get(model.shape_index)*
                                ((Circle) model.selected).getRadius()+ model.list_translate_x.get(index));
                        scale_button.setCenterY(((Circle) model.selected).getCenterY() + model.list_scale_y.get(model.shape_index)*
                                ((Circle) model.selected).getRadius()+ model.list_translate_y.get(index));
                    }
                    e.consume();

                }
            });
            model.current_shape.get(index).setOnMouseReleased(e -> {
                if (model.start_selecting && !model.ready_to_move ) {
                    //if the movement is finished, stopped the movement
                    if (model.start_selecting) {
                        model.ready_to_move = true;
                    }
                }
            });


        }

    }
    public void deattach() {
        if (this.getChildren().size() != 0) {
            this.getChildren().remove(this.getChildren().size()-1);
        }
    }
    public WritableImage getImage() {
        WritableImage colouredImage = new WritableImage((int) 1000, (int) 790);
        return canvas.snapshot(new SnapshotParameters(), colouredImage);
    }
    public Color return_color() {return null;}
}
