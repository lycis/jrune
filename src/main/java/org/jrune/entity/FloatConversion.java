package org.jrune.entity;

/**
 * conversion algorithm for float values.
 * @author lycis
 *
 */
class FloatConversion implements IRunePropertyValueConversion<Float> {

    @Override
    public String toPropertyValue(Float value) {
	return value.toString();
    }

    @Override
    public Float fromPropertyValue(String value) throws IllegalArgumentException {
	try {
	    return Float.parseFloat(value);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException(e);
	}
    }

}
