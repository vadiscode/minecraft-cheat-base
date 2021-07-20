package linux.module;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

import linux.Client;
import linux.event.EventManager;
import linux.event.EventTarget;
import linux.event.events.KeyPressEvent;
import linux.module.Module.Mod;
import linux.util.FileUtil;

public final class ModuleManager {
    private static final File MODULE_DIR = FileUtil.getConfigFile("Modules");
    private static List<Module> moduleList = new ArrayList<Module>();

    public static void start() {
        Reflections reflections = new Reflections("linux.module.modules", new Scanner[0]);
        Set<Class<? extends Module>> classes = (Set<Class<? extends Module>>)reflections.getSubTypesOf((Class)Module.class);
        for (Class clazz : classes) {
            try {
                Module.Mod modAnnotation;
                Module loadedModule = (Module)clazz.newInstance();
                if (!clazz.isAnnotationPresent(Module.Mod.class)) continue;
                loadedModule.setProperties(clazz.getSimpleName(), (modAnnotation = (Mod) clazz.getAnnotation(Module.Mod.class)).displayName().equals("null") ? clazz.getSimpleName() : modAnnotation.displayName(), Module.Category.valueOf(StringUtils.capitalize((String)clazz.getPackage().getName().split("modules.")[1])), modAnnotation.keybind(), modAnnotation.suffix(), modAnnotation.shown());
                if (modAnnotation.enabled()) {
                    loadedModule.enable();
                }
                moduleList.add(loadedModule);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        moduleList.sort(new Comparator<Module>(){

            @Override
            public int compare(Module m1, Module m2) {
                String s1 = m1.getDisplayName();
                String s2 = m2.getDisplayName();
                return s1.compareTo(s2);
            }
        });
        ModuleManager.load();
        ModuleManager.save();
        EventManager.register(new ModuleManager());
    }

    @EventTarget
    private void onKeyBoard(KeyPressEvent event) {
        for (Module mod : moduleList) {
            if (event.getKey() != mod.getKeybind()) continue;
            mod.toggle();
            ModuleManager.save();
        }
    }

    public static Module getModule(String modName) {
        for (Module module : moduleList) {
            if (!module.getId().equalsIgnoreCase(modName) && !module.getDisplayName().equalsIgnoreCase(modName)) continue;
            return module;
        }
        Module empty = new Module();
        empty.setProperties("Null", "Null", null, -1, null, false);
        return empty;
    }

    public static void load() {
        try {
            List<String> fileContent = FileUtil.read(MODULE_DIR);
            for (String line : fileContent) {
                String[] split = line.split(":");
                String id = split[0];
                String displayName = split[1];
                String shown = split[2];
                String keybind = split[3];
                String strEnabled = split[4];
                Module module = ModuleManager.getModule(id);
                module.setProperties(id, displayName, module.getCategory(), module.getKeybind(), module.getSuffix(), module.isShown());
                if (module.getId().equalsIgnoreCase("null")) continue;
                int moduleKeybind = keybind.equalsIgnoreCase("null") ? -1 : Integer.parseInt(keybind);
                boolean enabled = Boolean.parseBoolean(strEnabled);
                boolean visible = Boolean.parseBoolean(shown);
                if (enabled != module.isEnabled()) {
                    module.toggle();
                }
                module.setShown(visible);
                module.setDisplayName(displayName);
                module.setKeybind(moduleKeybind);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        for (Module module : moduleList) {
            String id = module.getId();
            String displayName = module.getDisplayName();
            String visible = Boolean.toString(module.isShown());
            String moduleKey = module.getKeybind() <= 0 ? "null" : Integer.toString(module.getKeybind());
            String enabled = Boolean.toString(module.isEnabled());
            fileContent.add(String.format("%s:%s:%s:%s:%s", id, displayName, visible, moduleKey, enabled));
        }
        FileUtil.write(MODULE_DIR, fileContent, true);
    }

    public static List<Module> getModules() {
        return moduleList;
    }

    public static List<Module> getModulesForRender() {
        List<Module> renderList = moduleList;
        renderList.sort(new Comparator<Module>(){

            @Override
            public int compare(Module m1, Module m2) {
                String s1 = String.format("%s" + (m1.getSuffix().length() > 0 ? " \u00a77[%s]" : ""), m1.getDisplayName(), m1.getSuffix());
                String s2 = String.format("%s" + (m2.getSuffix().length() > 0 ? " \u00a77[%s]" : ""), m2.getDisplayName(), m2.getSuffix());
                return (int) (Client.clientFont.getWidth(s2) - Client.clientFont.getWidth(s1));
            }
        });
        return renderList;
    }

}