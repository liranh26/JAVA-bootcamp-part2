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
		
		// This is the dependency (now its null but we need to say new like the example)
		ProductDao dao1;
		
//		example - this is what we try to avoid - the new is a dependency!
//		dao = new JdbcProductDao(); // or dao = new MongoProductDao();
//		System.out.println("The number of product in the DB id :" + dao.count());
		
		
		/* using annotations we will create new dao */
		/* The spring container - which spring works. we need context 
		   the context creates the path to our beans in the confing file */
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfing.class);
		dao1 = ctx.getBean("jdbcDao", ProductDao.class);
		
		System.out.println("------------------------------");
		System.out.println("dao is an instance of " + dao1.getClass().getName());
		System.out.println("The number of product in the DB :" + dao1.count());
		System.out.println("------------------------------");
		
		
	}

}
