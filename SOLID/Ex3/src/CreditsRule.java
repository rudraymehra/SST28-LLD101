// Rule 4: Earned credits must be >= 20
// OLD: was → else if (s.earnedCredits < 20) { reasons.add("credits below 20"); }
// NEW: its own class.

public class CreditsRule implements EligibilityRule {
    public String check(StudentProfile s) {
        if (s.earnedCredits < 20) return "credits below 20";
        return null;
    }
}
