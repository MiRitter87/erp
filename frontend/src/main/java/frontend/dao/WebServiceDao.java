package frontend.dao;

import frontend.generated.ws.soap.employee.WebServiceMessage;
import frontend.generated.ws.soap.employee.WebServiceMessageType;
import frontend.generated.ws.soap.employee.WebServiceResult.Messages;

/**
 * Data Access Object providing functionality that is used by all WebService DAOs.
 *
 * @author Michael
 */
public class WebServiceDao {
    /**
     * Raises an exception in case the employee WebService result contains an error message.
     *
     * @param messages A list of messages returned by the employee WebService.
     * @throws Exception The exception containing error information.
     */
    protected void raiseExceptionForErrors(final Messages messages) throws Exception {
        for (WebServiceMessage webServiceMessage : messages.getMessage()) {
            if (webServiceMessage.getType() == WebServiceMessageType.E) {
                throw new Exception(webServiceMessage.getText());
            }
        }
    }

    /**
     * Raises an exception in case the department WebService result contains an error message.
     *
     * @param messages A list of messages returned by the department WebService.
     * @throws Exception The exception containing error information.
     */
    protected void raiseExceptionForErrors(
            final frontend.generated.ws.soap.department.WebServiceResult.Messages messages) throws Exception {
        for (frontend.generated.ws.soap.department.WebServiceMessage webServiceMessage : messages.getMessage()) {
            if (webServiceMessage.getType() == frontend.generated.ws.soap.department.WebServiceMessageType.E) {
                throw new Exception(webServiceMessage.getText());
            }
        }
    }
}
