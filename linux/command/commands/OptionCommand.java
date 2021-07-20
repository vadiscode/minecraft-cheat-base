package linux.command.commands;

import linux.command.Command;
import linux.module.Module;
import linux.module.ModuleManager;
import linux.option.Option;
import linux.option.OptionManager;
import linux.option.types.BooleanOption;
import linux.option.types.NumberOption;
import linux.util.ChatUtil;

public class OptionCommand extends Command {
    @Override
    public void runCommand(String[] args) {
        if (args.length < 2) {
            ChatUtil.sendMessage(OptionCommand.getHelpString());
            return;
        }
        Module mod = ModuleManager.getModule(args[0]);
        if (!mod.getId().equalsIgnoreCase("Null")) {
            Option option = OptionManager.getOption(args[1], mod.getId());
            if (option instanceof BooleanOption) {
            	BooleanOption booleanOption = (BooleanOption)option;
                booleanOption.setValue((Boolean)(booleanOption = (BooleanOption)option).getValue() == false);
                ChatUtil.sendMessage(String.valueOf(option.getDisplayName()) + " set to " + option.getValue());
                OptionManager.save();
            } else if (option instanceof NumberOption) {
                try {
                    option.setValue(Double.parseDouble(args[2]));
                    ChatUtil.sendMessage(String.valueOf(option.getDisplayName()) + " set to " + args[2]);
                }
                catch (NumberFormatException e) {
                	ChatUtil.sendMessage("Number option format error.");
                }
                OptionManager.save();
            } else {
            	ChatUtil.sendMessage("Option not recognized.");
            }
        } else {
        	ChatUtil.sendMessage(OptionCommand.getHelpString());
        }
    }

    public static String getHelpString() {
        return "Set option - (modname) (option name) <value>";
    }
}