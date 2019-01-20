package muramasa.itech.api.gui.slot;

import muramasa.itech.api.capability.implementations.MachineStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class SlotOutput extends SlotItemHandler {

    private MachineStackHandler stackHandler;
    private int index;

    public SlotOutput(MachineStackHandler stackHandler, int index, int xPosition, int yPosition) {
        super(stackHandler, index, xPosition, yPosition);
        this.stackHandler = stackHandler;
        this.index = index;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onSlotChanged() {
        super.onSlotChanged();
//        stackHandler.onInputChanged(index);
    }
}