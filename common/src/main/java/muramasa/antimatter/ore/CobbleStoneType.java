package muramasa.antimatter.ore;

import muramasa.antimatter.block.BlockStone;
import muramasa.antimatter.block.BlockStoneSlab;
import muramasa.antimatter.block.BlockStoneStair;
import muramasa.antimatter.block.BlockStoneWall;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.registration.RegistryType;
import muramasa.antimatter.texture.Texture;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

import java.util.LinkedHashMap;
import java.util.Map;

public class CobbleStoneType extends StoneType {
    String beginningPath;
    Map<String, Block> blocks = new LinkedHashMap<>();
    public static final String[] SUFFIXES = {"bricks_chiseled", "bricks_cracked", "bricks_mossy", "cobble_mossy", "bricks", "cobble", "smooth", ""};

    public static final String[] SLAB_SUFFIXES = {"bricks_mossy_slab", "cobble_mossy_slab", "bricks_slab", "cobble_slab", "smooth_slab", "slab"};

    public static final String[] STAIR_SUFFIXES = {"bricks_mossy_stairs", "cobble_mossy_stairs", "bricks_stairs", "cobble_stairs", "smooth_stairs", "stairs"};
    public static final String[] WALL_SUFFIXES = {"bricks_mossy_wall", "cobble_mossy_wall", "bricks_wall", "cobble_wall", "smooth_wall", "wall"};
    public CobbleStoneType(String domain, String id, Material material, String beginningPath, SoundType soundType, boolean generateBlock) {
        super(domain, id, material, new Texture(domain, beginningPath + id + "/stone"), soundType, generateBlock);
        this.beginningPath = beginningPath;
    }

    @Override
    public void onRegistryBuild(RegistryType registry) {
        if (registry == RegistryType.BLOCKS) {
            if (generateBlock) {
                for (int i = 0; i < SUFFIXES.length; i++) {
                    Block stone;
                    if (i == 7) {
                        stone = new BlockStone(this);
                        setState(stone);
                    } else {
                        stone = new BlockStone(this, SUFFIXES[i]);
                    }
                    blocks.put(SUFFIXES[i], stone);
                    if (i < 2){
                        continue;
                    }
                    int i2 = i - 2;
                    blocks.put(SLAB_SUFFIXES[i2], new BlockStoneSlab(this, SUFFIXES[i]));
                    blocks.put(STAIR_SUFFIXES[i2], new BlockStoneStair(this, SUFFIXES[i], stone));
                    blocks.put(WALL_SUFFIXES[i2], new BlockStoneWall(this, SUFFIXES[i]));
                }
            }
        }
    }

    public String getBeginningPath() {
        return beginningPath;
    }

    public Block getBlock(String name) {
        return blocks.get(name);
    }

    public Map<String, Block> getBlocks() {
        return blocks;
    }
}
