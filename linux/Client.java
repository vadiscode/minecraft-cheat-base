package linux;

import linux.command.CommandManager;
import linux.gui.clickgui.ClickUI;
import linux.module.ModuleManager;
import linux.option.OptionManager;
import linux.util.FontUtil;
import linux.util.friend.FriendManager;
import linux.util.render.TTFFontRenderer;

public class Client {
	//Client Data
	public static final String clientName = "Linux";
	public static double clientVersion = 1.0;
	public static String clientAuthor = "vadiscode";
	
	//Other Client Functions
	public static TTFFontRenderer clientFont = new FontUtil().getFont("Verdana");
	public static ClickUI clickGUI;
	
	//Client Start Thread
	public static void start() {
		ModuleManager.start();
        CommandManager.start();
        OptionManager.start();
        FriendManager.start();
        clickGUI = new ClickUI();
	}
}
