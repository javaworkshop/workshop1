package org.workshop1.dao;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.workshop1.model.Data;
import org.workshop1.model.Klant;

class DataConverter implements Converter {

    @Override
    public boolean canConvert(Class object) {
            return Data.class.isAssignableFrom(object);
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
                else {
                    Object obj = getAttributeMethod.invoke(value, null);
                    if(obj == null || obj.equals(0))
                        writer.addAttribute("xsi:nil", "true");
                    else
                        writer.setValue(obj.toString());
                }
            }
            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                writer.setValue("!!!Exception occurred while marshalling!!!");
            }
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String className = reader.getNodeName();
        className = "org.workshop1.model." + Character.toUpperCase(className.charAt(0)) 
                + className.substring(1);
        Object dataObject;
        try {
            Class clazz = Class.forName(className);
            dataObject = clazz.newInstance();
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            return null;
        }
        while(reader.hasMoreChildren()) {
            reader.moveDown();
            // TODO
            reader.moveUp();
        }
        return dataObject;
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
