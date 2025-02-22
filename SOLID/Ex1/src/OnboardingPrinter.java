import java.util.List;

// ============================================================
// NEW CLASS — OnboardingPrinter (extracted from OnboardingService)
// ============================================================
// WHAT:  Handles ALL console printing for the onboarding flow.
// WHY:   System.out.println() calls were SCATTERED all over
//        OnboardingService. Printing is NOT registration logic.
//        If tomorrow you want to send an email instead of
//        printing to console, you ONLY change this class.
// BENEFIT: OnboardingService becomes clean business logic,
//          no formatting/display noise.
// ============================================================

public class OnboardingPrinter {

    public void printInput(String raw) {
        System.out.println("INPUT: " + raw);
    }

    public void printErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) System.out.println("- " + e);
    }

    public void printConfirmation(String id, int total, StudentRecord rec) {
        System.out.println("OK: created student " + id);
        System.out.println("Saved. Total students: " + total);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }
}
