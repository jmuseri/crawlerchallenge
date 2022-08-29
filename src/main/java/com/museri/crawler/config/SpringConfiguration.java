package com.museri.crawler.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.museri.crawler.domain.Internet;
import com.museri.crawler.domain.Page;
import com.museri.crawler.service.InternetService;
import com.museri.crawler.service.WebPageCrawlerService;
import com.museri.crawler.service.impl.MultithreadedWebCrawlerServiceImpl;
import com.museri.crawler.service.impl.SimpleInternetServiceImpl;
import com.museri.crawler.service.impl.SingleThreadedWebCrawlerServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;
import java.util.List;

@Configuration
public class SpringConfiguration  implements CommandLineRunner {

    @Value("${internet.file.path}")
    private String internetFilePath;


    @Bean
    public InternetService internetService(){
        return new SimpleInternetServiceImpl();
    }

    @Bean
    public WebPageCrawlerService crawlerService(){
        return new SingleThreadedWebCrawlerServiceImpl(internetService());
    }

    @Override
    public void run(String... args) throws Exception {
        TypeReference<Internet> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(internetFilePath);
        Internet internet = new ObjectMapper().readValue(inputStream, typeReference);
        if (internet != null) {
            List<Page> pages = internet.getPages();
            if (!ObjectUtils.isEmpty(pages)) {
                pages.forEach(page -> internetService().addPage(page.getAddress(), page.getLinks()));
            }
        }
    }

}
