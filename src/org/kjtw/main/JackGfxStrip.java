package org.kjtw.main;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JLabel;

public class JackGfxStrip extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6957574225010914819L;
	private JackGFXPanel panel;
	private int canvascount;
	private GridBagConstraints gbc_panel;
	private BufferedImage currentimage;
	private List<JackRawImage> list;
	public JackGfxStrip(final List<JackRawImage> lst) {
		this.list=lst;
		canvascount=0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{54, 27, 428, 0, 33, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		currentimage = list.get(0).getImgout();
		
		panel = new JackGFXPanel(currentimage);

		gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 5;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);

		final JLabel lblFrameCount = new JLabel("Canvas "+(canvascount+1)+" of "+list.size());
		GridBagConstraints gbc_lblFrameCount = new GridBagConstraints();
		gbc_lblFrameCount.insets = new Insets(0, 0, 0, 5);
		gbc_lblFrameCount.gridx = 2;
		gbc_lblFrameCount.gridy = 1;
		add(lblFrameCount, gbc_lblFrameCount);

		JButton button = new JButton("< 5");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvascount -=5;
				if (canvascount <0)
				{
					canvascount =0;
				}
				currentimage = list.get(canvascount).getImgout();
				panel.setImage(currentimage);
				lblFrameCount.setText("Canvas "+(canvascount+1)+" of "+list.size());
				panel.revalidate();
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 1;
		add(button, gbc_button);
		
		JButton button_1 = new JButton("< 1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvascount --;
				if (canvascount <0)
				{
					canvascount =0;
				}
				currentimage = list.get(canvascount).getImgout();
				panel.setImage(currentimage);
				lblFrameCount.setText("Canvas "+(canvascount+1)+" of "+list.size());
				panel.revalidate();
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 0, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 1;
		add(button_1, gbc_button_1);
		
		JButton button_2 = new JButton("> 5");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvascount +=5;
				if (canvascount >list.size()-1)
				{
					canvascount =list.size()-1;
				}
				currentimage = list.get(canvascount).getImgout();
				panel.setImage(currentimage);
				lblFrameCount.setText("Canvas "+(canvascount+1)+" of "+list.size());
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
				currentimage = list.get(canvascount).getImgout();
				panel.setImage(currentimage);
				lblFrameCount.setText("Canvas "+(canvascount+1)+" of "+list.size());
				panel.revalidate();
			}
		});
		

		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.insets = new Insets(0, 0, 0, 5);
		gbc_button_3.gridx = 3;
		gbc_button_3.gridy = 1;
		add(button_3, gbc_button_3);

		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_2.gridx = 4;
		gbc_button_2.gridy = 1;
		add(button_2, gbc_button_2);
	}
	public JackGfxStrip() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{54, 27, 428, 0, 33, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	public int getCanvasCount()
	{
		return canvascount;
	}
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return currentimage;
	}
	public List<JackRawImage> getList() {
		// TODO Auto-generated method stub
		return list;
	}

}
