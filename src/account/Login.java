package account;

import org.eclipse.swt.widgets.Display;
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

import play.ClientWindow;
import client.Client;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;

public class Login {
	final static int ServerPort = 1234; 
	protected Shell shell;
	private Text nameTxt;
	private Text passwordTxt;
	
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
		if(shell == null) {
			shell = new Shell();
		}
		shell.setSize(600, 300);
		shell.setText("Login");

		//		Image image = ImageUtil.getImage(display, "common/icon.png");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 564, 64);

		Label lblWelcomeToMini = new Label(composite, SWT.NONE);
		lblWelcomeToMini.setBounds(186, 39, 151, 15);
		lblWelcomeToMini.setText("Welcome to Mini Kahoot!");

		//		CLabel lblNewLabel = new CLabel(composite, SWT.NONE);
		//		lblNewLabel.setBounds(176, 12, 61, 21);
		//		lblNewLabel.setImage(image);

		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 80, 564, 119);

		Label lblName = new Label(composite_1, SWT.NONE);
		lblName.setBounds(86, 30, 55, 15);
		lblName.setText("Name:");

		nameTxt = new Text(composite_1, SWT.BORDER);
		nameTxt.setBounds(158, 27, 76, 21);

		Label lblLogin = new Label(composite_1, SWT.NONE);
		lblLogin.setBounds(246, 10, 55, 15);
		lblLogin.setText("Login");

		Label lblPassword = new Label(composite_1, SWT.NONE);
		lblPassword.setBounds(86, 57, 55, 15);
		lblPassword.setText("Password:");

		passwordTxt = new Text(composite_1, SWT.BORDER | SWT.PASSWORD);
		passwordTxt.setBounds(158, 54, 76, 21);

		Link link = new Link(composite_1, SWT.NONE);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Click to open register window
				try {
					for (Control kid : shell.getChildren()) {
				          kid.dispose();
				    }
					Register regWindow = new Register();
					regWindow.setShell(shell);
					regWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		link.setBounds(158, 104, 195, 15);
		link.setText("<a>Not have an account? Register now!</a>");

		Color redColor = new Color(display, 255, 0, 0);

		Label lblInvalid = new Label(composite_1, SWT.NONE);
		lblInvalid.setBounds(158, 81, 246, 15);
		lblInvalid.setText("");
		lblInvalid.setForeground(redColor);

		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(0, 205, 574, 56);

		Button btnLogin = new Button(composite_2, SWT.NONE);
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
							//String loginMsg = loginMsg(name, password);
							for (Control kid : shell.getChildren()) {
						          kid.dispose();
						    }
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
		btnLogin.setBounds(383, 10, 75, 25);
		btnLogin.setText("Login");

	}

	private boolean checkValid(String str) {
		if(str.isBlank() || str.isEmpty()) return false;
		return true;
	}

	private boolean checkAccount(String name, String password) {
		//Get check account code in here
		//If exist: return true, otherwise return false
		return true;
	}
}
