package com.mmbackendassignment.mmbackendassignment.dto;

import java.util.ArrayList;

public class PageDto {

    public ArrayList content = new ArrayList<>();
    public long amount;
    public long currentPage;
    public long totalPages;

    public PageDto(long amount, long currentPage, long totalPages) {
        this.amount = amount;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}
