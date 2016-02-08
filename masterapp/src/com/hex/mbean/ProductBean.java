/*
 * EmpBean.java
 *
 * Created on May 22, 2007, 3:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.hex.mbean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.ListDataModel; 
import javax.faces.model.DataModel; 
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlPanelGroup;
import java.io.Serializable;
import com.hex.vo.Product;
import com.hex.delegate.ProductBusinessDelegate;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import java.util.Date;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.hex.util.HexApplicationException;

import java.lang.reflect.Field;	
public class ProductBean implements Serializable{
    private List productList;
    private boolean deleteAll;
    private boolean hasContents;
    private Product product;
    private SelectItem[] pageList;
    private int pageSize;
    private String currentPage;
    
    

   private DataModel productDataModel;	
   private SelectItem[] columnNameList;	

   private String fieldValue;		
   private String columnName;		
   private String columnValue;	
   
   private HtmlPanelGroup productPanel;

private int scrollerPage;

    public int getScrollerPage() {
        return this.scrollerPage;
    }

    public void setScrollerPage(int scrollerPage) {
        this.scrollerPage = scrollerPage;
    }
    
    public ProductBean() {
        product = new Product();
	setProductPanel(new HtmlPanelGroup());
        pageSize = 100;
        initialiseList();
        
    }
    private void initialiseList(){
        try{
		productList = getAllContents();
		doPagination(productList);
        }catch(Exception ex){
   		FacesContext.getCurrentInstance ().addMessage ( "Error", new FacesMessage ( FacesMessage.SEVERITY_INFO, ex.getMessage(), ex.getMessage()));        
        }
        
    }
    
    
    public void pageValueChanged(ValueChangeEvent ve){
        currentPage = (String)ve.getNewValue();
        getList();
        
          FacesContext.getCurrentInstance().renderResponse();
    }
    public SelectItem[] getPageList() {
        return pageList;
    }
    
    public void setPageList(SelectItem[] pageList) {
        this.pageList = pageList;
    }
    public String getCurrentPage() {
        return currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
     public void setProductDataModel(DataModel productDataModel) {
            this.productDataModel = productDataModel;
        }
    
        public DataModel getProductDataModel() {
            return productDataModel;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public Product getProduct() {
        return product;
    }
    
    
     
     
    
    public void setHasContents(boolean hasContents) {
        this.hasContents = hasContents;
    }
    
    public boolean isHasContents() {
        return hasContents;
    }
    
    public void setDeleteAll(boolean deleteAll) {
        this.deleteAll = deleteAll;
    }
    
    public boolean isDeleteAll() {
        return deleteAll;
    }
    
    public List getProductList(){
        return productList;
    }
    
    public void setProductList(List productList){
        this.productList = productList;
    }
    
public String createNewRecord(){
	
			
        ProductBusinessDelegate delegate = new ProductBusinessDelegate();
		
        try{
			
			delegate.insert(product);
        }
        /*Bug# 11 dated :25 Mar Updated by bhushan*/
        catch(HexApplicationException ex){
		System.out.println("inside *************  catch(HexApplicationException ex)");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"primary key violation Duplicate entry","Duplicate entry"));  
		//ex.printStackTrace();
            /*String msg=null;
            String msg1=null;
            String msg2="GenericJDBCException"; 
            String msg3="ConstraintViolationException";
            String msg4="SQLGrammarException";
            String getmsg=ex.toString();
            if(getmsg.indexOf(msg3)>=0){
                System.out.println("Duplicate Value");
                msg = ex.getStackTraceString().substring(ex.getStackTraceString().indexOf("Duplicate"));
               // msg1 = msg.substring(0,msg.indexOf("at"));
			msg1="Duplicate Value Entered";
            }
             if(getmsg.indexOf(msg2)>=0){
                System.out.println("Data too long");
                ex.printStackTrace();
                msg =  ex.getStackTraceString().substring(ex.getStackTraceString().indexOf("Data"));
                String str1 = msg.substring(0,msg.indexOf("at com"));
                int indx=str1.indexOf("at row");
                //msg1=str1.substring(0, indx);
			msg1="Data is Too Long";
             }
             if(getmsg.indexOf(msg4)>=0){
                msg1="Database/Table Not Found!!!";
             }
		//msg1="exception in createNewRecord !!!! ";
             FacesContext.getCurrentInstance ().addMessage ("Error", new FacesMessage ( FacesMessage.SEVERITY_INFO, msg1,msg1));    */    
             return "productAdd";
        }
        catch(NumberFormatException exp3){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"primary key violation Duplicate entry","Duplicate entry"));        
            return "productAdd";
        }
	catch(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"primary key violation Duplicate entry","Duplicate entry"));        
            return "productAdd";
        }
        catch(Error e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"primary key violation Duplicate entry","Duplicate entry"));       
            return "productAdd";
        }
        /*End: Bug# 11 dated :25 Mar Updated by bhushan*/
        initialiseList();
        return "productList";
    }

    public void clear(){
		fieldValue = "";
		setColumnValue("");
		initialiseList();
	}
    
    public String getList(){
  
    try{
        int iCurrentPage = Integer.parseInt(currentPage);
        int endRecord = (iCurrentPage*pageSize);
        int startRecord = endRecord - pageSize;
        List resultList =  getContents(startRecord,pageSize);
        this.setProductList(resultList);
         productDataModel.setWrappedData(resultList);
         }catch(Exception ex){
	             ex.printStackTrace();
	             FacesContext.getCurrentInstance ().addMessage ( "Error", new FacesMessage ( FacesMessage.SEVERITY_INFO, ex.getMessage(), ex.getMessage()));        
        }
        return "productList";
    }
    private List getContents(int startRecord, int pageSize){
        ProductBusinessDelegate delegate = new ProductBusinessDelegate();
        try{
            return (List)delegate.getAllProduct(startRecord,pageSize);
        }catch(Exception ex){
            ex.printStackTrace();
             FacesContext.getCurrentInstance ().addMessage ( "Error", new FacesMessage ( FacesMessage.SEVERITY_INFO, ex.getMessage(), ex.getMessage()));        
            return null;
        }
        
    }
    
    
    private List getAllContents(){
	
	populateColumnNameFields(); // Calling local method to populate fields in the drop-down. 

        ProductBusinessDelegate delegate = new ProductBusinessDelegate();
        try{
            return (List)delegate.getAllProduct();
        }catch(Exception ex){
            ex.printStackTrace();
             FacesContext.getCurrentInstance ().addMessage ( "Error", new FacesMessage ( FacesMessage.SEVERITY_INFO, ex.getMessage(), ex.getMessage()));        
            return null;
        }
        
    }
    
    
    public String select(){
        this.setProduct((Product)productDataModel.getRowData());
		
		FacesContext.getCurrentInstance().renderResponse();
        return "productEdit";
    }
   

   public String updateRecord(){
       
		try{
			
            ProductBusinessDelegate delegate = new ProductBusinessDelegate();
				
            delegate.update(product);
        }catch(Exception ex){
            ex.printStackTrace();
             FacesContext.getCurrentInstance ().addMessage ( "Error", new FacesMessage ( FacesMessage.SEVERITY_INFO, ex.getMessage(), ex.getMessage()));
             return "productEdit";
        }
           initialiseList();
        return "productList";
    }
    
    public String delete(){
    
        List deleteList = new ArrayList();
		System.out.println("getting into delete method");
        for(int index=0;index<productList.size();index++){
            Product product = (Product)productList.get(index);
            if(product.isToBeDeleted()== true){
                deleteList.add(product);
            }
        }
        
        
        ProductBusinessDelegate delegate = new ProductBusinessDelegate();
        try{
            if(deleteList.size()>0)
                delegate.deleteAll(deleteList);
        }catch(Exception ex){
            ex.printStackTrace();
             FacesContext.getCurrentInstance ().addMessage ( "Error", new FacesMessage ( FacesMessage.SEVERITY_INFO, ex.getMessage(), ex.getMessage()));        
        }
         initialiseList();
        return "productList";

    }
    
   

 public String add(){

System.out.println("getting into add method");
     product = new Product();
	 return "productAdd";
	}


	public SelectItem[] getColumnNameList() {
		return columnNameList;
	}

	public void setColumnNameList(SelectItem[] columnNameList) {
		this.columnNameList = columnNameList;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	private Field[] getFields(Object source) {
		return source.getClass().getDeclaredFields();
	}

	private void populateColumnNameFields() {
		
		if (columnNameList == null || columnNameList.length == 0) {
			Field fields[] = getFields(product);			
			columnNameList = new SelectItem[fields.length-1];
			int i=0;
	

			for (int index = 0; index < fields.length-2; index++) {
				Field field = fields[index];
				if (field.getName() != null && field.getName().length() != 0
						&& field.getName()!= "hashCode" && field.getName()!= "toBeDeleted") {
					String tempColumnValue = field.getName();
					tempColumnValue = tempColumnValue.substring(0, 1)
							.toUpperCase()
							+ tempColumnValue.substring(1,
									tempColumnValue.length()).toLowerCase();
									
						columnValue=field.getName();
					columnNameList[i] = new SelectItem(field.getName(),
							tempColumnValue);
				  i++;
				}
			}

			columnValue="--Select the field--";
            columnNameList[i] = new SelectItem(columnValue,"--Select the field--");

		}
	}


	public String search() {	

	System.out.println("Entering into Search of BEAN********");

		ProductBusinessDelegate delegate = new ProductBusinessDelegate();
		try {
			productList = (List) delegate.search(getFieldValue(),
					getColumnValue());
			if (productList.size() > 0) {
	  			 doPagination(productList);
			}
			else{
				 doPagination(productList);
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = 
								    new FacesMessage
									(FacesMessage.SEVERITY_ERROR, 
									"The value you have entered is not available in the Database...",""); 
				facesContext.addMessage("productForm", facesMessage);

				

				return null;
			}
		} catch (Exception ex) {

			ex.printStackTrace();

		}
		return "productList";
	}

	public void doPagination(List productList){
		
		int recordCount = productList.size();
		int totalPages = (recordCount / pageSize)+(recordCount % pageSize >0?1:0);
		if(recordCount>pageSize){
		productList = productList.subList(0,pageSize);
		}
		currentPage = "1";
		productDataModel = new ListDataModel();
		pageList = new SelectItem[totalPages];
		for(int index=0;index<totalPages;index++){
		    String pagenum = String.valueOf(index+1);
		    pageList[index] = new SelectItem(pagenum,pagenum);
		}
		productDataModel.setWrappedData(productList);
		getProductPanel().setRendered(true);
	}


	public HtmlPanelGroup getProductPanel() {
		return productPanel;
	}
	public void setProductPanel(HtmlPanelGroup productPanel) {
		this.productPanel = productPanel;
	}
    
}

