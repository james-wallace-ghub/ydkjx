package org.kjtw.main;

import org.kjtw.structures.JackGraphic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

public class Segue extends AnimationBase {

		 public Segue(final Pane layer, JackGraphic jg, double durationMs, byte[] sr) {

		  super(jg, 0, durationMs,sr);
		  // make explosion visible on screen
		  layer.getChildren().add( getView());
		  // remove objects when animation finishes
		  setOnFinished(new EventHandler<ActionEvent>() {

		   public void handle(ActionEvent event) {

		    // stop animation
		    stop();
		   }
		  });

		}
}
