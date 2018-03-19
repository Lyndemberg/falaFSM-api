package io.github.recursivejr.discenteVivo.models.Enum;

import com.fasterxml.jackson.databind.annotation.JsonAppend;

@JsonAppend(attrs = {
        @JsonAppend.Attr(value = "respondido")
})
public enum EnumRespondido {

    TRUE("True"), FALSE("False");

    private String status;

    EnumRespondido(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
