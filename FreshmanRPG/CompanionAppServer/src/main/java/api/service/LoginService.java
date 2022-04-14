package api.service;

import datasource.DatabaseException;

/**
 * Login Service interface
 *
 * @author Jun
 */
public interface LoginService {
    /**
     *
     * @param username
     * @param password
     * @return
     * @throws DatabaseException if username of password is incorrect, this will be thrown
     */
    int loginWithCredentials(String username, String password) throws DatabaseException;
}
