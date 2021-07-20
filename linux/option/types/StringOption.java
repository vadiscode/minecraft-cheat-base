package linux.option.types;

import java.lang.reflect.Field;

import linux.module.Module;
import linux.option.Option;

public class StringOption extends Option<String> {
    public StringOption(String id, String name, String value, Module module) {
        super(id, name, value, module);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        for (Field field : this.getModule().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Option.Op.class) || !field.getName().equalsIgnoreCase(this.getId())) continue;
            try {
                if (!field.getType().isAssignableFrom(String.class)) continue;
                field.set(this.getModule(), value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}