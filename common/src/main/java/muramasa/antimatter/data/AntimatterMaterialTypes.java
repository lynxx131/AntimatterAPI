package muramasa.antimatter.data;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.AntimatterConfig;
import muramasa.antimatter.Ref;
import muramasa.antimatter.block.BlockStorage;
import muramasa.antimatter.block.BlockSurfaceRock;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.cover.CoverPlate;
import muramasa.antimatter.fluid.AntimatterFluid;
import muramasa.antimatter.fluid.AntimatterMaterialFluid;
import muramasa.antimatter.item.CoverMaterialItem;
import muramasa.antimatter.material.*;
import muramasa.antimatter.ore.BlockOre;
import muramasa.antimatter.ore.BlockOreStone;
import muramasa.antimatter.ore.StoneType;
import muramasa.antimatter.registration.Side;
import muramasa.antimatter.structure.BlockStateElement;
import muramasa.antimatter.structure.StructureBuilder;
import muramasa.antimatter.structure.StructureElement;
import muramasa.antimatter.util.Utils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import tesseract.FluidPlatformUtils;

public class AntimatterMaterialTypes {
    //Item Types
    public static MaterialTypeItem<?> DUST = new MaterialTypeItem<>("dust", 2, true, Ref.U);
    public static MaterialTypeItem<?> DUST_SMALL = new MaterialTypeItem<>("dust_small", 2, true, Ref.U4);
    public static MaterialTypeItem<?> DUST_TINY = new MaterialTypeItem<>("dust_tiny", 2, true, Ref.U9);
    public static MaterialTypeItem<?> DUST_IMPURE = new MaterialTypeItem<>("dust_impure", 2, true, Ref.U);
    public static MaterialTypeItem<?> DUST_PURE = new MaterialTypeItem<>("dust_pure", 2, true, Ref.U);
    public static MaterialTypeItem<MaterialTypeBlock.IOreGetter> ROCK = new MaterialTypeItem<>("rock", 2, false, Ref.U9, (domain, type, mat) -> {
        AntimatterAPI.all(StoneType.class, s -> AntimatterAPI.register(BlockSurfaceRock.class, new BlockSurfaceRock(domain, mat, s)));
        new MaterialItem(domain, type, mat);
    });
    public static MaterialTypeItem<?> CRUSHED = new MaterialTypeItem<>("crushed", 2, true, Ref.U);
    public static MaterialTypeItem<?> CRUSHED_CENTRIFUGED = new MaterialTypeItem<>("crushed_centrifuged", 2, true, Ref.U);
    public static MaterialTypeItem<?> CRUSHED_PURIFIED = new MaterialTypeItem<>("crushed_purified", 2, true, Ref.U);
    public static MaterialTypeItem<?> RAW_ORE = new MaterialTypeItem<>("raw_ore", 2, true, -1);
    public static MaterialTypeItem<?> INGOT = new MaterialTypeItem<>("ingot", 2, true, Ref.U);
    public static MaterialTypeItem<?> INGOT_HOT = new MaterialTypeItem<>("ingot_hot", 2, true, Ref.U);
    public static MaterialTypeItem<?> NUGGET = new MaterialTypeItem<>("nugget", 2, true, Ref.U9);
    public static MaterialTypeItem<?> GEM = new MaterialTypeItem<>("gem", 2, true, Ref.U);
    public static MaterialTypeItem<?> GEM_BRITTLE = new MaterialTypeItem<>("gem_brittle", 2, true, Ref.U);
    public static MaterialTypeItem<?> GEM_POLISHED = new MaterialTypeItem<>("gem_polished", 2, true, Ref.U);
    public static MaterialTypeItem<?> LENS = new MaterialTypeItem<>("lens", 2, true, Ref.U * 3 / 4);
    public static MaterialTypeItem<?> PLATE = new MaterialTypeItem<>("plate", 2, true, Ref.U, (a, b, c) -> CoverFactory.builder((u, v, t, w) -> new CoverPlate(u, v, t, w, b, c)).item((u, v) -> new CoverMaterialItem(u.getDomain(), b, u, c)).build(Ref.ID, "plate_" + c.getId()));
    public static MaterialTypeItem<?> PLATE_DENSE = new MaterialTypeItem<>("plate_dense", 2, true, Ref.U * 9);
    public static MaterialTypeItem<?> PLATE_TINY = new MaterialTypeItem<>("plate_tiny", 2, true, Ref.U / 8);
    public static MaterialTypeItem<?> ROD = new MaterialTypeItem<>("rod", 2, true, Ref.U2);
    public static MaterialTypeItem<?> ROD_LONG = new MaterialTypeItem<>("rod_long", 2, true, Ref.U);
    public static MaterialTypeItem<?> RING = new MaterialTypeItem<>("ring", 2, true, Ref.U4);
    public static MaterialTypeItem<?> FOIL = new MaterialTypeItem<>("foil", 2, true, Ref.U);
    public static MaterialTypeItem<?> BOLT = new MaterialTypeItem<>("bolt", 2, true, Ref.U8);
    public static MaterialTypeItem<?> SCREW = new MaterialTypeItem<>("screw", 2, true, Ref.U9);
    public static MaterialTypeItem<?> GEAR = new MaterialTypeItem<>("gear", 2, true, Ref.U * 4);
    public static MaterialTypeItem<?> GEAR_SMALL = new MaterialTypeItem<>("gear_small", 2, true, Ref.U);
    public static MaterialTypeItem<?> WIRE_FINE = new MaterialTypeItem<>("wire_fine", 2, true, Ref.U8);
    public static MaterialTypeItem<?> SPRING = new MaterialTypeItem<>("spring", 2, true, Ref.U);
    public static MaterialTypeItem<?> ROTOR = new MaterialTypeItem<>("rotor", 2, true, Ref.U * 4 + Ref.U4);
    public static MaterialTypeItem<?> DRILLBIT = new MaterialTypeItem<>("drill_bit", 2, true, Ref.U * 4);
    public static MaterialTypeItem<?> CHAINSAWBIT = new MaterialTypeItem<>("chainsaw_bit", 2, true, Ref.U * 2);
    public static MaterialTypeItem<?> WRENCHBIT = new MaterialTypeItem<>("wrench_bit", 2, true, Ref.U * 4);
    public static MaterialTypeItem<?> BUZZSAW_BLADE = new MaterialTypeItem<>("buzzsaw_blade", 2, true, Ref.U * 4);
    //Block Types
    public static MaterialTypeBlock<MaterialTypeBlock.IOreGetter> ORE = new MaterialTypeBlock<>("ore", 1, true, -1, (domain, type, mat) -> AntimatterAPI.all(StoneType.class, s -> new BlockOre(domain, mat, s, type)));
    public static MaterialTypeBlock<MaterialTypeBlock.IOreGetter> ORE_SMALL = new MaterialTypeBlock<>("ore_small", 1, false, -1, (domain, type, mat) -> AntimatterAPI.all(StoneType.class, s -> new BlockOre(domain, mat, s, type)));
    public static MaterialTypeBlock<MaterialTypeBlock.IBlockGetter> ORE_STONE = new MaterialTypeBlock<>("ore_stone", 1, true, -1,(domain, type, mat) -> new BlockOreStone(domain, mat));
    public static MaterialTypeBlock<MaterialTypeBlock.IBlockGetter> BLOCK = new MaterialTypeBlock<>("block", 1, false, -1, BlockStorage::new);
    public static MaterialTypeBlock<MaterialTypeBlock.IBlockGetter> RAW_ORE_BLOCK = new MaterialTypeBlock<>("raw_ore_block", 2, false, -1, BlockStorage::new);
    public static MaterialTypeBlock<MaterialTypeBlock.IBlockGetter> FRAME = new MaterialTypeBlock<>("frame", 1, true, -1, BlockStorage::new);
    //Fluid Types
    public static MaterialTypeFluid<MaterialTypeFluid.IFluidGetter> LIQUID = new MaterialTypeFluid<>("liquid", 1, true, -1);
    public static MaterialTypeFluid<MaterialTypeFluid.IFluidGetter> GAS = new MaterialTypeFluid<>("gas", 1, true, -1);
    public static MaterialTypeFluid<MaterialTypeFluid.IFluidGetter> PLASMA = new MaterialTypeFluid<>("plasma", 1, true, -1);

