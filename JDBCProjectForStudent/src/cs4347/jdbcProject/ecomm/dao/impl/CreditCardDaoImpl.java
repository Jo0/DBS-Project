package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.util.DAOException;


public class CreditCardDaoImpl implements CreditCardDAO
{
	private static final String insertSQL = 
			"INSERT INTO creditcard (name, ccNumber, expDate, securityCode, customerID) VALUES (?, ?, ?, ?, ?);";
	@Override
	public CreditCard create(Connection connection, CreditCard creditCard, Long customerID)
			throws SQLException, DAOException {
		
		PreparedStatement ps = null;
		
		ps = connection.prepareStatement(insertSQL);
		ps.setString(1, creditCard.getName());
		ps.setString(2, creditCard.getCcNumber());
		ps.setString(3, creditCard.getExpDate());
		ps.setString(4, creditCard.getSecurityCode());
		ps.setLong(5, customerID);
		
		ps.executeUpdate();

		return creditCard;
	
	}

	private static final String selectQuery = 
			"SELECT name, ccNumber, expDate, securityCode FROM creditcard where customerID = ?";
	@Override
	public CreditCard retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		
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
		
		CreditCard cc = new CreditCard();
		cc.setName(rs.getString("name"));
		cc.setCcNumber(rs.getString("ccNumber"));
		cc.setExpDate(rs.getString("expDate"));
		cc.setSecurityCode(rs.getString("securityCode"));		
		return cc;
	}

	private static final String deleteSQL = 
		    "DELETE FROM creditcard WHERE customerID = ?;";
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
