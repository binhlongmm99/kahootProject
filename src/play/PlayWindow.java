package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
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
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Text;

import client.Client;

import org.eclipse.swt.widgets.Group;

public class PlayWindow {

	protected Shell shell;
	private String room;
	private String clientName;
	private ArrayList<Question> questions;
	
	private int index;
	
	
	
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
		shell.setSize(780, 463);
		shell.setText("Playing");
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
				
		index = 0;
		
		Color red = new Color(display, 255, 0, 0);
		Color green = new Color(display, 0, 255, 0);
		
		Composite lbComposite = new Composite(shell, SWT.BORDER);
		lbComposite.setLayoutData(new RowData(223, 421));
		
		Label lblLeaderboard = new Label(lbComposite, SWT.NONE);
		lblLeaderboard.setAlignment(SWT.CENTER);
		lblLeaderboard.setBounds(56, 22, 96, 15);
		lblLeaderboard.setText("Leaderboard");
		
		Composite tableComposite = new Composite(lbComposite, SWT.NONE);
		tableComposite.setBounds(10, 73, 203, 276);
		
		Table table = new Table(tableComposite, SWT.BORDER | SWT.HIDE_SELECTION | SWT.V_SCROLL);
		table.setBounds(24, 22, 154, 230);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnPlayer = new TableColumn(table, SWT.CENTER);
		tblclmnPlayer.setWidth(81);
		tblclmnPlayer.setText("Player");
		
		TableColumn tblclmnScore = new TableColumn(table, SWT.CENTER);
		tblclmnScore.setWidth(66);
		tblclmnScore.setText("Score");
		
		Label lblUpdate = new Label(lbComposite, SWT.NONE);
		lblUpdate.setAlignment(SWT.CENTER);
		lblUpdate.setBounds(60, 52, 78, 15);
		lblUpdate.setText("Update");
		
		Composite exitComposite = new Composite(lbComposite, SWT.NONE);
		exitComposite.setBounds(71, 365, 127, 46);
		
