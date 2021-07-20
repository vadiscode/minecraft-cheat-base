package linux.module.modules.combat.aura;

import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.option.Option;
import linux.option.OptionManager;
import linux.option.types.BooleanOption;

public class AuraMode extends BooleanOption {
    public AuraMode(String name, boolean value, Module module) {
        super(name, name, value, module, true);
    }

    @Override
    public void setValue(Boolean value) {
        if (value.booleanValue()) {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof AuraMode)) continue;
                ((BooleanOption)option).setValueHard(false);
            }
        } else {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof AuraMode) || option == this) continue;
                ((BooleanOption)option).setValueHard(true);
                break;
            }
        }
        super.setValue(value);
    }

    public void onUpdate(UpdateEvent event) {
        return;
    }
}
