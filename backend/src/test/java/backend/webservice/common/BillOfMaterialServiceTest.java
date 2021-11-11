package backend.webservice.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import backend.dao.BillOfMaterialDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.model.Currency;
import backend.model.billOfMaterial.BillOfMaterial;
import backend.model.billOfMaterial.BillOfMaterialItem;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;

/**
 * Tests the BillOfMaterial service.
 * 
 * @author Michael
 */
public class BillOfMaterialServiceTest {
	/**
	 * DAO to access material data.
	 */
	private static MaterialDao materialDAO;
	
	/**
	 * DAO to access BillOfMaterial data.
	 */
	private static BillOfMaterialDao billOfMaterialDAO;
	
	/**
	 * A screw of 50mm length.
	 */
	private Material screw50mm;
	
	/**
	 * A screw of 30mm length.
	 */
	private Material screw30mm;
	
	/**
	 * A box that can be filled with screws.
	 */
	private Material box;
	
	/**
	 * A box of 50mm screws.
	 */
	private Material boxedScrews50mm;
	
	/**
	 * A box of 30mm screws.
	 */
	private Material boxedScrews30mm;
	
	/**
	 * BillOfMaterial: Box with 50mm screws.
	 */
	private BillOfMaterial bom50mmScrewBox;
	
	/**
	 * Item: Box for 50mm screws.
	 */
	private BillOfMaterialItem bomItem50mmBox;
	
	/**
	 * Item: 50mm screws.
	 */
	private BillOfMaterialItem bomItem50mmScrews;
	
	/**
	 * BillOfMaterial: Box with 30mm screws.
	 */
	private BillOfMaterial bom30mmScrewBox;
	
	/**
	 * Item: Box for 30mm screws.
	 */
	private BillOfMaterialItem bomItem30mmBox;
	
