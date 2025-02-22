import java.util.*;

public class OnboardingService {

    // =================================================================
    //  OLD CODE (the "god class" version — everything crammed in one)
    // =================================================================
    // private final FakeDb db;
    //
    // public OnboardingService(FakeDb db) { this.db = db; }
    //
    // public void registerFromRawInput(String raw) {
    //     System.out.println("INPUT: " + raw);                          ← printing
    //
    //     Map<String,String> kv = new LinkedHashMap<>();                 ← parsing
    //     String[] parts = raw.split(";");                               ← parsing
    //     for (String p : parts) {                                       ← parsing
    //         String[] t = p.split("=", 2);                              ← parsing
    //         if (t.length == 2) kv.put(t[0].trim(), t[1].trim());      ← parsing
    //     }                                                              ← parsing
    //
    //     String name = kv.getOrDefault("name", "");
    //     String email = kv.getOrDefault("email", "");
    //     String phone = kv.getOrDefault("phone", "");
    //     String program = kv.getOrDefault("program", "");
    //
    //     List<String> errors = new ArrayList<>();                       ← validation
    //     if (name.isBlank()) errors.add("name is required");            ← validation
    //     if (email.isBlank() || !email.contains("@"))                   ← validation
    //         errors.add("email is invalid");                            ← validation
    //     if (phone.isBlank() || !phone.chars()                          ← validation
    //         .allMatch(Character::isDigit))                             ← validation
    //         errors.add("phone is invalid");                            ← validation
    //     if (!(program.equals("CSE") || program.equals("AI")           ← validation
    //         || program.equals("SWE")))                                 ← validation
    //         errors.add("program is invalid");                          ← validation
    //
    //     if (!errors.isEmpty()) {                                       ← printing
    //         System.out.println("ERROR: cannot register");              ← printing
    //         for (String e : errors) System.out.println("- " + e);     ← printing
    //         return;
    //     }
    //
    //     String id = IdUtil.nextStudentId(db.count());
    //     StudentRecord rec = new StudentRecord(id, name, email, phone, program);
    //
    //     db.save(rec);                                                  ← saving
    //
    //     System.out.println("OK: created student " + id);              ← printing
    //     System.out.println("Saved. Total students: " + db.count());   ← printing
    //     System.out.println("CONFIRMATION:");                           ← printing
    //     System.out.println(rec);                                       ← printing
    // }
    //
    // SEE THE PROBLEM? ↑↑↑  Parsing, validation, saving, printing
    // — ALL jumbled into ONE method. That violates SRP.
    // =================================================================


    // =================================================================
    //  NEW CODE (SRP version — each job is done by a separate class)
    // =================================================================

    private final InputParser parser;             // Job: parse raw string
    private final StudentValidator validator;     // Job: validate fields
    private final StudentRepository repo;         // Job: save/read data (interface!)
    private final OnboardingPrinter printer;      // Job: print output

    public OnboardingService(InputParser parser, StudentValidator validator,
                             StudentRepository repo, OnboardingPrinter printer) {
        this.parser = parser;
        this.validator = validator;
        this.repo = repo;
        this.printer = printer;
    }

    // NOW LOOK HOW CLEAN THIS METHOD IS:
    // It just ORCHESTRATES — calls others in order, does no real work itself.
    public void registerFromRawInput(String raw) {
        printer.printInput(raw);

        Map<String, String> fields = parser.parse(raw);

        List<String> errors = validator.validate(fields);
        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(repo.count());
        StudentRecord rec = new StudentRecord(id, fields.get("name"), fields.get("email"),
                fields.get("phone"), fields.get("program"));
        repo.save(rec);
        printer.printConfirmation(id, repo.count(), rec);
    }
}
