package com.mmbackendassignment.mmbackendassignment.dto;

import java.util.ArrayList;

public class PageOutputDto {

    public ArrayList<Object> content = new ArrayList<>();
    public long amount;
    public long currentPage;
    public long totalPages;

    public PageOutputDto(long amount, long currentPage, long totalPages) {
        this.amount = amount;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}
