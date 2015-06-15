/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kjtw.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

import org.kjtw.process.JGLoad;
import org.kjtw.resources.YDKJPalettes;

/**
 * FXML Controller class
 *
 * @author James
 */
public class SegueFXML implements Initializable {

    @FXML // fx:id="Palettecode"
    private ChoiceBox<String> Palettecode; // Value injected by FXMLLoader
    @FXML // fx:id="Segbank"
    private ChoiceBox<Integer> Segbank; // Value injected by FXMLLoader
    @FXML // fx:id="Segnum"
    private ChoiceBox<Integer> Segnum; // Value injected by FXMLLoader
    @FXML // fx:id="Playbut"
    private Button Playbut; // Value injected by FXMLLoader

    @FXML // fx:id="gfx"
    private Pane gfx; // Value injected by FXMLLoader

    private String palette;
    private int bank;
    private int number;
    Hashtable<String,String> palettelist;
    JGLoad jg;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		// create layers
        gfx.setStyle("-fx-background-color: #000000;");
        try {
        	File file = null;
        	String indir = null;
        	do
        	{
		        JFileChooser chooser = new JFileChooser("C:\\ydkj");
		        chooser.setDialogTitle("Open a QNUMBERS.SRF");
		        chooser.setFileFilter(new FileNameExtensionFilter("QNUMBERS.SRF", "srf"));
		        chooser.showOpenDialog(null);
		        file = chooser.getSelectedFile();
		        indir = file.getPath();
        	}
        	while (!file.getName().toLowerCase().equals("qnumbers.srf"));        	
			jg = new JGLoad(indir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        new YDKJPalettes();
        palettelist = YDKJPalettes.getPalettes();
        assert Palettecode != null : "fx:id=\"Palettecode\" was not injected: check your FXML file.";

        Palettecode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue != null) {
        palette = palettelist.get(newValue); 
          }
        }  
    });
        
        assert Segbank != null : "fx:id=\"Segbank\" was not injected: check your FXML file.";

        Segbank.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() 
        {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
            if (newValue != null) {
            bank = newValue;
          }
        }  
    });

        assert Segnum != null : "fx:id=\"Segnum\" was not injected: check your FXML file.";

        Segnum.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() 
        {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
            if (newValue != null) {
            number = newValue;
          }
        }  
    });

     Playbut.setOnAction(new EventHandler<ActionEvent>() {

          @Override
           public void handle(ActionEvent event) {
               System.out.println("processing gfx");
        	int gfxval = (-1000 * bank) - (number*10);
           int sndval = (number * 1000) + (bank + 1);

               System.out.println("setting gfx");
           
          jg.setRes((short) gfxval,(short)sndval,palette);
		  gfx.getChildren().clear();
                                 System.out.println("starting anim");

		  Segue animation = new Segue( gfx, jg.getGfx(), 400/6,jg.getSnd().toWav());  
                                 System.out.println("playing anim");

		  animation.play();  

            }
        });
} 

}
