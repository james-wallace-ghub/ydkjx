package org.kjtw.displays;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JToggleButton;

public class JackTemplate extends JackGFX {

	private static final long serialVersionUID = -7319512477022269566L;

	public JackTemplate(final byte[] data, byte[] bs) {
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.rowHeights = new int[]{464, 20, 121};
		gridBagLayout.columnWidths = new int[]{593};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0};
		
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		final JTextPane textPane = new JTextPane();
		String out= new String(data);
		textPane.setFont(new Font("Dialog", textPane.getFont().getStyle(), textPane.getFont().getSize()));
		textPane.setText(new String(out));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 2;
		add(scrollPane_1, gbc_scrollPane_1);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setFont(new Font("Dialog", textPane_1.getFont().getStyle(), textPane_1.getFont().getSize()));
		textPane_1.setForeground(Color.WHITE);
		textPane_1.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(textPane_1);
		
		if (bs !=null)
		{
			textPane_1.setText(new String(bs));
		}

	}

	/**
	 * @wbp.parser.constructor
	 */
	public JackTemplate(final byte[] data) {
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
		textPane.setFont(new Font("Dialog", textPane.getFont().getStyle(), textPane.getFont().getSize()));
		try {
			textPane.setText(new String(data,"MACROMAN").trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß"));
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
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
					try {
						textPane.setText(new String(data,"MS932").trim());
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					try {
						textPane.setText(new String(data,"MACROMAN").trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß"));
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});

	}


}
