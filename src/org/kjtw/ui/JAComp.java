package org.kjtw.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

public class JAComp extends Composite {

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public JAComp(Composite parent, int style) {
        super(parent, style);
        
        Label label = new Label(this, SWT.NONE);
        label.setText("Clue");
        label.setBounds(0, 0, 450, 41);
        
        Label label_1 = new Label(this, SWT.NONE);
        label_1.setText("Question");
        label_1.setBounds(0, 62, 450, 41);
        
        Label label_2 = new Label(this, SWT.NONE);
        label_2.setText("Answer");
        label_2.setBounds(0, 109, 450, 41);
        
        Label label_3 = new Label(this, SWT.NONE);
        label_3.setText("Status");
        label_3.setBounds(0, 173, 450, 41);
        
        Label label_4 = new Label(this, SWT.NONE);
        label_4.setText("Score");
        label_4.setAlignment(SWT.CENTER);
        label_4.setBounds(165, 259, 121, 41);

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

}
