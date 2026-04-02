package com.me.hardment.extraparts.client.gui;

import com.me.hardment.extraparts.common.parts.PartData;
import com.me.hardment.extraparts.common.parts.PartRegistry;
import com.me.hardment.extraparts.common.parts.PartType;
import com.me.hardment.extraparts.common.parts.PlayerParts;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PartsScreen extends Screen {

    private PartType selectedCategory = PartType.WINGS;

    public PartsScreen() {
        super(Component.literal("Extra Parts"));
    }

    @Override
    protected void init() {
        // ya no usamos botones
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {

        int screenWidth = this.width;
        int screenHeight = this.height;

        int leftX = 20;
        int startY = 40;

        int i = 0;

        // 🟩 Categorías
        for (PartType part : PartType.values()) {

            int y = startY + (i * 20);

            boolean hovered = mouseX >= leftX && mouseX <= leftX + 100 &&
                    mouseY >= y && mouseY <= y + 12;

            int color;

            if (part == selectedCategory) {
                color = 0x00FF00;
            } else if (hovered) {
                color = 0xFFFF00;
            } else {
                color = 0xFFFFFF;
            }

            g.drawString(this.font, part.name(), leftX, y, color);

            i++;
        }

        // ⬛ panel derecho
        g.fill(140, 30, screenWidth - 10, screenHeight - 10, 0x66000000);

        // 🔥 PARTES REALES (NO placeholders)
        var parts = PartRegistry.getByType(selectedCategory);

        int startX = 160;
        int startGridY = 50;

        int size = 60;
        int spacing = 10;

        for (int j = 0; j < parts.size(); j++) {

            PartData part = parts.get(j);

            int x = startX + (j % 3) * (size + spacing);
            int y = startGridY + (j / 3) * (size + spacing);

            // fondo
            g.fill(x, y, x + size, y + size, 0xFF555555);

            // 🖼️ textura
            g.blit(
                    part.getTexture(),
                    x + 5, y + 5,
                    0, 0,
                    size - 10, size - 10,
                    size - 10, size - 10
            );
        }

        // título
        g.drawCenteredString(this.font, "Extra Parts", screenWidth / 2, 10, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        int leftX = 20;
        int startY = 40;

        int i = 0;

        // 🔹 seleccionar categoría
        for (PartType part : PartType.values()) {

            int y = startY + (i * 20);

            if (mouseX >= leftX && mouseX <= leftX + 100 &&
                    mouseY >= y && mouseY <= y + 12) {

                selectedCategory = part;
                return true;
            }

            i++;
        }

        // 🔥 CLICK EN PARTES (GRID)
        var parts = PartRegistry.getByType(selectedCategory);

        int startX = 160;
        int startGridY = 50;

        int size = 60;
        int spacing = 10;

        for (int j = 0; j < parts.size(); j++) {

            int x = startX + (j % 3) * (size + spacing);
            int y = startGridY + (j / 3) * (size + spacing);

            if (mouseX >= x && mouseX <= x + size &&
                    mouseY >= y && mouseY <= y + size) {

                // 🔥 ACTIVAR / DESACTIVAR PARTE
                PlayerParts.togglePart(selectedCategory);

                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}