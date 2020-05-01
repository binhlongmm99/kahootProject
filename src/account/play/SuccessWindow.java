package play;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import client.Client;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SuccessWindow {

	protected Shell shell;
	private String clientName;
	private String room;
	private ArrayList<Question> questions;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public void setClientName(String name) {
		this.clientName = name;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public void setQuestions(ArrayList<Question> q) {
		this.questions = q;
	}
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SuccessWindow window = new SuccessWindow();
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
		shell.setSize(485, 505);
		shell.setText("Create room successful!");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 10, 459, 64);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(33, 10, 155, 15);
		lblNewLabel.setText("Congratulations " + clientName);
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBounds(33, 39, 308, 15);
		lblNewLabel_1.setText("You've just created room " + room + " with the following questions:");
		
		if(questions.size() != 0) {
			for(int i=0; i<questions.size(); i++) {
				Question q = questions.get(i);
				Composite composite_1 = new Composite(shell, SWT.NONE);
				composite_1.setBounds(0, 75 + 156*i, 434, 156);
				
				Label lblEnterQuestion = new Label(composite_1, SWT.NONE);
				lblEnterQuestion.setLocation(10, 10);
				lblEnterQuestion.setSize(91, 15);
				lblEnterQuestion.setText("Question " + (i+1) + ":");
				
				Text questionTxt = new Text(composite_1, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY);
				questionTxt.setLocation(114, 10);
				questionTxt.setSize(298, 46);
				questionTxt.setText(q.getQuestion());
				
				Label lblEnterChoices = new Label(composite_1, SWT.NONE);
				lblEnterChoices.setLocation(10, 73);
				lblEnterChoices.setSize(91, 15);
				lblEnterChoices.setText("Enter 4 choices: ");
				
				Label lblA = new Label(composite_1, SWT.NONE);
				lblA.setLocation(106, 73);
				lblA.setSize(19, 15);
				lblA.setText("A: ");
				
				Text ATxt = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
				ATxt.setLocation(130, 70);
				ATxt.setSize(130, 21);
				ATxt.setText(q.getA());
				
				Label lblB = new Label(composite_1, SWT.NONE);
				lblB.setLocation(275, 71);
				lblB.setSize(19, 15);
				lblB.setText("B: ");
				
				Text BTxt = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
				BTxt.setLocation(301, 71);
				BTxt.setSize(123, 21);
				BTxt.setText(q.getB());
				
				Label lblC = new Label(composite_1, SWT.NONE);
				lblC.setBounds(105, 98, 19, 15);
				lblC.setText("C:");
				
				Text CTxt = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
				CTxt.setBounds(130, 98, 129, 21);
				CTxt.setText(q.getC());
				
				Label lblD = new Label(composite_1, SWT.NONE);
				lblD.setBounds(274, 98, 19, 15);
				lblD.setText("D:");
				
				Text DTxt = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
				DTxt.setBounds(302, 95, 122, 21);
				DTxt.setText(q.getD());
				
				Label lblEnterAnswerabcd = new Label(composite_1, SWT.NONE);
				lblEnterAnswerabcd.setBounds(10, 128, 129, 15);
				lblEnterAnswerabcd.setText("Enter answer(A/B/C/D):");
				
				Text answerTxt = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
				answerTxt.setBounds(145, 125, 76, 21);
				answerTxt.setText(q.getAnswer());
			}
		}
		
		Button btnExit = new Button(shell, SWT.NONE);
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
				//Add questions and room to DB
				//Questions save in questions variable
				//Room save in room variable
			}
		});
		btnExit.setBounds(373, 431, 75, 25);
		btnExit.setText("Exit");
	
	}
}
