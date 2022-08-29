package com.museri.crawler.service.impl;

import com.museri.crawler.domain.dto.CrawledPageDto;
import com.museri.crawler.service.InternetService;
import com.museri.crawler.service.WebPageCrawlerService;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class SingleThreadedWebCrawlerServiceImpl implements WebPageCrawlerService {


    private final InternetService internetService;

    public SingleThreadedWebCrawlerServiceImpl(InternetService internetService) {
        this.internetService = internetService;
    }

    @Override
    public CrawledPageDto crawl(String rootURL) {

        Queue<String> urlQueue = new LinkedList<>();
        Set<String> visitedURLs = new HashSet<>();
        Set<String> skippedURLs = new HashSet<>();
        Set<String> errorURLs = new HashSet<>();

        urlQueue.add(rootURL);
        while (!urlQueue.isEmpty()) {
            String address = urlQueue.remove();
            // remove the next url string from the queue to begin traverse.

            // if the address has already been visited skip
            if (visitedURLs.contains(address)) {
                skippedURLs.add(address); //is writing also when already exists but is a set.
            } else if (!errorURLs.contains(address)) {
                List<String> urls = internetService.visitUrl(address);
                if (!CollectionUtils.isEmpty(urls)) {
                    visitedURLs.add(address);
                    urls.forEach(url -> urlQueue.add(url));
                } else {
                    errorURLs.add(address);
                }
            }
        }
        return new CrawledPageDto(rootURL, visitedURLs, skippedURLs, errorURLs);
    }

}