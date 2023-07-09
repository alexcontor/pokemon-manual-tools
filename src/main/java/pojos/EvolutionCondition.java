package pojos;

import enums.EvolutionConditionsEnum;

public class EvolutionCondition {

    EvolutionConditionsEnum mainCondition;
    String mainConditionRef;

    EvolutionConditionsEnum secondaryCondition;
    String secondaryConditionRef;

    public EvolutionCondition(EvolutionConditionsEnum mainCondition, String mainConditionRef, EvolutionConditionsEnum secondaryCondition, String secondaryConditionRef) {
        this.mainCondition = mainCondition;
        this.mainConditionRef = mainConditionRef;
        this.secondaryCondition = secondaryCondition;
        this.secondaryConditionRef = secondaryConditionRef;
    }

    public EvolutionConditionsEnum getMainCondition() {
        return mainCondition;
    }

    public void setMainCondition(EvolutionConditionsEnum mainCondition) {
        this.mainCondition = mainCondition;
    }

    public String getMainConditionRef() {
        return mainConditionRef;
    }

    public void setMainConditionRef(String mainConditionRef) {
        this.mainConditionRef = mainConditionRef;
    }

    public EvolutionConditionsEnum getSecondaryCondition() {
        return secondaryCondition;
    }

    public void setSecondaryCondition(EvolutionConditionsEnum secondaryCondition) {
        this.secondaryCondition = secondaryCondition;
    }

    public String getSecondaryConditionRef() {
        return secondaryConditionRef;
    }

    public void setSecondaryConditionRef(String secondaryConditionRef) {
        this.secondaryConditionRef = secondaryConditionRef;
    }
}
