package linux.util;

import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;

public class EntityUtils {
    public static final Class[] animalsClasses = new Class[]{EntityBat.class, EntityChicken.class, EntityCow.class, EntityMooshroom.class, EntityPig.class, EntitySheep.class, EntitySquid.class, EntityVillager.class, EntityHorse.class, EntityOcelot.class, EntityWolf.class, EntityIronGolem.class, EntitySnowman.class, EntityRabbit.class};
    public static final Class[] hostilesClasses = new Class[]{EntityBlaze.class, EntityCreeper.class, EntityGuardian.class, EntityEndermite.class, EntityGhast.class, EntityMagmaCube.class, EntitySilverfish.class, EntitySkeleton.class, EntitySlime.class, EntityWitch.class, EntityWither.class, EntityZombie.class, EntitySpider.class, EntityCaveSpider.class, EntityEnderman.class, EntityPigZombie.class};
}