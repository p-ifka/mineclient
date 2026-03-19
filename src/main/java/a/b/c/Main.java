package a.b.c;

import a.b.c.templ.Module;
import a.b.c.modules.*;
import a.b.c.ModuleConfig;
import a.b.c.CommandHandler;

import java.util.HashMap;
import java.util.ArrayList;



import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


/**
* 
**/
@Mod(modid=Main.modID, name=Main.modName, version=Main.modVersion)
public class Main
{
    public static final String modID = "c";
    public static final String modName = "c";
    public static final String modVersion = "0.1";

    public static Minecraft mc;
    public static ArrayList<Module> modules = new ArrayList<Module>();
    public static ArrayList<ModuleConfig.ModuleCfg> moduleConfigs = new ArrayList<ModuleConfig.ModuleCfg>();


    @EventHandler
    public void init(FMLInitializationEvent event)
    {
	mc = Minecraft.getMinecraft();
	MinecraftForge.EVENT_BUS.register(this);

	CommandHandler commandHandler = new CommandHandler();
	
	modules.add(new Sprint());

	for(int i=0; i<modules.size();i++) {
	    moduleConfigs.add(new ModuleConfig.ModuleCfg());
	    modules.get(i).init(moduleConfigs.get(i));
	}
	
    }

    @SubscribeEvent
    public void onClientTickEvent(ClientTickEvent event)
    {
	if(mc.player == null) { return; }
	for(int i=0; i<modules.size();i++) {
	    if(moduleConfigs.get(i).enabled) {
		modules.get(i).onTick(event, moduleConfigs.get(i));
	    }
	}
    }
    
    
}
