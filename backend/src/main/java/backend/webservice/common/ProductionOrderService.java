package backend.webservice.common;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;
import backend.dao.ProductionOrderDao;

/**
 * Common implementation of the production order WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 *
 */
public class ProductionOrderService {
	/**
	 * DAO for production order access.
	 */
	private ProductionOrderDao productionOrderDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(ProductionOrderService.class);
	
	
	/**
	 * Initializes the production order service.
	 */
	public ProductionOrderService() {
		this.productionOrderDAO = DAOManager.getInstance().getProductionOrderDAO();
	}
}
