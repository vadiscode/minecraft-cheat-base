package linux.module;

import java.awt.Color;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import linux.Client;
import linux.event.EventManager;
import linux.option.Option;
import linux.option.OptionManager;

public class Module {
    private String id;
    private String displayName;
    private boolean enabled;
    private Category category;
    private int keybind;
    private String suffix;
    private boolean shown;
    
    public void setProperties(String id, String displayName, Category type, int keybind, String suffix, boolean shown) {
        this.id = id;
        this.displayName = displayName;
        this.category = type;
        this.keybind = keybind;
        this.suffix = suffix;
        this.shown = shown;
    }

    public void preInitialize() {
    }

    public void toggle() {
        if (this.enabled) {
            this.disable();
        } else {
            this.enable();
        }
    }

    public void enable() {
        this.enabled = true;
        EventManager.register(this);
    }

    public void disable() {
        this.enabled = false;
        EventManager.unregister(this);
    }

    public List<Option> getOptions() {
        ArrayList<Option> optionList = new ArrayList<Option>();
        for (Option option : OptionManager.getOptionList()) {
            if (!option.getModule().equals(this)) continue;
            optionList.add(option);
        }
        return optionList;
    }

    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public boolean drawDisplayName(float x, float y, int color) {
        if (this.enabled && this.shown) {
            Client.clientFont.drawStringWithShadow(String.format("%s" + (this.suffix.length() > 0 ? " §7%s" : ""), this.displayName, this.suffix), x, y, color);
            return true;
        }
        return false;
    }

    public boolean drawDisplayName(float x, float y) {
        if (this.enabled && this.shown) {
        	Client.clientFont.drawStringWithShadow(String.format("%s" + (this.suffix.length() > 0 ? " §7%s" : ""), this.displayName, this.suffix), x, y, Color.WHITE.getRGB());
            return true;
        }
        return false;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isShown() {
        return this.shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Module getInstance() {
        for (Module mod : ModuleManager.getModules()) {
            if (!mod.getClass().equals(this.getClass())) continue;
            return mod;
        }
        return null;
    }

    public static enum Category {
        Combat,
        Movement,
        Render,
        Player,
        World;
    }

    @Target(value={ElementType.TYPE})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface Mod {
        public String displayName() default "null";
        public boolean enabled() default false;
        public int keybind() default -1;
        public boolean shown() default true;
        public String suffix() default "";
    }
}