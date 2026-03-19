package a.b.c.util;

import a.b.c.Main;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil
{
    /**
    * send message to user
    **/
    public static void sendClientMessage(String text)
    {
	if(Main.mc != null) {
	    Main.mc.player.sendMessage(new TextComponentString(text));
	}
    }
}