    static {
        AntimatterMaterialTypes.ROCK.set((m, s) -> {
            if (m == null || s == null || !AntimatterMaterialTypes.ROCK.allowGen(m)) return MaterialTypeBlock.getEmptyBlockAndLog(AntimatterMaterialTypes.ROCK, m, s);
            BlockSurfaceRock rock = AntimatterAPI.get(BlockSurfaceRock.class, "surface_rock_" + m.getId() + "_" + s.getId());
            return new MaterialTypeBlock.Container(rock != null ? rock.defaultBlockState() : Blocks.AIR.defaultBlockState());
        });
        AntimatterMaterialTypes.ORE.set((m, s) -> {
            if (m != null) {
                Item item = AntimatterAPI.getReplacement(AntimatterMaterialTypes.ORE, m);
                if (item instanceof BlockItem) {
                    return new MaterialTypeBlock.Container(((BlockItem) item).getBlock().defaultBlockState());
                }
            }
            if (m == null || s == null || !AntimatterMaterialTypes.ORE.allowGen(m)) return MaterialTypeBlock.getEmptyBlockAndLog(AntimatterMaterialTypes.ORE, m, s);
            BlockOre block = AntimatterAPI.get(BlockOre.class, AntimatterMaterialTypes.ORE.getId() + "_" + m.getId() + "_" + Utils.getConventionalStoneType(s));
            return new MaterialTypeBlock.Container(block != null ? block.defaultBlockState() : Blocks.AIR.defaultBlockState());
        }).blockType();
        AntimatterMaterialTypes.ORE_SMALL.set((m, s) -> {
            if (m == null || s == null || !AntimatterMaterialTypes.ORE_SMALL.allowGen(m))
                return MaterialTypeBlock.getEmptyBlockAndLog(AntimatterMaterialTypes.ORE_SMALL, m, s);
            BlockOre block = AntimatterAPI.get(BlockOre.class, AntimatterMaterialTypes.ORE_SMALL.getId() + "_" + m.getId() + "_" + Utils.getConventionalStoneType(s));
            return new MaterialTypeBlock.Container(block != null ? block.defaultBlockState() : Blocks.AIR.defaultBlockState());
        }).blockType();
        AntimatterMaterialTypes.ORE_STONE.set(m -> {
            if (m == null || !AntimatterMaterialTypes.ORE_STONE.allowGen(m)) return MaterialTypeBlock.getEmptyBlockAndLog(AntimatterMaterialTypes.ORE_STONE, m);
            BlockOreStone block = AntimatterAPI.get(BlockOreStone.class, AntimatterMaterialTypes.ORE_STONE.getId() + "_" + m.getId());
            return new MaterialTypeBlock.Container(block != null ? block.defaultBlockState() : Blocks.AIR.defaultBlockState());
        }).blockType();
        AntimatterMaterialTypes.BLOCK.set(m -> {
            if (m != null) {
                Item item = AntimatterAPI.getReplacement(AntimatterMaterialTypes.BLOCK, m);
                if (item instanceof BlockItem) {
                    return new MaterialTypeBlock.Container(((BlockItem) item).getBlock().defaultBlockState());
                }
            }
            if (m == null || !AntimatterMaterialTypes.BLOCK.allowGen(m)) return MaterialTypeBlock.getEmptyBlockAndLog(AntimatterMaterialTypes.BLOCK, m);
            BlockStorage block = AntimatterAPI.get(BlockStorage.class, AntimatterMaterialTypes.BLOCK.getId() + "_" + m.getId());
            return new MaterialTypeBlock.Container(block != null ? block.defaultBlockState() : Blocks.AIR.defaultBlockState());
        }).blockType();
        AntimatterMaterialTypes.RAW_ORE_BLOCK.set(m -> {
            if (m != null) {
                Item item = AntimatterAPI.getReplacement(AntimatterMaterialTypes.RAW_ORE_BLOCK, m);
                if (item instanceof BlockItem) {
                    return new MaterialTypeBlock.Container(((BlockItem) item).getBlock().defaultBlockState());
                }
            }
            if (m == null || !AntimatterMaterialTypes.RAW_ORE_BLOCK.allowGen(m)) return MaterialTypeBlock.getEmptyBlockAndLog(AntimatterMaterialTypes.RAW_ORE_BLOCK, m);
            BlockStorage block = AntimatterAPI.get(BlockStorage.class, AntimatterMaterialTypes.RAW_ORE_BLOCK.getId() + "_" + m.getId());
            return new MaterialTypeBlock.Container(block != null ? block.defaultBlockState() : Blocks.AIR.defaultBlockState());
        }).blockType();
        AntimatterMaterialTypes.FRAME.set(m -> {
            if (m == null || !AntimatterMaterialTypes.FRAME.allowGen(m)) return MaterialTypeBlock.getEmptyBlockAndLog(AntimatterMaterialTypes.FRAME, m);
            BlockStorage block = AntimatterAPI.get(BlockStorage.class, AntimatterMaterialTypes.FRAME.getId() + "_" + m.getId());
            return new MaterialTypeBlock.Container(block != null ? block.defaultBlockState() : Blocks.AIR.defaultBlockState());
        }).blockType();

        AntimatterMaterialTypes.LIQUID.set((m, i) -> {
            if (m == null || !AntimatterMaterialTypes.LIQUID.allowGen(m)) return MaterialTypeFluid.getEmptyFluidAndLog(AntimatterMaterialTypes.LIQUID, m);
            if (m.getId().equals("water")) return FluidPlatformUtils.createFluidStack(Fluids.WATER, i);
            else if (m.getId().equals("lava")) return FluidPlatformUtils.createFluidStack(Fluids.LAVA, i);
            AntimatterFluid fluid = AntimatterAPI.get(AntimatterFluid.class, AntimatterMaterialTypes.LIQUID.getId() + "_" + m.getId());
            if (fluid == null) throw new IllegalStateException("Tried to get null fluid");
            return FluidPlatformUtils.createFluidStack(fluid.getFluid(), i);
        });
        AntimatterMaterialTypes.GAS.set((m, i) -> {
            if (m == null || !AntimatterMaterialTypes.GAS.allowGen(m)) return MaterialTypeFluid.getEmptyFluidAndLog(AntimatterMaterialTypes.GAS, m);
            AntimatterFluid fluid = AntimatterAPI.get(AntimatterFluid.class, AntimatterMaterialTypes.GAS.getId() + "_" + m.getId());
            if (fluid == null) throw new IllegalStateException("Tried to get null fluid");
            return FluidPlatformUtils.createFluidStack(fluid.getFluid(), i);
        });
        AntimatterMaterialTypes.PLASMA.set((m, i) -> {
            if (m == null || !AntimatterMaterialTypes.PLASMA.allowGen(m)) return MaterialTypeFluid.getEmptyFluidAndLog(AntimatterMaterialTypes.PLASMA, m);
            AntimatterFluid fluid = AntimatterAPI.get(AntimatterFluid.class, AntimatterMaterialTypes.PLASMA.getId() + "_" + m.getId());
            if (fluid == null) throw new IllegalStateException("Tried to get null fluid");
            return FluidPlatformUtils.createFluidStack(fluid.getFluid(), i);
        });
    }

