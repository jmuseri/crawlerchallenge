package com.museri.crawler.domain;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Page {
    private String address;
    private ArrayList<String> links;
}