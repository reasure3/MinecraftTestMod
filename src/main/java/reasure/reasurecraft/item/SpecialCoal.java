package reasure.reasurecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpecialCoal extends TooltipItem {
    public SpecialCoal(Properties properties) {
        super(properties.rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) {
        if (recipeType == IRecipeType.SMELTING) {
            return 1;
        } else if (recipeType == IRecipeType.BLASTING || recipeType == IRecipeType.SMOKING) {
            return 2;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return this.getDefaultInstance();
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity playerEntity) {
        super.onCraftedBy(stack, world, playerEntity);
        stack.shrink(1); // not work
    }
}
