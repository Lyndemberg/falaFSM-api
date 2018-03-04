package io.github.recursivejr.discenteVivo.resources;

import java.io.File;

public class ThreadDiskManagement implements Runnable {

    public void run() {
        //Seta o Diretorio
        File diretorio = new File(FotoManagement.getPathname());

        if (!diretorio.exists())
            diretorio.mkdir();

        System.out.println("Entrou thread");

        //Perrcore todos os Arquivos do Diretorio e Remove-os
        for (File foto: diretorio.listFiles()) {
            foto.delete();
            System.out.println("Deletou a porra da foto");
        }
    }
}
