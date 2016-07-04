package edu.softserve.zoo.service.notifier;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.service.exception.NotifierException;

/**
 * Base interface for all types of communication with employees.
 *
 * @author Ilya Doroshenko
 */
public interface EmployeeNotifier {

    void sendPassword(Employee employee, String password);
}
