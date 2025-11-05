package com.back.domain.wiseSaying.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryParse {
    private String keywordType;
    private String keyword;
    private int page;

    public QueryParse() {
        this.keywordType = null;
        this.keyword = null;
        this.page = 1;
    }

    public QueryParse(String keywordType, String keyword) {
        this.keywordType = keywordType;
        this.keyword = keyword;
        this.page = 1;
    }

    public QueryParse(int page, String keywordType, String keyword) {
        this.keywordType = keywordType;
        this.keyword = keyword;
        this.page = page;

    }
}
