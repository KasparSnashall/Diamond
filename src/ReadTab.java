import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class ReadTab{
	
	public Composite create(CTabFolder folder,Shell shell,Display display){// composite allows me to use more then one item in my tab folder
        Composite composite = new Composite(folder, SWT.NONE);
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false); // must figure these variables out
        gridData.horizontalSpan = 2;
        composite.setLayoutData(gridData);
        composite.setLayout(new GridLayout(2, false)); // two columns
        // addition of new items
        new Label(composite, SWT.NONE).setText("Read view");
        // make buttons in a row
        
        
        Button b = new Button(composite,SWT.PUSH);
        b.setText("numbers.py");
        // trying to run a simple python program
        
        Composite composite2 = new Composite(composite, SWT.NONE);
        final Text text1 = new Text(composite2, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
        text1.setSize(300, 400);
        
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
					 text1.append("output   \n");
				 }catch(Exception z){System.out.println(z);}	
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub}
			}});
        
        
        Button browse = new Button(composite, SWT.PUSH);
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
		
        return composite;
		}
	}
