package ajbc.learn.program;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ajbc.learn.config.AppConfing;
import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.ProductDao;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
public class Runner {

	public static void main(String[] args) {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		
		ProductDao dao1;

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfing.class);
		dao1 = ctx.getBean("jdbcDao", ProductDao.class);
		
		System.out.println("------------------------------");
		System.out.println("The number of product in the DB :" + dao1.count());
		System.out.println("------------------------------");
		
		
	}

}
