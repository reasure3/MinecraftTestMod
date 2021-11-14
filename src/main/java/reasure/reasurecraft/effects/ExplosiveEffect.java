package reasure.reasurecraft.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.world.Explosion;

public class ExplosiveEffect extends Effect {
    private static final EffectType type = EffectType.HARMFUL;
    private static final int liquidColor = 0x6B510F;

    public ExplosiveEffect() {
        super(type, liquidColor);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
        entity.level.explode(null, entity.getX(), entity.getY(), entity.getZ(), amplifier, Explosion.Mode.NONE);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 100 == 0;  // perform effect once every 5 seconds
    }
}
