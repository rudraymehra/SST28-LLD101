public class Main {
    public static void main(String[] args) {
        System.out.println("=== Student Onboarding ===");

        FakeDb db = new FakeDb();

        // ===== OLD: just passed db directly =====
        // OnboardingService svc = new OnboardingService(db);

        // ===== NEW: create each helper, then wire them into OnboardingService =====
        InputParser parser          = new InputParser();
        StudentValidator validator  = new StudentValidator();
        OnboardingPrinter printer   = new OnboardingPrinter();

        // db IS a StudentRepository (because FakeDb implements StudentRepository)
        OnboardingService svc = new OnboardingService(parser, validator, db, printer);

        String raw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        svc.registerFromRawInput(raw);

        System.out.println();
        System.out.println("-- DB DUMP --");
        System.out.print(TextTable.render3(db));
    }
}
