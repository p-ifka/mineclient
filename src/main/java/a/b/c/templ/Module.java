package a.b.c.templ;

import a.b.c.ModuleConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Module
{
    public String help(ModuleConfig.ModuleCfg cfg) { return "no help"; }
    
    public void init(ModuleConfig.ModuleCfg cfg) {}
    
    public void onTick(ClientTickEvent event, ModuleConfig.ModuleCfg cfg) {}
}
