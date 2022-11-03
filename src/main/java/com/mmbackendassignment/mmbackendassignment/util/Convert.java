package com.mmbackendassignment.mmbackendassignment.util;

import java.lang.reflect.*;
import java.util.Hashtable;

public class Convert {

    public static Object objects( Object objA, Object objB )  {
        if (objA == null) return null;

        Hashtable<String, Type> fieldsA = getFieldTypes( objA );

        for (Field f : objB.getClass().getDeclaredFields()) {
            // If fieldName exist in other object
            if ( fieldsA.get( f.getName() ) != null ) {

                Object value = null;
                try {
                    value = getFieldValue(objA, f.getName());
                }catch( NoSuchFieldException | IllegalAccessException e){
                    System.out.println(e);
                }

                switch ( getModifier(f) ) {
                    case "public" -> {
                        setPublicValue( fieldsA, f, objB, value );
                    }
                    case "private" -> {
                        setPrivateValue( f, objB, value );
                    }
                    case "protected" -> System.out.println("Property is protected");
                    default -> System.out.println("Property has no modifier");
                }
                break;
            }
        }

        return objB;
    }

    private static Object getFieldValue( Object obj, String fieldName ) throws NoSuchFieldException, IllegalAccessException {
        Field valueField = obj.getClass().getDeclaredField( fieldName );
        valueField.setAccessible(true);

        return valueField.get(obj);
    }

    @SuppressWarnings("rawtypes")
    private static void setPublicValue( Hashtable<String, Type> objFields, Field f,  Object obj, Object value) {
        if (value == null) return;

        Class bigClass = genericTypeToClass( f.getGenericType(), value, false);
        if ( !bigClass.equals( value.getClass()) ) return;

        String fieldType = objFields.get(f.getName()).toString();

        try {
            switch ( fieldType ) {
                case "int"      -> f.setInt(obj, (int) value);
                case "long"     -> f.setLong(obj, (long) value);
                case "float"    -> f.setFloat(obj, (float) value);
                case "double"   -> f.setDouble(obj, (double) value);
                case "boolean"  -> f.setBoolean(obj, (boolean) value);
                case "short"    -> f.setShort(obj, (short) value);
                case "byte"     -> f.setByte(obj, (byte) value);
                case "char"     -> f.setChar(obj, (char) value);
                default -> {if (f.getGenericType().equals( value.getClass() )) f.set(obj, value);}
            }
        }catch( IllegalAccessException e ){
            System.out.println(e);
        }
    }

    @SuppressWarnings("rawtypes")
    private static void setPrivateValue( Field f, Object obj, Object value ) {
        if (value == null) return;

        Class bigClass = genericTypeToClass( f.getGenericType(), value, false);
        if ( !bigClass.equals( value.getClass()) ) return;

        String methodName = "set" + f.getName().substring(0,1).toUpperCase() + f.getName().substring(1);

        try {
            Class[] args = new Class[1];
            args[0] = genericTypeToClass( f.getGenericType(), value,true);
            Method setMethod = obj.getClass().getMethod(methodName, args);

            setMethod.invoke( obj, value );
        }catch( NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            System.out.println(e);
        }
    }

    @SuppressWarnings("rawtypes")
    private static Class genericTypeToClass( Object genericType, Object value, boolean asPrimitive ){

        return switch ( genericType.toString() ) {
            case "int"      -> asPrimitive ? int.class : Integer.class;
            case "long"     -> asPrimitive ? long.class : Long.class;
            case "float"    -> asPrimitive ? float.class : Float.class;
            case "double"   -> asPrimitive ? double.class : Double.class;
            case "boolean"  -> asPrimitive ? boolean.class : Boolean.class;
            case "short"    -> asPrimitive ? short.class : Short.class;
            case "byte"     -> asPrimitive ? byte.class : Byte.class;
            case "char"     -> asPrimitive ? char.class : Character.class;
            default         -> value.getClass();
        };
    }

    private static Hashtable<String, Type> getFieldTypes (Object obj ){
        Hashtable<String, Type> fieldTable = new Hashtable<>();

        for (Field f : obj.getClass().getDeclaredFields()) {
            fieldTable.put( f.getName(), f.getGenericType() );
        }

        return fieldTable;
    }

    private static String getModifier( Field f ){
        int modifier = f.getModifiers();
        return Modifier.toString( modifier );
    }
}
