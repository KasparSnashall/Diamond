import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.python.core.PyDictionary;
import org.python.core.PyInstance;
import org.python.core.PyObject;


public class IndexTab {
	
	GridData griddata;
	public static String mydata = ""; // loaded data string
	public static Text textbox; // loaded_data output
	public static PyObject data; // data to be used
	
	public Composite create(CTabFolder folder,Shell shell,Display display){// composite allows me to use more then one item in my tab folder
        Composite composite = new Composite(folder, SWT.LEFT);
        composite.setLayout(new GridLayout(3, false)); // three columns
        griddata = new GridData(SWT.FILL, SWT.FILL, true, false); 
        griddata.horizontalSpan = 3;
        Label Indexview  = new Label(composite,SWT.NONE);
        Indexview.setText("Index View");
        Indexview.setLayoutData(griddata);
        
        Button loadButton = new Button(composite, SWT.RADIO);
        loadButton.setText("Loaded data");
        loadButton.setSelection(true);
        
        textbox = new Text(composite,SWT.BORDER);
        textbox.setText(mydata);
        griddata = new GridData(150,20); 
        griddata.horizontalSpan = 2;
        textbox.setLayoutData(griddata);
        
        Button peaksButton = new Button(composite, SWT.RADIO);
        peaksButton.setText("From file");
        
        Text filepathbox =  new Text(composite,SWT.BORDER);
        griddata = new GridData(150,20); 
        filepathbox.setLayoutData(griddata);
        Button browse = new Button(composite, SWT.PUSH);
        browse.setAlignment(SWT.LEFT);
        browse.setText("Browse");
        filepathbox.setEnabled(false);
        browse.setEnabled(false);
        
        loadButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				filepathbox.setEnabled(false);
		        browse.setEnabled(false);
		        textbox.setEnabled(true);
				}
			
		});
        
        peaksButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				filepathbox.setEnabled(true);
		        browse.setEnabled(true);
		        textbox.setEnabled(false);
				}
			
		});
        browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String filepath = new FileDialog(shell).open();
				if (filepath != null) {
						// if a filepath is input
			          File file = new File(filepath);
			          if (file.isFile()){
			        	  // check is actually a file and not just directory
			        	  filepathbox.setText(filepath);
			        	  }
			          else
			        	  filepathbox.setText("Not a file");
				}
			}
		});
        
        
        
        CTabFolder indexfolder = new CTabFolder(composite, SWT.TOP); // create a tab set
        griddata = new GridData(SWT.FILL, SWT.FILL, true, true,2, 1);
        //griddata.horizontalSpan = 2;
        indexfolder.setLayoutData(griddata);
        
        
        CTabItem cTabItem1 = new CTabItem(indexfolder, SWT.BORDER);
        cTabItem1.setText("Ntreor");
        Properties_Widget customwidget = new Properties_Widget();
        Table table = customwidget.create(indexfolder);
        cTabItem1.setControl(table);
        
        Button addvariable = new Button(composite,SWT.NONE);
        addvariable.setText("add variables");
        
        // way to return values
        
        addvariable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				for (int loopIndex = 0; loopIndex < table.getItems().length; loopIndex++) {
			  	      TableItem myitem = table.getItem(loopIndex);
			  	      
			  	    
			  	      if (myitem.getChecked() == true){
			  	    	
				  	  System.out.print(myitem.getText(1));
			  	      }
			  	      else{
			  	    	  
			  	      }
			  	      
			  }
			}
		}); 
        Button run = new Button(composite, SWT.NONE);
        run.setText("Run");
    	
        
        
        CTabItem cTabItem2 = new CTabItem(indexfolder, SWT.BORDER);
        cTabItem2.setText("McMy");
        
        return composite;
        }
	
	public String getMydata(){
	return mydata;
	}
	
	public void setMydata(PyObject loadeddata) {
		mydata = loadeddata.toString();
		data = loadeddata;
		textbox.setText(mydata);
	}
	
}
