package com.buschmais.jqassistant.plugin.junit4.test.rule;

import static com.buschmais.jqassistant.plugin.java.test.matcher.MethodDescriptorMatcher.methodDescriptor;
import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.buschmais.jqassistant.core.analysis.api.AnalysisException;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;
import com.buschmais.jqassistant.plugin.junit4.api.scanner.JunitScope;
import com.buschmais.jqassistant.plugin.junit4.test.set.Example;
import com.buschmais.jqassistant.plugin.junit4.test.set.IgnoredTestClass;
import com.buschmais.jqassistant.plugin.junit4.test.set.TestClass;

/**
 * Tests for Junit4 concepts.
 */
public class Junit4IT extends AbstractJavaPluginIT {

    /**
     * Verifies the concept "junit4:TestClassOrMethod".
     * 
     * @throws IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     * @throws NoSuchMethodException
     *             If the test fails.
     */
    @Test
    public void testClassOrMethod() throws IOException, AnalysisException, NoSuchMethodException {
        scanClasses(TestClass.class);
        applyConcept("junit4:TestClassOrMethod");
        store.beginTransaction();
        assertThat(query("MATCH (m:Method:Junit4:Test) RETURN m").getColumn("m"), hasItem(methodDescriptor(TestClass.class, "activeTestMethod")));
        assertThat(query("MATCH (c:Type:Class:Junit4:Test) RETURN c").getColumn("c"), hasItem(typeDescriptor(TestClass.class)));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "junit4:IgnoreTestClassOrMethod".
     * 
     * @throws IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     * @throws NoSuchMethodException
     *             If the test fails.
     */
    @Test
    public void ignoreTestClassOrMethod() throws IOException, AnalysisException, NoSuchMethodException {
        scanClasses(IgnoredTestClass.class);
        applyConcept("junit4:IgnoreTestClassOrMethod");
        store.beginTransaction();
        assertThat(query("MATCH (m:Method:Junit4:Ignore) RETURN m").getColumn("m"), hasItem(methodDescriptor(IgnoredTestClass.class, "ignoredTestMethod")));
        assertThat(query("MATCH (c:Type:Class:Junit4:Ignore) RETURN c").getColumn("c"), hasItem(typeDescriptor(IgnoredTestClass.class)));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "junit4:TestCaseImplementedByMethod".
     * 
     * @throws IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     * @throws NoSuchMethodException
     *             If the test fails.
     */
    @Test
    public void testCaseImplementedByMethod() throws IOException, AnalysisException, NoSuchMethodException {
        scanClasses(Example.class);
        scanResource(JunitScope.TESTREPORTS, "/TEST-com.buschmais.jqassistant.plugin.junit4.test.set.Example.xml");
        applyConcept("junit4:TestCaseImplementedByMethod");
        store.beginTransaction();
        verifyTestCaseImplementedByMethod("success");
        verifyTestCaseImplementedByMethod("failure");
        verifyTestCaseImplementedByMethod("error");
        verifyTestCaseImplementedByMethod("skipped");
        store.commitTransaction();
    }

    /**
     * Verifies if a IMPLEMENTED_BY relation exists between a test case and and
     * test method.
     * 
     * @param testcase
     *            The name of the test case.
     * @throws NoSuchMethodException
     *             If the test fails.
     */
    private void verifyTestCaseImplementedByMethod(String testcase) throws NoSuchMethodException {
        assertThat(query("MATCH (testcase:TestCase)-[:IMPLEMENTED_BY]->(testmethod:Method) WHERE testcase.name ='" + testcase + "' RETURN testmethod")
                .getColumn("testmethod"), hasItem(methodDescriptor(Example.class, testcase)));
    }
}
