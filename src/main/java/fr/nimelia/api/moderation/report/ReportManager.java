package fr.nimelia.api.moderation.report;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import fr.nimelia.api.CommonAPI;
import lombok.Getter;
import org.bson.Document;

import java.util.*;

@Getter
public class ReportManager {

    private final CommonAPI api;
    private final Map<UUID, Report> reports = new HashMap<>();

    public ReportManager(CommonAPI api) {
        this.api = api;
        this.loadReports();
    }

    private void loadReports() {
        for (Document document: getCollection().find()) {
            Gson gson = CommonAPI.GSON;
            Report report = gson.fromJson(document.toJson(), Report.class);
            reports.put(report.getUniqueId(), report);
        }
    }

    public void createReport(UUID owner, UUID target, String reason, ReportType reportType){
        if (isReportExists(owner, target)) return;

        Report report = new Report(owner, target, reason, reportType);
        Gson gson = CommonAPI.GSON;
        String json = gson.toJson(report);
        Document document = Document.parse(json);
        getCollection().insertOne(document);
        reports.put(report.getUniqueId(), report);
    }

    public void updateReport(Report report) {
        if (!isReportExists(report.getOwner(), report.getTarget())) return;

        Gson gson = CommonAPI.GSON;
        String json = gson.toJson(report.getUniqueId());
        Document document = Document.parse(json);
        getCollection().updateOne(Filters.eq("uuid", report.getUniqueId().toString()), Updates.set("json", document));
        reports.put(report.getUniqueId(), report);
    }

    public void approveReport(UUID owner, UUID target){
        Report report = getReport(owner, target);
        if (report == null) return;

        report.setApproved(true);
        updateReport(report);
    }

    public Report getReport(UUID owner, UUID target){
        if (!isReportExists(owner, target)) return null;
        for (Report report : reports.values()) {
            if (report != null && !report.isApproved() && report.getOwner().equals(owner) && report.getTarget().equals(target)) {
                return report;
            }
        }
        return null;
    }

    public List<Report> getReportsByOwner(UUID owner){
        List<Report> list = new ArrayList<>();
        for (Report report : reports.values()) {
            if (report != null && report.getOwner().equals(owner)) {
                list.add(report);
            }
        }
        return list;
    }

    public List<Report> getReportsByTarget(UUID target){
        List<Report> list = new ArrayList<>();
        for (Report report : reports.values()) {
            if (report != null && report.getTarget().equals(target)) {
                list.add(report);
            }
        }
        return list;
    }

    public List<Report> getApprovedReportsByOwner(UUID owner){
        List<Report> list = new ArrayList<>();
        for (Report report : getReportsByTarget(owner)) {
            if (report.isApproved()) {
                list.add(report);
            }
        }
        return list;
    }

    public boolean isReportExists(UUID owner, UUID target) {
        for (Report report : reports.values()) {
            if (report != null &&
                    !report.isApproved() &&
                    report.getOwner().equals(owner) &&
                    report.getTarget().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public MongoCollection<Document> getCollection() {
        return api.getMongo().getCollection("reports");
    }

}
