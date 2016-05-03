package edu.softserve.zoo.persistence.specification.general;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Bohdan Cherniakh
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ CriteriaSpecificationTest.class, HQLSpecificationTest.class, SQLSpecificationTest.class })
public class SpecificationTypesTestSuite {
}
