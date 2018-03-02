package io.github.recursivejr.discenteVivo.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FotoManagement {

    private static final String PATHNAME = "";

    public static String encodeFoto(File foto) throws IOException {

        //Recebe um Obj File da Foto e Transforma em Array de Bytes
        byte[] bytes = new byte[(int)foto.length()];
        new FileInputStream(foto).read(bytes);

        //Encoda o Arary de Bytem em Base64
        String fotoBase64 = new String(Base64.getEncoder().encodeToString(bytes));

        //Retorna a Foto em Base64
        return fotoBase64;
    }

    public static File decodeFoto(String fotoBase64) throws IOException {

        //Cria objeto File de foto no endereço PATHNAME com o nome sendo um hashcode da foto em base64
        File foto = new File(PATHNAME + fotoBase64.hashCode());

        //Se ja existe essa foto no disco retorna ela para evitar o processamento de decodificação
            //da foto em Base64 para a foto em Bytes
        if (foto.exists())
            return foto;

        //Decodifica a foto de Base64 para Array de Bytes
        byte[] fotoBytes = Base64.getDecoder().decode(fotoBase64);

        //Escreve o Array de Bytes em um Arquivo
        FileOutputStream fileOutputStream = new FileOutputStream(PATHNAME + fotoBase64.hashCode());
        fileOutputStream.write(fotoBytes);

        //Retorna a Foto
        return foto;
    }
}
