package linux.module.modules.render;

import java.util.Objects;

import org.lwjgl.opengl.GL11;

import linux.event.EventTarget;
import linux.event.events.NametagEvent;
import linux.event.events.NormaltagsEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.option.Option.Op;
import linux.util.MinecraftUtil;
import linux.util.RenderUtils;
import linux.util.friend.FriendManager;
import linux.util.render.GLUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;

@Mod
public class NameTags extends Module implements MinecraftUtil {
	@Op
	public boolean armor = true;
	@Op
	public boolean health = true;
	
	@EventTarget
    public void onRender(NametagEvent event) {
        for (Object o : this.mc.theWorld.playerEntities) {
            EntityPlayer p = (EntityPlayer)o;
            if (p == this.mc.getRenderViewEntity() || !p.isEntityAlive()) continue;
            this.mc.getRenderManager();
            double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosX;
            this.mc.getRenderManager();
            double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosY;
            this.mc.getRenderManager();
            double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
            this.renderNameTag(p, p.getDisplayName().getFormattedText(), pX, pY, pZ);
        }
    }
	
	@EventTarget
    public void cancelNormalTags(NormaltagsEvent event) {
        event.setCancelled(true);
    }
	
	private void renderNameTag(EntityPlayer entity, String tag, double x, double y, double z) {
        y += entity.isSneaking() ? 0.5 : 0.7;
        float var2 = this.mc.thePlayer.getDistanceToEntity(entity) / 4.0f;
        if (var2 < 1.7f) {
            var2 = 1.7f;
        }
        int colour = 16777215;
        if (FriendManager.isFriend(entity.getName())) {
            colour = 5488374;
        } else if (entity.isInvisible()) {
            colour = 16756480;
        }
        double health = Math.ceil(entity.getHealth() + entity.getAbsorptionAmount()) / 2.0;
        EnumChatFormatting healthColour = health > 10.0 ? EnumChatFormatting.DARK_GREEN : (health > 7.5 ? EnumChatFormatting.GREEN : (health > 5.0 ? EnumChatFormatting.YELLOW : (health > 2.5 ? EnumChatFormatting.GOLD : EnumChatFormatting.RED)));
        if (this.health) {
        	tag = Math.floor(health) == health ? String.valueOf(tag) + " " + (Object)((Object)healthColour) + (int)Math.floor(health) : String.valueOf(tag) + " " + (Object)((Object)healthColour) + health;
        }
        float scale = var2 * 1.25f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + 1.4f, (float)z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(this.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-(scale /= 100.0f), -scale, scale);
        GLUtil.setGLCap(2896, false);
        GLUtil.setGLCap(2929, false);
        int width = this.mc.fontRendererObj.getStringWidth(tag) / 2;
        GLUtil.setGLCap(3042, true);
        GL11.glBlendFunc(770, 771);
        RenderUtils.drawOldRect(-width - 2, -(this.mc.fontRendererObj.FONT_HEIGHT + 1), width + 2, 2.0f, 1.0f, -1728052224, -1728052224);
        this.mc.fontRendererObj.drawString(tag, -width, -(this.mc.fontRendererObj.FONT_HEIGHT - 1), colour, true);
        GL11.glPushMatrix();
        if (this.armor) {
            int xOffset = 0;
            for (ItemStack armourStack : entity.inventory.armorInventory) {
                if (!Objects.nonNull(armourStack)) continue;
                xOffset -= 8;
            }
            if (Objects.nonNull(entity.getHeldItem())) {
                xOffset -= 8;
                ItemStack renderStack = entity.getHeldItem().copy();
                if (renderStack.hasEffect() && (renderStack.getItem() instanceof ItemTool || renderStack.getItem() instanceof ItemArmor)) {
                    renderStack.stackSize = 1;
                }
                this.renderItemStack(renderStack, xOffset, -26);
                xOffset += 16;
            }
            for (ItemStack armourStack : entity.inventory.armorInventory) {
                if (!Objects.nonNull(armourStack)) continue;
                ItemStack renderStack2 = armourStack.copy();
                if (renderStack2.hasEffect() && (renderStack2.getItem() instanceof ItemTool || renderStack2.getItem() instanceof ItemArmor)) {
                    renderStack2.stackSize = 1;
                }
                this.renderItemStack(renderStack2, xOffset, -26);
                xOffset += 16;
            }
        }
        GL11.glPopMatrix();
        GLUtil.revertAllCaps();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

	private void renderItemStack(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        this.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, stack, x, y);
        this.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        this.renderEnchantText(stack, x, y);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }

	private void renderEnchantText(ItemStack stack, int x, int y) {
        int encY = y - 24;
        if (stack.getItem() instanceof ItemArmor) {
            int protectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
            int thornsLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
            int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (protectionLevel > 0) {
                this.mc.fontRendererObj.drawString("P" + protectionLevel, x * 2, encY, 16777215);
                encY += 8;
            }
            if (thornsLevel > 0) {
                this.mc.fontRendererObj.drawString("T" + thornsLevel, x * 2, encY, 16777215);
                encY += 8;
            }
            if (unbreakingLevel > 0) {
                this.mc.fontRendererObj.drawString("U" + unbreakingLevel, x * 2, encY, 16777215);
                encY += 8;
            }
        }
        if (stack.getItem() instanceof ItemBow) {
            int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
            int unbreakingLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (powerLevel > 0) {
                this.mc.fontRendererObj.drawString("P" + powerLevel, x * 2, encY, 16777215);
                encY += 8;
            }
            if (punchLevel > 0) {
                this.mc.fontRendererObj.drawString("K" + punchLevel, x * 2, encY, 16777215);
                encY += 8;
            }
            if (flameLevel > 0) {
                this.mc.fontRendererObj.drawString("F" + flameLevel, x * 2, encY, 16777215);
                encY += 8;
            }
            if (unbreakingLevel2 > 0) {
                this.mc.fontRendererObj.drawString("U" + unbreakingLevel2, x * 2, encY, 16777215);
                encY += 8;
            }
        }
        if (stack.getItem() instanceof ItemSword) {
            int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
            int knockbackLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
            int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            int unbreakingLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (sharpnessLevel > 0) {
                this.mc.fontRendererObj.drawString("S" + sharpnessLevel, x * 2, encY, 16777215);
                encY += 8;
            }
            if (knockbackLevel > 0) {
                this.mc.fontRendererObj.drawString("K" + knockbackLevel, x * 2, encY, 16777215);
                encY += 8;
            }
            if (fireAspectLevel > 0) {
                this.mc.fontRendererObj.drawString("F" + fireAspectLevel, x * 2, encY, 16777215);
                encY += 8;
            }
            if (unbreakingLevel3 > 0) {
                this.mc.fontRendererObj.drawString("U" + unbreakingLevel3, x * 2, encY, 16777215);
            }
        }
    }
}
