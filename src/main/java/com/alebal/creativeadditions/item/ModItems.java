package com.alebal.creativeadditions.item;

import com.alebal.creativeadditions.CreativeAdditions;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreativeAdditions.MOD_ID);

    // Example registration of a simple item, without a dedicated class.
    //public static final RegistryObject<Item> HANDCUFFS = ITEMS.register("handcuffs",
    //        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> HANDCUFFS = ITEMS.register("handcuffs", HandcuffsItem::new);
    public static final RegistryObject<Item> FIREBALL_LAUNCHER = ITEMS.register("fireball_launcher", FireballLauncherItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
