package io.github.recursivejr.discenteVivo.infraSecurity.listeners;

import io.github.recursivejr.discenteVivo.resources.FotoManagement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ListenerManagementDisk implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        FotoManagement.startFotoManagement();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
