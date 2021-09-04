package reasure.reasurecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

public class KeyboardHelper {
    private static final long WINDOW = Minecraft.getInstance().getWindow().getWindow();

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingKey(Key key, Side side) {
        switch (key) {
            case SHIFT:
                return isHoldingShift(side);
            case CTRL:
                return isHoldingCtrl(side);
            default:
                return false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift(Side side) {
        boolean left = (side != Side.RIGHT) && InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT);
        boolean right = (side != Side.LEFT) && InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
        return side == Side.BOTH_AND ? left && right : left || right;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingCtrl(Side side) {
        boolean left = (side != Side.RIGHT) && InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_CONTROL);
        boolean right = (side != Side.LEFT) && InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_CONTROL);
        return side == Side.BOTH_AND ? left && right : left || right;
    }

    public enum Side {
        LEFT("Left"),
        RIGHT("Right"),
        BOTH_OR(""),
        BOTH_AND("Both");

        public final String label;

        Side(final String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }

    public enum Key {
        SHIFT("Shift"),
        CTRL("Ctrl");

        public final String label;

        Key(final String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }
}
