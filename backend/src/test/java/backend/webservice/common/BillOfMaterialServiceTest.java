package backend.webservice.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import backend.dao.BillOfMaterialDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.model.billOfMaterial.BillOfMaterial;
import backend.model.billOfMaterial.BillOfMaterialItem;
import backend.model.material.Material;

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
	private BillOfMaterialItem bom50mmBox;
	
	/**
	 * Item: 50mm screws.
	 */
	private BillOfMaterialItem bom50mmScrews;
	
	/**
	 * BillOfMaterial: Box with 30mm screws.
	 */
	private BillOfMaterial bom30mmScrewBox;
	
	/**
	 * Item: Box for 30mm screws.
	 */
	private BillOfMaterialItem bom30mmBox;
	
	/**
	 * Item: 30mm screws.
	 */
	private BillOfMaterialItem bom30mmScrews;
	
	
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
		
	}
	
	
	@AfterEach
	/**
	 * Tasks to be performed after each test has been run.
	 */
	private void tearDown() {
		
	}
}
