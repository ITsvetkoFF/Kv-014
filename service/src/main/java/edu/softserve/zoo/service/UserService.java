package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Employee;

/**
 * @author Ilya Doroshenko
 */
public interface UserService {
    void changePassword(String newPassword, String confirmationPassword);
    Employee getCurrentUser();
}
