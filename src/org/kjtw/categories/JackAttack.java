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

public class JackAttack extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2970935854547118125L;
	private JLabel Title;
	private JButton btnPlayTitle;
	private Hashtable <String, byte[]> supplements;
	private Hashtable <String, String[]> strings;
	private SRFLoad QData;
	private int ansval;
	private JLabel Rootval;
	private JLabel lblRoot;
	private JLabel lblMatch;
	private JLabel lblDecoy;
	private JLabel lblDecoy_1;
	private JLabel lblDecoy_2;
	private JLabel mtchval;
	private JLabel dc1;
	private JLabel dc2;
	private JLabel dc3;
	private JButton btnNextAnswer;
	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public JackAttack(final QHeader qhd) throws IOException {
		supplements = new Hashtable<String, byte[]>();
		QData = new SRFLoad(qhd.path);
		supplements = QData.getData(); 
		strings = QData.getStrs();
		String[] strsa = strings.get("STR_130");
		final char[] strs = new char[7];
		for (int i=0; i < 7; i++)
		{
			strs[i] = strsa[0].charAt(i);
		}
		final String[] roots = strings.get("Root_128");
		final String[] match = strings.get("Mtch_128");
		final String[] decoy = strings.get("Dcoy_128");
		
		ansval =0;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {160, 150, 150, 2};
		gridBagLayout.rowHeights = new int[]{0, 0, 30, 28, 29, 27, 27, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Rootval = new JLabel("inst");
		GridBagConstraints gbc_Rootval = new GridBagConstraints();
		gbc_Rootval.gridwidth = 2;
		gbc_Rootval.insets = new Insets(0, 0, 5, 0);
		gbc_Rootval.gridx = 1;
		gbc_Rootval.gridy = 2;
		add(Rootval, gbc_Rootval);
		
		mtchval = new JLabel("<dynamic>");
		GridBagConstraints gbc_mtchval = new GridBagConstraints();
		gbc_mtchval.gridwidth = 2;
		gbc_mtchval.insets = new Insets(0, 0, 5, 0);
		gbc_mtchval.gridx = 1;
		gbc_mtchval.gridy = 3;
		add(mtchval, gbc_mtchval);
		
		btnPlayTitle = new JButton("Play Title");
		btnPlayTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						new Thread(new AudioPlayer(supplements.get("snd_2"))).start();
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

		dc1 = new JLabel("<dynamic>");
		GridBagConstraints gbc_dc1 = new GridBagConstraints();
		gbc_dc1.gridwidth = 2;
		gbc_dc1.insets = new Insets(0, 0, 5, 0);
		gbc_dc1.gridx = 1;
		gbc_dc1.gridy = 4;
		add(dc1, gbc_dc1);

		dc2 = new JLabel("<dynamic>");
		GridBagConstraints gbc_dc2 = new GridBagConstraints();
		gbc_dc2.gridwidth = 2;
		gbc_dc2.insets = new Insets(0, 0, 5, 0);
		gbc_dc2.gridx = 1;
		gbc_dc2.gridy = 5;
		add(dc2, gbc_dc2);

		dc3 = new JLabel("<dynamic>");
		GridBagConstraints gbc_dc3 = new GridBagConstraints();
		gbc_dc3.gridwidth = 2;
		gbc_dc3.gridx = 1;
		gbc_dc3.gridy = 6;
		add(dc3, gbc_dc3);
		
		int position = Integer.valueOf(strs[ansval])-48;
		Rootval.setText(roots[position-1]);
		mtchval.setText(match[position-1]);
		dc1.setText(decoy[position-1]);
		dc2.setText(decoy[position+7-1]);
		dc3.setText(decoy[position+14-1]);
		
		
		btnNextAnswer = new JButton("Next Answer (1 of 7)");
		GridBagConstraints gbc_btnNextAnswer = new GridBagConstraints();
		gbc_btnNextAnswer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNextAnswer.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextAnswer.gridx = 0;
		gbc_btnNextAnswer.gridy = 1;
		btnNextAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ansval ++;
				if (ansval > 6)
				{
					ansval =0;
				}
				btnNextAnswer.setText("Next Answer ("+(ansval+1)+" of 7)");
				int position = Integer.valueOf(strs[ansval])-48;
				Rootval.setText(roots[position-1]);
				mtchval.setText(match[position-1]);
				dc1.setText(decoy[position-1]);
				dc2.setText(decoy[position+7-1]);
				dc3.setText(decoy[position+14-1]);
					}
		});
		
		
		add(btnNextAnswer, gbc_btnNextAnswer);
		
		lblRoot = new JLabel("Root");
		GridBagConstraints gbc_lblRoot = new GridBagConstraints();
		gbc_lblRoot.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoot.gridx = 0;
		gbc_lblRoot.gridy = 2;
		add(lblRoot, gbc_lblRoot);
		
		lblMatch = new JLabel("Match");
		GridBagConstraints gbc_lblMatch = new GridBagConstraints();
		gbc_lblMatch.insets = new Insets(0, 0, 5, 5);
		gbc_lblMatch.gridx = 0;
		gbc_lblMatch.gridy = 3;
		add(lblMatch, gbc_lblMatch);
		
		
		lblDecoy = new JLabel("Decoy 1");
		GridBagConstraints gbc_lblDecoy = new GridBagConstraints();
		gbc_lblDecoy.insets = new Insets(0, 0, 5, 5);
		gbc_lblDecoy.gridx = 0;
		gbc_lblDecoy.gridy = 4;
		add(lblDecoy, gbc_lblDecoy);
		
		
		lblDecoy_1 = new JLabel("Decoy 2");
		GridBagConstraints gbc_lblDecoy_1 = new GridBagConstraints();
		gbc_lblDecoy_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDecoy_1.gridx = 0;
		gbc_lblDecoy_1.gridy = 5;
		add(lblDecoy_1, gbc_lblDecoy_1);
		

		lblDecoy_2 = new JLabel("Decoy 3");
		GridBagConstraints gbc_lblDecoy_2 = new GridBagConstraints();
		gbc_lblDecoy_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblDecoy_2.gridx = 0;
		gbc_lblDecoy_2.gridy = 6;
		add(lblDecoy_2, gbc_lblDecoy_2);
		
		
	}
}
