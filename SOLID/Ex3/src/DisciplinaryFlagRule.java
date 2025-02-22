// Rule 1: Student must NOT have any disciplinary flag
// OLD: was inside the if/else chain → if (s.disciplinaryFlag != LegacyFlags.NONE) ...
// NEW: its own class. Returns reason if failed, null if passed.

public class DisciplinaryFlagRule implements EligibilityRule {
    public String check(StudentProfile s) {
        if (s.disciplinaryFlag != LegacyFlags.NONE) return "disciplinary flag present";
        return null;
    }
}
