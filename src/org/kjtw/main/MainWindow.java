package org.kjtw.main;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.widgets.Button;

public class MainWindow implements LineListener {

    protected Shell shell;
    static Tree tree;
    static File file;
    static String indir = null;    
    static String dir = null;
    static StyledText styledText;
	static String filenameunq ="";
	SRFProcess srfp = null;
	boolean playCompleted;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
        	MainWindow window = new MainWindow();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setSize(476, 345);
        shell.setText("YDKJ SRF Extractor");

		makeTree();

        Menu menu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menu);
        
        MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
        mntmNewSubmenu.setText("Menu");
        
        Menu menu_1 = new Menu(mntmNewSubmenu);
        mntmNewSubmenu.setMenu(menu_1);


        MenuItem mntmLoadSrf = new MenuItem(menu_1, SWT.NONE);
	    mntmLoadSrf.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
	    	styledText.setText("");
	    	tree.dispose();
	    	makeTree();
	    	tree = srfp.getTree();
			tree.setBounds(0, 0, 160, 233);
	    }
        });
        mntmLoadSrf.setText("Load SRF");
                
        TextViewer textViewer = new TextViewer(shell, SWT.BORDER);
        styledText = textViewer.getTextWidget();
        styledText.setBounds(217, 0, 243, 276);
        styledText.setWordWrap(true);
        
        Button btnSaveResource = new Button(shell, SWT.NONE);
        btnSaveResource.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		SRFSave(srfp);
        	}
        });
        btnSaveResource.setBounds(0, 251, 103, 25);
        btnSaveResource.setText("Save Resources");
        
        Button btnSetdirectory = new Button(shell, SWT.NONE);
        btnSetdirectory.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		SRFSetOutDirectory();
        	}
        });
        btnSetdirectory.setText("Set Directory");
        btnSetdirectory.setBounds(108, 251, 103, 25);
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
		Hashtable<String, Byte[]> data = srfp.getData();
		Hashtable<String, Byte[]> save = srfp.getSaves();
		Hashtable<String, String> parents = srfp.getParents();
		Enumeration<String> enumKey = data.keys();
		while(enumKey.hasMoreElements()) {
		    String key = enumKey.nextElement();
		    String type = parents.get(key.substring(0, key.indexOf('_')));
		    String suffix="";
		    Byte[] val = null;

		    if (type.equals("audio"))
		    {
			val = save.get(key);
		    	suffix=".aifc";
		    }
		    else if (!type.equals("qheader"))
		    {
			val = data.get(key);
		    	suffix=".txt";
		    }
			try {
				File output = new File(dir, key+suffix);
				output.createNewFile();
				FileOutputStream fos = new FileOutputStream(output);
				fos.write(byteconvert(val));
				fos.close();
			} catch (IOException e) {
				System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
			}

		}
	}

	private void makeTree() {

		SRFSetInDirectory();
		try {
			srfp = new SRFProcess(shell, file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		tree = srfp.getTree();
		tree.setBounds(0, 0, 160, 233);
        tree.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
        		TreeItem ti = (TreeItem)event.item;
        		TreeItem parent = ti.getParentItem();
        		if (parent !=null)
        		{
	    		String type = srfp.getParents().get(parent.getText());
				Byte[] data = srfp.getData().get(parent.getText()+'_'+ti.getText()); 

				byte[] recall = byteconvert(data);
				
				
	    		if (type.equals("audio"))
	    		{
	
	    			playSound(recall);
	    		}
	    		else
	    		{
	    			styledText.setText(new String(recall));
	    		}
	        }
            }
        });

	}

	protected byte[] byteconvert(Byte[] data) {
		byte[] recall = new byte[data.length];
		for (int i=0; i < data.length; i++)
		{
			recall[i] = data[i];
		}
		return recall;
	}

	private void playSound(byte[] bytes) {
		try {
		InputStream audio = new ByteArrayInputStream(bytes);
		playCompleted = false;
		AudioInputStream ais = AudioSystem.getAudioInputStream(audio);
	AudioFormat format = ais.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Clip clip = AudioSystem.getClip();
		clip.addLineListener(this);
	    clip.open(ais);
	    clip.start(); 
	    while (!playCompleted) {
            // wait for the playback completes
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }         
	    }
        clip.close();
	} catch (UnsupportedAudioFileException
			| IOException | LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
//		if (bytes != null) try {
//			AudioInputStream st = AudioSystem.getAudioInputStream(new ByteArrayInputStream(bytes));
//			AudioFormat fm = st.getFormat();
//			DataLine.Info inf = new DataLine.Info(Clip.class, fm, ((int)st.getFrameLength()*fm.getFrameSize()));
//			Clip c = (Clip)AudioSystem.getLine(inf);
//			c.open(st);
//			c.start();
//			c.drain();
//			c.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	 	public void update(LineEvent event) {
	        LineEvent.Type type = event.getType();
	         
	     if (type == LineEvent.Type.STOP) {
	            playCompleted = true;
	            System.out.println("Playback completed.");
	        }
	 
	    }
	}

