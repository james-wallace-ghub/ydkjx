package org.kjtw.displays;

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

import org.kjtw.resources.YDKJPalettes;
import org.kjtw.structures.JackGraphic;

import javax.swing.JComboBox;

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
	private JSlider slider;
	private JToggleButton tglbtnInvertBg;
	private JScrollPane scrollPane;
	private JTextArea textPane;
	private JToggleButton tglbtnShowText;
	private JComboBox comboBox;
	/**
	 * @wbp.parser.constructor
	 */
	public JackAnimStrip(JackGraphic jg) {

		jgfx=jg;

		canvascount=0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{68, 71, 87, 79, 79, 58, 94, 79, 0};
		gridBagLayout.rowHeights = new int[]{480, 35, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(640, 100));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
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
			//textPane.setText(jgfx.getFrameTxt(canvascount));
		}



		String[] palettes = { "YDKJ 1", "YDKJ 2", "YDKJ 3", "YDKJ 4 (The Ride)", "HeadRush", "Offline", "Louder! Faster! Funnier!" };

		panel = new JackGFXPanel(currentimage);
		panel.setBackground(Color.white);

		gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 8;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		final JLabel lblFrameCount = new JLabel("Frame "+(canvascount+1)+" of "+jgfx.getFrameSize());
		GridBagConstraints gbc_lblFrameCount = new GridBagConstraints();
		gbc_lblFrameCount.insets = new Insets(0, 0, 0, 5);
		gbc_lblFrameCount.gridx = 4;
		gbc_lblFrameCount.gridy = 2;
		add(lblFrameCount, gbc_lblFrameCount);
				
				comboBox = new JComboBox(palettes);
				comboBox.setSelectedIndex(1);
				comboBox.addActionListener(this);
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
						panel.setImage(currentimage);
						lblFrameCount.setText("Frame "+(canvascount+1)+" of "+jgfx.getFrameSize());
						panel.revalidate();

						if (tglbtnShowText.isSelected())
						{
							String txt = jgfx.getIndFrameJSON(canvascount);
							textPane.setText(txt);
						}
						
						}
					}
				});
				GridBagConstraints gbc_comboBox = new GridBagConstraints();
				gbc_comboBox.insets = new Insets(0, 0, 0, 5);
				gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox.gridx = 5;
				gbc_comboBox.gridy = 2;
				add(comboBox, gbc_comboBox);

				GridBagConstraints gbc_slider = new GridBagConstraints();
				gbc_slider.fill = GridBagConstraints.HORIZONTAL;
				gbc_slider.gridwidth = 3;
				gbc_slider.insets = new Insets(0, 0, 0, 5);
				gbc_slider.gridx = 1;
				gbc_slider.gridy = 2;
				add(slider, gbc_slider);
				

		
				tglbtnShowText = new JToggleButton("Show text");
				tglbtnShowText.setSelected(false);
				GridBagConstraints gbc_tglbtnShowText = new GridBagConstraints();
				gbc_tglbtnShowText.insets = new Insets(0, 0, 0, 5);
				gbc_tglbtnShowText.gridx = 6;
				gbc_tglbtnShowText.gridy = 2;
				add(tglbtnShowText, gbc_tglbtnShowText);
				tglbtnShowText.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (tglbtnShowText.isSelected())
						{
							String txt = jgfx.getIndFrameJSON(canvascount);
							textPane.setText(txt);
						}
						else
						{
							textPane.setText("");
						}
					}
				});
		
		
		
		tglbtnInvertBg = new JToggleButton("Invert BG");
		tglbtnInvertBg.setSelected(false);
		GridBagConstraints gbc_btnToggleBg = new GridBagConstraints();
		gbc_btnToggleBg.gridx = 7;
		gbc_btnToggleBg.gridy = 2;
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
    	JComboBox cb = (JComboBox)arg0.getSource();
        String pal = (String)cb.getSelectedItem();
        
    	jgfx.SetPalette(new YDKJPalettes().getPalettes().get(pal));
		currentimage = jgfx.getFrameImg(canvascount,jgfx.GetPalette());
		panel.setImage(currentimage);
        }
	public JackGraphic getGfx() {
		return jgfx;
	}

	public boolean isRaw() {
		// TODO Auto-generated method stub
		return false;
	}

}

