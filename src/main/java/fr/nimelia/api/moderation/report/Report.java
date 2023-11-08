package fr.nimelia.api.moderation.report;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Report {

    private final UUID uniqueId;
    private final ReportType reportType;
    private final UUID owner;
    private final UUID target;
    private final long reportedAt;
    private final String reason;
    private boolean approved;

    public Report(UUID owner, UUID target, String reason, ReportType reportType) {
        this.uniqueId = UUID.randomUUID();
        this.owner = owner;
        this.target = target;
        this.reason = reason;
        this.reportType = reportType;
        this.reportedAt = System.currentTimeMillis();
    }
}
