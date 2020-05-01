package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class JoinRoomWindow {

	protected Shell shell;
	private String clientName;
	
	public void setClientName(String name) {
		this.clientName = name;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JoinRoomWindow window = new JoinRoomWindow();
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
		createContents(display);
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
	protected void createContents(Display display) {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Join room");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 424, 64);
		
		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setBounds(170, 23, 55, 15);
		lblHello.setText("Hello, " + clientName);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(0, 71, 424, 157);
		
		Label lblChooseRoom = new Label(composite_1, SWT.NONE);
		lblChooseRoom.setBounds(21, 10, 122, 15);
		lblChooseRoom.setText("Choose room: ");
		
		List list = new List(composite_1, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		list.setBounds(106, 6, 46, 137);
		
		//CODE HERE
		//Connect to DB to get all available rooms and add to list
		//Go to https://o7planning.org/vi/11321/huong-dan-java-swt-list to see details of List
		//Sample code: 
		//roomList = getAvailableRooms();
		//for(String room: roomList)
		//	list.add(room);	
		//room must be a string variable
				
		
		Color red = new Color(display, 255, 0, 0);
		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setBounds(176, 32, 122, 15);
		lblNewLabel.setText("");
		lblNewLabel.setForeground(red);
		
		Button btnJoin = new Button(shell, SWT.NONE);
		btnJoin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = list.getSelectionIndex();
				if(index == -1) {
					lblNewLabel.setText("Please choose room!");
				} else {
					lblNewLabel.setText("");
					String room = list.getItem(index);
//					shell.close();
					if(isHost(clientName, room)) {
						try {
							StartWindow window = new StartWindow();
							window.setClientName(clientName);
							window.setRoom(room);
							window.open();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						try {
							WaitWindow window = new WaitWindow();
							window.setClientName(clientName);
							window.setRoom(room);
							window.open();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					
				}
			}
		});
		btnJoin.setBounds(248, 234, 75, 25);
		btnJoin.setText("Join");
		
		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnExit.setBounds(329, 234, 75, 25);
		btnExit.setText("Exit");

	}
	
	private boolean isHost(String clientName, String room) {
		//CODE HERE
		//Check if client is host of given room or not
		//Return true if client is host
	}
}
