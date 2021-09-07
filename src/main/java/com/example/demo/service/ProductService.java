package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.helper.CSVHelper;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public Product saveProduct(Product product) {
		return repository.save(product);
	}
	
	public List<Product> saveProducts(List<Product> products) {
		return repository.saveAll(products);
	}
	
	public List<Product> getProducts(){
		return repository.findAll();
	}
	
	public Product getProductBy(int id){
		return repository.findById(id).orElse(null);
	}
	
	public Product getProductByName(String name){
		return repository.findByName(name);
	}
	
	public String deleteProduct(int id) {
		repository.deleteById(id);
		return "Product Deleted" + id ;
	}
	
	
	public Product updateProduct (Product product) {
		
		Product existingproduct = repository.findById(product.getId()).orElse(null);
		existingproduct.setName(product.getName());
		existingproduct.setQuantity(product.getQuantity());
		existingproduct.setPrice(product.getPrice());
		return repository.save(existingproduct);
	}
	
	
	public void save(MultipartFile file) {
	    try {
	      List<Product> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
	      repository.saveAll(tutorials);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	  }


}
