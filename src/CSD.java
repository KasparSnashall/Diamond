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
        
        
        // composite allows me to use more then one item in my tab folder
        Composite composite = new Composite(folder, SWT.NONE);
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false); // must figure these variables out
        gridData.horizontalSpan = 2;
        composite.setLayoutData(gridData);
        composite.setLayout(new GridLayout(2, false)); // two columns
        // addition of new items
        new Label(composite, SWT.NONE).setText("Write view");
        // make buttons in a row
        
        
        Button b = new Button(composite,SWT.PUSH);
        b.setText("numbers.py");
        b.addSelectionListener(new SelectionListener(){
        	
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("called");
				
				
				
				
				try{
					 int number1 = 10;
					 int number2 = 32;
					 Process p = Runtime.getRuntime().exec("python python_code/numbers.py "+number1+" "+number2);
					 BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					 int ret = new Integer(in.readLine()).intValue();
					 System.out.println("value is : "+ret);
				 }catch(Exception z){System.out.println(z);}
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub}
			}});
        
        new Button(composite, SWT.BUTTON4).setText("button 4");
        
        
        Button browse = new Button(shell, SWT.PUSH);
		browse.setText("Browse...");
		gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData.horizontalIndent = 5;
		browse.setLayoutData(gridData);
		
		// adding a browse and return function
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String fileName = new FileDialog(shell).open();
				if (fileName != null) {
					Image dogImage = new Image(display, fileName);
				}
			}
		});
		
		// trying to run a simple python program
		
		
		/*
		b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				
			}});
		*/

		// try running py4j
		
        
        
        
        
        
        
        // set control
        cTabItem2.setControl(composite);
        
        // pack and load shell
        shell.pack();
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
