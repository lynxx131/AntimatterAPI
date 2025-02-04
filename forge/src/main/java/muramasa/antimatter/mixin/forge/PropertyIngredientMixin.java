package muramasa.antimatter.mixin.forge;

import muramasa.antimatter.recipe.ingredient.PropertyIngredient;
import muramasa.antimatter.registration.forge.AntimatterRegistration;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PropertyIngredient.class, remap = false)
public class PropertyIngredientMixin {
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return AntimatterRegistration.PROPERTY_SERIALIZER;
    }
}
