package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class AddressDaoImpl implements AddressDAO
{

	private static final String insertSQL = 
			"INSERT INTO address (address1, address2, city, state, zipcode, customerID) VALUES (?, ?, ?, ?, ?, ?);";
	@Override
	public Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException {
	  
		PreparedStatement ps = null;
		
		ps = connection.prepareStatement(insertSQL);
		ps.setString(1, address.getAddress1());
		ps.setString(2, address.getAddress2());
		ps.setString(3, address.getCity());
		ps.setString(4, address.getState());
		ps.setString(5, address.getZipcode());
		ps.setLong(6, customerID);
		ps.executeUpdate();

		return address;
	
	}

	private static final String selectQuery = 
			"SELECT address1, address2, city, state, zipcode FROM address where customerID = ?";
	@Override
	public Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
	
		if (customerID == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}
	
		
		PreparedStatement ps = null;
		
		ps = connection.prepareStatement(selectQuery);
		ps.setLong(1, customerID);
		ResultSet rs = ps.executeQuery();
		if(!rs.next()) {
			return null;
		}
		
		Address add = new Address();
		add.setAddress1(rs.getString("address1"));
		add.setAddress2(rs.getString("address2"));
		add.setCity(rs.getString("city"));
		add.setState(rs.getString("state"));
		add.setZipcode(rs.getString("zipcode"));
		return add;
	
	}

	private static final String deleteSQL = 
		    "DELETE FROM address WHERE customerID = ?;";
	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		
		if (customerID == null) {
			throw new DAOException("Trying to delete Customer with NULL ID");
		}

		
		PreparedStatement ps = null;
		
		ps = connection.prepareStatement(deleteSQL);
		ps.setLong(1, customerID);

		ps.executeUpdate();
		
		
	}

}
