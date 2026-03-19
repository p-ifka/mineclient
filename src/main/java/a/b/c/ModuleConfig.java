package a.b.c;

import java.util.HashMap;

public class ModuleConfig
{
    public static class Range
    {
	public float lower;
	public float upper;
    }

    public enum ModuleConfigType
    {
	BOOL, NUMBER, RANGE, STRING
    }

    public static class ModuleConfigValue
    {
	public ModuleConfigType type;
	public int index;
    }

    public static class ModuleCfg
    {
	public HashMap<String, ModuleConfigValue> nameMap;
	public float[] numberValues;
	public Range[] rangeValues;
	public String[] stringValues;
	public boolean[] booleanValues;
    }

    public static Range stringToRange(String str)
    {
	String[] parts;
	Range result;

	parts = str.split("-");
	result = new Range();
	if(parts.length < 2) {
	    return null;
	} else {
	    try
	    {
		result.lower = Float.parseFloat(parts[0]);
		result.upper = Float.parseFloat(parts[1]);
		return result;
	    } catch(Exception e){
		return null;
	    }
	}
    }

    public static boolean setValue(String name, String value, ModuleCfg cfg)
    {
	if(value == null || value.length() < 1) {
	    return false;
	}
	ModuleConfigValue val;
	
	val = cfg.nameMap.get(name);
	if(val == null) { return false; }
	switch(val.type) {
	case BOOL:
	    char ch = value.toLowerCase().charAt(0);
	    if(ch == 't' || ch == '1' || ch == 'y') {
		cfg.booleanValues[val.index] = true;
	    } else if(ch == 'f' || ch == '0' || ch == 'n') {
		cfg.booleanValues[val.index] = false;
	    } else {
		return false;
	    }
	    break;
	case NUMBER:
	    try
	    {
		cfg.numberValues[val.index] = Float.parseFloat(value);
	    } catch(Exception e) {
		return false;
	    }
	    break;
	case STRING:
	    cfg.stringValues[val.index] = value;
	    break;
	case RANGE:
	    Range rangeValue = stringToRange(value);
	    if(rangeValue != null) {
		cfg.rangeValues[val.index] = rangeValue;
	    } else {
		return false;
	    }
	}
	return true;
    }

}
