package com.iwe.avengers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.dynamodb.entity.Avenger;
import com.iwe.avenger.lambda.response.HandlerResponse;
import com.iwe.avengers.exception.AvengerNotFoundException;

public class UpdateAvengersHandler implements RequestHandler<Avenger, HandlerResponse> {

	private AvengerDAO dao = new AvengerDAO();
	
	@Override
	public HandlerResponse handleRequest(final Avenger avenger, final Context context) {
		
		Avenger updatedAvenger = null;
		
		context.getLogger().log("[#] - Searching avenger by id: " + avenger.getId());
		
		final Avenger avengerFound = dao.search(avenger.getId());
		if(avengerFound == null) {
            throw new AvengerNotFoundException("[NotFound] - Avenger id: "
                    + avenger.getId());
        }
		
		
		context.getLogger().log("[#] Avenger found, updating...");
		
		updatedAvenger = dao.update(avenger);
		
		context.getLogger().log("[#] Avenger successfully updated");
		
		
		final HandlerResponse response = HandlerResponse.builder().setObjectBody(updatedAvenger).build();
		
		return response;
		
	}
}
