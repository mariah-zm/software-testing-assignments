package org.productalerter.model.eventlog;

import org.productalerter.model.domain.CategoryEnum;

public enum EventLogTypeEnum {

    ALERT_CREATED(0),
    ALERTS_DELETED(1),
    USER_VALID_LOGIN(5),
    USER_LOGGED_OUT(6),
    USER_VIEWED_ALERTS(7);

    private final int value;

    EventLogTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
