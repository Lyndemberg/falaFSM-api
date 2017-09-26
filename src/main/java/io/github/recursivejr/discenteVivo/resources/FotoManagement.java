package io.github.recursivejr.discenteVivo.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FotoManagement {

    private static final String PATHNAME = "";

    public static String encodeFoto(File foto) throws IOException {

        byte[] bytes = new byte[(int)foto.length()];
        new FileInputStream(foto).read(bytes);

        String fotoBase64 = new String(Base64.getEncoder().encodeToString(bytes));
        return fotoBase64;
    }

    public static File decodeFoto(String fotoBase64) throws IOException {

        byte[] fotoBytes = Base64.getDecoder().decode(fotoBase64);

        FileOutputStream fileOutputStream = new FileOutputStream(PATHNAME + fotoBase64.hashCode());
        fileOutputStream.write(fotoBytes);

        File foto = new File(PATHNAME + fotoBase64.hashCode());
        return foto;
    }
}
