package org.kjtw.main;

import javax.imageio.ImageIO;

import java.awt.Dimension;
import java.awt.GridBagLayout;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class JackAnimStrip extends JackGfxStrip implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1689714429602155854L;
	private JackGFXPanel panel;
	private int canvascount;
	private GridBagConstraints gbc_panel;
	private BufferedImage currentimage;
	private JackGraphic jgfx;
	private JRadioButton rdbtnYdkjPalette;
	private AbstractButton rdbtnYdkjPalette_1;
	private JRadioButton rdbtnYdkjPalette_2;
	private JRadioButton rdbtnYdkjPalette_3;
	private JRadioButton rdbtnHeadrushPalette;
	private JRadioButton rdbtnOffline;
	private JRadioButton rdbtnLff;
	private JSlider slider;
	private JToggleButton tglbtnInvertBg;
	private JScrollPane scrollPane;
	private JTextArea textPane;
	/**
	 * @wbp.parser.constructor
	 */
	public JackAnimStrip(JackGraphic jg) {

		jgfx=jg;

		canvascount=0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{68, 71, 87, 79, 79, 58, 94, 79, 0};
		gridBagLayout.rowHeights = new int[]{0, 444, 50, 10, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(640, 100));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);
		
		textPane = new JTextArea();
		textPane.setWrapStyleWord(true);
		scrollPane.setViewportView(textPane);

		if (jgfx.getFrameSize()==0)
		{
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				InputStream input = classLoader.getResourceAsStream("org/kjtw/resources/nodice.png");
			    currentimage = ImageIO.read(input);
			} catch (IOException e) {
			}
		}
		else
		{
			currentimage = jgfx.getFrameImg(canvascount,jgfx.GetPalette());
			textPane.setText(jgfx.getFrameTxt(canvascount));
		}
		
		JLabel lblPalette = new JLabel("Palette:");
		GridBagConstraints gbc_lblPalette = new GridBagConstraints();
		gbc_lblPalette.insets = new Insets(0, 0, 5, 5);
		gbc_lblPalette.gridx = 0;
		gbc_lblPalette.gridy = 0;
		add(lblPalette, gbc_lblPalette);

		ButtonGroup group = new ButtonGroup();

		rdbtnYdkjPalette = new JRadioButton("YDKJ 1");
		rdbtnYdkjPalette.setActionCommand("org/kjtw/resources/YDKJ1PAL.bmp");

		GridBagConstraints gbc_rdbtnYdkjPalette = new GridBagConstraints();
		gbc_rdbtnYdkjPalette.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnYdkjPalette.gridx = 1;
		gbc_rdbtnYdkjPalette.gridy = 0;
		add(rdbtnYdkjPalette, gbc_rdbtnYdkjPalette);
		group.add(rdbtnYdkjPalette);
		rdbtnYdkjPalette.addActionListener(this);

		rdbtnYdkjPalette_1 = new JRadioButton("YDKJ 2");
		rdbtnYdkjPalette_1.setActionCommand("org/kjtw/resources/YDKJ2PAL.bmp");
		rdbtnYdkjPalette_1.setSelected(true);
		GridBagConstraints gbc_rdbtnYdkjPalette_1 = new GridBagConstraints();
		gbc_rdbtnYdkjPalette_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnYdkjPalette_1.gridx = 2;
		gbc_rdbtnYdkjPalette_1.gridy = 0;
		add(rdbtnYdkjPalette_1, gbc_rdbtnYdkjPalette_1);
		group.add(rdbtnYdkjPalette_1);
		rdbtnYdkjPalette_1.addActionListener(this);
		
		rdbtnYdkjPalette_2 = new JRadioButton("YDKJ 3");
		rdbtnYdkjPalette_2.setActionCommand("org/kjtw/resources/YDKJ3PAL.bmp");
		GridBagConstraints gbc_rdbtnYdkjPalette_2 = new GridBagConstraints();
		gbc_rdbtnYdkjPalette_2.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnYdkjPalette_2.gridx = 3;
		gbc_rdbtnYdkjPalette_2.gridy = 0;
		add(rdbtnYdkjPalette_2, gbc_rdbtnYdkjPalette_2);
		group.add(rdbtnYdkjPalette_2);
		rdbtnYdkjPalette_2.addActionListener(this);
		
		rdbtnYdkjPalette_3 = new JRadioButton("YDKJ 4");
		rdbtnYdkjPalette_3.setActionCommand("org/kjtw/resources/YDKJ4PAL.bmp");
		GridBagConstraints gbc_rdbtnYdkjPalette_3 = new GridBagConstraints();
		gbc_rdbtnYdkjPalette_3.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnYdkjPalette_3.gridx = 4;
		gbc_rdbtnYdkjPalette_3.gridy = 0;
		add(rdbtnYdkjPalette_3, gbc_rdbtnYdkjPalette_3);
		group.add(rdbtnYdkjPalette_3);
		rdbtnYdkjPalette_3.addActionListener(this);

		rdbtnHeadrushPalette = new JRadioButton("HeadRush");
		rdbtnHeadrushPalette.setActionCommand("org/kjtw/resources/HRUSHP.bmp");
		GridBagConstraints gbc_rdbtnHeadrushPalette = new GridBagConstraints();
		gbc_rdbtnHeadrushPalette.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnHeadrushPalette.gridx = 5;
		gbc_rdbtnHeadrushPalette.gridy = 0;
		add(rdbtnHeadrushPalette, gbc_rdbtnHeadrushPalette);
		group.add(rdbtnHeadrushPalette);
		rdbtnHeadrushPalette.addActionListener(this);
		
		rdbtnOffline = new JRadioButton("Offline");
		rdbtnOffline.setActionCommand("org/kjtw/resources/OFFLINEP.bmp");
		GridBagConstraints gbc_rdbtnOffline = new GridBagConstraints();
		gbc_rdbtnOffline.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnOffline.gridx = 6;
		gbc_rdbtnOffline.gridy = 0;
		group.add(rdbtnOffline);
		add(rdbtnOffline, gbc_rdbtnOffline);
		rdbtnOffline.addActionListener(this);
		
		rdbtnLff = new JRadioButton("L!F!F!");
		rdbtnLff.setActionCommand("org/kjtw/resources/LFFP.bmp");
		GridBagConstraints gbc_rdbtnLff = new GridBagConstraints();
		gbc_rdbtnLff.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnLff.gridx = 7;
		gbc_rdbtnLff.gridy = 0;
		group.add(rdbtnLff);
		add(rdbtnLff, gbc_rdbtnLff);
		rdbtnLff.addActionListener(this);

		panel = new JackGFXPanel(currentimage);
		panel.setBackground(Color.white);
		final JLabel lblFrameCount = new JLabel("Frame "+(canvascount+1)+" of "+jgfx.getFrameSize());
		GridBagConstraints gbc_lblFrameCount = new GridBagConstraints();
		gbc_lblFrameCount.insets = new Insets(0, 0, 0, 5);
		gbc_lblFrameCount.gridwidth = 2;
		gbc_lblFrameCount.gridx = 5;
		gbc_lblFrameCount.gridy = 3;
		add(lblFrameCount, gbc_lblFrameCount);
		

		gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 8;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		slider = new JSlider();
		slider.setMaximum(jgfx.getFrameSize()-1);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setValue(canvascount);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {	
				canvascount = source.getValue();
				currentimage = jgfx.getFrameImg(canvascount,jgfx.GetPalette());
				textPane.setText(jgfx.getFrameTxt(canvascount));
				panel.setImage(currentimage);
				lblFrameCount.setText("Frame "+(canvascount+1)+" of "+jgfx.getFrameSize());
				panel.revalidate();
				}
			}
		});
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.gridwidth = 5;
		gbc_slider.insets = new Insets(0, 0, 0, 5);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 3;
		add(slider, gbc_slider);

		
		tglbtnInvertBg = new JToggleButton("Invert BG");
		tglbtnInvertBg.setSelected(false);
		GridBagConstraints gbc_btnToggleBg = new GridBagConstraints();
		gbc_btnToggleBg.gridx = 7;
		gbc_btnToggleBg.gridy = 3;
		add(tglbtnInvertBg, gbc_btnToggleBg);
		tglbtnInvertBg.setSelected(false);
		tglbtnInvertBg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnInvertBg.isSelected())
				{
					panel.setBackground(Color.black);
				}
				else
				{
					panel.setBackground(Color.white);
				}
			}
		});
				
				


	}
	public JackAnimStrip() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{54, 27, 428, 0, 33, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		jgfx = new JackGraphic();
	}
	
	public int getCanvasCount()
	{
		return canvascount;
	}
	public BufferedImage getImage() {
		return currentimage;
	}

	public Color[] getStripPalette() {
		return jgfx.GetPalette();
	}
    @Override
    public void actionPerformed(ActionEvent arg0) {
    	String pal = arg0.getActionCommand();
    	jgfx.SetPalette(pal);
		currentimage = jgfx.getFrameImg(canvascount,jgfx.GetPalette());
		textPane.setText(jgfx.getFrameTxt(canvascount));
		panel.setImage(currentimage);
        }
	public JackGraphic getGfx() {
		return jgfx;
	}

	public String getJS() {
		StringBuilder output = new StringBuilder();
		for (JackFrame jf : jgfx.getFrames())
		{
			output.append(jf.getTxt());
		}
		// TODO Auto-generated method stub
		return output.toString();
	}

	public boolean isRaw() {
		// TODO Auto-generated method stub
		return false;
	}

}

