package com.example.pokedex.model;

import com.squareup.moshi.Json;

public class Type {

    @Json(name = "type")
    private TypeName typeName;

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
    }
}
