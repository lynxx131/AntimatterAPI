package muramasa.antimatter.worldgen.vein;

import muramasa.antimatter.material.Material;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WorldGenVeinVariantMaterial {

    public final int weight;
    public final int maxY;
    public final Material material;
    public final int minY;

    private WorldGenVeinVariantMaterial(int weight, Material material, int minY, int maxY) {
        this.weight = weight;
        this.material = material;
        this.minY = minY;
        this.maxY = maxY;
    }

    static List<WorldGenVeinVariantMaterial> getFlat(int weight, Material material, int minY, int maxY) {
        return IntStream.range(0, weight).mapToObj(i -> new WorldGenVeinVariantMaterial(1, material, minY, maxY)).collect(Collectors.toList());
    }

}
