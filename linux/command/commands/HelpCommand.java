package linux.command.commands;

import linux.command.Command;
import linux.command.Command.Com;
import linux.command.CommandManager;
import linux.util.ChatUtil;

@Com(names={"help"})
public class HelpCommand extends Command {
    @Override
    public void runCommand(String[] args) {
        for (Command command : CommandManager.commandList) {
            if (command instanceof OptionCommand || command.getHelp() == null) continue;
            ChatUtil.sendMessage(command.getHelp());
        }
        ChatUtil.sendMessage(OptionCommand.getHelpString());
    }

    @Override
    public String getHelp() {
        return "Help - help - Returns a list of commands.";
    }
}