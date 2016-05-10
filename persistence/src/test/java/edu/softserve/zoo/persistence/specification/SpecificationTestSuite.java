package edu.softserve.zoo.persistence.specification;

import edu.softserve.zoo.persistence.specification.generic.SupportedSpecificationTest;
import edu.softserve.zoo.persistence.specification.specification_type.CriterionSpecificationTest;
import edu.softserve.zoo.persistence.specification.specification_type.HQLSpecificationTest;
import edu.softserve.zoo.persistence.specification.specification_type.SQLSpecificationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Bohdan Cherniakh
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({SupportedSpecificationTest.class, CriterionSpecificationTest.class, HQLSpecificationTest.class, SQLSpecificationTest.class })
public class SpecificationTestSuite {
}
