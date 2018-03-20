package io.github.recursivejr.falaFSM.infraSecurity;

import io.github.recursivejr.falaFSM.infraSecurity.model.NivelAcesso;

public class AcessControll {

    public static NivelAcesso buscarNivelPermissao(String login) {
		/*
			Verifica com base no token se é um administrador, apenas administradores posuem email no token
				logo a condição de parada é possuir um "@" no token.
			Caso seja um Administrador e retornado o Nivel de Acesso 1,
			*/

        if (login.contains("@"))
            return NivelAcesso.NIVEL_1;
        else
            return NivelAcesso.NIVEL_2;

    }
}
