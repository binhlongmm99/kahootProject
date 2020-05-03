package play;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import client.Client;

public class LeaderboardWindow {

	protected Shell shell;
	private String clientName;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setClientName(String clientName) {
		this.clientName = clientName;
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
		shell = new Shell();
		shell.setSize(450, 455);
		shell.setText("Leaderboard");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 424, 406);
		
		Table table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(110, 104, 205, 247);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnPlayer = new TableColumn(table, SWT.CENTER);
		tblclmnPlayer.setWidth(100);
		tblclmnPlayer.setText("Player");
		
		TableColumn tblclmnScore = new TableColumn(table, SWT.CENTER);
		tblclmnScore.setWidth(100);
		tblclmnScore.setText("Score");
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setBounds(155, 20, 92, 22);
		lblNewLabel.setText("Leaderboard");
		
		Label lblRoom = new Label(composite, SWT.NONE);
		lblRoom.setAlignment(SWT.CENTER);
		lblRoom.setBounds(155, 59, 92, 22);
		lblRoom.setText("Room: ");
		
		Button btnExit = new Button(composite, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				         kid.dispose();
				    }
					//String loginMsg = loginMsg(name, password);
					ClientWindow clientWindow = new ClientWindow();
					clientWindow.setShell(shell);
					clientWindow.setClientName(clientName);
					clientWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnExit.setBounds(315, 371, 75, 25);
		btnExit.setText("Exit");

	}

}
