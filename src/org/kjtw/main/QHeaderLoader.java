package org.kjtw.main;

import java.awt.EventQueue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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

import org.kjtw.categories.FillInTheBlank;
import org.kjtw.categories.QHeadProcess;
import org.kjtw.categories.QHeader;
import org.kjtw.categories.StandardQuestion;
import org.kjtw.categories.Whatshisname;
import org.kjtw.main.SRFProcess;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;

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
    private JPanel panel;
    GridBagConstraints gbc_panel;
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
        frmYdkjExtractor.setBounds(100, 100, 827, 430);
        frmYdkjExtractor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {113, 30, 153, 133, 133};
        gridBagLayout.rowHeights = new int[] {24, 18, -33, 30, 30, 30, 23, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0};
        gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        frmYdkjExtractor.getContentPane().setLayout(gridBagLayout);
        
        JLabel lblResourcesInSrf = new JLabel("Resources in SRF");
        GridBagConstraints gbc_lblResourcesInSrf = new GridBagConstraints();
        gbc_lblResourcesInSrf.anchor = GridBagConstraints.SOUTH;
        gbc_lblResourcesInSrf.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblResourcesInSrf.insets = new Insets(0, 0, 5, 5);
        gbc_lblResourcesInSrf.gridx = 0;
        gbc_lblResourcesInSrf.gridy = 0;
        frmYdkjExtractor.getContentPane().add(lblResourcesInSrf, gbc_lblResourcesInSrf);
        

        gbc_panel = new GridBagConstraints();
        gbc_panel.gridheight = 10;
        gbc_panel.gridwidth = 3;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 2;
        gbc_panel.gridy = 0;


        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.weighty = 1.0;
        gbc_scrollPane.weightx = 1.0;
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridheight = 10;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        frmYdkjExtractor.getContentPane().add(scrollPane, gbc_scrollPane);
        
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
        do
        {
	        JFileChooser chooser = new JFileChooser(dirin);
	        chooser.setDialogTitle("Open the qheaders.srf file to continue");
	        chooser.setFileFilter(new FileNameExtensionFilter("QHEADERS.SRF", "srf"));
	        chooser.showOpenDialog(null);
	        file = chooser.getSelectedFile();
	        filenameunq = file.getName();
	        indir = file.getPath();
        } while (!filenameunq.toLowerCase().equals("qheaders.srf"));
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
    
    protected byte[] byteconvert(Byte[] data) {
        if (data != null)
        {
            byte[] recall = new byte[data.length];
            for (int i=0; i < data.length; i++)
            {
                recall[i] = data[i];
            }
            return recall;
        }
        return null;
    }

        private void makeTree() {

            QHeadSetInDirectory();
            try {
                qhp = new QHeadProcess(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }       
            
            tree = qhp.getTree();
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

              QHeader qdat = qhp.getTable().get(node.toString());
         
              if (qdat != null)
              {
		          if (qdat.getType()== 0) 
		          {
		        	  switch (qdat.getSubType())
		        	  {
		        	  	case 1:
		        	  	{
				              try {
				            	  if (panel != null)
				            	  {
				            		  panel.removeAll();
				            	  }
								panel = new StandardQuestion(qdat);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				              break;
		        	  	}
		        	  	case 2:
		        	  	{
				              try {
				            	  if (panel != null)
				            	  {
				            		  panel.removeAll();
				            	  }
								panel = new FillInTheBlank(qdat);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				              break;
		        	  	}
		        	  	case 3:
		        	  	{
				              try {
				            	  if (panel != null)
				            	  {
				            		  panel.removeAll();
				            	  }
								panel = new Whatshisname(qdat);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				              break;
		        	  	}
		        	  		
		        	  }
		              frmYdkjExtractor.getContentPane().add(panel, gbc_panel);
		              frmYdkjExtractor.revalidate();
		          }
		        	  
              }
        }
}
