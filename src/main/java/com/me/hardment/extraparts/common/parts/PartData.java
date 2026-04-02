package com.me.hardment.extraparts.common.parts;

import net.minecraft.resources.ResourceLocation;

public class PartData {

    private final PartType type;
    private final ResourceLocation texture;

    public PartData(PartType type, ResourceLocation texture) {
        this.type = type;
        this.texture = texture;
    }

    public PartType getType() {
        return type;
    }

    public ResourceLocation getTexture() {
        return texture;
    }
}