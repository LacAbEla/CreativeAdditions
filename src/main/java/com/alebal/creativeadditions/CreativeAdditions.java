package com.alebal.creativeadditions;

import com.alebal.creativeadditions.entity.EndermanModifications;
import com.alebal.creativeadditions.item.ModItems;
import com.mojang.logging.LogUtils;

import net.minecraft.world.InteractionHand;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreativeAdditions.MOD_ID)
public class CreativeAdditions
{
    public static final String MOD_ID = "creativeadditions";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreativeAdditions()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        EndermanModifications.register();

        ModItems.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    // TODO: move this to a class for this purpose (ItemEvents? HandcuffsItem?)
    @SubscribeEvent
    public void entityInteract(PlayerInteractEvent.EntityInteract event)
    {
        var playerInventory = event.getPlayer().getInventory();
        // playerInventory.getSelected() always returns the ItemStack held in the MAIN HAND
        LOGGER.debug("Event fired at " + event.getSide() + ". Hand: " + event.getHand());
        if(playerInventory.getSelected().is(ModItems.HANDCUFFS.get()) && true) { // TODO check the entity is a mob
            // TODO? Allow handcuffs to work on off hand
                // it appears some vanilla items cause the off-hand version of this event to not fire. shouldn't cancelling this event on the main hand do that as well? investigate
                    // checked with spawn eggs
                    // right-clicking some non-villagers with no items or with items that don't interact causes the event to fire twice per hand at client (total 4+2). If the event is cancelled here it only fires once per hand (total 2+2).
                        // this also happens when right-clicking villagers and cancelling the event (total 2+2), BUT if the event is not cancelled then it only fires for the main hand (but does repeat, total 3+0)
                            // this means 3 calls in total if I don't cancel here but 4 calls if I do
                            // may have something to do with some piece of villager trading code being successful
                            // I should continue trying find a guide for this sh*t
            if(event.getHand() == InteractionHand.MAIN_HAND && event.getSide().isServer()) {
                if(true) { // TODO if mob is not cuffed
                    // TODO store in mob that it is now cuffed

                    // TODO Show the mob is cuffed / add cuffs to villager's hands
                        // villager hand slots may be used to show items if the player has in its hands something the villager wants - risky, possible bugs

                    // TODO add infinite (possible?) slowness 255
                        // Other (probably better) options:
                            // there may be an AI event that prevents movement if cancelled
                            // a modification to the AI itself. This probably can't be done in a generic way.
                            // the NoAI tag, but that also stops mobs rotating and looking around

                    playerInventory.getSelected().setCount(playerInventory.getSelected().getCount() - 1); // Remove 1 cuffs

                    // TODO? Emit disapproval sound

                    // TODO? Mobs could drop their cuffs if they die
                } else {
                    // TODO Store in mob that it is no longer cuffed

                    // TODO Remove its infinite slowness

                    // TODO Give back 1 cuffs to the player - always return to inventory if possible, drop if inventory full
                }
            }

            if(event.isCancelable())
                event.setCanceled(true);
            else
                LOGGER.error("Could not cancel PlayerInteractEvent.EntityInteract!");
        }
    }

    private void setup(final FMLCommonSetupEvent event)
    {
//        // some preinit code
//        LOGGER.info("HELLO FROM PREINIT");
//        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }


//    @SubscribeEvent
//    public void vanillaEvent(VanillaGameEvent event) {
//        if(event.getCause() instanceof EnderMan && event.getVanillaEvent().getName() == "block_destroy") {
//            logToPlayer(event.getClass().getName().toString() + ": enderman");
//            event.getCause().kill();
//        }
//    }


//
//    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event)
//    {
//        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
//    }
//
//    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
//    // Event bus for receiving Registry Events)
//    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class RegistryEvents
//    {
//        @SubscribeEvent
//        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
//        {
//            // Register a new block here
//            LOGGER.info("HELLO from Register Block");
//        }
//    }
}
