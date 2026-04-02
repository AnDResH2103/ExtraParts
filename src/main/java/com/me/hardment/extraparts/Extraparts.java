package com.me.hardment.extraparts;

import com.me.hardment.extraparts.client.ClientEvents;
import com.me.hardment.extraparts.client.ClientModEvents;
import com.me.hardment.extraparts.client.ClientSetup;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Extraparts.MODID)
public class Extraparts {

    public static final String MODID = "extraparts";

    public Extraparts(IEventBus modEventBus) {

        modEventBus.addListener(ClientSetup::onAddLayers);

        // ✅ MOD BUS (keybind)
        modEventBus.addListener(ClientModEvents::registerKeys);

        // ✅ GAME BUS (tick)
        NeoForge.EVENT_BUS.register(ClientEvents.class);

        System.out.println("Extraparts cargado");
    }
}