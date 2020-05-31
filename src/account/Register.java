package account;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.io.IOException;

import org.eclipse.swt.SWT;
//import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import client.Client;

//import common.ImageUtil;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class Register {

	protected Shell shell;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Register window = new Register();
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
//		Image image = ImageUtil.getImage(display, "D:\\eclipse-workspace\\Kaggle\\src\\common\\icon.png");
		
		if(shell == null) shell = new Shell();
		shell.setSize(780, 480);
		shell.setText("Register");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 10, 575, 64);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblNewLabel.setBounds(150, 22, 211, 20);
		lblNewLabel.setText("Join Kahoot with us!");
		
//		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
//		lblNewLabel_1.setBounds(114, 10, 135, 44);
//		lblNewLabel_1.setImage(image);
		
		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(0, 78, 739, 270);
		
		Label lblName = new Label(composite_2, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblName.setBounds(27, 10, 64, 18);
		lblName.setText("Name:");
		
		Label lblPassword = new Label(composite_2, SWT.NONE);
		lblPassword.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblPassword.setBounds(27, 61, 102, 26);
		lblPassword.setText("Password:");
		
		Label lblNewLabel_2 = new Label(composite_2, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(27, 108, 121, 26);
		lblNewLabel_2.setText("Confirm password:");
		
		Text nameTxt = new Text(composite_2, SWT.BORDER);
		nameTxt.setBounds(168, 7, 217, 29);
		
		Text passwordTxt = new Text(composite_2, SWT.BORDER | SWT.PASSWORD);
		passwordTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		passwordTxt.setBounds(168, 58, 217, 29);
		
		Text confirmPwTxt = new Text(composite_2, SWT.BORDER | SWT.PASSWORD);
		confirmPwTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		confirmPwTxt.setBounds(168, 105, 217, 29);
		
		Color redColor = new Color(display, 255, 0, 0);
		Color greenColor = new Color(display, 0, 255, 0);
		
		Label lblErrorTxt = new Label(composite_2, SWT.NONE);
		lblErrorTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblErrorTxt.setBounds(58, 167, 254, 29);
		lblErrorTxt.setText("");
		lblErrorTxt.setForeground(redColor);
		
		Label lblSuccessTxt = new Label(composite_2, SWT.NONE);
		lblSuccessTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblSuccessTxt.setBounds(58, 213, 254, 29);
		lblSuccessTxt.setText("");
		lblSuccessTxt.setForeground(greenColor);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(0, 367, 739, 64);
		
		Button btnRegister = new Button(composite_1, SWT.NONE);
		btnRegister.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//If click
				//Get register information
				String name = nameTxt.getText();
				String password = passwordTxt.getText();
				String confirmPw = confirmPwTxt.getText();
				String sRep = null;
				
				System.out.println(password);
				System.out.println(confirmPw);
				if(password.compareTo(confirmPw) != 0) {
					//Check confirm password
					lblErrorTxt.setText("Confirm password is not similar to password. Please try again!");
				}else if(!checkValid(name) || !checkValid(password) || !checkValid(confirmPw)) {
					lblErrorTxt.setText("Invalid name or password! Please try again!");
				}else {
					//send account info to server
					try {
						client.dos.writeUTF(client.createNameMsg(name, password));
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
					if(client.isNameExist(sRep)) {
						//Check register information from server
						lblErrorTxt.setText("User name exist");
					}
					else {
						//Create new account
						
						lblErrorTxt.setText("");
						lblSuccessTxt.setText("You have created a new account! Click Exit button to close window!");
					}
				}
				
				//Check valid string
				 
			}
		});
		btnRegister.setBounds(529, 10, 75, 44);
		btnRegister.setText("Register");
		
		Button btnExit = new Button(composite_1, SWT.NONE);
		btnExit.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				         kid.dispose();
				    }
					Login window = new Login();
					window.setShell(shell);
					window.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnExit.setBounds(624, 10, 75, 44);
		btnExit.setText("Exit");

	}
	
	private boolean checkValid(String str) {
		if(str.isBlank() || str.isEmpty()) return false;
		if(!str.matches("/^[0-9a-zA-Z]+$/")) return false;
		return true;
	}

	private boolean checkRegister(String name, String password) {
		//Check register information from server
		//Correct: return true, otherwise: return false
		//Get code here
		return true;
	}
}
