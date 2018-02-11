package io.github.recursivejr.discenteVivo;

import javax.ws.rs.ApplicationPath;

import io.github.recursivejr.discenteVivo.infraSecurity.filters.FilterSecurityAuthentication;
import io.github.recursivejr.discenteVivo.infraSecurity.filters.FilterSecurityAuthorization;
import org.glassfish.jersey.server.ResourceConfig;

import io.github.recursivejr.discenteVivo.infraSecurity.filters.CORSFilter;


@ApplicationPath("rest")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        //Scaneia Dinamicamente pelos Controllers (classes com @Path)
        packages("io.github.recursivejr.discenteVivo.controllers");

        //Injeta os Filters
        register(CORSFilter.class);
        register(FilterSecurityAuthentication.class);
        register(FilterSecurityAuthorization.class);
    }
}
