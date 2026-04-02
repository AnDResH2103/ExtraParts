package com.me.hardment.extraparts.client.gui;

import com.me.hardment.extraparts.common.parts.PartType;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class PartsButton extends Button {

    private final PartType part;

    public PartsButton(int x, int y, PartType part, OnPress onPress) {
        super(x, y, 60, 20, Component.literal(part.name()), onPress, DEFAULT_NARRATION);
        this.part = part;
    }

    public PartType getPart() {
        return part;
    }
}