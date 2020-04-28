package muramasa.antimatter.cover.pipe;

import muramasa.antimatter.cover.CoverTransition;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import tesseract.TesseractAPI;
import tesseract.api.electric.IElectricNode;
import tesseract.graph.ITickingController;
import tesseract.util.Dir;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CoverEnergy extends CoverTransition implements IElectricNode {

    @Override
    public String getId() {
        return "energy_transition";
    }

    @Override
    public void onPlace(TileEntity tile, Direction side) {
        //World world = tile.getWorld();
        //if (world != null)
        //    TesseractAPI.registerElectricNode(world.getDimension().getType().getId(), tile.getPos().toLong(), this);
    }

    @Override
    public void onRemove(TileEntity tile, Direction side) {
        // TODO: check neighbors
    }

    @Override
    public void onUpdate(TileEntity tile, Direction side) {
        // TODO: update controller
    }

    @Override
    public long insert(long maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public long extract(long maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public long getEnergy() {
        return 0;
    }

    @Override
    public long getCapacity() {
        return 0;
    }

    @Override
    public int getOutputAmperage() {
        return 0;
    }

    @Override
    public int getOutputVoltage() {
        return 0;
    }

    @Override
    public int getInputAmperage() {
        return 0;
    }

    @Override
    public int getInputVoltage() {
        return 0;
    }

    @Override
    public boolean canOutput() {
        return false;
    }

    @Override
    public boolean canInput() {
        return false;
    }

    @Override
    public boolean canOutput(@Nonnull Dir direction) {
        return false;
    }

    @Override
    public boolean connects(@Nonnull Dir direction) {
        return false;
    }

    @Override
    public void reset(@Nullable ITickingController oldController, @Nullable ITickingController newController) {

    }
}
