package linux.option;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.StringUtils;

import linux.command.CommandManager;
import linux.module.Module;
import linux.module.ModuleManager;
import linux.option.types.BooleanOption;
import linux.option.types.NumberOption;
import linux.option.types.StringOption;
import linux.util.FileUtil;

public final class OptionManager {
    private static final File OPTION_DIR = FileUtil.getConfigFile("Options");
    private static CopyOnWriteArrayList<Option> optionList = new CopyOnWriteArrayList();
    
    public static void start() {
        try {
            for (Module mod : ModuleManager.getModules()) {
                mod.preInitialize();
                Field[] arrfield = mod.getClass().getDeclaredFields();
                int n = arrfield.length;
                for (int i = 0; i < n; ++i) {
                    Object value;
                    Option.Op opAnnotation;
                    String name;
                    Field field = arrfield[i];
                    field.setAccessible(true);
                    if (!field.isAnnotationPresent(Option.Op.class)) continue;
                    if (field.getType().isAssignableFrom(Float.TYPE) || field.getType().isAssignableFrom(Double.TYPE) || field.getType().isAssignableFrom(Integer.TYPE) || field.getType().isAssignableFrom(Long.TYPE) || field.getType().isAssignableFrom(Short.TYPE) || field.getType().isAssignableFrom(Byte.TYPE)) {
                        try {
                            value = (Number)field.get(mod);
                        }
                        catch (IllegalArgumentException e) {
                            value = 0;
                        }
                        opAnnotation = field.getAnnotation(Option.Op.class);
                        name = opAnnotation.name().equalsIgnoreCase("null") ? StringUtils.capitalize((String)field.getName()) : StringUtils.capitalize((String)opAnnotation.name());
                        NumberOption option = new NumberOption(field.getName(), StringUtils.capitalize((String)name), (Number)value, mod);
                        option.setMin(opAnnotation.min());
                        option.setMax(opAnnotation.max());
                        option.setIncrement(opAnnotation.increment());
                        optionList.add(option);
                        continue;
                    }
                    if (field.getType().isAssignableFrom(Boolean.TYPE)) {
                        boolean value2;
                        try {
                            value2 = field.getBoolean(mod);
                        }
                        catch (IllegalArgumentException e) {
                            value2 = false;
                        }
                        opAnnotation = field.getAnnotation(Option.Op.class);
                        name = opAnnotation.name().equalsIgnoreCase("null") ? StringUtils.capitalize((String)field.getName()) : StringUtils.capitalize((String)opAnnotation.name());
                        optionList.add(new BooleanOption(field.getName(), StringUtils.capitalize((String)name), value2, mod, false));
                        continue;
                    }
                    if (!field.getType().isAssignableFrom(String.class)) continue;
                    try {
                        value = (String)field.get(mod);
                    }
                    catch (IllegalArgumentException e) {
                        value = "";
                    }
                    opAnnotation = field.getAnnotation(Option.Op.class);
                    name = opAnnotation.name().equalsIgnoreCase("null") ? StringUtils.capitalize((String)field.getName()) : StringUtils.capitalize((String)opAnnotation.name());
                    optionList.add(new StringOption(field.getName(), StringUtils.capitalize((String)name), (String)value, mod));
                }
                ArrayList<String> nameList = new ArrayList<String>();
                if (CommandManager.optionCommand.getNames() != null) {
                    for (String name : CommandManager.optionCommand.getNames()) {
                        nameList.add(name);
                    }
                }
                nameList.add(mod.getId());
                nameList.add(mod.getDisplayName());
                CommandManager.optionCommand.setNames(nameList.toArray(new String[0]));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        OptionManager.load();
        OptionManager.save();
    }

    public static void load() {
        List<String> fileContent = FileUtil.read(OPTION_DIR);
        for (String line : fileContent) {
            try {
                String[] split = line.split(":");
                String optionId = split[0];
                String optionValue = split[1];
                String modId = split[2];
                Option option = OptionManager.getOption(optionId, split[2]);
                if (option == null) continue;
                if (option instanceof NumberOption) {
                    ((NumberOption)option).setValue(optionValue);
                    continue;
                }
                if (option instanceof BooleanOption) {
                    ((BooleanOption)option).setValueHard(Boolean.parseBoolean(optionValue));
                    continue;
                }
                if (!(option instanceof StringOption)) continue;
                option.setValue(optionValue);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        for (Option option : optionList) {
            String optionId = option.getId();
            String optionVal = option.getValue().toString();
            Module mod = option.getModule();
            fileContent.add(String.format("%s:%s:%s", optionId, optionVal, mod.getId()));
        }
        FileUtil.write(OPTION_DIR, fileContent, true);
    }

    public static Option getOption(String optionName, String modId) {
        for (Option option : optionList) {
            if (!option.getId().equalsIgnoreCase(optionName) && !option.getDisplayName().equalsIgnoreCase(optionName) || !option.getModule().getId().equalsIgnoreCase(modId)) continue;
            return option;
        }
        return new Option<Integer>("Null", "Null", 0, new Module());
    }

    public static List<Option> getOptionList() {
        return optionList;
    }
}