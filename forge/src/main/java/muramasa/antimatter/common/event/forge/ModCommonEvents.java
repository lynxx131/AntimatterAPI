package muramasa.antimatter.common.event.forge;

import muramasa.antimatter.AntimatterConfig;
import muramasa.antimatter.Ref;
import muramasa.antimatter.datagen.AntimatterDynamics;
import net.devtech.arrp.api.RRPEvent;
import net.devtech.arrp.api.RRPInitEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Ref.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {
    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent e) {
        AntimatterConfig.onModConfigEvent(e.getConfig());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRRPInit(RRPInitEvent event){
        AntimatterDynamics.runAssetProvidersDynamically();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onResourcePackAddAfter(RRPEvent.AfterVanilla event){
        AntimatterDynamics.addResourcePacks(event::addPack);
    }
}
