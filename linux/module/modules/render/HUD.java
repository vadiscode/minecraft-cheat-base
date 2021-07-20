package linux.module.modules.render;

import java.awt.Color;

import linux.event.EventTarget;
import linux.event.events.Render2DEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.module.modules.render.hud.LinuxTheme;
import linux.option.Option.Op;
import linux.option.OptionManager;

@Mod(enabled=true, shown=false)
public class HUD extends Module {
	private LinuxTheme linuxTheme = new LinuxTheme("Linux Theme", true, this);
	@Op
	public static boolean time = true;
	
	@Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.linuxTheme);
        super.preInitialize();
    }

    @EventTarget
    private void onRender2D(Render2DEvent event) {
        this.linuxTheme.onRender(event);
    }
    
    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
}
