package com.alebal.creativeadditions.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

/**
 * Modifications to the vanilla EnderMan.
 */
public final class EndermanModifications {
    public static class EndermanBurnInsteadOfTakeBlockGoal extends EnderMan.EndermanTakeBlockGoal {
        public EndermanBurnInsteadOfTakeBlockGoal(EnderMan pEnderman) {
            super(pEnderman);
        }

        @Override
        public void tick() {
            // Original code
            Random random = this.enderman.getRandom();
            Level level = this.enderman.level;
            int i = Mth.floor(this.enderman.getX() - 2.0D + random.nextDouble() * 4.0D);
            int j = Mth.floor(this.enderman.getY() + random.nextDouble() * 3.0D);
            int k = Mth.floor(this.enderman.getZ() - 2.0D + random.nextDouble() * 4.0D);
            BlockPos blockpos = new BlockPos(i, j, k);
            BlockState blockstate = level.getBlockState(blockpos);
            Vec3 vec3 = new Vec3((double) this.enderman.getBlockX() + 0.5D, (double) j + 0.5D, (double) this.enderman.getBlockZ() + 0.5D);
            Vec3 vec31 = new Vec3((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D);
            BlockHitResult blockhitresult = level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this.enderman));
            boolean flag = blockhitresult.getBlockPos().equals(blockpos);
            if (blockstate.is(BlockTags.ENDERMAN_HOLDABLE) && flag) {
                // Block-removing code, changed.
                this.enderman.setCarriedBlock(Blocks.AIR.defaultBlockState());
                enderman.setSecondsOnFire(5);
            }
        }
    }

    public static void register(){
        MinecraftForge.EVENT_BUS.register(EndermanModifications.class);
    }

    @SubscribeEvent
    public static void entityJoinEvent(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof EnderMan enderman) {
            // Replace enderman's EndermanTakeBlockGoal with EndermanBurnOnTakeBlockGoal.
            for(var wrappedGoal : enderman.goalSelector.getAvailableGoals()){
                if(wrappedGoal.getGoal().toString().equals("EndermanTakeBlockGoal"))
                    enderman.goalSelector.removeGoal(wrappedGoal.getGoal());
            }
            enderman.goalSelector.addGoal(11, new EndermanBurnInsteadOfTakeBlockGoal(enderman));
        }
    }
}
