package org.kjtw.categories;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import org.kjtw.process.AudioPlayer;
import org.kjtw.process.SRFLoad;
import org.kjtw.structures.QHeader;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JLabel;

public class FiberOpticFieldTrip extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6720303554134016701L;
	private JLabel Title;
	private JLabel Qtext;
	private JLabel txt1;
	private JLabel txt2;
	private JLabel txt3;
	private JLabel txt4;
	private JButton btnPlayTitle;
	private JButton btnPlaypreamble;
	private JButton btnPlayQ;
	private JButton btnAnswers;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton btnAllWrongAnswers;
	private JButton btnClosingRemark;
	private JCheckBox chckbxNewCheckBox;
	private Hashtable <String, byte[]> supplements;
	private SRFLoad QData;
	private JLabel lblNewLabel;
	private JButton btnToggleAltTitles;
	private int titleval;
	private JButton button;
	private JButton button_5;
	private JButton button_6;
	private JLabel label;
	private JButton button_7;
	private JButton button_8;
	private JButton button_10;
	private JButton button_9;
	private JButton button_11;
	private JButton button_12;
	private JButton button_13;
	private JButton button_14;
	private JButton button_15;
	private JButton button_16;
	private JButton button_17;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public FiberOpticFieldTrip(final QHeader qhd) throws IOException {
		supplements = new Hashtable<String, byte[]>();
		QData = new SRFLoad(qhd.getPath());
		supplements = QData.getData();
		
		if (qhd.getForced() != null)
		{
			qhd.setTitlea(new String(supplements.get("STR_18")));
			qhd.setTitleb(new String(supplements.get("STR_19")));
		}
		titleval =0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {79, 93, 51, 77, 134, 132, 2};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		btnPlayTitle = new JButton("Play Title");
		btnPlayTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (titleval)
				{
					case 0:
					default:
					{
						new Thread(new AudioPlayer(supplements.get("snd_1"))).start();
						break;
					}
					case 1:
					{
						new Thread(new AudioPlayer(supplements.get("snd_18"))).start();
						break;
					}
					case 2:
					{
						new Thread(new AudioPlayer(supplements.get("snd_19"))).start();
						break;
					}
				
				}
			}
		});
		
		lblNewLabel = new JLabel("Difficulty:"+qhd.getDiff());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 4;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		button = new JButton("Intro title");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 1;
		add(button, gbc_button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_14"))).start();
			}
		});

		label = new JLabel(qhd.getTitle());
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.NORTH;
		gbc_label.gridheight = 2;
		gbc_label.gridwidth = 2;
		gbc_label.fill = GridBagConstraints.HORIZONTAL;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		add(label, gbc_label);
		GridBagConstraints gbc_btnPlayTitle = new GridBagConstraints();
		gbc_btnPlayTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlayTitle.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayTitle.gridx = 3;
		gbc_btnPlayTitle.gridy = 1;
		add(btnPlayTitle, gbc_btnPlayTitle);
		
		Title = new JLabel(new String(supplements.get("STR_1")));
		GridBagConstraints gbc_Title = new GridBagConstraints();
		gbc_Title.gridwidth = 2;
		gbc_Title.insets = new Insets(0, 0, 5, 0);
		gbc_Title.fill = GridBagConstraints.HORIZONTAL;
		gbc_Title.gridx = 4;
		gbc_Title.gridy = 1;
		add(Title, gbc_Title);
		
		btnPlaypreamble = new JButton("Preamble");
		btnPlaypreamble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_2"))).start();
			}
		});
		GridBagConstraints gbc_btnPlaypreamble = new GridBagConstraints();
		gbc_btnPlaypreamble.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlaypreamble.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlaypreamble.gridx = 3;
		gbc_btnPlaypreamble.gridy = 2;
		add(btnPlaypreamble, gbc_btnPlaypreamble);
		
		btnPlayQ = new JButton("Play Q");
		btnPlayQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_3"))).start();
			}
		});
		
		Qtext = new JLabel("<html><body style='width:100%'>"+new String(supplements.get("STR_2")));
		GridBagConstraints gbc_Qtext = new GridBagConstraints();
		gbc_Qtext.gridheight = 2;
		gbc_Qtext.gridwidth = 2;
		gbc_Qtext.insets = new Insets(0, 0, 5, 0);
		gbc_Qtext.fill = GridBagConstraints.BOTH;
		gbc_Qtext.gridx = 4;
		gbc_Qtext.gridy = 2;
		add(Qtext, gbc_Qtext);
		
		button_5 = new JButton("Location 1");
		GridBagConstraints gbc_button_5 = new GridBagConstraints();
		gbc_button_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_5.insets = new Insets(0, 0, 5, 5);
		gbc_button_5.gridx = 0;
		gbc_button_5.gridy = 3;
		add(button_5, gbc_button_5);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_15"))).start();
			}
		});
		
				label_1 = new JLabel(new String(supplements.get("STR_7")));
				GridBagConstraints gbc_label_1 = new GridBagConstraints();
				gbc_label_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_label_1.gridwidth = 2;
				gbc_label_1.insets = new Insets(0, 0, 5, 5);
				gbc_label_1.gridx = 1;
				gbc_label_1.gridy = 3;
				add(label_1, gbc_label_1);
		GridBagConstraints gbc_btnPlayQ = new GridBagConstraints();
		gbc_btnPlayQ.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlayQ.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayQ.gridx = 3;
		gbc_btnPlayQ.gridy = 3;
		add(btnPlayQ, gbc_btnPlayQ);
		
		btnAnswers = new JButton("Answers");
		btnAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_5"))).start();
			}
		});
		
		button_6 = new JButton("Location 2");
		GridBagConstraints gbc_button_6 = new GridBagConstraints();
		gbc_button_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_6.insets = new Insets(0, 0, 5, 5);
		gbc_button_6.gridx = 0;
		gbc_button_6.gridy = 4;
		add(button_6, gbc_button_6);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_16"))).start();
			}
		});
		
				label_2 = new JLabel(new String(supplements.get("STR_8")));
				GridBagConstraints gbc_label_2 = new GridBagConstraints();
				gbc_label_2.fill = GridBagConstraints.HORIZONTAL;
				gbc_label_2.gridwidth = 2;
				gbc_label_2.insets = new Insets(0, 0, 5, 5);
				gbc_label_2.gridx = 1;
				gbc_label_2.gridy = 4;
				add(label_2, gbc_label_2);
		GridBagConstraints gbc_btnAnswers = new GridBagConstraints();
		gbc_btnAnswers.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAnswers.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnswers.gridx = 3;
		gbc_btnAnswers.gridy = 4;
		add(btnAnswers, gbc_btnAnswers);
		
		button_1 = new JButton("1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_7"))).start();
			}
		});
		
		button_7 = new JButton("Call 1");
		GridBagConstraints gbc_button_7 = new GridBagConstraints();
		gbc_button_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_7.insets = new Insets(0, 0, 5, 5);
		gbc_button_7.gridx = 0;
		gbc_button_7.gridy = 5;
		add(button_7, gbc_button_7);
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_20"))).start();
			}
		});
		
		label_3 = new JLabel(new String(supplements.get("STR_9")));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_3.gridwidth = 2;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 5;
		add(label_3, gbc_label_3);

		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 3;
		gbc_button_1.gridy = 5;
		add(button_1, gbc_button_1);
		
		txt1 = new JLabel(new String(supplements.get("STR_3")));
		GridBagConstraints gbc_txt1 = new GridBagConstraints();
		gbc_txt1.gridwidth = 2;
		gbc_txt1.insets = new Insets(0, 0, 5, 0);
		gbc_txt1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt1.gridx = 4;
		gbc_txt1.gridy = 5;
		add(txt1, gbc_txt1);
		
		button_2 = new JButton("2");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_8"))).start();
			}
		});
		
		button_8 = new JButton("Call 2");
		GridBagConstraints gbc_button_8 = new GridBagConstraints();
		gbc_button_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_8.insets = new Insets(0, 0, 5, 5);
		gbc_button_8.gridx = 0;
		gbc_button_8.gridy = 6;
		add(button_8, gbc_button_8);
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_17"))).start();
			}
		});

		button_10 = new JButton("Return to Q");
		GridBagConstraints gbc_button_10 = new GridBagConstraints();
		gbc_button_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_10.insets = new Insets(0, 0, 5, 5);
		gbc_button_10.gridx = 1;
		gbc_button_10.gridy = 6;
		add(button_10, gbc_button_10);
		
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_18"))).start();
			}
		});

		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 3;
		gbc_button_2.gridy = 6;
		add(button_2, gbc_button_2);
		
		txt2 = new JLabel(new String(supplements.get("STR_4")));
		GridBagConstraints gbc_txt2 = new GridBagConstraints();
		gbc_txt2.gridwidth = 2;
		gbc_txt2.insets = new Insets(0, 0, 5, 0);
		gbc_txt2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt2.gridx = 4;
		gbc_txt2.gridy = 6;
		add(txt2, gbc_txt2);
		
		button_3 = new JButton("3");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_9"))).start();
			}
		});
		
		button_14 = new JButton("Call 3");
		GridBagConstraints gbc_button_14 = new GridBagConstraints();
		gbc_button_14.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_14.insets = new Insets(0, 0, 5, 5);
		gbc_button_14.gridx = 0;
		gbc_button_14.gridy = 7;
		add(button_14, gbc_button_14);
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_27"))).start();
			}
		});

		button_9 = new JButton("Reintroduce");
		GridBagConstraints gbc_button_9 = new GridBagConstraints();
		gbc_button_9.anchor = GridBagConstraints.WEST;
		gbc_button_9.insets = new Insets(0, 0, 5, 5);
		gbc_button_9.gridx = 1;
		gbc_button_9.gridy = 7;
		add(button_9, gbc_button_9);
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_22"))).start();
			}
		});

		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 3;
		gbc_button_3.gridy = 7;
		add(button_3, gbc_button_3);
		
		txt3 = new JLabel(new String(supplements.get("STR_5")));
		GridBagConstraints gbc_txt3 = new GridBagConstraints();
		gbc_txt3.gridwidth = 2;
		gbc_txt3.insets = new Insets(0, 0, 5, 0);
		gbc_txt3.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt3.gridx = 4;
		gbc_txt3.gridy = 7;
		add(txt3, gbc_txt3);
		
		button_4 = new JButton("4");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_10"))).start();
			}
		});
		
		button_15 = new JButton("Call 4");
		GridBagConstraints gbc_button_15 = new GridBagConstraints();
		gbc_button_15.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_15.insets = new Insets(0, 0, 5, 5);
		gbc_button_15.gridx = 0;
		gbc_button_15.gridy = 8;
		add(button_15, gbc_button_15);
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_28"))).start();
			}
		});

		button_11 = new JButton("Value");
		GridBagConstraints gbc_button_11 = new GridBagConstraints();
		gbc_button_11.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_11.insets = new Insets(0, 0, 5, 5);
		gbc_button_11.gridx = 1;
		gbc_button_11.gridy = 8;
		add(button_11, gbc_button_11);
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_23"))).start();
			}
		});

		
		GridBagConstraints gbc_button_4 = new GridBagConstraints();
		gbc_button_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_4.insets = new Insets(0, 0, 5, 5);
		gbc_button_4.gridx = 3;
		gbc_button_4.gridy = 8;
		add(button_4, gbc_button_4);
		
		txt4 = new JLabel(new String(supplements.get("STR_6")));
		GridBagConstraints gbc_txt4 = new GridBagConstraints();
		gbc_txt4.gridwidth = 2;
		gbc_txt4.insets = new Insets(0, 0, 5, 0);
		gbc_txt4.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt4.gridx = 4;
		gbc_txt4.gridy = 8;
		add(txt4, gbc_txt4);
		
		button_16 = new JButton("Call 5");
		GridBagConstraints gbc_button_16 = new GridBagConstraints();
		gbc_button_16.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_16.insets = new Insets(0, 0, 5, 5);
		gbc_button_16.gridx = 0;
		gbc_button_16.gridy = 9;
		add(button_16, gbc_button_16);
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_29"))).start();
			}
		});
		
		btnAllWrongAnswers = new JButton("Play 'all wrong' audio");
		btnAllWrongAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_11"))).start();
			}
		});
				
						button_12 = new JButton("End Game");
						GridBagConstraints gbc_button_12 = new GridBagConstraints();
						gbc_button_12.fill = GridBagConstraints.HORIZONTAL;
						gbc_button_12.insets = new Insets(0, 0, 5, 5);
						gbc_button_12.gridx = 3;
						gbc_button_12.gridy = 9;
						add(button_12, gbc_button_12);
						button_12.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								new Thread(new AudioPlayer(supplements.get("snd_12"))).start();
							}
						});
		
				GridBagConstraints gbc_btnAllWrongAnswers = new GridBagConstraints();
				gbc_btnAllWrongAnswers.anchor = GridBagConstraints.WEST;
				gbc_btnAllWrongAnswers.insets = new Insets(0, 0, 5, 5);
				gbc_btnAllWrongAnswers.gridx = 4;
				gbc_btnAllWrongAnswers.gridy = 9;
				add(btnAllWrongAnswers, gbc_btnAllWrongAnswers);
		
		btnClosingRemark = new JButton("Closing remark");
		btnClosingRemark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_6"))).start();
			}
		});
		GridBagConstraints gbc_btnClosingRemark = new GridBagConstraints();
		gbc_btnClosingRemark.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClosingRemark.insets = new Insets(0, 0, 5, 0);
		gbc_btnClosingRemark.gridx = 5;
		gbc_btnClosingRemark.gridy = 9;
		add(btnClosingRemark, gbc_btnClosingRemark);
		
		button_17 = new JButton("Call 6");
		GridBagConstraints gbc_button_17 = new GridBagConstraints();
		gbc_button_17.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_17.insets = new Insets(0, 0, 0, 5);
		gbc_button_17.gridx = 0;
		gbc_button_17.gridy = 10;
		add(button_17, gbc_button_17);
		button_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_21"))).start();
			}
		});
		
		chckbxNewCheckBox = new JCheckBox("Show answer");
		chckbxNewCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txt1.setForeground(Color.BLACK);
				txt2.setForeground(Color.BLACK);
				txt3.setForeground(Color.BLACK);
				txt4.setForeground(Color.BLACK);
				if (e.getStateChange() == ItemEvent.DESELECTED)
				{
				}
				else
				{
					switch (qhd.getAnswer())
					{
						case 1:
						txt1.setForeground(Color.GREEN);
						break;
						case 2:
						txt2.setForeground(Color.GREEN);
						break;
						case 3:
						txt3.setForeground(Color.GREEN);
						break;
						case 4:
						txt4.setForeground(Color.GREEN);
						break;
						
					}
				}
			}
		});
				
						button_13 = new JButton("Hangup");
						GridBagConstraints gbc_button_13 = new GridBagConstraints();
						gbc_button_13.fill = GridBagConstraints.HORIZONTAL;
						gbc_button_13.insets = new Insets(0, 0, 0, 5);
						gbc_button_13.gridx = 3;
						gbc_button_13.gridy = 10;
						add(button_13, gbc_button_13);
						button_13.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								new Thread(new AudioPlayer(supplements.get("snd_19"))).start();
							}
						});
		
				GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
				gbc_chckbxNewCheckBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 0, 5);
				gbc_chckbxNewCheckBox.gridx = 4;
				gbc_chckbxNewCheckBox.gridy = 10;
				add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);

		if (qhd.getTitlea() != null)
		{
			btnPlayTitle.setText("Play Title 1");
			btnToggleAltTitles = new JButton("Toggle alt titles");
			btnToggleAltTitles.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					titleval++;
					if (titleval >2)
					{
						titleval =0;
					}
					switch (titleval)
					{
						case 0:
						default:
						{
							btnPlayTitle.setText("Play Title 1");
							Title.setText(qhd.getTitle());
							break;
						}
						case 1:
						{
							btnPlayTitle.setText("Play Title 2");
							Title.setText(qhd.getTitlea());
							break;
						}
						case 2:
						{
							btnPlayTitle.setText("Play Title 3");
							Title.setText(qhd.getTitleb());
							break;
						}
					}
				}
				
			});
			GridBagConstraints gbc_btnToggleAltTitles = new GridBagConstraints();
			gbc_btnToggleAltTitles.insets = new Insets(0, 0, 5, 5);
			gbc_btnToggleAltTitles.gridx = 0;
			gbc_btnToggleAltTitles.gridy = 0;
			add(btnToggleAltTitles, gbc_btnToggleAltTitles);
		}		

	
	}
}
