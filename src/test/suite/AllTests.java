package test.suite;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({IndicatorTestSuite.class, TradeTestSuite.class})
public class AllTests {

}
