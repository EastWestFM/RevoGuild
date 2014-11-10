package net.karolek.revoguild.packetlistener;

import lombok.Getter;
import net.karolek.revoguild.packetlistener.events.PacketReceiveEvent;
import net.karolek.revoguild.packetlistener.events.PacketSendEvent;
import net.karolek.revoguild.utils.Reflection;
import net.karolek.revoguild.utils.Reflection.FieldAccessor;
import net.karolek.revoguild.utils.Reflection.MethodInvoker;
import net.minecraft.util.io.netty.channel.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PacketManager {

    @Getter
    private static final HashMap<UUID, Channel> channels = new HashMap<UUID, Channel>();
    private static FieldAccessor<Channel> clientChannel;
    private static FieldAccessor<Object> playerConnection;
    private static FieldAccessor<Object> networkManager;
    private static MethodInvoker handleMethod;
    static {
        try {
            clientChannel = Reflection.getField(Reflection.getMinecraftClass("NetworkManager"), Channel.class, 0);
            playerConnection = Reflection.getSimpleField(Reflection.getMinecraftClass("EntityPlayer"), "playerConnection");
            networkManager = Reflection.getSimpleField(Reflection.getMinecraftClass("PlayerConnection"), "networkManager");
            handleMethod = Reflection.getMethod(Reflection.getCraftBukkitClass("entity.CraftEntity"), "getHandle");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Channel getChannel(Player p) {
        Object eP = handleMethod.invoke(p);
        return clientChannel.get(networkManager.get(playerConnection.get(eP)));
    }

    public static void registerPlayer(final Player p) {

        Channel c = getChannel(p);

        ChannelHandler handler = new ChannelDuplexHandler() {

            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                PacketSendEvent event = new PacketSendEvent(msg, p);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled() || event.getPacket() == null)
                    return;
                super.write(ctx, event.getPacket(), promise);
            }

            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                PacketReceiveEvent event = new PacketReceiveEvent(msg, p);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled() || event.getPacket() == null)
                    return;
                super.channelRead(ctx, event.getPacket());
            }

        };

        c.pipeline().addBefore("packet_handler", "RevoGuild", handler);
        channels.put(p.getUniqueId(), c);
    }

    public static void unregisterPlayer(Player p) {
        channels.remove(p.getUniqueId()).pipeline().remove("RevoGuild");
    }

}
