import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");
        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);

        // ===== OLD: engine had rules hard-coded inside =====
        // EligibilityEngine engine = new EligibilityEngine(new FakeEligibilityStore());

        // ===== NEW: we CREATE rule objects and pass them as a list =====
        // ORDER MATTERS — they are checked in this order (same as the old if/else chain)
        // Want to add a 5th rule? Just add:  new BacklogRule()  to this list.
        // Engine code stays UNTOUCHED. That's OCP!
        List<EligibilityRule> rules = List.of(
                new DisciplinaryFlagRule(),
                new CgrRule(),
                new AttendanceRule(),
                new CreditsRule()
        );

        EligibilityEngine engine = new EligibilityEngine(rules, new FakeEligibilityStore());
        engine.runAndPrint(s);
    }
}
