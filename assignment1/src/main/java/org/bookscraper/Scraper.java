package org.bookscraper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Scraper {

    private final String website;

    public Scraper(String website) {
        this.website = website;
    }



}
