import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Gradebook {
    private final List<String> subjects;
    private final Map<String, List<Double>> subjectsGrades;
    public Gradebook() {
        this.subjects = new ArrayList<>();
        this.subjectsGrades = new HashMap<>();
    }
    public void addSubject(String subject) {
        subjects.add(subject);
        subjectsGrades.put(subject, new ArrayList<>());
    }
    public void addGrade(String subject, double grade) {
        if (subjectsGrades.containsKey(subject)) {
            subjectsGrades.get(subject).add(grade);
        } else {
            throw new IllegalArgumentException(subject + " not found in list of subjects");
        }
    }
    public double calcAvgForSubject(String subject) {
        if (subjectsGrades.containsKey(subject)) {
            List<Double> grades = subjectsGrades.get(subject);
            double subjectGradeSum = grades.stream().mapToDouble(Double::doubleValue).sum();
            int subjectGradeCount = grades.size();
            if (subjectGradeCount > 0) {
                return Math.round((subjectGradeSum / subjectGradeCount) * 100.0) / 100.0;
            } else {
                throw new IllegalArgumentException("No grades found for subject");
            }
        } else {
            throw new IllegalArgumentException("Subject not in subjects");
        }
    }
    public double calcAvgForAllSubjects() {
        double sum = 0;
        double size = 0;
        for (String subject: subjectsGrades.keySet()) {
            List<Double> grades = subjectsGrades.get(subject);
            sum += grades.stream().mapToDouble(Double::doubleValue).sum();
            size += grades.size();
        }
        return sum / size;
    }
    public List<String> getSubjects() {
        return subjects;
    }
    public Map<String, List<Double>> getGrades() {
        return subjectsGrades;
    }
}
