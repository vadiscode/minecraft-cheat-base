package linux.module.modules.movement.speed;

import java.util.List;

import linux.event.Event;
import linux.event.events.MoveEvent;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.module.modules.movement.Speed;
import linux.util.MathUtils;
import linux.util.MinecraftUtil;
import net.minecraft.client.entity.EntityPlayerSP;

public class Bhop extends SpeedMode implements MinecraftUtil {
	private double moveSpeed;
    private double lastDist;
    private double stage;

	public Bhop(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
    public boolean enable() {
        if (super.enable()) {
            if (mc.thePlayer != null) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            this.stage = 4.0;
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event)) {
            if (MathUtils.roundToPlace(mc.thePlayer.posY - (double)((int)mc.thePlayer.posY), 3) == MathUtils.roundToPlace(0.138, 3)) {
                EntityPlayerSP player = mc.thePlayer;
                player.motionY -= 0.08;
                event.setY(event.getY() - 0.0931);
                EntityPlayerSP player2 = mc.thePlayer;
                player2.posY -= 0.0931;
            }
            if (this.stage == 2.0 && (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
                mc.thePlayer.motionY = 0.4;
                event.setY(0.4);
                this.moveSpeed *= 2.149;
            } else if (this.stage == 3.0) {
                double difference = 0.66 * (this.lastDist - Speed.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            } else {
                List collidingList = mc.theWorld.getCollidingBlockBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0, mc.thePlayer.motionY, 0.0));
                if (collidingList.size() > 0 || mc.thePlayer.isCollidedVertically) {
                    this.stage = 1.0;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed());
            Speed.setMoveSpeed(event, this.moveSpeed);
            this.stage += 1.0;
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
