package linux.module.modules.combat.aura;

import linux.event.Event;
import linux.event.EventTarget;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.module.modules.combat.Aura;
import linux.util.AimUtil;
import linux.util.Location;
import linux.util.MinecraftUtil;
import linux.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;

public class Single extends AuraMode implements MinecraftUtil {
	private EntityLivingBase target;
	private Timer timer = new Timer();
	
	public Single(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@EventTarget
    public void onUpdate(UpdateEvent event) {
		Aura auraModule = (Aura)this.getModule();
        if (event.getState().equals((Object)Event.State.PRE)) {
            this.target = auraModule.getClosestEntity();
        } else {
            boolean block;
            double dist = 1.0E9;
            Entity entity = null;
            for (Object object : this.mc.theWorld.loadedEntityList) {
                EntityLivingBase e2;
                if (!(object instanceof EntityLivingBase) || !((double)(e2 = (EntityLivingBase)object).getDistanceToEntity(mc.thePlayer) < dist) || !(auraModule.isEntityValid(e2))) continue;
                dist = e2.getDistanceToEntity(mc.thePlayer);
                entity = e2;
            }
            boolean bl2 = block = entity != null && (Boolean)(auraModule.autoblock != false && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword);
            if (block && entity.getDistanceToEntity(mc.thePlayer) < (float)((auraModule.blockrange))) {
                this.mc.playerController.sendUseItem(mc.thePlayer, this.mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
            }
            if (auraModule.isEntityValid(this.target) && this.timer.hasReached((long) (1000 / auraModule.speed))) {
            	auraModule.attack(this.target);
                this.target = null;
                this.timer.reset();
            }
        }
        if (this.target != null) {
            float[] rotations = AimUtil.getRotations(this.target);
            event.setRotations(rotations[0], rotations[1]);
        }
    }
}
