package webloader;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WebWorker extends Thread {

	private int rowNumber;
	private WebFrame frame;
	private Semaphore isDone; //notifies that this thread is done
	private String urlString;
	
	public WebWorker(String urlString, int rowNumber, WebFrame frame, Semaphore isDone ) {
		this.rowNumber = rowNumber;
		this.urlString = urlString;
		this.frame = frame;
		this.isDone = isDone;
	}
	
	
	//Downloads the data and handles the exceptions accordingly
	@Override
	public void run() {
//  This is the core web/download i/o code...
 		InputStream input = null;
		StringBuilder contents = null;
		
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
		
			// Set connect() to throw an IOException
			// if connection does not succeed in this many msecs.
			connection.setConnectTimeout(5000);
			
			connection.connect();
			input = connection.getInputStream();

			BufferedReader reader  = new BufferedReader(new InputStreamReader(input));
			
			char[] array = new char[1000];
			
			long startingTime = System.currentTimeMillis();
			
			int len;
			contents = new StringBuilder(1000);
			while ((len = reader.read(array, 0, array.length)) > 0) {
				contents.append(array, 0, len);
				Thread.sleep(300);
				if(this.isInterrupted()) {
					this.interrupt();
				}
			}
			int finalsize = contents.length();			
			// Successful download if we get here
			Date downloadDate = new Date();
			long downloadTime = System.currentTimeMillis() - startingTime;
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); 
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					frame.setStatus(rowNumber, dateFormat.format(downloadDate) + " " + downloadTime + "ms " + finalsize + " bytes");
				}
				
			});
		}
		// Otherwise control jumps to a catch...
		catch(MalformedURLException ignored) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setStatus(rowNumber, "err");
				}
			});
		}
		catch(InterruptedException exception) {
			// YOUR CODE HERE
			// deal with interruption
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setStatus(rowNumber, "interrupted");
				}
			});
		}
		catch(IOException ignored) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setStatus(rowNumber, "err");
				}
			});
		}
		// "finally" clause, to close the input stream
		// in any case
		finally {
			isDone.release();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.incrementCompleted();
					frame.decrementRunning();
				}
			});
			try{
				if (input != null) input.close();
			}
			catch(IOException ignored) {}
		}

	}
}
