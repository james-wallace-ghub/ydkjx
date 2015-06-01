package org.kjtw.main;

import org.kjtw.process.JGLoad;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SegueTest extends Application {

	Pane segLayer;
	@Override
	public void start(Stage primaryStage) {
		try {
		// create root node
		  Group root = new Group();
		  
		  // create layers
		  segLayer = new Pane();
		// add layers to scene root
		  root.getChildren().add( segLayer);
		  
		// create scene
		  Scene scene = new Scene( root, 640, 480, Color.BLACK);
		  
		  // show stage
		  primaryStage.setScene(scene);
		  primaryStage.show();
		  
		  JGLoad jg = (new JGLoad("C:\\JackboxGames\\YOU DON'T KNOW JACK V2\\J2Itms\\QNUMBERS.SRF",(short) -3060,(short)6004,"org/kjtw/resources/YDKJ2PAL.bmp"));
		  
		  Segue animation = new Segue( segLayer, jg.getGfx(), 400/6,jg.getSnd());  
		  animation.play();  
	}
		catch(Exception e) {
			  e.printStackTrace();
			 }
			}
			 

	public static void main(String[] args) {
		launch(args);
	}
}
