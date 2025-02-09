package com.ninjagame.ninjagame.domain.ninja;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum NinjaClass {
    ASSASSIN, MARKSMAN, SAMURAI;

    public static List<NinjaClass> convertAllowedNinjaClasses(String classesString) {
        if (classesString == null || classesString.trim().isEmpty()) {
            return List.of();
        }

        return Arrays.stream(classesString.split("\\s*,\\s*"))
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(NinjaClass::isValidClass)
                .map(NinjaClass::valueOf)
                .collect(Collectors.toList());
    }




    public static String convertToString(List<NinjaClass> classes) {
        return classes.stream().map(NinjaClass::name).collect(Collectors.joining(","));
    }

    public static boolean isValidClass(String className) {
        String trimmed = className.trim();
        return Arrays.stream(NinjaClass.values())
                .map(Enum::name)
                .anyMatch(valid -> valid.equalsIgnoreCase(trimmed));
    }



    public String toPrettyString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
