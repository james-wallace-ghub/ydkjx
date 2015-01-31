package org.kjtw.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class JackTemplate extends JackGfxStrip {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7319512477022269566L;

	public JackTemplate(byte[] data) {
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 457};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0};
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setText(new String(data));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
	}


}
