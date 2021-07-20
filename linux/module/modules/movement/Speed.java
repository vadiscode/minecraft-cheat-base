package linux.module.modules.movement;

import linux.event.EventTarget;
import linux.event.events.MoveEvent;
import linux.event.events.TickEvent;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.module.modules.movement.speed.Bhop;
import linux.module.modules.movement.speed.Jumpy;
import linux.option.OptionManager;
import linux.util.MinecraftUtil;
import net.minecraft.potion.Potion;

@Mod
public class Speed extends Module implements MinecraftUtil {
	private Jumpy jumpy = new Jumpy("Jumpy", true, this);
	private Bhop bhop = new Bhop("Bhop", false, this);
	
	@Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.jumpy);
        OptionManager.getOptionList().add(this.bhop);
        this.updateSuffix();
        super.preInitialize();
    }

    @Override
    public void enable() {
        this.jumpy.enable();
        this.bhop.enable();
        super.enable();
    }

    @EventTarget
    private void onMove(MoveEvent event) {
        this.jumpy.onMove(event);
        this.bhop.onMove(event);
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        this.jumpy.onUpdate(event);
        this.bhop.onUpdate(event);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.jumpy.getValue()).booleanValue()) {
            this.setSuffix("Jumpy");
        }
        else if (((Boolean)this.bhop.getValue()).booleanValue()) {
            this.setSuffix("Bhop");
        }
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static void setMoveSpeed(MoveEvent event, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
}
