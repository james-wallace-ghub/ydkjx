package org.kjtw.main;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class JackGFX extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2631343788849889367L;
	private JTabbedPane tabbedPane;

	
	public JackGfxStrip getStrip()
	{
		return 	(JackGfxStrip) tabbedPane.getSelectedComponent();

	}


	/**
	 * Create the panel.
	 */
	public JackGFX(JackGraphic jgfx) {
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		tabbedPane.addTab("Raw Frames", null, new JackGfxStrip(jgfx),
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
