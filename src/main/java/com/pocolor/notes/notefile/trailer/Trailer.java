package com.pocolor.notes.notefile.trailer;

public record Trailer(Dependencies dependencies) {
    public byte[] serialize() {
        return this.dependencies.serialize();
    }
}
