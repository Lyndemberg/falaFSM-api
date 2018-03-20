package io.github.recursivejr.falaFSM.exceptions;

public class AutenticacaoException extends Exception {

    public AutenticacaoException() {
        super("Nao Foi Possivel Autenticar O Usuario.");
    }

    public AutenticacaoException(String msg) {
        super("Nao Foi Possivel Autenticar O Usuario, Por Motivo de : " + msg + ";");
    }
}
