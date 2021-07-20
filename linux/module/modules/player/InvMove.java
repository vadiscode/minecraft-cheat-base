package linux.module.modules.player;

import org.lwjgl.input.Keyboard;

import linux.event.EventTarget;
import linux.event.events.Render2DEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.util.MinecraftUtil;
import net.minecraft.client.gui.GuiChat;

@Mod
public class InvMove extends Module implements MinecraftUtil {
    @EventTarget
    private void onRender(Render2DEvent event) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                this.pitch(this.pitch() - 2.0f);
            }
            if (Keyboard.isKeyDown(208)) {
            	this.pitch(this.pitch() + 2.0f);
            }
            if (Keyboard.isKeyDown(203)) {
            	this.yaw(this.yaw() - 3.0f);
            }
            if (Keyboard.isKeyDown(205)) {
            	this.yaw(this.yaw() + 3.0f);
            }
        }
    }
    
    public static float yaw() {
        return mc.thePlayer.rotationYaw;
    }

    public static void yaw(float yaw) {
    	mc.thePlayer.rotationYaw = yaw;
    }

	public static float pitch() {
        return mc.thePlayer.rotationPitch;
    }

    public static void pitch(float pitch) {
        mc.thePlayer.rotationPitch = pitch;
    }
}