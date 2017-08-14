package io.github.recursivejr.discenteVivo.factories;

public class Fabrica {
    
    public static FabricaDaoPostgres criarFabricaDaoPostgres(){
            return new FabricaDaoPostgres();
    }
}
