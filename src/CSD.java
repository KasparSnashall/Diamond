import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CSD {
	  
    public static void main(String[] args) throws ScriptException, IOException{
    	Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("My Powder Diffraction toolkit");

        // create a new GridLayout with two columns
        // of different size
        GridLayout layout = new GridLayout(2, false);

        // set the layout to the shell
        shell.setLayout(layout);
        
        
        // creating initial tabs for operation selection
        CTabFolder folder = new CTabFolder(shell, SWT.TOP); // create a tab set
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true,2, 1);
        folder.setLayoutData(data);
        
        
        // add tabs to the layout
        // make these more descriptive
        CTabItem cTabItem1 = new CTabItem(folder, SWT.NONE);
        cTabItem1.setText("Read");
        CTabItem cTabItem2 = new CTabItem(folder, SWT.NONE);
        cTabItem2.setText("Write");
        CTabItem cTabItem3 = new CTabItem(folder, SWT.NONE);
        cTabItem3.setText("Plot");
        CTabItem cTabItem4 = new CTabItem(folder, SWT.NONE);
        cTabItem4.setText("Index");
        CTabItem cTabItem5 = new CTabItem(folder, SWT.NONE);
        cTabItem5.setText("Search");
        
        
        
        // add in the readtab (this will allow reading of data)
        ReadTab myreadtab = new ReadTab();
        cTabItem1.setControl(myreadtab.create(folder,shell,display));
        
        // pack and load shell
        
        shell.pack();
        shell.setSize(500, 500);
        shell.open();
        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
        display.dispose();
    }
}

class mybutton{
	
}
