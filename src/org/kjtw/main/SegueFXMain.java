/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kjtw.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author James
 */
public class SegueFXMain extends Application {
    
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(SegueFXMain.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            TitledPane page = (TitledPane) FXMLLoader.load(SegueFXMain.class.getResource("FXML.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(SegueFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}