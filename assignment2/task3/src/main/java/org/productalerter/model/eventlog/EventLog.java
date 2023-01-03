package org.productalerter.model.eventlog;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventLog {

    private String id;
    private LocalDateTime timestamp;
    private int eventLogType;
    private String userId;
    private SystemState systemState;

}
