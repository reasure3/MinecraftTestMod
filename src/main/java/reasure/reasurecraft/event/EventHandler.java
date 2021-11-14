package reasure.reasurecraft.event;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.init.ModEffects;
import reasure.reasurecraft.init.ModRecipes;
import reasure.reasurecraft.item.crafting.recipe.TossingRecipe;

@Mod.EventBusSubscriber(modid = ReasureCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onItemToss(final ItemTossEvent event) {
        ItemEntity itemEntity = event.getEntityItem();
        ItemStack item = itemEntity.getItem();
        World world = itemEntity.level;
        BlockPos pos = event.getPlayer().blockPosition().below();
        BlockState state = world.getBlockState(pos);

        final TossingRecipe recipe = world.getRecipeManager().getRecipeFor(ModRecipes.TOSSING_TYPE, new Inventory(item), world).orElse(null);
        if (recipe != null) {
            if (recipe.isValid(item, state.getBlock())) {
                BlockPos dropPos = pos.above();
                InventoryHelper.dropItemStack(world, dropPos.getX(), dropPos.getY(), dropPos.getZ(), recipe.getResultItem().copy());
                item.shrink(1);
//                itemEntity.setItem(item);
                world.removeBlock(pos, true);
            }
        }
    }

    @SubscribeEvent
    public static void onAttack(final AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        if (player.hasEffect(ModEffects.TELEPORT_EFFECT.get())) {
            if (target instanceof LivingEntity) {
                Vector3d playerPos = player.position();
                Vector3d targetPos = target.position();

                player.teleportTo(targetPos.x, targetPos.y, targetPos.z);
                target.teleportTo(playerPos.x, playerPos.y, playerPos.z);
            }
        }
    }
}
