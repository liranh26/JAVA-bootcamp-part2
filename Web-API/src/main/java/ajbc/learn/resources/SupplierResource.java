package ajbc.learn.resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.dao.SupplierDao;
import ajbc.learn.models.ErrorMessage;
import ajbc.learn.models.Product;
import ajbc.learn.models.Supplier;

@RequestMapping("/suppliers")
@RestController // this eliminates the neseccery of @ResponseBody for the function
public class SupplierResource {

	@Autowired
	SupplierDao dao;
	@Autowired
	ProductDao productDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllSuppliers(@RequestParam Map<String, String> map) throws DaoException {
		List<Supplier> suppliers = null;
		Set<String> keys = map.keySet();
		
		if(keys.contains("city")) {
			try {
			suppliers = dao.getSuppliersInCity(map.get("city"));
			}catch(DaoException e) {
				ErrorMessage errMsg = new ErrorMessage();
				errMsg.setData(e.getMessage());
				errMsg.setMsg("Failed to get supplier! ");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMsg) ;
			}
		}else
			suppliers = dao.getAllSuppliers();
		
		if (suppliers == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(suppliers);
	}
	
		
	@RequestMapping(method = RequestMethod.GET, path="/{id}")
	public ResponseEntity<?> getSupplierById(@PathVariable Integer id) {
		
		Supplier supplier;
		try {
			supplier = dao.getSupplier(id);
			return ResponseEntity.ok(supplier);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to get supplier with id: "+id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMsg) ;
			
		}
		
	}
	

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier){
		try {
			dao.addSupplier(supplier);
			supplier = dao.getSupplier(supplier.getSupplierId());
			return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to add supplier to DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg) ;
		}
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, path="/{id}")
	public ResponseEntity<?> updateSupplier(@RequestBody Supplier supplier, @PathVariable Integer id){
		
		try {
			supplier.setSupplierId(id);
			dao.updateSupplier(supplier);
			supplier = dao.getSupplier(supplier.getSupplierId());
			return ResponseEntity.status(HttpStatus.OK).body(supplier);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to update supplier in DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg) ;
		}
	}

	
	@RequestMapping(method = RequestMethod.DELETE, path="/{id}")
	public ResponseEntity<?> deleteSupplier(@PathVariable Integer id){
		
		try {
			Supplier supplier = dao.getSupplier(id);
			dao.deleteSupplier(id);
			productDao.deleteSupplierInProducts(id);
			supplier = dao.getSupplier(id);
			return ResponseEntity.status(HttpStatus.OK).body(supplier);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to delete supplier from DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg);
		}
	}	
	
	
	@RequestMapping(method = RequestMethod.GET, path ="/inactive")
	public ResponseEntity<?> getInactiveSuppliers(){
		List<Supplier> suppliers;
		try {
			suppliers = dao.getInactiveSuppliers();
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to delete supplier from DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg);
		}
		return ResponseEntity.ok(suppliers);
		
	}
}
