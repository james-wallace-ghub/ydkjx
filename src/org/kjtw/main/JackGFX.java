package org.kjtw.main;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class JackGFX extends JPanel {

	private JackGfxStrip JGS;

	public JackGfxStrip getStrip()
	{
		return JGS;
	}

	/**
	 * Create the panel.
	 */
	public JackGFX(JackGraphic jgfx) {
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		JGS = new JackGfxStrip(jgfx);
		tabbedPane.addTab("Raw Frames", null, JGS,
                "Raw Frames");
		tabbedPane.addTab("Animation", null, new JackAnimStrip(jgfx),
                "Animation");
		
	}

	public JackGFX() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{54, 27, 428, 0, 33, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}

}
