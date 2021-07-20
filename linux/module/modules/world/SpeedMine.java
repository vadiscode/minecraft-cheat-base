package linux.module.modules.world;

import linux.event.EventTarget;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.util.MinecraftUtil;

@Mod
public class SpeedMine extends Module implements MinecraftUtil {
	@EventTarget
    public void onUpdate(UpdateEvent event) {
        if (this.mc.playerController.curBlockDamageMP > 0.8f) {
            this.mc.playerController.curBlockDamageMP = 1.0f;
        }
        this.mc.playerController.blockHitDelay = 0;
    }
}
