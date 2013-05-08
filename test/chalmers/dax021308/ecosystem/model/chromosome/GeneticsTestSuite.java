/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.chromosome;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Loanne Berggren
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ BooleanGeneTests.class, ChromosomeTest.class,
		DeerGenomeTest.class,
		DoubleGeneTests.class })
public class GeneticsTestSuite {

}
