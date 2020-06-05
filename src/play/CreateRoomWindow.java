package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import java.io.IOException;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import client.Client;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class CreateRoomWindow {

	protected Shell shell;
	private String clientName;
	private Text roomTxt;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setClientName(String name) {
		this.clientName = name;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CreateRoomWindow window = new CreateRoomWindow();
			window.setClientName("abcd");
			//window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(Client client) {
		Display display = Display.getDefault();
		String sRep = null;
		List topicList;
		try {
			client.dos.writeUTF(client.getTopicList(clientName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sRep = client.dis.readUTF();
			System.out.println(sRep);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] parts = sRep.split("-");
		createContents(display, client, parts);
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
	protected void createContents(Display display, Client client, String[] parts) {
		if(shell == null) shell = new Shell();
		shell.setSize(1350, 700);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 3;
		shell.setLayout(layout);
		
		shell.setText("Create room");
		
		new Label(shell, SWT.NULL);

		Label lblUser = new Label(shell, SWT.NONE);
		lblUser.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblUser.setAlignment(SWT.CENTER);
		lblUser.setBounds(267, 30, 201, 40);
		lblUser.setText("User: " + clientName);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 100;
		lblUser.setLayoutData(data);
		
		new Label(shell, SWT.NULL);
		
		Label lblChooseTopic = new Label(shell, SWT.NONE);
		lblChooseTopic.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		//lblChooseTopic.setBounds(26, 53, 129, 40);
		lblChooseTopic.setText("Choose topic: ");
		
		List list = new List(shell, SWT.BORDER | SWT.V_SCROLL | SWT.SINGLE);
		list.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		//list.setBounds(195, 25, 454, 88);
		
		//CODE HERE
		//Add list of topics
		//Sample code
		//for(String topic: topicList)
		//   List.add(topic)
		System.out.println(parts[0]);	
		if (parts[0].contains("TL")) {
			for (int i = 1; i < parts.length; i++) {
				list.add(parts[i]);
				System.out.println(parts[i]);	
			}
		}
		else {
			System.out.println("Problem at getting topic");
		}
		
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		data.horizontalSpan = 2;
		data.verticalSpan = 4;
		int listHeight = list.getItemHeight() * 20;
		Rectangle trim = list.computeTrim(0, 0, 0, listHeight);
		data.heightHint = trim.height;
		list.setLayoutData(data);
		
		Color red = new Color(display, 255, 0, 0);
		
		Label lblPleaseChooseTopic = new Label(shell, SWT.NONE);
		lblPleaseChooseTopic.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		//lblPleaseChooseTopic.setBounds(26, 143, 365, 31);
		lblPleaseChooseTopic.setText("");
		data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 40;
		data.horizontalSpan = 3;
		lblPleaseChooseTopic.setLayoutData(data);
		
		Button btnNext = new Button(shell, SWT.NONE);
		btnNext.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = list.getSelectionIndex();
				if(index == -1) {
					lblPleaseChooseTopic.setText("Please choose topic!");
					lblPleaseChooseTopic.setForeground(red);
				} else {
					lblPleaseChooseTopic.setText("");
					
					//Get selected topic name
					Random random = new Random();
					String room = Integer.toString(random.nextInt(10000));
					String topic = list.getItem(index);
					try {
						client.dos.writeUTF(client.createRoomMsg(room, topic));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//***********************************************
					//CODE HERE
					//Create room and get roomID with selected topic
					
					
					//Go to start window
					try {
						for (Control kid : shell.getChildren()) {
							kid.dispose();
						}
						StartWindow window = new StartWindow();
						window.setShell(shell);
						window.setClientName(clientName);
						window.setRoom(room);
						window.open(client);
					} catch (Exception ex) {
						ex.printStackTrace();
					} 
				}
			}

			private String createRoom(String topic) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		//btnNext.setBounds(520, 205, 118, 47);
		btnNext.setText("Next");
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		data.widthHint = 122;
		data.heightHint = 50;
		data.horizontalSpan = 3;
		btnNext.setLayoutData(data);

	}
}
