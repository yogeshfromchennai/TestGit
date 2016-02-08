package com.hex.service;

import com.hex.vo.Product;
import java.util.List;
import java.util.ArrayList;
import com.hex.util.HexApplicationException;
import com.hex.dao.ProductDao;

public interface IProduct {
	
	public void setProductDao ( ProductDao productDao );
	
	public ProductDao getProductDao ();

	public void insert(Product object) throws HexApplicationException;

	public void delete(Product object) throws HexApplicationException;

	public void deleteAll(java.util.Collection entries) throws HexApplicationException;

	public void update(Product object) throws HexApplicationException;

	public Object select(Product object) throws HexApplicationException;

	public Object getAllProduct() throws HexApplicationException;

	public Object getAllProduct(int startRecord, int endRecord) throws HexApplicationException;

	public int getProductCount() throws HexApplicationException;

	public List search(String fieldValue, String columnName) throws HexApplicationException;

	
}
