package muramasa.antimatter.recipe;

import com.google.gson.JsonObject;
import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.fabricators_of_create.porting_lib.crafting.CraftingHelper;
import muramasa.antimatter.datagen.builder.AntimatterShapedRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class RecipeUtil {
    @ExpectPlatform
    public static boolean isNBTIngredient(Class<? extends Ingredient> clazz){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isCompoundIngredient(Class<? extends Ingredient> clazz){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addConditionalRecipe(Consumer<FinishedRecipe> consumer, AntimatterShapedRecipeBuilder builtRecipe, Class configClass, String configFieldName, String recipeDomain, String recipeName) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addConditionalRecipe(Consumer<FinishedRecipe> consumer, AntimatterShapedRecipeBuilder builtRecipe, String config, String configField, String recipeDomain, String recipeName) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemStack getItemStack(JsonObject object, boolean readNBT){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Ingredient> void write(FriendlyByteBuf buffer, T ingredient){
        throw new AssertionError();
    }
}
