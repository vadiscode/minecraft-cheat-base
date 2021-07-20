package linux.module.modules.movement;

import linux.event.Event;
import linux.event.EventTarget;
import linux.event.events.PlayerSlowdownEvent;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.util.MinecraftUtil;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Mod
public class NoSlow extends Module implements MinecraftUtil {
	@EventTarget
    public void onSlowdown(PlayerSlowdownEvent event) {
        event.setCancelled(true);
    }

	@EventTarget
    public void onUpdate(UpdateEvent event) {
        if (!this.mc.thePlayer.isBlocking()) {
            return;
        }
        if (event.getState() == Event.State.PRE) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
        } else {
        	this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(0, 0, 0), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
        }
    }
}
