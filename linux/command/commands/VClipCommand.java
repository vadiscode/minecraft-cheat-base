package linux.command.commands;

import linux.command.Command;
import linux.command.Command.Com;
import linux.util.ChatUtil;
import linux.util.MinecraftUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

@Com(names={"vc", "vclip"})
public class VClipCommand extends Command implements MinecraftUtil {
    @Override
    public void runCommand(String[] args) {
        double posMod = Double.parseDouble(args[1]);
        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + posMod, mc.thePlayer.posZ);
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        ChatUtil.sendMessage("Teleported " + posMod + " blocks vertically.");
    }

    @Override
    public String getHelp() {
        return "VClip - Vertical teleport.";
    }
}