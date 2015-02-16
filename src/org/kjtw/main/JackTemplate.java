package org.kjtw.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.awt.Color;

public class JackTemplate extends JackGFX {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7319512477022269566L;

	public JackTemplate(byte[] data, byte[] bs) {
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.rowHeights = new int[]{193, 45};
		gridBagLayout.columnWidths = new int[]{517};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0};
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setText(new String(data));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		add(scrollPane_1, gbc_scrollPane_1);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setForeground(Color.WHITE);
		textPane_1.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(textPane_1);
		textPane_1.setText(new String(bs));

	}

	public JackTemplate(byte[] data) {
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.rowHeights = new int[]{193, 45};
		gridBagLayout.columnWidths = new int[]{517};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0};
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setText(new String(data));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
	}


}
