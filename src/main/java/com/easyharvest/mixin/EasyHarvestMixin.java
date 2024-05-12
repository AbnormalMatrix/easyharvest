package com.easyharvest.mixin;

import com.easyharvest.EasyHarvest;
import com.easyharvest.EasyHarvestInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CropBlock.class)
public abstract class EasyHarvestMixin implements EasyHarvestInterface {
    @Shadow public abstract int getMaxAge();

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {


        int maxAge = getMaxAge();
        int age = state.get(CropBlock.AGE);
        if (age < maxAge) {
            return ActionResult.FAIL;
        }

        // Get the type of crop
        Block blockType =world.getBlockState(pos).getBlock();

        world.breakBlock(pos, true, player);

        // set the block at the position to the same block type
        world.setBlockState(pos, blockType.getDefaultState());



        return ActionResult.SUCCESS;
    }
}
