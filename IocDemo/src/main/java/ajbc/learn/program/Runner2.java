package ajbc.learn.program;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ajbc.learn.config.AppConfing;
import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
public class Runner2 {

	public static void main(String[] args) {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfing.class);

		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		

		
//		RowMapper<Category> rowMapper = new RowMapper<Category>() {
//			
//			@Override
//			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Category cat = new Category();
//				cat.setCategoryId(rs.getInt(1));
//				cat.setCategoryName(rs.getString("categoryName"));
//				cat.setDescription(rs.getString("description"));
//				cat.setPicture(rs.getBytes("picture"));
//				return cat;
//			}
//		};
//		
		
		
		
		
		
//		CRUD
//		insertNewShipper(template);
//		updateShipperPhone(template, 4, "(012) 345-6789");
		
		//query that return a single value
		//print num of products
//		printNumProducts(template);
//		printShipperName(template, 5);
//		printCityOfCustomerById(template, "FISSA");
		
		//query that return a row
//		printProductDetail(template, 3);
//		printAnyOrderOfEmployeeById(template, 5);
//		printAllShippers(template);		
//		printAllShippersName(template);
	
		//convert object to instance
//		printCategoryById(template, rowMapper, 5);
//		printAllCategories(template, rowMapper);
	
	}

	private static void printAllCategories(JdbcTemplate template, RowMapper<Category> rowMapper) {
		List<Category> list = template.query("select * from categories", rowMapper);
		for (Category category : list) {
			System.out.println(category);
		}
	}

	private static void printCategoryById(JdbcTemplate template, RowMapper<Category> rowMapper, int id) {
		String sql = "select * from categories where categoryid = ? ";
		Category cat = template.queryForObject(sql, rowMapper, id);
		System.out.println(cat);
	}

	private static void printAllShippersName(JdbcTemplate template) {
		String sql = "select companyname from shippers";
		List<String> list = template.queryForList(sql, String.class);
		for (String name: list) {
			System.out.println(name);			
		}	
	}

	private static void printAllShippers(JdbcTemplate template) {
		String sql = "select * from shippers";
		List<Map<String, Object>> data = template.queryForList(sql);
		for (Map<String, Object> map : data) {
			System.out.println(map);			
		}	
	}

	private static void printAnyOrderOfEmployeeById(JdbcTemplate template, int employeeId) {
		String sql = "select * from Orders where employeeid = ?";
		List<Map<String, Object>> data = template.queryForList(sql, employeeId);
		if(data != null && data.size() > 1)
			System.out.println(data.get(0));
		else
			System.out.println("Nothing");
	}

	private static void printProductDetail(JdbcTemplate template, int id) {
		String sql = "select* from products where productid = ?";
		Map<String, Object> data = template.queryForMap(sql, id);
		System.out.println(data);
	}

	private static void printCityOfCustomerById(JdbcTemplate template, String id) {
		String sql = "select city from customers where customerid = ?";
		String city = template.queryForObject(sql, String.class, id);
		System.out.println("city is " + city);
	}

	private static void printShipperName(JdbcTemplate template, int id) {
		String sql = "select companyname from shippers where shipperid = ?";
		String name = template.queryForObject(sql, String.class, id);
		System.out.println("Shipper company name is" + name);
	}

	private static void printNumProducts(JdbcTemplate template) {
		String sql = "select count(*) from products";
		int count = template.queryForObject(sql, int.class);//what returns from the query
		System.out.println("count "+ count);
	}

	private static void insertNewShipper(JdbcTemplate template) {
		String sql = "insert into shippers values(?,?)";
		int rowsAffected = template.update(sql, "Soso & sami", "123-456-789");
		System.out.println("Rows affected: "+ rowsAffected);
	}
	
	private static void updateShipperPhone(JdbcTemplate template, int id, String phone) {
		String sql = "update shippers set phone=? where shipperid = ?";
		int rowsAffected = template.update(sql, phone, id);
		System.out.println("Rows affected: "+ rowsAffected);
		
	}
	

}
