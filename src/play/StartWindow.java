package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StartWindow {

	protected Shell shell;
	private String room;
	private String clientName;
	
	public void setClientName(String name) {
		this.clientName = name;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StartWindow window = new StartWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Start your room");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 424, 104);
		
		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(105, 23, 173, 15);
		lblHello.setText("Hello, " + clientName);
		
		Label lblYouveJustJoined = new Label(composite, SWT.NONE);
		lblYouveJustJoined.setBounds(122, 63, 173, 15);
		lblYouveJustJoined.setText("You've just joined room " + room);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(0, 110, 424, 141);
		
		Button btnStart = new Button(composite_1, SWT.NONE);
		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//CODE HERE
				//Tell server to start room and get questions of given room
				
				shell.dispose();
			}
		});
		btnStart.setBounds(155, 52, 75, 25);
		btnStart.setText("Start");
		
		Button btnCancel = new Button(composite_1, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnCancel.setBounds(155, 91, 75, 25);
		btnCancel.setText("Cancel");

	}
}
