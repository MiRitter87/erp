package backend.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import backend.exception.ObjectInUseException;
import backend.exception.ObjectUnchangedException;
import backend.model.billOfMaterial.BillOfMaterialItem;
import backend.model.material.Material;
import backend.model.purchaseOrder.PurchaseOrderItem;
import backend.model.salesOrder.SalesOrderItem;

/**
 * Provides access to material database persistence using Hibernate.
 * 
 * @author Michael
 */
public class MaterialHibernateDao implements MaterialDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public MaterialHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	public void insertMaterial(Material material) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(material);
			entityManager.flush();	//Assures, that the generated material ID is available.
			entityManager.getTransaction().commit();
		}
		catch(Exception exception) {
			//If something breaks a rollback is necessary!?
			if(entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw exception;
		}
		finally {
			entityManager.close();			
		}
	}

	
	@Override
	public void deleteMaterial(Material material) throws ObjectInUseException, Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		this.checkMaterialInUse(material, entityManager);
		
		//In order to successfully delete an entity, it first has to be fetched from the database.
		Material deleteMaterial = entityManager.find(Material.class, material.getId());
		
		entityManager.getTransaction().begin();
		
		try {
			entityManager.remove(deleteMaterial);
			entityManager.getTransaction().commit();			
		}
		catch(Exception exception) {
			//If something breaks a rollback is necessary.
			if(entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw exception;
		}
		finally {
			entityManager.close();			
		}
	}

	
	@Override
	public List<Material> getMaterials() throws Exception {
		List<Material> materials = null;
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Material> criteriaQuery = criteriaBuilder.createQuery(Material.class);
			Root<Material> criteria = criteriaQuery.from(Material.class);
			criteriaQuery.select(criteria);
			criteriaQuery.orderBy(criteriaBuilder.asc(criteria.get("id")));	//Order by id ascending
			TypedQuery<Material> typedQuery = entityManager.createQuery(criteriaQuery);
			materials = typedQuery.getResultList();
			
			entityManager.getTransaction().commit();			
		}
		catch(Exception exception) {
			//If something breaks a rollback is necessary.
			if(entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw exception;
		}
		finally {
			entityManager.close();			
		}
		
		return materials;
	}

	
	@Override
	public Material getMaterial(Integer id) throws Exception {
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		Material material = entityManager.find(Material.class, id);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return material;
	}

	
	@Override
	public void updateMaterial(Material material) throws ObjectUnchangedException, Exception {
		EntityManager entityManager;
		
		this.checkMaterialDataChanged(material);
		
		entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(material);
		entityManager.getTransaction().commit();
		entityManager.close();	
	}
	
	
	@Override
	public Set<Integer> getAllImageIds() throws Exception {
		List<Material> materials = null;
		Set<Integer> imageIds = new HashSet<Integer>();
		EntityManager entityManager = this.sessionFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Material> criteriaQuery = criteriaBuilder.createQuery(Material.class);
			Root<Material> criteria = criteriaQuery.from(Material.class);
			criteriaQuery.select(criteria);
			criteriaQuery.where(criteria.get("image").isNotNull());
			TypedQuery<Material> typedQuery = entityManager.createQuery(criteriaQuery);
			materials = typedQuery.getResultList();
			
			entityManager.getTransaction().commit();			
		}
		catch(Exception exception) {
			//If something breaks a rollback is necessary.
			if(entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			throw exception;
		}
		finally {
			entityManager.close();			
		}
		
		//Get the image IDs of all materials that have an image defined.
		for(Material tempMaterial:materials)
			imageIds.add(tempMaterial.getImage().getId());
		
		return imageIds;
	}
	
	
	/**
	 * Checks if the data of the given material differ from the material that is persisted at database level.
	 * 
	 * @param material The material to be checked.
	 * @throws ObjectUnchangedException In case the material has not been changed.
	 * @throws Exception In case an error occurred during determination of the material stored at the database.
	 */
	private void checkMaterialDataChanged(final Material material) throws ObjectUnchangedException, Exception {
		Material databaseMaterial = this.getMaterial(material.getId());
		
		if(databaseMaterial.equals(material))
			throw new ObjectUnchangedException();
	}
	
	
	/**
	 * Checks if the material is referenced by another business object.
	 * 
	 * @param material The material which is checked.
	 * @throws ObjectInUseException In case the material is in use.
	 */
	private void checkMaterialInUse(final Material material, final EntityManager entityManager) throws ObjectInUseException {		
		this.checkMaterialUsedInSalesOrder(material, entityManager);
		this.checkMaterialUsedInPurchaseOrder(material, entityManager);
		this.checkMaterialUsedInBillOfMaterial(material, entityManager);
	}
	
	
	/**
	 * Checks if the material is referenced in any sales order.
	 * 
	 * @param material The material which is checked.
	 * @throws ObjectInUseException In case the material is in use.
	 */
	private void checkMaterialUsedInSalesOrder(final Material material, final EntityManager entityManager) throws ObjectInUseException {
		SalesOrderItem salesOrderItem;
		List<SalesOrderItem> salesOrderItems;		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SalesOrderItem> criteriaQuery = criteriaBuilder.createQuery(SalesOrderItem.class);
		
		Root<SalesOrderItem> criteria = criteriaQuery.from(SalesOrderItem.class);
		criteriaQuery.select(criteria).distinct(true);
		criteriaQuery.where(criteriaBuilder.equal(criteria.get("material"), material));
		
		TypedQuery<SalesOrderItem> typedQuery = entityManager.createQuery(criteriaQuery);
		salesOrderItems = typedQuery.getResultList();
		
		if(salesOrderItems.size() > 0) {
			salesOrderItem = salesOrderItems.get(0);
			throw new ObjectInUseException(material.getId(), salesOrderItem.getSalesOrder().getId(), salesOrderItem.getSalesOrder());
		}
	}
	
	
	/**
	 * Checks if the material is referenced in any purchase order.
	 * 
	 * @param material The material which is checked.
	 * @throws ObjectInUseException In case the material is in use.
	 */
	private void checkMaterialUsedInPurchaseOrder(final Material material, final EntityManager entityManager) throws ObjectInUseException {
		PurchaseOrderItem purchaseOrderItem;
		List<PurchaseOrderItem> purchaseOrderItems;		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PurchaseOrderItem> criteriaQuery = criteriaBuilder.createQuery(PurchaseOrderItem.class);
		
		Root<PurchaseOrderItem> criteria = criteriaQuery.from(PurchaseOrderItem.class);
		criteriaQuery.select(criteria).distinct(true);
		criteriaQuery.where(criteriaBuilder.equal(criteria.get("material"), material));
		
		TypedQuery<PurchaseOrderItem> typedQuery = entityManager.createQuery(criteriaQuery);
		purchaseOrderItems = typedQuery.getResultList();
		
		if(purchaseOrderItems.size() > 0) {
			purchaseOrderItem = purchaseOrderItems.get(0);
			throw new ObjectInUseException(material.getId(), purchaseOrderItem.getPurchaseOrder().getId(), purchaseOrderItem.getPurchaseOrder());
		}
	}
	
	
	/**
	 * Checks if the material is referenced in any BillOfMaterial.
	 * 
	 * @param material The material which is checked.
	 * @throws ObjectInUseException In case the material is in use.
	 */
	private void checkMaterialUsedInBillOfMaterial(final Material material, final EntityManager entityManager) throws ObjectInUseException {
		this.checkMaterialUsedInBomHead(material, entityManager);
		this.checkMaterialUsedInBomItem(material, entityManager);
	}
	
	
	/**
	 * Checks if the material is referenced in the head part of any BillOfMaterial.
	 * 
	 * @param material The material which is checked.
	 * @throws ObjectInUseException In case the material is in use.
	 */
	private void checkMaterialUsedInBomHead(final Material material, final EntityManager entityManager) throws ObjectInUseException {
		
	}
	
	
	/**
	 * Checks if the material is referenced in the item part of any BillOfMaterial.
	 * 
	 * @param material The material which is checked.
	 * @throws ObjectInUseException In case the material is in use.
	 */
	private void checkMaterialUsedInBomItem(final Material material, final EntityManager entityManager) throws ObjectInUseException {
		BillOfMaterialItem billOfMaterialItem;
		List<BillOfMaterialItem> billOfMaterialItems;		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BillOfMaterialItem> criteriaQuery = criteriaBuilder.createQuery(BillOfMaterialItem.class);
		
		Root<BillOfMaterialItem> criteria = criteriaQuery.from(BillOfMaterialItem.class);
		criteriaQuery.select(criteria).distinct(true);
		criteriaQuery.where(criteriaBuilder.equal(criteria.get("material"), material));
		
		TypedQuery<BillOfMaterialItem> typedQuery = entityManager.createQuery(criteriaQuery);
		billOfMaterialItems = typedQuery.getResultList();
		
		if(billOfMaterialItems.size() > 0) {
			billOfMaterialItem = billOfMaterialItems.get(0);
			throw new ObjectInUseException(material.getId(), billOfMaterialItem.getBillOfMaterial().getId(), billOfMaterialItem.getBillOfMaterial());
		}
	}
}
