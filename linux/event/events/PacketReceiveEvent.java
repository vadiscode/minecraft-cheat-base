package linux.event.events;

import linux.event.Event;
import net.minecraft.network.Packet;

public class PacketReceiveEvent extends Event {
    private Packet packet;

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public PacketReceiveEvent(Packet packet) {
        this.packet = packet;
    }
}