package org.kjtw.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class RKComp extends Composite {

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public RKComp(Composite parent, int style) {
        super(parent, style);
        
        Label label_7 = new Label(this, SWT.NONE);
        label_7.setText("Score");
        label_7.setAlignment(SWT.CENTER);
        label_7.setBounds(174, 347, 133, 41);
        
        Label label = new Label(this, SWT.NONE);
        label.setText("Value");
        label.setBounds(353, 10, 97, 41);
        
        Label label_1 = new Label(this, SWT.NONE);
        label_1.setText("Title");
        label_1.setBounds(0, 10, 332, 41);
        
        Label label_2 = new Label(this, SWT.NONE);
        label_2.setText("Question 1");
        label_2.setBounds(0, 57, 230, 41);
        
        Label label_3 = new Label(this, SWT.NONE);
        label_3.setText("Question 2");
        label_3.setBounds(239, 57, 211, 41);
        
        Button button = new Button(this, SWT.NONE);
        button.setText("Answer");
        button.setAlignment(SWT.CENTER);
        button.setBounds(0, 138, 450, 41);
        
        Label label_4 = new Label(this, SWT.NONE);
        label_4.setText("Status");
        label_4.setAlignment(SWT.CENTER);
        label_4.setBounds(0, 200, 450, 41);
        
        Label label_5 = new Label(this, SWT.NONE);
        label_5.setText("Score");
        label_5.setAlignment(SWT.CENTER);
        label_5.setBounds(159, 249, 133, 41);

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

}
