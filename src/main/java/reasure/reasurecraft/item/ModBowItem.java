package reasure.reasurecraft.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class ModBowItem extends BowItem {
    public ModBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack bow, World world, LivingEntity entity, int timeLeft) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;
            boolean hasInfinite = canShootWithoutProjectile(player, bow);
            ItemStack projectile = getProjectile(player, bow);

            int timeDrawn = this.getUseDuration(bow) - timeLeft;
            timeDrawn = ForgeEventFactory.onArrowLoose(bow, world, player, timeDrawn, !projectile.isEmpty() || hasInfinite);
            if (timeDrawn < 0) return;

            if (!projectile.isEmpty() || hasInfinite) {
                if (projectile.isEmpty()) {
                    projectile = defaultProjectile();
                }

                float power = getPowerForTime(timeDrawn);
                if (!((double)power < 0.1D)) {
                    boolean infinite = isInfinite(player, bow, projectile);
                    boolean isTippedArrow = projectile.getItem() == Items.SPECTRAL_ARROW || projectile.getItem() == Items.TIPPED_ARROW;
                    if (!world.isClientSide) {
                        AbstractArrowEntity arrow = createArrow(world, projectile, player);
                        arrow = customArrow(arrow);

                        arrow.shootFromRotation(player, player.xRot, player.yRot, 0.0F, power * 3.0F, 1.0F);

                        arrow.setCritArrow(isCriticArrow(bow, arrow, projectile, power));

                        arrow.setBaseDamage(getArrowDamage(bow, arrow, projectile));

                        arrow.setKnockback(getArrowKnockback(bow, arrow, projectile));

                        arrow.setRemainingFireTicks(getArrowFireTick(bow, arrow, projectile));

                        bow.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));

                        if (infinite || isTippedArrow) {
                            arrow.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        world.addFreshEntity(arrow);
                    }

                    world.playSound(null, player.getX(), player.getY(), player.getZ(), getShootingSound(bow), SoundCategory.PLAYERS,
                            1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + power * 0.5F);

                    if (!infinite && !player.abilities.instabuild) {
                        projectile.shrink(1);
                        if (projectile.isEmpty()) {
                            player.inventory.removeItem(projectile);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    protected boolean canShootWithoutProjectile(PlayerEntity player, ItemStack bow) {
        return player.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow) > 0;
    }

    protected ItemStack defaultProjectile() {
        return new ItemStack(Items.ARROW);
    }

    protected boolean isInfinite(PlayerEntity player, ItemStack bow, ItemStack projectile) {
        return canShootWithoutProjectile(player, bow) || (projectile.getItem() instanceof ArrowItem && ((ArrowItem)projectile.getItem()).isInfinite(projectile, bow, player));
    }

    protected AbstractArrowEntity createArrow(World world, ItemStack projectile, PlayerEntity player) {
        ArrowItem arrowItem = (ArrowItem)(projectile.getItem() instanceof ArrowItem ? projectile.getItem() : Items.ARROW);
        return arrowItem.createArrow(world, projectile, player);
    }

    @SuppressWarnings("unused")
    protected boolean isCriticArrow(ItemStack bow, AbstractArrowEntity arrow, ItemStack projectile, float power) {
        return power >= 1.0f;
    }

    @SuppressWarnings("unused")
    protected double getArrowDamage(ItemStack bow, AbstractArrowEntity arrow, ItemStack projectile) {
        int enchant_power = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bow);
        if (enchant_power > 0) {
            return arrow.getBaseDamage() + enchant_power * 0.5D + 0.5D;
        }
        return arrow.getBaseDamage();
    }

    @SuppressWarnings("unused")
    protected int getArrowKnockback(ItemStack bow, AbstractArrowEntity arrow, ItemStack projectile) {
        return Math.max(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bow), 0);
    }

    @SuppressWarnings("unused")
    protected int getArrowFireTick(ItemStack bow, AbstractArrowEntity arrow, ItemStack projectile) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bow) > 0 ? 100 * 20 : 0;
    }

    @SuppressWarnings("unused")
    protected SoundEvent getShootingSound(ItemStack bow) {
        return SoundEvents.ARROW_SHOOT;
    }

    public ItemStack getProjectile(PlayerEntity player, ItemStack bow) {
        if (!(bow.getItem() instanceof ShootableItem)) {
            return ItemStack.EMPTY;
        } else {
            Predicate<ItemStack> predicate = ((ShootableItem)bow.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = ShootableItem.getHeldProjectile(player, predicate);
            if (!itemstack.isEmpty()) {
                return itemstack;
            } else {
                predicate = ((ShootableItem)bow.getItem()).getAllSupportedProjectiles();

                for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
                    ItemStack itemstack1 = player.inventory.getItem(i);
                    if (predicate.test(itemstack1)) {
                        return itemstack1;
                    }
                }

                return player.abilities.instabuild ? defaultProjectile() : ItemStack.EMPTY;
            }
        }
    }
}
