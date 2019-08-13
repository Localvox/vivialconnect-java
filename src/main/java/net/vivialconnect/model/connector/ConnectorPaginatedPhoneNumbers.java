package net.vivialconnect.model.connector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectorPaginatedPhoneNumbers {

    @JsonProperty("connector")
    private Connector connector;

    @JsonProperty("count")
    private int count;

    @JsonProperty("next")
    private int next;

    @JsonProperty("pages")
    private int pages;

    @JsonProperty("previous")
    private int previous;

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }
}
