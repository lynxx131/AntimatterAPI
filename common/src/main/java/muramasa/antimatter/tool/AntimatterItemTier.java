package muramasa.antimatter.tool;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import muramasa.antimatter.Data;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.util.TagUtils;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public class AntimatterItemTier implements Tier {

    private final Material primary;
    private final Material secondary;

    public static final AntimatterItemTier NULL = new AntimatterItemTier(Data.NULL, Data.NULL);

    private static final Int2ObjectMap<AntimatterItemTier> TIERS_LOOKUP = new Int2ObjectOpenHashMap<>();

    static {
        TIERS_LOOKUP.put(NULL.hashCode(), NULL);
    }

    AntimatterItemTier(@Nonnull Material primary, @Nonnull Material secondary) {
        this.primary = primary.has(MaterialTags.TOOLS) ? primary : Data.NULL;
        this.secondary = secondary.has(MaterialTags.HANDLE) ? secondary : Data.NULL;
    }

    public static Optional<AntimatterItemTier> get(int key) {
        return Optional.ofNullable(TIERS_LOOKUP.get(key));
    }

    public static AntimatterItemTier getOrCreate(String primaryName, String secondaryName) {
        return TIERS_LOOKUP.computeIfAbsent(Objects.hash(primaryName, secondaryName), m -> new AntimatterItemTier(Material.get(primaryName), Material.get(secondaryName)));
    }

    public static AntimatterItemTier getOrCreate(Material primary, Material secondary) {
        return TIERS_LOOKUP.computeIfAbsent(Objects.hash(primary.hashCode(), secondary.hashCode()), m -> new AntimatterItemTier(primary, secondary));
    }

    @Override
    public int getUses() {
        return MaterialTags.TOOLS.getToolData(primary).toolDurability() + MaterialTags.HANDLE.getHandleData(secondary).durability();
    }

    @Override
    public float getSpeed() {
        return MaterialTags.TOOLS.getToolData(primary).toolSpeed() + MaterialTags.HANDLE.getHandleData(secondary).speed();
    }

    @Override
    public float getAttackDamageBonus() {
        return MaterialTags.TOOLS.getToolData(primary).toolDamage();
    }

    @Override
    public int getLevel() {
        return MaterialTags.TOOLS.getToolData(primary).toolQuality();
    }

    @Override
    public int getEnchantmentValue() {
        return (int) (getLevel() + getSpeed());
    }

    @Override
    public Ingredient getRepairIngredient() {
        if (primary.has(Data.GEM)) {
            return Ingredient.of(TagUtils.getForgelikeItemTag("gems/".concat(primary.getId())));
        } else if (primary.has(Data.INGOT)) {
            return Ingredient.of(TagUtils.getForgelikeItemTag("ingots/".concat(primary.getId())));
        } else if (primary.has(Data.DUST)) {
            return Ingredient.of(TagUtils.getForgelikeItemTag("dusts/".concat(primary.getId())));
        } //else if (ItemTags.getAllTags().getTag(new ResourceLocation("forge", "blocks/".concat(primary.getId()))) != null) {
         //   return Ingredient.of(TagUtils.getForgeItemTag("blocks/".concat(primary.getId())));
     //   }
        return Ingredient.EMPTY;
        // return null;
    }

    public Material getPrimary() {
        return primary;
    }

    public Material getSecondary() {
        return secondary;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        AntimatterItemTier tier = (AntimatterItemTier) obj;
        return primary == tier.getPrimary() && secondary == tier.getSecondary();
    }

    @Override
    public int hashCode() {
        return Objects.hash(primary.hashCode(), secondary.hashCode());
    }

}