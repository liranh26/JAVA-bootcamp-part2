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
import ajbc.learn.models.Category;
import ajbc.learn.models.ErrorMessage;
import ajbc.learn.models.Product;

@RequestMapping("/products")
@RestController // this eliminates the neseccery of @ResponseBody for the function
public class ProductResource {

	@Autowired
	ProductDao dao;

//	@ResponseBody  //instead of a view send to the browser this list is the body
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<List<Product>> getAllProducts() throws DaoException {
//		List<Product> products = dao.getAllProducts();
//		if (products == null)
//			return ResponseEntity.notFound().build();
//
//		return ResponseEntity.ok(products);
//	}

	// double min & max received from browser -> need to set annotation for that
	// @RequestParam
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductsByRange(@RequestParam Map<String, String> map) throws DaoException {
		List<Product> products;
		Set<String> keys = map.keySet();

		if (keys.contains("min") && keys.contains("max"))
			products = dao.getProductsByPriceRange(Double.parseDouble(map.get("min")),
					Double.parseDouble(map.get("max")));
		else
			products = dao.getAllProducts();

		if (products == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(products);
	}
	
	
	//get product by id via the path variable
	@RequestMapping(method = RequestMethod.GET, path="/{id}")
	public ResponseEntity<?> getProductsById(@PathVariable Integer id) {
		
		Product prod;
		try {
			prod = dao.getProduct(id);
			return ResponseEntity.ok(prod);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to get product with id: "+id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMsg) ;
			
		}
		
	}
	
	//get the incoming data from the request body send by the browser
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addProduct(@RequestBody Product prod){
		try {
			dao.addProduct(prod);
			prod = dao.getProduct(prod.getProductId());
			return ResponseEntity.status(HttpStatus.CREATED).body(prod);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to add product to DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg) ;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, path="/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody Product prod, @PathVariable Integer id){
		
		try {
			prod.setProductId(id);
			dao.updateProduct(prod);
			prod = dao.getProduct(prod.getProductId());
			return ResponseEntity.status(HttpStatus.OK).body(prod);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to update product in DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg) ;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path="/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
		
		try {
			Product prod = dao.getProduct(id);
			dao.deleteProduct(id);
			
			prod = dao.getProduct(id);
			return ResponseEntity.status(HttpStatus.OK).body(prod);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to delete product from DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg) ;
		}
	}	
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}/{catId}")
	public ResponseEntity<?> getProductCategory(@PathVariable Integer id, @PathVariable Integer catId){
		try {
			Category cat = dao.getProductCategory(id, catId);
			return ResponseEntity.status(HttpStatus.OK).body(cat);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to delete product from DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg) ;
		}
		
	}
}
