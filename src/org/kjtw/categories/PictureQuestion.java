package org.kjtw.categories;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import org.kjtw.main.AudioPlayer;
import org.kjtw.main.SRFLoad;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JLabel;

public class PictureQuestion extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9154322423323746517L;
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
	private Hashtable<String, byte[]> supplements;
	private Hashtable <String, BufferedImage> gfx;
	private SRFLoad QData;
	private JLabel lblNewLabel;
	private JButton btnToggleAltTitles;
	private int titleval;
	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public PictureQuestion(final QHeader qhd) throws IOException {
		supplements = new Hashtable<String, byte[]>();
		JFrame JackPic = new JFrame();
		QData = new SRFLoad(qhd.path);
		supplements = QData.getData();
		gfx = QData.getGfx();

		JLabel lblNewLabel_1 = new JLabel("");
		ImageIcon pic = new ImageIcon(gfx.get("off4_21500"));
		lblNewLabel_1.setIcon(pic);
		JackPic.getContentPane().add(lblNewLabel_1);
		JackPic.setBounds(0, 0, pic.getIconWidth()+20, pic.getIconHeight()+30);
		JackPic.setTitle("Question image");
		JackPic.setVisible(true);
		
		if (qhd.forced != null)
		{
			qhd.titlea= new String(supplements.get("STR_18"));
			qhd.titleb= new String(supplements.get("STR_19"));
		}
		titleval =0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {70, 187, 161, 2};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
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
		
		lblNewLabel = new JLabel("Value:"+qhd.value+"000");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_btnPlayTitle = new GridBagConstraints();
		gbc_btnPlayTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlayTitle.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayTitle.gridx = 0;
		gbc_btnPlayTitle.gridy = 1;
		add(btnPlayTitle, gbc_btnPlayTitle);
		
		Title = new JLabel(qhd.title);
		GridBagConstraints gbc_Title = new GridBagConstraints();
		gbc_Title.gridwidth = 2;
		gbc_Title.insets = new Insets(0, 0, 5, 0);
		gbc_Title.fill = GridBagConstraints.HORIZONTAL;
		gbc_Title.gridx = 1;
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
		gbc_btnPlaypreamble.gridx = 0;
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
		gbc_Qtext.gridx = 1;
		gbc_Qtext.gridy = 2;
		add(Qtext, gbc_Qtext);
		GridBagConstraints gbc_btnPlayQ = new GridBagConstraints();
		gbc_btnPlayQ.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlayQ.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayQ.gridx = 0;
		gbc_btnPlayQ.gridy = 3;
		add(btnPlayQ, gbc_btnPlayQ);
		
		btnAnswers = new JButton("Answers");
		btnAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_5"))).start();
			}
		});
		GridBagConstraints gbc_btnAnswers = new GridBagConstraints();
		gbc_btnAnswers.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAnswers.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnswers.gridx = 0;
		gbc_btnAnswers.gridy = 4;
		add(btnAnswers, gbc_btnAnswers);
		
		button_1 = new JButton("1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_7"))).start();
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 0;
		gbc_button_1.gridy = 5;
		add(button_1, gbc_button_1);
		
		txt1 = new JLabel(new String(supplements.get("STR_3")));
		GridBagConstraints gbc_txt1 = new GridBagConstraints();
		gbc_txt1.gridwidth = 2;
		gbc_txt1.insets = new Insets(0, 0, 5, 0);
		gbc_txt1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt1.gridx = 1;
		gbc_txt1.gridy = 5;
		add(txt1, gbc_txt1);
		
		button_2 = new JButton("2");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_8"))).start();
			}
		});
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 0;
		gbc_button_2.gridy = 6;
		add(button_2, gbc_button_2);
		
		txt2 = new JLabel(new String(supplements.get("STR_4")));
		GridBagConstraints gbc_txt2 = new GridBagConstraints();
		gbc_txt2.gridwidth = 2;
		gbc_txt2.insets = new Insets(0, 0, 5, 0);
		gbc_txt2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt2.gridx = 1;
		gbc_txt2.gridy = 6;
		add(txt2, gbc_txt2);
		
		button_3 = new JButton("3");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_9"))).start();
			}
		});
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 0;
		gbc_button_3.gridy = 7;
		add(button_3, gbc_button_3);
		
		txt3 = new JLabel(new String(supplements.get("STR_5")));
		GridBagConstraints gbc_txt3 = new GridBagConstraints();
		gbc_txt3.gridwidth = 2;
		gbc_txt3.insets = new Insets(0, 0, 5, 0);
		gbc_txt3.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt3.gridx = 1;
		gbc_txt3.gridy = 7;
		add(txt3, gbc_txt3);
		
		button_4 = new JButton("4");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_10"))).start();
			}
		});
		GridBagConstraints gbc_button_4 = new GridBagConstraints();
		gbc_button_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_4.insets = new Insets(0, 0, 5, 5);
		gbc_button_4.gridx = 0;
		gbc_button_4.gridy = 8;
		add(button_4, gbc_button_4);
		
		txt4 = new JLabel(new String(supplements.get("STR_6")));
		GridBagConstraints gbc_txt4 = new GridBagConstraints();
		gbc_txt4.gridwidth = 2;
		gbc_txt4.insets = new Insets(0, 0, 5, 0);
		gbc_txt4.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt4.gridx = 1;
		gbc_txt4.gridy = 8;
		add(txt4, gbc_txt4);
		
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
					switch (qhd.answer)
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
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 9;
		add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		
		btnAllWrongAnswers = new JButton("Play 'all wrong' audio");
		btnAllWrongAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_11"))).start();
			}
		});
		
				GridBagConstraints gbc_btnAllWrongAnswers = new GridBagConstraints();
				gbc_btnAllWrongAnswers.anchor = GridBagConstraints.WEST;
				gbc_btnAllWrongAnswers.insets = new Insets(0, 0, 5, 5);
				gbc_btnAllWrongAnswers.gridx = 1;
				gbc_btnAllWrongAnswers.gridy = 9;
				add(btnAllWrongAnswers, gbc_btnAllWrongAnswers);
		
		btnClosingRemark = new JButton("Closing remark");
		btnClosingRemark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_6"))).start();
			}
		});
		GridBagConstraints gbc_btnClosingRemark = new GridBagConstraints();
		gbc_btnClosingRemark.anchor = GridBagConstraints.WEST;
		gbc_btnClosingRemark.insets = new Insets(0, 0, 5, 0);
		gbc_btnClosingRemark.gridx = 2;
		gbc_btnClosingRemark.gridy = 9;
		add(btnClosingRemark, gbc_btnClosingRemark);

		if (qhd.titlea != null)
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
							Title.setText(qhd.title);
							break;
						}
						case 1:
						{
							btnPlayTitle.setText("Play Title 2");
							Title.setText(qhd.titlea);
							break;
						}
						case 2:
						{
							btnPlayTitle.setText("Play Title 3");
							Title.setText(qhd.titleb);
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
