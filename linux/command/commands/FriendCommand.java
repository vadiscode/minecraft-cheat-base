package linux.command.commands;

import linux.command.Command;
import linux.command.Command.Com;
import linux.util.ChatUtil;
import linux.util.friend.FriendManager;

@Com(names={"friend", "f"})
public class FriendCommand extends Command {
    @Override
    public void runCommand(String[] args) {
        if (args.length < 3) {
            ChatUtil.sendMessage(this.getHelp());
            return;
        }
        if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("a")) {
            String alias = args[2];
            if (args.length > 3 && (alias = args[3]).startsWith("\"") && args[args.length - 1].endsWith("\"")) {
                alias = alias.substring(1, alias.length());
                for (int i2 = 4; i2 < args.length; ++i2) {
                    alias = String.valueOf(String.valueOf(alias)) + " " + args[i2].replace("\"", "");
                }
            }
            if (FriendManager.isFriend(args[2]) && args.length < 3) {
            	ChatUtil.sendMessage(String.valueOf(String.valueOf(args[2])) + " is already your friend.");
                return;
            }
            FriendManager.removeFriend(args[2]);
            FriendManager.addFriend(args[2], alias);
            ChatUtil.sendMessage("Added " + args[2] + (args.length > 3 ? " as " + alias : ""));
        } else if (args[1].equalsIgnoreCase("del") || args[1].equalsIgnoreCase("d")) {
            if (FriendManager.isFriend(args[2])) {
                FriendManager.removeFriend(args[2]);
                ChatUtil.sendMessage("Removed friend: " + args[2]);
            } else {
            	ChatUtil.sendMessage(String.valueOf(String.valueOf(args[2])) + " is not your friend.");
            }
        } else {
        	ChatUtil.sendMessage(this.getHelp());
        }
    }

    @Override
    public String getHelp() {
        return "Friend - friend <f>  (add <a> | del <d>) (name) [alias | \"alias w/ spaces\"].";
    }
}