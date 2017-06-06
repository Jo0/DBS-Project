package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchasePersistenceService;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class PurchasePersistenceServiceImpl implements PurchasePersistenceService
{
	private DataSource dataSource;

	public PurchasePersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public Purchase create(Purchase purchase) throws SQLException, DAOException {
		
		PurchaseDAO purchaseDAO = new PurchaseDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			Purchase purc = purchaseDAO.create(connection, purchase);

			if ( purc.getId() == null) {
				throw new DAOException("Purchase can't be null.");
			}			

			connection.commit();
			return purc;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public Purchase retrieve(Long id) throws SQLException, DAOException {
		PurchaseDAO purchaseDAO = new PurchaseDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			Purchase purc = purchaseDAO.retrieve(connection, id);

			if ( purc.getId() == null) {
				throw new DAOException("purchase can't be null.");
			}			

			connection.commit();
			return purc;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public int update(Purchase purchase) throws SQLException, DAOException {
		PurchaseDAO PurchaseDAO = new PurchaseDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);		

			if (purchase.getId() == null) {
				throw new DAOException("Purchase can't be null.");
			}			
			
			int rows = PurchaseDAO.update(connection, purchase);
			connection.commit();
			return rows;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public int delete(Long id) throws SQLException, DAOException {
		PurchaseDAO PurchaseDAO = new PurchaseDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);		

			if (id == null) {
				throw new DAOException("Purchase can't be null.");
			}			
			
			int rows = PurchaseDAO.delete(connection, id);
			connection.commit();
			return rows;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public List<Purchase> retrieveForCustomerID(Long customerID) throws SQLException, DAOException {
		PurchaseDAO PurchaseDAO = new PurchaseDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);		
			
			if (customerID == null) {
				throw new DAOException("CustomerID can't be null.");			}			
			
			
			List<Purchase> results = PurchaseDAO.retrieveForCustomerID(connection, customerID);
				
			
			connection.commit();
			return results;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}	
	}

	@Override
	public PurchaseSummary retrievePurchaseSummary(Long customerID) throws SQLException, DAOException {
		PurchaseDAO PurchaseDAO = new PurchaseDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);		
			
			if (customerID == null) {
				throw new DAOException("CustomerID can't be null.");			}			
			
			
			PurchaseSummary percsum = PurchaseDAO.retrievePurchaseSummary(connection, customerID);
				
			
			connection.commit();
			return percsum;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}	
	}

	@Override
	public List<Purchase> retrieveForProductID(Long productID) throws SQLException, DAOException {
		PurchaseDAO PurchaseDAO = new PurchaseDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);		
			
			if (productID == null) {
				throw new DAOException("productID can't be null.");			}			
			
			
			List<Purchase> results = PurchaseDAO.retrieveForProductID(connection, productID);
				
			
			connection.commit();
			return results;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}	
	}

}
