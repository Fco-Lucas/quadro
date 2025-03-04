package com.maia.quadro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PageableDto {
    private List content = new ArrayList<>();
    private boolean first;
    private boolean end;
    @JsonProperty("page")
    private int number;
    private int size;
    @JsonProperty("pageElements")
    private int numberOfElements;
    private int totalPages;
    private int totalElements;

    public List getContent() {
        return content;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isEnd() {
        return end;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setContent(List content) {
        this.content = content;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
