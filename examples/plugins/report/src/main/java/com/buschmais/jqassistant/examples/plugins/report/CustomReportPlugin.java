package com.buschmais.jqassistant.examples.plugins.report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.buschmais.jqassistant.core.analysis.api.AnalysisListenerException;
import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.Concept;
import com.buschmais.jqassistant.core.analysis.api.rule.Constraint;
import com.buschmais.jqassistant.core.analysis.api.rule.Group;
import com.buschmais.jqassistant.core.analysis.api.rule.Rule;
import com.buschmais.jqassistant.core.report.api.ReportPlugin;
import com.buschmais.jqassistant.plugin.java.impl.store.descriptor.TypeDescriptor;

public class CustomReportPlugin implements ReportPlugin {

    private static final String PROPERTY_FILENAME = "customReport.fileName";

    private String fileName;

    @Override
    public void initialize(Map<String, Object> properties) throws AnalysisListenerException {
        this.fileName = (String) properties.get(PROPERTY_FILENAME);
        if (this.fileName == null) {
            throw new AnalysisListenerException("Property " + PROPERTY_FILENAME + " is not specified.");
        }
    }

    @Override
    public void begin() throws AnalysisListenerException {
    }

    @Override
    public void end() throws AnalysisListenerException {
    }

    @Override
    public void beginConcept(Concept concept) throws AnalysisListenerException {
    }

    @Override
    public void endConcept() throws AnalysisListenerException {
    }

    @Override
    public void beginGroup(Group group) throws AnalysisListenerException {
    }

    @Override
    public void endGroup() throws AnalysisListenerException {
    }

    @Override
    public void beginConstraint(Constraint constraint) throws AnalysisListenerException {
    }

    @Override
    public void endConstraint() throws AnalysisListenerException {
    }

    @Override
    public void setResult(Result<? extends Rule> result) throws AnalysisListenerException {
        Rule rule = result.getRule();
        if (rule instanceof Concept && "example:MethodsPerType".equals(rule.getId())) {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(fileName));
                writer.println("Methods per Type");
                writer.println("================");
                for (Map<String, Object> row : result.getRows()) {
                    TypeDescriptor type = (TypeDescriptor) row.get("Type");
                    Long methodCount = (Long) row.get("MethodCount");
                    writer.println(type.getFullQualifiedName() + ":" + methodCount);
                }
                writer.close();
            } catch (IOException e) {
                throw new AnalysisListenerException("Cannot write custom report.", e);
            }
        }
    }
}