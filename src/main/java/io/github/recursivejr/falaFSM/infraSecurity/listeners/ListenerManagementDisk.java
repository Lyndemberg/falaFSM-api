package io.github.recursivejr.falaFSM.infraSecurity.listeners;

import io.github.recursivejr.falaFSM.resources.FotoManagement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ListenerManagementDisk implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        FotoManagement.startFotoManagement();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        FotoManagement.stopFotoManagement();
    }
}
