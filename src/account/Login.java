package account;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//import common.ImageUtil;
//
//import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Listener;

import play.ClientWindow;
import client.Client;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class Login {
	final static int ServerPort = 1234; 
	protected Shell shell;

	public void setShell(Shell shell) {
		this.shell = shell;
	}


	/**
	 * Launch the application.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// getting localhost ip 
		Client client = new Client();
		try {
			Login window = new Login();
			window.open(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(Client client) {
		Display display = Display.getDefault();
		createContents(display, client);
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				System.out.println(e.widget + " disposed");
				try {
					client.dos.writeUTF("CS-Close socket");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
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
	protected void createContents(Display display, Client client) {
		if(shell == null) shell = new Shell();
		shell.setSize(780, 480);
		shell.setText("Login");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 564, 64);

		Label lblWelcomeToMini = new Label(composite, SWT.NONE);
		lblWelcomeToMini.setBounds(186, 39, 151, 15);
		lblWelcomeToMini.setText("Welcome to Mini Kahoot!");

		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 80, 744, 266);

		Label lblName = new Label(composite_1, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblName.setBounds(56, 58, 76, 28);
		lblName.setText("Name:");

		Text nameTxt = new Text(composite_1, SWT.BORDER);
		nameTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		nameTxt.setBounds(158, 55, 172, 31);

		Label lblLogin = new Label(composite_1, SWT.NONE);
		lblLogin.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblLogin.setAlignment(SWT.CENTER);
		lblLogin.setBounds(201, 10, 100, 28);
		lblLogin.setText("Login");

		Label lblPassword = new Label(composite_1, SWT.NONE);
		lblPassword.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblPassword.setBounds(56, 102, 85, 28);
		lblPassword.setText("Password:");

		Text passwordTxt = new Text(composite_1, SWT.BORDER | SWT.PASSWORD);
		passwordTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		passwordTxt.setBounds(158, 99, 172, 31);

		Link link = new Link(composite_1, SWT.NONE);
		link.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Click to open register window
				try {
					for (Control kid : shell.getChildren()) {
						kid.dispose();
					}
					Register window = new Register();
					window.setShell(shell);
					window.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		link.setBounds(158, 151, 248, 28);
		link.setText("<a>Not have an account? Register now!</a>");

		Color redColor = new Color(display, 255, 0, 0);

		Label lblInvalid = new Label(composite_1, SWT.NONE);
		lblInvalid.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblInvalid.setBounds(158, 196, 246, 28);
		lblInvalid.setText("");
		lblInvalid.setForeground(redColor);

		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(10, 364, 730, 56);

		Button btnLogin = new Button(composite_2, SWT.NONE);
		btnLogin.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Get login information
				String name = nameTxt.getText();
				String password = passwordTxt.getText();
				String sRep = null;
				//Send message to server

				if(!checkValid(name) || !checkValid(password)) {
					lblInvalid.setText("Invalid name or password! Please try again!");
				}else {
					//Send account info to server
					try {
						client.dos.writeUTF(client.loginMsg(name, password));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						sRep = client.dis.readUTF();
						System.out.println(sRep);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(!client.isNameExist(sRep)) {
						//If not exist account, create error information
						lblInvalid.setText("Invalid account! Please try again!");
					}else if (!client.isPasswordCorrect(sRep)) {
						lblInvalid.setText("Wrong password");
					} 
					else {
						//If exist, send loginMsg to server
						//And go to client interface
						lblInvalid.setText("");
						//shell.close();
						try {
							for (Control kid : shell.getChildren()) {
								kid.dispose();
							}
							//String loginMsg = loginMsg(name, password);
							ClientWindow clientWindow = new ClientWindow();
							clientWindow.setShell(shell);
							clientWindow.setClientName(name);
							clientWindow.open(client);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}



			}
		});
		btnLogin.setBounds(593, 10, 96, 36);
		btnLogin.setText("Login");

	}

	private boolean checkValid(String str) {
		if(str.isBlank() || str.isEmpty()) return false;
		if(!str.matches("/^[0-9a-zA-Z]+$/")) return false;
		return true;
	}

	private boolean checkAccount(String name, String password) {
		//Get check account code in here
		//If exist: return true, otherwise return false
		return true;
	}
}
