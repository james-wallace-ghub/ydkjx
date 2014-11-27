package org.kjtw.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class DDPlayer {

    protected Shell shell;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            DDPlayer window = new DDPlayer();
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
        shell.setSize(522, 334);
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
        label.setBounds(0, 0, 506, 41);
        
        Label lblQuestion = new Label(shell, SWT.NONE);
        lblQuestion.setText("Question");
        lblQuestion.setBounds(0, 47, 506, 41);
        
        Button label_3 = new Button(shell, SWT.NONE);
        label_3.setText("Label 1");
        label_3.setBounds(0, 93, 123, 41);
        
        Button label_4 = new Button(shell, SWT.NONE);
        label_4.setText("Label 2");
        label_4.setBounds(129, 93, 123, 41);
        
        Button label_5 = new Button(shell, SWT.NONE);
        label_5.setText("Label 3");
        label_5.setBounds(259, 93, 121, 41);
        
        Button label_6 = new Button(shell, SWT.NONE);
        label_6.setText("Label 4");
        label_6.setBounds(385, 93, 121, 41);
        
        Label lblScore = new Label(shell, SWT.NONE);
        lblScore.setAlignment(SWT.CENTER);
        lblScore.setText("Round Score");
        lblScore.setBounds(187, 176, 133, 41);
        
        Label lblTime = new Label(shell, SWT.NONE);
        lblTime.setText("Time");
        lblTime.setBounds(373, 235, 133, 41);
        
        Label label_1 = new Label(shell, SWT.NONE);
        label_1.setAlignment(SWT.CENTER);
        label_1.setBounds(35, 140, 55, 15);
        label_1.setText("1");
        
        Label label_2 = new Label(shell, SWT.NONE);
        label_2.setText("2");
        label_2.setAlignment(SWT.CENTER);
        label_2.setBounds(165, 140, 55, 15);
        
        Label label_7 = new Label(shell, SWT.NONE);
        label_7.setText("3");
        label_7.setAlignment(SWT.CENTER);
        label_7.setBounds(293, 140, 55, 15);
        
        Label label_8 = new Label(shell, SWT.NONE);
        label_8.setText("4");
        label_8.setAlignment(SWT.CENTER);
        label_8.setBounds(414, 140, 55, 15);
        
        Label label_9 = new Label(shell, SWT.NONE);
        label_9.setText("Score");
        label_9.setBounds(0, 235, 133, 41);

    }
}
