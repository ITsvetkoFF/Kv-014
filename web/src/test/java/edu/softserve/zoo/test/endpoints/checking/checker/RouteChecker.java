package edu.softserve.zoo.test.endpoints.checking.checker;

import edu.softserve.zoo.test.endpoints.Route;
import edu.softserve.zoo.test.endpoints.checking.CheckResult;

/**
 * Checks the route with some rules
 *
 * @author Bohdan Cherniakh
 */
public interface RouteChecker {

    /**
     * Checks if the route meets some predefined requirements or have some illegal parts
     *
     * @param route the route to check
     * @return the check result. The result contains errors with the description is the check is failed.
     */
    CheckResult check(Route route);
}
