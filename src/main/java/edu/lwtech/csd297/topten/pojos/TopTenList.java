package edu.lwtech.csd297.topten.pojos;

import java.util.*;

public class TopTenList {

    // Encapsulated member variables
    private int recID;              // Database ID (or -1 if it isn't in the database yet)
    private String description;
    private List<String> items;     // Note: First item in list is #10.  Last item is #1
    private int ownerID;
    private int numViews;
    private int numLikes;
    private boolean isPublished;

    public TopTenList(String description, List<String> items, int ownerID) {
        this(-1, description, items, false, ownerID, 0, 0);
    }

    public TopTenList(int recID, String description, List<String> items, boolean isPublished, int ownerID, int numViews, int numLikes) {

        if (recID < -1)
            throw new IllegalArgumentException("Invalid TopTenList argument: recID < -1");
        if (description == null)
            throw new IllegalArgumentException("Invalid TopTenList argument: description is null");
        if (description.isEmpty())
            throw new IllegalArgumentException("Invalid TopTenList argument: description is empty");
        if (items == null)
            throw new IllegalArgumentException("Invalid TopTenList argument: item list is null");
        if (items.size() < 10)
            throw new IllegalArgumentException("Invalid TopTenList argument: less than 10 items");
        if (ownerID < 0)
            throw new IllegalArgumentException("Invalid TopTenList argument: ownerID < 0");
        if (numViews < 0)
            throw new IllegalArgumentException("Invalid TopTenList argument: numViews < 0");
        if (numLikes < 0)
            throw new IllegalArgumentException("Invalid TopTenList argument: numLikes < 0");

        this.recID = recID;
        this.description = description;
        this.items = new ArrayList<>(items);
        this.ownerID = ownerID;
        this.isPublished = isPublished;
        this.numViews = numViews;
        this.numLikes = numLikes;
    }

    // ----------------------------------------------------------------

    public int getRecID() {
        return recID;
    }

    public void setRecID(int recID) {
        // Updates the recID of lists that have just been added to the database
        if (recID <= 0)
            throw new IllegalArgumentException("setRecID: recID cannot be negative.");
        if (this.recID != -1)
            throw new IllegalArgumentException("setRecID: list has already been added to the database (recID != 1).");

        this.recID = recID;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getItems() {
        return new ArrayList<>(items);      // Return a copy of the item list
    }

    public int getOwnerID() {
        return this.ownerID;
    }

    public boolean isPublished() {
        return this.isPublished;
    }

    public int getNumViews() {
        return this.numViews;
    }

    public int getNumLikes() {
        return this.numLikes;
    }

    public int incrementNumViews() {
        return ++numViews;
    }

    public int incrementNumLikes() {
        return ++numLikes;
    }

    public void publish(int ownerID) {
        if (ownerID == this.ownerID)
            isPublished = true;
    }

    @Override
    public String toString() {
        return recID + ": " + description
                + " (" + numViews + "/" + numLikes + ") "
                + (isPublished ? "[Published]" : "[Draft]");
    }

}
