package org.kjtw.main;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.kjtw.main.SRFProcess;

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
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;

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
	private JLabel lblSavingPleaseWait;
	private JButton btnSaveImageJavascript;
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
        frmYdkjExtractor.setBounds(100, 100, 1162, 941);
        frmYdkjExtractor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{200, 796, 133, 0};
        gridBagLayout.rowHeights = new int[]{30, 32, 600, 26, 29, 23, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        frmYdkjExtractor.getContentPane().setLayout(gridBagLayout);
        
        JLabel lblResourcesInSrf = new JLabel("Resources in SRF");
        GridBagConstraints gbc_lblResourcesInSrf = new GridBagConstraints();
        gbc_lblResourcesInSrf.anchor = GridBagConstraints.SOUTH;
        gbc_lblResourcesInSrf.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblResourcesInSrf.insets = new Insets(0, 0, 5, 5);
        gbc_lblResourcesInSrf.gridx = 0;
        gbc_lblResourcesInSrf.gridy = 0;
        frmYdkjExtractor.getContentPane().add(lblResourcesInSrf, gbc_lblResourcesInSrf);
        
        JTextArea txtrSelectAResource = new JTextArea();
        txtrSelectAResource.setBackground(UIManager.getColor("Button.background"));
        txtrSelectAResource.setWrapStyleWord(true);
        txtrSelectAResource.setFont(UIManager.getFont("TextField.font"));
        txtrSelectAResource.setTabSize(4);
        txtrSelectAResource.setLineWrap(true);
        txtrSelectAResource.setText("Select a resource to preview it  (sounds will autoplay)");
        GridBagConstraints gbc_txtrSelectAResource = new GridBagConstraints();
        gbc_txtrSelectAResource.weighty = 1.0;
        gbc_txtrSelectAResource.weightx = 1.0;
        gbc_txtrSelectAResource.fill = GridBagConstraints.BOTH;
        gbc_txtrSelectAResource.insets = new Insets(0, 0, 5, 5);
        gbc_txtrSelectAResource.gridx = 1;
        gbc_txtrSelectAResource.gridy = 0;
        frmYdkjExtractor.getContentPane().add(txtrSelectAResource, gbc_txtrSelectAResource);
        
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.weighty = 1.0;
        gbc_scrollPane.weightx = 1.0;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridheight = 2;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        frmYdkjExtractor.getContentPane().add(scrollPane, gbc_scrollPane);
        
        makeTree();
        
        gfxpanel = new JackGFX();
        gbc_gfxpanel = new GridBagConstraints();
        gbc_gfxpanel.gridheight = 2;
        gbc_gfxpanel.fill = GridBagConstraints.BOTH;
        gbc_gfxpanel.insets = new Insets(0, 0, 5, 5);
        gbc_gfxpanel.gridx = 1;
        gbc_gfxpanel.gridy = 1;
        frmYdkjExtractor.getContentPane().add(gfxpanel, gbc_gfxpanel);
        
        JButton btnSetOutputDirectory = new JButton("Set Output Directory");
        btnSetOutputDirectory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SRFSetOutDirectory();
            }
        });
        
                GridBagConstraints gbc_btnSetOutputDirectory = new GridBagConstraints();
                gbc_btnSetOutputDirectory.weighty = 0.8;
                gbc_btnSetOutputDirectory.weightx = 0.8;
                gbc_btnSetOutputDirectory.anchor = GridBagConstraints.NORTH;
                gbc_btnSetOutputDirectory.fill = GridBagConstraints.HORIZONTAL;
                gbc_btnSetOutputDirectory.insets = new Insets(0, 0, 5, 5);
                gbc_btnSetOutputDirectory.gridx = 0;
                gbc_btnSetOutputDirectory.gridy = 3;
                frmYdkjExtractor.getContentPane().add(btnSetOutputDirectory, gbc_btnSetOutputDirectory);
        
        JButton btnSaveSelectedResource = new JButton("Save Selected Resource");
        btnSaveSelectedResource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblSavingPleaseWait.setText("Saving, Please Wait");
                SRFSave1(currentselect);
                lblSavingPleaseWait.setText("");
            }
        });
        GridBagConstraints gbc_btnSaveSelectedResource = new GridBagConstraints();
        gbc_btnSaveSelectedResource.weighty = 0.8;
        gbc_btnSaveSelectedResource.weightx = 0.8;
        gbc_btnSaveSelectedResource.anchor = GridBagConstraints.NORTH;
        gbc_btnSaveSelectedResource.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnSaveSelectedResource.insets = new Insets(0, 0, 5, 5);
        gbc_btnSaveSelectedResource.gridx = 1;
        gbc_btnSaveSelectedResource.gridy = 3;
        frmYdkjExtractor.getContentPane().add(btnSaveSelectedResource, gbc_btnSaveSelectedResource);
        
        
        JLabel lblNewLabel = new JLabel("Format of exported audio");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.weighty = 1.0;
        gbc_lblNewLabel.weightx = 1.0;
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 2;
        gbc_lblNewLabel.gridy = 3;
        frmYdkjExtractor.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
        ButtonGroup group = new ButtonGroup();
        
        JButton btnLoadSrf = new JButton("Load SRF");
        btnLoadSrf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrollPane.setVisible(false);
                makeTree();
            }
        });
        GridBagConstraints gbc_btnLoadSrf = new GridBagConstraints();
        gbc_btnLoadSrf.weighty = 1.0;
        gbc_btnLoadSrf.weightx = 1.0;
        gbc_btnLoadSrf.anchor = GridBagConstraints.NORTH;
        gbc_btnLoadSrf.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnLoadSrf.insets = new Insets(0, 0, 5, 5);
        gbc_btnLoadSrf.gridx = 0;
        gbc_btnLoadSrf.gridy = 4;
        frmYdkjExtractor.getContentPane().add(btnLoadSrf, gbc_btnLoadSrf);
        
        JButton btnSaveResources = new JButton("Save All Resources");
        btnSaveResources.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblSavingPleaseWait.setText("Saving all files, Please Wait - this may take a while");
                SRFSave(srfp);
                lblSavingPleaseWait.setText("");
            }
        });
        GridBagConstraints gbc_btnSaveResources = new GridBagConstraints();
        gbc_btnSaveResources.weighty = 0.8;
        gbc_btnSaveResources.weightx = 0.8;
        gbc_btnSaveResources.anchor = GridBagConstraints.NORTH;
        gbc_btnSaveResources.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnSaveResources.insets = new Insets(0, 0, 5, 5);
        gbc_btnSaveResources.gridx = 1;
        gbc_btnSaveResources.gridy = 4;
        frmYdkjExtractor.getContentPane().add(btnSaveResources, gbc_btnSaveResources);
        JRadioButton rdbtnPcmwav = new JRadioButton("PCM (Wav)");
        rdbtnPcmwav.setActionCommand("PCM");
        rdbtnPcmwav.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_rdbtnPcmwav = new GridBagConstraints();
        gbc_rdbtnPcmwav.weighty = 1.0;
        gbc_rdbtnPcmwav.weightx = 1.0;
        gbc_rdbtnPcmwav.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnPcmwav.anchor = GridBagConstraints.NORTH;
        gbc_rdbtnPcmwav.fill = GridBagConstraints.HORIZONTAL;
        gbc_rdbtnPcmwav.gridx = 2;
        gbc_rdbtnPcmwav.gridy = 4;
        frmYdkjExtractor.getContentPane().add(rdbtnPcmwav, gbc_rdbtnPcmwav);
        group.add(rdbtnPcmwav);
        rdbtnPcmwav.addActionListener(this);
        
        lblSavingPleaseWait = new JLabel("");
        GridBagConstraints gbc_lblSavingPleaseWait = new GridBagConstraints();
        gbc_lblSavingPleaseWait.insets = new Insets(0, 0, 0, 5);
        gbc_lblSavingPleaseWait.gridx = 0;
        gbc_lblSavingPleaseWait.gridy = 5;
        frmYdkjExtractor.getContentPane().add(lblSavingPleaseWait, gbc_lblSavingPleaseWait);
        
        btnSaveImageJavascript = new JButton("Save resource data (JavaScript or Array)");
        GridBagConstraints gbc_btnSaveImageJavascript = new GridBagConstraints();
        gbc_btnSaveImageJavascript.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnSaveImageJavascript.insets = new Insets(0, 0, 0, 5);
        gbc_btnSaveImageJavascript.gridx = 1;
        gbc_btnSaveImageJavascript.gridy = 5;
        frmYdkjExtractor.getContentPane().add(btnSaveImageJavascript, gbc_btnSaveImageJavascript);
        btnSaveImageJavascript.setEnabled(false);
        btnSaveImageJavascript.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblSavingPleaseWait.setText("Generating resource data, Please Wait");
                SRFSaveJava(currentselect);
                lblSavingPleaseWait.setText("");
            }
        });

        JRadioButton rdbtnNativeaifc = new JRadioButton("Native (AIFC)");
        rdbtnNativeaifc.setSelected(true);
        rdbtnNativeaifc.setActionCommand("AIFC");
        GridBagConstraints gbc_rdbtnNativeaifc = new GridBagConstraints();
        gbc_rdbtnNativeaifc.weightx = 1.0;
        gbc_rdbtnNativeaifc.weighty = 1.0;
        gbc_rdbtnNativeaifc.anchor = GridBagConstraints.NORTHWEST;
        gbc_rdbtnNativeaifc.gridx = 2;
        gbc_rdbtnNativeaifc.gridy = 5;
        frmYdkjExtractor.getContentPane().add(rdbtnNativeaifc, gbc_rdbtnNativeaifc);

        //Register a listener for the radio buttons.
        rdbtnNativeaifc.addActionListener(this);
        group.add(rdbtnNativeaifc);
        

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
        Hashtable<String, byte[]> save = srfp.getSaves();
        Hashtable<String, String> parents = srfp.getParents();
        Enumeration<String> enumKey = data.keys();
        while(enumKey.hasMoreElements()) {
            String key = enumKey.nextElement();
            String nametype = key.substring(0, key.indexOf('_'));
            String type = parents.get(nametype);
            String suffix="";
            byte[] val = null;
        	String id = key.substring(key.indexOf('_')+1);
        	File typedir = new File (dir+File.separator+filenamenoext+File.separator+nametype);
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

          	  jgfx = new JackGraphic(r.data);

          	  List<JackRawImage> list = jgfx.getJri();
          	  int canvas =0;
          	  for (JackRawImage jri : list)
          	  {
          		  File outputimage = new File(typedir, id+"_"+canvas+".png");
          		  try {
          			  if (gfxpanel.getStrip().getStripPalette() != null)
          			  {
          				  ImageIO.write(jri.getImgout(gfxpanel.getStrip().getStripPalette()), "png", outputimage);
          			  }
          			  else 
          			  {
          				ImageIO.write(jri.getImgout(jgfx.GetPalette()), "png", outputimage);
          			  }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	canvas++;
          	  }
          	File outputimage = new File(typedir, id+".gif");
    		  try {
        			  if (gfxpanel.getStrip().getStripPalette() != null)
        			  {
        				  ImageIO.write(jgfx.toGif(gfxpanel.getStrip().getStripPalette()), "gif", outputimage);
        			  }
        			  else
        			  {
        				  ImageIO.write(jgfx.toGif(jgfx.GetPalette()), "gif", outputimage);
        			  }

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		  
	            try {
	                File output = new File(typedir, id+".js");
	                output.createNewFile();
	                PrintWriter out = new PrintWriter(output);
	                out.print(jgfx.getJS());
	                out.close();
	            } catch (IOException e) {
	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	            }

            }
            else if (type.equals("template"))
            {
            	val = data.get(key);
                suffix=".tmpl";
                
                
	            try {
	                File output = new File(typedir, id+".txt");
	                output.createNewFile();
	                FileOutputStream fos = new FileOutputStream(output);
	                fos.write(data.get(key));
	                fos.close();
	            } catch (IOException e) {
	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	            }
	              int ftype = KSFLUtilities.fcc(nametype);

	          	  BerkeleyResourceFile rp = srfp.getBRF();
	          	  MacResource r = rp.get(ftype, Short.parseShort(id));
	            
            	val = r.data;
                suffix=".tpl";

            }
            else if (!type.equals("qheader"))
            {
	            try {
	                File output = new File(typedir, filenamenoext+".csv");
	                if (!output.exists())
	                {
		                output.createNewFile();
		                FileOutputStream fos = new FileOutputStream(output);
		                fos.write(save.get("qhdr"));
		                fos.close();
	                }
	            } catch (IOException e) {
	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	            }

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
    	                File output = new File(typedir, id+".js");
    	                output.createNewFile();
    	                PrintWriter out = new PrintWriter(output);
    	                out.print(gfxpanel.getStrip().getGfx().getJS());
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
	        	  if (type != null)
	        	  {
	                  btnSaveImageJavascript.setEnabled(false);
		        	  if (!type.equals("qhdr"))
		        	  {
			        	  BerkeleyResourceFile rp = srfp.getBRF();
			        	  r = rp.get(ftype, Short.parseShort(id));
		        	  }              
		              if (type.equals("audio"))
		              {
							SoundResource rsnd = r.shallowRecast(SoundResource.class);
		      				new Thread(new AudioPlayer(rsnd.toWav())).start();
		              }
		              else if (type.equals("gfx"))
		              {
		            	  gfxpanel.removeAll();
		
		            	  jgfx = new JackGraphic(r.data);
									 
		            	  gfxpanel = new JackGFX(jgfx);
		                  frmYdkjExtractor.getContentPane().add(gfxpanel, gbc_gfxpanel);
		                  frmYdkjExtractor.revalidate();
		                  btnSaveImageJavascript.setEnabled(true);
		              }
		              else if (type.equals("template"))
		              {
		            	  gfxpanel.removeAll();
									 
		            	  gfxpanel = new JackTemplate(srfp.getData().get(currentselect),srfp.getSaves().get(currentselect));
		                  frmYdkjExtractor.getContentPane().add(gfxpanel, gbc_gfxpanel);
		                  frmYdkjExtractor.revalidate();
		                  btnSaveImageJavascript.setEnabled(true);

		              }
			              else
		              {
			              byte[] data = srfp.getData().get(currentselect); 
			                            
			              if (data != null)
			              {
			            	  gfxpanel.removeAll();
								 
			            	  gfxpanel = new JackTemplate(data);
			                  frmYdkjExtractor.getContentPane().add(gfxpanel, gbc_gfxpanel);
			                  frmYdkjExtractor.revalidate();
			              }
		              }
		          }
              }
        }
        @Override
        public void actionPerformed(ActionEvent arg0) {
            AIFCmode = arg0.getActionCommand();
            }
        }
    
