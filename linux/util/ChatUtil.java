package linux.util;

import net.minecraft.util.EnumChatFormatting;

public class ChatUtil {
	public static void sendMessage(String message) {
        new ChatMessage.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static void sendMessage(String message, boolean prefix) {
        new ChatMessage.ChatMessageBuilder(prefix, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }
}
