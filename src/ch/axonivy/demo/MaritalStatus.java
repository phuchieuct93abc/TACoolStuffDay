package ch.axonivy.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MaritalStatus {
	
	SINGLE("SINGLE"),
	MARRIED("GET_MARRIED"),
	DIVOICED("ALREADY_DIVOICED");
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	MaritalStatus(String name){
		this.name = name;
	}
	
	private static Map<String, MaritalStatus> namesMap = new HashMap<String, MaritalStatus>(3);

    static {
        namesMap.put("single", SINGLE);
        namesMap.put("get_married", MARRIED);
        namesMap.put("divoiced", DIVOICED);
    }

    @JsonCreator
    public static MaritalStatus forValue(String value) {
        return namesMap.get(StringUtils.lowerCase(value));
    }

    @JsonValue
    public String toValue() {
        for (Entry<String, MaritalStatus> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }
}
