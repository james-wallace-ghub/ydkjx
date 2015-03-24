package org.kjtw.displays;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

public class JackQheader extends JackGFX {

	private static final long serialVersionUID = -7319512477022269566L;

	public JackQheader(final String eu, final String japan, final String nuke) {
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.rowHeights = new int[]{508, 20};
		gridBagLayout.columnWidths = new int[]{587};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0};
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		final JTextPane textPane = new JTextPane();
		textPane.setText(nuke+eu);
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		final JToggleButton tglbtnJapaneseMode = new JToggleButton("Japanese Mode");
		GridBagConstraints gbc_tglbtnJapaneseMode = new GridBagConstraints();
		gbc_tglbtnJapaneseMode.fill = GridBagConstraints.VERTICAL;
		gbc_tglbtnJapaneseMode.gridx = 0;
		gbc_tglbtnJapaneseMode.gridy = 1;
		add(tglbtnJapaneseMode, gbc_tglbtnJapaneseMode);
		tglbtnJapaneseMode.setSelected(false);
		tglbtnJapaneseMode.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tglbtnJapaneseMode.isSelected())
				{
					textPane.setText(nuke+japan);
				}
				else
				{
					textPane.setText(nuke+eu);
				}
			}
		});

	}


}
