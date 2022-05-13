package ca.uwaterloo.cs349;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

class View3 extends Pane implements IView {

    //private TextArea text = new TextArea("");
    private Model model; // reference to the model
    HBox color = new HBox();
    ColorPicker colorPicker_line = new ColorPicker();
    ColorPicker colorPicker_shape = new ColorPicker();
    HBox line_thickness = new HBox();
    HBox line_type = new HBox();
    VBox tool_bar = new VBox();

    ToggleGroup group_1 = new ToggleGroup();
    Image thick_1_img = new Image("thick_1.jpg", 30, 30, false, true);
    Image thick_2_img = new Image("thick_2.jpg", 30, 30, false, true);
    Image thick_3_img = new Image("thick_3.jpg", 30, 30, false, true);
    Image thick_4_img = new Image("thick_4.jpg", 30, 30, false, true);
    ToggleButton thick_1 = new ToggleButton("",new ImageView(thick_1_img));
    ToggleButton thick_2 = new ToggleButton("",new ImageView(thick_2_img));
    ToggleButton thick_3 = new ToggleButton("",new ImageView(thick_3_img));
    ToggleButton thick_4 = new ToggleButton("",new ImageView(thick_4_img));

    ToggleGroup group_2 = new ToggleGroup();
    Image style_1_img = new Image("style1.jpg", 30, 30, false, true);
    Image style_2_img = new Image("style2.jpg", 30, 30, false, true);
    Image style_3_img = new Image("style3.jpg", 30, 30, false, true);
    Image style_4_img = new Image("style4.jpg", 30, 30, false, true);
    ToggleButton type_1 = new ToggleButton("",new ImageView(style_1_img));
    //ToggleButton type_2 = new ToggleButton("",new ImageView(style_2_img));
    ToggleButton type_2 = new ToggleButton("Empty");
    ToggleButton type_3 = new ToggleButton("",new ImageView(style_3_img));
    ToggleButton type_4 = new ToggleButton("",new ImageView(style_4_img));


