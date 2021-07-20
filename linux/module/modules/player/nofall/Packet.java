package linux.module.modules.player.nofall;

import linux.event.EventTarget;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.util.MinecraftUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Packet extends NofallMode implements MinecraftUtil {
	public Packet(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@EventTarget
    public void onUpdate(UpdateEvent event) {
		if (this.mc.thePlayer.fallDistance > 3.0f) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
            this.mc.thePlayer.fallDistance = 0.0f;
        }
	}
}
