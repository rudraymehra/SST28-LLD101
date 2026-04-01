package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 *
 * Current Implementation Details:
 * - mutable fields
 * - multiple constructors
 * - public setters
 * - tags list can be modified from outside
 * - validation is scattered elsewhere
 *
 * TODO (student): refactor to immutable + Builder.
 */
public final class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority;          // LOW, MEDIUM, HIGH, CRITICAL
    private final List<String> tags;
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;
    private final String source;            // e.g. "CLI", "WEBHOOK", "EMAIL"

    // ── private constructor — only the Builder may call this ──────────
    private IncidentTicket(Builder b)
    {
        this.id              = b.id;
        this.reporterEmail   = b.reporterEmail;
        this.title           = b.title;
        this.description     = b.description;
        this.priority        = b.priority;
        this.tags            = Collections.unmodifiableList(new ArrayList<>(b.tags));
        this.assigneeEmail   = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes      = b.slaMinutes;
        this.source          = b.source;
    }

    // ── Getters (no setters!) ────────────────────────────────────────
    public String       getId()
    { return id; }
    public String       getReporterEmail()
    { return reporterEmail; }
    public String       getTitle()
    { return title; }
    public String       getDescription()
    { return description; }
    public String       getPriority()
    { return priority; }
    public List<String> getTags()
    { return tags; }   // already unmodifiable
    public String       getAssigneeEmail()
    { return assigneeEmail; }
    public boolean      isCustomerVisible()
    { return customerVisible; }
    public Integer      getSlaMinutes()
    { return slaMinutes; }
    public String       getSource()
    { return source; }

    // ── Convenience: create a pre-populated Builder from this ticket ─
    public Builder toBuilder()
    {
        Builder b = new Builder(this.id, this.reporterEmail, this.title);
        b.description(this.description)
         .priority(this.priority)
         .tags(new ArrayList<>(this.tags))
         .assigneeEmail(this.assigneeEmail)
         .customerVisible(this.customerVisible)
         .slaMinutes(this.slaMinutes)
         .source(this.source);
        return b;
    }

    // ── Builder ──────────────────────────────────────────────────────
    public static class Builder {

        // required
        private final String id;
        private final String reporterEmail;
        private final String title;

        // optional (sensible defaults)
        private String       description     = null;
        private String       priority        = null;
        private List<String> tags            = new ArrayList<>();
        private String       assigneeEmail   = null;
        private boolean      customerVisible = false;
        private Integer      slaMinutes      = null;
        private String       source          = null;

        public Builder(String id, String reporterEmail, String title)
    {
            this.id            = id;
            this.reporterEmail = reporterEmail;
            this.title         = title;
        }

        // ── fluent optional setters ─────────────────────────────────
        public Builder description(String description)
    { this.description     = description;     return this; }
        public Builder priority(String priority)
    { this.priority        = priority;        return this; }
        public Builder tags(List<String> tags)
    { this.tags            = tags == null ? new ArrayList<>() : new ArrayList<>(tags); return this; }
        public Builder assigneeEmail(String assigneeEmail)
    { this.assigneeEmail   = assigneeEmail;   return this; }
        public Builder customerVisible(boolean visible)
    { this.customerVisible = visible;         return this; }
        public Builder slaMinutes(Integer slaMinutes)
    { this.slaMinutes      = slaMinutes;      return this; }
        public Builder source(String source)
    { this.source          = source;          return this; }

        public Builder addTag(String tag)
    {
            if (tag != null) this.tags.add(tag);
            return this;
        }

        // ── build() — single place for ALL validation ───────────────
        public IncidentTicket build()
    {
            // --- required fields ---
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");

            // --- optional fields ---
            Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");
            Validation.requireOptionalEmail(assigneeEmail, "assigneeEmail");
            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");

            return new IncidentTicket(this);
        }
    }

    @Override
    public String toString()
    {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }
}
