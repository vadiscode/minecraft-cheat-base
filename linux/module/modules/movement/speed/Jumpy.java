package linux.module.modules.movement.speed;

import java.util.List;

import linux.event.Event;
import linux.event.events.MoveEvent;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.module.modules.movement.Speed;
import linux.util.MinecraftUtil;

public class Jumpy extends SpeedMode implements MinecraftUtil {
    private static final double SPEED_BASE = 0.2873;
    private double moveSpeed;
    private double lastDist;
    private int stage;
    public static int settingUpTicks;

    public Jumpy(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            this.stage = 0;
            settingUpTicks = 2;
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event)) {
            if (mc.thePlayer.isCollidedHorizontally || mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing != 0.0f) {
                this.stage = 0;
                settingUpTicks = 5;
            } else {
                if (settingUpTicks > 0 && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                    this.moveSpeed = 0.09;
                    --settingUpTicks;
                } else if (this.stage == 1 && mc.thePlayer.isCollidedVertically && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                    this.moveSpeed = 1.0 + Speed.getBaseMoveSpeed() - 0.05;
                } else if (this.stage == 2 && mc.thePlayer.isCollidedVertically && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                    mc.thePlayer.motionY = 0.415;
                    event.setY(0.415);
                    this.moveSpeed *= 2.149;
                } else if (this.stage == 3) {
                    double difference = 0.66 * (this.lastDist - Speed.getBaseMoveSpeed());
                    this.moveSpeed = this.lastDist - difference;
                } else {
                    this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                }
                Speed.setMoveSpeed(event, this.moveSpeed);
                List collidingList = mc.theWorld.getCollidingBlockBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0, mc.thePlayer.motionY, 0.0));
                List collidingList2 = mc.theWorld.getCollidingBlockBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0, -0.4, 0.0));
                if (!(mc.thePlayer.isCollidedVertically || collidingList.size() <= 0 && collidingList2.size() <= 0 || this.stage <= 10)) {
                    if (this.stage >= 38) {
                        mc.thePlayer.motionY = -0.4;
                        event.setY(-0.4);
                        this.stage = 0;
                        settingUpTicks = 5;
                    } else {
                        mc.thePlayer.motionY = -0.001;
                        event.setY(-0.001);
                    }
                }
                if (settingUpTicks <= 0 && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                    ++this.stage;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
            double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
            double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        }
        return true;
    }
}