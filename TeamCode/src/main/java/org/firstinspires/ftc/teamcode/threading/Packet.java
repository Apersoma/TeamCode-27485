package org.firstinspires.ftc.teamcode.threading;


class Packet {
    private final Identity sender;
    private final Identity recipient;
    private final Signal signal;
    private final Object payload;

    public Packet(Identity sender, Identity recipient, Signal signal) {
        this(sender, recipient, signal, null);
    }

    public Packet(Identity sender, Identity recipient, Signal signal, Object payload) {
        this.sender = sender;
        this.recipient = recipient;
        this.signal = signal;
        this.payload = payload;
    }

    public Identity getSender() { return sender; }
    public Identity getRecipient() { return recipient; }
    public Signal getSignal() { return signal; }
    public Object getPayload() { return payload; }
}
