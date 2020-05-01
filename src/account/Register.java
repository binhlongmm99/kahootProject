package account;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.io.IOException;

import org.eclipse.swt.SWT;
//import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import client.Client;

//import common.ImageUtil;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class Register {

	protected Shell shell;
	private Text nameTxt;
	private Text passwordTxt;
	private Text confirmPwTxt;

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
		
		shell = new Shell();
		shell.setSize(600, 300);
		shell.setText("Register");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 10, 575, 64);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(255, 22, 106, 20);
		lblNewLabel.setText("Join Kahoot with us!");
		
//		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
//		lblNewLabel_1.setBounds(114, 10, 135, 44);
//		lblNewLabel_1.setImage(image);
		
		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(0, 78, 575, 125);
		
		Label lblName = new Label(composite_2, SWT.NONE);
		lblName.setBounds(88, 10, 55, 15);
		lblName.setText("Name:");
		
		Label lblPassword = new Label(composite_2, SWT.NONE);
		lblPassword.setBounds(88, 31, 55, 15);
		lblPassword.setText("Password:");
		
		Label lblNewLabel_2 = new Label(composite_2, SWT.NONE);
		lblNewLabel_2.setBounds(86, 52, 110, 15);
		lblNewLabel_2.setText("Confirm password:");
		
		nameTxt = new Text(composite_2, SWT.BORDER);
		nameTxt.setBounds(220, 7, 165, 21);
		
		passwordTxt = new Text(composite_2, SWT.BORDER | SWT.PASSWORD);
		passwordTxt.setBounds(220, 28, 165, 21);
		
		confirmPwTxt = new Text(composite_2, SWT.BORDER | SWT.PASSWORD);
		confirmPwTxt.setBounds(220, 49, 158, 21);
		
		Color redColor = new Color(display, 255, 0, 0);
		Color greenColor = new Color(display, 0, 255, 0);
		
		Label lblErrorTxt = new Label(composite_2, SWT.NONE);
		lblErrorTxt.setBounds(220, 76, 217, 15);
		lblErrorTxt.setText("");
		lblErrorTxt.setForeground(redColor);
		
		Label lblSuccessTxt = new Label(composite_2, SWT.NONE);
		lblSuccessTxt.setBounds(220, 97, 217, 15);
		lblSuccessTxt.setText("");
		lblSuccessTxt.setForeground(greenColor);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(0, 209, 575, 42);
		
		Button btnRegister = new Button(composite_1, SWT.NONE);
		btnRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//If click
				//Get register information
				String name = nameTxt.getText();
				String password = passwordTxt.getText();
				String confirmPw = confirmPwTxt.getText();
				String sRep = null;
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
		btnRegister.setBounds(361, 10, 75, 25);
		btnRegister.setText("Register");
		
		Button btnExit = new Button(composite_1, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Close window
				shell.close();
			}
		});
		btnExit.setBounds(458, 10, 75, 25);
		btnExit.setText("Exit");

	}
	
	private boolean checkValid(String str) {
		if(str.isBlank() || str.isEmpty()) return false;
		return true;
	}

	private boolean checkRegister(String name, String password) {
		//Check register information from server
		//Correct: return true, otherwise: return false
		//Get code here
		return true;
	}
}
