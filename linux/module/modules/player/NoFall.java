package linux.module.modules.player;

import linux.event.EventTarget;
import linux.event.events.TickEvent;
import linux.event.events.UpdateEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.module.modules.player.nofall.Ground;
import linux.module.modules.player.nofall.Packet;
import linux.option.OptionManager;

@Mod
public class NoFall extends Module {
	private Packet packet = new Packet("Packet", true, this);
	private Ground ground = new Ground("Ground", false, this);
	
	@Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.packet);
        OptionManager.getOptionList().add(this.ground);
        this.updateSuffix();
        super.preInitialize();
    }
	
	@EventTarget
    private void onUpdate(UpdateEvent event) {
        this.packet.onUpdate(event);
        this.ground.onUpdate(event);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.packet.getValue()).booleanValue()) {
            this.setSuffix("Packet");
        } else if (((Boolean)this.ground.getValue()).booleanValue()) {
            this.setSuffix("Ground");
        }
    }
}
