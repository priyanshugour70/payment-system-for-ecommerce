package com.cpt.payments;

import com.cpt.payments.pojo.response.error.ErrorData;
import com.cpt.payments.pojo.response.error.ErrorDetails;
import com.cpt.payments.pojo.response.error.ErrorWrapper;
import com.cpt.payments.pojo.response.error.TrustlyErrorResponse;
import com.cpt.payments.util.JsonUtils;

public class TestMain {

	public static void main(String[] args) {

		/*
		 {
			    "version": "1.1",
			    "error": {
			        "name": "JSONRPCError",
			        "code": 616,
			        "message": "ERROR_INVALID_CREDENTIALS",
			        "error": {
			            "signature": "R9+hjuMqbsH0Ku ... S16VbzRsw==",
			            "uuid": "258a2184-2842-b485-25ca-293525152425",
			            "method": "Deposit",
			            "data": {
			                "code" : 616,
			                "message" : "ERROR_INVALID_CREDENTIALS"
			            }
			        }
			}
		 */

		/*
		 * Attributes attributes = Attributes.builder()
				.country("LT")
				.locale("en")
				.build();

		Data data = Data.builder()
				.username("CTPuser")
				.attributes(attributes)
				.build();
		 */



		ErrorData errorData = ErrorData.builder()
				.code("616")
				.message("ERROR_INVALID_CREDENTIALS")
				.build();
		ErrorDetails errorDetails = ErrorDetails.builder()
				.signature(null)
				.uuid("258a2184-2842-b485-25ca-293525152425")
				.signature("R9+hjuMqbsH0Ku ... S16VbzRsw==")
				.method("Deposit")
				.data(errorData )
				.build();
		ErrorWrapper errorWrapper = ErrorWrapper.builder()
				.name("JSONRPCError")
				.code("616")
				.message("ERROR_INVALID_CREDENTIALS")
				.error(errorDetails)
				.build();
		TrustlyErrorResponse errorRes = TrustlyErrorResponse.builder()
				.version("1.1")
				.error(errorWrapper )
				.build(); 

		System.out.println(JsonUtils.toJsonString(errorRes));

	}

}
