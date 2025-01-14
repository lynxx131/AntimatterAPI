package muramasa.antimatter.network.packets;

import muramasa.antimatter.Antimatter;
import muramasa.antimatter.capability.IGuiHandler;
import muramasa.antimatter.gui.container.IAntimatterContainer;
import muramasa.antimatter.gui.event.IGuiEvent;
import muramasa.antimatter.network.AntimatterNetwork;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TileGuiEventPacket extends AbstractGuiEventPacket {

    public TileGuiEventPacket(IGuiEvent event, BlockPos pos) {
        super(event, pos, AntimatterNetwork.TILE_GUI_PACKET_ID);
    }

    public static void encodeStatic(TileGuiEventPacket msg, FriendlyByteBuf buf) {
        msg.event.getFactory().write(msg.event, buf);
        buf.writeBlockPos(msg.pos);
    }

    public static TileGuiEventPacket decode(FriendlyByteBuf buf) {
        return new TileGuiEventPacket(IGuiEvent.IGuiEventFactory.read(buf), buf.readBlockPos());
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        encodeStatic(this, buf);
    }

    @Override
    public void handleClient(ServerPlayer sender) {
        handle(this, sender);
    }

    public static void handle(final TileGuiEventPacket msg, ServerPlayer sender){
        if (sender != null) {
            BlockEntity tile = Utils.getTile(sender.getLevel(), msg.pos);
            if (tile instanceof IGuiHandler) {
                if (msg.event.forward()) {
                    ((IGuiHandler) tile).onGuiEvent(msg.event, sender);
                } else {
                    msg.event.handle(sender, ((IAntimatterContainer) sender.containerMenu).source());
                }
            }
        }
    }
}
