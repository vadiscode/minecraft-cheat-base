package linux.module.modules.combat;

import linux.event.EventTarget;
import linux.event.events.TickEvent;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.module.modules.combat.aura.Single;
import linux.option.Option.Op;
import linux.option.OptionManager;
import linux.util.AimUtil;
import linux.util.EntityUtils;
import linux.util.MathUtils;
import linux.util.MinecraftUtil;
import linux.util.friend.FriendManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

@Mod
public class Aura extends Module implements MinecraftUtil {
	private Single single = new Single("Single", true, this);
	@Op(min=0.0, max=20.0, increment=0.25)
    public double speed = 18.0;
	@Op(min=0.1, max=7.0, increment=0.1)
    public double range = 4.2;
    @Op(min=3.5, max=12.0, increment=0.5)
    public double blockrange = 8.0;
    @Op(min=0.0, max=360.0, increment=5.0)
    public double fov = 360.0;
    @Op
	private static boolean invisibles;
    @Op
    private static boolean players = true;
    @Op
    private static boolean animals = true;
    @Op
    private static boolean monsters = true;
    @Op
    public static boolean autoblock = true;
    @Op
    private static boolean dura = true;
	
	@Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.single);
        this.updateSuffix();
        super.preInitialize();
    }
	
	@EventTarget
    private void onUpdate(UpdateEvent event) {
        this.single.onUpdate(event);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.single.getValue()).booleanValue()) {
            this.setSuffix("Single");
        }
    }

	public boolean isEntityInFov(EntityLivingBase entity, double angle) {
        double angleDifference = MathUtils.getAngleDifference(mc.thePlayer.rotationYaw, AimUtil.getRotations(entity)[0]);
        return angleDifference > 0.0 && angleDifference < (angle *= 0.5) || -angle < angleDifference && angleDifference < 0.0;
    }

    public EntityLivingBase getClosestEntity() {
        double dist = 1.0E9;
        EntityLivingBase entity = null;
        for (Object object : this.mc.theWorld.loadedEntityList) {
            EntityLivingBase e2;
            if (!(object instanceof EntityLivingBase) || !((double)(e2 = (EntityLivingBase)object).getDistanceToEntity(mc.thePlayer) < dist) || !this.isEntityValid(e2) || !(e2.getDistanceToEntity(mc.thePlayer) < (this.range)) || e2.getName().contains("Body #")) continue;
            dist = e2.getDistanceToEntity(mc.thePlayer);
            entity = e2;
        }
        return entity;
    }

    public EntityLivingBase getNonBotEntity() {
        double dist = 1.0E9;
        EntityLivingBase entity = null;
        for (Object object : this.mc.theWorld.loadedEntityList) {
            EntityLivingBase e2;
            if (!(object instanceof EntityLivingBase) || !((double)(e2 = (EntityLivingBase)object).getDistanceToEntity(mc.thePlayer) < dist) || !this.isEntityValid(e2) || !(e2.getDistanceToEntity(mc.thePlayer) < (this.range)) || e2.getName().contains("Body #")) continue;
            dist = e2.getDistanceToEntity(mc.thePlayer);
            entity = e2;
        }
        return entity;
    }

    public void attack(EntityLivingBase entity) {
        boolean blocking = mc.thePlayer.isBlocking();
        if (blocking) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
        }
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
    }

    private void crit() {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.05, this.mc.thePlayer.posZ, false));
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.012511, this.mc.thePlayer.posZ, false));
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
    }

    public void attackLikeANiggerSperg(EntityLivingBase entity, boolean crit) {
        boolean blocking = mc.thePlayer.isBlocking();
        if (blocking) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
        }
        this.mc.thePlayer.swingItem();
        if (crit) {
            this.crit();
        } else {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
        }
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
    }

    public boolean isPlayerValid(EntityPlayer player) {
        return !player.isInvisible() || (Boolean)this.invisibles != false;
    }

    public boolean isEntityValidType(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer && ((Boolean)this.players && this.isPlayerValid((EntityPlayer)entity))) {
            return true;
        }
        boolean passive = false;
        boolean hostile = false;
        for (Class eClass : EntityUtils.animalsClasses) {
            if (eClass.getName() != entity.getClass().getName()) continue;
            passive = true;
            break;
        }
        for (Class eClass : EntityUtils.hostilesClasses) {
            if (eClass.getName() != entity.getClass().getName()) continue;
            hostile = true;
            break;
        }
        if (passive && ((Boolean)this.animals)) {
            return true;
        }
        return hostile && (Boolean)this.monsters != false;
    }

    public boolean isEntityValid(EntityLivingBase entity) {
        if (entity != null && entity != mc.thePlayer && this.isEntityValidType(entity) && this.isEntityInFov(entity, this.fov) && entity.isEntityAlive()) {
            if (!FriendManager.isFriend(entity.getName())) {
                return true;
            }
        }
        return false;
    }
}
