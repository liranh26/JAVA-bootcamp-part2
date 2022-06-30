package ajbc.learn.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Product;
import ajbc.learn.models.Supplier;

@SuppressWarnings("unchecked")
@Component(value = "supHtDao")
public class HibernateTemplateSupplierDao implements SupplierDao {

	@Autowired
	private HibernateTemplate template;

	@Override
	public void addSupplier(Supplier supplier) throws DaoException {
		template.persist(supplier);
	}

	@Override
	public void updateSupplier(Supplier supplier) throws DaoException {
		template.merge(supplier);
	}

	@Override
	public Supplier getSupplier(Integer supplierId) throws DaoException {
		Supplier supplier = template.get(Supplier.class, supplierId);
		if (supplier == null)
			throw new DaoException("No such product in the DB");
		return supplier;
	}

	@Override
	public void deleteSupplier(Integer productId) throws DaoException {
		Supplier supplier = getSupplier(productId);
		supplier.setInactive(1);
		updateSupplier(supplier);
	}

	@Override
	public List<Supplier> getAllSuppliers() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);
		return (List<Supplier>) template.findByCriteria(criteria);
	}

	@Override
	public List<Supplier> getSuppliersInCity(String city) throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);
		criteria.add(Restrictions.eq("city", city));
		List<Supplier> suppliers = (List<Supplier>) template.findByCriteria(criteria);
		if (suppliers.isEmpty())
			throw new DaoException("There is no supplier for city: " + city);
		return suppliers;
	}

	@Override
	public List<Supplier> getInactiveSuppliers() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);
		criteria.add(Restrictions.eq("inactive", 1));
		return (List<Supplier>) template.findByCriteria(criteria);
	}

}
