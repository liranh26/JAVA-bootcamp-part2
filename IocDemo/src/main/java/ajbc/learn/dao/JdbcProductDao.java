package ajbc.learn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Category;
import ajbc.learn.models.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component(value = "jdbcDao")
@Getter
@Setter
@NoArgsConstructor
public class JdbcProductDao implements ProductDao {


	@Autowired
	private JdbcTemplate template;

	
	RowMapper<Product> rowMapper = (rs, rowNum) -> {
		Product prod = new Product();
		prod.setProductId(rs.getInt(1));
		prod.setProductName(rs.getString(2));
		prod.setSupplierId(rs.getInt(3));
		prod.setQuantityPerUnit(rs.getString(4));
		prod.setUnitPrice(rs.getDouble(5));
		prod.setUnitsInStock(rs.getInt(6));
		prod.setReorderLevel(rs.getInt(7));
		prod.setDiscontinued(rs.getInt(8));

		return prod;
	};

	//CRUD
	
	@Override
	public void addProduct(Product product) throws DaoException {
		String sql = "insert into products values(?)";
		int rowsAffected = template.update(sql, product);
		System.out.println("Rows affected: "+ rowsAffected);
//		ProductDao.super.addProduct(product);
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		// TODO Auto-generated method stub
		ProductDao.super.updateProduct(product);
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		// TODO Auto-generated method stub
		return ProductDao.super.getProduct(productId);
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		// TODO Auto-generated method stub
		ProductDao.super.deleteProduct(productId);
	}	

	@Override
	public long count() throws DaoException {
		// TODO Auto-generated method stub
		return 123456;
	}	
	
}