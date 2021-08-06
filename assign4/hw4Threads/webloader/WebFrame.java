package webloader;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import javafx.print.PrinterJob.JobStatus;

public class WebFrame extends JFrame{

	private static final String URL_FILENAME = "HW4 Starter Code/webloader/links.txt";
	
	private JButton singleFetch;
	private JButton concurrentFetch;
	private JTextField threadLimit;
	private JLabel runningLabel;
	private JLabel completedLabel;
	private JLabel elapsedLabel;
	private JProgressBar progressBar;
	private JButton stop;
	
	
	private DefaultTableModel urlTableModel;
	private JTable urlTable;
	
	private boolean isRunning = false;
	private int runningThreads = 0;
	private int completedDownloads = 0;
	private int urlCount = 0;
	
	private Thread launcher;
	
	public WebFrame() {
		super("Web Loader");
		setPreferredSize(new Dimension(600, 600));
		JComponent content = (JComponent)getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		addURLTable(content);
		
		addFetchButton(content);
		
		addLimitField(content);
		
		addStatusLabels(content);
		
		addProgressBar(content);
		
		addStopButton(content);
		
		readData();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		this.setVisible(true);
	}

	//Set the running state
	private void setRunning(boolean state) {
		isRunning = state;
		/*
		 * Set buttons to be enabled accordingly
		 */
		singleFetch.setEnabled(!state);
		concurrentFetch.setEnabled(!state);
		stop.setEnabled(state);
	}
	
	private void readData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(URL_FILENAME));
			String line;
			while((line = reader.readLine()) != null) {
				urlTableModel.addRow(new String[] {line, ""});
				urlCount++;
			}
			reader.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Adder methods to create a user interface
	 */
	private void addLimitField(JComponent content) {
		threadLimit = new JTextField();
		threadLimit.setMaximumSize(new Dimension(60, 30));
		content.add(threadLimit);
	}
	
	private void addStopButton(JComponent content) {
		stop = new JButton("Stop");
		stop.addActionListener(new StopAction());
		stop.setEnabled(false);
		content.add(stop);
	}
	
	private void addProgressBar(JComponent content) {
		progressBar = new JProgressBar(0, 16);
		content.add(progressBar);
	}
	
	private void addStatusLabels(JComponent content) {
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		
		runningLabel = new JLabel("Running: 0");
		completedLabel = new JLabel("Completed: 0");
		elapsedLabel = new JLabel("Elapsed");
		
		labelPanel.add(runningLabel);
		labelPanel.add(completedLabel);
		labelPanel.add(elapsedLabel);
		
		content.add(labelPanel);
	}
	
	
	private void addFetchButton(JComponent content) {
		JPanel fetchPanel = new JPanel();
		fetchPanel.setLayout(new BoxLayout(fetchPanel, BoxLayout.Y_AXIS));
		singleFetch = new JButton("Single Fetch");
		concurrentFetch = new JButton("Concurrent Fetch");

		singleFetch.addActionListener(new SingleFetchAction());
		concurrentFetch.addActionListener(new ConcurrentFetchAction());

		singleFetch.setEnabled(true);
		concurrentFetch.setEnabled(true);
		
		fetchPanel.add(singleFetch);
		fetchPanel.add(concurrentFetch);
		content.add(fetchPanel);
	}
	
	private void addURLTable(JComponent content) {		
		urlTableModel = new DefaultTableModel(new String[] {"url","status"},  0);
		urlTable = new JTable(urlTableModel);
		JScrollPane tableScroll = new JScrollPane(urlTable);
		
		content.add(tableScroll);
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}catch(Exception e) {}
				WebFrame frame = new WebFrame();
			}
		});
	}
	



	
	/*
	 * Setter methods to drive the GUI 
	 */
	//Clears the statuses of URLs in the table 
	private void resetStatuses() {
		for(int i = 0; i < urlTableModel.getRowCount(); i++) {
			urlTableModel.setValueAt("", i, 1);
		}
	}
	
	public void incrementCompleted() {
		completedDownloads++;
		completedLabel.setText("Completed: " + completedDownloads);
		progressBar.setValue(completedDownloads);
	}
	
	public void decrementRunning() {
		runningThreads--;
		runningLabel.setText("Running: " + runningThreads);
	}
	
	public void incrementRunning() {
		runningThreads++;
		runningLabel.setText("Running: " + runningThreads);
	}
	
	//Sets the statuses for the URLs in the table
	public void setStatus(int row, String status) {
		urlTableModel.setValueAt(status, row, 1); 
	}
	
	public void setElapsed(long elapsedTime) {
		elapsedLabel.setText("Elapsed: " + elapsedTime + " ms");
	}
	
	//Resets the labels and the counters associated with them
	private void resetLabels() {
		runningThreads = 0;
		completedDownloads = 0;
		runningLabel.setText("Running: 0");
		completedLabel.setText("Completed: 0");
		elapsedLabel.setText("Elapsed:");
		progressBar.setValue(completedDownloads);
	}
	
	/*
	 * ActionListener implementations for the control panel buttons
	 */
	
	// Creates and starts the Launcher class with a limit specified in the threadLimit textField
	private class ConcurrentFetchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(!isRunning) {
				setRunning(true);
				resetLabels();
				resetStatuses();
				String numThreads = threadLimit.getText();
				launcher = new Launcher(numThreads.equals("")? 1 : Integer.valueOf(numThreads));
				launcher.start();
			}
		}
	}

	// Creates and starts the Launcher thread with a limit of 1
	private class SingleFetchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(!isRunning) {
				setRunning(true);
				resetLabels();
				resetStatuses();
				launcher = new Launcher(1);
				launcher.start();
			}
		}
	}
	
	// Interrupts the Launcher thread
	private class StopAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(isRunning) {
				setRunning(false);
				launcher.interrupt();
			}
		}
	}
	
	
	/*
	 * Main launcher thread which creates and starts all the other workers and
	 * coordinates the workflow between them.
	 * When interrupted, notifies all of its child threads to terminate using InterrutedException
	 */
	private class Launcher extends Thread{
		
		private int permits;
		private Semaphore canLaunch;
		
		public Launcher(int permits) {
			this.permits = permits;
			canLaunch = new Semaphore(permits);
		}
		
		public void run() {
			List<Thread> launchedThreads = new ArrayList<Thread>();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					incrementRunning();
					setRunning(true);
				}
			});
			long startingTime = System.currentTimeMillis();
			for(int i = 0; i < urlCount; i++) {
				try {
					canLaunch.acquire();
					WebWorker worker = new WebWorker((String)urlTableModel.getValueAt(i, 0), i, WebFrame.this, canLaunch);
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							incrementRunning();
						}
					});
					launchedThreads.add(worker);
					worker.start();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					for(int j = 0; j < launchedThreads.size(); j++) {
						launchedThreads.get(j).interrupt();
					}
					break;
				}
			}
			
			try {
				for(int i = 0; i < permits; i++)
					canLaunch.acquire();
			}catch(InterruptedException e) {
				for(int j = 0; j < launchedThreads.size(); j++) {
					launchedThreads.get(j).interrupt();
				}
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					decrementRunning();
					setRunning(false);
					setElapsed(System.currentTimeMillis() - startingTime);
				}
			});
		}
	}
}


