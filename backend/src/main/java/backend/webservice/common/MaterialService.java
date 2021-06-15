package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;
import backend.dao.ImageDao;
import backend.dao.MaterialDao;
import backend.exception.ObjectUnchangedException;
import backend.model.Material;
import backend.model.MaterialArray;
import backend.model.webservice.MaterialWS;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the Material WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 */
public class MaterialService {
	/**
	 * DAO for material access.
	 */
	private MaterialDao materialDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(MaterialService.class);
	
	
	/**
	 * Provides the material with the given id.
	 * 
	 * @param id The id of the material.
	 * @return The material with the given id, if found.
	 */
	public WebServiceResult getMaterial(final Integer id) {
		Material material = null;
		WebServiceResult getMaterialResult = new WebServiceResult(null);
		
		try {
			this.materialDAO = DAOManager.getInstance().getMaterialDAO();
			material = this.materialDAO.getMaterial(id);
			
			if(material != null) {
				//Material found
				getMaterialResult.setData(material);
			}
			else {
				//Material not found
				getMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("material.notFound"), id)));
			}
		}
		catch (Exception e) {
			getMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("material.getError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("material.getError"), id), e);
		}
		
		return getMaterialResult;
	}
	
	
	/**
	 * Provides a list of all materials.
	 * 
	 * @return A list of all materials.
	 */
	public WebServiceResult getMaterials() {
		MaterialArray materials = new MaterialArray();
		WebServiceResult getMaterialsResult = new WebServiceResult(null);
		
		try {
			this.materialDAO = DAOManager.getInstance().getMaterialDAO();
			materials.setMaterials(this.materialDAO.getMaterials());
			getMaterialsResult.setData(materials);
		} catch (Exception e) {
			getMaterialsResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("material.getMaterialsError")));
			
			logger.error(this.resources.getString("material.getMaterialsError"), e);
		}
		
		return getMaterialsResult;
	}
	
	
	/**
	 * Adds a material.
	 * 
	 * @param material The material to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addMaterial(final MaterialWS material) {
		Material convertedMaterial = new Material();
		WebServiceResult addMaterialResult = new WebServiceResult();
		this.materialDAO = DAOManager.getInstance().getMaterialDAO();
		
		try {
			convertedMaterial = this.convertMaterial(material);			
		}
		catch(Exception exception) {
			addMaterialResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("material.addError")));	
			logger.error(this.resources.getString("material.addError"), exception);
			return addMaterialResult;
		}
		
		//Validate the given material.
		try {
			convertedMaterial.validate();
		} catch (Exception validationException) {
			addMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
			return addMaterialResult;
		}
		
		//Insert material if validation is successful.
		try {
			this.materialDAO.insertMaterial(convertedMaterial);
			addMaterialResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.S, this.resources.getString("material.addSuccess")));
			addMaterialResult.setData(convertedMaterial.getId());
		} catch (Exception e) {
			addMaterialResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("material.addError")));
			
			logger.error(this.resources.getString("material.addError"), e);
		}
		
		return addMaterialResult;
	}
	
	
	/**
	 * Deletes the material with the given id.
	 * 
	 * @param id The id of the material to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteMaterial(final Integer id) {
		Material material = null;
		WebServiceResult deleteMaterialResult = new WebServiceResult(null);
		
		//Check if a material with the given code exists.
		try {
			this.materialDAO = DAOManager.getInstance().getMaterialDAO();
			material = this.materialDAO.getMaterial(id);
			
			if(material != null) {
				//Delete material if exists.
				this.materialDAO.deleteMaterial(material);
				deleteMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
						MessageFormat.format(this.resources.getString("material.deleteSuccess"), id)));
			}
			else {
				//Material not found.
				deleteMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("material.notFound"), id)));
			}
		}
		catch (Exception e) {
			deleteMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("material.deleteError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("material.deleteError"), id), e);
		}
		
		return deleteMaterialResult;
	}
	
	
	/**
	 * Updates an existing material.
	 * 
	 * @param material The material to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateMaterial(final MaterialWS material) {
		Material convertedMaterial = new Material();
		WebServiceResult updateMaterialResult = new WebServiceResult(null);
		this.materialDAO = DAOManager.getInstance().getMaterialDAO();
		
		try {
			convertedMaterial = this.convertMaterial(material);			
		}
		catch(Exception exception) {
			updateMaterialResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("material.updateError")));	
			logger.error(this.resources.getString("material.updateError"), exception);
			return updateMaterialResult;
		}
		
		//Validation of the given material
		try {
			convertedMaterial.validate();
		} catch (Exception validationException) {
			updateMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
			return updateMaterialResult;
		}
		
		//Update material if validation is successful.
		try {
			this.materialDAO.updateMaterial(convertedMaterial);
			updateMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
					MessageFormat.format(this.resources.getString("material.updateSuccess"), convertedMaterial.getId())));
		} 
		catch(ObjectUnchangedException objectUnchangedException) {
			updateMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.I, 
					MessageFormat.format(this.resources.getString("material.updateUnchanged"), convertedMaterial.getId())));
		}
		catch (Exception e) {
			updateMaterialResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("material.updateError"), convertedMaterial.getId())));
			
			logger.error(MessageFormat.format(this.resources.getString("material.updateError"), convertedMaterial.getId()), e);
		}
		
		return updateMaterialResult;
	}
	
	
	/**
	 * Converts the lean material representation that is provided by the WebService to the internal data model for further processing.
	 * 
	 * @param materialWS The lean material representation provided by the WebService.
	 * @return The Material model that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private Material convertMaterial(final MaterialWS materialWS) throws Exception {
		Material convertedMaterial = new Material();
		ImageDao imageDAO;
		
		convertedMaterial.setId(materialWS.getMaterialId());
		convertedMaterial.setName(materialWS.getName());
		convertedMaterial.setDescription(materialWS.getDescription());
		convertedMaterial.setUnit(materialWS.getUnit());
		convertedMaterial.setPricePerUnit(materialWS.getPricePerUnit());
		convertedMaterial.setCurrency(materialWS.getCurrency());
		convertedMaterial.setInventory(materialWS.getInventory());
		
		if(materialWS.getImageId() != null) {
			imageDAO = DAOManager.getInstance().getImageDAO();
			convertedMaterial.setImage(imageDAO.getImage(materialWS.getImageId()));
		}
		else {
			convertedMaterial.setImage(null);			
		}
		
		return convertedMaterial;
	}
}
