package org.kjtw.main;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.kjtw.process.SRFProcess;
import org.kjtw.resources.YDKJPalettes;
import org.kjtw.structures.CCCOut;
import org.kjtw.structures.DisorDatOut;
import org.kjtw.structures.FiberOpticOut;
import org.kjtw.structures.FillintheBlankOut;
import org.kjtw.structures.GameTemplate;
import org.kjtw.structures.GibberishOut;
import org.kjtw.structures.GuestHostQuestionOut;
import org.kjtw.structures.JackAttackOut;
import org.kjtw.structures.JackGraphic;
import org.kjtw.structures.PictureQuestionOut;
import org.kjtw.structures.QHeader;
import org.kjtw.structures.QHeaderout;
import org.kjtw.structures.ShortieQuestionOut;
import org.kjtw.structures.SuperAudioQuestionout;
import org.kjtw.structures.ThreeWayOut;
import org.kjtw.structures.WhatshisnameOut;
import org.kjtw.structures.YDKJQ;

import com.google.gson.Gson;
import com.kreative.ksfl.KSFLUtilities;
import com.kreative.rsrc.BerkeleyResourceFile;
import com.kreative.rsrc.MacResource;
import com.kreative.rsrc.MacResourceFile;
import com.kreative.rsrc.SoundResource;

import javax.swing.JProgressBar;
import javax.swing.JComboBox;
 
