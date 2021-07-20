package linux.module.modules.render.hud;

import linux.event.events.Render2DEvent;
import linux.module.Module;
import linux.option.Option;
import linux.option.OptionManager;
import linux.option.types.BooleanOption;

public class Theme extends BooleanOption {
    public Theme(String name, boolean value, Module module) {
        super(name, name, value, module, true);
    }

    @Override
    public void setValue(Boolean value) {
        if (value.booleanValue()) {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof Theme)) continue;
                ((BooleanOption)option).setValueHard(false);
            }
        } else {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof Theme) || option == this) continue;
                ((BooleanOption)option).setValueHard(true);
                break;
            }
        }
        super.setValue(value);
    }

    public boolean enable() {
        return (Boolean)this.getValue();
    }

    public boolean onRender(Render2DEvent event) {
        return (Boolean)this.getValue();
    }
}