package org.jrune.entity;

/**
 * conversion algorithm for double values.
 * @author lycis
 *
 */
class DoubleConversion implements IRunePropertyValueConversion<Double> {

    @Override
    public String toPropertyValue(Double value) {
	return value.toString();
    }

    @Override
    public Double fromPropertyValue(String value) throws IllegalArgumentException {
	try {
	    return Double.parseDouble(value);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException(e);
	}
    }

}
