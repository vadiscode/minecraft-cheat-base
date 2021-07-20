package linux.util;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class AimUtil implements MinecraftUtil {
    public static float[] getRotations(Entity entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX + (entity.posX - entity.lastTickPosX) - mc.thePlayer.posX;
        double diffZ = entity.posZ + (entity.posZ - entity.lastTickPosZ) - mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + (double)elb.getEyeHeight() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() - 0.3);
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY += entity.posY - entity.lastTickPosY, dist) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float getYawChangeToEntity(Entity entity) {
        Minecraft mc2 = mc.getMinecraft();
        double deltaX = entity.posX - mc2.thePlayer.posX;
        double deltaZ = entity.posZ - mc2.thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(-Math.atan(deltaX / deltaZ)));
        return MathHelper.wrapAngleTo180_float(-(mc2.thePlayer.rotationYaw - (float)yawToEntity));
    }

    public static float getPitchChangeToEntity(Entity entity) {
        Minecraft mc2 = mc.getMinecraft();
        double deltaX = entity.posX - mc2.thePlayer.posX;
        double deltaZ = entity.posZ - mc2.thePlayer.posZ;
        double deltaY = entity.posY - 1.6 + (double)entity.getEyeHeight() - 0.4 - mc2.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(mc2.thePlayer.rotationPitch - (float)pitchToEntity);
    }

    public static float[] getRotationsz(EntityLivingBase ent) {
        double x2 = ent.posX;
        double z2 = ent.posZ;
        double y2 = ent.posY + (double)(ent.getEyeHeight() / 2.0f);
        return AimUtil.getRotationFromPosition(x2, z2, y2);
    }

    public static float[] getAverageRotations(List<EntityLivingBase> targetList) {
        double posX = 0.0;
        double posY = 0.0;
        double posZ = 0.0;
        for (Entity ent : targetList) {
            posX += ent.posX;
            posY += ent.boundingBox.maxY - 2.0;
            posZ += ent.posZ;
        }
        return new float[]{AimUtil.getRotationFromPosition(posX /= (double)targetList.size(), posZ /= (double)targetList.size(), posY /= (double)targetList.size())[0], AimUtil.getRotationFromPosition(posX, posZ, posY)[1]};
    }

    public static float[] getRotationFromPosition(double x2, double z2, double y2) {
        double xDiff = x2 - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z2 - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y2 - Minecraft.getMinecraft().thePlayer.posY - 0.8;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
        float g2 = 0.006f;
        float sqrt = velocity * velocity * velocity * velocity - 0.006f * (0.006f * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float)Math.toDegrees(Math.atan(((double)(velocity * velocity) - Math.sqrt(sqrt)) / (double)(0.006f * d3)));
    }

    public static float getNewAngle(float angle) {
        if ((angle %= 360.0f) >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 360.0f - angle3;
        }
        return angle3;
    }

    public static double normalizeAngle(double angle) {
        return (angle + 360.0) % 360.0;
    }

    public static float normalizeAngle(float angle) {
        return (angle + 360.0f) % 360.0f;
    }
}