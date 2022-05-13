package ca.uwaterloo.cs349;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

interface IView {
    void updateView();
    void  deattach();
    WritableImage getImage();
    void updateProperty_view();
    Color return_color() ;
}