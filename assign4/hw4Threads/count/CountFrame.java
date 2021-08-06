package count;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class CountFrame{

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			private static final int NUM_COUNTERS = 4;
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame mainFrame = new JFrame("Count");
				mainFrame.setPreferredSize(new Dimension(400, 600));
				JComponent contentPane = (JComponent)mainFrame.getContentPane();
				contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
				for(int i = 0; i < NUM_COUNTERS; i++) {
					JCount counter = new JCount();
					contentPane.add(counter);
				}
				
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setVisible(true);
				mainFrame.pack();
			}
			
		});
	}
	
}
