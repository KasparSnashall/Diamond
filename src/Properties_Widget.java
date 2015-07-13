import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import org.python.core.PyObject;
import org.python.core.PyString;



public class Properties_Widget {
	
	public static PyObject MyClass; 
	
	public Table create(CTabFolder indexfolder){
		
		Display display = Display.getCurrent();
		Color grey = display.getSystemColor(SWT.COLOR_GRAY);

		
		
		Table table = new Table(indexfolder, SWT.CHECK | SWT.MULTI | SWT.V_SCROLL); // table to be returned
		table.setHeaderVisible(true); // make sure headers are seen
		table.setToolTipText("Options for the data");
		GridData griddata = new GridData();
		griddata.grabExcessHorizontalSpace = true;
		griddata.horizontalSpan = 2;
		table.setLayoutData(griddata);
		
		interpreter ie = new interpreter(); // call my interpreter
		ie.execfile("python_code/ntreor.py"); // my file
		MyClass = ie.createClass("Ntreor","'data','title','filepath'");// invoke my class as a pyobject
		PyObject mydict = MyClass.invoke("get_standard_dict"); // calls the run function
        String mydicti = mydict.toString();// make into string
        String replaced = mydicti.replace("{", ""); // replace the curly brackets
        String replaced2 = replaced.replace("}", ""); // replace the second curly bracket
        String replaced3 = replaced2.replaceAll("'", "");
        String[] pairs = replaced3.split(","); // split to list
        
        Map<String, String> NtreorDict = new HashMap<String, String>();
        ArrayList<String>variables = new ArrayList<String>();
        ArrayList<String>values = new ArrayList<String>();
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            NtreorDict.put(keyValue[0], keyValue[1]); // map dict
            variables.add(keyValue[0]);
            values.add(keyValue[1]);
            //System.out.print(keyValue[0]+keyValue[1]); // print values 
        }
        
        String[] titles = {"Enable","Variable","Standard Value","New Value"};
        
    	for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
    	      TableColumn column = new TableColumn(table, SWT.NULL);
    	      column.setText(titles[loopIndex]);
    	      column.setWidth(100);
    	    }
    	
    	for (int loopIndex = 0; loopIndex < variables.size(); loopIndex++) {
    	      final TableItem item = new TableItem(table, SWT.NULL);
    	      item.setBackground(grey);
    	      item.setText(1,variables.get(loopIndex));
    	      item.setText(2,values.get(loopIndex));
    	      item.setText(3, "");
    	      
    	}
    	
    	final TableEditor editor = new TableEditor(table);
    	//The editor must have the same size as the cell and must
    	//not be any smaller than 50 pixels.
    	editor.horizontalAlignment = SWT.LEFT;
    	editor.grabHorizontal = true;
    	editor.minimumWidth = 50;
    	
    	
    	table.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			// Clean up any previous editor control
    			Control oldEditor = editor.getEditor();
    			if (oldEditor != null) oldEditor.dispose();
    	
    			// Identify the selected row
    			TableItem item = (TableItem)e.item;
    			if (item == null) return;
    	
    			// The control that will be the editor must be a child of the Table
    			Text newEditor = new Text(table, SWT.BORDER);
    			newEditor.setText(item.getText(3));
    			newEditor.addModifyListener(new ModifyListener() {
    				@Override
    			public void modifyText(ModifyEvent me) {
    					Text text = (Text)editor.getEditor();
    					editor.getItem().setText(3, text.getText());
    				}
    			});
    			newEditor.selectAll();
    			newEditor.setFocus();
    			editor.setEditor(newEditor, item, 3);
    		}
    	});
    	Color white = display.getSystemColor(SWT.COLOR_WHITE);
    	table.addSelectionListener(new SelectionAdapter(){
    		
    		public void widgetSelected(SelectionEvent event){
    			TableItem item = (TableItem)event.item;
    			if (item == null) return;
    			if(event.detail == SWT.CHECK) {
                    //Now what should I do here to get Whether it is a checked event or Unchecked event.
    				if (item.getChecked() == true){
  			  	      
  				  	  item.setBackground(white);
  			  	      }
  			  	      else{
  			  	    	  item.setBackground(grey);
  			  	    	  
  			  	      }
                }
    		}
    		
    	});

    	
    	
    	for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
    	      table.getColumn(loopIndex).pack();
    	    }
    	
    	
    	System.out.println(table.getListeners(0));
        
    
   
	
    
	return table;  
	}
	
	public void reset_keywords(){
		MyClass.invoke("reset_keywords");
		System.out.println("keywords reset");
	}
	public String set_keywords(String key, String value){	
		PyObject mykey = new PyString(key);
		PyObject myvalue = new PyString(value);
		PyObject[] mylist = new PyObject[]{mykey,myvalue};
		MyClass.invoke("set_keywords",mylist);
		System.out.print(MyClass.invoke("get_keywords").toString());
		return null;
		
	}

}
