package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Product;
import com.example.demo.helper.CSVHelper;
import com.example.demo.message.ResponseMessage;
import com.example.demo.service.ProductService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	//Get all products
	@GetMapping("/products")
	public List<Product> findAllProducts(){
		return service.getProducts();	
	}

	//Get product by ID
	@GetMapping("/product/{id}")
	public Product findProductByID(@PathVariable int id) {
		return service.getProductBy(id);
	}
	
	//Add one product
	@PostMapping("/addproduct")
	public Product addProduct(@RequestBody Product product ) {
		return service.saveProduct(product);
	}
	
	//Add one product
	@PostMapping("/addproducts")
	public List<Product> addProducts(@RequestBody List<Product> products ) {
		return service.saveProducts(products);
	}
	
	//Update product details
	@PutMapping("/updateproduct")
	public Product updateProduct(@RequestBody Product product ) {
		return service.updateProduct(product);
	}
	
	//Delete product
	@DeleteMapping("/deleteproduct/{id}")
	public String deleteProduct(@PathVariable int id) {
		return service.deleteProduct(id);
	}
	
	
	//Upload from CSV
	@PostMapping("/uploadproducts")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";

	    if (CSVHelper.hasCSVFormat(file)) {
	      try {
	    	  service.save(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	      }
	    }

	    message = "Please upload a csv file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	  }
}
