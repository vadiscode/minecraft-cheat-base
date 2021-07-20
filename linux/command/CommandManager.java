package linux.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

import linux.command.Command.Com;
import linux.command.commands.OptionCommand;
import linux.command.commands.UnknownCommand;

public final class CommandManager {
    public static List<Command> commandList = new ArrayList<Command>();
    public static OptionCommand optionCommand = new OptionCommand();
    public static final UnknownCommand unknownCommand = new UnknownCommand();

    public static void start() {
        try {
            Reflections reflections = new Reflections("linux.command.commands", new Scanner[0]);
            Set<Class<? extends Command>> classes = (Set<Class<? extends Command>>)reflections.getSubTypesOf((Class)Command.class);
            for (Class clazz : classes) {
                Command loadedCommand = (Command)clazz.newInstance();
                if (!clazz.isAnnotationPresent(Command.Com.class)) continue;
                Command.Com comAnnotation = (Com) clazz.getAnnotation(Command.Com.class);
                loadedCommand.setNames(comAnnotation.names());
                commandList.add(loadedCommand);
            }
            commandList.add(optionCommand);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Command getCommandFromMessage(String message) {
        for (Command command : commandList) {
            if (command.getNames() == null) {
                return new UnknownCommand();
            }
            for (String name : command.getNames()) {
                if (!message.split(" ")[0].equalsIgnoreCase(name)) continue;
                return command;
            }
        }
        return unknownCommand;
    }
}