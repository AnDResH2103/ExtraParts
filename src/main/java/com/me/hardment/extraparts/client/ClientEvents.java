package com.me.hardment.extraparts.client;

import com.me.hardment.extraparts.client.gui.PartsScreen;
import com.me.hardment.extraparts.common.registry.ModKeyBindings;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class ClientEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {

        while (ModKeyBindings.OPEN_MENU.consumeClick()) {

            Minecraft mc = Minecraft.getInstance();

            if (mc.screen == null) {
                mc.setScreen(new PartsScreen());
            }
        }
    }
}