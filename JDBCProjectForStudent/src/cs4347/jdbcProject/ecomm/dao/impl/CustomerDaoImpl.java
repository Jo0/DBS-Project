package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerDaoImpl implements CustomerDAO
{
	
	private static final String insertSQL = 
			"INSERT INTO CUSTOMER (id, firstName, lastName, gender, dob, email) VALUES (?, ?, ?, ?, ?, ?);";
	
	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException
	{
        // Requirement: Create operations require that the customer's ID is null 
        // before being inserted into the table.
		if (customer.getId() != null) {
			throw new DAOException("Trying to insert Customer with NON-NULL ID");
		}

		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, 0);
			ps.setString(2, customer.getFirstName());
			ps.setString(3, customer.getLastName());
			ps.setString(4, Character.toString(customer.getGender()));
			ps.setDate(5, customer.getDob());
			ps.setString(6, customer.getEmail());
			ps.executeUpdate();

			// REQUIREMENT: Copy the generated auto-increment primary key to the customer ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			if(keyRS.next())
			{
			int lastKey = keyRS.getInt(1);
			customer.setId((long) lastKey);
			}
			return customer;

	}

	private static final String selectQuery = 
			"SELECT id, firstName, lastname, gender, dob, email FROM customer where id = ?";
	@Override
	public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException
	{
		if (id == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}
	
		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				return null;
			}
			
			Customer cust = new Customer();
			cust.setId(rs.getLong("id"));
			cust.setFirstName(rs.getString("firstname"));
			cust.setLastName(rs.getString("lastname"));
			cust.setGender(rs.getString("gender").charAt(0));
			cust.setDob(rs.getDate("dob"));
			cust.setEmail(rs.getString("email"));
			return cust;
	
	}

	private static final String updateSQL = 
			"UPDATE customer SET firstName = ?, lastname = ?, gender = ?, dob = ?, email = ? WHERE id = ?;";
	@Override
	public int update(Connection connection, Customer customer) throws SQLException, DAOException
	{
		Long id = customer.getId();
		if (id == null) {
			throw new DAOException("Trying to update Customer with NULL ID");
		}

		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, String.valueOf(customer.getGender()));
			ps.setDate(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			ps.setLong(6, id);

			int rows = ps.executeUpdate();
			return rows;

	}

	private static final String deleteSQL = 
		    "DELETE FROM CUSTOMER WHERE ID = ?;";
	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException 
	{
		if (id == null) {
			throw new DAOException("Trying to delete Customer with NULL ID");
		}

		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rows = ps.executeUpdate();
			return rows;
		
	}

	private static final String zipQuery = 
			"SELECT id, firstName, lastName, gender, dob, email FROM customer INNER JOIN address ON customer.ID=address.customerID where address.zipcode = ?";
	@Override
	public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
		if (zipCode == null) {
			throw new DAOException("Trying to retrieve Customer with NULL zipCode");
		}

		List<Customer> result = new ArrayList<Customer>();
		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(zipQuery);
			ps.setString(1, zipCode);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			
				Customer cust = new Customer();
				cust.setId(rs.getLong("id"));
				cust.setFirstName(rs.getString("firstname"));
				cust.setLastName(rs.getString("lastname"));
				cust.setGender(rs.getString("gender").charAt(0));
				cust.setDob(rs.getDate("dob"));
				cust.setEmail(rs.getString("email"));
				result.add(cust);
			}
			return result;
		
	}

	private static final String dobQuery = 
			"SELECT id, firstName, lastName, gender, dob, email FROM customer WHERE dob between ? and ?";
	@Override
	public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate) throws SQLException, DAOException 
	{
		if (startDate == null || endDate == null) {
			throw new DAOException("Trying to retrieve Customer with NULL dob");
		}

		List<Customer> result = new ArrayList<Customer>();
	
		PreparedStatement ps = null;
		
		
			ps = connection.prepareStatement(dobQuery);
			ps.setDate(1, startDate);
			ps.setDate(2, endDate);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			
				Customer cust = new Customer();
				cust.setId(rs.getLong("id"));
				cust.setFirstName(rs.getString("firstname"));
				cust.setLastName(rs.getString("lastname"));
				cust.setGender(rs.getString("gender").charAt(0));
				cust.setDob(rs.getDate("dob"));
				cust.setEmail(rs.getString("email"));
				result.add(cust);
			}
			return result;
		
	}

}
