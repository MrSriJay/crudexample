package com.example.demo.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;


public class CSVHelper {

	public static String TYPE = "text/csv";
	  static String[] HEADERs = { "Name", "Quantity", "Price" };

	  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

	  public static List<Product> csvToTutorials(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

	      List<Product> tutorials = new ArrayList<Product>();

	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	      for (CSVRecord csvRecord : csvRecords) {
	    	  Product product = new Product(
	              csvRecord.get("Name"),
	              Integer.parseInt(csvRecord.get("Quantity")),
	              Double.parseDouble(csvRecord.get("Price"))
	            );

	        tutorials.add(product);
	      }

	      return tutorials;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	    }
	  }
}
