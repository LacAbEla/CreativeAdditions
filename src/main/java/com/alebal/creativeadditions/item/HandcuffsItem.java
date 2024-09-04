package com.alebal.creativeadditions.item;

import com.alebal.creativeadditions.CreativeAdditions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class HandcuffsItem extends Item {

    public HandcuffsItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    }

    // None of these work on villagers and it appears there are no more interaction methods
    // TODO Some interacteable entities like villagers (but not horses) appear to prevent this from executing. Is there a way to prevent that without events?
        // if using events I could either make everything happen on an event like I was doing before
        // or try to use the event to cancel further events if the item is being held
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        CreativeAdditions.LOGGER.debug("Handcuffs usage on ENTITY. Hand: " + usedHand + ". Entity: " + interactionTarget);
        return InteractionResult.PASS;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        CreativeAdditions.LOGGER.debug("Handcuffs usage. Hand: " + usedHand);
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        CreativeAdditions.LOGGER.debug("Handcuffs usage on block.");
        return InteractionResult.PASS;
    }


    // TODO Investigate event
    /*
    @SubscribeEvent
    public void entityInteract(PlayerInteractEvent.EntityInteract event)
    {
        var playerInventory = event.getPlayer().getInventory();
        // playerInventory.getSelected() always returns the ItemStack held in the MAIN HAND
        CreativeAdditions.LOGGER.debug("Event fired at " + event.getSide() + ". Hand: " + event.getHand());
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
                CreativeAdditions.LOGGER.error("Could not cancel PlayerInteractEvent.EntityInteract!");
        }
    }
    */
}
