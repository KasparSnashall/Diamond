
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class CSD {

    public static void main(String[] args) {
    	Display display = new Display();
        Shell shell = new Shell(display);

        // create a new GridLayout with two columns
        // of different size
        GridLayout layout = new GridLayout(2, false);

        // set the layout to the shell
        shell.setLayout(layout);
        
        
        // creating intial tabs for operation selection
        CTabFolder folder = new CTabFolder(shell, SWT.TOP); // create a tab set
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true,2, 1);
        folder.setLayoutData(data);
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
        
        
        
        Text text = new Text(folder, SWT.BORDER); // new text element
        text.setText("Hello"); // new variable text gets a value
        cTabItem1.setControl(text); // add this to a tab



        
        // create a label and a button
        Label label = new Label(shell, SWT.NONE);
        label.setText("A label");
        Button button = new Button(shell, SWT.PUSH);
        button.setText("Press Me");

        // create a new label that will span two columns
        label = new Label(shell, SWT.BORDER);
        label.setText("This is a label");
        // create new layout data
        label.setLayoutData(data);

        // create a new label which is used as a separator
        label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
        
        // create new layout data
        data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalSpan = 2;
        label.setLayoutData(data);

        // create a right aligned button
        Button b = new Button(shell, SWT.PUSH);
        b.setText("New Button");

        data = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
        b.setLayoutData(data);
        
         // create a spinner with min value 0 and max value 1000
        Spinner spinner = new Spinner(shell, SWT.READ_ONLY);
        spinner.setMinimum(0);
        spinner.setMaximum(1000);
        spinner.setSelection(500);
        spinner.setIncrement(1);
        spinner.setPageIncrement(100);
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        gridData.widthHint = SWT.DEFAULT;
        gridData.heightHint = SWT.DEFAULT;
        gridData.horizontalSpan = 2;
        spinner.setLayoutData(gridData);

        Composite composite = new Composite(shell, SWT.BORDER);
        gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        gridData.horizontalSpan = 2;
        composite.setLayoutData(gridData);
        composite.setLayout(new GridLayout(1, false));

        Text txtTest = new Text(composite, SWT.NONE);
        txtTest.setText("Testing");
        gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        txtTest.setLayoutData(gridData);

        Text txtMoreTests = new Text(composite, SWT.NONE);
        txtMoreTests.setText("Another test");
        
        Group group = new Group(shell, SWT.NONE);
        group.setText("This is my group");
        gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
        gridData.horizontalSpan = 2;
        group.setLayoutData(gridData);
        group.setLayout(new RowLayout(SWT.VERTICAL));
        Text txtAnotherTest = new Text(group, SWT.NONE);
        txtAnotherTest.setText("Another test");

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
