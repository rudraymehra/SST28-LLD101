package com.example.tickets;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer that creates tickets.
 *
 * Current Implementation Details:
 * - creates partially valid objects
 * - mutates after creation (bad for auditability)
 * - validation is scattered & incomplete
 *
 * TODO (student):
 * - After introducing immutable IncidentTicket + Builder, refactor this to stop mutating.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title)
    {
        return new IncidentTicket.Builder(id, reporterEmail, title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .addTag("NEW")
                .build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket ticket)
    {
        List<String> updatedTags = new ArrayList<>(ticket.getTags());
        updatedTags.add("ESCALATED");

        return ticket.toBuilder()
                .priority("CRITICAL")
                .tags(updatedTags)
                .build();
    }

    public IncidentTicket assign(IncidentTicket ticket, String assigneeEmail)
    {
        return ticket.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}
