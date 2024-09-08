package com.alebal.creativeadditions.item;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.Random;

public class FireballLauncherItem extends Item {
    // TODO replace fireball_launcher's texture with a 3D model
    // TODO Add variations or configuration options for more speed or power

    private int explosionPower = 1;


    public FireballLauncherItem() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT));
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        // Play sound(s)
        Random random = level.random;
        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        if (random.nextFloat() >= 0.96F) {
            if (random.nextFloat() >= 0.4F)
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GHAST_SCREAM, SoundSource.PLAYERS, 0.75F, (random.nextFloat() - random.nextFloat()) * 0.1F + 1.0F);
            else
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GHAST_HURT, SoundSource.PLAYERS, 0.75F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }

        // Launch fireball
        if(!level.isClientSide()) {
            var lookAngle = player.getLookAngle();
            var fireball = new LargeFireball(level, player, lookAngle.x, lookAngle.y, lookAngle.z, explosionPower); // The "offset" parameters are used to calculate the fireball's direction
            fireball.setPosRaw(fireball.getX() + lookAngle.x, fireball.getY() + player.getEyeHeight() + lookAngle.y, fireball.getZ() + lookAngle.z);
            //fireball.setDeltaMovement(player.getDeltaMovement()); // TODO this should copy the player's momentum
            //var deltaX = player.getX() - player.xOld; // always zero :/
            //var deltaY = player.getY() - player.yOld;
            //var deltaZ = player.getZ() - player.zOld;
            // TODO check if there is a block ahead. The shooting offset allows to shoot through blocks that are less than 1m wide
            level.addFreshEntity(fireball);
        }

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}