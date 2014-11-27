package org.kjtw.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

public class QSelect {

    protected Shell shell;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            QSelect window = new QSelect();
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
        shell.setSize(476, 345);
        shell.setText("SWT Application");
        
        Menu menu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menu);
        
        MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
        mntmNewSubmenu.setText("Menu");
        
        Menu menu_1 = new Menu(mntmNewSubmenu);
        mntmNewSubmenu.setMenu(menu_1);
        
        MenuItem mntmLoadSrf = new MenuItem(menu_1, SWT.NONE);
        mntmLoadSrf.setText("Load SRF");
        
        MenuItem mntmDirectory = new MenuItem(menu_1, SWT.NONE);
        mntmDirectory.setText("Directory");
        
        MenuItem mntmShowCorrectAnswer = new MenuItem(menu_1, SWT.CHECK);
        mntmShowCorrectAnswer.setText("Show correct answers in list");
        
        Label lblLabel = new Label(shell, SWT.NONE);
        lblLabel.setText("1");
        lblLabel.setBounds(0, 71, 460, 41);
        
        Label lblType = new Label(shell, SWT.NONE);
        lblType.setText("Round");
        lblType.setBounds(0, 0, 460, 41);
        
        Label lblExplanation = new Label(shell, SWT.NONE);
        lblExplanation.setBounds(0, 234, 160, 53);
        lblExplanation.setText("Score");
        
        Label label = new Label(shell, SWT.NONE);
        label.setText("2");
        label.setBounds(0, 118, 460, 41);
        
        Label label_1 = new Label(shell, SWT.NONE);
        label_1.setText("3");
        label_1.setBounds(0, 165, 460, 41);

    }
}
