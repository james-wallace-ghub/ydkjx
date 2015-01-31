package org.kjtw.categories;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import org.kjtw.main.AudioPlayer;
import org.kjtw.main.SRFLoad;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JLabel;
import java.awt.Font;

public class DisorDat extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3488557141705808578L;
	private JLabel Title;
	private JButton btnPlayTitle;
	private JButton btnPlaypreamble;
	private JButton a1;
	private JCheckBox chckbxNewCheckBox;
	private Hashtable <String, byte[]> supplements;
	private Hashtable <String, String[]> strings;
	private SRFLoad QData;
	private int titleval;
	private JButton btnPreamble;
	private JButton btnOption;
	private JButton btnOption_1;
	private JLabel lblOrBoth;
	private JButton a2;
	private JButton a3;
	private JButton a4;
	private JButton a5;
	private JButton a6;
	private JButton a7;
	private JLabel lblNewLabel;
	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public DisorDat(final QHeader qhd) throws IOException {
		supplements = new Hashtable<String, byte[]>();
		QData = new SRFLoad(qhd.path);
		supplements = QData.getData(); 
		strings = QData.getStrs();
		String[] strs = strings.get("STR#_3");
		final byte[] ans = supplements.get("ANS#_4");
		titleval =0;
		
		final Color Opt1 = new Color(255, 215, 0);
		final Color Opt2 = new Color(0, 0, 255);
		final Color Opt3 = new Color(0, 128, 0);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {160, 150, 150, 2};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		GridBagConstraints gbc_btnPlayTitle = new GridBagConstraints();
		gbc_btnPlayTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlayTitle.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlayTitle.gridx = 0;
		gbc_btnPlayTitle.gridy = 0;
		add(btnPlayTitle, gbc_btnPlayTitle);
		
		Title = new JLabel(qhd.title);
		GridBagConstraints gbc_Title = new GridBagConstraints();
		gbc_Title.gridwidth = 2;
		gbc_Title.insets = new Insets(0, 0, 5, 0);
		gbc_Title.fill = GridBagConstraints.HORIZONTAL;
		gbc_Title.gridx = 1;
		gbc_Title.gridy = 0;
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
		gbc_btnPlaypreamble.gridy = 1;
		add(btnPlaypreamble, gbc_btnPlaypreamble);
		
		btnPreamble = new JButton("Preamble 2");
		GridBagConstraints gbc_btnPreamble = new GridBagConstraints();
		gbc_btnPreamble.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPreamble.insets = new Insets(0, 0, 5, 5);
		gbc_btnPreamble.gridx = 1;
		gbc_btnPreamble.gridy = 1;
		btnPreamble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_3"))).start();
			}
		});
		add(btnPreamble, gbc_btnPreamble);
		String inst = new String(supplements.get("STR_2"));
		lblNewLabel = new JLabel(inst);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);
		
		btnOption = new JButton(strs[0]);
		btnOption.setForeground(Opt1);
		GridBagConstraints gbc_btnOption = new GridBagConstraints();
		gbc_btnOption.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOption.insets = new Insets(0, 0, 5, 5);
		gbc_btnOption.gridx = 0;
		gbc_btnOption.gridy = 3;
		btnOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_4"))).start();
			}
		});

		add(btnOption, gbc_btnOption);
		
		btnOption_1 = new JButton(strs[1]);
		btnOption_1.setForeground(Opt2);
		GridBagConstraints gbc_btnOption_1 = new GridBagConstraints();
		gbc_btnOption_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOption_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnOption_1.gridx = 1;
		gbc_btnOption_1.gridy = 3;
		btnOption_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_5"))).start();
			}
		});
		add(btnOption_1, gbc_btnOption_1);
		
		a1 = new JButton("<html><body style='width:100%'>"+strs[2]);
		a1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		a1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_6"))).start();
			}
		});
		
		lblOrBoth = new JLabel("Or both");
		if (!inst.toLowerCase().contains("both"))
		{
			lblOrBoth.setText("");
		}
		lblOrBoth.setForeground(Opt3);
		GridBagConstraints gbc_lblOrBoth = new GridBagConstraints();
		gbc_lblOrBoth.insets = new Insets(0, 0, 5, 0);
		gbc_lblOrBoth.gridx = 2;
		gbc_lblOrBoth.gridy = 3;
		add(lblOrBoth, gbc_lblOrBoth);
		GridBagConstraints gbc_a1 = new GridBagConstraints();
		gbc_a1.fill = GridBagConstraints.HORIZONTAL;
		gbc_a1.insets = new Insets(0, 0, 5, 5);
		gbc_a1.gridx = 0;
		gbc_a1.gridy = 4;
		add(a1, gbc_a1);
		
		chckbxNewCheckBox = new JCheckBox("Show answer");
		chckbxNewCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				a1.setForeground(Color.BLACK);
				a2.setForeground(Color.BLACK);
				a3.setForeground(Color.BLACK);
				a4.setForeground(Color.BLACK);
				a5.setForeground(Color.BLACK);
				a6.setForeground(Color.BLACK);
				a7.setForeground(Color.BLACK);
				
				if (e.getStateChange() == ItemEvent.DESELECTED)
				{
				}
				else
				{
					Color[] cols = new Color[7];
					for (int i =4 ; i <11; i++)
					{
						switch (ans[i])
						{
							case 1:
							{
								cols[i-4] = Opt1;
								break;
							}
							case 2:
							{
								cols[i-4] = Opt2;
								break;
							}
							case 3:
							{
								cols[i-4] = Opt3;
								break;
							}
						}
						 
					}
					a1.setForeground(cols[0]);
					a2.setForeground(cols[1]);
					a3.setForeground(cols[2]);
					a4.setForeground(cols[3]);
					a5.setForeground(cols[4]);
					a6.setForeground(cols[5]);
					a7.setForeground(cols[6]);
					revalidate();
				}
			}
		});
		
		a3 = new JButton("<html><body style='width:100%'>"+strs[4]);
		a3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GridBagConstraints gbc_a3 = new GridBagConstraints();
		gbc_a3.fill = GridBagConstraints.HORIZONTAL;
		gbc_a3.insets = new Insets(0, 0, 5, 5);
		gbc_a3.gridx = 0;
		gbc_a3.gridy = 5;
		a3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_8"))).start();
			}
		});
				
				a2 = new JButton("<html><body style='width:100%'>"+strs[3]);
				a2.setFont(new Font("Tahoma", Font.PLAIN, 10));
				GridBagConstraints gbc_a2 = new GridBagConstraints();
				gbc_a2.fill = GridBagConstraints.HORIZONTAL;
				gbc_a2.insets = new Insets(0, 0, 5, 0);
				gbc_a2.gridx = 2;
				gbc_a2.gridy = 4;
				a2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Thread(new AudioPlayer(supplements.get("snd_7"))).start();
					}
				});
				
						add(a2, gbc_a2);
		
				add(a3, gbc_a3);
				
				a6 = new JButton("<html><body style='width:100%'>"+strs[7]);
				a6.setFont(new Font("Tahoma", Font.PLAIN, 10));
				GridBagConstraints gbc_a6 = new GridBagConstraints();
				gbc_a6.fill = GridBagConstraints.HORIZONTAL;
				gbc_a6.insets = new Insets(0, 0, 5, 0);
				gbc_a6.gridx = 2;
				gbc_a6.gridy = 6;
				a6.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Thread(new AudioPlayer(supplements.get("snd_11"))).start();
					}
				});
						
						a4 = new JButton("<html><body style='width:100%'>"+strs[5]);
						a4.setFont(new Font("Tahoma", Font.PLAIN, 10));
						GridBagConstraints gbc_a4 = new GridBagConstraints();
						gbc_a4.fill = GridBagConstraints.HORIZONTAL;
						gbc_a4.insets = new Insets(0, 0, 5, 0);
						gbc_a4.gridx = 2;
						gbc_a4.gridy = 5;
						a4.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								new Thread(new AudioPlayer(supplements.get("snd_9"))).start();
							}
						});
						add(a4, gbc_a4);
						
						a5 = new JButton("<html><body style='width:100%'>"+strs[6]);
						a5.setFont(new Font("Tahoma", Font.PLAIN, 10));
						GridBagConstraints gbc_a5 = new GridBagConstraints();
						gbc_a5.fill = GridBagConstraints.HORIZONTAL;
						gbc_a5.insets = new Insets(0, 0, 5, 5);
						gbc_a5.gridx = 0;
						gbc_a5.gridy = 6;
						a5.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								new Thread(new AudioPlayer(supplements.get("snd_10"))).start();
							}
						});
						
								add(a5, gbc_a5);
				
						add(a6, gbc_a6);
		
		a7 = new JButton("<html><body style='width:100%'>"+strs[8]);
		a7.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GridBagConstraints gbc_a7 = new GridBagConstraints();
		gbc_a7.fill = GridBagConstraints.HORIZONTAL;
		gbc_a7.insets = new Insets(0, 0, 5, 5);
		gbc_a7.gridx = 1;
		gbc_a7.gridy = 7;
		add(a7, gbc_a7);
		a7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new AudioPlayer(supplements.get("snd_12"))).start();
			}
		});

		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 9;
		add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		
	}
}
