import java.util.*;

public class EligibilityEngine {

    // =================================================================
    //  OLD CODE — giant if/else chain (OCP violation)
    // =================================================================
    // private final FakeEligibilityStore store;
    //
    // public EligibilityEngine(FakeEligibilityStore store) { this.store = store; }
    //
    // public EligibilityEngineResult evaluate(StudentProfile s) {
    //     List<String> reasons = new ArrayList<>();
    //     String status = "ELIGIBLE";
    //
    //     // EVERY rule is an if/else here — to add a new rule you EDIT this method
    //     if (s.disciplinaryFlag != LegacyFlags.NONE) {       ← rule 1 hard-coded
    //         status = "NOT_ELIGIBLE";
    //         reasons.add("disciplinary flag present");
    //     } else if (s.cgr < 8.0) {                           ← rule 2 hard-coded
    //         status = "NOT_ELIGIBLE";
    //         reasons.add("CGR below 8.0");
    //     } else if (s.attendancePct < 75) {                   ← rule 3 hard-coded
    //         status = "NOT_ELIGIBLE";
    //         reasons.add("attendance below 75");
    //     } else if (s.earnedCredits < 20) {                   ← rule 4 hard-coded
    //         status = "NOT_ELIGIBLE";
    //         reasons.add("credits below 20");
    //     }
    //
    //     return new EligibilityEngineResult(status, reasons);
    // }
    //
    // PROBLEM: Adding rule 5 means editing THIS method. That's OCP violation.
    // =================================================================


    // =================================================================
    //  NEW CODE — loop over rule objects (OCP compliant)
    // =================================================================

    private final List<EligibilityRule> rules;   // list of rule objects
    private final FakeEligibilityStore store;

    // Rules are INJECTED from outside — engine doesn't know or care
    // how many rules there are or what they check.
    public EligibilityEngine(List<EligibilityRule> rules, FakeEligibilityStore store) {
        this.rules = rules;
        this.store = store;
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s);
        p.print(s, r);
        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        // LOOK — no if/else chain anymore!
        // Just loop through whatever rules were given to us.
        // To add rule 5: create a new class, add to list in Main. DONE.
        // This method NEVER changes.
        for (EligibilityRule rule : rules) {
            String reason = rule.check(s);
            if (reason != null) {
                status = "NOT_ELIGIBLE";
                reasons.add(reason);
                break;   // stop at first failure (same as old else-if behavior)
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;
    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}
