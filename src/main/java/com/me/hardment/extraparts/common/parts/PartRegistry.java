package com.me.hardment.extraparts.common.parts;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class PartRegistry {

    public static final List<PartData> PARTS = new ArrayList<>();

    static {
        // 👇 TU PRIMERA PARTE (ALAS)
        PARTS.add(new PartData(
                PartType.WINGS,
                ResourceLocation.fromNamespaceAndPath("extraparts", "textures/capa-alas.png")
        ));
    }

    public static List<PartData> getByType(PartType type) {
        List<PartData> result = new ArrayList<>();

        for (PartData part : PARTS) {
            if (part.getType() == type) {
                result.add(part);
            }
        }

        return result;
    }
}