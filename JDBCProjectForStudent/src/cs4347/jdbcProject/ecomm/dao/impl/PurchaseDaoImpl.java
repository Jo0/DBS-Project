package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class PurchaseDaoImpl implements PurchaseDAO
{

	private static final String insertSQL = 
			"INSERT INTO PURCHASE (id, customerID, productID, purchaseDate, purchaseAmount) VALUES (?, ?, ?, ?, ?);";
	@Override
	public Purchase create(Connection connection, Purchase purchase) throws SQLException, DAOException {
		// Requirement: Create operations require that the customer's ID is null 
        // before being inserted into the table.
		if (purchase.getId() != null) {
			throw new DAOException("Trying to insert purchase with NON-NULL ID");
		}

		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, 0);
			ps.setLong(2, purchase.getCustomerID());
			ps.setLong(3, purchase.getProductID());
			ps.setDate(4, purchase.getPurchaseDate());
			ps.setDouble(5, purchase.getPurchaseAmount());
			ps.executeUpdate();

			// REQUIREMENT: Copy the generated auto-increment primary key to the customer ID.
			
			ResultSet keyRS = ps.getGeneratedKeys();
			if(keyRS.next())
			{
			int lastKey = keyRS.getInt(1);
			purchase.setId((long) lastKey);
			}
			return purchase;
		
	}

	private static final String selectQuery = 
			"SELECT id, customerID, productID, purchaseDate, purchaseAmount FROM purchase where id = ?";
	@Override
	public Purchase retrieve(Connection connection, Long id) throws SQLException, DAOException {
		if (id == null) {
			throw new DAOException("Trying to retrieve Purchase with NULL ID");
		}
	
		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				return null;
			}
			
	
			Purchase purc  = new Purchase();
			purc.setId(rs.getLong("id"));
			purc.setCustomerID(rs.getLong("customerID"));
			purc.setProductID(rs.getLong("productID"));
			purc.setPurchaseDate(rs.getDate("purchaseDate"));
			purc.setPurchaseAmount(rs.getDouble("purchaseAmount"));
			return purc;
		
	}
	
	private static final String updateSQL = 
			"UPDATE PURCHASE SET customerID = ?, productID = ?, purchaseDate = ?, purchaseAmount = ?;";
	@Override
	public int update(Connection connection, Purchase purchase) throws SQLException, DAOException {
		
		Long id = purchase.getId();
		if (id == null) {
			throw new DAOException("Trying to update Purchase with NULL ID");
		}

		
		PreparedStatement ps = null;
	
			ps = connection.prepareStatement(updateSQL);
			ps.setLong(1, purchase.getCustomerID());
			ps.setLong(2, purchase.getProductID());
			ps.setDate(3, purchase.getPurchaseDate());
			ps.setDouble(4, purchase.getPurchaseAmount());
			int rows = ps.executeUpdate();
			return rows;
		
	}

	private static final String deleteSQL = 
		    "DELETE FROM PURCHASE WHERE ID = ?;";
	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {

		if (id == null) {
			throw new DAOException("Trying to delete Purchase with NULL ID");
		}

		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rows = ps.executeUpdate();
			return rows;
	}

	private static final String custIDQuery = 
			"SELECT id, customerID, productID, purchaseDate, purchaseAmount FROM PURCHASE where customerID = ?";
	@Override
	public List<Purchase> retrieveForCustomerID(Connection connection, Long customerID)
			throws SQLException, DAOException {

		List<Purchase> result = new ArrayList<Purchase>();
		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(custIDQuery);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			
				Purchase purc  = new Purchase();
				purc.setId(rs.getLong("id"));
				purc.setCustomerID(rs.getLong("customerID"));
				purc.setProductID(rs.getLong("productID"));
				purc.setPurchaseDate(rs.getDate("purchaseDate"));
				purc.setPurchaseAmount(rs.getDouble("purchaseAmount"));			
				result.add(purc);
			}
			return result;
	}

	private static final String prodIDQuery = 
			"SELECT id, customerID, productID, purchaseDate, purchaseAmount FROM PURCHASE where productID = ?";
	@Override
	public List<Purchase> retrieveForProductID(Connection connection, Long productID)
			throws SQLException, DAOException {
		
		List<Purchase> result = new ArrayList<Purchase>();
		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(prodIDQuery);
			ps.setLong(1, productID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			
				Purchase purc  = new Purchase();
				purc.setId(rs.getLong("id"));
				purc.setCustomerID(rs.getLong("customerID"));
				purc.setProductID(rs.getLong("productID"));
				purc.setPurchaseDate(rs.getDate("purchaseDate"));
				purc.setPurchaseAmount(rs.getDouble("purchaseAmount"));			
				result.add(purc);
			}
			return result;
	}

	private static final String PurchaseQuery = 
			"SELECT MIN(purchaseAmount) as minp, MAX(purchaseAmount) as maxp, AVG(purchaseAmount) as avgp FROM PURCHASE WHERE customerID = ?;";
	@Override
	public PurchaseSummary retrievePurchaseSummary(Connection connection, Long customerID)
			throws SQLException, DAOException {
		
		PurchaseSummary percsum = new PurchaseSummary();
		PreparedStatement ps = null;
		
		
		ps = connection.prepareStatement(PurchaseQuery);
		ps.setLong(1, customerID);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
	    percsum.minPurchase = (float)rs.getDouble("minp");
		percsum.maxPurchase = (float)rs.getDouble("maxp");		
		percsum.avgPurchase = (float)rs.getDouble("avgp");
		}
		
		return percsum;
	}
	
}
