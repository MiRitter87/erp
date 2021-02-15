package backend.tools.test;

import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;

/**
 * Provides convenience methods for testing of SOAP WebServices.
 * 
 * @author Michael
 */
public class SoapTestTools {
	/**
	 * Checks if a WebServiceResult contains an error message.
	 * 
	 * @param result The WebServiceResult.
	 * @return true, if error messages are contained; false otherwise.
	 */
	public static boolean resultContainsErrorMessage(final WebServiceResult result) {
		for(WebServiceMessage message:result.getMessages()) {
			if(message.getType() == WebServiceMessageType.E)
				return true;
		}
		
		return false;
	}
}
