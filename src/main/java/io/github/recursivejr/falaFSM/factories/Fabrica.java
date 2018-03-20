package io.github.recursivejr.falaFSM.factories;

public class Fabrica {
    
    public static FabricaDaoPostgres criarFabricaDaoPostgres(){
            return new FabricaDaoPostgres();
    }
}
