package net.minecraft.util;

import org.lwjgl.input.Keyboard;

import linux.module.modules.player.InvMove;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState()
    {
    	if (new InvMove().getInstance().isEnabled() && !(Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
    		this.moveStrafe = 0.0F;
            this.moveForward = 0.0F;
            
            if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.getKeyCode())) {
                this.moveForward += 1.0f;
            }
            
            if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.getKeyCode())) {
                this.moveForward -= 1.0f;
            }
            
            if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.getKeyCode())) {
                this.moveStrafe += 1.0f;
            }
            
            if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.getKeyCode())) {
                this.moveStrafe -= 1.0f;
            }
            
            this.jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.getKeyCode());
            this.sneak = this.gameSettings.keyBindSneak.isKeyDown();

            if (this.sneak)
            {
                this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
                this.moveForward = (float)((double)this.moveForward * 0.3D);
            }
    	} else {
    		this.moveStrafe = 0.0F;
            this.moveForward = 0.0F;

            if (this.gameSettings.keyBindForward.isKeyDown())
            {
                ++this.moveForward;
            }

            if (this.gameSettings.keyBindBack.isKeyDown())
            {
                --this.moveForward;
            }

            if (this.gameSettings.keyBindLeft.isKeyDown())
            {
                ++this.moveStrafe;
            }

            if (this.gameSettings.keyBindRight.isKeyDown())
            {
                --this.moveStrafe;
            }

            this.jump = this.gameSettings.keyBindJump.isKeyDown();
            this.sneak = this.gameSettings.keyBindSneak.isKeyDown();

            if (this.sneak)
            {
                this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
                this.moveForward = (float)((double)this.moveForward * 0.3D);
            }
    	}
    }
}
