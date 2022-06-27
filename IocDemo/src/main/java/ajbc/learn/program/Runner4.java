package ajbc.learn.program;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ajbc.learn.config.AppConfing;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.HibernateTemplateProductDao;
import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Category;
import ajbc.learn.models.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner4 {

	public static void main(String[] args) throws DaoException {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// using hibernate

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfing.class);) {

//			!!!---> This works but it is not solid - beacuase its calling the HibernateTemplateProductDao class and not interface
//			HibernateTemplateProductDao dao = ctx.getBean(HibernateTemplateProductDao.class);

//solid -> makes HibernateTemplateProductDao a @Component , then check its in the scan in config @ComponentScan(basePackages = {"ajbc.learn.dao"})
			ProductDao dao = ctx.getBean("htDao", ProductDao.class);

			System.out.println("Dao is instace of: " + dao.getClass().getName());

			System.out.println("count is " + dao.count());

			List<Product> products = dao.getAllProducts();

			Double min = 50.0, max = 200.0;
			products = dao.getProductsByPriceRange(min, max);
			System.out.println(products.size());

			products = dao.getProductsByPriceRange(max, min);
			System.out.println(products.size());

			Product prod = dao.getProduct(1);
			System.out.println(prod);

			prod.setUnitPrice(prod.getUnitPrice() + 1);
			
			try {
				dao.updateProduct(prod);
			} catch (DaoException e) {
				System.out.println(e);
			}

		}
	}

}
