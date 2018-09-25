package com.iwe.avengers;

import java.util.HashMap;
import java.util.Map;

import com.iwe.avenger.dynamodb.entity.Avenger;

public class AvengerDAO {

	private Map<String, Avenger> mapper = new HashMap<>();
	
	public AvengerDAO() {
		mapper.put("aaaa-bbbb-cccc-dddd", new Avenger("aaaa-bbbb-dddd-cccc", "Captain America", "Stever Rogers"));		
		mapper.put("aaaa-aaaa-aaaa-aaaa", new Avenger("aaaa-aaaa-aaaa-aaaa", "Hulk", "Bruce Banner"));
	}
	
	public Avenger search(String id) {
		return mapper.get(id);
	}

	public Avenger update(Avenger avenger) {
		return mapper.replace(avenger.getId(), avenger);		
	}
	
	public Avenger create(Avenger avenger) {

		String id = "IssoFoiGerado";
		avenger.setId(id);
		mapper.put(id, avenger);
		return avenger;

	}

	public void remove(Avenger avenger) {
		mapper.remove(avenger.getId());
	}

}
