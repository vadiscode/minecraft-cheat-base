package linux.module.modules.movement;

import linux.event.EventTarget;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.util.MinecraftUtil;

@Mod
public class Sprint extends Module implements MinecraftUtil {
	@EventTarget
    public void onUpdate(UpdateEvent event) {
        if (this.mc.thePlayer.getFoodStats().getFoodLevel() >= 6 && this.mc.thePlayer.moveForward > 0.0f) {
            this.mc.thePlayer.setSprinting(true);
        }
    }
}
