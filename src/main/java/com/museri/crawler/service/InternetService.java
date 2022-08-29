package com.museri.crawler.service;

import java.util.List;

public interface InternetService {
    List<String> visitUrl(String address);

    void addPage(String address, List<String> urls);

}
