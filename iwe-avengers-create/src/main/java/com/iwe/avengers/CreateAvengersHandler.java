package com.iwe.avengers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.dynamodb.entity.Avenger;
import com.iwe.avenger.lambda.response.HandlerResponse;

public class CreateAvengersHandler implements RequestHandler<Avenger, HandlerResponse> {
	
	private AvengerDAO dao = new AvengerDAO();
	
	@Override
	public HandlerResponse handleRequest(final Avenger newAvenger, final Context context) {
		context.getLogger().log("[#] - Creating avenger...");
		
		final Avenger createdAvenger = dao.merge(newAvenger);
		
		context.getLogger().log("[#] - Avenger created!");
		
		return HandlerResponse.builder().setObjectBody(createdAvenger).build();
	}
}
