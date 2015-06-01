package org.jrune.entity;

/**
 * Conversion algorithm for integer values.
 * 
 * @author lycis
 *
 */
class IntegerConversion implements IRunePropertyValueConversion<Integer> {

    @Override
    public String toPropertyValue(Integer value) {
	return value.toString();
    }

    @Override
    public Integer fromPropertyValue(String value) throws IllegalArgumentException{
	try {
	    return Integer.parseInt(value);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException(e);
	}
    }

}
