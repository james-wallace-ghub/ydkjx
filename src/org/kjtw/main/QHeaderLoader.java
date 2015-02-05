package org.kjtw.main;

import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.kjtw.categories.CelebrityCollectCall;
import org.kjtw.categories.DisorDat;
import org.kjtw.categories.FiberOpticFieldTrip;
import org.kjtw.categories.FillInTheBlank;
import org.kjtw.categories.Gibberish;
import org.kjtw.categories.GuestHostQuestion;
import org.kjtw.categories.JackAttack;
import org.kjtw.categories.PictureQuestion;
import org.kjtw.categories.QHeadProcess;
import org.kjtw.categories.QHeader;
import org.kjtw.categories.StandardQuestion;
import org.kjtw.categories.SuperAudioQuestion;
import org.kjtw.categories.ThreeWay;
import org.kjtw.categories.Whatshisname;
import org.kjtw.main.SRFProcess;

import com.kreative.ksfl.KSFLUtilities;
import com.kreative.rsrc.BerkeleyResourceFile;
import com.kreative.rsrc.MacResourceFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class QHeaderLoader implements TreeSelectionListener {

    private JFrame frmYdkjExtractor;
    static JTree tree;
    static File file;
    static String indir = null;    
    static String dir = null;
    static String filenameunq ="";
    SRFProcess srfp = null;
    static String currentselect="";
    JScrollPane scrollPane;
    QHeadProcess qhp = null;
    JPanel panel_1;
    GridBagConstraints gbc_panel;
	private JLabel lblResourcesInSrf;
	private String fpath;
	private BerkeleyResourceFile rp;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    QHeaderLoader window = new QHeaderLoader();
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
    public QHeaderLoader() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmYdkjExtractor = new JFrame();
        frmYdkjExtractor.setTitle("Jack Player");
        frmYdkjExtractor.setBounds(100, 100, 1024, 430);
        frmYdkjExtractor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {400, 562};
        gridBagLayout.rowHeights = new int[] {24, 30};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0};
        frmYdkjExtractor.getContentPane().setLayout(gridBagLayout);
        
        lblResourcesInSrf = new JLabel("Resources in SRF");
        GridBagConstraints gbc_lblResourcesInSrf = new GridBagConstraints();
        gbc_lblResourcesInSrf.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblResourcesInSrf.anchor = GridBagConstraints.SOUTH;
        gbc_lblResourcesInSrf.insets = new Insets(0, 0, 5, 5);
        gbc_lblResourcesInSrf.gridx = 0;
        gbc_lblResourcesInSrf.gridy = 0;
        frmYdkjExtractor.getContentPane().add(lblResourcesInSrf, gbc_lblResourcesInSrf);
        
        panel_1 = new JPanel(new CardLayout());
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.anchor = GridBagConstraints.WEST;
        gbc_panel_1.gridheight = 2;
        gbc_panel_1.fill = GridBagConstraints.VERTICAL;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 0;


        frmYdkjExtractor.getContentPane().add(panel_1, gbc_panel_1);
		JPanel panel = new JPanel(new CardLayout());
		panel_1.add(panel,"Panel");
        CardLayout cl = (CardLayout)(panel_1.getLayout());
        cl.show(panel_1,"Panel");
        
        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        frmYdkjExtractor.getContentPane().add(scrollPane, gbc_scrollPane, 0);
        
        makeTree();
    }
    
	protected void QHeadSetInDirectory() {
        String dirin="";
        if (indir ==null)
        {
            dirin="C:\\JackBoxGames";
        }
        else
        {
            dirin=indir;
        }
        boolean hasqhdr=false;
        do
        {
	        JFileChooser chooser = new JFileChooser(dirin);
	        chooser.setDialogTitle("Open a file containing Question headers, such as qheaders.srf");
	        chooser.setFileFilter(new FileNameExtensionFilter("QHEADERS.SRF", "srf"));
	        chooser.showOpenDialog(null);
	        file = chooser.getSelectedFile();
	        filenameunq = file.getName();
	        indir = file.getPath();
			RandomAccessFile raf = null;
			//Force read only
			try {
				raf = new RandomAccessFile(file, "r");
			} catch (FileNotFoundException e) {
			    JOptionPane.showMessageDialog(null, "Resource tree can't be made, that file doesn't exist.");
				e.printStackTrace();
			}
			if (raf != null)
			{
	    		byte[]packet = new byte[4];
	    		try {
					raf.read(packet, 0, 4);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		if (issrf(packet))
	    		{
	    			try {
	    				rp = new BerkeleyResourceFile(file, "r", MacResourceFile.CREATE_NEVER);
	    			} catch (IOException e) {
	    				System.err.println("Error: Invalid file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	    				return;
	    			}
	    			fpath = file.getPath().replaceFirst(file.getName(), "");
    				for (int type : rp.getTypes()) {
    					String ftype = KSFLUtilities.fccs(type).trim();
    					if (ftype.equals("qhdr"))
    					{
    						hasqhdr = true;
    					}
    				}
	    		}	 
			}
        } while (hasqhdr==false);
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
    

        private void makeTree() {

            QHeadSetInDirectory();
            try {
                qhp = new QHeadProcess(rp,fpath);
            } catch (IOException e1) {
                e1.printStackTrace();
            }       
            
            tree = qhp.getTree();
            if (tree != null)
            {
        		tree.setFont(new Font("Tahoma", Font.PLAIN, 10));
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

              QHeader qdat = qhp.getTable().get(node.toString());
              if (qdat!= null)
              {
            	  lblResourcesInSrf.setText(qdat.getPath().substring(qdat.getPath().indexOf("QF")));
            	  frmYdkjExtractor.getContentPane().remove(panel_1); 
              
                  panel_1 = new JPanel(new CardLayout());
                  GridBagConstraints gbc_panel_1 = new GridBagConstraints();
                  gbc_panel_1.gridheight = 11;
                  gbc_panel_1.fill = GridBagConstraints.BOTH;
                  gbc_panel_1.gridx = 1;
                  gbc_panel_1.gridy = 0;

                  frmYdkjExtractor.revalidate();
	              frmYdkjExtractor.repaint();

	          	switch (qdat.getType())
	          	{
		  			case 0:
		  			{
		          	  switch (qdat.getSubType())
		        	  {
		        	  	case 1:
		        	  	{
			  				try {
								panel_1.add(new StandardQuestion(qdat),"Panel");
								break;
							} catch (IOException e0) {
								// TODO Auto-generated catch block
								e0.printStackTrace();
							}
		        	  	}
		        	  	case 2:
			        	  	{
				  				try {
									panel_1.add(new FillInTheBlank(qdat),"Panel");
									break;
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
			        	  	}
		        	  	case 3:
		        	  	{
			  				try {
								panel_1.add(new Whatshisname(qdat),"Panel");
								break;
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		        	  	}
		        	  	case 4:
		        	  	{
			  				try {
								panel_1.add(new PictureQuestion(qdat),"Panel");
								break;
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		        	  	}
		        	  	case 5:
		        	  	{
			  				try {
								panel_1.add(new SuperAudioQuestion(qdat),"Panel");
								break;
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		        	  	}
		        	  	case 6:
		        	  	{
			  				try {
								panel_1.add(new GuestHostQuestion(qdat),"Panel");
								break;
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		        	  	}

		  			}
		          	  break;
		  			}
		  			case 2:
		  			{
		  				try {
							panel_1.add(new Gibberish(qdat),"Panel");
							break;
		  				} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		  			}
		  			case 3:
		  			{
		  				try {
							panel_1.add(new DisorDat(qdat),"Panel");
							break;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		  			}
		  			case 4:
		  			{
		  				try {
							panel_1.add(new JackAttack(qdat),"Panel");
							break;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		  			}
		  			case 5:
		  			{
		  				try {
							panel_1.add(new FiberOpticFieldTrip(qdat),"Panel");
							break;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		  			}
		  			case 10:
		  			{
		  				try {
							panel_1.add(new CelebrityCollectCall(qdat),"Panel");
							break;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		  			}
		  			case 12:
		  			{
		  				try {
							panel_1.add(new ThreeWay(qdat),"Panel");
							break;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		  			}
	          	}
	          	frmYdkjExtractor.getContentPane().add(panel_1,gbc_panel_1);
	            CardLayout cl = (CardLayout)(panel_1.getLayout());
	            cl.show(panel_1,"Panel");
                frmYdkjExtractor.revalidate();
	              frmYdkjExtractor.repaint();

              }
        }
    	private static boolean issrf(byte[] packet) 
    	{
    		if ((packet[0] == 115) && (packet[1] == 114) && (packet[2] == 102) && (packet[3] == 49)) //srf1 in byte form
    		{
    			return true;
    		}
    		else
    		{
    			return false;
    		}
    	}

}