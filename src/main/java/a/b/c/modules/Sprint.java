package a.b.c.modules;

import a.b.c.Main;
import a.b.c.ModuleConfig;
import a.b.c.templ.Module;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.settings.KeyBinding;

public class Sprint
extends Module
{

    public static int REQUIRE_MOVING;
    
    @Override
    public void init(ModuleConfig.ModuleCfg cfg)
    {
	cfg.name = "sprint";
	REQUIRE_MOVING = ModuleConfig.addValue(cfg, "require-moving", true);
    }

    @Override
    public String help(ModuleConfig.ModuleCfg cfg)
    {
	return "sprint: always sprint\n"
	+ "options:\n"
	+ "   >bool require-moving: {" + cfg.booleanValues.get(REQUIRE_MOVING) + "} whether to only sprint when a movement key is pressed";
    }

    

    @Override
    public void onTick(ClientTickEvent event, ModuleConfig.ModuleCfg cfg)
    {
	if(!cfg.booleanValues.get(REQUIRE_MOVING) || (Main.mc.player.movementInput.forwardKeyDown
	|| Main.mc.player.movementInput.leftKeyDown
	|| Main.mc.player.movementInput.rightKeyDown
	|| Main.mc.player.movementInput.backKeyDown))
	{
	    Main.mc.player.setSprinting(true);
	}
    }
    
}
