package ajbc.learn.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Category;

@SuppressWarnings("unchecked")
@Component(value = "catHtDao")
public class HibernateTemplateCategoryDao implements CategoryDao {

	@Autowired
	private HibernateTemplate template;

	@Override
	public void addCategory(Category category) throws DaoException {
		template.persist(category);
	}

	@Override
	public void updateCategory(Category category) throws DaoException {
		template.merge(category);
	}

	@Override
	public Category getCategory(Integer categoryId) throws DaoException {
		Category cat = template.get(Category.class, categoryId);
		if(cat == null)
			throw new DaoException("Catgory with id: "+categoryId+" doesn't exist in DB!");
		return cat;
	}

	@Override
	public void deleteCategory(Integer categoryId) throws DaoException {
		Category category = getCategory(categoryId);
		category.setInactive(1);
		updateCategory(category);
	}

	@Override
	public List<Category> getAllCategories() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);	
		return (List<Category>) template.findByCriteria(criteria);
	}

	@Override
	public List<Category> getInactiveCategories() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);	
		criteria.add(Restrictions.eq("inactive", 1));
		List<Category> categories = (List<Category>) template.findByCriteria(criteria);
		if(categories.isEmpty())
			throw new DaoException("All categories are active!");
		return categories;
	}


}
