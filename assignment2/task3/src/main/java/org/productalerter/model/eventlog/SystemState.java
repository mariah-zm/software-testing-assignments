package org.productalerter.model.eventlog;

import lombok.Getter;
import lombok.Setter;
import org.productalerter.model.http.MarketAlertUmResponse;

import java.util.List;

@Getter
@Setter
public class SystemState {

    private String userId;
    private boolean loggedIn;
    private List<MarketAlertUmResponse> alerts;

}