		Button btnExit = new Button(exitComposite, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		btnExit.setEnabled(false);
		btnExit.setBounds(26, 10, 75, 25);
		btnExit.setText("Exit");
		
		Composite answerComposite = new Composite(shell, SWT.NONE);
		answerComposite.setLayoutData(new RowData(529, 424));
		
		Composite topComposite = new Composite(answerComposite, SWT.NONE);
		topComposite.setBounds(10, 10, 509, 125);
		
		Label lblPlayer = new Label(topComposite, SWT.NONE);
		lblPlayer.setBounds(47, 20, 55, 15);
		lblPlayer.setText("Player: " + clientName);
		
		Label lblRoom = new Label(topComposite, SWT.NONE);
		lblRoom.setBounds(377, 20, 55, 15);
		lblRoom.setText("Room: " + room);
		
		Label lblQuestion = new Label(topComposite, SWT.NONE);
		lblQuestion.setAlignment(SWT.CENTER);
		lblQuestion.setBounds(202, 53, 96, 15);
		lblQuestion.setText("Question " + (index+1));
		
		Label lblAnswer = new Label(topComposite, SWT.NONE);
		lblAnswer.setBounds(229, 88, 55, 15);
		lblAnswer.setText("");
		
		Composite questionComposite = new Composite(answerComposite, SWT.NONE);
		questionComposite.setBounds(10, 141, 509, 223);
		
		Label lblQuestion_1 = new Label(questionComposite, SWT.NONE);
		lblQuestion_1.setBounds(24, 35, 55, 15);
		lblQuestion_1.setText("Question: ");
		
		Text text = new Text(questionComposite, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL);
		text.setBounds(117, 29, 362, 68);
		text.setText(questions.get(index).getQuestion());
		
		Text ATxt = new Text(questionComposite, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.CANCEL);
		ATxt.setBounds(91, 125, 131, 21);
		ATxt.setText(questions.get(index).getA());
		
		Text BTxt = new Text(questionComposite, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.CANCEL);
		BTxt.setBounds(348, 125, 131, 21);
		BTxt.setText(questions.get(index).getB());
		
		Text CTxt = new Text(questionComposite, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.CANCEL);
		CTxt.setBounds(91, 168, 131, 21);
		CTxt.setText(questions.get(index).getC());
		
		Text DTxt = new Text(questionComposite, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.CANCEL);
		DTxt.setBounds(348, 168, 131, 21);
		DTxt.setText(questions.get(index).getD());
		
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				btnA.setSelection(false);
//				btnB.setSelection(false);
//				btnC.setSelection(false);
//				btnD.setSelection(false);
				lblAnswer.setText("");
				index = index + 1;
				if(index < questions.size()) {
					text.setText(questions.get(index).getQuestion());
					ATxt.setText(questions.get(index).getA());
					BTxt.setText(questions.get(index).getB());
					CTxt.setText(questions.get(index).getC());
					DTxt.setText(questions.get(index).getD());
					lblUpdate.setText("Update #" + index);
					lblQuestion.setText("Question " + (index+1));
					countdown(display, this, true);
				} else {
					answerComposite.dispose();
					shell.setSize(250, 463);
					btnExit.setEnabled(true);
					lblUpdate.setText("Result");
				}
			}
			
		};
		
		countdown(display, runnable, true);
		
		Button btnA = new Button(questionComposite, SWT.RADIO);
		btnA.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				countdown(display, runnable, false);
				Button source=  (Button) e.getSource();
				String ans = source.getText();
				
				if(ans.compareTo(questions.get(index).getAnswer()) == 0) {
					lblAnswer.setForeground(green);
					lblAnswer.setText("Correct");
				} else {
					lblAnswer.setForeground(red);
					lblAnswer.setText("Not " + ans + ". Answer is " + questions.get(index).getAnswer());
				}
				
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnA.setSelection(false);
				lblAnswer.setText("");
				index = index + 1;
				if(index < questions.size()) {
					text.setText(questions.get(index).getQuestion());
					ATxt.setText(questions.get(index).getA());
					BTxt.setText(questions.get(index).getB());
					CTxt.setText(questions.get(index).getC());
					DTxt.setText(questions.get(index).getD());
					lblUpdate.setText("Update #" + index);
					lblQuestion.setText("Question " + (index+1));
					countdown(display, runnable, true);
				} else {
					answerComposite.dispose();
					shell.setSize(250, 463);
					btnExit.setEnabled(true);
					lblUpdate.setText("Result");
				}
				
			}
		});
		btnA.setBounds(26, 127, 36, 16);
		btnA.setText("A");
		
		Button btnB = new Button(questionComposite, SWT.RADIO);
		btnB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				countdown(display, runnable, false);
				Button source=  (Button) e.getSource();
				String ans = source.getText();

				if(ans.compareTo(questions.get(index).getAnswer()) == 0) {
					lblAnswer.setForeground(green);
					lblAnswer.setText("Correct");
				} else {
					lblAnswer.setForeground(red);
					lblAnswer.setText("Not " + ans + ". Answer is " + questions.get(index).getAnswer());
				}
				
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnB.setSelection(false);
				lblAnswer.setText("");
				index = index + 1;
				if(index < questions.size()) {
					text.setText(questions.get(index).getQuestion());
					ATxt.setText(questions.get(index).getA());
					BTxt.setText(questions.get(index).getB());
					CTxt.setText(questions.get(index).getC());
					DTxt.setText(questions.get(index).getD());
					lblUpdate.setText("Update #" + index);
					lblQuestion.setText("Question " + (index+1));
					countdown(display, runnable, true);
				} else {
					answerComposite.dispose();
					shell.setSize(250, 463);
					btnExit.setEnabled(true);
					lblUpdate.setText("Result");
				}
			}
		});
		btnB.setText("B");
		btnB.setBounds(294, 127, 36, 16);
		
		Button btnC = new Button(questionComposite, SWT.RADIO);
		btnC.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				countdown(display, runnable, false);
				Button source=  (Button) e.getSource();
				String ans = source.getText();

				if(ans.compareTo(questions.get(index).getAnswer()) == 0) {
					lblAnswer.setForeground(green);
					lblAnswer.setText("Correct");
				} else {
					lblAnswer.setForeground(red);
					lblAnswer.setText("Not " + ans + ". Answer is " + questions.get(index).getAnswer());
				}
				
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnC.setSelection(false);
				lblAnswer.setText("");
				index = index + 1;
				if(index < questions.size()) {
					text.setText(questions.get(index).getQuestion());
					ATxt.setText(questions.get(index).getA());
					BTxt.setText(questions.get(index).getB());
					CTxt.setText(questions.get(index).getC());
					DTxt.setText(questions.get(index).getD());
					lblUpdate.setText("Update #" + index);
					lblQuestion.setText("Question " + (index+1));
					countdown(display, runnable, true);
				} else {
					answerComposite.dispose();
					shell.setSize(250, 463);
					btnExit.setEnabled(true);
					lblUpdate.setText("Result");
				}
			}
		});
		btnC.setText("C");
		btnC.setBounds(24, 173, 36, 16);
		
		Button btnD = new Button(questionComposite, SWT.RADIO);
		btnD.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				countdown(display, runnable, false);
				Button source=  (Button) e.getSource();
				String ans = source.getText();

				if(ans.compareTo(questions.get(index).getAnswer()) == 0) {
					lblAnswer.setForeground(green);
					lblAnswer.setText("Correct");
				} else {
					lblAnswer.setForeground(red);
					lblAnswer.setText("Not " + ans + ". Answer is " + questions.get(index).getAnswer());
				}
				
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnD.setSelection(false);
				lblAnswer.setText("");
				index = index + 1;
				if(index < questions.size()) {
					text.setText(questions.get(index).getQuestion());
					ATxt.setText(questions.get(index).getA());
					BTxt.setText(questions.get(index).getB());
					CTxt.setText(questions.get(index).getC());
					DTxt.setText(questions.get(index).getD());
					lblUpdate.setText("Update #" + index);
					lblQuestion.setText("Question " + (index+1));
					countdown(display, runnable, true);
				} else {
					answerComposite.dispose();
					shell.setSize(250, 463);
					btnExit.setEnabled(true);
					lblUpdate.setText("Result");
				}
			}
		});
		btnD.setText("D");
		btnD.setBounds(294, 173, 36, 16);

	}
	
	private void countdown(Display display, Runnable runnable, boolean choose) {
		if(choose == true) {
			//Start countdown
			//5s each time
			display.timerExec(5*1000, runnable);
			//System.out.println("Start countdown");
		} else {
			//Stop countdown
			display.timerExec(-1, runnable);
			//System.out.println("Stop countdown");
		}
	}
}
