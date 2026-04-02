package com.me.hardment.extraparts.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.me.hardment.extraparts.common.parts.PartType;
import com.me.hardment.extraparts.common.parts.PlayerParts;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class WingsLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("extraparts", "textures/capa-alas.png");

    public WingsLayer(PlayerRenderer renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack,
                       net.minecraft.client.renderer.MultiBufferSource buffer,
                       int light,
                       AbstractClientPlayer player,
                       float limbSwing,
                       float limbSwingAmount,
                       float partialTicks,
                       float ageInTicks,
                       float netHeadYaw,
                       float headPitch) {

        if (!PlayerParts.hasPart(PartType.WINGS)) return;

        poseStack.pushPose();

        // 📍 Posición base en la espalda
        this.getParentModel().body.translateAndRotate(poseStack);

        VertexConsumer vertex = buffer.getBuffer(
                net.minecraft.client.renderer.RenderType.entityTranslucent(TEXTURE)
        );

        float size = 0.7f;

        // 🪽 IZQUIERDA
        poseStack.pushPose();

        poseStack.translate(-0.25, 0.15, 0.15);
        poseStack.mulPose(Axis.YP.rotationDegrees(25));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-20));

        drawWing(vertex, poseStack.last(), light, size, true);

        poseStack.popPose();

        // 🪽 DERECHA
        poseStack.pushPose();

        poseStack.translate(0.25, 0.15, 0.15);
        poseStack.mulPose(Axis.YP.rotationDegrees(-25));
        poseStack.mulPose(Axis.ZP.rotationDegrees(20));

        drawWing(vertex, poseStack.last(), light, size, false);

        poseStack.popPose();

        poseStack.popPose();
    }

    // 🔥 Método para dibujar alas
    private void drawWing(VertexConsumer vertex,
                          PoseStack.Pose pose,
                          int light,
                          float size,
                          boolean left) {

        float u1 = left ? 0f : 0.5f;
        float u2 = left ? 0.5f : 1f;

        vertex.addVertex(pose.pose(), -size, 0, 0)
                .setUv(u1, 0)
                .setColor(255, 255, 255, 255)
                .setLight(light)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setNormal(0, 0, 1);

        vertex.addVertex(pose.pose(), size, 0, 0)
                .setUv(u2, 0)
                .setColor(255, 255, 255, 255)
                .setLight(light)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setNormal(0, 0, 1);

        vertex.addVertex(pose.pose(), size, size, 0)
                .setUv(u2, 1)
                .setColor(255, 255, 255, 255)
                .setLight(light)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setNormal(0, 0, 1);

        vertex.addVertex(pose.pose(), -size, size, 0)
                .setUv(u1, 1)
                .setColor(255, 255, 255, 255)
                .setLight(light)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setNormal(0, 0, 1);
    }
}