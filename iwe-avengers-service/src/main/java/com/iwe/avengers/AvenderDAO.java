package com.iwe.avengers;

import java.util.HashMap;
import java.util.Map;

import com.iwe.avenger.dynamodb.entity.Avenger;

public class AvenderDAO {

	private Map<String, Avenger> mapper = new HashMap<>();
	
	
	public AvenderDAO() {
		mapper.put("aaaa-bbbb-dddd-cccc", new Avenger("aaaa-bbbb-dddd-cccc", "Captain America", "Stever Rogers"));		
		mapper.put("aaaa-aaaa-aaaa-aaaa", new Avenger("aaaa-aaaa-aaaa-aaaa", "Hulk", "Bruce Banner"));
	}
	
	public Avenger search(String id) {
		return mapper.get(id);
	}

}
