package assign3;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class MetropolisesFrame extends JFrame{

	private static final String POPULATION_LESS_THAN_OR_EQUAL = "Population Less Than Or Equal";
	private static final String POPULATION_GREATER_THAN = "Population Larger Than";
	private static final String PARTIAL_MATCH = "Partial Match";
	private static final String EXACT_MATCH = "Exact Match";
	
	JTextField metropolisField;
	JTextField continentField;
	JTextField populationField;
	JButton addButton;
	JButton searchButton;
	JComboBox<String> populationPulldown;
	JComboBox<String> matchTypePulldown; 
	MetropolisesModel model;
	
	
	public MetropolisesFrame() throws SQLException {
		super("Metropolis Viewer");
		
		model = new MetropolisesModel();
		
		JTable table = new JTable(model);
		
		JComponent content = (JComponent)getContentPane();
		
		addSearchBars(content);
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		addButtons(eastPanel);
		addSearchOptions(eastPanel);
		add(eastPanel, BorderLayout.EAST);
		
		add(table);
		setPreferredSize(new Dimension(800, 600));
		pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void addSearchOptions(JComponent content) {
		JPanel searchOptions = new JPanel();
		searchOptions.setLayout(new BoxLayout(searchOptions, BoxLayout.Y_AXIS));
		
		populationPulldown = new JComboBox<String>();
		matchTypePulldown = new JComboBox<String>();
		
		populationPulldown.addItem(POPULATION_GREATER_THAN);
		populationPulldown.addItem(POPULATION_LESS_THAN_OR_EQUAL);
		matchTypePulldown.addItem(PARTIAL_MATCH);
		matchTypePulldown.addItem(EXACT_MATCH);
		
		searchOptions.add(populationPulldown);
		searchOptions.add(matchTypePulldown);
		
		searchOptions.setBorder(new TitledBorder("Search Options"));
		
		searchOptions.setMaximumSize(new Dimension(600, 100));
		content.add(searchOptions);
	}
	
	private void addButtons(JComponent content) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setMinimumSize(new Dimension(100, 100));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		addButton = new JButton("Add");
		searchButton = new JButton("Search");
		
		addButton.addActionListener(new addAction());
		searchButton.addActionListener(new searchAction());
		
		buttonPanel.add(addButton);
		buttonPanel.add(searchButton);
		
		content.add(buttonPanel);
		
	}
	
	private void addSearchBars(JComponent content) {
		JPanel searchBars = new JPanel();
		GridLayout grid = new GridLayout(1, 3);
		grid.setHgap(20);
		
		JPanel metPanel = new JPanel();
		metPanel.setLayout(new BoxLayout(metPanel, BoxLayout.X_AXIS));
		JPanel conPanel = new JPanel();
		conPanel.setLayout(new BoxLayout(conPanel, BoxLayout.X_AXIS));
		JPanel popPanel = new JPanel();
		popPanel.setLayout(new BoxLayout(popPanel, BoxLayout.X_AXIS));
		
		JLabel metropolisLabel = new JLabel("Metropolis:");
		JLabel continetLabel = new JLabel("Continent:");
		JLabel populationLabel = new JLabel("Population:");
		
		metropolisField = new JTextField(10);
		continentField = new JTextField(10);
		populationField = new JTextField(10);
		
		metPanel.add(metropolisLabel);
		metPanel.add(metropolisField);
		
		conPanel.add(continetLabel);
		conPanel.add(continentField);
		
		popPanel.add(populationLabel);
		popPanel.add(populationField);
		
		searchBars.add(metPanel);
		searchBars.add(conPanel);
		searchBars.add(popPanel);
		
		content.add(searchBars, BorderLayout.NORTH);
	}
	
	
	class addAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(!metropolisField.getText().equals("") && !continentField.getText().equals("") && !populationField.getText().equals("")) {
				try {
					model.addEntry(metropolisField.getText(), continentField.getText(), populationField.getText());
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	class searchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				model.query(metropolisField.getText(), continentField.getText(), populationField.getText(), 
										((String)populationPulldown.getSelectedItem()).equals(POPULATION_GREATER_THAN),
										((String)matchTypePulldown.getSelectedItem()).equals(EXACT_MATCH));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
}
