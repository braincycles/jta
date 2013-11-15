package test.suite;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.testPortfolio;
import test.testPosition;

@RunWith(Suite.class)
@SuiteClasses({testPosition.class, testPortfolio.class})
public class TradeTestSuite {

}
