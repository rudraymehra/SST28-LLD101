// ============================================================
// NEW INTERFACE — EligibilityRule
// ============================================================
// WHAT:  A contract — every rule must have a check() method.
//        check() takes a StudentProfile and returns:
//          - a reason string like "CGR below 8.0" if FAILED
//          - null if PASSED (no problem)
//
// WHY:   OLD code had a giant if/else chain in EligibilityEngine.
//        To add a new rule, you had to EDIT that chain — violating OCP.
//        NOW each rule is a separate class implementing this interface.
//        To add a new rule: make a new class, add it to the list. DONE.
//        Engine code stays UNTOUCHED.
//
// THIS IS OCP:  Open for extension (add new rule classes)
//               Closed for modification (engine doesn't change)
// ============================================================

public interface EligibilityRule {
    String check(StudentProfile s);
}
