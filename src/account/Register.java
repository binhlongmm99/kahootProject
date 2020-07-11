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
		shell.setSize(1350, 700);
		shell.setText("Register");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 1314, 77);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblNewLabel.setBounds(457, 10, 325, 46);
		lblNewLabel.setText("Join Kahoot with us!");
		
//		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
//		lblNewLabel_1.setBounds(114, 10, 135, 44);
//		lblNewLabel_1.setImage(image);
		
		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(10, 93, 1314, 459);
		
		Label lblName = new Label(composite_2, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblName.setBounds(30, 109, 173, 30);
		lblName.setText("Name:");
		
		Label lblPassword = new Label(composite_2, SWT.NONE);
		lblPassword.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblPassword.setBounds(30, 187, 173, 33);
		lblPassword.setText("Password:");
		
		Label lblNewLabel_2 = new Label(composite_2, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(30, 261, 173, 36);
		lblNewLabel_2.setText("Confirm password:");
		
		Text nameTxt = new Text(composite_2, SWT.BORDER);
		nameTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		nameTxt.setBounds(220, 106, 389, 33);
		
		Text passwordTxt = new Text(composite_2, SWT.BORDER | SWT.PASSWORD);
		passwordTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		passwordTxt.setBounds(220, 184, 389, 36);
		
		Text confirmPwTxt = new Text(composite_2, SWT.BORDER | SWT.PASSWORD);
		confirmPwTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		confirmPwTxt.setBounds(220, 258, 389, 39);
		
		Color redColor = new Color(display, 255, 0, 0);
		Color greenColor = new Color(display, 0, 255, 0);
		
		Label lblErrorTxt = new Label(composite_2, SWT.NONE);
		lblErrorTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblErrorTxt.setBounds(220, 330, 491, 39);
		lblErrorTxt.setText("");
		lblErrorTxt.setForeground(redColor);
		
		Label lblSuccessTxt = new Label(composite_2, SWT.NONE);
		lblSuccessTxt.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblSuccessTxt.setBounds(220, 397, 491, 39);
		lblSuccessTxt.setText("");
		lblSuccessTxt.setForeground(greenColor);
		
		Label lblRegister = new Label(composite_2, SWT.NONE);
		lblRegister.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblRegister.setAlignment(SWT.CENTER);
		lblRegister.setBounds(532, 43, 191, 36);
		lblRegister.setText("Register");
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 570, 1314, 81);
		
		Button btnRegister = new Button(composite_1, SWT.NONE);
		btnRegister.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
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
		btnRegister.setBounds(925, 10, 106, 47);
		btnRegister.setText("Register");
		
		Button btnExit = new Button(composite_1, SWT.NONE);
		btnExit.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
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
		btnExit.setBounds(1098, 10, 106, 47);
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
