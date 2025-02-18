package backend.dao;

import java.io.Closeable;
import java.io.IOException;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Manages a central database connection and provides DAOs for database access.
 *
 * @author Michael
 */
public final class DAOManager implements Closeable {
    /**
     * Instance of this class.
     */
    private static DAOManager instance;

    /**
     * Factory for database session.
     */
    private EntityManagerFactory sessionFactory;

    /**
     * DAO to manage employee data.
     */
    private EmployeeDao employeeDao;

    /**
     * DAO to manage department data.
     */
    private DepartmentDao departmentDao;

    /**
     * DAO to manage material data.
     */
    private MaterialDao materialDao;

    /**
     * DAO to manage business partner data.
     */
    private BusinessPartnerDao businessPartnerDao;

    /**
     * DAO to manage sales order data.
     */
    private SalesOrderDao salesOrderDao;

    /**
     * DAO to manage purchase order data.
     */
    private PurchaseOrderDao purchaseOrderDao;

    /**
     * DAO to manage account data.
     */
    private AccountDao accountDao;

    /**
     * DAO to manage posting data.
     */
    private PostingDao postingDao;

    /**
     * DAO to manage image data.
     */
    private ImageDao imageDao;

    /**
     * DAO for execution of native SQL commands.
     */
    private NativeSqlDao nativeSqlDao;

    /**
     * DAO to manage BillOfMaterial data.
     */
    private BillOfMaterialDao billOfMaterialDao;

    /**
     * DAO to manage ProductionOrder data.
     */
    private ProductionOrderDao productionOrderDao;

    /**
     * Initializes the DAOManager.
     */
    private DAOManager() {
        this.sessionFactory = this.getSessionFactory();
    }

    /**
     * Provides the instance of the DAOManager.
     *
     * @return The instance of the DAOManager.
     */
    public static DAOManager getInstance() {
        if (instance == null) {
            instance = new DAOManager();
        }

        return instance;
    }

    /**
     * Builds a session factory for database access.
     *
     * @return Session factory for database access.
     */
    private EntityManagerFactory getSessionFactory() {
        // The given string must match with the persistence unit defined in the persistence.xml file.
        return Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    /**
     * Returns a DAO to manage employee data.
     *
     * @return The EmployeeDAO.
     */
    public EmployeeDao getEmployeeDAO() {
        if (this.employeeDao == null) {
            this.employeeDao = new EmployeeHibernateDao(this.sessionFactory);
        }

        return this.employeeDao;
    }

    /**
     * Returns a DAO to manage department data.
     *
     * @return The DepartmentDAO.
     */
    public DepartmentDao getDepartmentDAO() {
        if (this.departmentDao == null) {
            this.departmentDao = new DepartmentHibernateDao(this.sessionFactory);
        }

        return this.departmentDao;
    }

    /**
     * Returns a DAO to manage material data.
     *
     * @return The MaterialDAO.
     */
    public MaterialDao getMaterialDAO() {
        if (this.materialDao == null) {
            this.materialDao = new MaterialHibernateDao(this.sessionFactory);
        }

        return this.materialDao;
    }

    /**
     * Returns a DAO to manage business partner data.
     *
     * @return The BusinessPartnerDAO.
     */
    public BusinessPartnerDao getBusinessPartnerDAO() {
        if (this.businessPartnerDao == null) {
            this.businessPartnerDao = new BusinessPartnerHibernateDao(this.sessionFactory);
        }

        return this.businessPartnerDao;
    }

    /**
     * Returns a DAO to manage sales order data.
     *
     * @return The SalesOrderDAO.
     */
    public SalesOrderDao getSalesOrderDAO() {
        if (this.salesOrderDao == null) {
            this.salesOrderDao = new SalesOrderHibernateDao(this.sessionFactory);
        }

        return this.salesOrderDao;
    }

    /**
     * Returns a DAO to manage purchase order data.
     *
     * @return The PurchaseOrderDAO.
     */
    public PurchaseOrderDao getPurchaseOrderDAO() {
        if (this.purchaseOrderDao == null) {
            this.purchaseOrderDao = new PurchaseOrderHibernateDao(this.sessionFactory);
        }

        return this.purchaseOrderDao;
    }

    /**
     * Returns a DAO to manage account data.
     *
     * @return The AccountDAO.
     */
    public AccountDao getAccountDAO() {
        if (this.accountDao == null) {
            this.accountDao = new AccountHibernateDao(this.sessionFactory);
        }

        return this.accountDao;
    }

    /**
     * Returns a DAO to manage posting data.
     *
     * @return The PostingDao.
     */
    public PostingDao getPostingDAO() {
        if (this.postingDao == null) {
            this.postingDao = new PostingHibernateDao(this.sessionFactory);
        }

        return this.postingDao;
    }

    /**
     * Returns a DAO to manage image data.
     *
     * @return The ImageDAO.
     */
    public ImageDao getImageDAO() {
        if (this.imageDao == null) {
            this.imageDao = new ImageHibernateDao(this.sessionFactory);
        }

        return this.imageDao;
    }

    /**
     * Returns a DAO to execute native SQL commands.
     *
     * @return The nativeSqlDAO.
     */
    public NativeSqlDao getNativeSqlDAO() {
        if (this.nativeSqlDao == null) {
            this.nativeSqlDao = new NativeSqlHibernateDao(this.sessionFactory);
        }

        return this.nativeSqlDao;
    }

    /**
     * Returns a DAO to manage BillOfMaterial data.
     *
     * @return The BillOfMaterialDao.
     */
    public BillOfMaterialDao getBillOfMaterialDAO() {
        if (this.billOfMaterialDao == null) {
            this.billOfMaterialDao = new BillOfMaterialHibernateDao(this.sessionFactory);
        }

        return this.billOfMaterialDao;
    }

    /**
     * Returns a DAO to manage ProductionOrder data.
     *
     * @return The ProductionOrderDao.
     */
    public ProductionOrderDao getProductionOrderDAO() {
        if (this.productionOrderDao == null) {
            this.productionOrderDao = new ProductionOrderHibernateDao(this.sessionFactory);
        }

        return this.productionOrderDao;
    }

    @Override
    public void close() throws IOException {
        try {
            this.sessionFactory.close();
            instance = null;
        } catch (IllegalStateException exception) {
            throw new IOException(exception.getMessage());
        }
    }
}
