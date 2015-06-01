package org.kjtw.main;

import java.util.ArrayList;
import java.util.List;

import org.kjtw.process.AudioPlayer;
import org.kjtw.structures.JackGraphic;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationBase extends Transition {

 private final ImageView imageView;
 private int count=-1;

 private int lastIndex;
 private boolean start = true;
 private List<Integer> noiseframes;
 private List<AudioPlayer> ap;
 private JackGraphic gfx;
 private byte[] sr;
 private List<Image> jgfxlist;

 public AnimationBase( JackGraphic jg, int startframe, double durationMs, byte[] bs) {
	System.out.println("Going into segue");

         jgfxlist=new ArrayList<Image>();
	 gfx =jg;
	 this.sr = bs;
	 imageView = new ImageView();
         noiseframes = new ArrayList<Integer>();
         ap = new ArrayList<AudioPlayer>();
         
         ap.add(new AudioPlayer(sr));
         System.out.println("Adding audio");
  for (int i=startframe; i < gfx.getFrameSize(); i++)
  {
      System.out.println("adding gfx "+i);
	  jgfxlist.add(gfx.getFrameImg(i));
	  if (gfx.getFrame(i).isNoiseFrame())
	  {
		  noiseframes.add(i);
	  }
	  if (gfx.getFrame(i).isStopFrame())
	  {
		  if (this.count ==-1)
		  {
			  this.count = i+1;
		  }
	  }
  }

  setCycleCount(1);
  setCycleDuration(Duration.millis(durationMs*count));
  setInterpolator(Interpolator.LINEAR);
 }

 protected void interpolate(double k) {
  
  final int index = Math.min((int) Math.floor(k * count), count - 1);
  if (index != lastIndex) {
	  imageView.setImage(jgfxlist.get(index));
	  lastIndex = index;
      System.out.println("playing gfx "+index);
	  
	  if (start)
	  {
                    System.out.println("playing sound");
              ap.get(0).play();
//		new Thread(new AudioPlayer(sr)).start();
		start = false;
	  }
   if (noiseframes.contains(index))
   {
              ap.get(0).play();
//		new Thread(new AudioPlayer(sr)).start();
   }
  }
  
 }
 
 public ImageView getView() {
  return imageView;
 }

}
