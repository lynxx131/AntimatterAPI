package muramasa.antimatter.block;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.builder.AntimatterBlockModelBuilder;
import muramasa.antimatter.datagen.builder.VariantBlockStateBuilder.VariantBuilder;
import net.minecraft.resources.ResourceLocation;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.ore.CobbleStoneType;
import muramasa.antimatter.ore.StoneType;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.registration.ISharedAntimatterObject;
import muramasa.antimatter.registration.ITextureProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;

public class BlockStoneSlab extends SlabBlock implements ISharedAntimatterObject, ITextureProvider, IModelProvider {
    protected String domain, id, suffix;
    CobbleStoneType type;

    public BlockStoneSlab(CobbleStoneType type, String suffix) {
        super(getProps(type));
        domain = type.getDomain();
        String s = suffix.isEmpty() ? "" : "_";
        id = type.getId() + s + suffix + "_slab";
        this.suffix = suffix;
        this.type = type;
        AntimatterAPI.register(getClass(), this);
    }

    private static Properties getProps(StoneType type) {
        Properties props = Properties.of(type.getBlockMaterial()).sound(type.getSoundType()).strength(type.getHardness(), type.getResistence());
        if (type.doesRequireTool()) {
            props.requiresCorrectToolForDrops();
        }
        return props;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(type.getDomain(), type.getBeginningPath() + type.getId() + "/" + (suffix.isEmpty() ? "stone" : suffix))};
    }

    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        AntimatterBlockModelBuilder top = prov.models().getBuilder(getId() + "_top").parent(prov.existing("minecraft", "block/slab_top")).texture("bottom", getTextures()[0]).texture("top", getTextures()[0]).texture("side", getTextures()[0]);
        AntimatterBlockModelBuilder bottom = prov.models().getBuilder(getId()).parent(prov.existing("minecraft", "block/slab")).texture("bottom", getTextures()[0]).texture("top", getTextures()[0]).texture("side", getTextures()[0]);
        ResourceLocation both = prov.existing(this.getDomain(), "block/" + this.getId().replace("_slab", ""));
        prov.getVariantBuilder(block).forAllStates(s -> {
            if (s.getValue(TYPE) == SlabType.DOUBLE) {
                return new VariantBuilder().modelFile(both);
            }
            return new VariantBuilder().modelFile(s.getValue(TYPE) == SlabType.TOP ? top : bottom);
        });
    }
}
