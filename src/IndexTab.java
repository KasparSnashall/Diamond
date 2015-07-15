import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
	public static String filepath = LoadTab.get_filepath(); // get the current filepath (if loaded data this will be the loaded path)
	
	public Composite create(CTabFolder folder,Shell shell,Display display){// composite allows me to use more then one item in my tab folder
		// new master composite
        Composite composite = new Composite(folder, SWT.LEFT);
        composite.setLayout(new GridLayout(3, false)); // three columns composite
       
        // the index view
        Label Indexview  = new Label(composite,SWT.NONE);
        Indexview.setText("Index View");
        griddata = new GridData(SWT.FILL, SWT.FILL, true, false); 
        griddata.horizontalSpan = 3; // 3 columns wide 
        Indexview.setLayoutData(griddata);
        
        // the load radio button
        Button loadButton = new Button(composite, SWT.RADIO);
        loadButton.setText("Loaded data");
        loadButton.setSelection(true);
        
        // the load data textbox
        textbox = new Text(composite,SWT.BORDER);
        textbox.setText(mydata);
        griddata = new GridData(150,20); 
        griddata.horizontalSpan = 2;
        textbox.setLayoutData(griddata);
        
        // from file radio button
        Button peaksButton = new Button(composite, SWT.RADIO);
        peaksButton.setText("From file");
        
        // text box for the filepath
        Text filepathbox =  new Text(composite,SWT.BORDER);
        griddata = new GridData(150,20); 
        filepathbox.setLayoutData(griddata);
        
        // browse button
        Button browse = new Button(composite, SWT.PUSH);
        browse.setAlignment(SWT.LEFT);
        browse.setText("Browse");
        filepathbox.setEnabled(false);
        browse.setEnabled(false);
    

        
        // browse selection function
        
        browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String filepath = new FileDialog(shell).open();
				if (filepath != null) {
						// if a filepath is input
			          File file = new File(filepath);
			          if (file.isFile()){
			        	  // check is actually a file and not just directory or stub
			        	  filepathbox.setText(filepath);
			        	  }
			          else
			        	  filepathbox.setText("Not a file");
				}
			}
		});
       
        
        // properties widget tabs
        CTabFolder indexfolder = new CTabFolder(composite, SWT.TOP); // create a tab set
        griddata = new GridData(SWT.FILL,SWT.FILL,false, true, 2, 2);
        griddata.grabExcessHorizontalSpace = true;
        indexfolder.setLayoutData(griddata);
        
        // indeing programs list
        Table indexingprogs = new Table(composite,SWT.CHECK | SWT.SCROLL_LINE); // list of programs
        griddata = new GridData(SWT.FILL,SWT.FILL,false, true, 1, 2);
        griddata.minimumHeight = 200;
        indexingprogs.setLayoutData(griddata);
        new TableColumn(indexingprogs,SWT.NULL).setText("Programs");
        indexingprogs.getColumn(0).pack();
        indexingprogs.setHeaderVisible(true); // make sure headers are seen
        
        // list of programs available
        String[] myprogslist = new String[]{"Ntreor","McMaille"};
        // add the list to the programs table
        for (int loopIndex = 0; loopIndex < myprogslist.length; loopIndex++) {
  	      TableItem item = new TableItem(indexingprogs, SWT.NULL);
  	      item.setText(0,myprogslist[loopIndex]);}
        
        
        // add variable button
        Button addvariable = new Button(composite,SWT.NONE);
        addvariable.setText("Add variables");
        
        // reset button
        Button Reset = new Button(composite,SWT.NONE);
        Reset.setText("Reset");
        
        // Add the ntreor tab, at time of writing this is the only one available
        CTabItem cTabItem1 = new CTabItem(indexfolder, SWT.NONE);
        cTabItem1.setText("Ntreor");
        // get the properties widget
        Properties_Widget Ntreor = new Properties_Widget();
        Table Ntreor_table = Ntreor.create(indexfolder, "python_code/ntreor.py", "Ntreor", ""); // file, class name, options
        cTabItem1.setControl(Ntreor_table);
        
        // add in second tab currently a stub
        CTabItem cTabItem2 = new CTabItem(indexfolder, SWT.BORDER);
        cTabItem2.setText("McMaille");
        
        // make a list of the current properties widgets
        List<Properties_Widget> widgets_list = new ArrayList<Properties_Widget>();
        widgets_list.add(Ntreor);
        // make a list of the properties widgets tables
        List<Table> table_list = new ArrayList<Table>();
        table_list.add(Ntreor_table);
        
        
        // colours used in the properties widget
		Color grey = display.getSystemColor(SWT.COLOR_GRAY);
		Color white = display.getSystemColor(SWT.COLOR_WHITE);
		Color green = display.getSystemColor(SWT.COLOR_GREEN);
		Color red = display.getSystemColor(SWT.COLOR_RED);
		
		// run button
        Button run = new Button(composite, SWT.NONE);
        run.setText("Run");
        griddata = new GridData();
        griddata.horizontalAlignment = SWT.RIGHT; // align the button right
        run.setLayoutData(griddata);
        
        // output textbox
        Text output = new Text(composite,SWT.BORDER | SWT.MULTI | SWT.WRAP |SWT.V_SCROLL);
        griddata = new GridData(SWT.FILL,SWT.FILL,false, true, 1, 3);
        griddata.minimumHeight = 200;
        griddata.horizontalSpan = 3;
        output.setLayoutData(griddata);
        
        
        // add variable button function
        addvariable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (indexfolder.getEnabled() == false){
					output.append("Prebuilt files may not have variables added \n");
				}
				for (int loopIndex = 0; loopIndex < widgets_list.size(); loopIndex++){
					// go through the list of programs,tables and programs list (check boxes)
					Properties_Widget myprog = widgets_list.get(loopIndex); // get the program
					Table mytable = table_list.get(loopIndex); // get the table
					TableItem ischecked = indexingprogs.getItem(loopIndex); // make sure the program is selected
					
					if (ischecked.getChecked() == true){ // is it checked?
				try{
					
				for (int loopIndex1 = 0; loopIndex1 < mytable.getItems().length; loopIndex1++) {
						  // go through table get checked values
				  	      TableItem myitem = mytable.getItem(loopIndex1);
				  	      if (myitem.getChecked() == true){
				  	      String value = myitem.getText(3);
				  	      String key = myitem.getText(1);
				  	      
				  	      if (value == ""){ // make sure value is not null
				  	    	output.append("Value for" + key + " Not defined\n");
				  	    	myitem.setBackground(red); // make background red if it is
				  	    	}
				  	      else {
				  	    	myitem.setBackground(green); // make green
				  	    	myprog.set_keywords(key, value); // use the program to set keywords
				  	    	output.append(key + " " + value+"\n"); // print the values added
				  	    	
				  	      }
					  	  }}}
				catch (Exception e) {System.out.print(e);}
			
			}
				}
			}
		});
        
        
        // reset button function
        Reset.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent event) {
        		for (int loopIndex = 0; loopIndex < widgets_list.size(); loopIndex++){
					Properties_Widget myprog = widgets_list.get(loopIndex);
					Table mytable = table_list.get(loopIndex);
					myprog.reset_keywords(); // reset the keywords in the program
					for (int loopIndex1 = 0; loopIndex1 < mytable.getItems().length; loopIndex1++) {
				  	      TableItem myitem = mytable.getItem(loopIndex1); // go through each table and set the item text to ""
				  	      myitem.setText(3,"");
				  	      myitem.setChecked(false); // unckeck the boxes
				  	      myitem.setBackground(grey); // clear the background
				  	      output.setText(""); // reset the output
				  	      
					}
					
					}
        	}
        });
        
        // run button function
        run.addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent event) {
        		if (filepath == null){
        			output.append("no file slected \n"); // file or data must be selected
        		}
        		else{
        			
        			try{
        		for(int loopIndex = 0; loopIndex < widgets_list.size(); loopIndex++){
        		Properties_Widget myprog = widgets_list.get(loopIndex); // get the program
        		TableItem myitem = indexingprogs.getItem(loopIndex); // get checked?
        		
        		
        		if (myitem.getChecked() == true){
        		
        		
        		output.append(myprog.get_Name()+" Running \n"); // running...
        		File myfile = new File(filepath); // check if file
        		String mynewfilepath  = myfile.getParent().toString(); // get the parent directory
        		String mybase = "/scratch/workspace_git/Diamond"; // current base directory
        		Path base = Paths.get(mybase); // current module path will make this automatic
        		Path myfilepath = Paths.get(mynewfilepath); 

        		Path relativepath = base.relativize(myfilepath); // relative path of runfile (Ntreor requires this)
        		System.out.print(relativepath.toString()); // dev check
        		
        		myprog.set_filepath(relativepath.toString()+"/"); // pass the relative filepath to the prog
        		myprog.set_title(myfile.getName()); // set the filename (file.end , handled in the python script ) 
        		String newoutput = myprog.run(); // the output
        		output.append(newoutput); // print the output
        		
        		
        		}}}
        		catch(Exception e){
        			output.append(e.getMessage());
        			
        		}
        		}
        		}
        	});
        
        // load button function
        loadButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				filepathbox.setEnabled(false);
		        browse.setEnabled(false);
		        textbox.setEnabled(true);
		        filepath = LoadTab.get_filepath();
		        indexfolder.setEnabled(true);
		        indexfolder.setVisible(true);
		        
				}
			
		});
        
        // filepathbox function (changes String filepath to selected)
        filepathbox.addModifyListener(new ModifyListener() {
			@Override
		public void modifyText(ModifyEvent me) {
				filepath = filepathbox.getText();
			}
		});
        
        // peaks button function changes the filepath to the loaded data's filepath
        peaksButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				filepathbox.setEnabled(true);
		        browse.setEnabled(true);
		        textbox.setEnabled(false);
		        indexfolder.setEnabled(false);
				}
			
		});
        return composite;
        }
	
	public String getMydata(){
		// return the data to other progs
	return mydata;
	}
	
	public void setMydata(String myfilepath) {
		// loaded tab sends the filepath
		textbox.setText(myfilepath);
	}

}
