package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import client.Client;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class WaitWindow {

	protected Shell shell;
	private String room;
	private String clientName;

	public void setShell(Shell shell) {
		this.shell = shell;
	}

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
			WaitWindow window = new WaitWindow();
			//window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(Client client) {
		String sRep = null;
		Display display = Display.getDefault();

		//		createContents(display, client);

		if(shell == null) shell = new Shell();
		shell.setSize(780, 480);
		shell.setText("Waiting to play");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 744, 162);

		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(259, 25, 235, 32);
		lblHello.setText("Hello, " + clientName);

		Label lblYouveJustJoined = new Label(composite, SWT.NONE);
		lblYouveJustJoined.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblYouveJustJoined.setAlignment(SWT.CENTER);
		lblYouveJustJoined.setBounds(214, 90, 326, 32);
		lblYouveJustJoined.setText("You've just joined room " + room);

		Label lblPleaseWait = new Label(shell, SWT.NONE);
		lblPleaseWait.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lblPleaseWait.setAlignment(SWT.CENTER);
		lblPleaseWait.setBounds(279, 215, 202, 38);
		lblPleaseWait.setText("Please wait!");

		Button btnPlay = new Button(shell, SWT.NONE);
		btnPlay.setEnabled(false);
		btnPlay.setBounds(317, 293, 123, 48);
		btnPlay.setText("Play");

		shell.open();
		shell.layout();
		//		while (!shell.isDisposed()) {
		//			if (!display.readAndDispatch()) {
		//				display.sleep();
		//			}
		//		}

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				// ...
				String msg = null;
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
					try { 

						// read the message sent to this client 
						msg = client.dis.readUTF(); 

						if(client.isReadyToPlay(msg)) {
							System.out.println(msg); 
							for (Control kid : shell.getChildren()) {
								kid.dispose();
							}
							
							client.dos.writeUTF(client.createScoreMsg(clientName, room, 0));
							PlayWindow window = new PlayWindow();
							window.setShell(shell);
							window.setClientName(clientName);
							window.setRoom(room);
							window.open(client);
							

						}
						else{
							client.dos.writeUTF("WH");
						}
						
					} catch (IOException e) { 

						e.printStackTrace(); 
					} 
				}



			} 
		});



	}

	private boolean isReadyToPlay(String room) {
		//CODE HERE
		//Check if room is ready to play or not
		//Return true if room is ready
		return true;
	}

}
