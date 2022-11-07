package org.electronicsscraper.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlertResponse extends Alert {

    private String id;
    private LocalDateTime postDate;

}
