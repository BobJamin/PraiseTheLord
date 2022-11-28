package com.bobjamin.kratosplugin.utils;

import com.bobjamin.kratosplugin.models.Class;
import com.bobjamin.kratosplugin.models.Method;
import com.bobjamin.kratosplugin.models.Metric;
import java.util.*;
import java.util.stream.Collectors;

public class MetricCalculator {

    public static double computeWMC(Class _class) {
        return _class.getMethods().stream().mapToDouble(Method::getJumps).sum();
    }

    public static double computeTotal(List<Metric> metrics) {
        // TODO: add weight on each metric for the total
        return metrics.stream().mapToDouble(Metric::getMetricValue).sum();
    }

    public static double computeTCC(Class c) {

        List<Pair<Method,Method>> pairs = generateMethodPairs(c.getMethods());
        int matchingPairCount = 0;

        if(pairs.size() == 0)
            return 1;

        for(Pair<Method, Method> pair : pairs){
            Set<String> attributeAccessesA = pair.getL().getAttributeAccesses();
            Set<String> attributeAccessesB = pair.getR().getAttributeAccesses();
            if(attributeAccessesA.stream().anyMatch(attributeAccessesB::contains)){
                matchingPairCount++;
            }
        }

        return (double)matchingPairCount / pairs.size();
    }

    public static double computeATFD(Class c) {
        return c.getExternalAccesses().size();
    }

    private static List<Pair<Method,Method>> generateMethodPairs(List<Method> methods) {
        return methods.stream()
                .flatMap(method1 -> methods.stream().filter(m -> m != method1).map(method2 -> new Pair<>(method1,method2)))
                .distinct()
                .collect(Collectors.toList());
    }
}
