package count;

import javax.swing.JLabel;
import java.awt.event.*;

public class Counter implements Runnable{

	private int target;
	private JLabel currentLabel;
	private static final int INTERVAL = 10000;
	
	public Counter(int target, JLabel currentLabel) {
		this.target = target;
		this.currentLabel = currentLabel;
	}
	
	//Counts and updates the label after a given interval 
	@Override
	public void run(){
		try {
			int update = INTERVAL;
			for(int i = 1; i <= target; i++) {
				if(Thread.currentThread().isInterrupted()) {
					throw new InterruptedException();
				}
				update--;
				if(update == 0) {
					currentLabel.setText(i + "");
					update = 10000;
					Thread.sleep(100);
				}
			}
		}catch(InterruptedException e) {
			currentLabel.setText("0");
		}
	}
	
}
