package edu.softserve.zoo.web.test.controller.endpoints.checking.checker.impl;

import edu.softserve.zoo.web.test.controller.endpoints.checking.CheckResult;
import edu.softserve.zoo.web.test.controller.endpoints.checking.checker.RouteChecker;
import edu.softserve.zoo.web.test.controller.endpoints.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks that resource identifiers are in plural form by
 * checking the 's' ending of the identifier.
 *
 * @author Bohdan Cherniakh
 */
@Component
public class ResourceIdInPluralFormRouteChecker implements RouteChecker {

    /*
     * regular expression for parsing the resource identifiers from the URI.
     * Basic idea is to parse the permitted characters after the '/' symbol
     */
    private static final String BASIC_ROUTE_REGEX = "(/(?!api|v1)([a-z-]+))";

    private static final Pattern BASIC_ROUTE_PATTERN = Pattern.compile(BASIC_ROUTE_REGEX);
    private static final Set<String> IGNORED = new HashSet<>();

    private static final String PLURAL_ENDING = "s";

    private static final String PLURAL_FORM_ERROR_MESSAGE = "%s - basic resource id should be in plural form!";

    static {
        String[] ignoredNouns = {"women", "men", "children", "teeth", "feet",
                "people", "mice", "cacti", "foci", "fungi", "nuclei", "syllabi",
                "phenomena", "criteria", "data", "user", "count", "performance", "dashboard"};

        Collections.addAll(IGNORED, ignoredNouns);
    }

    @Override
    public CheckResult check(Route route) {
        CheckResult result = new CheckResult();
        Matcher matcher = BASIC_ROUTE_PATTERN.matcher(route.getUri());

        while (matcher.find()) {
            String resourceId = matcher.group(2);
            if (!isInPluralForm(resourceId)) {
                result.addError(String.format(PLURAL_FORM_ERROR_MESSAGE, route.toString()));
            }
        }
        return result;
    }

    private boolean isInPluralForm(String resourceId) {
        return (IGNORED.contains(resourceId) || StringUtils.endsWithIgnoreCase(resourceId, PLURAL_ENDING));
    }
}
