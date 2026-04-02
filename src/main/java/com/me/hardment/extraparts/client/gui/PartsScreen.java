package com.me.hardment.extraparts.client.gui;

import com.me.hardment.extraparts.common.parts.PartData;
import com.me.hardment.extraparts.common.parts.PartRegistry;
import com.me.hardment.extraparts.common.parts.PartType;
import com.me.hardment.extraparts.common.parts.PlayerParts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import org.joml.Vector3f;
import org.joml.Quaternionf;

public class PartsScreen extends Screen {

    private PartType selectedCategory = PartType.WINGS;
    private int selectedPartIndex = -1;

    // 🔥 SCROLL
    private int scrollOffset = 0;
    private static final int CATEGORY_HEIGHT = 24;
    private static final int VISIBLE_CATEGORIES = 10;

    public PartsScreen() {
        super(Component.literal("Extra Parts"));
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {

        int screenWidth = this.width;
        int screenHeight = this.height;

        int leftX = 20;
        int startY = 40;

        // 🟫 PANEL IZQUIERDO
        g.fill(leftX - 10, 20, leftX + 120, screenHeight - 20, 0xAA101010);

        PartType[] allParts = PartType.values();

        int startIndex = scrollOffset;
        int endIndex = Math.min(allParts.length, startIndex + VISIBLE_CATEGORIES);

        for (int i = startIndex; i < endIndex; i++) {

            PartType part = allParts[i];
            int y = startY + ((i - startIndex) * CATEGORY_HEIGHT);

            boolean hovered = mouseX >= leftX && mouseX <= leftX + 100 &&
                    mouseY >= y && mouseY <= y + 16;

            boolean selected = part == selectedCategory;

            if (selected) {
                g.fill(leftX - 8, y - 2, leftX + 102, y + 16, 0x55FFFF55);
                g.fill(leftX - 10, y - 2, leftX - 6, y + 16, 0xFFFFFF55);
            } else if (hovered) {
                g.fill(leftX - 8, y - 2, leftX + 102, y + 16, 0x55888888);
            }

            int color = selected ? 0xFFFFFF : (hovered ? 0xFFFFAA : 0xBBBBBB);

            g.drawString(this.font, part.name(), leftX + 1, y, color);
            g.drawString(this.font, part.name(), leftX, y, color);
        }

        // 🟩 SCROLLBAR
        int total = allParts.length;

        if (total > VISIBLE_CATEGORIES) {

            int barHeight = VISIBLE_CATEGORIES * CATEGORY_HEIGHT;
            int totalHeight = total * CATEGORY_HEIGHT;

            int scrollBarHeight = Math.max(20, (barHeight * barHeight) / totalHeight);
            int scrollY = startY + (scrollOffset * (barHeight - scrollBarHeight)) / (total - VISIBLE_CATEGORIES);

            g.fill(leftX + 105, startY, leftX + 110, startY + barHeight, 0x55222222);
            g.fill(leftX + 105, scrollY, leftX + 110, scrollY + scrollBarHeight, 0xFFAAAAAA);
        }

        // 🟫 PANEL DERECHO
        g.fill(140, 30, screenWidth - 10, screenHeight - 10, 0xAA101010);

        var parts = PartRegistry.getByType(selectedCategory);

        int startX = 160;
        int startGridY = 50;

        int size = 64;
        int spacing = 12;

        for (int j = 0; j < parts.size(); j++) {

            PartData part = parts.get(j);

            int x = startX + (j % 3) * (size + spacing);
            int y = startGridY + (j / 3) * (size + spacing);

            boolean hovered = mouseX >= x && mouseX <= x + size &&
                    mouseY >= y && mouseY <= y + size;

            boolean selected = j == selectedPartIndex;

            int bgColor = 0xFF3A3A3A;

            if (hovered) bgColor = 0xFF4A4A4A;
            if (selected) bgColor = 0xFF6A6A2A;

            g.fill(x, y, x + size, y + size, bgColor);

            if (selected) {
                g.fill(x, y, x + size, y + 2, 0xFFFFFF55);
                g.fill(x, y + size - 2, x + size, y + size, 0xFFFFFF55);
                g.fill(x, y, x + 2, y + size, 0xFFFFFF55);
                g.fill(x + size - 2, y, x + size, y + size, 0xFFFFFF55);
            }

            g.blit(part.getTexture(), x + 6, y + 6, 0, 0,
                    size - 12, size - 12,
                    size - 12, size - 12);
        }

        // 🧍 PREVIEW DEL JUGADOR (🔥 FIX COMPLETO)
        int previewX = 60;
        int previewY = screenHeight - 40;
        int scale = 35;

        var player = Minecraft.getInstance().player;

        if (player != null) {

            float mouseRotX = (float)(mouseX - previewX) * 0.01F;

            // 🔥 SOLO ROTACIÓN EN Y (más estable)
            Quaternionf rotation = new Quaternionf()
                    .rotateX((float)Math.PI)   // 🔥 corrige que esté de cabeza
                    .rotateY(mouseRotX);

            Quaternionf cameraRotation = new Quaternionf()
                    .rotateY((float)Math.PI); // para que mire al frente

            Vector3f translation = new Vector3f(0.0F, player.getBbHeight() / 2.0F, 0.0F);

            InventoryScreen.renderEntityInInventory(
                    g,
                    (float) previewX,
                    (float) previewY,
                    (float) scale,
                    translation,
                    rotation,
                    cameraRotation,
                    player
            );
        } else {
            g.drawString(this.font, "Cargando...", previewX - 30, previewY, 0xAAAAAA);
        }

        // 🏷️ título
        g.drawCenteredString(this.font, "§lEXTRA PARTS", screenWidth / 2, 12, 0xFFFFFF);

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {

        int maxScroll = Math.max(0, PartType.values().length - VISIBLE_CATEGORIES);

        if (scrollY < 0) {
            scrollOffset = Math.min(scrollOffset + 1, maxScroll);
        } else if (scrollY > 0) {
            scrollOffset = Math.max(scrollOffset - 1, 0);
        }

        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        int leftX = 20;
        int startY = 40;

        PartType[] allParts = PartType.values();

        int startIndex = scrollOffset;
        int endIndex = Math.min(allParts.length, startIndex + VISIBLE_CATEGORIES);

        for (int i = startIndex; i < endIndex; i++) {

            PartType part = allParts[i];
            int y = startY + ((i - startIndex) * CATEGORY_HEIGHT);

            if (mouseX >= leftX && mouseX <= leftX + 100 &&
                    mouseY >= y && mouseY <= y + 16) {

                selectedCategory = part;
                selectedPartIndex = -1;
                return true;
            }
        }

        var parts = PartRegistry.getByType(selectedCategory);

        int startX = 160;
        int startGridY = 50;

        int size = 64;
        int spacing = 12;

        for (int j = 0; j < parts.size(); j++) {

            int x = startX + (j % 3) * (size + spacing);
            int y = startGridY + (j / 3) * (size + spacing);

            if (mouseX >= x && mouseX <= x + size &&
                    mouseY >= y && mouseY <= y + size) {

                selectedPartIndex = j;

                PartData part = parts.get(j);
                PlayerParts.setPart(selectedCategory, part);

                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}