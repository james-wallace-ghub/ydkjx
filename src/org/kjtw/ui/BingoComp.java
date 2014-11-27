package org.kjtw.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

public class BingoComp extends Composite {

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public BingoComp(Composite parent, int style) {
        super(parent, style);
        
        Label label = new Label(this, SWT.NONE);
        label.setText("Question");
        label.setBounds(0, 0, 450, 41);
        
        Label label_6 = new Label(this, SWT.NONE);
        label_6.setText("Score");
        label_6.setBounds(195, 249, 99, 41);
        
        Label label_1 = new Label(this, SWT.NONE);
        label_1.setText("1");
        label_1.setAlignment(SWT.CENTER);
        label_1.setBounds(10, 114, 73, 32);
        
        Label label_2 = new Label(this, SWT.NONE);
        label_2.setText("2");
        label_2.setAlignment(SWT.CENTER);
        label_2.setBounds(89, 114, 81, 32);
        
        Label label_3 = new Label(this, SWT.NONE);
        label_3.setText("3");
        label_3.setAlignment(SWT.CENTER);
        label_3.setBounds(176, 114, 81, 32);
        
        Label label_4 = new Label(this, SWT.NONE);
        label_4.setText("4");
        label_4.setAlignment(SWT.CENTER);
        label_4.setBounds(263, 114, 81, 32);
        
        Label label_5 = new Label(this, SWT.NONE);
        label_5.setText("5");
        label_5.setAlignment(SWT.CENTER);
        label_5.setBounds(350, 114, 81, 32);
        
        Label label_7 = new Label(this, SWT.NONE);
        label_7.setText("1");
        label_7.setAlignment(SWT.CENTER);
        label_7.setBounds(154, 211, 20, 32);
        
        Label label_8 = new Label(this, SWT.NONE);
        label_8.setText("2");
        label_8.setAlignment(SWT.CENTER);
        label_8.setBounds(180, 211, 20, 32);
        
        Label label_9 = new Label(this, SWT.NONE);
        label_9.setText("3");
        label_9.setAlignment(SWT.CENTER);
        label_9.setBounds(206, 211, 20, 32);
        
        Label label_10 = new Label(this, SWT.NONE);
        label_10.setText("4");
        label_10.setAlignment(SWT.CENTER);
        label_10.setBounds(232, 211, 20, 32);
        
        Label label_11 = new Label(this, SWT.NONE);
        label_11.setText("5");
        label_11.setAlignment(SWT.CENTER);
        label_11.setBounds(258, 211, 20, 32);

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

}
