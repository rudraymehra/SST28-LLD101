package com.example.reports;

/**
 * Starter demo.
 *
 * CURRENT BEHAVIOR:
 * - Everyone can open everything
 * - Disk load happens on every call
 *
 * AFTER REFACTOR:
 * - Client code should use ReportProxy
 * - Unauthorized access should be blocked
 * - Real report should load lazily and ideally once per proxy
 */
public class App {

    public static void main(String[] args)
    {
        // create users with different roles
        User student = new User("Jasleen", "STUDENT");
        User faculty = new User("Prof. Noor", "FACULTY");
        User admin = new User("Kshitij", "ADMIN");

        // now we use ReportProxy instead of ReportFile directly
        // the proxy wraps the real report and adds access control + lazy loading
        Report publicReport = new ReportProxy("R-101", "Orientation Plan", "PUBLIC");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review", "FACULTY");
        Report adminReport = new ReportProxy("R-303", "Budget Audit", "ADMIN");

        ReportViewer viewer = new ReportViewer();

        System.out.println("=== CampusVault Demo ===");

        // student can see public report - should load from disk
        viewer.open(publicReport, student);
        System.out.println();

        // student tries to open faculty report - should be denied
        viewer.open(facultyReport, student);
        System.out.println();

        // faculty opens faculty report - allowed, loads from disk
        viewer.open(facultyReport, faculty);
        System.out.println();

        // admin opens admin report - allowed, loads from disk
        viewer.open(adminReport, admin);
        System.out.println();

        // admin opens same report again - should use cache, no disk load
        viewer.open(adminReport, admin);
    }
}
