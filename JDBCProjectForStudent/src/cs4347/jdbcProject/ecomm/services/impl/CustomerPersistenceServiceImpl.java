package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.impl.AddressDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CreditCardDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.services.CustomerPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService
{
	private DataSource dataSource;

	public CustomerPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	/**
	 * This method provided as an example of transaction support across multiple inserts.
	 * 
	 * Persists a new Customer instance by inserting new Customer, Address, 
	 * and CreditCard instances. Notice the transactional nature of this 
	 * method which inludes turning off autocommit at the start of the 
	 * process, and rolling back the transaction if an exception 
	 * is caught. 
	 */
	@Override
	public Customer create(Customer customer) throws SQLException, DAOException
	{
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();

		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			Customer cust = customerDAO.create(connection, customer);
			Long custID = cust.getId();

			if (cust.getAddress() == null) {
				throw new DAOException("Customers must include an Address instance.");
			}
			Address address = cust.getAddress();
			addressDAO.create(connection, address, custID);

			if (cust.getCreditCard() == null) {
				throw new DAOException("Customers must include an CreditCard instance.");
			}
			CreditCard creditCard = cust.getCreditCard();
			creditCardDAO.create(connection, creditCard, custID);

			connection.commit();
			return cust;
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
	public Customer retrieve(Long id) throws SQLException, DAOException {

		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();

		Connection connection = dataSource.getConnection();		
		try {
			connection.setAutoCommit(false);
			Customer cust = customerDAO.retrieve(connection, id);
			
			Address address = addressDAO.retrieveForCustomerID(connection, id);
			cust.setAddress(address);
			
			if (cust.getAddress() == null) {
				throw new DAOException("Customers must include an Address instance.");
			}

			CreditCard creditCard = creditCardDAO.retrieveForCustomerID(connection, id);
			cust.setCreditCard(creditCard);
			
			if (cust.getCreditCard() == null) {
				throw new DAOException("Customers must include an CreditCard instance.");
			}

			connection.commit();
			return cust;
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
	public int update(Customer customer) throws SQLException, DAOException {

		CustomerDAO customerDAO = new CustomerDaoImpl();
		//AddressDAO addressDAO = new AddressDaoImpl();
		//CreditCardDAO creditCardDAO = new CreditCardDaoImpl();

		Connection connection = dataSource.getConnection();		
		try {
			connection.setAutoCommit(false);
			
			if(customer.getId()== null)
				{
				throw new DAOException("Customers must have an ID.");
				}			
			
			if (customer.getAddress() == null) {
				throw new DAOException("Customers must include an Address instance.");
			}
			
			if (customer.getCreditCard() == null) {
				throw new DAOException("Customers must include an CreditCard instance.");
			}			
			int rows = customerDAO.update(connection, customer);
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
		
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();

		Connection connection = dataSource.getConnection();		
		try {
			connection.setAutoCommit(false);
			
			addressDAO.deleteForCustomerID(connection, id);
			
			if (addressDAO.retrieveForCustomerID(connection, id) != null) {
				throw new DAOException("Address was not deleted.");
			}
			
			creditCardDAO.deleteForCustomerID(connection, id);
			
			if (creditCardDAO.retrieveForCustomerID(connection, id) != null) {
				throw new DAOException("CreditCard was not deleted");
			}

			int rows = customerDAO.delete(connection, id);
			
		

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
	public List<Customer> retrieveByZipCode(String zipCode) throws SQLException, DAOException {
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();

		Connection connection = dataSource.getConnection();		
		try {
			connection.setAutoCommit(false);
			List<Customer> result = new ArrayList<Customer>();
			
			result = customerDAO.retrieveByZipCode(connection, zipCode);
			
			for(Customer customer:result)
			{
				customer.setAddress(addressDAO.retrieveForCustomerID(connection, customer.getId()));
				if (customer.getAddress() == null) {
					throw new DAOException("Customers must have an Address instance.");
				}
				
				customer.setCreditCard(creditCardDAO.retrieveForCustomerID(connection, customer.getId()));				
				if (customer.getCreditCard() == null) {
					throw new DAOException("Customers must have an CreditCard instance.");
				}
			}		

			connection.commit();
			return result;
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
	public List<Customer> retrieveByDOB(Date startDate, Date endDate) throws SQLException, DAOException {
		
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();

		Connection connection = dataSource.getConnection();		
		try {
			connection.setAutoCommit(false);
			List<Customer> result = new ArrayList<Customer>();
			
			result = customerDAO.retrieveByDOB(connection, startDate, endDate);
			
			for(Customer customer:result)
			{
				customer.setAddress(addressDAO.retrieveForCustomerID(connection, customer.getId()));
				if (customer.getAddress() == null) {
					throw new DAOException("Customers must have an Address instance.");
				}
				
				customer.setCreditCard(creditCardDAO.retrieveForCustomerID(connection, customer.getId()));				
				if (customer.getCreditCard() == null) {
					throw new DAOException("Customers must have an CreditCard instance.");
				}
			}		

			connection.commit();
			return result;
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
