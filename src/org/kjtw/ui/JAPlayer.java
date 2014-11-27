package org.kjtw.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;

public class JAPlayer {

    protected Shell shell;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            JAPlayer window = new JAPlayer();
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
        shell.setSize(504, 334);
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
        
        Label lblClue = new Label(shell, SWT.NONE);
        lblClue.setText("Clue");
        lblClue.setBounds(0, 0, 488, 41);
        
        Label lblQuestion = new Label(shell, SWT.NONE);
        lblQuestion.setText("Question");
        lblQuestion.setBounds(0, 47, 488, 41);
        
        Label lblAnswer = new Label(shell, SWT.NONE);
        lblAnswer.setText("Answer");
        lblAnswer.setBounds(0, 93, 488, 41);
        
        Label lblStatus = new Label(shell, SWT.NONE);
        lblStatus.setText("Status");
        lblStatus.setBounds(0, 140, 488, 41);
        
        Label lblScore = new Label(shell, SWT.NONE);
        lblScore.setAlignment(SWT.CENTER);
        lblScore.setText("Score");
        lblScore.setBounds(185, 234, 121, 41);

    }
}
