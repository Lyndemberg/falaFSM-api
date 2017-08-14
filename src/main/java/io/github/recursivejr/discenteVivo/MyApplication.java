package io.github.recursivejr.discenteVivo;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("rest")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        packages("io.github.recursivejr.discenteVivo.controllers");
    }
}
