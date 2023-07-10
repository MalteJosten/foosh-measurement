package com.vs.foosh.api.model;

import java.util.ArrayList;
import java.util.List;

import com.vs.foosh.api.exceptions.EnvironmentalVariableNotFoundException;

public class EnvironmentVariableList {
    private static List<EnvironmentVariable> variables;
    
    public static List<EnvironmentVariable> getInstance() {
        if (variables == null) {
            variables = new ArrayList<EnvironmentVariable>();
        }

        return variables;
    }

    public static void setVariables(List<EnvironmentVariable> variableList) {
        if (variables != null) {
            clearVariables();
        }

        getInstance().addAll(variableList);
    }

    public void pushVariable(EnvironmentVariable variable) {
        // TODO: Pre-processing? Checks?
        getInstance().add(variable);
    }

    public static List<EnvironmentVariable> getVariables() {
        return getInstance();
    }

    public static void clearVariables() {
        getInstance().clear();
    }

    public static EnvironmentVariable getVariable(String id) {
        for (EnvironmentVariable variable: getVariables()) {
            if (variable.getId().toString().equals(id)) {
                return variable;
            }
        }

        throw new EnvironmentalVariableNotFoundException(id);
    }

    public static void deleteVariable(String id) {
        // TODO: Implement
    }
}
