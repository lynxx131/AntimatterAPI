package muramasa.antimatter.recipe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import muramasa.antimatter.Ref;
import muramasa.antimatter.recipe.ingredient.FluidIngredient;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.antimatter.recipe.map.RecipeMap;
import muramasa.antimatter.recipe.serializer.AntimatterRecipeSerializer;
import muramasa.antimatter.tile.TileEntityMachine;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Recipe implements IRecipe {
    private final ItemStack[] itemsOutput;
    @Nonnull
    private final List<Ingredient> itemsInput;
    @Nonnull
    private final List<FluidIngredient> fluidsInput;
    private final FluidStack[] fluidsOutput;
    private final int duration;
    private final int special;
    private final long power;
    private final int amps;
    private double[] chances;
    private boolean hidden;
    private Set<RecipeTag> tags = new ObjectOpenHashSet<>();
    private Map<ItemStack, Double> itemsWithChances = null;
    public ResourceLocation id;
    public String mapId;
    //Used for recipe validators, e.g. cleanroom.
    public final List<IRecipeValidator> validators = Collections.emptyList();

    private boolean valid;

    public static void init() {
        
    }

    public static final RecipeType<Recipe> RECIPE_TYPE = RecipeType.register("antimatter_machine");

    public Recipe(@Nonnull List<Ingredient> stacksInput, ItemStack[] stacksOutput, @Nonnull List<FluidIngredient> fluidsInput, FluidStack[] fluidsOutput, int duration, long power, int special, int amps) {
        this.itemsInput = ImmutableList.copyOf(stacksInput);
        this.itemsOutput = stacksOutput;
        this.duration = duration;
        this.power = power;
        this.special = special;
        this.fluidsInput = ImmutableList.copyOf(fluidsInput);
        this.amps = amps;
        this.fluidsOutput = fluidsOutput;
        this.valid = true;
    }

    //After data reload this is false.
    public boolean isValid() {
        return valid;
    }

    public void invalidate() {
        if (this.id != null)
            this.valid = false;
    }

    public int getAmps() {
        return amps;
    }

    public void addChances(double[] chances) {
        this.chances = chances;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void addTags(Set<RecipeTag> tags) {
        this.tags = tags;
    }

    public boolean hasInputItems() {
        return itemsInput.size() > 0;
    }

    public boolean hasOutputItems() {
        return itemsOutput != null && itemsOutput.length > 0;
    }

    public boolean hasInputFluids() {
        return fluidsInput.size() > 0;
    }

    public boolean hasOutputFluids() {
        return fluidsOutput != null && fluidsOutput.length > 0;
    }

    public boolean hasChances() {
        //TODO change this if we add input chances?
        return chances != null && chances.length == itemsOutput.length;
    }

    public void setIds(ResourceLocation id, String map) {
        this.id = id;
        this.mapId = map;
    }

    @Override
    public void setId(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public void sortInputItems() {
        this.itemsInput.sort((a, b) -> {
            boolean a1 = RecipeMap.isIngredientSpecial(a);
            boolean a2 = RecipeMap.isIngredientSpecial(b);
            if (a1 == a2) return 0;
            if (a1) return 1;
            return -1;
        });
    }

    @Nonnull
    public List<Ingredient> getInputItems() {
        return hasInputItems() ? itemsInput : Collections.emptyList();
    }

    @Nonnull
    public List<RecipeIngredient> getCastedInputs() {
        return hasInputItems() ? itemsInput.stream().filter(t -> t instanceof RecipeIngredient).map(t -> (RecipeIngredient)t).collect(Collectors.toList()) : Collections.emptyList();
    }


    @Nullable
    public ItemStack[] getOutputItems() {
        return getOutputItems(true);
    }

    public ItemStack[] getOutputItems(boolean chance) {
        if (hasOutputItems()) {
            ItemStack[] outputs = itemsOutput.clone();
            if (chances != null) {
                List<ItemStack> evaluated = new ObjectArrayList<>();
                for (int i = 0; i < outputs.length; i++) {
                    if (!chance || Ref.RNG.nextDouble() < chances[i]) {
                        evaluated.add(outputs[i].copy());
                    }
                }
                outputs = evaluated.toArray(new ItemStack[0]);
            }
            return outputs;
        }
        return null;
    }

    /**
     * Returns a list of items not bound by chances.
     *
     * @return list of items.
     */
    public ItemStack[] getFlatOutputItems() {
        if (hasOutputItems()) {
            ItemStack[] outputs = itemsOutput.clone();
            if (chances != null) {
                List<ItemStack> evaluated = new ObjectArrayList<>();
                for (int i = 0; i < outputs.length; i++) {
                    if (chances[i] < 1.0) continue;
                    evaluated.add(outputs[i]);
                }
                outputs = evaluated.toArray(new ItemStack[0]);
            }
            return outputs;
        }
        return null;
    }

    //Note: does call get().
    public boolean hasSpecialIngredients() {
        for (Ingredient ingredient : itemsInput) {
            if (RecipeMap.isIngredientSpecial(ingredient)) {
                return true;
            }
        }
        return false;
    }

    @Nonnull
    public List<FluidIngredient> getInputFluids() {
        return fluidsInput;
    }

    @Nullable
    public FluidStack[] getOutputFluids() {
        return hasOutputFluids() ? fluidsOutput.clone() : null;
    }

    public int getDuration() {
        return duration;
    }

    public long getPower() {
        return power;
    }

    @Nullable
    public double[] getChances() {
        return chances;
    }

    public int getSpecialValue() {
        return special;
    }

    public boolean isHidden() {
        return hidden;
    }

    public Set<RecipeTag> getTags() {
        return tags;
    }

    //todo fix tis
    public Map<ItemStack, Double> getChancesWithStacks(){
        if (itemsWithChances == null) {
            if (itemsOutput != null){
                ImmutableMap.Builder<ItemStack, Double> map = ImmutableMap.builder();
                if (hasChances()){
                    for (int i = 0; i < itemsOutput.length; i++) {
                        map.put(itemsOutput[i], chances[i]);
                    }
                } else {
                    for (ItemStack itemStack : itemsOutput) {
                        map.put(itemStack, 1.0);
                    }
                }
                itemsWithChances = map.build();
            } else {
                itemsWithChances = ImmutableMap.of();
            }
        }
        return itemsWithChances;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (itemsInput.size() > 0) {
            builder.append("\nInput Items: { ");
            for (int i = 0; i < itemsInput.size(); i++) {
                builder.append("\n Item ").append(i);
                //builder.append(itemsInput.get(i).get().getMatchingStacks()[0].getDisplayName()).append(" x").append(itemsInput.get(i).get().getMatchingStacks()[0].getCount());
                builder.append(itemsInput.get(i).toJson());
                if (i != itemsInput.size() - 1) builder.append(", ");
            }
            builder.append(" }\n");
        }
        if (itemsOutput != null) {
            builder.append("Output Items: { ");
            for (int i = 0; i < itemsOutput.length; i++) {
                builder.append(itemsOutput[i].getHoverName()).append(" x").append(itemsOutput[i].getCount());
                if (i != itemsOutput.length - 1) builder.append(", ");
            }
            builder.append(" }\n");
        }
        if (fluidsInput != null) {
            builder.append("Input Fluids: { ");
            //for (int i = 0; i < fluidsInput.size(); i++) {
            //    builder.append(fluidsInput.get(i).getFluid().getRegistryName()).append(": ").append(fluidsInput[i].getAmount()).append("mb");
            //    if (i != fluidsInput.length - 1) builder.append(", ");
            // }
            builder.append(" }\n");
        }
        if (fluidsOutput != null) {
            builder.append("Output Fluids: { ");
            for (int i = 0; i < fluidsOutput.length; i++) {
                builder.append(AntimatterPlatformUtils.getIdFromFluid(fluidsOutput[i].getFluid())).append(": ").append(fluidsOutput[i].getAmount()).append("mb");
                if (i != fluidsOutput.length - 1) builder.append(", ");
            }
            builder.append(" }\n");
        }
        if (chances != null) {
            builder.append("Chances: { ");
            for (int i = 0; i < chances.length; i++) {
                builder.append(chances[i] * 100).append("%");
                if (i != chances.length - 1) builder.append(", ");
            }
            builder.append(" }\n");
        }
        builder.append("Special: ").append(special).append("\n");
        return builder.toString();
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return false;
    }

    @Override
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public String getMapId() {
        return mapId;
    }

    @Override
    public ResourceLocation getId() {
        return id != null ? id : new ResourceLocation(Ref.ID, "default");
    }

    @Override
    public net.minecraft.world.item.crafting.RecipeSerializer<?> getSerializer() {
        return AntimatterRecipeSerializer.INSTANCE;
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return Recipe.RECIPE_TYPE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @FunctionalInterface
    public interface IRecipeValidator {
        boolean validate(Recipe recipe, TileEntityMachine<?> machine);

        default boolean tick(Recipe recipe, TileEntityMachine<?> machine) {
            return true;
        }
    }
}
