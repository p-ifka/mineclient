package a.b.c;

import a.b.c.util.ChatUtil;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;

public class CommandHandler
{
    public static final char COMMAND_PREFIX = ':';

    public CommandHandler()
    {
	MinecraftForge.EVENT_BUS.register(this);
    }
    
    
    String[] tokenizeCommand(String msg)
    {
	ArrayList<String> result;
	String[] t = {null};
	String tmp;
	int len;
	char ch;

	result = new ArrayList<String>();
	len = msg.length();
	tmp = new String();
	for(int i=1; i<len; i++) {
	    ch = msg.charAt(i);
	    if(ch == ' ' || i == (len - 1)) {
		if(i == (len - 1)) { tmp = tmp + ch; }
		result.add(tmp);
		tmp = new String();
	    } else {
		tmp = tmp + ch;
	    }
	}

	return result.toArray(t);
    }

    private boolean processModuleCommand(String[] cmd)
    {
	for(int i=0; i<Main.modules.size(); i++) {
	    ModuleConfig.ModuleCfg modCfg = Main.moduleConfigs.get(i);
	    if(cmd[0].equals(modCfg.name)) {
		if(cmd.length <= 1) {
		    ChatUtil.sendClientMessage(Main.modules.get(i).help(modCfg));
		    return true;
		}
		if(cmd[1].equals("enable") || cmd[1].equals("t") || cmd[1].equals("1")) {
		    ChatUtil.sendClientMessage("enabled " + modCfg.name);
		    modCfg.enabled = true;
		    return true;
		}
		if(cmd[1].equals("disable") || cmd[1].equals("f") || cmd[1].equals("0")) {
		    ChatUtil.sendClientMessage("disabled " + modCfg.name);
		    modCfg.enabled = false;
		    return true;
		}
		if(cmd[1].equals("toggle")) {
		    if(modCfg.enabled) {
			ChatUtil.sendClientMessage("disabled " + modCfg.name);
			modCfg.enabled = false;
		    } else {
			ChatUtil.sendClientMessage("enabled " + modCfg.name);
			modCfg.enabled = true;			
		    }
		    return true;
		}
		if(cmd[1].equals("set")) {
		    if(cmd.length < 4) {
			ChatUtil.sendClientMessage("usage: :{module-name} set {option} {value}");
			return true;
		    }
		    if (ModuleConfig.setValue(cmd[2], cmd[3], modCfg)) {
			ChatUtil.sendClientMessage(cmd[2] + "=" + cmd[3]);
			return true;
		    } else {
			ChatUtil.sendClientMessage("option not found or not applicable to provided value");
			return true;
		    }
		}
		
		return false;
	    }
	}
	return false;
    }
    
    @SubscribeEvent
    public void onClientChatEvent(ClientChatEvent event)
    {
	String msg;
	String[] cmd;

	msg = event.getMessage();
	if(msg.charAt(0) != COMMAND_PREFIX) {
	    return;
	}
	event.setCanceled(true); // dont send message
	
	if(msg.length() < 2) {
	    return;
	}
	

	cmd = tokenizeCommand(msg);

	if(cmd.length < 1) {
	    return;
	}

	if(cmd[0].equals("modules")) {
	    String response = "module list:\n";
	    for(int i=0; i<Main.modules.size(); i++) {
		ModuleConfig.ModuleCfg cfg = Main.moduleConfigs.get(i);
		response = response.concat(" >" + cfg.name);
	    }
	    ChatUtil.sendClientMessage(response);
	} else if(!processModuleCommand(cmd)) {
	    ChatUtil.sendClientMessage("command not found, run :help for a list of commands or :modules for a list of modules");
	}
	
    }
}
