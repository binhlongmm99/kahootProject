package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;

import client.Client;

import org.eclipse.swt.widgets.Group;

public class PlayWindow {

	protected Shell shell;
	private String room;
	private String clientName;
	private ArrayList<Question> questions;
	private String[] answers;
	
	private long startTime;
	private long endTime;
	private long playTime;	
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setClientName(String name) {
		this.clientName = name;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public ArrayList<Question> getQuestions(String room) {
		//CODE HERE
		//Return array list of all questions of given room from DB
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PlayWindow window = new PlayWindow();
		//	window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(Client client) {
		Display display = Display.getDefault();
		createContents(display,client);
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
		shell.setSize(595, 539);
		shell.setText("Ready to play");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 569, 108);
		
		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setBounds(254, 15, 105, 15);
		lblHello.setText("Hello, " + clientName);
		
		Label lblYouveJustJoined = new Label(composite, SWT.NONE);
		lblYouveJustJoined.setBounds(205, 36, 154, 15);
		lblYouveJustJoined.setText("You've just joined room " + room);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(48, 41, 55, 15);
		lblNewLabel.setText("New Label");
		
		Button btnStartGame = new Button(composite, SWT.NONE);
		btnStartGame.setBounds(467, 10, 75, 25);
		btnStartGame.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Delete button Start, create button Submit and print all questions
				startTime = System.currentTimeMillis();
				
				btnStartGame.dispose();
				
				questions = getQuestions(room);
				if(questions.size() != 0) {
					answers = new String[questions.size()];
					for(Question q: questions) {
						int i = questions.indexOf(q);
						Composite composite_1_1 = new Composite(shell, SWT.NONE);
						composite_1_1.setBounds(0, 298 + 172*i, 569, 171);
						
						Label lblQuestion_1 = new Label(composite_1_1, SWT.NONE);
						lblQuestion_1.setText("Question " + (i+1) + ":");
						lblQuestion_1.setBounds(21, 10, 55, 15);
						
						Text questionTxt = new Text(composite_1_1, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL);
						questionTxt.setBounds(82, 10, 463, 54);
						questionTxt.setText(q.getQuestion());
						
//						Label lblAnswer = new Label(composite_1_1, SWT.NONE);
//						lblAnswer.setBounds(10, 49, 55, 15);
//						lblAnswer.setText("");
//						
//						Label lblCorrect = new Label(composite_1_1, SWT.NONE);
//						lblCorrect.setBounds(10, 31, 55, 15);
//						lblCorrect.setText("");
						
						Button btnA = new Button(composite_1_1, SWT.RADIO);
						btnA.setText("A");
						btnA.setBounds(77, 70, 32, 16);
						btnA.addSelectionListener(new SelectionAdapter()  {
							 
				            @Override
				            public void widgetSelected(SelectionEvent e) {
				            	//Update client's answer
				                Button source=  (Button) e.getSource();
				                 
				                if(source.getSelection())  {
				                    answers[i] = source.getText();
				                }
				            }
				             
				        });
						
						Button btnB = new Button(composite_1_1, SWT.RADIO);
						btnB.setText("B");
						btnB.setBounds(77, 110, 32, 16);
						btnB.addSelectionListener(new SelectionAdapter()  {
							 
				            @Override
				            public void widgetSelected(SelectionEvent e) {
				            	//Update client's answer
				                Button source=  (Button) e.getSource();
				                 
				                if(source.getSelection())  {
				                    answers[i] = source.getText();
				                }
				            }
				             
				        });
						
						Button btnC = new Button(composite_1_1, SWT.RADIO);
						btnC.setText("C");
						btnC.setBounds(319, 70, 32, 16);
						btnC.addSelectionListener(new SelectionAdapter()  {
							 
				            @Override
				            public void widgetSelected(SelectionEvent e) {
				            	//Update client's answer
				                Button source=  (Button) e.getSource();
				                 
				                if(source.getSelection())  {
				                    answers[i] = source.getText();
				                }
				            }
				             
				        });
						
						Button btnD = new Button(composite_1_1, SWT.RADIO);
						btnD.setText("D");
						btnD.setBounds(319, 110, 32, 16);
						btnD.addSelectionListener(new SelectionAdapter()  {
							 
				            @Override
				            public void widgetSelected(SelectionEvent e) {
				            	//Update client's answer
				                Button source=  (Button) e.getSource();
				                 
				                if(source.getSelection())  {
				                    answers[i] = source.getText();
				                }
				            }
				             
				        });
						
						Text ATxt = new Text(composite_1_1, SWT.BORDER | SWT.READ_ONLY);
						ATxt.setBounds(115, 70, 178, 27);
						ATxt.setText(q.getA());
						
						Text BTxt = new Text(composite_1_1, SWT.BORDER | SWT.READ_ONLY);
						BTxt.setBounds(115, 108, 178, 27);
						BTxt.setText(q.getB());
						
						Text CTxt = new Text(composite_1_1, SWT.BORDER | SWT.READ_ONLY);
						CTxt.setBounds(357, 70, 188, 27);
						CTxt.setText(q.getC());
						
						Text DTxt = new Text(composite_1_1, SWT.BORDER | SWT.READ_ONLY);
						DTxt.setBounds(357, 108, 188, 27);
						DTxt.setText(q.getD());
						
					}
				}
				
				Button btnSubmit = new Button(composite, SWT.NONE);
				btnSubmit.setBounds(467, 41, 75, 25);
				btnSubmit.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						//Delete button Submit, print all results
						btnSubmit.dispose();
						
						endTime = System.currentTimeMillis();
						playTime = endTime - startTime;
						int seconds = (int) (playTime / 1000) % 60 ;
						int minutes = (int) ((playTime / (1000*60)) % 60); 
						lblNewLabel.setText(minutes + "m" + seconds + "s");
						
						Label lblNumOfCorrectAns = new Label(composite, SWT.NONE);
						lblNumOfCorrectAns.setAlignment(SWT.CENTER);
						lblNumOfCorrectAns.setBounds(132, 57, 281, 15);
						lblNumOfCorrectAns.setText("");
						
						Label lblCorrectAns = new Label(composite, SWT.NONE);
						lblCorrectAns.setAlignment(SWT.CENTER);
						lblCorrectAns.setBounds(142, 83, 271, 15);
						lblCorrectAns.setText("");
						
						int numOfCorrectAns = 0;
						String correctAns = "Answer:";
						
						for(int i=0; i<questions.size(); i++) {
							correctAns += " " + (i+1) + "." + questions.get(i).getAnswer() + ";";
							if(answers[i] != null && answers[i].compareTo(questions.get(i).getAnswer()) == 0)
								numOfCorrectAns += 1;
						}
						
						lblNumOfCorrectAns.setText("Correct: " + numOfCorrectAns + "/" + questions.size());
						lblCorrectAns.setText(correctAns);
					}
				});
				btnSubmit.setText("Submit");

			}
		});
		btnStartGame.setText("Start");
		
//		Button btnSubmit = new Button(composite, SWT.NONE);
//		btnSubmit.setBounds(467, 41, 75, 25);
//		btnSubmit.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		btnSubmit.setText("Submit");
		
		Button btnExitRoom = new Button(composite, SWT.NONE);
		btnExitRoom.setBounds(467, 72, 75, 25);
		btnExitRoom.addSelectionListener(new SelectionAdapter() {
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
			}
		});
		btnExitRoom.setText("Exit room");
		
		
		
	}
}
