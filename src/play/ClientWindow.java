package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import account.Login;
import client.Client;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class ClientWindow {

	protected Shell shell;
	private String clientName;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setClientName(String name) {
		clientName = name;
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ClientWindow window = new ClientWindow();
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
		shell.setSize(1350, 700);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 3;
		shell.setLayout(layout);
		
		shell.setText("Playing Kahoot");
		
		new Label(shell, SWT.NULL);

		Label lblKahoot = new Label(shell, SWT.NONE);
		lblKahoot.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblKahoot.setAlignment(SWT.CENTER);
		//lblKahoot.setBounds(285, 26, 134, 33);
		lblKahoot.setText("Kahoot\n\n\nHello " + clientName);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 100;
		lblKahoot.setLayoutData(data);
		
		new Label(shell, SWT.NULL);
		
		Button btnCreateNewQuestion = new Button(shell, SWT.NONE);
		btnCreateNewQuestion.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnCreateNewQuestion.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				          kid.dispose();
				    }
					CreateTopicWindow createTopicWindow = new CreateTopicWindow();
					createTopicWindow.setShell(shell);
					createTopicWindow.setClientName(clientName);
					createTopicWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		//btnCreateNewQuestion.setBounds(258, 66, 199, 40);
		data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 100;
		data.horizontalSpan = 3;
		btnCreateNewQuestion.setLayoutData(data);
		btnCreateNewQuestion.setText("Create new questions");
		
		Button btnJoinGame = new Button(shell, SWT.NONE);
		btnJoinGame.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnJoinGame.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				          kid.dispose();
				    }
					JoinGameWindow joinGameWindow = new JoinGameWindow();
					joinGameWindow.setClientName(clientName);
					joinGameWindow.setShell(shell);
					joinGameWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		//btnJoinGame.setBounds(258, 148, 199, 46);
		btnJoinGame.setText("Join game");
		data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 100;
		data.horizontalSpan = 3;
		btnJoinGame.setLayoutData(data);
		
		

	}

}
