package org.kjtw.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class DemoOnePlayer {

    protected Shell shell;
    private Text text;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            DemoOnePlayer window = new DemoOnePlayer();
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
        
        text = new Text(shell, SWT.BORDER);
        text.setBounds(0, 239, 460, 48);
        
        Button button = new Button(shell, SWT.NONE);
        button.setBounds(0, 97, 227, 79);
        button.setText("7");
        
        Button button_1 = new Button(shell, SWT.NONE);
        button_1.setText("21");
        button_1.setBounds(233, 97, 227, 79);

    }
}
