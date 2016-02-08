package com.hex.delegate;

import com.hex.vo.Product;
import com.hex.service.IProduct;
import com.hex.util.BootStrapper;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import com.hex.util.HexApplicationException;

public class ProductBusinessDelegate {
	
	IProduct product = null;

	public ProductBusinessDelegate() {
		//ioProcess = new <ProcessClassName> ();
		product = (IProduct) BootStrapper.getService().getBean("Product");

	}

	public void insert(Product object) throws HexApplicationException {
		product.insert(object);	
	}

	public void delete(Product object) throws HexApplicationException {
		product.delete(object);
	}

	public void deleteAll(java.util.Collection entries) throws HexApplicationException {
		product.deleteAll(entries);
	}

	public void update(Product object) throws HexApplicationException {
		product.update(object);	
	}

	public Object select(Product object) throws HexApplicationException {
		return product.select(object);	
	}

	public Object getAllProduct() throws HexApplicationException {
		return product.getAllProduct();	
	}

	public Object getAllProduct(int startRecord, int endRecord) throws HexApplicationException {
		return product.getAllProduct(startRecord, endRecord);	
	}

	public int getProductCount() throws HexApplicationException {
		return product.getProductCount();	
	}
	
	public List search(String fieldValue, String columnName) throws HexApplicationException {
		return product.search(fieldValue, columnName);	
	}



}
