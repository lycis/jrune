package org.jrune.entity;

/**
 * Conversion algorithm for Boolean values.
 * 
 * @author lycis
 *
 */
class BooleanConversion implements IRunePropertyValueConversion<Boolean> {
    
    private final static String VALUE_TRUE = "true";
    private final static String VALUE_FALSE = "false";

    @Override
    public String toPropertyValue(Boolean value) {
	if(value.booleanValue()) {
	    return VALUE_TRUE;
	}
	
	return VALUE_FALSE;
    }

    @Override
    public Boolean fromPropertyValue(String value) {
	if(VALUE_TRUE.equalsIgnoreCase(value)) {
	    return true;
	} else if(VALUE_FALSE.equalsIgnoreCase(value)) {
	    return false;
	}
	
	throw new IllegalArgumentException(value + " is not boolean");
    }

}
