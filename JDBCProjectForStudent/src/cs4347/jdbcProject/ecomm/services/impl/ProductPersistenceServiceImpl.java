package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.services.ProductPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	private DataSource dataSource;

	public ProductPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public Product create(Product product) throws SQLException, DAOException {
		ProductDAO productDAO = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			Product prod = productDAO.create(connection, product);

			if ( prod.getId() == null) {
				throw new DAOException("Product can't be null.");
			}			

			connection.commit();
			return prod;
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
	public Product retrieve(Long id) throws SQLException, DAOException {
		
		ProductDAO productDAO = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			Product prod = productDAO.retrieve(connection, id);

			if ( prod.getId() == null) {
				throw new DAOException("Product can't be null.");
			}			

			connection.commit();
			return prod;
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
	public int update(Product product) throws SQLException, DAOException {
		ProductDAO productDAO = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);		

			if (product.getId() == null) {
				throw new DAOException("Product can't be null.");
			}			
			
			int rows = productDAO.update(connection, product);
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
		ProductDAO productDAO = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);		

			if (id == null) {
				throw new DAOException("Product can't be null.");
			}			
			
			int rows = productDAO.delete(connection, id);
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
	public Product retrieveByUPC(String upc) throws SQLException, DAOException {
		
		ProductDAO productDAO = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);		
			
			
			Product prod = productDAO.retrieveByUPC(connection, upc);
			if ( prod.getId() == null) {
				throw new DAOException("Product can't be null.");
			}			
			
			connection.commit();
			return prod;
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
	public List<Product> retrieveByCategory(int category) throws SQLException, DAOException {

		ProductDAO productDAO = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);		
			
			
			List<Product> prod = productDAO.retrieveByCategory(connection, category);
			
			for(Product product:prod){			
				if ( product.getId() == null) {
					throw new DAOException("Product can't be null.");
				}			
			}
			connection.commit();
			return prod;
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
