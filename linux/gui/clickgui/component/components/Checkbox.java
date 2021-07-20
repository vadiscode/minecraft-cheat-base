package linux.gui.clickgui.component.components;

import org.lwjgl.opengl.GL11;

import linux.gui.clickgui.ClickUI;
import linux.gui.clickgui.component.Component;
import linux.module.modules.render.HUD;
import linux.option.types.BooleanOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Checkbox extends Component {
    private boolean hovered;
    private BooleanOption op;
    private Button parent;
    private int offset;
    private int x;
    private int y;

    public Checkbox(BooleanOption option, Button button, int offset) {
        this.op = option;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset + 12, this.hovered ? -15724528 : -15724528);
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.op.getDisplayName(), (this.parent.parent.getX() + 10 + 4) * 2 + 5, (this.parent.parent.getY() + this.offset + 2) * 2 + 4, -1, true);
        GL11.glPopMatrix();
        Gui.drawRect(this.parent.parent.getX() + 3 + 4, this.parent.parent.getY() + this.offset + 3, this.parent.parent.getX() + 9 + 4, this.parent.parent.getY() + this.offset + 9, -6710887);
        if (this.op.getValue().booleanValue()) {
            Gui.drawRect(this.parent.parent.getX() + 4 + 4, this.parent.parent.getY() + this.offset + 4, this.parent.parent.getX() + 8 + 4, this.parent.parent.getY() + this.offset + 8, -12566464);
        }
        Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, this.parent.parent.getX() + 3, this.parent.parent.getY() + this.offset, HUD.rainbow(0));
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
        	this.op.toggle();
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
    }
}