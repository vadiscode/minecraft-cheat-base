package linux.module.modules.render;

import linux.event.EventTarget;
import linux.event.events.TickEvent;
import linux.module.Module;
import linux.module.Module.Mod;
import linux.option.Option.Op;

@Mod
public class ESP extends Module {
	@Op
	public static boolean outline;
	
	@EventTarget
	public void onTick(TickEvent event) {
		if (this.outline) {
			setSuffix("Outline");
		}
		else {
			setSuffix("Glow");
		}
	}
}
