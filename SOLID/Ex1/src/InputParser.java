import java.util.*;

// ============================================================
// NEW CLASS — InputParser (extracted from OnboardingService)
// ============================================================
// WHAT:  Takes the ugly raw string and converts it into a clean Map.
// WHY:   Parsing was happening INSIDE OnboardingService.
//        That's not OnboardingService's job. Its job is to ORCHESTRATE,
//        not to parse strings. So we pulled parsing OUT into its own class.
// BENEFIT: If tomorrow the input format changes (say JSON instead of
//          key=value), you ONLY change THIS file. Nothing else breaks.
// ============================================================

public class InputParser {

    // Takes: "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE"
    // Returns: {name=Riya, email=riya@sst.edu, phone=9876543210, program=CSE}
    public Map<String, String> parse(String raw) {
        Map<String, String> kv = new LinkedHashMap<>();
        String[] parts = raw.split(";");
        for (String p : parts) {
            String[] t = p.split("=", 2);
            if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        }
        return kv;
    }
}
