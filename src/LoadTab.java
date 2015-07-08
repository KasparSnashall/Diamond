import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.python.core.PyArray;
import org.python.core.PyInstance;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;

public class LoadTab{
	
	public Composite create(CTabFolder folder,Shell shell,Display display){// composite allows me to use more then one item in my tab folder
        Composite composite = new Composite(folder, SWT.NONE);
        GridLayout layout = new GridLayout(3,false);
        layout.marginWidth = 25;
        layout.marginHeight = 25;
        composite.setLayout(layout);
        
        Label readview = new Label(composite, SWT.NONE);
        readview.setText("Read view");
        
        // fill grid data
        GridData griddata = new GridData(GridData.CENTER);
		griddata.horizontalSpan = 3;
		readview.setLayoutData(griddata);
		
		// centre grid data

        
		
		
		// sample name row
		Label samplename = new Label(composite, SWT.NONE);
		samplename.setText("Sample Name:");
		
		final Text sampletext = new Text(composite, SWT.BORDER);
		sampletext.setText("name");
		GridData griddata2 = new GridData(150,15);
		griddata2.horizontalSpan = 2;
		sampletext.setLayoutData(griddata2);
		
		
		// adding in the loading buttons
		
		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("File path:");
		
		final Text filetext = new Text(composite, SWT.BORDER);
		filetext.setText("...");
		GridData griddata3 = new GridData(150,15);
		filetext.setLayoutData(griddata3);
		
		
		
		Button browse = new Button(composite, SWT.PUSH);
		browse.setText("Browse...");
		
		
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String filepath = new FileDialog(shell).open();
				if (filepath != null) {
						// if a filepath is input
			          File file = new File(filepath);
			          if (file.isFile()){
			        	  // check is actually a file and not just directory
			        	  filetext.setText(filepath);
			        	  sampletext.setText(file.getName());}
			          else
			        	  filetext.setText("Not a file");
				}
			}
		});
		
		
        
        // trying to run a simple python program
        
		
		final Button rangebox = new Button(composite, SWT.CHECK);
		rangebox.setText("Range");
		rangebox.addSelectionListener(new SelectionAdapter()
		{
		    @Override
		    public void widgetSelected(SelectionEvent e)
		    {
		        if (rangebox.getSelection())
		            System.out.println("here");
		        else
		        	;
		    }
		});
		
		final Text upper = new Text(composite, SWT.BORDER);
		upper.setText("upper");
		final Text lower = new Text(composite,SWT.BORDER);
		lower.setText("lower");
		
        
        Button b = new Button(composite,SWT.PUSH);
        b.setText("Load");
        GridData griddata4 = new GridData(300,30);
        griddata4.horizontalSpan = 3;
        griddata4.minimumWidth = 40;
        b.setLayoutData(griddata4);
        
        
        
        
        final Text text1 = new Text(composite, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
        GridData griddata5 = new GridData(300,300);
        griddata5.horizontalSpan = 3;
        griddata5.grabExcessHorizontalSpace = true;
        griddata5.grabExcessVerticalSpace = true;
        text1.setLayoutData(griddata5);
        
        
        
        
		// load button function
       
        b.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("called"); // check
				try{
					
					interpreter ie = new interpreter(); // call my interpreter
					ie.execfile("python_code/Loader.py"); // init my file
					String myfilepath = filetext.getText(); // get filepath
					String newstring = "'"+myfilepath+"'"; // make sure python knows its a string
					PyInstance Loader = ie.createClass("Loader",newstring); // invoke my class as a pyobject
					PyObject array = Loader.invoke("load_hkl_data"); // calls the run function
					PyObject newarray = Loader.invoke("trythis", array);
					array.toString();
					//Array newarray = (Array)array.__tojava__(Array.class); // string
					text1.append(array+"\n"); // print
					
					
					
					
					
					}catch(Exception z){text1.append(z.getMessage());}	
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub}
			}});
        
        // add in a browse function
       
		
		
		
		
        return composite;
		}
	}
