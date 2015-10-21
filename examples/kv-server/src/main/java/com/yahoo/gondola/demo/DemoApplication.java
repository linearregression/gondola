/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the New BSD License.
 * See the accompanying LICENSE file for terms.
 */
package com.yahoo.gondola.demo;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

public class DemoApplication extends ResourceConfig {
    public DemoApplication(@Context ServletContext servletContext) throws Exception {
        DemoService demoService = new DemoService();
        registerClasses(DemoResources.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(demoService).to(DemoService.class);
            }
        });
        packages(true, "com.yahoo.gondola.demo");
    }
}
