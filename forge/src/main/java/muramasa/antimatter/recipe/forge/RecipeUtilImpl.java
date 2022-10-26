package muramasa.antimatter.recipe.forge;

import com.google.gson.JsonObject;
import muramasa.antimatter.datagen.builder.AntimatterShapedRecipeBuilder;
import muramasa.antimatter.recipe.forge.condition.ConfigCondition;
import muramasa.antimatter.recipe.forge.condition.TomlConfigCondition;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.NBTIngredient;

import java.util.function.Consumer;

public class RecipeUtilImpl {
    public static boolean isNBTIngredient(Class<? extends Ingredient> clazz){
        return clazz == NBTIngredient.class;
    }

    public static boolean isCompoundIngredient(Class<? extends Ingredient> clazz){
        return clazz == CompoundIngredient.class;
    }

    public static void addConditionalRecipe(Consumer<FinishedRecipe> consumer, AntimatterShapedRecipeBuilder builtRecipe, Class configClass, String configFieldName, String recipeDomain, String recipeName) {
        ConditionalRecipe.builder().addCondition(new ConfigCondition(configClass, configFieldName))
                .addRecipe(builtRecipe::build).build(consumer, recipeDomain, recipeName);
    }

    public static void addConditionalRecipe(Consumer<FinishedRecipe> consumer, AntimatterShapedRecipeBuilder builtRecipe, String config, String configField, String recipeDomain, String recipeName) {
        ConditionalRecipe.builder().addCondition(new TomlConfigCondition(config, configField))
                .addRecipe(builtRecipe::build).build(consumer, recipeDomain, recipeName);
    }

    public static ItemStack getItemStack(JsonObject object, boolean readNBT){
        return CraftingHelper.getItemStack(object, readNBT);
    }

    public static <T extends Ingredient> void write(FriendlyByteBuf buffer, T ingredient){
        CraftingHelper.write(buffer, ingredient);
    }

}