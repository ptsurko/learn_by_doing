package com.test;

import com.test.services.AppService;
import com.test.services.ConfigService;
import com.test.services.TestService;
import com.test.services.TestServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class MyApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(TestServiceImpl.class).to(TestService.class);
        bind(ConfigService.class).to(ConfigService.class).in(Singleton.class);
        bind(AppService.class).to(AppService.class);
    }
}
