package com.me.hardment.extraparts.client;

import com.me.hardment.extraparts.client.render.WingsLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientSetup {

    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {

        for (var skin : event.getSkins()) {

            PlayerRenderer renderer = event.getSkin(skin);

            if (renderer != null) {
                renderer.addLayer(new WingsLayer(renderer));
            }
        }
    }
}