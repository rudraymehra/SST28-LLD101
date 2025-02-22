// Rule 2: CGR must be >= 8.0
// OLD: was → else if (s.cgr < 8.0) { reasons.add("CGR below 8.0"); }
// NEW: its own class.

public class CgrRule implements EligibilityRule {
    public String check(StudentProfile s) {
        if (s.cgr < 8.0) return "CGR below 8.0";
        return null;
    }
}
