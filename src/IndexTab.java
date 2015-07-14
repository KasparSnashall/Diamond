import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.python.core.PyObject;


public class IndexTab {
	
	GridData griddata;
	public static String mydata = ""; // loaded data string
	public static Text textbox; // loaded_data output
	public static PyObject data; // data to be used
	public static String filepath = LoadTab.get_filepath(); // get the current filepath
	
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
		        filepath = LoadTab.get_filepath();
		        
				}
			
		});
        
        filepathbox.addModifyListener(new ModifyListener() {
			@Override
		public void modifyText(ModifyEvent me) {
				filepath = filepathbox.getText();
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
        griddata = new GridData(SWT.FILL,SWT.FILL,false, true, 2, 2);
        //griddata.horizontalSpan = 2;
        griddata.grabExcessHorizontalSpace = true;
        indexfolder.setLayoutData(griddata);
        
        Table indexingprogs = new Table(composite,SWT.CHECK | SWT.SCROLL_LINE);
        griddata = new GridData(SWT.FILL,SWT.FILL,false, true, 1, 2);
        griddata.minimumHeight = 200;
        indexingprogs.setLayoutData(griddata);
        new TableColumn(indexingprogs,SWT.NULL).setText("Programs");
        indexingprogs.getColumn(0).pack();
        indexingprogs.setHeaderVisible(true); // make sure headers are seen
        
        String[] myprogslist = new String[]{"Ntreor","McMy"};
        
        
        for (int loopIndex = 0; loopIndex < myprogslist.length; loopIndex++) {
  	      TableItem item = new TableItem(indexingprogs, SWT.NULL);
  	      item.setText(0,myprogslist[loopIndex]);}
        
        

        Button addvariable = new Button(composite,SWT.NONE);
        addvariable.setText("Add variables");
        Button Reset = new Button(composite,SWT.NONE);
        Reset.setText("Reset");
        
        CTabItem cTabItem1 = new CTabItem(indexfolder, SWT.NONE);
        cTabItem1.setText("Ntreor");
        Properties_Widget Ntreor = new Properties_Widget();
        Table Ntreor_table = Ntreor.create(indexfolder, "python_code/ntreor.py", "Ntreor", "");
        cTabItem1.setControl(Ntreor_table);
        
        
        
        List<Properties_Widget> widgets_list = new ArrayList<Properties_Widget>();
        widgets_list.add(Ntreor);
        List<Table> table_list = new ArrayList<Table>();
        table_list.add(Ntreor_table);
        
        
        
		Color grey = display.getSystemColor(SWT.COLOR_GRAY);
		Color white = display.getSystemColor(SWT.COLOR_WHITE);
		Color green = display.getSystemColor(SWT.COLOR_GREEN);
		Color red = display.getSystemColor(SWT.COLOR_RED);
		
        Button run = new Button(composite, SWT.NONE);
        run.setText("Run");
        griddata = new GridData();

        griddata.horizontalAlignment = SWT.RIGHT;
        run.setLayoutData(griddata);
        
        Text output = new Text(composite,SWT.BORDER | SWT.MULTI | SWT.WRAP |SWT.V_SCROLL);
        griddata = new GridData(SWT.FILL,SWT.FILL,false, true, 1, 3);
        griddata.minimumHeight = 200;
        griddata.horizontalSpan = 3;
        output.setLayoutData(griddata);
        
        
        
        CTabItem cTabItem2 = new CTabItem(indexfolder, SWT.BORDER);
        cTabItem2.setText("McMy");
        
        
        
        
        addvariable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				for (int loopIndex = 0; loopIndex < widgets_list.size(); loopIndex++){
					Properties_Widget myprog = widgets_list.get(loopIndex);
					Table mytable = table_list.get(loopIndex);
					System.out.print(myprog.get_Name());
				
				try{
				for (int loopIndex1 = 0; loopIndex1 < mytable.getItems().length; loopIndex1++) {
				  	      TableItem myitem = mytable.getItem(loopIndex1);
				  	    
				  	      if (myitem.getChecked() == true){
				  	      String value = myitem.getText(3);
				  	      String key = myitem.getText(1);
				  	      
				  	      if (value == ""){
				  	    	output.append("Value for" + key + " Not defined\n");
				  	    	myitem.setBackground(red);}
				  	      else {
				  	    	myitem.setBackground(green);
				  	    	myprog.set_keywords(key, value);
				  	    	output.append(key + " " + value+"\n");
				  	    	
				  	      }
					  	  }}}
				catch (Exception e) {System.out.print(e);}
			
			}
				}
		});
        
        
        
        Reset.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent event) {
        		System.out.println(filepath);
        		for (int loopIndex = 0; loopIndex < widgets_list.size(); loopIndex++){
					Properties_Widget myprog = widgets_list.get(loopIndex);
					Table mytable = table_list.get(loopIndex);
					System.out.print(myprog.get_Name());
					for (int loopIndex1 = 0; loopIndex1 < mytable.getItems().length; loopIndex1++) {
				  	      TableItem myitem = mytable.getItem(loopIndex1);
				  	      myitem.setText(3,"");
				  	      myitem.setChecked(false);
				  	      myitem.setBackground(grey);
				  	      output.setText("");
				  	      
					}
					
					}
        	}
        });
        
        run.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent event) {
        		if (filepath == null){
        			output.append("no file slected \n");
        		}
        		else{
        			
        			try{
        		for(int loopIndex = 0; loopIndex < widgets_list.size(); loopIndex++){
        		Properties_Widget myprog = widgets_list.get(loopIndex);	
        		
        		
        		
        		File myfile = new File(filepath);
        		String mynewfilepath  = myfile.getParent().toString();
        		String mybase = "/scratch/workspace_git/Diamond";
        		Path base = Paths.get(mybase); // path of current module will make this atuomatic
        		Path myfilepath = Paths.get(mynewfilepath); // path of runfile
        		System.out.println(base);
        		System.out.println(myfilepath);
        		
        		
        		Path relativepath = base.relativize(myfilepath);
        		
        		System.out.print(relativepath.toString());
        		Ntreor.set_filepath(relativepath.toString()+"/");
        		
        		Ntreor.set_title(myfile.getName());
        		String newoutput = Ntreor.run();
        		output.append(newoutput);
        		}}
        		catch(Exception e){
        			System.out.println(e.getMessage());
        			
        		}
        		}
        		
        		}
        	});
        return composite;
        }
	
	public String getMydata(){
	return mydata;
	}
	
	public void setMydata(String myfilepath) {
		
		textbox.setText(myfilepath);
	}

}
