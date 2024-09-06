package com.alebal.creativeadditions.item;
import com.alebal.creativeadditions.CreativeAdditions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireballLauncherItem extends Item {

    public FireballLauncherItem() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT));
    }
    // TODO replace fireball_launcher's texture with a 3D model

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        CreativeAdditions.LOGGER.debug("FOOSH. Hand: " + usedHand + ". Looking: " + player.getLookAngle() + " - client?:" + level.isClientSide());

        // TODO play a sound when firing
        if(!level.isClientSide()) {
            var lookAngle = player.getLookAngle();
            var fireball = new LargeFireball(level, player, lookAngle.x, lookAngle.y, lookAngle.z, 1);
            fireball.setPosRaw(fireball.getX(), fireball.getY() + player.getEyeHeight() * 0.75, fireball.getZ());
            fireball.setDeltaMovement(player.getDeltaMovement()); // TODO this should copy the player's momentum
            // TODO add offset? Launching fireballs repeatedly while still is visually unpleasant
            level.addFreshEntity(fireball);

        }
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}