	/**
	 * Item: 30mm screws.
	 */
	private BillOfMaterialItem bomItem30mmScrews;
	
	
	@BeforeAll
	/**
	 * Tasks to be performed once at startup of test class.
	 */
	public static void setUpClass() {
		materialDAO = DAOManager.getInstance().getMaterialDAO();
		billOfMaterialDAO = DAOManager.getInstance().getBillOfMaterialDAO();
	}
	
	
	@AfterAll
	/**
	 * Tasks to be performed once at end of test class.
	 */
	public static void tearDownClass() {
		try {
			DAOManager.getInstance().close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.createDummyMaterials();
		this.createDummyBoms();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.deleteDummyBoms();
		this.deleteDummyMaterials();
	}
	
	
	/**
	 * Initializes the database with dummy materials.
	 */
	private void createDummyMaterials() {
		this.screw30mm = new Material();
		this.screw30mm.setName("Screw - 30mm length");
		this.screw30mm.setDescription("A screw 30mm length, 4mm thread. For wood.");
		this.screw30mm.setUnit(UnitOfMeasurement.ST);
		this.screw30mm.setPricePerUnit(BigDecimal.valueOf(0.01));
		this.screw30mm.setCurrency(Currency.EUR);
		this.screw30mm.setInventory(Long.valueOf(10000));
		
		this.screw50mm = new Material();
		this.screw50mm.setName("Screw - 50mm length");
		this.screw50mm.setDescription("A screw 50mm length, 4mm thread. For wood.");
		this.screw50mm.setUnit(UnitOfMeasurement.ST);
		this.screw50mm.setPricePerUnit(BigDecimal.valueOf(0.01));
		this.screw50mm.setCurrency(Currency.EUR);
		this.screw50mm.setInventory(Long.valueOf(10000));
		
		this.box = new Material();
		this.box.setName("Box for screws");
		this.box.setDescription("A packaging for different kinds of screws. Made of cardboard.");
		this.box.setUnit(UnitOfMeasurement.ST);
		this.box.setPricePerUnit(BigDecimal.valueOf(0.05));
		this.box.setCurrency(Currency.EUR);
		this.box.setInventory(Long.valueOf(250));
		
		this.boxedScrews30mm = new Material();
		this.boxedScrews30mm.setName("A package of screws. 100 pieces - 30mm length.");
		this.boxedScrews30mm.setDescription("4mm thread. For wood");
		this.boxedScrews30mm.setUnit(UnitOfMeasurement.ST);
		this.boxedScrews30mm.setPricePerUnit(BigDecimal.valueOf(0,99));
		this.boxedScrews30mm.setCurrency(Currency.EUR);
		this.boxedScrews30mm.setInventory(Long.valueOf(100));
		
		this.boxedScrews50mm = new Material();
		this.boxedScrews50mm.setName("A package of screws. 100 pieces - 50mm length.");
		this.boxedScrews50mm.setDescription("4mm thread. For wood");
		this.boxedScrews50mm.setUnit(UnitOfMeasurement.ST);
		this.boxedScrews50mm.setPricePerUnit(BigDecimal.valueOf(0,99));
		this.boxedScrews50mm.setCurrency(Currency.EUR);
		this.boxedScrews50mm.setInventory(Long.valueOf(100));
		
		try {
			materialDAO.insertMaterial(this.screw30mm);
			materialDAO.insertMaterial(this.screw50mm);
			materialDAO.insertMaterial(this.box);
			materialDAO.insertMaterial(this.boxedScrews30mm);
			materialDAO.insertMaterial(this.boxedScrews50mm);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Initializes the database with dummy BillOfMaterials.
	 */
	private void createDummyBoms() {
		this.bomItem30mmBox = new BillOfMaterialItem();
		this.bomItem30mmBox.setId(1);
		this.bomItem30mmBox.setMaterial(this.box);
		this.bomItem30mmBox.setQuantity(1);
		
		this.bomItem30mmScrews = new BillOfMaterialItem();
		this.bomItem30mmScrews.setId(2);
		this.bomItem30mmScrews.setMaterial(this.screw30mm);
		this.bomItem30mmScrews.setQuantity(100);
		
		this.bom30mmScrewBox = new BillOfMaterial();
		this.bom30mmScrewBox.setName("Bill of material - A box of 100 screws. 30mm length.");
		this.bom30mmScrewBox.setDescription("4mm thread. For wood");
		this.bom30mmScrewBox.setMaterial(this.boxedScrews30mm);
		this.bom30mmScrewBox.addItem(this.bomItem30mmBox);
		this.bom30mmScrewBox.addItem(this.bomItem30mmScrews);
		
		this.bomItem50mmBox = new BillOfMaterialItem();
		this.bomItem50mmBox.setId(1);
		this.bomItem50mmBox.setMaterial(this.box);
		this.bomItem50mmBox.setQuantity(1);
		
		this.bomItem50mmScrews = new BillOfMaterialItem();
		this.bomItem50mmScrews.setId(2);
		this.bomItem50mmScrews.setMaterial(this.screw50mm);
		this.bomItem50mmScrews.setQuantity(100);
		
		this.bom50mmScrewBox = new BillOfMaterial();
		this.bom50mmScrewBox.setName("Bill of material - A box of 100 screws. 50mm length.");
		this.bom50mmScrewBox.setDescription("4mm thread. For wood");
		this.bom50mmScrewBox.setMaterial(this.boxedScrews50mm);
		this.bom50mmScrewBox.addItem(this.bomItem50mmBox);
		this.bom50mmScrewBox.addItem(this.bomItem50mmScrews);
		
		try {
			billOfMaterialDAO.insertBillOfMaterial(this.bom30mmScrewBox);
			billOfMaterialDAO.insertBillOfMaterial(this.bom50mmScrewBox);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy materials from the database.
	 */
	private void deleteDummyMaterials() {
		try {
			materialDAO.deleteMaterial(this.boxedScrews50mm);
			materialDAO.deleteMaterial(this.boxedScrews30mm);
			materialDAO.deleteMaterial(this.box);
			materialDAO.deleteMaterial(this.screw50mm);
			materialDAO.deleteMaterial(this.screw30mm);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Deletes the dummy BillOfMaterials from the database.
	 */
	private void deleteDummyBoms() {
		try {
			billOfMaterialDAO.deleteBillOfMaterial(this.bom50mmScrewBox);
			billOfMaterialDAO.deleteBillOfMaterial(this.bom30mmScrewBox);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
