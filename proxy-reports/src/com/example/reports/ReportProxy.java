package com.example.reports;

/**
 * TODO (student):
 * Implement Proxy responsibilities here:
 * - access check
 * - lazy loading
 * - caching of RealReport within the same proxy
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    private RealReport cachedReport = null;

    public ReportProxy(String reportId, String title, String classification)
    {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user)
    {
        // step 1: check if user has permission
        if (!accessControl.canAccess(user, classification))
    {
            System.out.println("[ACCESS DENIED] " + user.getName()
                    + " (" + user.getRole() + ") cannot view "
                    + classification + " report: " + title);
            return;
        }

        // step 2: lazy load - only create real report on first allowed access
        if (cachedReport == null)
    {
            System.out.println("[proxy] first access, creating real report...");
            cachedReport = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[proxy] using cached report (no disk load)");
        }

        // step 3: delegate to the real report
        cachedReport.display(user);
    }
}
