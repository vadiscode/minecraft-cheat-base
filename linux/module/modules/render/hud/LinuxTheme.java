package linux.module.modules.render.hud;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import linux.Client;
import linux.event.events.Render2DEvent;
import linux.module.Module;
import linux.module.ModuleManager;
import linux.module.modules.render.HUD;

public class LinuxTheme extends Theme {
    public LinuxTheme(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onRender(Render2DEvent event) {
        if (super.onRender(event)) {
        	String time = new SimpleDateFormat("H:mm").format(new Date());
        	Client.clientFont.drawStringWithShadow("L", 3, 3, HUD.rainbow(0));
        	if (HUD.time == true) {
        		Client.clientFont.drawStringWithShadow("inux §7" + time, 8, 3, Color.WHITE.getRGB());
        	} else {
        		Client.clientFont.drawStringWithShadow("inux", 8, 3, Color.WHITE.getRGB());
        	}
	        int y = 1;
	        for (Module mod : ModuleManager.getModulesForRender()) {
	            if (!mod.drawDisplayName(event.getWidth() - Client.clientFont.getWidth(String.format("%s" + (mod.getSuffix().length() > 0 ? " §7%s" : ""), mod.getDisplayName(), mod.getSuffix())) - 1, y, Color.WHITE.getRGB())) continue;
	            y += 10;
	        }
        }
        return super.onRender(event);
    }
}