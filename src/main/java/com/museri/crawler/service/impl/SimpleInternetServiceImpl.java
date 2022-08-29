package com.museri.crawler.service.impl;

import com.museri.crawler.service.InternetService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimpleInternetServiceImpl implements InternetService {


    private static final Map<String, List<String>> internet = new HashMap<>();

    @Override
    public List<String> visitUrl(String address) {
        return internet.get(address);
    }

    @Override
    public void addPage(String address, List<String> urls) {
        internet.putIfAbsent(address, urls);
    }
}
