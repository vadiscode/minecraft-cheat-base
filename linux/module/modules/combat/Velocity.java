package linux.module.modules.combat;

import linux.event.EventTarget;
import linux.event.events.PacketReceiveEvent;
import linux.event.events.TickEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.option.Option.Op;
import linux.util.MinecraftUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

@Mod
public class Velocity extends Module implements MinecraftUtil {
	@Op(min=0.0, max=200.0, increment=5.0)
    private double percent = 0.0;

    @EventTarget
    private void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.getPacket();
            if (mc.theWorld.getEntityByID(packet.getEntityID()) == mc.thePlayer) {
                if (this.percent > 0.0) {
                    S12PacketEntityVelocity tmp52_51 = packet;
                    tmp52_51.motionX = (int)((double)tmp52_51.motionX * (this.percent / 100.0));
                    S12PacketEntityVelocity tmp71_70 = packet;
                    tmp71_70.motionY = (int)((double)tmp71_70.motionY * (this.percent / 100.0));
                    S12PacketEntityVelocity tmp90_89 = packet;
                    tmp90_89.motionZ = (int)((double)tmp90_89.motionZ * (this.percent / 100.0));
                } else {
                    event.setCancelled(true);
                }
            }
        } else if (event.getPacket() instanceof S27PacketExplosion) {
            S27PacketExplosion s27PacketExplosion;
            S27PacketExplosion packet2 = s27PacketExplosion = (S27PacketExplosion)event.getPacket();
            s27PacketExplosion.field_149152_f = (float)((double)s27PacketExplosion.field_149152_f * (this.percent / 100.0));
            S27PacketExplosion s27PacketExplosion2 = packet2;
            s27PacketExplosion2.field_149153_g = (float)((double)s27PacketExplosion2.field_149153_g * (this.percent / 100.0));
            S27PacketExplosion s27PacketExplosion3 = packet2;
            s27PacketExplosion3.field_149159_h = (float)((double)s27PacketExplosion3.field_149159_h * (this.percent / 100.0));
        }
    }
}
