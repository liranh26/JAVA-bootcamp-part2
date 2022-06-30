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

import ajbc.learn.dao.CategoryDao;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Category;
import ajbc.learn.models.ErrorMessage;
import ajbc.learn.models.Product;

@RequestMapping("/categories")
@RestController // this eliminates the neseccery of @ResponseBody for the function
public class CategoryResource {

	@Autowired
	CategoryDao dao;
	@Autowired
	ProductDao productDao;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories() throws DaoException {
		List<Category> categorys;

		categorys = dao.getAllCategories();

		if (categorys == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(categorys);
	}

	// get product by id via the path variable
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {

		Category category;
		try {
			category = dao.getCategory(id);
			return ResponseEntity.ok(category);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("Failed to get category with id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMsg);
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addCategory(@RequestBody Category category) {
		try {
			dao.addCategory(category);
			category = dao.getCategory(category.getCategoryId());
			return ResponseEntity.status(HttpStatus.CREATED).body(category);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("Failed to add category to DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable Integer id) {

		try {
			category.setCategoryId(id);
			dao.updateCategory(category);
			category = dao.getCategory(category.getCategoryId());
			return ResponseEntity.status(HttpStatus.OK).body(category);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("Failed to update category in DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {

		try {
			Category category = dao.getCategory(id);
			dao.deleteCategory(id);
			productDao.deleteCategoryInProducts(id);
			category = dao.getCategory(id);
			return ResponseEntity.status(HttpStatus.OK).body(category);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("failed to delete category from DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/inactive")
	public ResponseEntity<?> getInactiveCategory() {

		try {
			List<Category> categories = dao.getInactiveCategories();
			return ResponseEntity.status(HttpStatus.OK).body(categories);
		} catch (DaoException e) {
			ErrorMessage errMsg = new ErrorMessage();
			errMsg.setData(e.getMessage());
			errMsg.setMsg("No inactive categories in DB.");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errMsg);
		}
	}

}