    public static void init() {

        AntimatterMaterialTypes.NUGGET.replacement(AntimatterMaterials.Iron, () -> Items.IRON_NUGGET);
        AntimatterMaterialTypes.NUGGET.replacement(AntimatterMaterials.Gold, () -> Items.GOLD_NUGGET);
        AntimatterMaterialTypes.INGOT.replacement(AntimatterMaterials.Iron, () -> Items.IRON_INGOT);
        AntimatterMaterialTypes.INGOT.replacement(AntimatterMaterials.Gold, () -> Items.GOLD_INGOT);
        AntimatterMaterialTypes.INGOT.replacement(AntimatterMaterials.Netherite, () -> Items.NETHERITE_INGOT);
        AntimatterMaterialTypes.INGOT.replacement(AntimatterMaterials.Copper, () -> Items.COPPER_INGOT);


        AntimatterMaterialTypes.DUST.replacement(AntimatterMaterials.Redstone, () -> Items.REDSTONE);
        AntimatterMaterialTypes.DUST.replacement(AntimatterMaterials.Glowstone, () -> Items.GLOWSTONE_DUST);
        AntimatterMaterialTypes.DUST.replacement(AntimatterMaterials.Blaze, () -> Items.BLAZE_POWDER);
        AntimatterMaterialTypes.DUST.replacement(AntimatterMaterials.Sugar, () -> Items.SUGAR);
        AntimatterMaterialTypes.RAW_ORE.replacement(AntimatterMaterials.Iron, () -> Items.RAW_IRON);
        AntimatterMaterialTypes.RAW_ORE.replacement(AntimatterMaterials.Copper, () -> Items.RAW_COPPER);
        AntimatterMaterialTypes.RAW_ORE.replacement(AntimatterMaterials.Gold, () -> Items.RAW_GOLD);
        AntimatterMaterialTypes.GEM.replacement(AntimatterMaterials.Flint, () -> Items.FLINT);
        AntimatterMaterialTypes.GEM.replacement(AntimatterMaterials.Diamond, () -> Items.DIAMOND);
        AntimatterMaterialTypes.GEM.replacement(AntimatterMaterials.Emerald, () -> Items.EMERALD);
        AntimatterMaterialTypes.GEM.replacement(AntimatterMaterials.Lapis, () -> Items.LAPIS_LAZULI);
        AntimatterMaterialTypes.GEM.replacement(AntimatterMaterials.Coal, () -> Items.COAL);
        AntimatterMaterialTypes.GEM.replacement(AntimatterMaterials.Charcoal, () -> Items.CHARCOAL);
        AntimatterMaterialTypes.GEM.replacement(AntimatterMaterials.EnderEye, () -> Items.ENDER_EYE);
        AntimatterMaterialTypes.GEM.replacement(AntimatterMaterials.EnderPearl, () -> Items.ENDER_PEARL);

        AntimatterMaterialTypes.ROD.replacement(AntimatterMaterials.Blaze, () -> Items.BLAZE_ROD);
        AntimatterMaterialTypes.ROD.replacement(AntimatterMaterials.Bone, () -> Items.BONE);
        AntimatterMaterialTypes.ROD.replacement(AntimatterMaterials.Wood, () -> Items.STICK);

        AntimatterMaterialTypes.BLOCK.replacement(AntimatterMaterials.Iron, () -> Items.IRON_BLOCK);
        AntimatterMaterialTypes.BLOCK.replacement(AntimatterMaterials.Copper, () -> Items.COPPER_BLOCK);
        AntimatterMaterialTypes.BLOCK.replacement(AntimatterMaterials.Gold, () -> Items.GOLD_BLOCK);
        AntimatterMaterialTypes.BLOCK.replacement(AntimatterMaterials.Diamond, () -> Items.DIAMOND_BLOCK);
        AntimatterMaterialTypes.BLOCK.replacement(AntimatterMaterials.Emerald, () -> Items.EMERALD_BLOCK);
        AntimatterMaterialTypes.BLOCK.replacement(AntimatterMaterials.Lapis, () -> Items.LAPIS_BLOCK);
        AntimatterMaterialTypes.BLOCK.replacement(AntimatterMaterials.Netherite, () -> Items.NETHERITE_BLOCK);
        AntimatterMaterialTypes.RAW_ORE_BLOCK.replacement(AntimatterMaterials.Iron, () -> Items.RAW_IRON_BLOCK);
        AntimatterMaterialTypes.RAW_ORE_BLOCK.replacement(AntimatterMaterials.Copper, () -> Items.RAW_COPPER_BLOCK);
        AntimatterMaterialTypes.RAW_ORE_BLOCK.replacement(AntimatterMaterials.Gold, () -> Items.RAW_GOLD_BLOCK);

        AntimatterMaterialTypes.ROTOR.dependents(AntimatterMaterialTypes.PLATE, AntimatterMaterialTypes.SCREW, AntimatterMaterialTypes.RING);
        AntimatterMaterialTypes.SCREW.dependents(AntimatterMaterialTypes.BOLT);
        AntimatterMaterialTypes.BOLT.dependents(AntimatterMaterialTypes.ROD);
        AntimatterMaterialTypes.RING.dependents(AntimatterMaterialTypes.ROD);
        AntimatterMaterialTypes.CRUSHED.dependents(AntimatterMaterialTypes.CRUSHED_PURIFIED, AntimatterMaterialTypes.CRUSHED_CENTRIFUGED, AntimatterMaterialTypes.DUST_IMPURE);
        AntimatterMaterialTypes.DUST_PURE.dependents(AntimatterMaterialTypes.DUST);
        AntimatterMaterialTypes.DUST_IMPURE.dependents(AntimatterMaterialTypes.DUST_PURE);
        AntimatterMaterialTypes.DUST.dependents(AntimatterMaterialTypes.DUST_SMALL, AntimatterMaterialTypes.DUST_TINY);
        AntimatterMaterialTypes.GEAR_SMALL.dependents(AntimatterMaterialTypes.PLATE);
        AntimatterMaterialTypes.GEAR.dependents(AntimatterMaterialTypes.PLATE, AntimatterMaterialTypes.ROD);

        AntimatterMaterialTypes.DUST_TINY.setHidden();
        AntimatterMaterialTypes.DUST_SMALL.setHidden();
        AntimatterMaterialTypes.DRILLBIT.setHidden().unSplitName();
        AntimatterMaterialTypes.CHAINSAWBIT.setHidden().unSplitName();
        AntimatterMaterialTypes.WRENCHBIT.setHidden().unSplitName();
        AntimatterMaterialTypes.BUZZSAW_BLADE.setHidden().unSplitName();
        AntimatterMaterialTypes.RAW_ORE.unSplitName();
        AntimatterMaterialTypes.RAW_ORE_BLOCK.unSplitName();
    }

    public static void postInit() {
        AntimatterMaterialTypes.LIQUID.all().stream().filter(l -> !l.getId().equals("water") && !l.getId().equals("lava")).forEach(m -> AntimatterAPI.register(AntimatterFluid.class, new AntimatterMaterialFluid(Ref.SHARED_ID, m, AntimatterMaterialTypes.LIQUID)));
        AntimatterMaterialTypes.GAS.all().forEach(m -> AntimatterAPI.register(AntimatterFluid.class, new AntimatterMaterialFluid(Ref.SHARED_ID, m, AntimatterMaterialTypes.GAS)));
        AntimatterMaterialTypes.PLASMA.all().forEach(m -> AntimatterAPI.register(AntimatterFluid.class, new AntimatterMaterialFluid(Ref.SHARED_ID, m, AntimatterMaterialTypes.PLASMA)));
        AntimatterAPI.all(Material.class, Material::setChemicalFormula);
        if (AntimatterConfig.WORLD.ORE_VEIN_SMALL_ORE_MARKERS) AntimatterMaterialTypes.ORE.all().forEach(m -> m.flags(AntimatterMaterialTypes.ORE_SMALL));
    }
}
