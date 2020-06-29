package muramasa.antimatter.capability.impl;

import muramasa.antimatter.capability.IInteractHandler;
import muramasa.antimatter.tool.AntimatterToolType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InteractHandler<T extends TileEntity> implements IInteractHandler<T> {

    private T tile;

    public InteractHandler(T tile) {
        this.tile = tile;
    }

    @Override
    public boolean onInteract(@Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull Direction side, @Nullable AntimatterToolType type) {
        return false;
    }

    @Nonnull
    @Override
    public T getTile() {
        if (tile == null) throw new NullPointerException("InteractHandler cannot have a null tile");
        return tile;
    }
}
