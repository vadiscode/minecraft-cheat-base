package linux.command.commands;

import linux.command.Command;
import linux.command.Command.Com;
import linux.util.ChatUtil;

@Com(names={""})
public class UnknownCommand extends Command {
    @Override
    public void runCommand(String[] args) {
        ChatUtil.sendMessage("There is no such command. Type \"help\" for open a list of commands.");
    }
}