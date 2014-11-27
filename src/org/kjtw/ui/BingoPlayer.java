package org.kjtw.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class BingoPlayer {

    protected Shell shell;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            BingoPlayer window = new BingoPlayer();
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
        shell.setSize(532, 334);
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
        
        Label lblQuestion = new Label(shell, SWT.NONE);
        lblQuestion.setText("Question");
        lblQuestion.setBounds(0, 0, 514, 41);
        
        Label label_9 = new Label(shell, SWT.NONE);
        label_9.setText("Score");
        label_9.setBounds(0, 235, 133, 41);
        
        Label label_4 = new Label(shell, SWT.NONE);
        label_4.setText("5");
        label_4.setAlignment(SWT.CENTER);
        label_4.setBounds(416, 99, 98, 32);
        
        Label label = new Label(shell, SWT.NONE);
        label.setText("4");
        label.setAlignment(SWT.CENTER);
        label.setBounds(312, 99, 98, 32);
        
        Label label_1 = new Label(shell, SWT.NONE);
        label_1.setText("1");
        label_1.setAlignment(SWT.CENTER);
        label_1.setBounds(0, 99, 98, 32);
        
        Label label_2 = new Label(shell, SWT.NONE);
        label_2.setText("2");
        label_2.setAlignment(SWT.CENTER);
        label_2.setBounds(104, 99, 98, 32);
        
        Label label_3 = new Label(shell, SWT.NONE);
        label_3.setText("3");
        label_3.setAlignment(SWT.CENTER);
        label_3.setBounds(208, 99, 98, 32);

    }
}
