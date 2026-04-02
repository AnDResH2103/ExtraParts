package com.me.hardment.extraparts.client;

import com.me.hardment.extraparts.common.registry.ModKeyBindings;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public class ClientModEvents {

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ModKeyBindings.OPEN_MENU);
    }
}