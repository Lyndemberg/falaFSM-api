package io.github.recursivejr.discenteVivo;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

import io.github.recursivejr.discenteVivo.infraSecurity.CORSFilter;


@ApplicationPath("rest")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        packages("io.github.recursivejr.discenteVivo.controllers");
        register(CORSFilter.class);
    }
}