public class BatchProcessor extends JPanel
                             implements ActionListener, 
                                        PropertyChangeListener {
 
    private JButton startButton;
    private JTextArea taskOutput;
    private Task task;
    private JLabel lblPaletteusEngine;
    private JButton button;
  	private static String dirin;
  	private static String dirout;
  	private String pal;
  	private JProgressBar progressBar;
	static ArrayList<String> fileNames;
	static ArrayList<String> qhnames;
	static ArrayList<QHeader> qheads;
	private JComboBox comboBox;

	class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
        	fileNames.clear();
        	qhnames.clear();        	
        	qheads.clear();
        	ExecutorService es = Executors.newFixedThreadPool(10);

            taskOutput.append("Scanning for files\n");
        	Path in = Paths.get(dirin);
        	getFileNames(in);
            
            int progress = 0;
            int total = fileNames.size();
            //Initialize progress property.
            setProgress(progress/total);
//            taskOutput.append("Processing "+total+"files\n");

            taskOutput.append("Processing question data\n");
            
            for (final String file : qhnames)
            {
                es.submit(new Runnable() {
                    public void run() {
                        procq(file);
                    }
                 });
//                progress++;
//                setProgress(progress/total);
            }
            es.shutdown();
            try {
				es.awaitTermination(1, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            taskOutput.append("Processing "+qheads.size()+"questions\n");
            
            for (final QHeader qh: qheads)
            {
                es.submit(new Runnable() {
                    public void run() {
            	try {
					QHProc(qh);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    }
                 });
//                progress++;
//                setProgress(progress/total);
            }

            for (final String file : fileNames)
            {
                es.submit(new Runnable() {
                    public void run() {
                        procfile(file);
                    }
                 });
//                progress++;
//                setProgress(progress/total);
            }
            es.shutdown();
            try {
				es.awaitTermination(1, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            es.shutdown();
            try {
				es.awaitTermination(1, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return null;
        }

    	protected void procq(String file) {
            taskOutput.append("Processing file"+file+"\n");
    		File f = new File(file);
            SRFProcess srfp = null;
    		try {
    			srfp = new SRFProcess(f);
    		} catch (IOException e) {
                taskOutput.append("Error detected with "+file+" processing, may not be fatal, carrying on\n");
    		}
            String suff = file.replace(dirin, "");
            suff = suff.substring(0, suff.length()-4);
            String out = dirout + File.separator +suff;
            try {
    			QSave(out, srfp,f);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
                taskOutput.append("Error detected with "+file+" saving, may not be fatal, carrying on\n");
    		}		
            taskOutput.append(file+"complete\n");
		}

		private void QSave(String out, SRFProcess srfp, File f) 
		{

	        Hashtable<String, byte[]> data = srfp.getData();
	        Hashtable<String, String> parents = srfp.getParents();
	   		BerkeleyResourceFile rp = srfp.getBRF();
	        Enumeration<String> enumKey = data.keys();
			List<QHeaderout> qh = new ArrayList<QHeaderout>();
			boolean firstpass=true;
			while(enumKey.hasMoreElements()) {			
		        String key = enumKey.nextElement();
		        String nametype = key.substring(0, key.indexOf('_'));
		    	int ftype = KSFLUtilities.fcc(nametype);
		    	File typedir = new File (dirout+File.separator+nametype);
		        String type = parents.get(nametype);
		   		typedir.mkdirs();
		        if (type.equals("qheader"))
		        {
		        	if (firstpass ==true)
		        	{
		            	int ftypex = KSFLUtilities.fcc("qhdr");
		        		
						for (int id2 : rp.getfullIDs(ftype)) 
						{
							MacResource r = rp.getFromFullID(ftypex, id2);
							QHeader qh1 = new QHeader(id2,r.data,f);
							qh.add(new QHeaderout (qh1,true));
							qheads.add(qh1);
						}
						firstpass = false;
		        	}					
		        }
			}
		    if (qh.size() > 0)
		    {
		       try {
		            File output = new File(dirout, ".json");
		            if (!output.exists())
		            {
		                output.createNewFile();
		        		Gson gson = new Gson();
		        		String json = gson.toJson(qh); 
		                PrintWriter pout = new PrintWriter(output);
		                pout.print(json);
		                pout.close();
		            }
		        } catch (IOException e) {
		            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
		        }
		        
		
		    }
		}

		protected void procfile(String file) {
            taskOutput.append("Processing file"+file+"\n");
    		File f = new File(file);
            SRFProcess srfp = null;
    		try {
    			srfp = new SRFProcess(f);
    		} catch (IOException e) {
                taskOutput.append("Error detected with "+file+" processing, may not be fatal, carrying on\n");
    		}
            String suff = file.replace(dirin, "");
            suff = suff.substring(0, suff.length()-4);
            String out = dirout + File.separator +suff;
            try {
    			SRFSave(out, srfp,f);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
                taskOutput.append("Error detected with "+file+" saving, may not be fatal, carrying on\n");
    		}		
            taskOutput.append(file+"complete\n");
		}


        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            taskOutput.append("Done!\n");
        }
    }
 
    public BatchProcessor() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{116, 58, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{33, 0, 227, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
                      
                      lblPaletteusEngine = new JLabel("Palette");
                      GridBagConstraints gbc_lblPaletteusEngine = new GridBagConstraints();
                      gbc_lblPaletteusEngine.insets = new Insets(0, 0, 5, 5);
                      gbc_lblPaletteusEngine.gridx = 0;
                      gbc_lblPaletteusEngine.gridy = 0;
                      add(lblPaletteusEngine, gbc_lblPaletteusEngine);
                      
                      button = new JButton("Input Directory");
                      button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							SetInDirectory();
                      	}
                      });
                      
              		String[] palettes = { "YDKJ 1", "YDKJ 2", "YDKJ 3", "YDKJ 4 (The Ride)", "HeadRush", "Offline", "Louder! Faster! Funnier!","Movies/TV/Sports" };

                      comboBox = new JComboBox(palettes);
              		comboBox.setSelectedIndex(1);
            		comboBox.addActionListener(new ActionListener() {
            	    public void actionPerformed(ActionEvent arg0) {
            	    	JComboBox cb = (JComboBox)arg0.getSource();
            	        String p = (String)cb.getSelectedItem();
            	        
            	        new YDKJPalettes();
						pal = YDKJPalettes.getPalettes().get(p);
            	    }
            	       });
            		
            		GridBagConstraints gbc_comboBox = new GridBagConstraints();
                      gbc_comboBox.gridwidth = 6;
                      gbc_comboBox.insets = new Insets(0, 0, 5, 5);
                      gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
                      gbc_comboBox.gridx = 1;
                      gbc_comboBox.gridy = 0;
                      add(comboBox, gbc_comboBox);
                      GridBagConstraints gbc_button = new GridBagConstraints();
                      gbc_button.anchor = GridBagConstraints.WEST;
                      gbc_button.insets = new Insets(0, 0, 5, 5);
                      gbc_button.gridx = 0;
                      gbc_button.gridy = 1;
                      add(button, gbc_button);
                      
                      progressBar = new JProgressBar();
                      GridBagConstraints gbc_progressBar = new GridBagConstraints();
                      gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
                      gbc_progressBar.gridwidth = 6;
                      gbc_progressBar.insets = new Insets(0, 0, 5, 5);
                      gbc_progressBar.gridx = 1;
                      gbc_progressBar.gridy = 1;
                      add(progressBar, gbc_progressBar);
               
                      //Create the demo's UI.
                      startButton = new JButton("Start");
                      GridBagConstraints gbc_startButton = new GridBagConstraints();
                      gbc_startButton.insets = new Insets(0, 0, 5, 0);
                      gbc_startButton.gridx = 7;
                      gbc_startButton.gridy = 1;
                      add(startButton, gbc_startButton);
                      startButton.setActionCommand("start");
                      startButton.addActionListener(this);
                       
               taskOutput = new JTextArea(5, 20);
               taskOutput.setMargin(new Insets(5,5,5,5));
               taskOutput.setEditable(false);
               GridBagConstraints gbc = new GridBagConstraints();
               gbc.gridwidth = 8;
               gbc.fill = GridBagConstraints.BOTH;
               gbc.gridx = 0;
               gbc.gridy = 2;
               JScrollPane scrollPane = new JScrollPane(taskOutput);
               add(scrollPane, gbc);
        setBorder(null);
 
    }
 
    public void QHProc(QHeader qh) throws IOException {
        taskOutput.append("Processing Question"+qh.getName()+"\n");
        File f = new File(qh.getPath());
        String suff = f.getAbsolutePath().replace(dirin, "");
        suff = suff.substring(0, suff.length()-4);
        String wout = dirout + File.separator +suff;
    	Gson gson = new Gson();

    	YDKJQ q=null;
        switch (qh.getType())
        {
        	case 0:
        	{
        		switch (qh.getSubType())
        		{
	    			case 1:
	    			{
	    				q = new ShortieQuestionOut(wout,qh);	
	    				break;
	    			}
	    			case 2:
	    			{
	    				q = new FillintheBlankOut(wout,qh);
	    				break;
	    			}
	    			case 3:
	    			{
	    				q = new WhatshisnameOut(wout,qh);
	    				break;
	    			}
	    			case 4:
	    			{
	    				q = new PictureQuestionOut(wout, qh,pal);
	    				break;
	    			}
	    			case 5:
	    			{
	    				q = new SuperAudioQuestionout(wout,qh);
	    				break;
	    			}
	    			case 6:
	    			{
	    				q = new GuestHostQuestionOut(wout,qh,pal);
	    			}
        		}
        		break;
        	}
		case 1:
		{
			break;			
		}
		case 2:
		{
			q = new GibberishOut(wout,qh);
			break;
		}
		case 3:
		{
			q = new DisorDatOut(wout,qh);
			break;
		}
		case 4:
		{
			q = new JackAttackOut(wout,qh);
			break;
		}
		case 5:
		{
			q = new FiberOpticOut(wout,qh);
			break;
		}
		case 10:
		{
			q = new CCCOut(wout,qh,pal);
			break;
		}
		case 12:
		{
			q = new ThreeWayOut(wout,qh);
			break;
		}
        }
        try {
            File output = new File(wout, qh.getName()+".json");
            output.createNewFile();
            PrintWriter out = new PrintWriter(output);
            out.print(gson.toJson(q));
            out.close();
        } catch (IOException e) {
            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
        }
        
        fileNames.remove(qh.getPath());
        taskOutput.append(qh.getName()+"complete\n");
		
	}

	protected static void SetInDirectory() {
		String indir="";

        if (dirin == null)
        {
            indir="C:\\ydkj";
        }
        else
        {
            indir=dirin;
        }
        JFileChooser chooser = new JFileChooser(indir);
        chooser.setDialogTitle("Select the root directory (containing the _CD file)");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        dirin = chooser.getSelectedFile().getPath();		
	}

    protected static void SetOutDirectory() {
		String outdir="";

        if (dirout == null)
        {
            outdir="C:\\ydkj";
        }
        else
        {
            outdir=dirin;
        }
        JFileChooser chooser = new JFileChooser(outdir);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        dirout = chooser.getSelectedFile().getPath();		
	}

	/**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
    	if (evt.getActionCommand().equals("start"))
    	{    	
	        startButton.setEnabled(false);
	        SetOutDirectory();
	        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	        //Instances of javax.swing.SwingWorker are not reusuable, so
	        //we create new instances as needed.
	        task = new Task();
	        task.addPropertyChangeListener(this);
	        task.execute();
    	}
    	else
    	{
        	pal = evt.getActionCommand();
    	}
    }
 
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            taskOutput.append(String.format(
                    "Completed %d%% of task.\n", task.getProgress()));
        } 
    }
 
 
    /**
     * Create the GUI and show it. As with all GUI code, this must run
     * on the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("BatchProcessor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        JComponent newContentPane = new BatchProcessor();
        fileNames = new ArrayList<String>();        	
        qhnames = new ArrayList<String>();
        qheads = new ArrayList<QHeader>();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    private List<String> getFileNames(Path dir){
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            for (Path path : stream) {
                if(path.toFile().isDirectory())
                {
                	getFileNames(path);
                }
                else {
                	if (path.getFileName().toString().toLowerCase().contains(".srf"))
                	{
                		String p = path.toAbsolutePath().toString();
                		File f = new File(p);

            			BerkeleyResourceFile rp = null;
            			try {
            				rp = new BerkeleyResourceFile(f, "r", MacResourceFile.CREATE_NEVER);
            			} catch (IOException e) {
            				System.err.println("Error: Invalid file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
            			}
            			boolean qhead=false;
        				for (int type : rp.getTypes()) {
        					String ftype = KSFLUtilities.fccs(type).trim();
        					if (ftype.equals("qhdr"))
        					{
        						qhnames.add(p);
        						qhead=true;
        					}
        				}
        				
        				if (qhead == false)
        				{
        					fileNames.add(p);
        				}
                	}
                }
            }
            stream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return fileNames;
    } 
    
    protected void SRFSave(String dirout, SRFProcess srfp, File f) throws Exception{

        Hashtable<String, byte[]> data = srfp.getData();
        Hashtable<String, String> parents = srfp.getParents();
   		BerkeleyResourceFile rp = srfp.getBRF();
        Enumeration<String> enumKey = data.keys();
		List<QHeaderout> qh = new ArrayList<QHeaderout>();
		List<QHeaderout> qh2 = new ArrayList<QHeaderout>();
		boolean firstpass=true;
		while(enumKey.hasMoreElements()) {			
	        String key = enumKey.nextElement();
	        String nametype = key.substring(0, key.indexOf('_'));
	    	int ftype = KSFLUtilities.fcc(nametype);
	    	File typedir = new File (dirout+File.separator+nametype);
	        String type = parents.get(nametype);
	        String suffix="";
	        byte[] val = null;
	    	String id = key.substring(key.indexOf('_')+1);
	   		typedir.mkdirs();
	        if (type.equals("audio"))
	        {
	        	MacResource r = rp.get(ftype, Short.parseShort(id));
	        	SoundResource rsnd = r.shallowRecast(SoundResource.class);
		            try {
		                File output = new File(typedir, id+".wav");
		                output.createNewFile();
		                FileOutputStream fos = new FileOutputStream(output);
		                fos.write(rsnd.toWav());
		                fos.close();
		            } catch (IOException e) {
		                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
		            }
	        }
	        else if (type.equals("gfx"))
	        {
	
	      	  MacResource r = rp.get(ftype, Short.parseShort(id));
	
	      	  JackGraphic jgfx = new JackGraphic(r.data);
	
	          jgfx.SetPalette(pal);
	      	  File outputimage = new File(typedir, id+".gif");
	
			  try {
				ImageIO.write(jgfx.toGif(jgfx.GetPalette()), "gif", outputimage);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	    			  		
	    		  
	            try {
		                File output = new File(typedir, id+".json");
		                output.createNewFile();
		                PrintWriter out = new PrintWriter(output);
		                out.print(jgfx.getTileJSON());
		                out.print(jgfx.getFrameJSON());
		                out.close();
		            } catch (IOException e) {
		                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
		            }
	
	        }
	        else if (type.equals("template"))
	        {
	       		List<GameTemplate>gt = new ArrayList<GameTemplate>();
	       		for (short id2 : rp.getIDs(ftype))
	       		{
	            	  MacResource r = rp.get(ftype, (short) id2);
	            	  gt.add(new GameTemplate(r.data));
	       		}
	       		
	            try {
	                File output = new File(typedir, nametype+".json");
	                if (!output.exists())
	                {
		                output.createNewFile();
		        		Gson gson = new Gson();
		        		String json = gson.toJson(gt); 
		                PrintWriter out = new PrintWriter(output);
		                out.print(json);
		                out.close();
	                }
	            } catch (IOException e) {
	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	            }
	
	        }
	        else if (type.equals("qheader"))
	        {
	
	        	if (firstpass ==true)
	        	{
	            	int ftypex = KSFLUtilities.fcc("qhdr");
	        		
					for (int id2 : rp.getfullIDs(ftype)) 
					{
						MacResource r = rp.getFromFullID(ftypex, id2);
						QHeader qh1 = new QHeader(id2,r.data,f);
						qh.add(new QHeaderout (qh1,true));
						qheads.add(qh1);
						qh2.add(new QHeaderout (qh1,false));
					}
					firstpass = false;
	        	}					
	        }
	        else
	        {
	        	val = data.get(key);
	        	suffix=".txt";
	        }
	        if (val != null)
	        { 
	            try {
	                File output = new File(typedir, id+suffix);
	                output.createNewFile();
	                FileOutputStream fos = new FileOutputStream(output);
	                fos.write(val);
	                fos.close();
	            } catch (IOException e) {
	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	            }
	        }
	    }
	    if (qh.size() > 0)
	    {
	       try {
	            File output = new File(dirout, ".json");
	            if (!output.exists())
	            {
	                output.createNewFile();
	        		Gson gson = new Gson();
	        		String json = gson.toJson(qh); 
	                PrintWriter out = new PrintWriter(output);
	                out.print(json);
	                out.close();
	            }
	        } catch (IOException e) {
	            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	        }
	        
	        try {
	            File output = new File(dirout, "J.json");
	            if (!output.exists())
	            {
	                output.createNewFile();
	        		Gson gson = new Gson();
	        		String json = gson.toJson(qh2); 
	                PrintWriter out = new PrintWriter(output);
	                out.print(json);
	                out.close();
	            }
	        } catch (IOException e) {
	            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	        }
	
	    }
	}
    

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	SetInDirectory();
                createAndShowGUI();
            }
        });
    }
}