package org.workshop1.dao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.workshop1.model.Data;

public class DataConverter implements Converter {

    @Override
    public boolean canConvert(Class object) {
            return object.equals(Data.class);
        }

    @Override
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {        
        Class valueClass = value.getClass();
        Field[] fields = valueClass.getDeclaredFields();
        for(Field f : fields) {
            writer.startNode(f.getName());
            try {
            String getAttribute = "get" + Character.toUpperCase(f.getName().charAt(0)) 
                    + f.getName().substring(1);
            Method getAttributeMethod = valueClass.getMethod(getAttribute, null);
            if(Data.class.isAssignableFrom(f.getType()))
                context.convertAnother(getAttributeMethod.invoke(value, null));
            else
                writer.setValue(getAttributeMethod.invoke(value, null).toString());
            }
            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {}
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean isInteger(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }
    
    public boolean isDouble(String s) {
        //DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        //char decSep = dfs.getDecimalSeparator();
        char decSep = '.';
        int nrSeparators = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == decSep) {
                nrSeparators++;
                if(nrSeparators > 1)
                    return false;
            }
            else if(!Character.isDigit(s.charAt(i))) {
                    return false;
            }
        }
        return true;
    }
    
}
