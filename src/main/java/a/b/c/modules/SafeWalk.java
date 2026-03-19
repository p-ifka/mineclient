package a.b.c.modules;

import a.b.c.Main;
import a.b.c.ModuleConfig;
import a.b.c.templ.Module;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;

public class SafeWalk
extends Module
{
    private static int MIN_FALL;
    private static boolean sneaking = false;
    
    @Override
    public void init(ModuleConfig.ModuleCfg cfg)
    {
	cfg.name = "safewalk";
	MIN_FALL = ModuleConfig.addValue("min-fall", 6, cfg);
    }

    @Override
    public String help(ModuleConfig.ModuleCfg cfg)
    {
	return "safewalk: sneak when on an edge\n"
	+ "options:\n"
	+ "   >number min-fall: {" + cfg.numberValues.get(MIN_FALL) + "} minimum fall distance for safewalk to engage on";
    }

    

    @Override
    public void onTick(ClientTickEvent event, ModuleConfig.ModuleCfg cfg)
    {
	BlockPos playerPos = player.getPosition();
	playerPos = playerPos.down(1);
	if(mc.world.getBlockState(playerPos).getBlock().equals(Blocks.AIR)) {
	    sneaking = true;
	    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
	} else {
	    if(sneaking) {
		sneaking = false;
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
	    }
	}
    }
    
}
