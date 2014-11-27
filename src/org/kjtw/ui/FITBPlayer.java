package org.kjtw.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class FITBPlayer {

    protected Shell shell;
    private Text txtTypeAnswerHere;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            FITBPlayer window = new FITBPlayer();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setSize(504, 446);
        shell.setText("SWT Application");
        
        Menu menu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menu);
        
        MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
        menuItem.setText("Menu");
        
        Menu menu_1 = new Menu(menuItem);
        menuItem.setMenu(menu_1);
        
        MenuItem menuItem_1 = new MenuItem(menu_1, SWT.NONE);
        menuItem_1.setText("Load SRF");
        
        MenuItem menuItem_2 = new MenuItem(menu_1, SWT.NONE);
        menuItem_2.setText("Directory");
        
        MenuItem menuItem_3 = new MenuItem(menu_1, SWT.CHECK);
        menuItem_3.setText("Show correct answers in list");
        
        MenuItem mntmExtractAllAudio = new MenuItem(menu_1, SWT.NONE);
        mntmExtractAllAudio.setText("Extract all audio");
        
        Label label = new Label(shell, SWT.NONE);
        label.setText("Title");
        label.setBounds(0, 0, 332, 41);
        
        Label lblQuestion = new Label(shell, SWT.NONE);
        lblQuestion.setText("Question");
        lblQuestion.setBounds(0, 47, 488, 41);
        
        Label lblTime = new Label(shell, SWT.NONE);
        lblTime.setText("Value");
        lblTime.setBounds(353, 0, 135, 41);
        
        Label label_3 = new Label(shell, SWT.NONE);
        label_3.setText("Label 1");
        label_3.setBounds(0, 93, 488, 41);
        
        Label label_4 = new Label(shell, SWT.NONE);
        label_4.setText("Label 2");
        label_4.setBounds(0, 140, 488, 41);
        
        Label label_5 = new Label(shell, SWT.NONE);
        label_5.setText("Label 3");
        label_5.setBounds(0, 187, 488, 41);
        
        Label label_6 = new Label(shell, SWT.NONE);
        label_6.setText("Label 4");
        label_6.setBounds(0, 240, 488, 41);
        
        txtTypeAnswerHere = new Text(shell, SWT.BORDER);
        txtTypeAnswerHere.setText("Type answer here");
        txtTypeAnswerHere.setBounds(0, 281, 488, 40);
        
        Label label_1 = new Label(shell, SWT.NONE);
        label_1.setAlignment(SWT.CENTER);
        label_1.setText("Score");
        label_1.setBounds(174, 347, 133, 41);

    }
}
