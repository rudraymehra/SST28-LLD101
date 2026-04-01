import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Starter demo that shows why mutability is risky.
 *
 * After refactor:
 * - direct mutation should not compile (no setters)
 * - external modifications to tags should not affect the ticket
 * - service "updates" should return a NEW ticket instance
 */
public class TryIt {

    public static void main(String[] args)
    {
        TicketService service = new TicketService();

        IncidentTicket original = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created : " + original);

        IncidentTicket assigned = service.assign(original, "agent@example.com");
        System.out.println("\nAssigned : " + assigned);
        System.out.println("Original: " + original);  

        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nEscalated: " + escalated);
        System.out.println("Assigned : " + assigned);  
        try {
            List<String> tags = escalated.getTags();
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("\nERROR: external mutation was allowed!");
        } catch (UnsupportedOperationException e)
    {
            System.out.println("\nExternal tag mutation blocked: " + e.getClass().getSimpleName());
        }

        System.out.println("\nDone — immutability verified.");
    }
}
