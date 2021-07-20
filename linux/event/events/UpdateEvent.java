package linux.event.events;

import linux.event.Event;
import linux.util.Location;

public class UpdateEvent extends Event {
    public Event.State state;
    public float yaw;
    public float pitch;
    public double y;
    private boolean onground;
    private Location location;
    private boolean alwaysSend;
    public boolean pre;
    public boolean ground;
    public double x;
    public double z;
    public boolean onGround;
    public boolean fullUpdate;

    public UpdateEvent() {
        this.state = Event.State.POST;
    }

    public UpdateEvent(double y, float yaw, float pitch, boolean ground) {
        this.state = Event.State.PRE;
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.onground = ground;
        this.ground = ground;
        this.y = y;
        this.yaw = yaw;
        this.pitch = pitch;
        this.fullUpdate = false;
    }

    public Event.State getState() {
        return this.state;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void ground(boolean newGround) {
        this.ground = newGround;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isOnground() {
        return this.onground;
    }

    public boolean shouldAlwaysSend() {
        return this.alwaysSend;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setGround(boolean ground) {
        this.onground = ground;
    }

    public void setAlwaysSend(boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }

    public boolean isPre() {
        return this.pre;
    }

    public boolean isAlwaysSend() {
        return this.alwaysSend;
    }

    public boolean isFullUpdate() {
        return this.fullUpdate;
    }

    public void setRotations(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public float yaw() {
        return this.yaw;
    }

    public float pitch() {
        return this.pitch;
    }

    public boolean ground() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void forceFullUpdate(boolean fullUpdate) {
        this.fullUpdate = fullUpdate;
    }
}