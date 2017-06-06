package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO
{

	private static final String insertSQL = 
			"INSERT INTO PRODUCT (id, prodName, prodDescription, prodCategory, prodUPC) VALUES (?, ?, ?, ?, ?);";
	@Override
	public Product create(Connection connection, Product product) throws SQLException, DAOException {
		// Requirement: Create operations require that the product's ID is null 
        // before being inserted into the table.
		if (product.getId() != null) {
			throw new DAOException("Trying to insert Product with NON-NULL ID");
		}

		
		PreparedStatement ps = null;
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, 0);
			ps.setString(2, product.getProdName());
			ps.setString(3, product.getProdDescription());
			ps.setInt(4, product.getProdCategory());
			ps.setString(5, product.getProdUPC());
			ps.executeUpdate();

			// REQUIREMENT: Copy the generated auto-increment primary key to the customer ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			if(keyRS.next())
			{
			int lastKey = keyRS.getInt(1);
			product.setId((long) lastKey);
			}
			return product;
		
	}

	private static final String selectQuery = 
			"SELECT id, prodName, prodDescription, prodCategory, prodUPC FROM product where id = ?";
	@Override
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException {
		
		if (id == null) {
			throw new DAOException("Trying to retrieve Product with NULL ID");
		}
	
		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				return null;
			}			
	
			Product prod  = new Product();
			prod.setId(rs.getLong("id"));
			prod.setProdName(rs.getString("prodName"));
			prod.setProdDescription(rs.getString("prodDescription"));
			prod.setProdCategory(rs.getInt("prodCategory"));
			prod.setProdUPC(rs.getString("prodUPC"));
			return prod;
		
	}

	private static final String updateSQL = 
			"UPDATE PRODUCT SET prodName = ?, prodDescription = ?, prodCategory = ?, prodUPC = ?;";
	@Override
	public int update(Connection connection, Product product) throws SQLException, DAOException {
		
		Long id = product.getId();
		if (id == null) {
			throw new DAOException("Trying to update Product with NULL ID");
		}

		
		PreparedStatement ps = null;
	
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
			int rows = ps.executeUpdate();
			return rows;
		
	}

	private static final String deleteSQL = 
		    "DELETE FROM PRODUCT WHERE ID = ?;";
	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		
		if (id == null) {
			throw new DAOException("Trying to delete Product with NULL ID");
		}

		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rows = ps.executeUpdate();
			return rows;
		
	}

	private static final String catQuery = 
			"SELECT id, prodName, prodDescription, prodCategory, prodUPC FROM product where prodCategory = ?";
	@Override
	public List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException {
		

		List<Product> result = new ArrayList<Product>();
		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(catQuery);
			ps.setInt(1, category);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			
				Product prod  = new Product();
				prod.setId(rs.getLong("id"));
				prod.setProdName(rs.getString("prodName"));
				prod.setProdDescription(rs.getString("prodDescription"));
				prod.setProdCategory(rs.getInt("prodCategory"));
				prod.setProdUPC(rs.getString("prodUPC"));
				result.add(prod);
			}
			return result;
		
	}

	private static final String upcQuery = 
			"SELECT id, prodName, prodDescription, prodCategory, prodUPC FROM product where prodUPC = ?";
	@Override
	public Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException {
		
		if (upc == null) {
			throw new DAOException("Trying to retrieve Customer with NULL zipCode");
		}
		
		PreparedStatement ps = null;
		
			ps = connection.prepareStatement(upcQuery);
			ps.setString(1, upc);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				return null;
			}
			
	
			Product prod  = new Product();
			prod.setId(rs.getLong("id"));
			prod.setProdName(rs.getString("prodName"));
			prod.setProdDescription(rs.getString("prodDescription"));
			prod.setProdCategory(rs.getInt("prodCategory"));
			prod.setProdUPC(rs.getString("prodUPC"));
			return prod;
		
	}

}
