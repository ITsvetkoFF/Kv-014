package edu.softserve.zoo.web.test.rest.endpoints;

import edu.softserve.zoo.web.test.rest.endpoints.checking.CheckResult;
import edu.softserve.zoo.web.test.rest.endpoints.checking.checker.RouteChecker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;

/**
 * This test runs the route validation with custom set of checks.
 * And displays an error descriptions in case if one or more checks fail.
 *
 * @author Bohdan Cherniakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/web-test-ctx.xml")
@ActiveProfiles("test")
public class RoutesCorrectnessTest {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private List<RouteChecker> checkers;

    @Test
    public void checksRoutesCorrectness() {
        CheckResult overallResult = new CheckResult();
        List<Route> routes = getRoutesFromContext();
        for (Route route : routes) {
            for (RouteChecker checker : checkers) {
                CheckResult checkResult = checker.check(route);
                if (checkResult.hasErrors()) {
                    overallResult.addError(checkResult.getErrorMessage());
                }
            }
        }
        //There should be no errors
        assertFalse(overallResult.getErrorMessage(), overallResult.hasErrors());
    }

    //Gets routes from the Spring context
    private List<Route> getRoutesFromContext() {
        Set<RequestMappingInfo> requestMappingInfos = requestMappingHandlerMapping.getHandlerMethods().keySet();
        List<Route> routes = new LinkedList<>();
        for (RequestMappingInfo handlerMethod : requestMappingInfos) {
            Set<String> patterns = handlerMethod.getPatternsCondition().getPatterns();
            for (String pattern : patterns) {
                Method method = requestMappingHandlerMapping.getHandlerMethods().get(handlerMethod).getMethod();
                routes.add(new Route(pattern, method));
            }
        }
        return routes;
    }

}
