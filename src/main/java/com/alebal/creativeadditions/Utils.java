package com.alebal.creativeadditions;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;

public final class Utils {
    public static void logToPlayer(String text) {
        if(Minecraft.getInstance().player != null)
            Minecraft.getInstance().player.displayClientMessage(new TextComponent(ChatFormatting.AQUA + text), false);
    }
}
