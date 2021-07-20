package linux.command.commands;

import org.lwjgl.input.Keyboard;

import linux.command.Command;
import linux.command.Command.Com;
import linux.module.Module;
import linux.module.ModuleManager;
import linux.util.ChatUtil;

@Com(names={"bind", "b"})
public class BindCommand extends Command {
    @Override
    public void runCommand(String[] args) {
        Module module;
        String modName = "";
        String keyName = "";
        if (args.length > 1) {
            modName = args[1];
            if (args.length > 2) {
                keyName = args[2];
            }
        }
        if ((module = ModuleManager.getModule(modName)).getId().equalsIgnoreCase("null")) {
            ChatUtil.sendMessage("Invalid Module.");
            return;
        }
        if (keyName == "") {
        	ChatUtil.sendMessage(String.valueOf(String.valueOf(module.getDisplayName())) + "'s bind has been cleared.");
            module.setKeybind(0);
            ModuleManager.save();
            return;
        }
        module.setKeybind(Keyboard.getKeyIndex(keyName.toUpperCase()));
        ModuleManager.save();
        if (Keyboard.getKeyIndex(keyName.toUpperCase()) == 0) {
        	ChatUtil.sendMessage("Invalid Key entered, Bind cleared.");
        } else {
        	ChatUtil.sendMessage(String.valueOf(String.valueOf(module.getDisplayName())) + " bound to " + keyName);
        }
    }

    @Override
    public String getHelp() {
        return "Bind - bind <b> (module) (key) - Bind a module to a key.";
    }
}