package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import client.Client;

import org.eclipse.swt.widgets.Composite;

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
		//		createContents(client);
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Waiting to play");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 424, 81);

		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(144, 22, 128, 15);
		lblHello.setText("Hello, " + clientName);

		Label lblYouveJustJoined = new Label(composite, SWT.NONE);
		lblYouveJustJoined.setAlignment(SWT.CENTER);
		lblYouveJustJoined.setBounds(109, 56, 197, 15);
		lblYouveJustJoined.setText("You've just joined room " + room);

		Label lblPleaseWait = new Label(shell, SWT.NONE);
		lblPleaseWait.setAlignment(SWT.CENTER);
		lblPleaseWait.setBounds(112, 112, 202, 31);
		lblPleaseWait.setText("Please wait!");

		Button btnPlay = new Button(shell, SWT.NONE);
		btnPlay.setEnabled(false);
		btnPlay.setBounds(174, 174, 75, 25);
		btnPlay.setText("Play");
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			while(true) {
			try {
				sRep = client.dis.readUTF();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if(client.isReadyToPlay(sRep)) {
				//					lblPleaseWait.setText("Room is ready. Click Play to open");
				//					btnPlay.setEnabled(true);
				//					break;
				try {
					PlayWindow window = new PlayWindow();
					window.setClientName(clientName);
					window.setRoom(room);
					window.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
					break;
				}
			}
		}
	}
	}


	/**
	 * Create contents of the window.
	 */
	protected void createContents(Client client) {

		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Waiting to play");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 424, 81);

		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(144, 22, 128, 15);
		lblHello.setText("Hello, " + clientName);

		Label lblYouveJustJoined = new Label(composite, SWT.NONE);
		lblYouveJustJoined.setAlignment(SWT.CENTER);
		lblYouveJustJoined.setBounds(109, 56, 197, 15);
		lblYouveJustJoined.setText("You've just joined room " + room);

		Label lblPleaseWait = new Label(shell, SWT.NONE);
		lblPleaseWait.setAlignment(SWT.CENTER);
		lblPleaseWait.setBounds(112, 112, 202, 31);
		lblPleaseWait.setText("Please wait!");

		Button btnPlay = new Button(shell, SWT.NONE);
		btnPlay.setEnabled(true);
		btnPlay.setBounds(174, 174, 75, 25);
		btnPlay.setText("Ready");
		btnPlay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String sRep = null;
				btnPlay.setText("Play");
				btnPlay.setEnabled(false);
				while(true) {
					try {
						sRep = client.dis.readUTF();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if(client.isReadyToPlay(sRep)) {
						lblPleaseWait.setText("Room is ready. Click Play to open");
						btnPlay.setEnabled(true);
						break;
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
