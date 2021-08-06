package assign3;

import java.sql.SQLException;

import javax.swing.UIManager;

public class MetropolisesApp {

	public static void main(String[] args) throws SQLException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e) {
			
		}
		
		MetropolisesFrame frame = new MetropolisesFrame();
		frame.setVisible(true);
	}
	
}
