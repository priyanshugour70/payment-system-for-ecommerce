package com.cpt.payments.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonUtils {
	
	private JsonUtils() {
	}
	
	private static final Gson gson = new Gson();
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static String toJsonString(Object obj) {
		if(obj == null) {
			return null;
		}
		
		return gson.toJson(obj);
	}
	
	public static JsonNode toJsonNode(Object obj) {
		if(obj == null) {
			return null;
		}
		
		gson.toJson(obj);
		
        JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(gson.toJson(obj));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return jsonNode;
	}

}
