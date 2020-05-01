package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import client.Client;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class LeaderboardWindow {

	protected Shell shell;
	private String clientName;
	
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
			LeaderboardWindow window = new LeaderboardWindow();
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
		createContents(client);
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
	protected void createContents(Client client) {
		if(shell == null) shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Leaderboard");
		
		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				          kid.dispose();
				    }
					ClientWindow window = new ClientWindow();
					window.setShell(shell);
					window.setClientName(clientName);
					window.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnExit.setBounds(145, 98, 75, 25);
		btnExit.setText("Exit");

	}
}
