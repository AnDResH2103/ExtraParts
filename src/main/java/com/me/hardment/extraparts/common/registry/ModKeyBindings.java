package com.me.hardment.extraparts.common.registry;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static final KeyMapping OPEN_MENU =
            new KeyMapping("key.extraparts.open_menu", GLFW.GLFW_KEY_F12, "key.categories.misc");
}