package org.kjtw.main;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JLabel;

public class JackGfxStrip extends JPanel {
	private JackGFXPanel panel;
	private int canvascount;
	private GridBagConstraints gbc_panel;
	public JackGfxStrip(final List<JackRawImage> list) {
		canvascount=0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{54, 27, 0, 0, 0, 0, 0, 64, 0, 0, 0, 0, 0, 0, 0, 0, 33, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		panel = new JackGFXPanel(list.get(0).getImgout());
		gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 16;
		gbc_panel.gridwidth = 17;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);

		final JLabel lblFrameCount = new JLabel("Canvas "+(canvascount+1)+" of "+list.size());
		GridBagConstraints gbc_lblFrameCount = new GridBagConstraints();
		gbc_lblFrameCount.gridwidth = 13;
		gbc_lblFrameCount.insets = new Insets(0, 0, 0, 5);
		gbc_lblFrameCount.gridx = 2;
		gbc_lblFrameCount.gridy = 16;
		add(lblFrameCount, gbc_lblFrameCount);

		JButton button = new JButton("< 5");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvascount -=5;
				if (canvascount <0)
				{
					canvascount =0;
				}
				panel.setImage(list.get(canvascount).getImgout());
				lblFrameCount.setText("Canvas "+(canvascount+1)+" of "+list.size());
				panel.revalidate();
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 16;
		add(button, gbc_button);
		
		JButton button_1 = new JButton("< 1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvascount --;
				if (canvascount <0)
				{
					canvascount =0;
				}
				panel.setImage(list.get(canvascount).getImgout());
				lblFrameCount.setText("Canvas "+(canvascount+1)+" of "+list.size());
				panel.revalidate();
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 0, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 16;
		add(button_1, gbc_button_1);
		
		JButton button_2 = new JButton("> 5");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvascount +=5;
				if (canvascount >list.size())
				{
					canvascount =list.size();
				}
				panel.setImage(list.get(canvascount).getImgout());
				lblFrameCount.setText("Canvas "+(canvascount+1)+" of "+list.size());
				panel.revalidate();
			}
		});
		
		JButton button_3 = new JButton("> 1");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvascount ++;
				if (canvascount >list.size())
				{
					canvascount =list.size();
				}
				panel.setImage(list.get(canvascount).getImgout());
				lblFrameCount.setText("Canvas "+(canvascount+1)+" of "+list.size());
				panel.revalidate();
			}
		});
		

		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.insets = new Insets(0, 0, 0, 5);
		gbc_button_3.gridx = 15;
		gbc_button_3.gridy = 16;
		add(button_3, gbc_button_3);

		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.gridx = 16;
		gbc_button_2.gridy = 16;
		add(button_2, gbc_button_2);
	}

}
