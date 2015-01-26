package org.kjtw.categories;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

import javax.swing.JLabel;

import org.kjtw.main.AudioPlayer;

public class FiberSoundBoard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6779094118819684556L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 * @param supplements 
	 * @param title 
	 */
	public FiberSoundBoard(final Hashtable<String, byte[]> supplements, String title) {
		setTitle("Fiber Optic Sound Board");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{191, 56, 250, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JButton btnIntroTitle = new JButton("Intro title");
		GridBagConstraints gbc_btnIntroTitle = new GridBagConstraints();
		gbc_btnIntroTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnIntroTitle.insets = new Insets(0, 0, 5, 5);
		gbc_btnIntroTitle.gridx = 0;
		gbc_btnIntroTitle.gridy = 0;
		btnIntroTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_14"))).start();
			}
		});
		contentPane.add(btnIntroTitle, gbc_btnIntroTitle);
		
		JButton btnLocation = new JButton("Location 1");
		btnLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_15"))).start();
			}
		});
		GridBagConstraints gbc_btnLocation = new GridBagConstraints();
		gbc_btnLocation.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLocation.insets = new Insets(0, 0, 5, 5);
		gbc_btnLocation.gridx = 0;
		gbc_btnLocation.gridy = 1;
		contentPane.add(btnLocation, gbc_btnLocation);
		
		JLabel lblTitle = new JLabel(title);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitle.gridwidth = 2;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 0;
		contentPane.add(lblTitle, gbc_lblTitle);
		
		JLabel label = new JLabel(new String(supplements.get("STR_8")));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.HORIZONTAL;
		gbc_label.gridwidth = 2;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		contentPane.add(label, gbc_label);
		
		JButton btnPreciseLocation = new JButton("Location 2");
		GridBagConstraints gbc_btnPreciseLocation = new GridBagConstraints();
		gbc_btnPreciseLocation.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPreciseLocation.insets = new Insets(0, 0, 5, 5);
		gbc_btnPreciseLocation.gridx = 0;
		gbc_btnPreciseLocation.gridy = 2;
		btnPreciseLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_16"))).start();
			}
		});
		contentPane.add(btnPreciseLocation, gbc_btnPreciseLocation);
		
		JButton btnCall = new JButton("Call 1");
		GridBagConstraints gbc_btnCall = new GridBagConstraints();
		gbc_btnCall.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCall.insets = new Insets(0, 0, 5, 5);
		gbc_btnCall.gridx = 0;
		gbc_btnCall.gridy = 3;
		btnCall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_20"))).start();
			}
		});
		
		JLabel label_1 = new JLabel(new String(supplements.get("STR_7")));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_1.gridwidth = 2;
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 2;
		contentPane.add(label_1, gbc_label_1);
		contentPane.add(btnCall, gbc_btnCall);
		
		JButton btnCall_1 = new JButton("Call 2");
		GridBagConstraints gbc_btnCall_1 = new GridBagConstraints();
		gbc_btnCall_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCall_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnCall_1.gridx = 0;
		gbc_btnCall_1.gridy = 4;
		btnCall_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_17"))).start();
			}
		});
		
		JLabel label_2 = new JLabel(new String(supplements.get("STR_9")));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_2.gridwidth = 2;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 3;
		contentPane.add(label_2, gbc_label_2);
		contentPane.add(btnCall_1, gbc_btnCall_1);
		
		JButton btnCall_2 = new JButton("Call 3");
		GridBagConstraints gbc_btnCall_2 = new GridBagConstraints();
		gbc_btnCall_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCall_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnCall_2.gridx = 0;
		gbc_btnCall_2.gridy = 5;
		btnCall_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_27"))).start();
			}
		});
		
				
				JButton btnReturnToQ = new JButton("Return to Q");
				GridBagConstraints gbc_btnReturnToQ = new GridBagConstraints();
				gbc_btnReturnToQ.insets = new Insets(0, 0, 5, 5);
				gbc_btnReturnToQ.gridx = 1;
				gbc_btnReturnToQ.gridy = 4;
				btnReturnToQ.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new Thread(new AudioPlayer(supplements.get("snd_18"))).start();
					}
				});
				contentPane.add(btnReturnToQ, gbc_btnReturnToQ);

		contentPane.add(btnCall_2, gbc_btnCall_2);
		
		JButton btnCall_3 = new JButton("Call 4");
		GridBagConstraints gbc_btnCall_3 = new GridBagConstraints();
		gbc_btnCall_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCall_3.insets = new Insets(0, 0, 5, 5);
		gbc_btnCall_3.gridx = 0;
		gbc_btnCall_3.gridy = 6;
		btnCall_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_28"))).start();
			}
		});
		
		JButton btnReintroduce = new JButton("Reintroduce");
		GridBagConstraints gbc_btnReintroduce = new GridBagConstraints();
		gbc_btnReintroduce.insets = new Insets(0, 0, 5, 5);
		gbc_btnReintroduce.gridx = 1;
		gbc_btnReintroduce.gridy = 5;
		btnReintroduce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_22"))).start();
			}
		});
		contentPane.add(btnReintroduce, gbc_btnReintroduce);

		contentPane.add(btnCall_3, gbc_btnCall_3);
		
		JButton btnCall_4 = new JButton("Call 5");
		GridBagConstraints gbc_btnCall_4 = new GridBagConstraints();
		gbc_btnCall_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCall_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnCall_4.gridx = 0;
		gbc_btnCall_4.gridy = 7;
		btnCall_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_29"))).start();
			}
		});
		
		JButton btnValue = new JButton("Value");
		GridBagConstraints gbc_btnValue = new GridBagConstraints();
		gbc_btnValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnValue.insets = new Insets(0, 0, 5, 5);
		gbc_btnValue.gridx = 1;
		gbc_btnValue.gridy = 6;
		btnValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_23"))).start();
			}
		});
		contentPane.add(btnValue, gbc_btnValue);

		contentPane.add(btnCall_4, gbc_btnCall_4);
		
		JButton btnCall_5 = new JButton("Call 6");
		GridBagConstraints gbc_btnCall_5 = new GridBagConstraints();
		gbc_btnCall_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCall_5.insets = new Insets(0, 0, 0, 5);
		gbc_btnCall_5.gridx = 0;
		gbc_btnCall_5.gridy = 8;
		btnCall_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_21"))).start();
			}
		});
		
		JButton btnEndGame = new JButton("End Game");
		GridBagConstraints gbc_btnEndGame = new GridBagConstraints();
		gbc_btnEndGame.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEndGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnEndGame.gridx = 1;
		gbc_btnEndGame.gridy = 7;
		btnEndGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_12"))).start();
			}
		});
		
				contentPane.add(btnEndGame, gbc_btnEndGame);

		contentPane.add(btnCall_5, gbc_btnCall_5);
		
		JButton btnHangup = new JButton("Hangup");
		GridBagConstraints gbc_btnHangup = new GridBagConstraints();
		gbc_btnHangup.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHangup.insets = new Insets(0, 0, 0, 5);
		gbc_btnHangup.gridx = 1;
		gbc_btnHangup.gridy = 8;
		btnHangup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new AudioPlayer(supplements.get("snd_19"))).start();
			}
		});
		
				contentPane.add(btnHangup, gbc_btnHangup);
	}

}
