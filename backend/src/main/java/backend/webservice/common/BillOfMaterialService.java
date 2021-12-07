package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.BillOfMaterialDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.exception.DuplicateIdentifierException;
import backend.exception.EntityExistsException;
import backend.exception.NoItemsException;
import backend.exception.ObjectUnchangedException;
import backend.model.billOfMaterial.BillOfMaterial;
import backend.model.billOfMaterial.BillOfMaterialArray;
import backend.model.billOfMaterial.BillOfMaterialItem;
import backend.model.billOfMaterial.BillOfMaterialItemWS;
import backend.model.billOfMaterial.BillOfMaterialWS;
import backend.model.material.Material;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;

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
	
	
	/**
	 * Provides a list of all BillOfMaterials.
	 * 
	 * @param material The material on which the BillOfMaterial is based.
	 * @return A list of all purchase orders.
	 */
	public WebServiceResult getBillOfMaterials(final Material material) {
		BillOfMaterialArray billOfMaterials = new BillOfMaterialArray();
		WebServiceResult getBillOfMaterialsResult = new WebServiceResult(null);
		
		try {
			billOfMaterials.setBillOfMaterials(this.billOfMaterialDao.getBillOfMaterials(material));
			getBillOfMaterialsResult.setData(billOfMaterials);
		} catch (Exception e) {
			getBillOfMaterialsResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("billOfMaterial.getBillOfMaterialsError")));
			
			logger.error(this.resources.getString("billOfMaterial.getBillOfMaterialsError"), e);
		}
		
		return getBillOfMaterialsResult;
	}
	
	
	/**
	 * Deletes the BillOfMaterial with the given id.
	 * 
	 * @param id The id of the BillOfMaterial to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteBillOfMaterial(final Integer id) {
		BillOfMaterial billOfMaterial = null;
		WebServiceResult deleteBillOfMaterialResult = new WebServiceResult(null);
		
		//Check if a BillOfMaterial with the given ID exists.
		try {
			billOfMaterial = this.billOfMaterialDao.getBillOfMaterial(id);
			
			if(billOfMaterial != null) {
				//Delete BillOfMaterial if exists.
				this.billOfMaterialDao.deleteBillOfMaterial(billOfMaterial);
				deleteBillOfMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
						MessageFormat.format(this.resources.getString("billOfMaterial.deleteSuccess"), id)));
			}
			else {
				//BillOfMaterial not found.
				deleteBillOfMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("billOfMaterial.notFound"), id)));
			}
		}
		catch(Exception e) {
			deleteBillOfMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("billOfMaterial.deleteError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("billOfMaterial.deleteError"), id), e);
		}
		
		return deleteBillOfMaterialResult;
	}
	
	
	/**
	 * Updates an existing BillOfMaterial.
	 * 
	 * @param billOfMaterial The BillOfMaterial to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateBillOfMaterial(final BillOfMaterialWS billOfMaterial) {
		BillOfMaterial convertedBillOfMaterial = new BillOfMaterial();
		WebServiceResult updateBillOfMaterialResult = new WebServiceResult();
		
		try {
			convertedBillOfMaterial = this.convertBillOfMaterial(billOfMaterial);
		}
		catch(Exception exception) {
			updateBillOfMaterialResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, MessageFormat.format(this.resources.getString("billOfMaterial.updateError"), convertedBillOfMaterial.getId())));	
			logger.error(MessageFormat.format(this.resources.getString("billOfMaterial.updateError"), convertedBillOfMaterial.getId()), exception);
			return updateBillOfMaterialResult;
		}
		
		updateBillOfMaterialResult = this.validate(convertedBillOfMaterial);
		if(WebServiceTools.resultContainsErrorMessage(updateBillOfMaterialResult)) {
			return updateBillOfMaterialResult;
		}
		
		updateBillOfMaterialResult = this.update(convertedBillOfMaterial, updateBillOfMaterialResult);
		
		return updateBillOfMaterialResult;
	}
	
	
	/**
	 * Adds a BillOfMaterial.
	 * 
	 * @param billOfMaterial The BillOfMaterial to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addBillOfMaterial(final BillOfMaterialWS billOfMaterial) {
		BillOfMaterial convertedBillOfMaterial = new BillOfMaterial();
		WebServiceResult addBillOfMaterialResult = new WebServiceResult();
		
		try {
			convertedBillOfMaterial = this.convertBillOfMaterial(billOfMaterial);
		}
		catch(Exception exception) {
			addBillOfMaterialResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("billOfMaterial.addError")));	
			logger.error(this.resources.getString("billOfMaterial.addError"), exception);
			return addBillOfMaterialResult;
		}
		
		addBillOfMaterialResult = this.validate(convertedBillOfMaterial);
		if(WebServiceTools.resultContainsErrorMessage(addBillOfMaterialResult)) {
			return addBillOfMaterialResult;
		}
		
		addBillOfMaterialResult = this.add(convertedBillOfMaterial, addBillOfMaterialResult);
		addBillOfMaterialResult.setData(convertedBillOfMaterial.getId());
		
		return addBillOfMaterialResult;
	}
	
	
	/**
	 * Converts the lean BillOfMaterial representation that is provided by the WebService to the internal data model for further processing.
	 * 
	 * @param billOfMaterialWS The lean BillOfMaterial representation provided by the WebService.
	 * @return 
	 * @return The BillOfMaterial model that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private BillOfMaterial convertBillOfMaterial(final BillOfMaterialWS billOfMaterialWS) throws Exception {
		BillOfMaterial convertedBillOfMaterial;
		
		convertedBillOfMaterial = this.convertBillOfMaterialHead(billOfMaterialWS);
		convertedBillOfMaterial.setItems(this.convertBillOfMaterialItems(billOfMaterialWS, convertedBillOfMaterial));
		
		return convertedBillOfMaterial;
	}
	
	
	/**
	 * Converts the head data of the BillOfMaterial from the lean WebService representation to the internal data model of the backend.
	 * 
	 * @param billOfMaterialWS The lean BillOfMaterial representation provided by the WebService.
	 * @return The BillOfMaterial model that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private BillOfMaterial convertBillOfMaterialHead(final BillOfMaterialWS billOfMaterialWS) throws Exception {
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		BillOfMaterial billOfMaterial = new BillOfMaterial();
		
		//Basic object data that are copied as-is.
		billOfMaterial.setId(billOfMaterialWS.getBillOfMaterialId());
		billOfMaterial.setName(billOfMaterialWS.getName());
		billOfMaterial.setDescription(billOfMaterialWS.getDescription());
		
		//Object references. Only the ID is given and the whole backend object has to be loaded and referenced.
		if(billOfMaterialWS.getMaterialId() != null)
			billOfMaterial.setMaterial(materialDAO.getMaterial(billOfMaterialWS.getMaterialId()));
		
		return billOfMaterial;
	}
	
	
	/**
	 * Converts the item data of the BillOfMaterial from the lean WebService representation to the internal data model of the backend.
	 * 
	 * @param billOfMaterialWS The lean BillOfMaterial representation provided by the WebService.
	 * @param billOfMaterial The converted BillOfMaterial that is build based on the WebService representation.
	 * @return A list of item models that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private List<BillOfMaterialItem> convertBillOfMaterialItems(final BillOfMaterialWS billOfMaterialWS, final BillOfMaterial billOfMaterial) throws Exception {
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		List<BillOfMaterialItem> billOfMaterialItems = new ArrayList<BillOfMaterialItem>();
		
		for(BillOfMaterialItemWS itemWS:billOfMaterialWS.getItems()) {
			BillOfMaterialItem billOfMaterialItem = new BillOfMaterialItem();
			
			billOfMaterialItem.setId(itemWS.getItemId());
			billOfMaterialItem.setBillOfMaterial(billOfMaterial);
			billOfMaterialItem.setMaterial(materialDAO.getMaterial(itemWS.getMaterialId()));
			billOfMaterialItem.setQuantity(itemWS.getQuantity());
			
			billOfMaterialItems.add(billOfMaterialItem);
		}
		
		return billOfMaterialItems;
	}
	
	
	/**
	 * Validates the BillOfMaterial.
	 * 
	 * @param billOfMaterial The BillOfMaterial to be validated.
	 * @return The result of the validation.
	 */
	private WebServiceResult validate(final BillOfMaterial billOfMaterial) {
		WebServiceResult webServiceResult = new WebServiceResult(null);
		
		try {
			billOfMaterial.validate();
		} 
		catch(NoItemsException noItemsException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, this.resources.getString("billOfMaterial.noItemsGiven")));
			return webServiceResult;
		}
		catch(DuplicateIdentifierException duplicateIdentifierException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("billOfMaterial.duplicateItemKey"), billOfMaterial.getId(), 
							duplicateIdentifierException.getDuplicateIdentifier())));
			return webServiceResult;
		}
		catch (Exception validationException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
			return webServiceResult;
		}
		
		return webServiceResult;
	}
	
	
	/**
	 * Updates the given BillOfMaterial.
	 * 
	 * @param billOfMaterial The BillOfMaterial to be updated.
	 * @param webServiceResult The WebServiceResult to which messages are added.
	 * @return The result of the update function.
	 */
	private WebServiceResult update(final BillOfMaterial billOfMaterial, WebServiceResult webServiceResult) {
		try {
			this.billOfMaterialDao.updateBillOfMaterial(billOfMaterial);
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
					MessageFormat.format(this.resources.getString("billOfMaterial.updateSuccess"), billOfMaterial.getId())));
		} 
		catch(ObjectUnchangedException objectUnchangedException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.I, 
					MessageFormat.format(this.resources.getString("billOfMaterial.updateUnchanged"), billOfMaterial.getId())));
		}
		catch(EntityExistsException entityExistsException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("billOfMaterial.BomForMaterialExists"), 
							billOfMaterial.getMaterial().getId(), entityExistsException.getId())));
		}
		catch (Exception e) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("billOfMaterial.updateError"), billOfMaterial.getId())));
			
			logger.error(MessageFormat.format(this.resources.getString("billOfMaterial.updateError"), billOfMaterial.getId()), e);
		}
		
		return webServiceResult;
	}
	
	
	/**
	 * Inserts the given BillOfMaterial.
	 * 
	 * @param billOfMaterial The BillOfMaterial to be inserted.
	 * @param webServiceResult The WebServiceResult to which messages are added.
	 * @return The result of the insert function.
	 */
	private WebServiceResult add(final BillOfMaterial billOfMaterial, WebServiceResult webServiceResult) {		
		try {
			this.billOfMaterialDao.insertBillOfMaterial(billOfMaterial);
			webServiceResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.S, this.resources.getString("billOfMaterial.addSuccess")));			
		} 
		catch(EntityExistsException entityExistsException) {
			webServiceResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, MessageFormat.format(this.resources.getString("billOfMaterial.BomForMaterialExists"), 
							billOfMaterial.getMaterial().getId(), entityExistsException.getId())));
		}
		catch (Exception exception) {
			webServiceResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("billOfMaterial.addError")));
			
			logger.error(this.resources.getString("billOfMaterial.addError"), exception);
		}
		
		return webServiceResult;
	}
}
