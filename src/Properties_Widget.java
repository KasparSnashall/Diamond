import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.python.core.PyInstance;
import org.python.core.PyObject;


public class Properties_Widget {
	
	public Table create(CTabFolder indexfolder){
		
		Table table = new Table(indexfolder, SWT.NONE); // table to be returned
		table.setHeaderVisible(true); // make sure headers are seen
		table.setToolTipText("Options for the data");
		interpreter ie = new interpreter(); // call my interpreter
		ie.execfile("python_code/ntreor.py"); // my file
		PyInstance Loader = ie.createClass("Ntreor","'data','title','filepath'");// invoke my class as a pyobject
		PyObject mydict = Loader.invoke("get_keywords"); // calls the run function
        String mydicti = mydict.toString();// maike into string
        String replaced = mydicti.replace("{", " "); // replace the curly brackets
        String replaced2 = replaced.replace("}", " "); // replace the second curly bracket
        String[] pairs = replaced2.split(","); // split to list
        
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
        
        String[] titles = {"Enable","Variable","Standard Value","New value",""};
        
    	for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
    	      TableColumn column = new TableColumn(table, SWT.NULL);
    	      column.setText(titles[loopIndex]);
    	      column.setWidth(100);
    	    }
    	
    	for (int loopIndex = 0; loopIndex < variables.size(); loopIndex++) {
    	      final TableItem item = new TableItem(table, SWT.NULL);
    	      
    	      TableEditor editor = new TableEditor(table);
    	      Button check = new Button(table,SWT.CHECK);
    	      
    	      editor.grabHorizontal = true;
    	      editor.setEditor(check, item, 0);
    	      
    	      item.setText(1,variables.get(loopIndex));
    	      item.setText(2,values.get(loopIndex));
    	      item.setText(3, "");
    	      
    	      TableEditor editor1 = new TableEditor(table);
    	      Text text = new Text(table, SWT.BORDER);
    	      text.setText("");
    	      text.setEnabled(false);
    	      editor1.grabHorizontal = true;
    	      editor1.setEditor(text,item,4);
    	      check.addSelectionListener(new SelectionAdapter()
  				{
  			    @Override
  			    public void widgetSelected(SelectionEvent e)
  			    {
  			    	if (check.getSelection())
  		            {text.setEnabled(true);
  		            }
  		        else
  		        	{text.setEnabled(false);
  		        	
  		        	}
  			    }
  			    
  			});
    	      text.addModifyListener(new ModifyListener() {
                  public void modifyText(ModifyEvent e) {
                          Text text = (Text)editor1.getEditor();
                          editor.getItem().setText(3,text.getText());
                  }
    	      
    	      });
    	     
    	}
    		
    	
    	for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
    	      table.getColumn(loopIndex).pack();
    	    }
    	
    	
    	
        
    
   
		
    
	return table;    
	}

}
