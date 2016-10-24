package cs2410.assn8.view;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Carson Bradshaw
 *@version 1.0
 *
 *GameCell Class that builds each individual cell in the minesweeper game. Stores all appropriate data for each cell.
 */
public class GameCell extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * enabled bool to check if the current cell has been pressed
	 */
	private boolean enabled = true;
	/**
	 * regularBomb icon for regular bomb
	 */
	private ImageIcon regularBomb = new ImageIcon(new ImageIcon("src\\images\\bombNormal.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * redBomb icon for red bomb
	 */
	private ImageIcon redBomb = new ImageIcon(new ImageIcon("src\\images\\bombRed.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * greenBomb icon for green bomb
	 */
	private ImageIcon greenBomb = new ImageIcon(new ImageIcon("src\\images\\greenBombCell.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * pressedCell icon for pressed cell
	 */
	private ImageIcon pressedCell = new ImageIcon(new ImageIcon("src\\images\\emptyCell.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * unpressedCell icon for unpressed Cell
	 */
	private ImageIcon unpressedCell = new ImageIcon(new ImageIcon("src\\images\\unpressedCell.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * flagCell icon for regular bomb
	 */
	public ImageIcon flagCell = new ImageIcon(new ImageIcon("src\\images\\flagCell.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * unpressedQuestion icon for unpressed questions cell 
	 */
	public ImageIcon unpressedQuestion = new ImageIcon(new ImageIcon("src\\images\\questionCell.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 * pressedQuestion icon for pressed question cells
	 */
	public ImageIcon pressedQuestion = new ImageIcon(new ImageIcon("src\\images\\pressedQuestionMark.png").getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	/**
	 *GameCell Constructor which creates all of the gui components and adds appropriate listeners
	 */
	public GameCell(){ 
		this.setIcon(unpressedCell);
		this.setFont(new Font("Comic Sans MS", Font.BOLD,22));
	}
	/**
	 * setRegularBomb sets the cells image to the regularBomb image
	 */
	public void setRegularBomb(){
		this.setIcon(regularBomb);
	}
	/**
	 * setRedBombImage sets the cells icon to the redRomb
	 */
	public void setRedBomb(){
		this.setIcon(redBomb);
	}
	/**
	 * setGreenBomb sets the cells icon to the greenBomb
	 */
	public void setGreenBomb(){
		this.setIcon(greenBomb);
	}
	/**
	 * setPressedCell sets the cells icon to the pressedCell image
	 */
	public void setPressedCell(){
		this.setIcon(pressedCell);
	}
	/**
	 * setUnPressedCell sets the cells icon to the unpressedCell Image
	 */
	public void setUnPressedCell(){
		this.setIcon(unpressedCell);
	}
	/**
	 * setFlag sets the cells icon to the unpressedFlage image
	 */
	public void setFlag(){
		this.setIcon(flagCell);
	}
	/**
	 * setUnPressedQuestion sets the cells icon the unpressedquestion mark image
	 */
	public void setUnPressedQuestion(){
		this.setIcon(unpressedQuestion);
	}
	/**
	 * setPressedQuestion sets the cells icon to the pressedQuestionMark
	 */
	public void setPressedQuestion(){
		this.setIcon(pressedQuestion);
	}
	/**
	 * bomb bool that keeps track of whether or not a cell has a bomb
	 */
	private boolean bomb=false;
	/**
	 * setBomb method that sets the cell to have a bomb
	 */
	public void setBomb(){
		this.bomb=true;
	}
	/**
	 * disableBomb sets bomb equal to false
	 */
	public void disableBomb(){
		this.bomb=false;
	}
	/**
	 * hasBomb method that returns whether or not a cell has a bomb
	 */
	public boolean hasBomb(){
		return bomb;
	}
	/**
	 * setEnabled sets the bool enabled to true
	 */
	public void setEnabledTrue(){
		this.enabled=true;
	}
	/**
	 * setEnabledFalse disables the JLabel
	 */
	public void setEnabledFalse(){
		this.enabled=false;
	}
	/**
	 * getEnabledStatus returns the enabled bool
	 */
	public boolean getEnabledStatus(){
		return enabled;
	}

}