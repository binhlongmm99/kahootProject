package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import client.Client;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.List;

class Question {
	private String question;
	private String a,b,c,d;
	private String answer;
	
	public Question(String Question, String A, String B, String C, String D, String Answer) {
		this.question = Question;
		this.a = A;
		this.b = B;
		this.c = C;
		this.d = D;
		this.answer = Answer;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public String getA() {
		return this.a;
	}
	
	public String getB() {
		return this.b;
	}
	
	public String getC() {
		return this.c;
	}
	
	public String getD() {
		return this.d;
	}
	
	public String getAnswer() {
		return this.answer;
	}
}
public class CreateQuestionWindow {

	protected Shell shell;
	private String clientName;
	//private String room;
	private String topicName;
	private String answer;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setClientName(String name) {
		this.clientName = name;
	}
	
	public void setTopic(String topic) {
		this.topicName = topic;
	}
	
//	public void setRoom(String room) {
//		this.room = room;
//	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CreateQuestionWindow window = new CreateQuestionWindow();
			window.setClientName("abcd");
			//window.setRoom("1");
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
		shell.setSize(486, 371);
		shell.setText("Create questions");
		
		Composite questionComposite = new Composite(shell, SWT.NONE);
		questionComposite.setBounds(0, 109, 460, 169);
		
		Label lblQuestion = new Label(questionComposite, SWT.NONE);
		lblQuestion.setBounds(20, 22, 55, 15);
		lblQuestion.setText("Question: ");
		
		Text questionTxt = new Text(questionComposite, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		questionTxt.setBounds(105, 22, 333, 39);
		
		Label lblChoices = new Label(questionComposite, SWT.NONE);
		lblChoices.setBounds(20, 81, 55, 15);
		lblChoices.setText("Choices: ");
		
		Button btnA = new Button(questionComposite, SWT.RADIO);
		btnA.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button source=  (Button) e.getSource();
				answer = source.getText();
			}
		});
		btnA.setBounds(84, 81, 35, 16);
		btnA.setText("A");
		btnA.setSelection(false);
		
		Button btnB = new Button(questionComposite, SWT.RADIO);
		btnB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button source=  (Button) e.getSource();
				answer = source.getText();
			}
		});
		btnB.setText("B");
		btnB.setBounds(261, 80, 35, 16);
		btnB.setSelection(false);
		
		Button btnC = new Button(questionComposite, SWT.RADIO);
		btnC.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button source=  (Button) e.getSource();
				answer = source.getText();
			}
		});
		btnC.setText("C");
		btnC.setBounds(84, 117, 35, 16);
		btnC.setSelection(false);
		
		Button btnD = new Button(questionComposite, SWT.RADIO);
		btnD.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button source=  (Button) e.getSource();
				answer = source.getText();
			}
		});
		btnD.setText("D");
		btnD.setBounds(261, 117, 35, 16);
		btnD.setSelection(false);
		
		Text ATxt = new Text(questionComposite, SWT.BORDER);
		ATxt.setBounds(125, 81, 117, 21);
		
		Text BTxt = new Text(questionComposite, SWT.BORDER);
		BTxt.setBounds(302, 81, 136, 21);
		
		Text CTxt = new Text(questionComposite, SWT.BORDER);
		CTxt.setBounds(125, 112, 117, 21);
		
		Text DTxt = new Text(questionComposite, SWT.BORDER);
		DTxt.setBounds(303, 112, 135, 21);
		
		Color red = new Color(display, 255, 0, 0);
		Color green = new Color(display, 0, 255, 0);
		
		Label lblError = new Label(questionComposite, SWT.NONE);
		lblError.setBounds(20, 144, 93, 15);
		lblError.setText("");
		
		Composite headerComposite = new Composite(shell, SWT.NONE);
		headerComposite.setBounds(0, 10, 460, 82);
		
		Label lblUser = new Label(headerComposite, SWT.NONE);
		lblUser.setBounds(24, 10, 55, 20);
		lblUser.setText("User: " + clientName);
		
		Label lblEnterTopic = new Label(headerComposite, SWT.NONE);
		lblEnterTopic.setBounds(24, 42, 76, 15);
		lblEnterTopic.setText("Topic: ");
		
		Text topicTxt = new Text(headerComposite, SWT.BORDER | SWT.READ_ONLY);
		topicTxt.setBounds(122, 36, 196, 21);
		topicTxt.setText(topicName);
		
		Composite btnComposite = new Composite(shell, SWT.NONE);
		btnComposite.setBounds(0, 284, 460, 50);
		
		Button btnCreate = new Button(btnComposite, SWT.NONE);
		btnCreate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//topicName = topicTxt.getText();
				String question = questionTxt.getText();
				String a = ATxt.getText();
				String b = BTxt.getText();
				String c = CTxt.getText();
				String d = DTxt.getText();
				
				btnA.setSelection(false);
				btnB.setSelection(false);
				btnC.setSelection(false);
				btnD.setSelection(false);
				
				if(isEmpty(topicName) || isEmpty(question) || isEmpty(a) || isEmpty(b) || isEmpty(c) || isEmpty(d) || isEmpty(answer)) {
					lblError.setForeground(red);
					lblError.setText("Error!");
				} else {
					try {
						Question q = new Question(question, a, b, c, d, answer);
						lblError.setText("Create success");
						lblError.setForeground(green);
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
//					System.out.println("Question " + q.getQuestion() + " is created");
//					System.out.println(q.getAnswer());
					
					//Reset text to enter new question
					lblError.setText("");
					questionTxt.setText("");
					ATxt.setText("");
					BTxt.setText("");
					CTxt.setText("");
					DTxt.setText("");
					answer = "";
				}
			}
		});
		btnCreate.setBounds(257, 10, 75, 25);
		btnCreate.setText("Create");
		
		Button btnConfirm = new Button(btnComposite, SWT.NONE);
		btnConfirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				System.out.println("You've just created questions for topic " + topic);
				//Click exit to back to Client window
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
		btnConfirm.setBounds(350, 10, 75, 25);
		btnConfirm.setText("Confirm");
	}
	
	private boolean isEmpty(String str) {
		if(str == null) return true;
		if(str != null && str == "") return true;
		return false;
	}
}
