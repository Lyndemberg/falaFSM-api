package io.github.recursivejr.discenteVivo.infraSecurity;

import javax.ws.rs.core.CacheControl;

public class CacheController {

    public static CacheControl getCacheControl() {
        CacheControl cacheControl = new CacheControl();

        cacheControl.setPrivate(true);
        cacheControl.setMaxAge(300);

        return cacheControl;
    }
}
