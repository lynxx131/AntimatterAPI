package muramasa.antimatter.integration.jei.renderer;

import muramasa.antimatter.recipe.Recipe;
import net.minecraft.client.gui.FontRenderer;

import java.util.Objects;

public class FuelInfoRenderer implements IRecipeInfoRenderer{
    public static final IRecipeInfoRenderer INSTANCE = new FuelInfoRenderer();
    @Override
    public void render(Recipe recipe, FontRenderer fontRenderer, int guiOffsetX, int guiOffsetY) {
        String fuelPerMb =  "Fuel content(mb): "+(recipe.getPower() / Objects.requireNonNull(recipe.getInputFluids())[0].getAmount());
        String fuelPerB =  "Fuel content(bb): "+(recipe.getPower() / Objects.requireNonNull(recipe.getInputFluids())[0].getAmount())*1000;
        renderString(fuelPerMb,fontRenderer, 5, 5,guiOffsetX,guiOffsetY);
        renderString(fuelPerB,fontRenderer, 5, 15,guiOffsetX,guiOffsetY);
    }
}
