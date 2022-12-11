package com.vqilai.compont;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ManualService manualService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        manualService.scheduled();
    }
}
