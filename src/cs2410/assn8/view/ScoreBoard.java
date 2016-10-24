package cs2410.assn8.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Carson Bradshaw
 *@version 1.0
 *
 *ScoreBoard class that builds the gui componenets for the scoreboard and stores and displays data for score and timer
 */
public class ScoreBoard extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -647050149860732115L;
	/**
	 * timer Timer stores the time elapsed.
	 */
	private Timer timer = new Timer(1000,this);
	/**
	 * timeCounter counter to keep track of time
	 */
	public int timeCounter = 0;
	/**
	 * numberBombs stores the number of bombs left
	 */
	private int numberBombs = 100;
	/**
	 * timerLabel is a label that stores the timer
	 */
	private JLabel timerLabel = new JLabel();
	/**
	 * bombsLabel is a label that stores the number of bombs
	 */
	private JLabel bombsLabel = new JLabel();
	/**
	 * resestDefaultImage Default smiley face icon for the resetButton
	 */
	private ImageIcon defaultResetButtonImage = new ImageIcon(new ImageIcon("src/images/defaultResetButtonImage.gif").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * pressedResetButtonImage o-face smiley image for use for the resetbutton when a square is pressed 
	 */
	private ImageIcon pressedResetButtonImage = new ImageIcon(new ImageIcon("src/images/pressedResetButtonImage.PNG").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * deadResetButtonImage dead face image for use for the resetbutton when a bomb is clicked on
	 */
	private ImageIcon deadResetButtonImage = new ImageIcon(new ImageIcon("src/images/deadResetButtonImage.PNG").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * wonResetButtonImage glasses cool face image for use for the resetButton when the user wins
	 */
	private ImageIcon wonResetButtonImage = new ImageIcon(new ImageIcon("src/images/wonResetButtonImage.PNG").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * resetBtn JButton which tells the program when to reset
	 */
	private JButton resetBtn = new JButton(defaultResetButtonImage);
	/**
	 * ScoreBoard() default constructor which sets all of the scoreboard GUI components
	 */
	public ScoreBoard(){
		this.setLayout(new FlowLayout());
		bombsLabel.setHorizontalAlignment(JLabel.CENTER);
		bombsLabel.setPreferredSize(new Dimension(250,30));
		bombsLabel.setFont(new Font("Comic Sans MS", Font.BOLD,18));
		updateBombView();
		this.add(bombsLabel);
		resetBtn.setHorizontalAlignment(JLabel.CENTER);
		resetBtn.setPreferredSize(new Dimension(30,30));
		resetBtn.setRolloverEnabled(false);
		setDefaultResetButton();
		this.add(resetBtn);
		timerLabel.setHorizontalAlignment(JLabel.CENTER);
		timerLabel.setPreferredSize(new Dimension(250,30));
		timerLabel.setFont(new Font("Comic Sans MS", Font.BOLD,18));
		updateTimeView();
		this.add(timerLabel);
		this.update(this.getGraphics());
	}
	/**
	 * decNumberBombs() decrements the number of bombs
	 */
	public void decNumberBombs(){
		--numberBombs;
		updateBombView();
	}
	/**
	 * incNumberBombs() increments the number of bombs
	 */
	public void incNumberBombs(){
		++numberBombs;
		updateBombView();
	}
	/**
	 * updateBombView Method which updates the view of the scoreBoard
	 */
	public void updateBombView(){
		bombsLabel.setText("Bombs Left: " + Integer.toString(numberBombs));
		bombsLabel.update(bombsLabel.getGraphics());
	}
	/**
	 * startTimer starts the timer
	 */
	public void startTimer(){
		timer.start();
	}
	/**
	 * stopTimer stops the timer
	 */
	public void stopTimer(){
		timer.stop();
	}
	/**
	 * resetTimer resets the timer
	 */
	public void resetTimer(){
		timeCounter = 0;
		if(timer.isRunning()){
			timer.stop();
		}
	}
	/**
	 * resetScoreBoard resets the entire scoreBoard
	 */
	public void resetScoreBoard(){
		numberBombs=100;
		resetTimer();
		updateTimeView();
		updateBombView();
		setDefaultResetButton();
	}
	/**
	 * updateTimeView updates the time portion of the scoreboard
	 */
	public void updateTimeView(){
		timerLabel.setText("Time Elapsed: " + timeCounter);
		timerLabel.update(timerLabel.getGraphics());
	}
	/**
	 * setDefaultResetButton sets the icon of the reset button equal to the default image
	 */
	public void setDefaultResetButton(){
		resetBtn.setIcon(defaultResetButtonImage);
		resetBtn.update(resetBtn.getGraphics());
	}
	/**
	 *setPressedResetButton sets the icon of the reset button equal to the pressed image 
	 */
	public void setPressedResetButton(){
		resetBtn.setIcon(pressedResetButtonImage);
		resetBtn.update(resetBtn.getGraphics());
	}
	/**
	 * setDeadResetButton sets the icon of the reset button equal to the dead image
	 */
	public void setDeadResetButton(){
		resetBtn.setIcon(deadResetButtonImage);
		resetBtn.update(resetBtn.getGraphics());
	}
	/**
	 * setWonResetButton() sets the icon of the reset button equal to the won image
	 */
	public void setWonResetButton(){
		resetBtn.setIcon(wonResetButtonImage);
		resetBtn.update(resetBtn.getGraphics());
	}
	/**
	 * addResetButtonActionListener adds an action listener to the reset button
	 */
	public void addResetButtonActionListener(ActionListener listener){
		resetBtn.addActionListener(listener);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == timer){
			++timeCounter;
			updateTimeView();
		}
	}
	
}
