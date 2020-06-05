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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

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
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 3;
		shell.setLayout(layout);
		
		shell.setText("Enter topic");
		
		new Label(shell, SWT.NULL);

		Label lblUser = new Label(shell, SWT.NONE);
		lblUser.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblUser.setAlignment(SWT.CENTER);
		lblUser.setBounds(267, 30, 201, 40);
		lblUser.setText("User: " + clientName);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 100;
		lblUser.setLayoutData(data);
		
		new Label(shell, SWT.NULL);
		
		Label lblEnterTopic = new Label(shell, SWT.NONE);
		lblEnterTopic.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblEnterTopic.setAlignment(SWT.CENTER);
		//lblEnterTopic.setBounds(82, 74, 129, 32);
		lblEnterTopic.setText("Enter topic: ");
		
		Text text = new Text(shell, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL | SWT.WRAP));
		//text.setBounds(279, 71, 278, 39);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.heightHint = 50;
		data.horizontalSpan = 2;
		text.setLayoutData(data);
		
		Color red = new Color(display, 255, 0, 0); 
		
		Label lblNotEnterTopic = new Label(shell, SWT.NONE);
		lblNotEnterTopic.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblNotEnterTopic.setAlignment(SWT.CENTER);
		//lblNotEnterTopic.setBounds(110, 142, 317, 39);
		lblNotEnterTopic.setText("");
		lblNotEnterTopic.setForeground(red);
		data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 50;
		data.horizontalSpan = 3;
		lblNotEnterTopic.setLayoutData(data);
		
		Button btnNext = new Button(shell, SWT.NONE);
		btnNext.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String topic = text.getText();
				if(topic == null || topic == "") {
					lblNotEnterTopic.setText("Not enter topic!");
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
		//btnNext.setBounds(579, 10, 117, 44);
		btnNext.setText("Next");
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		data.widthHint = 122;
		data.heightHint = 50;
		data.horizontalSpan = 3;
		btnNext.setLayoutData(data);
		

	}
}
