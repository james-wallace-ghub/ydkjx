package org.kjtw.categories;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import org.kjtw.main.AudioPlayer;
import org.kjtw.main.SRFLoad;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.Font;

public class Gibberish extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7342045851117088195L;
	private JLabel Title;
	private JLabel Qtext;
	private JLabel txt1;
	private JLabel txt2;
	private JLabel txt3;
	private JButton btnPlayTitle;
	private JButton btnPlaypreamble;
	private JButton btnPlayQ;
	private JLabel btnAnswers;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton btnAllWrongAnswers;
	private JButton btnClosingRemark;
	private JCheckBox chckbxNewCheckBox;
	private Hashtable <String, byte[]> supplements;
	private SRFLoad QData;
	private int titleval;
	private int hintval;
	private JLabel lblNewLabel;
	private JButton btnRightAnswer;
	private JButton btnWrongAnswer;
	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public Gibberish(final QHeader qhd) throws IOException {
		QData = new SRFLoad(qhd.path);
		supplements = QData.getData(); 
		final String spelltext = "<html><body style='width:100%'>"+"Alternate spellings: "+new String(supplements.get("Wrds_128"));
		final String questiontext = "<html><body style='width:100%'>"+new String(supplements.get("STR_130"));

		titleval =0;
		hintval=0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {70, 244, 243, 2};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 48, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		GridBagConstraints gbc_btnPlayTitle = new GridBagConstraints();
		gbc_btnPlayTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlayTitle.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayTitle.gridx = 0;
		gbc_btnPlayTitle.gridy = 0;
		add(btnPlayTitle, gbc_btnPlayTitle);
		
		Title = new JLabel("Title here");
		GridBagConstraints gbc_Title = new GridBagConstraints();
		gbc_Title.gridwidth = 2;
		gbc_Title.insets = new Insets(0, 0, 5, 0);
		gbc_Title.fill = GridBagConstraints.HORIZONTAL;
		gbc_Title.gridx = 1;
		gbc_Title.gridy = 0;
		add(Title, gbc_Title);
		
		
		button_1 = new JButton("1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hintval ==0)
				{
					new Thread(new AudioPlayer(supplements.get("snd_5"))).start();					
				}
				else
				{
					new Thread(new AudioPlayer(supplements.get("snd_6"))).start();
				}
			}
		});
		
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
		gbc_btnPlaypreamble.gridy = 1;
		add(btnPlaypreamble, gbc_btnPlaypreamble);
		
		btnPlayQ = new JButton("Hints/Followups");
		btnPlayQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hintval++;
				if (hintval>1)
				{
					hintval=0;
				}
				switch (hintval)
				{
					case 0:
					default:
					{
						btnAnswers.setText("Hints");
						break;
					}
					case 1:
					{
						btnAnswers.setText("Followups");
						break;
					}
				}				
				
			}
		});
		GridBagConstraints gbc_btnPlayQ = new GridBagConstraints();
		gbc_btnPlayQ.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlayQ.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayQ.gridx = 0;
		gbc_btnPlayQ.gridy = 2;
		add(btnPlayQ, gbc_btnPlayQ);
		
				chckbxNewCheckBox = new JCheckBox("Show answer");
				GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
				gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
				gbc_chckbxNewCheckBox.gridx = 0;
				gbc_chckbxNewCheckBox.gridy = 3;
				add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		
		btnWrongAnswer = new JButton("Wrong Answer");
		GridBagConstraints gbc_btnWrongAnswer = new GridBagConstraints();
		gbc_btnWrongAnswer.insets = new Insets(0, 0, 5, 5);
		gbc_btnWrongAnswer.gridx = 0;
		gbc_btnWrongAnswer.gridy = 4;
		btnWrongAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_16"))).start();
			}
		});

		add(btnWrongAnswer, gbc_btnWrongAnswer);

		btnAnswers = new JLabel("Hints");
		GridBagConstraints gbc_btnAnswers = new GridBagConstraints();
		gbc_btnAnswers.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnswers.gridx = 0;
		gbc_btnAnswers.gridy = 5;
		add(btnAnswers, gbc_btnAnswers);
		
		lblNewLabel = new JLabel(new String(supplements.get("Gibr_128")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 5;
		add(lblNewLabel, gbc_lblNewLabel);
		
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 0;
		gbc_button_1.gridy = 6;
		add(button_1, gbc_button_1);
		
		
		button_2 = new JButton("2");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hintval ==0)
				{
					new Thread(new AudioPlayer(supplements.get("snd_7"))).start();					
				}
				else
				{
					new Thread(new AudioPlayer(supplements.get("snd_8"))).start();
				}
			}
		});
		
				txt1 = new JLabel(new String(supplements.get("Hnt1_128")));
				GridBagConstraints gbc_txt1 = new GridBagConstraints();
				gbc_txt1.gridwidth = 2;
				gbc_txt1.insets = new Insets(0, 0, 5, 0);
				gbc_txt1.fill = GridBagConstraints.HORIZONTAL;
				gbc_txt1.gridx = 1;
				gbc_txt1.gridy = 6;
				add(txt1, gbc_txt1);
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 0;
		gbc_button_2.gridy = 7;
		add(button_2, gbc_button_2);

		Qtext = new JLabel("questiontext");
		Qtext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_Qtext = new GridBagConstraints();
		gbc_Qtext.fill = GridBagConstraints.BOTH;
		gbc_Qtext.gridheight = 4;
		gbc_Qtext.gridwidth = 2;
		gbc_Qtext.insets = new Insets(0, 0, 5, 0);
		gbc_Qtext.gridx = 1;
		gbc_Qtext.gridy = 1;
		add(Qtext, gbc_Qtext);
		
		button_3 = new JButton("3");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hintval ==0)
				{
					new Thread(new AudioPlayer(supplements.get("snd_9"))).start();					
				}
				else
				{
					new Thread(new AudioPlayer(supplements.get("snd_10"))).start();
				}
			}
		});
		
				txt2 = new JLabel(new String(supplements.get("Hnt2_128")));
				GridBagConstraints gbc_txt2 = new GridBagConstraints();
				gbc_txt2.gridwidth = 2;
				gbc_txt2.insets = new Insets(0, 0, 5, 0);
				gbc_txt2.fill = GridBagConstraints.HORIZONTAL;
				gbc_txt2.gridx = 1;
				gbc_txt2.gridy = 7;
				add(txt2, gbc_txt2);
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 0;
		gbc_button_3.gridy = 8;
		add(button_3, gbc_button_3);
		
				txt3 = new JLabel(new String(supplements.get("Hnt3_128")));
				GridBagConstraints gbc_txt3 = new GridBagConstraints();
				gbc_txt3.gridwidth = 2;
				gbc_txt3.insets = new Insets(0, 0, 5, 0);
				gbc_txt3.fill = GridBagConstraints.HORIZONTAL;
				gbc_txt3.gridx = 1;
				gbc_txt3.gridy = 8;
				add(txt3, gbc_txt3);
		
		btnAllWrongAnswers = new JButton("Play 'all wrong' audio");
		btnAllWrongAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_14"))).start();
			}
		});
				
				btnRightAnswer = new JButton("Right Answer");
				GridBagConstraints gbc_btnRightAnswer = new GridBagConstraints();
				gbc_btnRightAnswer.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnRightAnswer.insets = new Insets(0, 0, 0, 5);
				gbc_btnRightAnswer.gridx = 0;
				gbc_btnRightAnswer.gridy = 9;
				btnRightAnswer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Thread(new AudioPlayer(supplements.get("snd_11"))).start();
					}
				});

				add(btnRightAnswer, gbc_btnRightAnswer);
		
				GridBagConstraints gbc_btnAllWrongAnswers = new GridBagConstraints();
				gbc_btnAllWrongAnswers.anchor = GridBagConstraints.WEST;
				gbc_btnAllWrongAnswers.insets = new Insets(0, 0, 0, 5);
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
		gbc_btnClosingRemark.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClosingRemark.gridx = 2;
		gbc_btnClosingRemark.gridy = 9;
		add(btnClosingRemark, gbc_btnClosingRemark);

		Qtext.setText(questiontext);
		Title.setText(qhd.title);

		chckbxNewCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED)
				{
					Qtext.setText(questiontext);
					lblNewLabel.setText(new String(supplements.get("Gibr_128")));
					validate();

				}
				else
				{
					Qtext.setText(spelltext);
					lblNewLabel.setText(new String(supplements.get("Ansr_128")));
					validate();
				}
			}
		});

		if (qhd.forced != null)
		{
			qhd.titlea= new String(supplements.get("STR_18"));
			qhd.titleb= new String(supplements.get("STR_19"));
		}
		
		if (supplements.get("snd_16")==null)
		{
			btnWrongAnswer.setEnabled(false);
		}

	}
}
