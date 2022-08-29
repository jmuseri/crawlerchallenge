package com.museri.crawler.service;

import com.museri.crawler.domain.dto.CrawledPageDto;

public interface WebPageCrawlerService {
    CrawledPageDto crawl(String rootURL);
}
