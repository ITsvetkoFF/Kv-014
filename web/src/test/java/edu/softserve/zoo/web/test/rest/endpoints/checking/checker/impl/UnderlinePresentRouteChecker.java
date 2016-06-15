package edu.softserve.zoo.web.test.rest.endpoints.checking.checker.impl;

import edu.softserve.zoo.web.test.rest.endpoints.Route;
import edu.softserve.zoo.web.test.rest.endpoints.checking.CheckResult;
import edu.softserve.zoo.web.test.rest.endpoints.checking.checker.RouteChecker;
import org.springframework.stereotype.Component;

/**
 * Helps determine the common mistake: usage of a underline instead of a hyphen
 * in a resource identifier.
 *
 * @author Bohdan Cherniakh
 */
@Component
public class UnderlinePresentRouteChecker implements RouteChecker {
    private static final String UNDERLINE_FOUND_ERROR = "%s contains underline!";

    @Override
    public CheckResult check(Route route) {
        CheckResult checkResult = new CheckResult();
        if (route.getUri().contains("_")) {
            checkResult.addError(String.format(UNDERLINE_FOUND_ERROR, route.toString()));
        }
        return checkResult;
    }
}
