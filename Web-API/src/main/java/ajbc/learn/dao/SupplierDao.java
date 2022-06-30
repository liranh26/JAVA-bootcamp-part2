package ajbc.learn.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ajbc.learn.models.Product;
import ajbc.learn.models.Supplier;

@Transactional(rollbackFor = {DaoException.class}, readOnly = true)
public interface SupplierDao {

	//CRUD operations
	@Transactional(readOnly = false)
	public default void addSupplier(Supplier supplier) throws DaoException {
		throw new DaoException("Method not implemented");
	}

	@Transactional(readOnly = false)
	public default void updateSupplier(Supplier supplier) throws DaoException {
		throw new DaoException("Method not implemented");
	}

	public default Supplier getSupplier(Integer supplierId) throws DaoException {
		throw new DaoException("Method not implemented");
	}

	@Transactional(readOnly = false)
	public default void deleteSupplier(Integer productId) throws DaoException {
		throw new DaoException("Method not implemented");
	}

	//Queries
	
	// QUERIES
		public default List<Supplier> getAllSuppliers() throws DaoException {
			throw new DaoException("Method not implemented");
		}

		public default List<Supplier> getSuppliersInCity(String city) throws DaoException {
			throw new DaoException("Method not implemented");
		}

		public default List<Supplier> getInactiveSuppliers() throws DaoException {
			throw new DaoException("Method not implemented");
		}

	
	
}