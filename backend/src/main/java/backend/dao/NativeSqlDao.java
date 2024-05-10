package backend.dao;

/**
 * Interface for Native SQL command execution.
 *
 * @author Michael
 */
public interface NativeSqlDao {
    /**
     * Executes the given SQL statement.
     *
     * @param sqlStatement The SQL statement to be executed.
     * @throws Exception SQL execution failed.
     */
    void executeStatement(String sqlStatement) throws Exception;
}
