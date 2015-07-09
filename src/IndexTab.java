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
        
        
        interpreter ie = new interpreter(); // call my interpreter
		ie.execfile("python_code/mydict.py"); // my file
		PyInstance Loader = ie.createClass("MyDict","");// invoke my class as a pyobject
		PyObject mydict = Loader.invoke("get_dict"); // calls the run function
        String mydicti = mydict.toString();// remove the brackets
        String[] pairs = mydicti.split(",");
        Map<String, String> myMap = new HashMap<String, String>();
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            System.out.print(keyValue[0]);
            myMap.put(keyValue[0], keyValue[1]);
        }
        
        
        
        
        
        
        CTabFolder indexfolder = new CTabFolder(composite, SWT.TOP); // create a tab set
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true,3, 1);
        indexfolder.setLayoutData(data);
        
        
        CTabItem cTabItem1 = new CTabItem(indexfolder, SWT.BORDER);
        cTabItem1.setText("Ntreor");
        
        Table table = new Table(indexfolder, SWT.NONE);
        String[] titles = {" ", "C", "!", "Description", "Resource", "In Folder", "Location"};
    	for (int i=0; i<titles.length; i++) {
    		TableColumn column = new TableColumn (table, SWT.NONE);
    		column.setText (titles [i]);
    	}	
    	int count = 12;
    	for (int i=0; i<count; i++) {
    		TableItem item = new TableItem (table, SWT.NONE);
    		item.setText (0, "x");
    		item.setText (1, "y");
    		item.setText (2, "!");
    		item.setText (3, "this stuff behaves the way I expect");
    		item.setText (4, "almost everywhere");
    		item.setText (5, "some.folder");
    		item.setText (6, "line " + i + " in nowhere");
    	}
    	for (int i=0; i<titles.length; i++) {
    		table.getColumn (i).pack ();
    	}	
        cTabItem1.setControl(table);
        
        
        
        
        
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
