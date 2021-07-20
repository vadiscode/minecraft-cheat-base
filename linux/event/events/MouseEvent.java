package linux.event.events;

import linux.event.Event;

public class MouseEvent extends Event {
    private int key;

    public MouseEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}