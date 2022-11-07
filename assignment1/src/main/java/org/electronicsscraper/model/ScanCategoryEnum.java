package org.electronicsscraper.model;

public enum ScanCategoryEnum {

    ART("Art & Photography"),
    BIOGRAPHY("Biography"),
    CHILDREN("Children's Books"),
    CRAFTS("Crafts & Hobbies"),
    CRIME("Crime & Thriller"),
    FICTION("Fiction"),
    FOOD("Food & Drink"),
    COMICS("Graphic Novels, Anime & Manga"),
    HISTORY("History & Archaeology"),
    MIND("Mind, Body & Spirit"),
    SCI_FI("Science Fiction, Fantasy & Horror");

    private final String categoryName;

    ScanCategoryEnum(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }

}
