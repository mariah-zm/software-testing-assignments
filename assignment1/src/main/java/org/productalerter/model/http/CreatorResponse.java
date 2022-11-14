package org.productalerter.model.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatorResponse {

    private int numProductsRequested;
    private int alertsAffected;
    private List<String> errorMessages;

}
