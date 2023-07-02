import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import services.ConfigServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConfigServiceTest.class
})
public class TestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
