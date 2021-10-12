package backend.dao;

import javax.persistence.EntityManagerFactory;

import backend.model.account.Posting;

/**
 * Provides access to posting database persistence using Hibernate.
 * 
 * @author Michael
 */
public class PostingHibernateDao implements PostingDao {
	/**
	 * Factory for database session.
	 */
	protected EntityManagerFactory sessionFactory;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param sessionFactory The database session factory.
	 */
	public PostingHibernateDao(final EntityManagerFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	public void insertPosting(Posting posting) throws Exception {
		// TODO Auto-generated method stub
	}


	@Override
	public void deletePosting(Posting posting) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
