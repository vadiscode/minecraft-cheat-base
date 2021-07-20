package linux.option.types;

import java.lang.reflect.Field;

import linux.module.Module;
import linux.option.Option;

public class BooleanOption extends Option<Boolean> {
    private boolean isModType;

    public BooleanOption(String id, String name, boolean value, Module module, boolean isModType) {
        super(id, name, value, module);
        this.isModType = isModType;
    }

    @Override
    public void setValue(Boolean value) {
        super.setValue(value);
        for (Field field : this.getModule().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Option.Op.class) || !field.getName().equalsIgnoreCase(this.getId())) continue;
            try {
                if (!field.getType().isAssignableFrom(Boolean.TYPE)) continue;
                field.setBoolean(this.getModule(), value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setValueHard(Boolean value) {
        super.setValue(value);
        for (Field field : this.getModule().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Option.Op.class) || !field.getName().equalsIgnoreCase(this.getId())) continue;
            try {
                if (!field.getType().isAssignableFrom(Boolean.TYPE)) continue;
                field.setBoolean(this.getModule(), value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void toggle() {
        this.setValue((Boolean)this.getValue() == false);
    }

    public boolean isModType() {
        return this.isModType;
    }
}