import systems.danger.kotlin.*

danger(args) {

    // ✅ Basic PR Info
    val pr = github.pullRequest
    message("🚀 Running checks for PR: #${pr.number} - ${pr.title}")

    // 👀 Check for description
    if (pr.body.isNullOrBlank()) {
        warn("⚠️ Please add a PR description for better context.")
    }

    // ✅ Parse Lint Report
    val lintReport = File("app/build/reports/lint-results-debug.xml")
    if (lintReport.exists()) {
        val lintContent = lintReport.readText()
        val issues = Regex("<issue").findAll(lintContent).count()

        if (issues > 0) {
            warn("⚠️ Android Lint found **$issues issues**. Please review the Lint report.")
            markdown("👉 [View Lint Report](app/build/reports/lint-results-debug.html)")
        }
    } else {
        message("✅ No Lint report found (skipped).")
    }

    // ✅ Parse Detekt Report
    val detektReport = File("app/build/reports/detekt/detekt.xml")
    if (detektReport.exists()) {
        val detektContent = detektReport.readText()
        val issues = Regex("<error").findAll(detektContent).count()

        if (issues > 0) {
            fail("❌ Detekt found **$issues issues**. Please fix before merge.")
            markdown("👉 [View Detekt Report](app/build/reports/detekt/detekt.html)")
        }
    } else {
        message("✅ No Detekt report found (skipped).")
    }

    // ✅ Unit test reminder
    val testReport = File("app/build/test-results/testDebugUnitTest/TEST-*.xml")
    if (!testReport.exists()) {
        warn("⚠️ Unit test results not found. Did tests run?")
    }

    // ✅ Small safety rule: PR size
    val additions = pr.additions ?: 0
    val deletions = pr.deletions ?: 0
    if (additions + deletions > 500) {
        warn("⚠️ PR is quite large (${additions + deletions} changes). Consider splitting it.")
    }
}
