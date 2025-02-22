// Rule 3: Attendance must be >= 75%
// OLD: was → else if (s.attendancePct < 75) { reasons.add("attendance below 75"); }
// NEW: its own class.

public class AttendanceRule implements EligibilityRule {
    public String check(StudentProfile s) {
        if (s.attendancePct < 75) return "attendance below 75";
        return null;
    }
}
