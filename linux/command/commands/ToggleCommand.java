package linux.command.commands;

import linux.command.Command;
import linux.command.Command.Com;
import linux.module.Module;
import linux.module.ModuleManager;
import linux.util.ChatUtil;

@Com(names={"toggle", "t", "tog"})
public class ToggleCommand extends Command {
    @Override
    public void runCommand(String[] args) {
        Module module;
        String modName = "";
        if (args.length > 1) {
            modName = args[1];
        }
        if ((module = ModuleManager.getModule(modName)).getId().equalsIgnoreCase("null")) {
            ChatUtil.sendMessage("Invalid Module.");
            return;
        }
        module.toggle();
        ChatUtil.sendMessage(String.valueOf(String.valueOf(module.getDisplayName())) + " is now " + (module.isEnabled() ? "enabled" : "disabled"));
        ModuleManager.save();
    }

    @Override
    public String getHelp() {
        return "Toggle - toggle <t, tog> (module) - Toggles a module on or off";
    }
}