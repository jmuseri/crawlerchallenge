package com.museri.crawler.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CrawledPageDto {
    private String startPage;
    private Set<String> success;
    private Set<String> skipped;
    private Set<String> error;
}
