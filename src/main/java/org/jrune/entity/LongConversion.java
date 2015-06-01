package org.jrune.entity;

/**
 * conversion algorithm for long values.
 * @author lycis
 *
 */
class LongConversion implements IRunePropertyValueConversion<Long> {

    @Override
    public String toPropertyValue(Long value) {
	return value.toString();
    }

    @Override
    public Long fromPropertyValue(String value) throws IllegalArgumentException {
	try {
	    return Long.parseLong(value);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException(e);
	}
    }

}
