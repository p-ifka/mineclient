package a.b.c;

import java.util.HashMap;
import java.util.ArrayList;

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
	public String name;
	public boolean enabled;
	
	public HashMap<String, ModuleConfigValue> nameMap;
	
	public ArrayList<Float> numberValues;
	public ArrayList<Range> rangeValues;
	public ArrayList<String> stringValues;
	public ArrayList<Boolean> booleanValues;

	public ModuleCfg()
	{
	    name = "";
	    enabled = false;
	    nameMap = new HashMap<String, ModuleConfigValue>();
	    numberValues = new ArrayList<Float>();
	    rangeValues = new ArrayList<Range>();
	    stringValues = new ArrayList<String>();
	    booleanValues = new ArrayList<Boolean>();
	}
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


    public static int addValue(ModuleCfg cfg, String name, boolean value)
    {
	int index = cfg.booleanValues.size();
	ModuleConfigValue valInfo = new ModuleConfigValue();
	valInfo.type = ModuleConfigType.BOOL;
	valInfo.index = index;
	cfg.booleanValues.add(value);
	cfg.nameMap.put(name, valInfo);
	return index;
    }

    
    public static int addValue(ModuleCfg cfg, String name, float value)
    {
	int index = cfg.numberValues.size();
	ModuleConfigValue valInfo = new ModuleConfigValue();
	valInfo.type = ModuleConfigType.NUMBER;
	valInfo.index = index;
	cfg.numberValues.add(value);
	cfg.nameMap.put(name, valInfo);
	return index;
    }

    public static int addValue(ModuleCfg cfg, String name, Range value)
    {
	int index = cfg.rangeValues.size();
	ModuleConfigValue valInfo = new ModuleConfigValue();
	valInfo.type = ModuleConfigType.RANGE;
	valInfo.index = index;
	cfg.rangeValues.add(value);
	cfg.nameMap.put(name, valInfo);
	return index;
    }
    
    public static int addValue(ModuleCfg cfg, String name, String value)
    {
	int index = cfg.stringValues.size();
	ModuleConfigValue valInfo = new ModuleConfigValue();
	valInfo.type = ModuleConfigType.STRING;
	valInfo.index = index;
	cfg.stringValues.add(value);
	cfg.nameMap.put(name, valInfo);
	return index;
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
		cfg.booleanValues.set(val.index, true);
	    } else if(ch == 'f' || ch == '0' || ch == 'n') {
		cfg.booleanValues.set(val.index, false);
	    } else {
		return false;
	    }
	    break;
	case NUMBER:
	    try
	    {
		cfg.numberValues.set(val.index, Float.parseFloat(value));
	    } catch(Exception e) {
		return false;
	    }
	    break;
	case STRING:
	    cfg.stringValues.set(val.index, value);
	    break;
	case RANGE:
	    Range rangeValue = stringToRange(value);
	    if(rangeValue != null) {
		cfg.rangeValues.set(val.index, rangeValue);
	    } else {
		return false;
	    }
	}
	return true;
    }

}
