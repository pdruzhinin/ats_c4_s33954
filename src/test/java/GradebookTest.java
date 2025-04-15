import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

public class GradebookTest {
  private static Gradebook gradebook;

  @BeforeAll
  public static void setUp() {
    gradebook = new Gradebook();
  }

  @Test
  public void testAddSubject() {
    gradebook = new Gradebook();
    gradebook.addSubject("Physics");
    List<String> expectedList = List.of("Physics");
    assertEquals(expectedList, gradebook.getSubjects());
  }

  @Test
  public void testAddGradeToSubject() {
    gradebook = new Gradebook();
    gradebook.addSubject("Math");
    gradebook.addGrade("Math", 5.0);
    Map<String, List<Double>> expectedMap = new HashMap<>();
    expectedMap.put("Math", List.of(5.0));
    assertEquals(expectedMap, gradebook.getGrades());
  }

  @TestFactory
  Stream<DynamicTest> dynamicTestsForSubjects() {
    gradebook = new Gradebook();
    gradebook.addSubject("Math");
    gradebook.addSubject("Chemistry");
    gradebook.addSubject("Biology");
    gradebook.addSubject("Physics");
    gradebook.addSubject("History");
    return gradebook.getSubjects().stream()
        .map(
            subject ->
                DynamicTest.dynamicTest(
                    "Test for subject: " + subject,
                    () -> {
                      gradebook.addGrade(subject, 5.0);
                      gradebook.addGrade(subject, 4.0);
                      assertEquals(
                          2,
                          gradebook.getGrades().get(subject).size(),
                          "Test amount of grades for subject: %s".formatted(subject));
                      double actualAverage = gradebook.calcAvgForSubject(subject);
                      double expectedAverage = 4.5;
                      assertEquals(
                          expectedAverage,
                          actualAverage,
                          0.001,
                          "Grade average test for subject: ".formatted(subject));
                    }));
  }

  @TestFactory
  Stream<DynamicTest> dynamicTestsForAverageForAllSubjects() {
    gradebook = new Gradebook();
    gradebook.addSubject("Math");
    gradebook.addSubject("Chemistry");
    gradebook.addSubject("Biology");
    gradebook.addSubject("Physics");

    List<DynamicTest> tests = new ArrayList<>();

    tests.add(
        DynamicTest.dynamicTest(
            "Add grade 5.0 to Math",
            () -> {
              gradebook.addGrade("Math", 5.0);
              assertEquals(5.0, gradebook.calcAvgForAllSubjects(), 0.001);
            }));

    tests.add(
        DynamicTest.dynamicTest(
            "Add grade 4.0 to Chemistry",
            () -> {
              gradebook.addGrade("Chemistry", 4.0);
              assertEquals(4.5, gradebook.calcAvgForAllSubjects(), 0.001);
            }));

    tests.add(
        DynamicTest.dynamicTest(
            "Add grade 5.0 to Math",
            () -> {
              gradebook.addGrade("Math", 5.0);
              assertEquals(4.666, gradebook.calcAvgForAllSubjects(), 0.001);
            }));

    tests.add(
        DynamicTest.dynamicTest(
            "Add grade 3.0 to Biology",
            () -> {
              gradebook.addGrade("Biology", 3.0);
              assertEquals(4.25, gradebook.calcAvgForAllSubjects(), 0.001);
            }));

    tests.add(
        DynamicTest.dynamicTest(
            "Add grade 4.0 to Physics",
            () -> {
              gradebook.addGrade("Physics", 4.0);
              assertEquals(4.2, gradebook.calcAvgForAllSubjects(), 0.001);
            }));

    return tests.stream();
  }

  // Testy na zadanie dodatkowe
  @Test
  public void testAddGradeToInvalidSubject() {
    gradebook = new Gradebook();
    gradebook.addSubject("Math");
    assertThrows(IllegalArgumentException.class, () -> gradebook.addGrade("Chemistry", 5.0));
  }

  @Test
  public void testCalcAvgForSubjectWithNoGrades() {
    gradebook = new Gradebook();
    gradebook.addSubject("Math");
    assertThrows(IllegalArgumentException.class, () -> gradebook.calcAvgForSubject("Math"));
  }

  @Test
  public void testCalcAvgForInvalidSubject() {
    gradebook = new Gradebook();
    assertThrows(IllegalArgumentException.class, () -> gradebook.calcAvgForSubject("Chemistry"));
  }
}
