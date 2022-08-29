package com.museri.crawler.service.impl;

import com.museri.crawler.domain.dto.CrawledPageDto;
import com.museri.crawler.service.InternetService;
import com.museri.crawler.service.WebPageCrawlerService;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;

public class MultithreadedWebCrawlerServiceImpl implements WebPageCrawlerService {

    private final InternetService internetService;

    //TODO Test and Finish this Implementation.
    public MultithreadedWebCrawlerServiceImpl(InternetService internetService) {
        this.internetService = internetService;
    }

    @Override
    public CrawledPageDto crawl(String rootURL) {


        ExecutorService executor = Executors.newCachedThreadPool();

        Queue<String> urlQueue = new LinkedList<>();
        Set<String> visitedURLs = new HashSet<>();
        Set<String> skippedURLs = new HashSet<>();
        Set<String> errorURLs = new HashSet<>();

        List<Callable<List<String>>> callables = new ArrayList<>();

        urlQueue.add(rootURL);
        while (!urlQueue.isEmpty()) {
            // remove the next url string from the queue to begin traverse.
            String address = urlQueue.remove();

            // if the address has already been visited skip
            if (visitedURLs.contains(address)) {
                skippedURLs.add(address);
            } else if (!errorURLs.contains(address)) {
                Callable<List<String>> crawler = () -> {
                    List<String> urls = internetService.visitUrl(address);
                    if (!CollectionUtils.isEmpty(urls)) {
                        visitedURLs.add(address);
                        return urls;
                        //urlQueue.addAll(urls);
                    } else {
                        errorURLs.add(address);
                        return Collections.EMPTY_LIST;
                    }
                };
                callables.add(crawler);

                if (urlQueue.isEmpty()) {
                    try {
                        List<Future<List<String>>> futures = executor.invokeAll(callables);
                        futures.forEach(future -> {
                            try {
                                urlQueue.addAll(future.get());
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
        executor.shutdown();
        return new CrawledPageDto(rootURL, visitedURLs, skippedURLs, errorURLs);
    }

}