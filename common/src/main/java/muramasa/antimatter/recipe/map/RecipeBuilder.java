package muramasa.antimatter.recipe.map;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.recipe.Recipe;
import muramasa.antimatter.recipe.RecipeTag;
import muramasa.antimatter.recipe.ingredient.FluidIngredient;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeBuilder {

    private static final Map<String, IRecipe> ID_MAP = new Object2ObjectArrayMap<>();

    private RecipeMap<? extends RecipeBuilder> recipeMap;
    protected List<ItemStack> itemsOutput = new ObjectArrayList<>();
    protected List<Ingredient> ingredientInput = new ObjectArrayList<>();
    protected List<FluidIngredient> fluidsInput = new ObjectArrayList<>();
    protected List<FluidStack> fluidsOutput = new ObjectArrayList<>();
    protected double[] chances;
    protected int duration, special;
    protected long power;
    protected int amps;
    protected boolean hidden;
    protected Set<RecipeTag> tags = new ObjectOpenHashSet<>();
    protected ResourceLocation id;

    public Recipe add() {
        Recipe r = build(duration, power, special, amps);
        addToMap(r);
        return r;
    }

    public static void clearList(){
        ID_MAP.clear();
    }

    public static Map<String, IRecipe> getIdMap() {
        return ID_MAP;
    }

    protected void addToMap(Recipe r) {
        recipeMap.add(r);
    }

    /**
     * Builds a recipe without adding it to a map.
     *
     * @return the recipe.
     */
    public Recipe build(int duration, long power, int special, int amps) {
        if (itemsOutput != null && itemsOutput.size() > 0 && !Utils.areItemsValid(itemsOutput.toArray(new ItemStack[0]))) {
            String id = this.id == null ? "": " Recipe ID: " + this.id;
            Utils.onInvalidData("RECIPE BUILDER ERROR - OUTPUT ITEMS INVALID!" + id + " Recipe map ID:" + recipeMap.getId());
            return Utils.getEmptyRecipe();
        }
        /*if (fluidsInput != null && fluidsInput.size() > 0) {
            Utils.onInvalidData("RECIPE BUILDER ERROR - INPUT FLUIDS INVALID!");
            return Utils.getEmptyRecipe();
        }*/
        if (fluidsOutput != null && fluidsOutput.size() > 0 && !Utils.areFluidsValid(fluidsOutput.toArray(new FluidStack[0]))) {
            String id = this.id == null ? "": " Recipe ID: " + this.id;
            Utils.onInvalidData("RECIPE BUILDER ERROR - OUTPUT FLUIDS INVALID!" + id + " Recipe map ID:" + recipeMap.getId());
            return Utils.getEmptyRecipe();
        }
        if (ingredientInput == null) ingredientInput = Collections.emptyList();
        /*if (itemsOutput != null) {
            for (int i = 0; i < itemsOutput.size(); i++) {
                itemsOutput.add(i, Unifier.get(itemsOutput.get(i)));
            }
        }*/
        if (amps < 1) amps = 1;
        Recipe recipe = new Recipe(
                ingredientInput,
                itemsOutput != null ? itemsOutput.toArray(new ItemStack[0]) : null,
                fluidsInput != null ? fluidsInput : Collections.emptyList(),
                fluidsOutput != null ? fluidsOutput.toArray(new FluidStack[0]) : null,
                duration, power, special, amps
        );
        if (chances != null) recipe.addChances(chances);
        recipe.setHidden(hidden);
        recipe.addTags(new ObjectOpenHashSet<>(tags));
        getID();
        if (this.id != null){
            recipe.setId(this.id);
            ID_MAP.put(this.id.toString(), recipe);
        }
        return recipe;
    }

    public void getID(){
        if (id == null){
            if (itemsOutput != null && itemsOutput.size() > 0){
                String id = AntimatterPlatformUtils.getIdFromItem(itemsOutput.get(0).getItem()).toString() + "_recipe";
                checkID(id);
            } else if (fluidsOutput != null && fluidsOutput.size() > 0){
                String id = AntimatterPlatformUtils.getIdFromFluid(fluidsOutput.get(0).getFluid()).toString() + "_recipe";
                checkID(id);
            } else if (!ingredientInput.isEmpty() && ingredientInput.get(0).getItems().length > 0){
                ItemStack stack = ingredientInput.get(0).getItems()[0];
                String id = AntimatterPlatformUtils.getIdFromItem(stack.getItem()).toString() + "_recipe";
                checkID(id);
            } else if (!fluidsInput.isEmpty()){
                FluidIngredient ing = fluidsInput.get(0);
                String id;
                if (ing.getTag() != null){
                    id = ing.getTag().location().toString() + "_recipe";
                } else {
                    List<FluidStack> list = Arrays.asList(ing.getStacks());
                    if (!list.isEmpty()){
                        id = AntimatterPlatformUtils.getIdFromFluid(list.get(0).getFluid()).toString() + "_recipe";
                    } else {
                        id = "antimatter:unknown_in_" + recipeMap.getId();
                    }
                }
                checkID(id);
            }
        }
    }

    private void checkID(String id) {
        if (ID_MAP.containsKey(id)){
            String newID;
            int i = 1;
            do {
                newID = id + "_" + i;
                i++;
            } while (ID_MAP.containsKey(newID));
            id = newID;
        }
        this.id = new ResourceLocation(id);
    }

    public Recipe add(long duration, long power, long special) {
        return add(duration, power, special, 1);
    }

    public Recipe add(long duration, long power, long special, int amps) {
        this.duration = (int) duration;
        this.power = power;
        this.special = (int) special;
        this.amps = amps;
        return add();
    }

    public Recipe add(long duration, long power) {
        return add(duration, power, this.special);
    }

    public Recipe add(int duration) {
        return add(duration, 0, this.special);
    }

    public RecipeBuilder ii(Ingredient... stacks) {
        ingredientInput.addAll(Arrays.asList(stacks));
        return this;
    }

    public RecipeBuilder ii(List<Ingredient> stacks) {
        ingredientInput.addAll(stacks);
        return this;
    }

    public RecipeBuilder io(ItemStack... stacks) {
        itemsOutput.addAll(Arrays.asList(stacks));
        return this;
    }

    public RecipeBuilder io(Item... stacks) {
        itemsOutput.addAll(Arrays.stream(stacks).map(Item::getDefaultInstance).toList());
        return this;
    }

    public RecipeBuilder io(List<ItemStack> stacks) {
        itemsOutput.addAll(stacks);
        return this;
    }

    public RecipeBuilder fi(FluidStack... stacks) {
        fluidsInput.addAll(Arrays.stream(stacks).map(FluidIngredient::of).toList());
        return this;
    }

    public RecipeBuilder fi(FluidIngredient... stacks) {
        fluidsInput.addAll(Arrays.asList(stacks));
        return this;
    }


    public RecipeBuilder fi(List<FluidStack> stacks) {
        fluidsInput.addAll(stacks.stream().map(FluidIngredient::of).toList());
        return this;
    }

    public RecipeBuilder fo(FluidStack... stacks) {
        fluidsOutput.addAll(Arrays.asList(stacks));
        return this;
    }

    public RecipeBuilder fo(List<FluidStack> stacks) {
        fluidsOutput.addAll(stacks);
        return this;
    }

    public RecipeBuilder id(ResourceLocation id){
        this.id = id;
        return this;
    }

    public RecipeBuilder id(String modid, String name){
        this.id = new ResourceLocation(modid, name);
        return this;
    }

    public RecipeBuilder id(String name){
        this.id = new ResourceLocation(recipeMap.getDomain(), name);
        return this;
    }

    /**
     * 10 = 10%, 75 = 75% etc
     **/
    public RecipeBuilder chances(double... values) {
        chances = values;
        return this;
    }

    public RecipeBuilder hide() {
        hidden = true;
        return this;
    }

    public RecipeBuilder tags(RecipeTag... tags) {
        this.tags = new ObjectOpenHashSet<>(tags);
        return this;
    }

    public void clear() {
        itemsOutput = new ObjectArrayList<>();
        ingredientInput = new ObjectArrayList<>();
        fluidsInput = new ObjectArrayList<>();
        fluidsOutput = new ObjectArrayList<>();
        chances = null;
        duration = special = 0;
        power = 0;
        hidden = false;
        tags.clear();
    }

    public RecipeMap<?> getMap() {
        return recipeMap;
    }

    public void setMap(RecipeMap<?> recipeMap) {
        this.recipeMap = recipeMap;
    }
}
