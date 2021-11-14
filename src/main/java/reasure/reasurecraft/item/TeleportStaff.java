package reasure.reasurecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import reasure.reasurecraft.config.ItemConfig;

public class TeleportStaff extends TooltipItem {
    public TeleportStaff(Properties properties) {
        super(properties.durability(1000).setNoRepair());
    }

    private BlockRayTraceResult rayTrace(World world, PlayerEntity player) {

        double range = ItemConfig.teleport_staff_distance.get(); // Objects.requireNonNull(player.getAttribute(ForgeMod.REACH_DISTANCE.get())).getValue(); = 5

        float xRot = player.xRot;
        float yRot = player.yRot;

        Vector3d eyePosition = player.getEyePosition(1.0F);

        float f1 = MathHelper.sin((float)(-yRot * (Math.PI / 180F) - Math.PI));
        float f2 = MathHelper.cos((float)(-yRot * (Math.PI / 180F) - Math.PI));
        float f3 = -MathHelper.cos((float)(-xRot * (Math.PI / 180F)));

        double destinationY = MathHelper.sin((float)(-xRot * (Math.PI / 180F))) * range;

        Vector3d destinationPosition = eyePosition.add(f1 * f3 * range, destinationY, f2 * f3 * range);

        return world.clip(new RayTraceContext(eyePosition, destinationPosition, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getDamageValue() < stack.getMaxDamage()) {
            int cooldown = ItemConfig.teleport_staff_cooldown.get();
            if (!player.isCreative() && cooldown > 0) {
                player.getCooldowns().addCooldown(this, cooldown);
            }

            Vector3d loc = rayTrace(world, player).getLocation();

            player.teleportTo(loc.x, loc.y, loc.z);

            player.fallDistance = 0;

            stack.setDamageValue(stack.getDamageValue() + 1);

            return ActionResult.sidedSuccess(stack, world.isClientSide());
        }

        return ActionResult.fail(stack);
    }
}
