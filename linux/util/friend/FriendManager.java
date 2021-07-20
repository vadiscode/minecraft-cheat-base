package linux.util.friend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import linux.util.FileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;

public final class FriendManager {
    private static final File FRIEND_DIR = FileUtil.getConfigFile("Friends");
    public static ArrayList<Friend> friendsList = new ArrayList();

    public static void start() {
        FriendManager.load();
        FriendManager.save();
    }

    public static void addFriend(String name, String alias) {
        friendsList.add(new Friend(name, alias));
        FriendManager.save();
    }

    public static String getAliasName(String name) {
        String alias = "";
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) continue;
            alias = friend.alias;
            break;
        }
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            return name;
        }
        return alias;
    }

    public static void removeFriend(String name) {
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(name)) continue;
            friendsList.remove(friend);
            break;
        }
        FriendManager.save();
    }

    public static String replaceText(String text) {
        for (Friend friend : friendsList) {
            if (!text.contains(friend.name)) continue;
            text = friend.alias;
        }
        return text;
    }

    public static boolean isFriend(String name) {
        boolean isFriend = false;
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) continue;
            isFriend = true;
            break;
        }
        if (Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            isFriend = true;
        }
        return isFriend;
    }

    public static void load() {
        friendsList.clear();
        List<String> fileContent = FileUtil.read(FRIEND_DIR);
        for (String line : fileContent) {
            try {
                String[] split = line.split(":");
                String name = split[0];
                String alias = split[1];
                FriendManager.addFriend(name, alias);
            }
            catch (Exception split) {
            	
            }
        }
    }

    public static void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        for (Friend friend : friendsList) {
            String alias = FriendManager.getAliasName(friend.name);
            fileContent.add(String.format("%s:%s", friend.name, alias));
        }
        FileUtil.write(FRIEND_DIR, fileContent, true);
    }
}