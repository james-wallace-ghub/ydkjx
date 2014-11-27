package org.kjtw.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class RKPlayer {

    protected Shell shell;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            RKPlayer window = new RKPlayer();
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
        shell.setSize(504, 338);
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
        lblQuestion.setText("Question 1");
        lblQuestion.setBounds(0, 47, 239, 41);
        
        Label lblTime = new Label(shell, SWT.NONE);
        lblTime.setText("Value");
        lblTime.setBounds(353, 0, 135, 41);
        
        Button lblAnswer = new Button(shell, SWT.NONE);
        lblAnswer.setAlignment(SWT.CENTER);
        lblAnswer.setText("Answer");
        lblAnswer.setBounds(0, 128, 488, 41);
        
        Label lblStaus = new Label(shell, SWT.NONE);
        lblStaus.setAlignment(SWT.CENTER);
        lblStaus.setText("Status");
        lblStaus.setBounds(0, 190, 488, 41);
        
        Label label_1 = new Label(shell, SWT.NONE);
        label_1.setAlignment(SWT.CENTER);
        label_1.setText("Score");
        label_1.setBounds(174, 237, 133, 41);
        
        Label lblQuestion_1 = new Label(shell, SWT.NONE);
        lblQuestion_1.setText("Question 2");
        lblQuestion_1.setBounds(239, 47, 249, 41);

    }
}
