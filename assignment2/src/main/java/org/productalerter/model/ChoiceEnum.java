package org.productalerter.model;

public enum ChoiceEnum {
    LOG_IN(1),
    LOG_OUT(2),
    ADD_ALERT(3),
    DELETE_ALERTS(4),
    VIEW_ALERTS(5),
    EXIT(6);

    private final int value;

    ChoiceEnum(int value) {
        this.value = value;
    }

    public static ChoiceEnum getEnumValue(int value) {
        for (ChoiceEnum choice: ChoiceEnum.values()) {
            if (choice.value == value) {
                return choice;
            }
        }
        return null;
    }
}
