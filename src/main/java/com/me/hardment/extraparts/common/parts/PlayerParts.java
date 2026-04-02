package com.me.hardment.extraparts.common.parts;

import java.util.HashSet;
import java.util.Set;

public class PlayerParts {

    private static final Set<PartType> activeParts = new HashSet<>();

    public static void togglePart(PartType part) {
        if (activeParts.contains(part)) {
            activeParts.remove(part);
        } else {
            activeParts.add(part);
        }
    }

    public static boolean hasPart(PartType part) {
        return activeParts.contains(part);
    }
}