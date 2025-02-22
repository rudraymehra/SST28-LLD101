import java.util.*;

// ============================================================
// MODIFIED — FakeDb
// ============================================================
// WHAT CHANGED: Added "implements StudentRepository"
// THAT'S IT. The code inside is EXACTLY the same.
//
// WHY:  Now FakeDb is saying "I follow the StudentRepository contract."
//       OnboardingService talks to the StudentRepository INTERFACE,
//       and FakeDb is just ONE implementation of it.
//       Tomorrow you can make RealDb implements StudentRepository
//       and swap it in without touching OnboardingService.
//
// OLD:  public class FakeDb {
// NEW:  public class FakeDb implements StudentRepository {
// ============================================================

public class FakeDb implements StudentRepository {
    private final List<StudentRecord> rows = new ArrayList<>();

    public void save(StudentRecord r) { rows.add(r); }
    public int count() { return rows.size(); }
    public List<StudentRecord> all() { return Collections.unmodifiableList(rows); }
}
