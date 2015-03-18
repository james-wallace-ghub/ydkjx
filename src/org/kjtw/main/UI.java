package org.kjtw.main;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.kjtw.displays.JackGFX;
import org.kjtw.displays.JackQheader;
import org.kjtw.displays.JackTemplate;
import org.kjtw.process.AudioPlayer;
import org.kjtw.process.SRFProcess;
import org.kjtw.structures.GameTemplate;
import org.kjtw.structures.JackGraphic;
import org.kjtw.structures.QHeader;
import org.kjtw.structures.QHeaderout;

import com.google.gson.Gson;
import com.kreative.ksfl.KSFLUtilities;
import com.kreative.rsrc.BerkeleyResourceFile;
import com.kreative.rsrc.MacResource;
import com.kreative.rsrc.SoundResource;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JScrollPane;

import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ScrollPaneConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

public class UI implements TreeSelectionListener, ActionListener {

    private JFrame frmYdkjExtractor;
    static JTree tree;
    static File file;
    static String indir = null;    
    static String dir = null;
    static String filenameunq ="";
    SRFProcess srfp = null;
    static String currentselect="";
    JScrollPane scrollPane;
    boolean playCompleted;
    String AIFCmode = "AIFC";
	private JackGFX gfxpanel;
	private GridBagConstraints gbc_gfxpanel;
	JackGraphic jgfx= null;
	private JMenuItem mntmSaveJsanimationInfo;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UI window = new UI();
                    window.frmYdkjExtractor.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public UI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmYdkjExtractor = new JFrame();
        frmYdkjExtractor.setTitle("YDKJ SRF Extractor");
        frmYdkjExtractor.setBounds(100, 100, 1162, 741);
        frmYdkjExtractor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{200, 796, 0};
        gridBagLayout.rowHeights = new int[]{24, 600, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        frmYdkjExtractor.getContentPane().setLayout(gridBagLayout);
        
        JLabel lblResourcesInSrf = new JLabel("Resources in SRF");
        GridBagConstraints gbc_lblResourcesInSrf = new GridBagConstraints();
        gbc_lblResourcesInSrf.insets = new Insets(0, 0, 5, 5);
        gbc_lblResourcesInSrf.gridx = 0;
        gbc_lblResourcesInSrf.gridy = 0;
        frmYdkjExtractor.getContentPane().add(lblResourcesInSrf, gbc_lblResourcesInSrf);
        
        final JLabel lblSavingPleaseWait = new JLabel("Select a resource to preview it  (sounds will autoplay)");
        GridBagConstraints gbc_lblSavingPleaseWait = new GridBagConstraints();
        gbc_lblSavingPleaseWait.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblSavingPleaseWait.weighty = 1.0;
        gbc_lblSavingPleaseWait.weightx = 1.0;
        gbc_lblSavingPleaseWait.insets = new Insets(0, 0, 5, 0);
        gbc_lblSavingPleaseWait.gridx = 1;
        gbc_lblSavingPleaseWait.gridy = 0;
        frmYdkjExtractor.getContentPane().add(lblSavingPleaseWait, gbc_lblSavingPleaseWait);
        
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.weighty = 1.0;
        gbc_scrollPane.weightx = 1.0;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        frmYdkjExtractor.getContentPane().add(scrollPane, gbc_scrollPane);
        
        makeTree();
        
        gfxpanel = new JackGFX();
        gbc_gfxpanel = new GridBagConstraints();
        gbc_gfxpanel.fill = GridBagConstraints.BOTH;
        gbc_gfxpanel.gridx = 1;
        gbc_gfxpanel.gridy = 1;
        frmYdkjExtractor.getContentPane().add(gfxpanel, gbc_gfxpanel);

        JMenuBar menuBar = new JMenuBar();
        frmYdkjExtractor.setJMenuBar(menuBar);
        
        JMenu mnFile = new JMenu("File I/O");
        menuBar.add(mnFile);
        
        JMenuItem mntmLoadNewSrf = new JMenuItem("Load New SRF");
        mnFile.add(mntmLoadNewSrf);
        mntmLoadNewSrf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrollPane.setVisible(false);
                makeTree();
            }
        });

        JMenuItem mntmSetOutputDirectory = new JMenuItem("Set Output Directory");
        mnFile.add(mntmSetOutputDirectory);
        mntmSetOutputDirectory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SRFSetOutDirectory();
            }
        });

        JMenu mnAudioOptions = new JMenu("Exported Audio Format");
        menuBar.add(mnAudioOptions);
        
        JRadioButtonMenuItem rdbtnmntmNewRadioItem = new JRadioButtonMenuItem("PCM (WAV only)");
        mnAudioOptions.add(rdbtnmntmNewRadioItem);
        
        ButtonGroup group = new ButtonGroup();
        rdbtnmntmNewRadioItem.setActionCommand("PCM");
        group.add(rdbtnmntmNewRadioItem);
        rdbtnmntmNewRadioItem.addActionListener(this);

        JRadioButtonMenuItem rdbtnmntmNativeaifcWav = new JRadioButtonMenuItem("Native (AIFC, WAV)");
        mnAudioOptions.add(rdbtnmntmNativeaifcWav);
        rdbtnmntmNativeaifcWav.setSelected(true);
        rdbtnmntmNativeaifcWav.setActionCommand("AIFC");
 
        //Register a listener for the radio buttons.
        rdbtnmntmNativeaifcWav.addActionListener(this);
        group.add(rdbtnmntmNativeaifcWav);
        
        JMenu mnSaveItems = new JMenu("Save Items");
        menuBar.add(mnSaveItems);
        
        JMenuItem mntmSaveSelected = new JMenuItem("Save Selected");
        mnSaveItems.add(mntmSaveSelected);
        mntmSaveSelected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblSavingPleaseWait.setText("Saving, Please Wait");
                SRFSave1(currentselect);
                lblSavingPleaseWait.setText("Select a resource to preview it  (sounds will autoplay)");
            }
        });

        JMenuItem mntmSaveAll = new JMenuItem("Save All");
        mnSaveItems.add(mntmSaveAll);
        mntmSaveAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblSavingPleaseWait.setText("Saving all files, Please Wait - this may take a while");
                SRFSave(srfp);
                lblSavingPleaseWait.setText("Select a resource to preview it  (sounds will autoplay)");
            }
        });

        mntmSaveJsanimationInfo = new JMenuItem("Save JS/Animation info");
        mnSaveItems.add(mntmSaveJsanimationInfo);
        mntmSaveJsanimationInfo.setEnabled(false);
        mntmSaveJsanimationInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblSavingPleaseWait.setText("Generating resource data, Please Wait");
                SRFSaveJava(currentselect);
                lblSavingPleaseWait.setText("Select a resource to preview it  (sounds will autoplay)");
            }
        });


    }
    
    protected void SRFSetInDirectory() {
        String dirin="";
        if (indir ==null)
        {
            dirin="C:\\JackBoxGames";
        }
        else
        {
            dirin=indir;
        }
        JFileChooser chooser = new JFileChooser(dirin);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("SRF Files", "srf"));
        chooser.showOpenDialog(null);
        file = chooser.getSelectedFile();
        filenameunq = file.getName();
        indir = file.getPath();
    }

    protected void SRFSetOutDirectory() {
        String dirout="";

        if (dir == null)
        {
            dirout="C:\\ydkj";
        }
        else
        {
            dirout=dir;
        }
        JFileChooser chooser = new JFileChooser(dirout);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        dir = chooser.getSelectedFile().getPath();
    }
    protected void SRFSave(SRFProcess srfp) {
        if (dir == null)
        {
            SRFSetOutDirectory();
        }
        String filenamenoext = filenameunq.substring(0, filenameunq.length()-4);

        Hashtable<String, byte[]> data = srfp.getData();
        Hashtable<String, String> parents = srfp.getParents();
        Enumeration<String> enumKey = data.keys();
		List<QHeaderout> qh = new ArrayList<QHeaderout>();
		List<QHeaderout> qh2 = new ArrayList<QHeaderout>();
		boolean firstpass=true;
		while(enumKey.hasMoreElements()) {
            String key = enumKey.nextElement();
            String nametype = key.substring(0, key.indexOf('_'));
        	File typedir = new File (dir+File.separator+filenamenoext+File.separator+nametype);
            String type = parents.get(nametype);
            String suffix="";
            byte[] val = null;
        	String id = key.substring(key.indexOf('_')+1);
       		typedir.mkdirs();
            if (type.equals("audio"))
            {
            	int ftype = KSFLUtilities.fcc(nametype);
            	BerkeleyResourceFile rp = srfp.getBRF();
            	MacResource r = rp.get(ftype, Short.parseShort(id));
            	SoundResource rsnd = r.shallowRecast(SoundResource.class);
            	if (AIFCmode.equals("AIFC"))
                {
    	            try {
    	                File output = new File(typedir, id+".aifc");
    	                output.createNewFile();
    	                FileOutputStream fos = new FileOutputStream(output);
    	                fos.write(rsnd.toAiff());
    	                fos.close();
    	            } catch (IOException e) {
    	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
    	            }
                }
            	else
                {
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
            }
            else if (type.equals("gfx"))
            {
              int ftype = KSFLUtilities.fcc(nametype);

          	  BerkeleyResourceFile rp = srfp.getBRF();
          	  MacResource r = rp.get(ftype, Short.parseShort(id));

          	  jgfx=null;
          	  System.gc();
          	  jgfx = new JackGraphic(r.data);

          	  {
	          	  
	          	File outputimage = new File(typedir, id+".gif");
	    		  try {
	    			  	if (gfxpanel.getStrip() == null)
	    			  	{
	        				  ImageIO.write(jgfx.toGif(jgfx.GetPalette()), "gif", outputimage);	    			  		
	    			  	}
	    			  	else
	    			  	{
	        			  if (gfxpanel.getStrip().getStripPalette() != null)
	        			  {
	        				  ImageIO.write(jgfx.toGif(gfxpanel.getStrip().getStripPalette()), "gif", outputimage);
	        			  }
	        			  else
	        			  {
	        				  ImageIO.write(jgfx.toGif(jgfx.GetPalette()), "gif", outputimage);
	        			  }
	    			  	}	
					} catch (NullPointerException e) {
      				  try {
						ImageIO.write(jgfx.toGif(jgfx.GetPalette()), "gif", outputimage);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	    			  		
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		  
	  	            try {
		                File output = new File(typedir, id+"t.json");
		                output.createNewFile();
		                PrintWriter out = new PrintWriter(output);
		                out.print(jgfx.getTileJSON());
		                out.close();
		            } catch (IOException e) {
		                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
		            }

    	            try {
    	                File output = new File(typedir, id+"f.json");
    	                output.createNewFile();
    	                PrintWriter out = new PrintWriter(output);
    	                out.print(jgfx.getFrameJSON());
    	                out.close();
    	            } catch (IOException e) {
    	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
    	            }

          	  }
            }
            else if (type.equals("template"))
            {
           		int ftype = KSFLUtilities.fcc(nametype);

           		BerkeleyResourceFile rp = srfp.getBRF();
            	  
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
	            	BerkeleyResourceFile rp = srfp.getBRF();
	            	int ftype = KSFLUtilities.fcc("qhdr");
            		
					for (int id2 : rp.getfullIDs(ftype)) 
					{
						MacResource r = rp.getFromFullID(ftype, id2);
						QHeader qh1 = new QHeader(id2,r.data,file);
						qh.add(new QHeaderout (qh1,true));
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
        	File typedir = new File (dir+File.separator+filenamenoext+File.separator);
            try {
                File output = new File(typedir, filenamenoext+".json");
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
                File output = new File(typedir, filenamenoext+"J.json");
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
    
        private void SRFSave1(String currentselect) {
            Hashtable<String, byte[]> data = srfp.getData();
            Hashtable<String, String> parents = srfp.getParents();
            if (dir == null)
            {
                SRFSetOutDirectory();
            }

            {
                String nametype = currentselect.substring(0, currentselect.indexOf('_'));
            	int ftype = KSFLUtilities.fcc(nametype);
                String filenamenoext = filenameunq.substring(0, filenameunq.length()-4);
            	File typedir = new File (dir+File.separator+filenamenoext+File.separator+nametype);
           		typedir.mkdirs();            	
            	String id = currentselect.substring(currentselect.indexOf('_')+1);
            	
            	String type = parents.get(nametype);
                String suffix="";
                byte[] val = null;

                if (type.equals("audio"))
                {
                	BerkeleyResourceFile rp = srfp.getBRF();
                	MacResource r = rp.get(ftype, Short.parseShort(id));
                	SoundResource rsnd = r.shallowRecast(SoundResource.class);
                	if (AIFCmode.equals("AIFC"))
                    {
        	            try {
        	                File output = new File(typedir, id+".aifc");
        	                output.createNewFile();
        	                FileOutputStream fos = new FileOutputStream(output);
        	                fos.write(rsnd.toAiff());
        	                fos.close();
        	            } catch (IOException e) {
        	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
        	            }
                    }
                	else
                    {
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
                }
                else if (type.equals("gfx"))
                {
                	int canvas = gfxpanel.getStrip().getCanvasCount();
                	
                	File outputimage = new File(typedir, id+"_"+canvas+".png");
                	try {
						ImageIO.write(gfxpanel.getStrip().getImage(), "png", outputimage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                else if (type.equals("template"))
                {
    	            try {
    	                File output = new File(typedir, id+".txt");
    	                output.createNewFile();
    	                FileOutputStream fos = new FileOutputStream(output);
    	                fos.write(data.get(currentselect));
    	                fos.close();
    	            } catch (IOException e) {
    	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
    	            }
                	BerkeleyResourceFile rp = srfp.getBRF();
                	MacResource r = rp.get(ftype, Short.parseShort(id));
    	            
                	val = r.data;
                    suffix=".tpl";
                }
                else if (!type.equals("qheader"))
                {
                val = data.get(currentselect);
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
        }

        private void SRFSaveJava(String currentselect) {
            Hashtable<String, byte[]> saves = srfp.getSaves();
            Hashtable<String, String> parents = srfp.getParents();
            if (dir == null)
            {
                SRFSetOutDirectory();
            }

            {
                String nametype = currentselect.substring(0, currentselect.indexOf('_'));
                String filenamenoext = filenameunq.substring(0, filenameunq.length()-4);
            	File typedir = new File (dir+File.separator+filenamenoext+File.separator+nametype);
           		typedir.mkdirs();            	
            	String id = currentselect.substring(currentselect.indexOf('_')+1);
            	
            	String type = parents.get(nametype);

                if (type.equals("gfx"))
                {
    	            try {
    	                File output = new File(typedir, id+"t.json");
    	                output.createNewFile();
    	                PrintWriter out = new PrintWriter(output);
    	                out.print(gfxpanel.getStrip().getTileJSON());
    	                out.close();
    	            } catch (IOException e) {
    	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
    	            }
                	
                }
                else
                {
    	            try {
    	                File output = new File(typedir, id+"arr.txt");
    	                output.createNewFile();
    	                PrintWriter out = new PrintWriter(output);
    	                out.print(new String(saves.get(currentselect)));
    	                out.close();
    	            } catch (IOException e) {
    	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
    	            }
                	
                }

            }
        }

        private void makeTree() {
        	srfp = null;
            SRFSetInDirectory();
            try {
                srfp = new SRFProcess(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }       
            
            tree = srfp.getTree();
            if (tree != null)
            {
                tree.setShowsRootHandles(true);
                tree.setRootVisible(false);
                tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
                scrollPane.setViewportView(tree);
                scrollPane.setVisible(true);

              //Listen for when the selection changes.
                tree.addTreeSelectionListener(this);
            }            

        }
        public void valueChanged(TreeSelectionEvent e) {
          //Returns the last path element of the selection.
          //This method is useful only when the selection model allows a single selection.
              DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                 tree.getLastSelectedPathComponent();

              if (node == null)
              //Nothing is selected.     
              return;

              DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

              if (parent != null)
              {
	              String type = srfp.getParents().get(parent.toString());
	              String id = node.toString();
	              String nametype = parent.toString();
	              currentselect = nametype+'_'+id;
	        	  int ftype = KSFLUtilities.fcc(nametype);
	        	  MacResource r=null;
	        	  BerkeleyResourceFile rp = srfp.getBRF();

	        	  if (type != null)
	        	  {
	        		  mntmSaveJsanimationInfo.setEnabled(false);
		        	  if (!type.equals("qheader"))
		        	  {
			        	  r = rp.get(ftype, Short.parseShort(id));
		        	  }              
		              if (type.equals("audio"))
		              {
							SoundResource rsnd = r.shallowRecast(SoundResource.class);
		      				new Thread(new AudioPlayer(rsnd.toWav())).start();
		              }
		              else if (type.equals("gfx"))
		              {
		            	  frmYdkjExtractor.getContentPane().remove(gfxpanel); 

		                  gfxpanel = new JackGFX(new CardLayout());
		                  gbc_gfxpanel = new GridBagConstraints();
		                  gbc_gfxpanel.fill = GridBagConstraints.BOTH;
		                  gbc_gfxpanel.gridx = 1;
		                  gbc_gfxpanel.gridy = 1;

		                  frmYdkjExtractor.revalidate();
			              frmYdkjExtractor.repaint();

		            	  jgfx = new JackGraphic(r.data);
		            	  gfxpanel.add(new JackGFX(jgfx),"Panel");
		                  mntmSaveJsanimationInfo.setEnabled(true);
		              }
		              else if (type.equals("template"))
		              {
		            	  frmYdkjExtractor.getContentPane().remove(gfxpanel); 

		                  gfxpanel = new JackGFX(new CardLayout());
		                  gbc_gfxpanel = new GridBagConstraints();
		                  gbc_gfxpanel.fill = GridBagConstraints.BOTH;
		                  gbc_gfxpanel.gridx = 1;
		                  gbc_gfxpanel.gridy = 1;

		                  frmYdkjExtractor.revalidate();
			              frmYdkjExtractor.repaint();

		            	  gfxpanel.add(new JackTemplate(srfp.getData().get(currentselect),srfp.getSaves().get(currentselect)),"Panel");
		                  mntmSaveJsanimationInfo.setEnabled(true);

		              }
		              else if (type.equals("qheader"))
		              {
		            	  frmYdkjExtractor.getContentPane().remove(gfxpanel); 

		                  gfxpanel = new JackGFX(new CardLayout());
		                  gbc_gfxpanel = new GridBagConstraints();
		                  gbc_gfxpanel.fill = GridBagConstraints.BOTH;
		                  gbc_gfxpanel.gridx = 1;
		                  gbc_gfxpanel.gridy = 1;

		                  frmYdkjExtractor.revalidate();
			              frmYdkjExtractor.repaint();
									 
		            	  gfxpanel.add(new JackQheader(srfp.getStr().get(currentselect),srfp.getStr().get(currentselect+"j")),"Panel");
		                  mntmSaveJsanimationInfo.setEnabled(true);

		              }
			          else
		              {
			              byte[] data = srfp.getData().get(currentselect); 
			                            
			              if (data != null)
			              {
			            	  frmYdkjExtractor.getContentPane().remove(gfxpanel); 

			                  gfxpanel = new JackGFX(new CardLayout());
			                  gbc_gfxpanel = new GridBagConstraints();
			                  gbc_gfxpanel.fill = GridBagConstraints.BOTH;
			                  gbc_gfxpanel.gridx = 1;
			                  gbc_gfxpanel.gridy = 1;

			                  frmYdkjExtractor.revalidate();
				              frmYdkjExtractor.repaint();
								 
			            	  gfxpanel.add(new JackTemplate(data),"Panel");
			              }
		              }
			          	frmYdkjExtractor.getContentPane().add(gfxpanel,gbc_gfxpanel);
			            CardLayout cl = (CardLayout)(gfxpanel.getLayout());
			            cl.show(gfxpanel,"Panel");
		                frmYdkjExtractor.revalidate();
			            frmYdkjExtractor.repaint();
		          }
              }
        }
        @Override
        public void actionPerformed(ActionEvent arg0) {
            AIFCmode = arg0.getActionCommand();
            }
        }
    
