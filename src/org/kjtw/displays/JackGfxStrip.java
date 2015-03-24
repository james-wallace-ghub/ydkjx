package org.kjtw.displays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import org.kjtw.resources.YDKJPalettes;
import org.kjtw.structures.JackGraphic;
import org.kjtw.structures.JackRawImage;

import javax.swing.ScrollPaneConstants;

public class JackGfxStrip extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6957574225010914819L;
	private JackGFXPanel panel;
	private int canvascount;
	private GridBagConstraints gbc_panel;
	private BufferedImage currentimage;
	private List<JackRawImage> list;
	private JackGraphic jgfx;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JToggleButton tglbtnShowJs;
	private JSlider slider;
	private JToggleButton tglbtnInvertBg;
	private JComboBox comboBox;
	private JLabel label;
	/**
	 * @wbp.parser.constructor
	 */
	public JackGfxStrip(JackGraphic jg) {

		jgfx=jg;
		this.list=jgfx.getJri();
		canvascount=1;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{68, 71, 87, 79, 79, 58, 94, 79, 0};
		gridBagLayout.rowHeights = new int[]{375, 72, 30, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		if (list.size()< 1)
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
			currentimage = list.get(0).getImgout(jgfx.GetPalette());
		}

		panel = new JackGFXPanel(currentimage);
		panel.setBackground(Color.white);

		gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 8;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
				
						scrollPane = new JScrollPane();
						scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
						GridBagConstraints gbc_scrollPane = new GridBagConstraints();
						gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
						gbc_scrollPane.gridwidth = 8;
						gbc_scrollPane.fill = GridBagConstraints.BOTH;
						gbc_scrollPane.gridx = 0;
						gbc_scrollPane.gridy = 1;
						add(scrollPane, gbc_scrollPane);
						
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
		final JLabel lblFrameCount = new JLabel("Canvas "+(canvascount)+" of "+(list.size()));
		GridBagConstraints gbc_lblFrameCount = new GridBagConstraints();
		gbc_lblFrameCount.insets = new Insets(0, 0, 0, 5);
		gbc_lblFrameCount.gridwidth = 3;
		gbc_lblFrameCount.gridx = 4;
		gbc_lblFrameCount.gridy = 3;
		add(lblFrameCount, gbc_lblFrameCount);

				slider = new JSlider();
				GridBagConstraints gbc_slider = new GridBagConstraints();
				gbc_slider.fill = GridBagConstraints.HORIZONTAL;
				gbc_slider.gridwidth = 8;
				gbc_slider.insets = new Insets(0, 0, 5, 0);
				gbc_slider.gridx = 0;
				gbc_slider.gridy = 2;
				add(slider, gbc_slider);
				slider.setMajorTickSpacing(1);
				slider.setMaximum(list.size()-1);
				slider.setMinimum(1);
				slider.setValue(1);
				slider.setPaintTicks(true);
				slider.setSnapToTicks(true);
				slider.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						JSlider source = (JSlider)e.getSource();
						if (!source.getValueIsAdjusting()) {	
						canvascount = source.getValue();
						currentimage = list.get(canvascount).getImgout(jgfx.GetPalette());
						panel.setImage(currentimage);
						lblFrameCount.setText("Canvas "+(canvascount)+" of "+(list.size()-1));
						panel.revalidate();		
						}
					}
				});
		
		tglbtnShowJs = new JToggleButton("Show JS");
		tglbtnShowJs.setSelected(false);
		tglbtnShowJs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnShowJs.isSelected())
				{
					textPane.setText(jgfx.getTileJSON());
					
				}
				else
				{
					textPane.setText("");
				}
			}
		});
		
		label = new JLabel("Palette");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 3;
		add(label, gbc_label);
		
		String[] palettes = { "YDKJ 1", "YDKJ 2", "YDKJ 3", "YDKJ 4 (The Ride)", "HeadRush", "Offline", "Louder! Faster! Funnier!","Movies/TV/Sports" };

		comboBox = new JComboBox(palettes);
		comboBox.setSelectedIndex(1);
		comboBox.addActionListener(this);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 3;
		add(comboBox, gbc_comboBox);
		GridBagConstraints gbc_tglbtnShowJs = new GridBagConstraints();
		gbc_tglbtnShowJs.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnShowJs.gridx = 3;
		gbc_tglbtnShowJs.gridy = 3;
		add(tglbtnShowJs, gbc_tglbtnShowJs);
		
		
		
				tglbtnInvertBg = new JToggleButton("Invert BG");
				GridBagConstraints gbc_tglbtnInvertBg = new GridBagConstraints();
				gbc_tglbtnInvertBg.gridx = 7;
				gbc_tglbtnInvertBg.gridy = 3;
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
				
						add(tglbtnInvertBg, gbc_tglbtnInvertBg);

	}
	public JackGfxStrip() {
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
	public List<JackRawImage> getList() {
		return list;
	}

	public Color[] getStripPalette() {
		return jgfx.GetPalette();
	}
    @Override
    public void actionPerformed(ActionEvent arg0) {
    	JComboBox cb = (JComboBox)arg0.getSource();
        String pal = (String)cb.getSelectedItem();
        
    	new YDKJPalettes();
		jgfx.SetPalette(YDKJPalettes.getPalettes().get(pal));
		currentimage = jgfx.getFrameImg(canvascount,jgfx.GetPalette());
		panel.setImage(currentimage);
       }
	public JackGraphic getGfx() {
		return jgfx;
	}
	public boolean isRaw() {
		// TODO Auto-generated method stub
		return true;
	}
	public String getTileJSON() {
		// TODO Auto-generated method stub
		return jgfx.getTileJSON();
	}
	public String getFrameJSON() {
		// TODO Auto-generated method stub
		return jgfx.getFrameJSON();
	}

}

