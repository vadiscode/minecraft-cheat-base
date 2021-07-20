package linux.gui.clickgui.component.components;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import linux.gui.clickgui.component.Component;
import linux.gui.clickgui.component.Frame;
import linux.module.Module;
import linux.option.Option;
import linux.option.types.BooleanOption;
import linux.option.types.NumberOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Button extends Component {
    public Module mod;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    private ArrayList<Component> subcomponents;
    public boolean open;
    private int height;

    public Button(Module mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList();
        this.open = false;
        this.height = 12;
        int opY = offset + 12;
        if (!mod.getOptions().isEmpty()) {
            for (Option option : mod.getOptions()) {
                if (option instanceof NumberOption) {
                    Slider slider = new Slider((NumberOption)option, this, opY);
                    this.subcomponents.add(slider);
                    opY += 12;
                }
                if (!(option instanceof BooleanOption)) continue;
                Checkbox check = new Checkbox((BooleanOption)option, this, opY);
                this.subcomponents.add(check);
                opY += 12;
            }
        }
        this.subcomponents.add(new Keybind(this, opY));
        this.subcomponents.add(new VisibleButton(this, mod, opY));
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
        int opY = this.offset + 12;
        for (Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isEnabled() ? -14540254 : -14540254) : (this.mod.isEnabled() ? -15592942 : -15592942));
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.mod.getDisplayName(), (this.parent.getX() + 2) * 2, (this.parent.getY() + this.offset + 2) * 2 + 4, this.mod.isEnabled() ? -1 : 10066329, true);
        if (this.subcomponents.size() > 2) {
            Minecraft.getMinecraft().fontRendererObj.drawString(this.open ? "-" : "+", (this.parent.getX() + this.parent.getWidth() - 10) * 2, (this.parent.getY() + this.offset + 2) * 2 + 4, -1, true);
        }
        GL11.glPopMatrix();
        if (this.open && !this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.renderComponent();
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return 12 * (this.subcomponents.size() + 1);
        }
        return 12;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
}