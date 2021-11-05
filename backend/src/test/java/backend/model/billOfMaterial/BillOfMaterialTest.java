package backend.model.billOfMaterial;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import backend.model.Currency;
import backend.model.material.Material;
import backend.model.material.UnitOfMeasurement;

/**
 * Tests the BillOfMaterial model.
 * 
 * @author Michael
 */
public class BillOfMaterialTest {
	/**
	 * The BillOfMaterial under test.
	 */
	private BillOfMaterial billOfMaterial;
	
	/**
	 * The item of the BillOfMaterial.
	 */
	private BillOfMaterialItem billOfMaterialItem;
	
	/**
	 * The parent material as defined in the BillOfMaterial.
	 */
	private Material parentMaterial;
	
	/**
	 * The child material of which the parent material is made of.
	 */
	private Material childMaterial;
	
	
	@BeforeEach
	/**
	 * Tasks to be performed before each test is run.
	 */
	private void setUp() {
		this.initMaterials();
		this.initBillOfMaterial();
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		this.billOfMaterialItem = null;
		this.billOfMaterial = null;
		this.childMaterial = null;
		this.parentMaterial = null;
	}
	
	
	/**
	 * Initializes the materials.
	 */
	private void initMaterials() {
		this.parentMaterial = new Material();
		this.parentMaterial.setName("Package of Screws - 50mm");
		this.parentMaterial.setDescription("A package of screw. 50mm length. 1000 pieces.");
		this.parentMaterial.setUnit(UnitOfMeasurement.ST);
		this.parentMaterial.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(14.99)));
		this.parentMaterial.setCurrency(Currency.EUR);
		this.parentMaterial.setInventory(Long.valueOf(10));
		
		this.childMaterial = new Material();
		this.childMaterial.setName("Screw - 50mm");
		this.childMaterial.setDescription("A single screw. 50mm length.");
		this.childMaterial.setUnit(UnitOfMeasurement.ST);
		this.childMaterial.setPricePerUnit(BigDecimal.valueOf(Double.valueOf(0.02)));
		this.childMaterial.setCurrency(Currency.EUR);
		this.childMaterial.setInventory(Long.valueOf(10000));
	}
	
	
	/**
	 * Initializes the BillOfMaterial.
	 */
	private void initBillOfMaterial() {
		this.billOfMaterialItem = new BillOfMaterialItem();
		this.billOfMaterialItem.setId(1);
		this.billOfMaterialItem.setMaterial(this.childMaterial);
		this.billOfMaterialItem.setQuantity(1000);
		
		this.billOfMaterial = new BillOfMaterial();
		this.billOfMaterial.setId(1);
		this.billOfMaterial.setName("Package of Screws - 50mm");
		this.billOfMaterial.setDescription("A package of screw. 50mm length. 1000 pieces.");
		this.billOfMaterial.setMaterial(this.parentMaterial);
		this.billOfMaterial.addItem(this.billOfMaterialItem);
	}
}
