package ajbc.learn.program;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ajbc.learn.config.AppConfing;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Product;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
public class Runner {

	public static void main(String[] args) throws DaoException {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		
		ProductDao dao;

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfing.class);
		dao = ctx.getBean("jdbcDao", ProductDao.class);
		
		System.out.println("------------------------------");
		dao.addProduct(new Product("Crazy Tea", 1, 2, "bla bla", 1.5, 10, 2, 5, 0));
		System.out.println("------------------------------");

		
		System.out.println("------------------------------");
//		dao.updateProduct(null);
		System.out.println("------------------------------");
	}

}
