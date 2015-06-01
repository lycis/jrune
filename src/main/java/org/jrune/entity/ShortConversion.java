package org.jrune.entity;

/**
 * conversion algorithm for short values.
 * @author lycis
 *
 */
class ShortConversion implements IRunePropertyValueConversion<Short> {

    @Override
    public String toPropertyValue(Short value) {
	return value.toString();
    }

    @Override
    public Short fromPropertyValue(String value) throws IllegalArgumentException {
	try {
	    return Short.parseShort(value);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException(e);
	}
    }

}
