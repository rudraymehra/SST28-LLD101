import java.util.*;

// ============================================================
// NEW CLASS — StudentValidator
// ============================================================
// WHAT:  Takes the parsed fields and checks if they are valid.
//        Returns a list of error messages (empty list = all good).
// WHY:   Validation rules were jammed inside OnboardingService (lines 25-29).
//        Mixed with printing and saving logic. Bad idea.
//        Now validation is ISOLATED. You can test it without running
//        the whole program. You can add new rules without touching
//        printing or database code.
// BENEFIT: Teacher says "add city validation" → you ONLY open this file.
// ============================================================

public class StudentValidator {

    public List<String> validate(Map<String, String> fields) {
        String name    = fields.getOrDefault("name", "");
        String email   = fields.getOrDefault("email", "");
        String phone   = fields.getOrDefault("phone", "");
        String program = fields.getOrDefault("program", "");

        List<String> errors = new ArrayList<>();

        if (name.isBlank())
            errors.add("name is required");

        if (email.isBlank() || !email.contains("@"))
            errors.add("email is invalid");

        if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit))
            errors.add("phone is invalid");

        if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE")))
            errors.add("program is invalid");

        return errors;  // empty list = no errors = student is valid
    }
}
