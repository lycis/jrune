package org.jrune.entity;

/**
 * Conversion algorithm for Strings.
 * 
 * @author lycis
 *
 */
class StringConversion implements IRunePropertyValueConversion<String> {

    @Override
    public String toPropertyValue(String value) {
	return value;
    }

    @Override
    public String fromPropertyValue(String value) {
	return value;
    }

}
