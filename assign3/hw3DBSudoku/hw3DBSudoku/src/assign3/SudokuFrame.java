package assign3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


 public class SudokuFrame extends JFrame {

	JTextArea puzzleArea;
	JTextArea solutionArea;
	JButton checkButton;
	JCheckBox autoCheck;
	Sudoku aSudoku;
	 
	public SudokuFrame() {
		super("Sudoku Solver");
		// Could do this:
		setLocationByPlatform(true);
		setPreferredSize(new Dimension(500, 400));
		setLayout(new BorderLayout(4,4));
		
		addTextAreas();
		addControlPanel();

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private void addTextAreas() {
		puzzleArea = new JTextArea(15, 20);
		puzzleArea.getDocument().addDocumentListener(new SudokuTextFieldListener());
		solutionArea = new JTextArea(15, 20);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		
		JScrollPane puzzleScroll = new JScrollPane(puzzleArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane solutionScroll = new JScrollPane(solutionArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		solutionArea.setLineWrap(true);
		solutionArea.setWrapStyleWord(true);
		
		puzzleArea.setLineWrap(true);
		puzzleArea.setWrapStyleWord(true);
		
		puzzleArea.setBorder(new TitledBorder("Puzzle"));
		solutionArea.setBorder(new TitledBorder("Solution"));
		
		centerPanel.add(puzzleScroll);
		centerPanel.add(solutionScroll);
		
		add(centerPanel, BorderLayout.CENTER);
	}
	
	
	private void addControlPanel() {
		checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAndSolve(true);
			}
		});
		autoCheck = new JCheckBox("Auto Check", true);
		autoCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createAndSolve(false);
			}
			
		});
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		southPanel.add(checkButton);
		southPanel.add(autoCheck);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}
	
	private void createAndSolve(boolean shouldAssert) {
		try {
			aSudoku = new Sudoku(Sudoku.textToGrid(puzzleArea.getText()));
			int count = aSudoku.solve();
			solutionArea.setText(aSudoku.getSolutionText() + "\n" + "solutions:" + count + 
												"\n" + "elapsed:" + aSudoku.getElapsed() + "ms");
		}catch (Exception ex) { 
			if(shouldAssert) {
				throw new RuntimeException("Parsing problem");
			}
		}
	}
	
	class SudokuTextFieldListener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			if(autoCheck.isSelected()) {
				createAndSolve(false);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if(autoCheck.isSelected()) {
				createAndSolve(false);
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
		}
	}

}
