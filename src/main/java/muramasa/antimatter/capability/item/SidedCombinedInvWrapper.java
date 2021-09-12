package muramasa.antimatter.capability.item;

import muramasa.antimatter.capability.CoverHandler;
import muramasa.antimatter.capability.machine.MachineCoverHandler;
import muramasa.antimatter.capability.machine.MachineItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;

public class SidedCombinedInvWrapper extends CombinedInvWrapper {
    Direction side;
    CoverHandler<?> coverHandler;
    public SidedCombinedInvWrapper(Direction side, CoverHandler<?> coverHandler, IItemHandlerModifiable... itemHandler) {
        super(itemHandler);
        this.side = side;
        this.coverHandler = coverHandler;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (coverHandler != null && coverHandler.get(side).getCover().blocksInput(coverHandler.get(side), CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (coverHandler != null && coverHandler.get(side).getCover().blocksOutput(coverHandler.get(side), CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) return ItemStack.EMPTY;
        return super.extractItem(slot, amount, simulate);
    }
}
