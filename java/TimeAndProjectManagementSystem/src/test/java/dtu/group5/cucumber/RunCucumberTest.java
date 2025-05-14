package dtu.group5.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.SNIPPET_TYPE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("dtu/group5/cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "summary")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "dtu/group5/cucumber")
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
public class RunCucumberTest {}
