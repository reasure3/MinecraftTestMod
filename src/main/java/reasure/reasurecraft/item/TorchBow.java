package reasure.reasurecraft.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import reasure.reasurecraft.entities.projectiles.TorchArrowEntity;

import java.util.function.Predicate;

public class TorchBow extends ModBowItem {
    public TorchBow(Properties properties) {
        super(properties.durability(384));
    }

    @Override
    protected double getArrowDamage(ItemStack bow, AbstractArrowEntity arrow, ItemStack projectile) {
        int power = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bow);
        return power > 0 ? power * 0.5D + 0.5D : 0D;
    }

    @Override
    protected boolean isCriticArrow(ItemStack bow, AbstractArrowEntity arrow, ItemStack projectile, float power) {
        return false;
    }

    @Override
    protected AbstractArrowEntity createArrow(World world, ItemStack projectile, PlayerEntity player) {
        return new TorchArrowEntity(world, player, projectile);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ammoStack -> {
            Item item = ammoStack.getItem();
            return item == Items.TORCH || item == Items.SOUL_TORCH || item == Items.REDSTONE_TORCH;
        };
    }

    @Override
    protected ItemStack defaultProjectile() {
        return Items.TORCH.getDefaultInstance();
    }

    @Override
    protected SoundEvent getShootingSound(ItemStack bow) {
        return SoundEvents.FIRECHARGE_USE;
    }
}
