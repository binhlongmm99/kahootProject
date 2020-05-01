package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import client.Client;

import org.eclipse.swt.widgets.Composite;

import java.io.IOException;
import java.util.ArrayList;

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
	private String room;
	private int numOfQuestions = 0;
	
	private ArrayList<Question> questions;
	private Text[] questionTxt;
	private Text[] ATxt;
	private Text[] BTxt;
	private Text[] CTxt;
	private Text[] DTxt;
	private Text[] answerTxt;
	
	public void setClientName(String name) {
		this.clientName = name;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CreateQuestionWindow window = new CreateQuestionWindow();
			window.setClientName("abcd");
			window.setRoom("1");
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
		shell = new Shell();
		shell.setSize(450, 548);
		shell.setText("Create questions");
					
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 434, 75);
		
		Label lblUser = new Label(composite, SWT.NONE);
		lblUser.setAlignment(SWT.CENTER);
		lblUser.setBounds(85, 10, 70, 15);
		lblUser.setText("User: " + clientName);
		
		Label lblRoom = new Label(composite, SWT.NONE);
		lblRoom.setAlignment(SWT.CENTER);
		lblRoom.setBounds(245, 10, 55, 15);
		lblRoom.setText("Room: " + room);
		
		Label lblChooseNumberOf = new Label(composite, SWT.NONE);
		lblChooseNumberOf.setBounds(10, 36, 170, 15);
		lblChooseNumberOf.setText("Choose number of questions: ");
		
		List list = new List(composite, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		list.setBounds(183, 32, 55, 33);
		list.add("1");
		list.add("2");
		
		Color redColor = new Color(display, 255, 0, 0);
		
		Label lblNoSelection = new Label(composite, SWT.NONE);
		lblNoSelection.setBounds(10, 60, 157, 15);
		lblNoSelection.setText("");
		lblNoSelection.setForeground(redColor);

		
		Button btnCreate = new Button(composite, SWT.NONE);
		btnCreate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = list.getSelectionIndex();
				if(index == -1) {
					lblNoSelection.setText("Not select!");
				} else {
					lblNoSelection.setText("");
					btnCreate.dispose();
					numOfQuestions = index + 1;
					questions = new ArrayList<Question>();
					questionTxt = new Text[numOfQuestions];
					ATxt = new Text[numOfQuestions];
					BTxt = new Text[numOfQuestions];
					CTxt = new Text[numOfQuestions];
					DTxt = new Text[numOfQuestions];
					answerTxt = new Text[numOfQuestions];
					
					for(int i=0; i<numOfQuestions; i++) {
						Composite composite_1 = new Composite(shell, SWT.NONE);
						composite_1.setBounds(0, 75 + 156*i, 434, 156);
						
						Label lblEnterQuestion = new Label(composite_1, SWT.NONE);
						lblEnterQuestion.setLocation(10, 10);
						lblEnterQuestion.setSize(91, 15);
						lblEnterQuestion.setText("Enter question:");
						
						questionTxt[i] = new Text(composite_1, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
						questionTxt[i].setLocation(114, 10);
						questionTxt[i].setSize(298, 46);
						
						Label lblEnterChoices = new Label(composite_1, SWT.NONE);
						lblEnterChoices.setLocation(10, 73);
						lblEnterChoices.setSize(91, 15);
						lblEnterChoices.setText("Enter 4 choices: ");
						
						Label lblA = new Label(composite_1, SWT.NONE);
						lblA.setLocation(106, 73);
						lblA.setSize(19, 15);
						lblA.setText("A: ");
						
						ATxt[i] = new Text(composite_1, SWT.BORDER);
						ATxt[i].setLocation(130, 70);
						ATxt[i].setSize(130, 21);
						
						Label lblB = new Label(composite_1, SWT.NONE);
						lblB.setLocation(275, 71);
						lblB.setSize(19, 15);
						lblB.setText("B: ");
						
						BTxt[i] = new Text(composite_1, SWT.BORDER);
						BTxt[i].setLocation(301, 71);
						BTxt[i].setSize(123, 21);
						
						Label lblC = new Label(composite_1, SWT.NONE);
						lblC.setBounds(105, 98, 19, 15);
						lblC.setText("C:");
						
						CTxt[i] = new Text(composite_1, SWT.BORDER);
						CTxt[i].setBounds(130, 98, 129, 21);
						
						Label lblD = new Label(composite_1, SWT.NONE);
						lblD.setBounds(274, 98, 19, 15);
						lblD.setText("D:");
						
						DTxt[i] = new Text(composite_1, SWT.BORDER);
						DTxt[i].setBounds(302, 95, 122, 21);
						
						Label lblEnterAnswerabcd = new Label(composite_1, SWT.NONE);
						lblEnterAnswerabcd.setBounds(10, 128, 129, 15);
						lblEnterAnswerabcd.setText("Enter answer(A/B/C/D):");
						
						answerTxt[i] = new Text(composite_1, SWT.BORDER);
						answerTxt[i].setBounds(145, 125, 76, 21);
						
					}
				}
				
			}
		});
		btnCreate.setBounds(255, 31, 75, 25);
		btnCreate.setText("Create");
						
		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(0, 458, 434, 51);
		
		Label lblTxt = new Label(composite_2, SWT.NONE);
		lblTxt.setBounds(10, 16, 333, 20);
		lblTxt.setText("");
		lblTxt.setForeground(redColor);
		
		Button btnSubmit = new Button(composite_2, SWT.NONE);
		btnSubmit.setBounds(349, 11, 75, 25);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Check if no question was created
				if(numOfQuestions == 0) {
					lblTxt.setText("No question was created! Please create questions!");
				} else {
					for(int i=0; i<numOfQuestions; i++) {
						String question = questionTxt[i].getText();
						String a = ATxt[i].getText();
						String b = BTxt[i].getText();
						String c = CTxt[i].getText();
						String d = DTxt[i].getText();
						String answer = answerTxt[i].getText();
						
						if(isNotValid(question) || isNotValid(a) || isNotValid(b) || isNotValid(c) || isNotValid(d) || isNotValid(answer)) {
							lblTxt.setText("Error! Please fill all informations at question #" + (i+1));
						} else if(isNotAnswer(answer)) {
							lblTxt.setText("Invalid answer! Please check answer at question #" + (i+1));
						} else {
							lblTxt.setText("");
							//Send question to server
							try {
								client.dos.writeUTF(client.createQuestionMsg(question, a, b, c, d, answer));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								client.dis.readUTF();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							Question q = new Question(question, a, b, c, d, answer);
							questions.add(q);
						}
					}
					
					if(lblTxt.getText().isEmpty()) {
						//No error
						shell.close();
						try {
							SuccessWindow window = new SuccessWindow();
							window.setClientName(clientName);
							window.setRoom(room);
							window.setQuestions(questions);
							window.open();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});
		btnSubmit.setText("Submit");	
	
	}
	
	private boolean isNotValid(String str) {
		if(str == null || str.isBlank() || str.isEmpty()) return true;
		return false;
	}
	
	private boolean isNotAnswer(String answer) {
		answer = answer.trim();
		if(answer.compareTo("A") == 0 || answer.compareTo("B") == 0 || answer.compareTo("C") == 0 || answer.compareTo("D") == 0) return false;
		return true;
	}
}
