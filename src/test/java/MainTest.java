package test;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("junit-jupiter")
@SelectClasses(
    {
        NameTest.class,
        PatientTest.class,
        PatientIdentityTest.class,
        PatientListTest.class,
    }
)
public class MainTest {

    @Test
    public void runAllTests() {
        System.out.println("Running all JUnit tests...");
    }
}
