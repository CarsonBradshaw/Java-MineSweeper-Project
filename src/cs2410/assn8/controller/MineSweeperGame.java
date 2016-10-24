package cs2410.assn8.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import cs2410.assn8.view.GameCell;
import cs2410.assn8.view.ScoreBoard;

/**
 * @author Carson Version
 * @version 1.0
 * 
 * MineSweeperGame class which adds the GameCells and Scoreboard to make the MineSweeper game GUI; also handles all game logic 
 *
 */
public class MineSweeperGame implements MouseListener, ActionListener {
	/**
	 * gameOver bool to tell when the game is over
	 */
	private boolean gameOver=false;
	/**
	 * entered bool to check to see if you are still in the game area
	 */
	private boolean entered = false;
	/**
	 * clicked bool to tell whether a cell has been clicked on or not
	 */
	private boolean clicked = false;
	/**
	 * newCellTemp temporary GameCell which represents the new cell where the cursor is 
	 */
	private GameCell newCellTemp;
	/**
	 * gameStarted bool to check if the game has started
	 */
	private boolean gameStarted = false;
	/**
	 * frame The frame used to house all GUI components
	 */
	private JFrame frame = new JFrame("Mine Sweeper");
	/**
	 * scoreBoard The scoreboard
	 */
	private ScoreBoard scoreBoard = new ScoreBoard();
	/**
	 * gameArea The area of the JFrame which store the game cells/squares
	 */
	private JPanel gameArea = new JPanel();
	/**
	 * pane JPanel which houses all components (scoreboard and game)
	 */
	private JPanel pane;
	/**
	 * cells 2D array that stores all the cell information
	 */
	private GameCell[][] cells = new GameCell[24][24];
	/**
	 * MineSweeperGame Class which builds minesweeper game and sets up all listeners to handle logic
	 */
	private MineSweeperGame(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = (JPanel)frame.getContentPane();
		scoreBoard.addResetButtonActionListener(this);
		pane.setLayout(new BorderLayout());
		pane.add(scoreBoard, BorderLayout.NORTH);
		
		gameArea.setLayout(new GridLayout(24,24));
		
		for(int r=0; r<24;++r){
			for(int c=0; c<24; ++c){
				cells[r][c]=new GameCell();
				cells[r][c].addMouseListener(this);
				gameArea.add(cells[r][c]);
			}
		}
		
		placeBombs();
		
		gameArea.setPreferredSize(new Dimension(720,720));
		
		pane.add(gameArea);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	/**
	 * resetGame resets the game
	 */
	private void resetGame(){
		//resets the board by reenabling the buttons, setting the text to null, and setting the buttons to the unpressedCell icon
		for(int r=0; r<24;++r){
			for(int c=0; c<24; ++c){
				cells[r][c].setText("");
				cells[r][c].setUnPressedCell();
				cells[r][c].setEnabledTrue();
			}
		}
		//places bombs randomly on the board
		placeBombs();
		scoreBoard.resetScoreBoard();
		gameStarted=false;
		gameOver=false;
	}
	/**
	 * placeBombs places bombs randomly on the gameboard
	 */
	private void placeBombs(){
		//clears all bombs from the board
		for(int r=0; r<24;++r){
			for(int c=0; c<24; ++c){
				cells[r][c].disableBomb();
			}
		}
		//counter for number of bombs
		int i=100;
		int r=0;
		int c=0;
		Random random = new Random();
		//randomly places the bombs in the array of cells
		while(i>0){
			r=random.nextInt(24);
			c=random.nextInt(24);
			if(cells[r][c].hasBomb()==false){
				cells[r][c].setBomb();
				--i;
			}
		}
	}
	/**
	 * hasWon bool method to tell whether or not the user won by checking each cell that does not have a bomb and checks to see if that cell has been pressed or flagged
	 */
	private boolean hasWon(){
		for(int r=0; r<24;++r){
			for(int c=0; c<24; ++c){
				if(cells[r][c].hasBomb() == false && (cells[r][c].getEnabledStatus() || cells[r][c].getIcon() == cells[r][c].flagCell)){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * gameOver Method which handles when the user clicks on a bomb
	 */
	private void gameOver(){
		scoreBoard.stopTimer();
		//if the user loses
		if(!hasWon()){
			scoreBoard.setDeadResetButton();
			for(int r = 0; r<24; ++r){
				for(int c=0;c<24;++c){
					//sets successfully marked bombs to green, unsuccessfull marked cells as yellow, and unmarked cells to red bombs
					if(cells[r][c].hasBomb() && cells[r][c].getIcon()==cells[r][c].flagCell){
						cells[r][c].setGreenBomb();
					}else if(cells[r][c].hasBomb() && cells[r][c].getIcon()!=cells[r][c].flagCell){
						cells[r][c].setRedBomb();
					}else if(cells[r][c].hasBomb()==false && (cells[r][c].getIcon()==cells[r][c].flagCell || cells[r][c].getIcon()==cells[r][c].unpressedQuestion)){
						cells[r][c].setRegularBomb();
					}
				}
			}
			//gives a game over message dialog if the user loses
			JOptionPane.showMessageDialog(null, "Game Over. In order to play again, please close this message and click on the face on the scoreboard.","Game Over",JOptionPane.INFORMATION_MESSAGE);
		}else if(hasWon()){		//if the user wins
			scoreBoard.setWonResetButton();
			for(int r = 0; r<24; ++r){
				for(int c=0;c<24;++c){
					//sets each of the bombs to green if the user wins
					if(cells[r][c].hasBomb()){
						cells[r][c].setGreenBomb();
					}
				}
			}
			//gives a won message dialog if the user wins
			JOptionPane.showMessageDialog(null, "Congrats, you won! You completed this game in " + scoreBoard.timeCounter + " seconds.","You WON!!!",JOptionPane.INFORMATION_MESSAGE);
		}
		gameOver=true;
	}
	/**
	 * cellPressedLogic method which handles all logic when a cell is pressed
	 */
	private void cellPressedLogic(GameCell newCellTemp){
		//if the cell has a bomb the game is over and the function returns
		if(newCellTemp.hasBomb()){
			gameOver();
			return;
		}
		
		int cellRowsIndex=0;
		int cellColsIndex=0;
		
		//stores the index of the cell that was pressed
		for(int r = 0; r<24; ++r){
			for(int c=0;c<24;++c){
				if(cells[r][c]==newCellTemp){
					cellRowsIndex = r;
					cellColsIndex = c;
				}
			}
		}
		//passes the cells row and col index to a recursive function which will solve for the blobs of correct cells
		cellBombProximityChecker(cellRowsIndex, cellColsIndex);
	}
	
	/**
	 * cellBombProximityChecker performs bounds checking and recursively calls itself to solve for the blobs of cells with neighbors and without
	 */
	private void cellBombProximityChecker(int r, int c){
		if(r>=0 && r<24 && c>=0 && c<24){
			//if there are neighboring bombs the cell will be filled with a colored number and return
			if(numBombNeighbors(r, c)>0){
				//sets the color of the text depending on how many bomb neighbors there are
				if(numBombNeighbors(r,c)==1){
					cells[r][c].setForeground(Color.BLUE);
				}else if(numBombNeighbors(r,c)==2){
					cells[r][c].setForeground(Color.GREEN);
				}else if(numBombNeighbors(r,c)==3){
					cells[r][c].setForeground(Color.RED);
				}else if(numBombNeighbors(r,c)==4){
					cells[r][c].setForeground(Color.CYAN);
				}else if(numBombNeighbors(r,c)==5){
					cells[r][c].setForeground(Color.MAGENTA);
				}else if(numBombNeighbors(r,c)==6){
					cells[r][c].setForeground(Color.YELLOW);
				}else if(numBombNeighbors(r,c)==7){
					cells[r][c].setForeground(Color.ORANGE);
				}else if(numBombNeighbors(r,c)==8){
					cells[r][c].setForeground(Color.PINK);
				}
				cells[r][c].setPressedCell();
				cells[r][c].setText(Integer.toString(numBombNeighbors(r, c)));
				cells[r][c].setVerticalTextPosition(SwingConstants.CENTER);
				cells[r][c].setHorizontalTextPosition(SwingConstants.CENTER);
				cells[r][c].setEnabledFalse();
				return;
			}
			if(numBombNeighbors(r, c)==0){			//if the cell doesn't have any bomb neighbors it is pressed and disabled
				cells[r][c].setPressedCell();
				cells[r][c].setEnabledFalse();
			}
			if(r>0 && c>0){		//checks up and to the left of the cell if the original cell does not have any bomb neighbors
				if(cells[r-1][c-1].hasBomb()==false && cells[r-1][c-1].getEnabledStatus()){
					cells[r-1][c-1].setPressedCell();
					cells[r-1][c-1].setEnabledFalse();
					cellBombProximityChecker(r-1, c-1);
				}
			}
			if(r>0){		//checks above the original cell if that original does not have any bomb neighbors
				if(cells[r-1][c].hasBomb()==false && cells[r-1][c].getEnabledStatus()){
					cells[r-1][c].setPressedCell();
					cells[r-1][c].setEnabledFalse();
					cellBombProximityChecker(r-1, c);
				}
			}
			if(r>0 && c<23){		//checks up and to the right if the original cell does not have any bomb neighbors
				if(cells[r-1][c+1].hasBomb()==false && cells[r-1][c+1].getEnabledStatus()){
					cells[r-1][c+1].setPressedCell();
					cells[r-1][c+1].setEnabledFalse();
					cellBombProximityChecker(r-1, c+1);
				}
			}
			if(c<23){		//checks to the right if the original cell does not have any bomb neighbors
				if(cells[r][c+1].hasBomb()==false && cells[r][c+1].getEnabledStatus()){
					cells[r][c+1].setPressedCell();
					cells[r][c+1].setEnabledFalse();
					cellBombProximityChecker(r, c+1);
				}
			}
			if(r<23 && c<23){		//checks down and to the right if the original cell does not have any bomb neighbors
				if(cells[r+1][c+1].hasBomb()==false && cells[r+1][c+1].getEnabledStatus()){
					cells[r+1][c+1].setPressedCell();
					cells[r+1][c+1].setEnabledFalse();
					cellBombProximityChecker(r+1, c+1);
				}
			}
			if(r<23){		//checks directly below the original cell if that original does not have any bomb neighbors
				if(cells[r+1][c].hasBomb()==false && cells[r+1][c].getEnabledStatus()){
					cells[r+1][c].setPressedCell();
					cells[r+1][c].setEnabledFalse();
					cellBombProximityChecker(r+1, c);
				}
			}
			if(r<23 && c>0){		//checks down and to the left if the original cell does not have any bomb neighbors
				if(cells[r+1][c-1].hasBomb()==false && cells[r+1][c-1].getEnabledStatus()){
					cells[r+1][c-1].setPressedCell();
					cells[r+1][c-1].setEnabledFalse();
					cellBombProximityChecker(r+1, c-1);
				}
			}
			if(c>0){		//checks directly to the left of the cell if that cell does not have any bomb neighbors
				if(cells[r][c-1].hasBomb()==false && cells[r][c-1].getEnabledStatus()){
					cells[r][c-1].setPressedCell();
					cells[r][c-1].setEnabledFalse();
					cellBombProximityChecker(r, c-1);
				}
			}
		}
	}
	/**
	 * numBombNeighbors method that returns how many bombs are surrounding the cell
	 */
	private int numBombNeighbors(int r, int c){
		int numBombs=0;
		//checks all eight directions around the original cell to determine whether or not it has a bomb; also performs bounds checking
		if(r>=0 && r<24 && c>=0 && c<24){
			if(r>0 && c>0){
				if(cells[r-1][c-1].hasBomb()==true){
					++numBombs;
				}
			}
			if(r>0){
				if(cells[r-1][c].hasBomb()==true){
					++numBombs;
				}
			}
			if(r>0 && c<23){
				if(cells[r-1][c+1].hasBomb()==true){
					++numBombs;
				}
			}
			if(c<23){
				if(cells[r][c+1].hasBomb()==true){
					++numBombs;
				}
			}
			if(r<23 && c<23){
				if(cells[r+1][c+1].hasBomb()==true){
					++numBombs;
				}
			}
			if(r<23){
				if(cells[r+1][c].hasBomb()==true){
					++numBombs;
				}
			}
			if(r<23 && c>0){
				if(cells[r+1][c-1].hasBomb()==true){
					++numBombs;
				}
			}
			if(c>0){
				if(cells[r][c-1].hasBomb()==true){
					++numBombs;
				}
			}
		}
		return numBombs;
	}
	

	/**
	 * @param args
	 * 
	 * creates main game by running an instance of MineSweeperGame
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new MineSweeperGame();				
			}});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() instanceof JButton){
			//calls reset function to reset the game
			resetGame();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		entered=true;
		newCellTemp = (GameCell) e.getSource();
		if(newCellTemp.getEnabledStatus() && clicked && newCellTemp.getIcon() != newCellTemp.unpressedQuestion && !gameOver){	
			newCellTemp.setPressedCell();
		}
		else if(newCellTemp.getEnabledStatus() && clicked && newCellTemp.getIcon() == newCellTemp.unpressedQuestion && !gameOver){
			newCellTemp.setPressedQuestion();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		entered=false;
		if(newCellTemp.getEnabledStatus() && !gameOver){
			if(newCellTemp.getIcon() == newCellTemp.pressedQuestion || newCellTemp.getIcon() == newCellTemp.unpressedQuestion){
				newCellTemp.setUnPressedQuestion();
			}else{
				newCellTemp.setUnPressedCell();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(e) && !gameOver){
			scoreBoard.setPressedResetButton();
			clicked = true;
			if(newCellTemp.getEnabledStatus()){
				if(newCellTemp.getIcon() == newCellTemp.unpressedQuestion){
					newCellTemp.setPressedQuestion();
				}else{
					newCellTemp.setPressedCell();
				}
			}
		}else if((newCellTemp.getEnabledStatus() || newCellTemp.getIcon() == newCellTemp.flagCell) && !gameOver){
			if(newCellTemp.getIcon() == newCellTemp.unpressedQuestion){
				newCellTemp.setUnPressedCell();
			}else if(newCellTemp.getIcon() == newCellTemp.flagCell){
				newCellTemp.setUnPressedQuestion();
				newCellTemp.setEnabledTrue();
				scoreBoard.incNumberBombs();
			}else{
				newCellTemp.setFlag();
				newCellTemp.setEnabledFalse();
				scoreBoard.decNumberBombs();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(e)&&!gameOver){
			scoreBoard.setDefaultResetButton();
			if(newCellTemp.getEnabledStatus() && clicked && newCellTemp.hasBomb()==false && gameStarted && entered){
				cellPressedLogic(newCellTemp);
				//code to cheat and win on the second click if that click is not a bomb
//			for(int r = 0; r<24; ++r){
//				for(int c=0;c<24;++c){
//					if(!cells[r][c].hasBomb()){
//						cellBombProximityChecker(r, c);
//					}
//				}
//			}
			//end of cheat code
			}else if(newCellTemp.getEnabledStatus() && clicked && newCellTemp.hasBomb()==true && gameStarted &&entered){
				gameOver();
			}else if(clicked && gameStarted==false && entered){
				while(newCellTemp.hasBomb()){
					placeBombs();
				}
				scoreBoard.startTimer();
				gameStarted=true;
				cellPressedLogic(newCellTemp);
			}
	 
			clicked=false;
			if(hasWon()){
				gameOver();
			}
		}
	}
}

