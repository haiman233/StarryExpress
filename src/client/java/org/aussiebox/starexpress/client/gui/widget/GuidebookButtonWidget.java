package org.aussiebox.starexpress.client.gui.widget;

import dev.doctor4t.wathe.client.gui.screen.ingame.LimitedInventoryScreen;
import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import org.aussiebox.starexpress.client.gui.screen.GuidebookScreen;
import org.jetbrains.annotations.NotNull;

public class GuidebookButtonWidget extends Button {
    public final LimitedInventoryScreen screen;

    public GuidebookButtonWidget(LimitedInventoryScreen screen, int x, int y) {
        super(x, y, 16, 16, Component.translatable("guidebook.tooltip.open"), button -> Minecraft.getInstance().setScreen(new GuidebookScreen()), DEFAULT_NARRATION);
        this.screen = screen;
    }

    protected void renderWidget(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
            super.renderWidget(context, mouseX, mouseY, delta);
            context.blitSprite(ShopEntry.Type.TOOL.getTexture(), this.getX() - 7, this.getY() - 7, 30, 30);
            context.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), this.getX(), this.getY());
            if (this.isHovered()) {
                this.drawShopSlotHighlight(context, this.getX(), this.getY(), 0);
                context.renderTooltip(Minecraft.getInstance().font, Component.translatable("guidebook.tooltip.open"), Minecraft.getInstance().font.width(Component.translatable("guidebook.tooltip.open")) / 2 - 10, this.getY() + 16);
            }
    }

    private void drawShopSlotHighlight(GuiGraphics context, int x, int y, int z) {
        int color = -1862287543;
        context.fillGradient(RenderType.guiOverlay(), x, y, x + 16, y + 14, color, color, z);
        context.fillGradient(RenderType.guiOverlay(), x, y + 14, x + 15, y + 15, color, color, z);
        context.fillGradient(RenderType.guiOverlay(), x, y + 15, x + 14, y + 16, color, color, z);
    }

}
