package org.kjtw.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

public class ThreeWayComp extends Composite {

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public ThreeWayComp(Composite parent, int style) {
        super(parent, style);
        
        Label label = new Label(this, SWT.NONE);
        label.setText("Title");
        label.setBounds(0, 10, 450, 41);
        
        Label label_1 = new Label(this, SWT.NONE);
        label_1.setText("Question");
        label_1.setBounds(0, 57, 450, 41);
        
        Button button = new Button(this, SWT.NONE);
        button.setText("Label 2");
        button.setBounds(164, 103, 123, 41);
        
        Button button_1 = new Button(this, SWT.NONE);
        button_1.setText("Label 1");
        button_1.setBounds(0, 103, 123, 41);
        
        Button button_2 = new Button(this, SWT.NONE);
        button_2.setText("Label 3");
        button_2.setBounds(329, 104, 121, 41);
        
        Label label_2 = new Label(this, SWT.NONE);
        label_2.setText("Round Score");
        label_2.setAlignment(SWT.CENTER);
        label_2.setBounds(164, 184, 133, 41);
        
        Label label_3 = new Label(this, SWT.NONE);
        label_3.setText("Score");
        label_3.setBounds(0, 245, 133, 41);
        
        Label label_4 = new Label(this, SWT.NONE);
        label_4.setText("Time");
        label_4.setBounds(317, 245, 133, 41);

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

}
