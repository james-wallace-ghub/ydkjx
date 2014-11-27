package org.kjtw.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

public class DDComp extends Composite {

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public DDComp(Composite parent, int style) {
        super(parent, style);
        
        Label label = new Label(this, SWT.NONE);
        label.setText("Title");
        label.setBounds(0, 0, 450, 41);
        
        Label label_1 = new Label(this, SWT.NONE);
        label_1.setText("Question");
        label_1.setBounds(0, 47, 450, 41);
        
        Button button = new Button(this, SWT.NONE);
        button.setText("Label 1");
        button.setBounds(0, 108, 99, 41);
        
        Button button_1 = new Button(this, SWT.NONE);
        button_1.setText("Label 2");
        button_1.setBounds(121, 108, 99, 41);
        
        Button button_2 = new Button(this, SWT.NONE);
        button_2.setGrayed(true);
        button_2.setText("Label 3");
        button_2.setBounds(235, 109, 99, 41);
        
        Button button_3 = new Button(this, SWT.NONE);
        button_3.setText("Label 4");
        button_3.setBounds(351, 108, 99, 41);
        
        Label label_2 = new Label(this, SWT.NONE);
        label_2.setText("4");
        label_2.setAlignment(SWT.CENTER);
        label_2.setBounds(373, 155, 55, 15);
        
        Label label_3 = new Label(this, SWT.NONE);
        label_3.setText("3");
        label_3.setAlignment(SWT.CENTER);
        label_3.setBounds(261, 155, 55, 15);
        
        Label label_4 = new Label(this, SWT.NONE);
        label_4.setText("2");
        label_4.setAlignment(SWT.CENTER);
        label_4.setBounds(142, 155, 55, 15);
        
        Label label_5 = new Label(this, SWT.NONE);
        label_5.setText("1");
        label_5.setAlignment(SWT.CENTER);
        label_5.setBounds(25, 155, 55, 15);
        
        Label label_6 = new Label(this, SWT.NONE);
        label_6.setText("Round Score");
        label_6.setAlignment(SWT.CENTER);
        label_6.setBounds(154, 200, 133, 41);
        
        Label label_7 = new Label(this, SWT.NONE);
        label_7.setText("Score");
        label_7.setBounds(0, 249, 133, 41);
        
        Label label_8 = new Label(this, SWT.NONE);
        label_8.setText("Time");
        label_8.setBounds(317, 249, 133, 41);

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

}
