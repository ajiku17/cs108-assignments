package assign3;

import javax.swing.table.AbstractTableModel;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.List.*;

public class MetropolisesModel extends AbstractTableModel{

	Connection conn;
	private int rowCount;
	private int columnCount;
	private static final String TABLE_NAME = "metropolises";
	
	private ArrayList<ArrayList<Object>> data;
	
	/**
	 * Model constructor which establishes a connection to a database specified in the 
	 * MyDBInfo.java file
	 * @throws SQLException
	 */
	public MetropolisesModel() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + 
							MyDBInfo.MYSQL_DATABASE_NAME, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
		}catch(Exception e) {
			System.out.println("couldnt connect to the database");
			System.exit(-1);
		}		
		rowCount = 0;
		columnCount = 0;

	}
	
	/**
	 * Returns the number of rows in the model. 
	 * A JTable uses this method to determine how many rows it should display. 
	 * This method should be quick, as it is called frequently during rendering.
	 * @return the number of rows in the model
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowCount;
	}

	/**
	 * Returns the number of columns in the model.
	 * A JTable uses this method to determine how many
	 * columns it should create and display by default.
	 * @return the number of columns in the model
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnCount;
	}

	/**
	 * Returns the value for the cell at columnIndex and rowIndex.
	 * @param rowIndex - the row whose value is to be queried
	 * @param columnIndex - the column whose value is to be queried
	 * @return the value Object at the specified cell
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(data == null)return null;
		return data.get(rowIndex).get(columnIndex);				
	}
	
	/**
	 * Queries the current database using passed string parameters, ignoring every
	 * empty string parameter.
	 * boolean values indicate the type of the search.
	 * @param metropolis
	 * @param continent
	 * @param population
	 * @param greaterThan
	 * @param exactSearch
	 * @throws SQLException
	 */
	public void query(String metropolis, String continent, String population,
								boolean greaterThan, boolean exactSearch) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from " + TABLE_NAME + " " +  
						whereClause(metropolis, continent, population, greaterThan, exactSearch) + ";");
		data = new ArrayList<ArrayList<Object>>();
		rowCount = 0;
		columnCount = rs.getMetaData().getColumnCount(); 
		while(rs.next()) {
			ArrayList<Object> row = new ArrayList<Object>();
			for(int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				row.add(rs.getObject(i + 1));
			}
			data.add(row);
			rowCount++;
		}
		st.close();
		rs.close();
		fireTableStructureChanged();
	}
	
	/**
	 * Returns a string representing the WHERE clause for the MySQL query
	 * specified by the parameters 
	 * @param metropolis
	 * @param continent
	 * @param population
	 * @param greaterThan
	 * @param exactMatch
	 * @return a string representing the where clause for an SQL script
	 */
	private String whereClause(String metropolis, String continent, String population, boolean greaterThan, boolean exactMatch) {
		if(metropolis.equals("") && continent.equals("") && population.equals("")) return "";
		String res = " where";
		if(!metropolis.equals("")) {
			if(exactMatch) 
				res += " metropolis = " + "\"" + metropolis + "\"";
			else 
				res += " metropolis like " + "\"%" + metropolis + "%\"";
		}
		if(!continent.equals("")) {
			if(!res.equals(" where")) res += " and";
			if(exactMatch) 
				res += " continent = " + "\"" + continent + "\"";
			else 
				res += " continent like " + "\"%" + continent + "%\"";
		}
		if(!population.equals("")) {
			if(!res.equals(" where")) res += " and";
			if(greaterThan)
				res += " population > " + "\"" + population + "\"";
			else
				res += " population <= " + "\"" + population + "\"";
		}
		
		return res;
	}
	
	/**
	 * Adds a new entry in the metropolises table
	 * @param metropolis
	 * @param continent
	 * @param population
	 * @throws SQLException
	 */
	public void addEntry(String metropolis, String continent, String population) throws SQLException {
		Statement st = conn.createStatement();
		st.executeUpdate("insert into " + TABLE_NAME + 
					" values(\"" + metropolis + "\", \"" + continent + "\", " + Long.parseLong(population) + ");");
		st.close();
		query(metropolis, continent, population, false, true);
	}
	

}
