package linux.module.modules.render;

import org.lwjgl.input.Keyboard;

import linux.Client;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.util.MinecraftUtil;

@Mod(shown = false,keybind = Keyboard.KEY_RSHIFT)
public class ClickGUI extends Module implements MinecraftUtil {	
	@Override
    public void enable() {
        mc.displayGuiScreen(Client.clickGUI);
    }
}