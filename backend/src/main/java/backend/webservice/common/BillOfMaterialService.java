package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.BillOfMaterialDao;
import backend.dao.DAOManager;
import backend.model.billOfMaterial.BillOfMaterial;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the BillOfMaterial WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 */
public class BillOfMaterialService {
	/**
	 * DAO for BillOfMaterial access.
	 */
	private BillOfMaterialDao billOfMaterialDao;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(BillOfMaterialService.class);
	
	
	/**
	 * Initializes the BillOfMaterial service.
	 */
	public BillOfMaterialService() {
		this.billOfMaterialDao = DAOManager.getInstance().getBillOfMaterialDAO();
	}
	
	
	/**
	 * Provides the BillOfMaterial with the given id.
	 * 
	 * @param id The id of the BillOfMaterial.
	 * @return The BillOfMaterial with the given id, if found.
	 */
	public WebServiceResult getBillOfMaterial(final Integer id) {
		BillOfMaterial billOfMaterial = null;
		WebServiceResult getBillOfMaterialResult = new WebServiceResult(null);
		
		try {
			billOfMaterial = this.billOfMaterialDao.getBillOfMaterial(id);
			
			if(billOfMaterial != null) {
				//BillOfMaterial found
				getBillOfMaterialResult.setData(billOfMaterial);
			}
			else {
				//BillOfMaterial not found
				getBillOfMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("billOfMaterial.notFound"), id)));
			}
		}
		catch (Exception e) {
			getBillOfMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("billOfMaterial.getError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("billOfMaterial.getError"), id), e);
		}
		
		return getBillOfMaterialResult;
	}
}
