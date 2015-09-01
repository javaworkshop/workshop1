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

class DataConverter implements Converter {

    @Override
    public boolean canConvert(Class object) {
        return Data.class.isAssignableFrom(object);
    }

    @Override
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        // determine class of value
        Class valueClass = value.getClass();
        // get the fields that belong to value
        Field[] fields = valueClass.getDeclaredFields();
        for(Field f : fields) {
            // create one node for every field
            writer.startNode(f.getName());
            try {
                String getAttribute = "get" + Character.toUpperCase(f.getName().charAt(0)) 
                        + f.getName().substring(1);
                // get getter method for current field
                Method getAttributeMethod = valueClass.getMethod(getAttribute, null);
                // if current field is a Data class, create child node(s)
                if(Data.class.isAssignableFrom(f.getType()))
                    context.convertAnother(getAttributeMethod.invoke(value, null));
                // else write value to current node
                else {
                    Object obj = getAttributeMethod.invoke(value, null);
                    if(obj == null || obj.equals(0)) {
                        writer.setValue("");
                    }
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
        // get name of the class that is represented by the current node
        String className = reader.getNodeName();
        // make instance of this class
        Object dataObject = makeInstance(className, "org.workshop1.model");
        while(reader.hasMoreChildren()) {
            // move reader down to child node
            reader.moveDown();
            
            String attributeName = reader.getNodeName();
            String setAttribute = "set" + Character.toUpperCase(attributeName.charAt(0)) 
                    + attributeName.substring(1);
            String value = reader.getValue();
            if(value.equals("")) {
                // move reader up to parent node, cursor moves forward
                reader.moveUp();
                continue;
            }
             
            try {           
                Method setAttributeMethod;
                // if current node has child nodes and thus represents a Data class
                if(reader.hasMoreChildren()) {
                    Object fieldObject = makeInstance(attributeName, "org.workshop1.model");
                    // read child nodes of current node and store values in fieldObject
                    fieldObject = context.convertAnother(dataObject, fieldObject.getClass());
                    setAttributeMethod = dataObject.getClass().getMethod(setAttribute, 
                            new Class[]{fieldObject.getClass()});
                    // call setter method of instance of parent node class and pass instance of
                    // current node class as parameter
                    setAttributeMethod.invoke(dataObject, fieldObject);
                }
                // else if current node represents simple integer, double, or String attribute
                else {                    
                    if(isInteger(value)) {
                        setAttributeMethod = dataObject.getClass().getMethod(setAttribute, 
                                new Class[]{int.class});
                        setAttributeMethod.invoke(dataObject, Integer.parseInt(value));
                    }
                    else if(isDouble(value)) {
                        setAttributeMethod = dataObject.getClass().getMethod(setAttribute, 
                                new Class[]{double.class});
                        setAttributeMethod.invoke(dataObject, Double.parseDouble(value));
                    }
                    else {
                        setAttributeMethod = dataObject.getClass().getMethod(setAttribute, 
                                new Class[]{String.class});
                        setAttributeMethod.invoke(dataObject, value);
                    }
                }
            }
            catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {}
            
            // move reader up to parent node, cursor moves forward
            reader.moveUp();
        }
        return dataObject;
    }
    
    private Object makeInstance(String className, String packageName) {
        String fullClassName = packageName + '.' + Character.toUpperCase(className.charAt(0)) 
                + className.substring(1);
        Object dataObject;
        try {
            Class clazz = Class.forName(fullClassName);
            dataObject = clazz.newInstance();
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            return null;
        }
        return dataObject;
    }
    
    private boolean isInteger(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }
    
    private boolean isDouble(String s) {
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
