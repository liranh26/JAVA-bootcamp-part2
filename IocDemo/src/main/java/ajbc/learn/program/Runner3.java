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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner3 {

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		//using hibernate
		
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfing.class);
				SessionFactory factory = ctx.getBean(SessionFactory.class);
				Session session = factory.openSession();) {

			
			//read from db: select * from categories where id = 1
			Category cat1 = session.get(Category.class, 1);
			System.out.println(cat1);
			System.out.println("-----------------");
			
			//write from db: 
			Category cat2 = new Category();
//			cat2.setCategoryId(9);
			cat2.setCatName("Baby products");
			cat2.setDescription("Diapers, Napkins, Pacifiers, Bottels");
			
			Transaction transaction = session.beginTransaction();
			try {
				session.persist(cat2);
				transaction.commit();
				System.out.println("Success inserting cat2");
			}catch(Exception e) {
				transaction.rollback();
				System.out.println("Inseration cancelled - roll back\n" + e);
			}
			
		}
	}

}
