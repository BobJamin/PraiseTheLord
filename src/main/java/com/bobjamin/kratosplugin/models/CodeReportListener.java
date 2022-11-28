package com.bobjamin.kratosplugin.models;

import java.util.List;

public interface CodeReportListener {
    void displayError(String message);
    void update(List<CodeReport> codeReports);
}
