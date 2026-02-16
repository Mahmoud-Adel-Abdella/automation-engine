package engine.run;

import engine.utils.ConfigManager;
import org.testng.IAlterSuiteListener;
import org.testng.xml.*;

import java.util.*;

public class DynamicSuiteBuilder implements IAlterSuiteListener {

    @Override
    public void alter(List<XmlSuite> suites) {

        RunManager.initialize();
        ConfigManager.initialize();

        String flowName = System.getProperty("flow");

        if (flowName == null) {
            throw new RuntimeException("No flow specified");
        }

        XmlSuite suite = suites.get(0);
        suite.setPreserveOrder(true);

        suite.getTests().clear();

        XmlTest test = new XmlTest(suite);
        test.setName(flowName);
        test.setPreserveOrder(true);

        List<XmlClass> xmlClasses = buildClassesFromFlow(flowName);

        test.setXmlClasses(xmlClasses);
    }

    private List<XmlClass> buildClassesFromFlow(String flowName) {

        List<XmlClass> classes = new ArrayList<>();

        var flowNode = ConfigManager.getFlow(flowName);

        Map<String, List<String>> classMethodMap = new LinkedHashMap<>();

        flowNode.get("tests").forEach(node -> {

            String fullMethod = node.asText();

            int lastDot = fullMethod.lastIndexOf(".");
            String className = fullMethod.substring(0, lastDot);
            String methodName = fullMethod.substring(lastDot + 1);

            classMethodMap
                    .computeIfAbsent(className, k -> new ArrayList<>())
                    .add(methodName);
        });

        for (var entry : classMethodMap.entrySet()) {

            XmlClass xmlClass = new XmlClass(entry.getKey());

            List<XmlInclude> includedMethods = new ArrayList<>();

            int index = 0;
            for (String method : entry.getValue()) {
                includedMethods.add(new XmlInclude(method, index++));
            }

            xmlClass.setIncludedMethods(includedMethods);

            classes.add(xmlClass);
        }

        return classes;
    }
}
