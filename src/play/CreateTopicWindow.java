package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import client.Client;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class CreateTopicWindow {

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
			CreateTopicWindow window = new CreateTopicWindow();
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
		if(shell == null) shell = new Shell();
		shell.setSize(1350, 700);
		shell.setText("Enter topic");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 0, 1314, 118);
		
		Label lblUser = new Label(composite, SWT.NONE);
		lblUser.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblUser.setAlignment(SWT.CENTER);
		lblUser.setBounds(472, 30, 302, 52);
		lblUser.setText("User: " + clientName);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 136, 1314, 389);
		
		Label lblEnterTopic = new Label(composite_1, SWT.NONE);
		lblEnterTopic.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblEnterTopic.setAlignment(SWT.CENTER);
		lblEnterTopic.setBounds(83, 74, 129, 32);
		lblEnterTopic.setText("Enter topic: ");
		
		Text text = new Text(composite_1, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		text.setBounds(279, 71, 614, 52);
		
		Color red = new Color(display, 255, 0, 0); 
		
		Label lblNotEnterTopic = new Label(composite_1, SWT.NONE);
		lblNotEnterTopic.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblNotEnterTopic.setAlignment(SWT.CENTER);
		lblNotEnterTopic.setBounds(281, 181, 451, 52);
		lblNotEnterTopic.setText("");
		
		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(10, 548, 1314, 87);
		
		Button btnNext = new Button(composite_2, SWT.NONE);
		btnNext.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String topic = text.getText();
				if(topic == null || topic == "") {
					lblNotEnterTopic.setText("Not enter topic!");
					lblNotEnterTopic.setForeground(red);
				} else {
					lblNotEnterTopic.setText("");
					try {
						client.dos.writeUTF(client.createTopicMsg(clientName, topic));
						System.out.println(client.dis.readUTF());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
//					try {
//						System.out.println(client.dis.readUTF());
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					try {
						for (Control kid : shell.getChildren()) {
					          kid.dispose();
					    }
						CreateQuestionWindow createQuestionWindow = new CreateQuestionWindow();
						createQuestionWindow.setShell(shell);
						createQuestionWindow.setClientName(clientName);
						createQuestionWindow.setTopic(topic);
						createQuestionWindow.open(client);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnNext.setBounds(1085, 10, 128, 51);
		btnNext.setText("Next");
		
		Button btnBack = new Button(composite_2, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Go to previous window (Client window)
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
		btnBack.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnBack.setBounds(53, 10, 128, 51);
		btnBack.setText("Back");
		

	}
}
