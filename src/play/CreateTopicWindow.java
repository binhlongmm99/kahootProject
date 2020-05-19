package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import client.Client;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class CreateTopicWindow {

	protected Shell shell;
	private Text text;
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
		shell.setSize(480, 409);
		shell.setText("Enter topic");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 0, 454, 118);
		
		Label lblUser = new Label(composite, SWT.NONE);
		lblUser.setAlignment(SWT.CENTER);
		lblUser.setBounds(152, 42, 119, 41);
		lblUser.setText("User: ");
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 131, 444, 143);
		
		Label lblEnterTopic = new Label(composite_1, SWT.NONE);
		lblEnterTopic.setAlignment(SWT.CENTER);
		lblEnterTopic.setBounds(164, 40, 101, 29);
		lblEnterTopic.setText("Enter topic: ");
		
		text = new Text(composite_1, SWT.BORDER);
		text.setBounds(97, 75, 247, 29);
		
		Color red = new Color(display, 255, 0, 0); 
		
		Label lblNotEnterTopic = new Label(composite_1, SWT.NONE);
		lblNotEnterTopic.setAlignment(SWT.CENTER);
		lblNotEnterTopic.setBounds(144, 118, 141, 15);
		lblNotEnterTopic.setText("");
		
		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(10, 285, 444, 64);
		
		Button btnNext = new Button(composite_2, SWT.NONE);
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
		btnNext.setBounds(311, 10, 75, 25);
		btnNext.setText("Next");

	}
}
