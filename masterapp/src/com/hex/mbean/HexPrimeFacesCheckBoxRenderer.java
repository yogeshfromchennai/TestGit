package com.hex.mbean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public class HexPrimeFacesCheckBoxRenderer extends
		com.sun.faces.renderkit.html_basic.CheckboxRenderer {

	public Object getConvertedValue(FacesContext context,
			UIComponent component, Object submittedValue)
			throws ConverterException {
              
                if("delete".equalsIgnoreCase(component.getId()))
                    return super.getConvertedValue(context, component, submittedValue);
                else
                    return submittedValue;            
	}

}
