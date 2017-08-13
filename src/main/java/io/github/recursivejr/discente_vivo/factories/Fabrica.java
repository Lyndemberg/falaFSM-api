package io.github.recursivejr.discente_vivo.factories;

public class Fabrica {
    
    public static FabricaDaoPostgres criarFabricaDaoPostgres(){
            return new FabricaDaoPostgres();
    }
}
