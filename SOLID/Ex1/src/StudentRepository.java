import java.util.*;

// ============================================================
// NEW INTERFACE — StudentRepository
// ============================================================
// WHAT:  A CONTRACT that says "whoever implements me must have
//        save(), count(), and all() methods."
// WHY:   OnboardingService was directly talking to FakeDb.
//        That means OnboardingService KNOWS it's a fake database.
//        That's tight coupling — bad.
//        Now OnboardingService talks to this INTERFACE instead.
//        It doesn't care if behind the scenes it's FakeDb, MySQL,
//        MongoDB, or a text file. It just calls save() and count().
// BENEFIT: Swap databases without touching OnboardingService AT ALL.
// ============================================================

public interface StudentRepository {
    void save(StudentRecord r);
    int count();
    List<StudentRecord> all();
}
