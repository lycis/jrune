package org.jrune.entity;

/**
 * This interface describes a conversion algorithm for non-String objects
 * to String-based entity property values.
 * 
 * When you need a conversion of an object to a property value that is not
 * yet supported out of the box you can implement this interface. Afterwards
 * you need to register your conversion algorithm with the entity management
 * by calling @link{org.jrune.entity.RuneEntity#registerPropertyValueConversion}:
 * 
 * <code>
 * RuneEntity.registerPropertyValueConversion(MyObject.class, new MyObjecConversion());
 * </code>
 * @author lycis
 *
 * @param <T> type of the object you wish to convert from or to
 */
public interface IRunePropertyValueConversion<T> {
    
    /**
     * Convert the given object into a valid value for a property.
     * @param from object that should be converted
     * @return string representation of the value
     */
    public String toPropertyValue(T value);
    
    /**
     * Convert a property value (string format) to an object.
     * @param value string-based property value
     * @return object
     * @throws IllegalArgumentException when the value can not be parsed
     */
    public T fromPropertyValue(String value) throws IllegalArgumentException;
}
