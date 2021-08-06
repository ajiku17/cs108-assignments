package tetris;

import java.awt.Toolkit;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;

public class JBrainTetris extends JTetris{

	protected Brain brain = new DefaultBrain();
	protected JCheckBox brainOn;
	protected JSlider adversary;
	protected JLabel adText;
	protected Brain.Move bestM;
	protected Random rand = new Random();
	
	JBrainTetris(int pixels) {
		super(pixels); 
	}
	
	private int randInt(int min, int max) {
		return  rand.nextInt((max - min) + 1) + min;
	}
	
	@Override
	public Piece pickNextPiece() {
		boolean alterPick = randInt(1, 99) < adversary.getValue();
		if(alterPick) {
			adText.setText("*Ok*");
			if(pieces.length <= 0) return null;
			Brain.Move worstMove = brain.bestMove(board, pieces[0], board.getHeight() - TOP_SPACE, null);
			for(int i = 1; i < pieces.length; i++) {
				Brain.Move nextMove = brain.bestMove(board, pieces[i], board.getHeight() - TOP_SPACE, null);
				if(nextMove == null)return super.pickNextPiece();
				if(worstMove.score < nextMove.score)
					worstMove = nextMove;
			}
			return worstMove.piece;
		}
		adText.setText("Ok");
		return super.pickNextPiece();  
	}
	
	@Override
	public void addNewPiece() {
		super.addNewPiece();
		board.undo();
		currentY++; // make up for a decrement in super.addNewPiece
		bestM = brain.bestMove(board, currentPiece, board.getHeight() - TOP_SPACE, bestM); 
	}
	
	@Override
	public void tick(int verb) {
		if(!brainOn.isSelected()) {
			super.tick(verb);
			return;
		}
		if(verb == DOWN) {
			if(bestM != null) {
				if(!bestM.piece.equals(currentPiece))
					super.tick(ROTATE);
					
				if(bestM.x > currentX) 
					super.tick(RIGHT);
				else if(bestM.x < currentX)
					super.tick(LEFT);
			}
		}
		super.tick(verb);
		
	}
	
	/*
	 * Overrides createControlPanel to add additional features
	 * @see tetris.JTetris#createControlPanel()
	 */
	@Override
	public JComponent createControlPanel() {
		JComponent superComp = super.createControlPanel();
		brainOn = new JCheckBox("Brain on");
		superComp.add(brainOn);
		JPanel adverPanel = new JPanel();
		adverPanel.setLayout((new BoxLayout(adverPanel, BoxLayout.Y_AXIS)));
		adverPanel.add(new JLabel("Adversary:"));
		adversary = new JSlider(0, 100, 15);
		adText = new JLabel("");
		adverPanel.add(adversary);
		adverPanel.add(adText);
		superComp.add(adverPanel);
		return superComp;
	}
	
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		JBrainTetris tetris = new JBrainTetris(16);
		JFrame frame = JBrainTetris.createFrame(tetris);
		frame.setVisible(true);
	}

}
