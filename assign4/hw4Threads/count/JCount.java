package count;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JCount extends JPanel{
	
	JTextField target;
	JLabel currentCount;
	JButton startButton;
	JButton stopButton;
	Thread counterThread;
	
	
	
	public JCount() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		target = new JTextField();
		currentCount = new JLabel("0");
		add(target);
		add(currentCount);
		addControlPanel();
		add(Box.createRigidArea(new Dimension(0, 40)));
	}
	
	//Adds the user interface and sets corresponding ActionListeners
	private void addControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));		
		startButton = new JButton("START");
		stopButton = new JButton("STOP");
		controlPanel.add(startButton);
		controlPanel.add(stopButton);
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentCount.setText("0");
				if(counterThread != null)counterThread.interrupt();
				counterThread =  new Thread(
							new Counter("".equals(target.getText()) ? 0 : Integer.valueOf(target.getText()), currentCount));
				counterThread.start();
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				counterThread.interrupt();
			}
		});
		add(controlPanel);
	}
}
