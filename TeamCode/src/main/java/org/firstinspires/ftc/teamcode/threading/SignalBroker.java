package org.firstinspires.ftc.teamcode.threading;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class SignalBroker {
    private final Map<Integer, BlockingQueue<Packet>> inboxes = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public int generateId() {
        return idGenerator.getAndIncrement();
    }

    public void register(Identity user) {
        inboxes.putIfAbsent(user.getId(), new LinkedBlockingQueue<>());
    }

    public void unregister(Identity user) {
        inboxes.remove(user.getId());
    }

    public void send(Packet packet) {
        Identity recipient = packet.getRecipient();

        if (recipient.getId() == Identity.ANY.getId()) {
            inboxes.values().forEach(queue -> queue.offer(packet));
        } else {
            BlockingQueue<Packet> queue = inboxes.get(recipient.getId());
            if (queue != null) {
                queue.offer(packet);
            }
        }
    }

    public Optional<Packet> receive(Identity user, long timeoutMs) {
        try {
            BlockingQueue<Packet> queue = inboxes.get(user.getId());
            if (queue == null) return Optional.empty();
            return Optional.ofNullable(queue.poll(timeoutMs, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }
}