package reasure.reasurecraft.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class TeleportEffect extends Effect {
    private static final EffectType type = EffectType.NEUTRAL;
    private static final int liquidColor = 0x032620;

    public TeleportEffect() {
        super(type, liquidColor);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
