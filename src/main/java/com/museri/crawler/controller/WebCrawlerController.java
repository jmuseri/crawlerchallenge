package com.museri.crawler.controller;


import com.museri.crawler.domain.dto.CrawledPageDto;
import com.museri.crawler.service.WebPageCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebCrawlerController {

    @Autowired
    private WebPageCrawlerService crawlerService;


    @GetMapping(value = "crawl/{address}")
    public CrawledPageDto crawlPage(@PathVariable String address) {
        return crawlerService.crawl(address);
    }
}