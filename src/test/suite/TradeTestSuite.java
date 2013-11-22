package test.suite;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.PortfolioTest;
import test.PositionTest;
import test.TradeTest;

@RunWith(Suite.class)
@SuiteClasses({PositionTest.class, PortfolioTest.class, TradeTest.class})
public class TradeTestSuite {

}
