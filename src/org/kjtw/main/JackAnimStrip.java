package org.kjtw.main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;

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
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class JackAnimStrip extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1689714429602155854L;
	private JackGFXPanel panel;
	private int canvascount;
	private GridBagConstraints gbc_panel;
	private BufferedImage currentimage;
	private List<BufferedImage> list;
	private JackGraphic jgfx;
	private JRadioButton rdbtnYdkjPalette;
	private AbstractButton rdbtnYdkjPalette_1;
	private JRadioButton rdbtnYdkjPalette_2;
	private JRadioButton rdbtnYdkjPalette_3;
	private JRadioButton rdbtnHeadrushPalette;
	private JRadioButton rdbtnOffline;
	private JRadioButton rdbtnLff;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	/**
	 * @wbp.parser.constructor
	 */
	public JackAnimStrip(JackGraphic jg) {

		jgfx=jg;
		this.list=jgfx.toFrames(jgfx.GetPalette());
		canvascount=0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{68, 71, 87, 79, 79, 58, 94, 79, 0};
		gridBagLayout.rowHeights = new int[]{0, 444, 30, 50, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		final JLabel lblFrameCount = new JLabel("Frame "+(canvascount+1)+" of "+list.size());
		GridBagConstraints gbc_lblFrameCount = new GridBagConstraints();
		gbc_lblFrameCount.gridwidth = 2;
		gbc_lblFrameCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblFrameCount.gridx = 3;
		gbc_lblFrameCount.gridy = 2;
		add(lblFrameCount, gbc_lblFrameCount);
		
				JButton button = new JButton("< 5");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						canvascount -=5;
						if (canvascount <0)
						{
							canvascount =0;
						}
						currentimage = list.get(canvascount);
						panel.setImage(currentimage);
						lblFrameCount.setText("Frame "+(canvascount+1)+" of "+list.size());
						panel.revalidate();
					}
				});
				GridBagConstraints gbc_button = new GridBagConstraints();
				gbc_button.fill = GridBagConstraints.HORIZONTAL;
				gbc_button.insets = new Insets(0, 0, 5, 5);
				gbc_button.gridx = 0;
				gbc_button.gridy = 2;
				add(button, gbc_button);
		
		JButton button_1 = new JButton("< 1");
		button_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				canvascount --;
				if (canvascount <0)
				{
					canvascount =0;
				}
				currentimage = list.get(canvascount);
				panel.setImage(currentimage);
				lblFrameCount.setText("Frame "+(canvascount+1)+" of "+list.size());
				panel.revalidate();
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 2;
		add(button_1, gbc_button_1);

		if (list.isEmpty())
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
			currentimage = list.get(canvascount);
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

		gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 8;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
				
				JButton button_2 = new JButton("> 5");
				button_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						canvascount +=5;
						if (canvascount >list.size()-1)
						{
							canvascount =list.size()-1;
						}
						currentimage = list.get(canvascount);
						panel.setImage(currentimage);
						lblFrameCount.setText("Frame "+(canvascount+1)+" of "+list.size());
						panel.revalidate();
					}
				});
						
						
						JButton button_3 = new JButton("> 1");
						button_3.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								canvascount ++;
								if (canvascount >list.size()-1)
								{
									canvascount =list.size()-1;
								}
								currentimage = list.get(canvascount);
								panel.setImage(currentimage);
								lblFrameCount.setText("Frame "+(canvascount+1)+" of "+list.size());
								panel.revalidate();
							}
						});
						

						GridBagConstraints gbc_button_3 = new GridBagConstraints();
						gbc_button_3.fill = GridBagConstraints.HORIZONTAL;
						gbc_button_3.insets = new Insets(0, 0, 5, 5);
						gbc_button_3.gridx = 6;
						gbc_button_3.gridy = 2;
						add(button_3, gbc_button_3);
				
						GridBagConstraints gbc_button_2 = new GridBagConstraints();
						gbc_button_2.insets = new Insets(0, 0, 5, 0);
						gbc_button_2.fill = GridBagConstraints.HORIZONTAL;
						gbc_button_2.gridx = 7;
						gbc_button_2.gridy = 2;
						add(button_2, gbc_button_2);
				
				
				scrollPane = new JScrollPane();
				GridBagConstraints gbc_scrollPane = new GridBagConstraints();
				gbc_scrollPane.gridwidth = 8;
				gbc_scrollPane.fill = GridBagConstraints.BOTH;
				gbc_scrollPane.gridx = 0;
				gbc_scrollPane.gridy = 3;
				add(scrollPane, gbc_scrollPane);
				
				textPane = new JTextPane();
				textPane.setEditable(false);
				scrollPane.setViewportView(textPane);

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
    	list = jgfx.toFrames(jgfx.GetPalette());
		currentimage = list.get(canvascount);
		panel.setImage(currentimage);
        }
	public JackGraphic getGfx() {
		return jgfx;
	}

}