    View3(Model model) {
        // keep track of the model
        this.model = model;
        colorPicker_shape.setValue(Color.BLACK);
        colorPicker_line.setValue(Color.BLACK);
        colorPicker_line.setPrefSize(100, 100);
        colorPicker_shape.setPrefSize(100, 100);
        color.getChildren().addAll(colorPicker_line,colorPicker_shape);

        thick_1.setToggleGroup(group_1);
        thick_2.setToggleGroup(group_1);
        thick_3.setToggleGroup(group_1);
        thick_4.setToggleGroup(group_1);
        thick_1.setPrefSize(50, 50);
        thick_2.setPrefSize(50, 50);
        thick_3.setPrefSize(50, 50);
        thick_4.setPrefSize(50, 50);
        line_thickness.getChildren().addAll(thick_1,thick_2,thick_3,thick_4);

        type_1.setToggleGroup(group_2);
        type_2.setToggleGroup(group_2);
        type_3.setToggleGroup(group_2);
        type_4.setToggleGroup(group_2);
        type_1.setPrefSize(50, 50);
        type_2.setPrefSize(50, 50);
        type_3.setPrefSize(50, 50);
        type_4.setPrefSize(50, 50);
        line_type.getChildren().addAll(type_1,type_2,type_3,type_4);

        tool_bar.getChildren().addAll(color,line_thickness,line_type);

        //tool_bar.setAlignment(Pos.BASELINE_LEFT);

        colorPicker_shape.setOnAction(e -> {
            System.out.println(" colorPicker_shape.getValue()");
            model.shape_color = colorPicker_shape.getValue();
            if (model.ready_to_move) {
                model.selected.setFill(colorPicker_shape.getValue());
            }
            e.consume();   // this will stop the event from being passed along
        });
        colorPicker_line.setOnAction(e -> {
            System.out.println(" colorPicker_line.getValue()");
            model.line_color = colorPicker_line.getValue();
            if (model.ready_to_move) {
                model.selected.setStroke(colorPicker_line.getValue());
            }
            e.consume();   // this will stop the event from being passed along
        });
        thick_1.setOnAction(e -> {
            System.out.println(" thick_1.getValue()");
            model.strokeWidth = 1;
            if (model.ready_to_move) {
                model.selected.setStrokeWidth(model.strokeWidth);
            }
            e.consume();   // this will stop the event from being passed along
        });
        thick_2.setOnAction(e -> {
            System.out.println(" thick_2.getValue()");
            model.strokeWidth = 5;
            if (model.ready_to_move) {
                model.selected.setStrokeWidth(model.strokeWidth);
            }
            e.consume();   // this will stop the event from being passed along
        });
        thick_3.setOnAction(e -> {
            System.out.println(" thick_3.getValue()");
            model.strokeWidth = 10;
            if (model.ready_to_move) {
                model.selected.setStrokeWidth(model.strokeWidth);
            }
            e.consume();   // this will stop the event from being passed along
        });
        thick_4.setOnAction(e -> {
            System.out.println(" thick_4.getValue()");
            model.strokeWidth = 20;
            if (model.ready_to_move) {
                model.selected.setStrokeWidth(model.strokeWidth);
            }
            e.consume();   // this will stop the event from being passed along
        });
        type_1.setOnAction(e -> {
            System.out.println(" thick_1.getValue()");
            model.line_type = 0;
            if (model.ready_to_move) {
                model.stroke_type();
            }
            e.consume();   // this will stop the event from being passed along
        });
        type_2.setOnAction(e -> {
            System.out.println(" thick_2.getValue()");
            model.line_type = 1;
            if (model.ready_to_move) {
                model.stroke_type();
            }
            e.consume();   // this will stop the event from being passed along
        });
        type_3.setOnAction(e -> {
            System.out.println(" thick_3.getValue()");
            model.line_type = 2;
            if (model.ready_to_move) {
                model.stroke_type();
            }
            e.consume();   // this will stop the event from being passed along
        });
        type_4.setOnAction(e -> {
            System.out.println(" thick_4.getValue()");
            model.line_type = 3;
            if (model.ready_to_move) {
                model.stroke_type();
            }
            e.consume();   // this will stop the event from being passed along
        });


        // set label properties
		/*text.setMaxSize(HelloMVC3.WINDOW_WIDTH, HelloMVC3.WINDOW_HEIGHT/2);
		text.setWrapText(true);*/

        // the previous controller code will just be handled here
        // we don't need always need a separate controller class!
		/*text.setOnMouseClicked(mouseEvent -> {
			System.out.println("Controller: changing Model (actionPerformed)")
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
        System.out.println("View3: updateView");

        // display an 'X' for each counter value
		/*StringBuilder s = new StringBuilder();
		for (int i=0; i<this.model.getCounterValue(); i++)
			s.append("X");
		this.text.setText(s.toString());
		*
		 */
    }
    public void updateProperty_view(){
        //System.out.println("I am trying to update the color");
        //colorPicker_shape.setValue(model.list_of_shape_color.get(model.shape_index));
        //		colorPicker_line.setValue(model.list_of_line_color.get(model.shape_index));




        if (model.disable_button) {
            group_2.getToggles().forEach(toggle -> {
                    Node node = (Node) toggle ;
                node.setDisable(true);
            });
            group_1.getToggles().forEach(toggle -> {
                Node node = (Node) toggle ;
                node.setDisable(true);
            });

        } else {
            group_2.getToggles().forEach(toggle -> {
                Node node = (Node) toggle ;
                node.setDisable(false);
            });
            group_1.getToggles().forEach(toggle -> {
                Node node = (Node) toggle ;
                node.setDisable(false);
            });
        }
        if (model.disable_color_line) {
            colorPicker_line.setDisable(true);
        } else {
            colorPicker_line.setDisable(false);
        }
        if (model.disable_color_shape) {
            colorPicker_shape.setDisable(true);
        } else {
            colorPicker_shape.setDisable(false);
        }

        if (model.selected != null) {
            Color current_shape = (Color) model.selected.getFill();
            Color current_line = (Color) model.selected.getStroke();
            System.out.println("current_shape" + current_shape);
            System.out.println("current_line" + current_line);

            colorPicker_shape.setValue((Color) model.selected.getFill());
            colorPicker_line.setValue((Color) model.selected.getStroke());
            if (model.list_of_thickness.size() != 0) {
                if (model.list_of_line_type.get(model.shape_index) == 0) {
                    type_1.setSelected(true);
                    type_2.setSelected(false);
                    type_3.setSelected(false);
                    type_4.setSelected(false);
                } else if (model.list_of_line_type.get(model.shape_index) == 1) {
                    type_1.setSelected(false);
                    type_2.setSelected(true);
                    type_3.setSelected(false);
                    type_4.setSelected(false);
                } else if (model.list_of_line_type.get(model.shape_index) == 2) {
                    type_1.setSelected(false);
                    type_2.setSelected(false);
                    type_3.setSelected(true);
                    type_4.setSelected(false);
                } else if (model.list_of_line_type.get(model.shape_index) == 3) {
                    type_1.setSelected(false);
                    type_2.setSelected(false);
                    type_3.setSelected(false);
                    type_4.setSelected(true);
                }
                if (model.list_of_thickness.get(model.shape_index) == 0) {
                    thick_1.setSelected(true);
                    thick_2.setSelected(false);
                    thick_3.setSelected(false);
                    thick_4.setSelected(false);
                } else if (model.list_of_thickness.get(model.shape_index) == 1) {
                    thick_1.setSelected(false);
                    thick_2.setSelected(true);
                    thick_3.setSelected(false);
                    thick_4.setSelected(false);
                } else if (model.list_of_thickness.get(model.shape_index) == 2) {
                    thick_1.setSelected(false);
                    thick_2.setSelected(false);
                    thick_3.setSelected(true);
                    thick_4.setSelected(false);
                } else if (model.list_of_thickness.get(model.shape_index) == 3) {
                    thick_1.setSelected(false);
                    thick_2.setSelected(false);
                    thick_3.setSelected(false);
                    thick_4.setSelected(true);
                }

            }
        }

    }
    public void deattach() {}
    public WritableImage getImage() {return null;}
    public Color return_color() {return colorPicker_shape.getValue();}
}
