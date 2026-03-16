package a.b.c;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
* 
**/
@Mod(modid=Main.modID, name=Main.modName, version=Main.modVersion)
public class Main
{
    public static final String modID = "c";
    public static final String modName = "c";
    public static final String modVersion = "0.1";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
	Minecraft mc;

	MinecraftForge.EVENT_BUS.register(this);
	mc = Minecraft.getMinecraft();
	
	
    }
    
    
}
