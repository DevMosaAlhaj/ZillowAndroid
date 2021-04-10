package com.mosaalhaj.zillow.response;

public class PagingResponse<T> {

    private final int currentPage ;
    private final int pagesCount ;
    private final T data ;

    public PagingResponse(int currentPage, int pagesCount, T data) {
        this.currentPage = currentPage;
        this.pagesCount = pagesCount;
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public T getData() {
        return data;
    }
}


