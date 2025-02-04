package muramasa.antimatter.datagen.loaders;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.pipe.types.FluidPipe;
import muramasa.antimatter.pipe.types.ItemPipe;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static muramasa.antimatter.data.AntimatterDefaultTools.HAMMER;
import static muramasa.antimatter.data.AntimatterMaterialTypes.PLATE;
import static muramasa.antimatter.data.AntimatterDefaultTools.WRENCH;

public class Pipes {
    public static void loadRecipes(Consumer<FinishedRecipe> consumer, AntimatterRecipeProvider provider) {
        final CriterionTriggerInstance in = provider.hasSafeItem(WRENCH.getTag());
        AntimatterAPI.all(ItemPipe.class, i -> {
            Material m = i.getMaterial();
            if (!m.has(PLATE)) return;
            if (i.getSizes().contains(PipeSize.TINY)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_item_tiny", "antimatter_pipes", "has_wrench", in, new ItemStack(i.getBlock(PipeSize.TINY), 12), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PPP", "H W", "PPP");
            }
            if (i.getSizes().contains(PipeSize.SMALL)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_item_small", "antimatter_pipes", "has_wrench", in, new ItemStack(i.getBlock(PipeSize.SMALL), 6), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PWP", "P P", "PHP");
            }
            if (i.getSizes().contains(PipeSize.NORMAL)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_item_normal", "antimatter_pipes", "has_wrench", in, new ItemStack(i.getBlock(PipeSize.NORMAL), 3), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PPP", "W H", "PPP");
            }
            if (i.getSizes().contains(PipeSize.LARGE)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_item_large", "antimatter_pipes", "has_wrench", in, new ItemStack(i.getBlock(PipeSize.LARGE), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PHP", "P P", "PWP");
            }
        });
        AntimatterAPI.all(FluidPipe.class, f -> {
            Material m = f.getMaterial();
            if (!m.has(PLATE)) return;
            if (f.getSizes().contains(PipeSize.TINY)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_fluid_tiny", "antimatter_pipes", "has_wrench", in, new ItemStack(f.getBlock(PipeSize.TINY), 12), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PPP", "H W", "PPP");
            }
            if (f.getSizes().contains(PipeSize.SMALL)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_fluid_small", "antimatter_pipes", "has_wrench", in, new ItemStack(f.getBlock(PipeSize.SMALL), 6), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PWP", "P P", "PHP");
            }
            if (f.getSizes().contains(PipeSize.NORMAL)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_fluid_normal", "antimatter_pipes", "has_wrench", in, new ItemStack(f.getBlock(PipeSize.NORMAL), 3), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PPP", "W H", "PPP");
            }
            if (f.getSizes().contains(PipeSize.LARGE)){
                provider.addStackRecipe(consumer, Ref.ID, m.getId() + "_pipe_fluid_large", "antimatter_pipes", "has_wrench", in, new ItemStack(f.getBlock(PipeSize.LARGE), 1), of('H', HAMMER.getTag(), 'W', WRENCH.getTag(), 'P', PLATE.getMaterialTag(m)), "PHP", "P P", "PWP");
            }
        });
    }
}
