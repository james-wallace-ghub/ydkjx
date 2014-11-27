package org.kjtw.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class FITBComp extends Composite {
    private Text text;

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public FITBComp(Composite parent, int style) {
        super(parent, style);
        
        Label label = new Label(this, SWT.NONE);
        label.setText("Title");
        label.setBounds(0, 0, 332, 29);
        
        Label label_1 = new Label(this, SWT.NONE);
        label_1.setText("Value");
        label_1.setBounds(353, 0, 97, 29);
        
        Label label_2 = new Label(this, SWT.NONE);
        label_2.setText("Question");
        label_2.setBounds(0, 35, 450, 41);
        
        Label label_3 = new Label(this, SWT.NONE);
        label_3.setText("Label 1");
        label_3.setBounds(0, 82, 450, 29);
        
        Label label_4 = new Label(this, SWT.NONE);
        label_4.setText("Label 2");
        label_4.setBounds(0, 117, 450, 29);
        
        Label label_5 = new Label(this, SWT.NONE);
        label_5.setText("Label 3");
        label_5.setBounds(0, 152, 450, 29);
        
        Label label_6 = new Label(this, SWT.NONE);
        label_6.setText("Label 4");
        label_6.setBounds(0, 187, 450, 29);
        
        text = new Text(this, SWT.BORDER);
        text.setText("Type answer here");
        text.setBounds(0, 222, 450, 40);
        
        Label label_7 = new Label(this, SWT.NONE);
        label_7.setText("Score");
        label_7.setAlignment(SWT.CENTER);
        label_7.setBounds(174, 347, 133, 41);
        
        Label label_8 = new Label(this, SWT.NONE);
        label_8.setText("Score");
        label_8.setAlignment(SWT.CENTER);
        label_8.setBounds(154, 268, 133, 32);

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }

}
