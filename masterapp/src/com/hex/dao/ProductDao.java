/*******************************************************************************
* @(#) MasterMaintenance.java[BAT001]                0.1            03/02/2005
*
* Copyright (C) : Deutsche Leasing AG
*
* Version History:
*-------------------------------------------------------------------------------
* Date          Author(Emp No.)          Nature of Change                Version
*------------------------------------------------------------------------------
* 03/02/2005    Karthick.S               Initial Release                     0.1
* 22/08/2005    Venkatesh.N(4540)        Adding new methods for
*                                        change in functionality
* 31/01/2006    Venkatesh.N(4540)        UAT defect 13741 fix
* 01/02/06      Venkatesh.N(4540)        UAT defect 13740 fix
* 18/01/2013    Aiswaria K(21667)        For SIT defect 12106
*******************************************************************************/
package com.hex.dao;

import com.hex.vo.Product;
import com.hex.util.HexApplicationException;
import java.util.List;

public interface ProductDao  {

	public Product select(Product object) throws HexApplicationException;

	public void insert(Product object) throws HexApplicationException;

	public void update(Product object) throws HexApplicationException;

	public void delete(Product object) throws HexApplicationException;

	public void deleteAll(java.util.Collection entries) throws HexApplicationException;

	public java.util.List getAllProduct() throws HexApplicationException;

	public java.util.List getAllProduct( int startRecord, int endRecord ) throws HexApplicationException;

	public int getProductCount() throws HexApplicationException;

	public List search(String fieldValue, String columnName)throws HexApplicationException;

	
}
