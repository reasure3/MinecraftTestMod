package reasure.reasurecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

public class KeyboardHelper {
    private static final long WINDOW = Minecraft.getInstance().getWindow().getWindow();

    public enum Side {
        LEFT, RIGHT, BOTH_OR, BOTH_AND
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift() {
        return isHoldingShift(Side.BOTH_OR);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift(Side side) {
        boolean left = (side != Side.RIGHT) && InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT);
        boolean right = (side != Side.LEFT) && InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
        return side == Side.BOTH_AND ? left && right : left || right;
    }

    public static String getShiftText(Side side) {
        switch (side) {
            case LEFT:
                return "Left Shift";
            case RIGHT:
                return "Right Shift";
            case BOTH_OR:
                return "Shift";
            case BOTH_AND:
                return "Both Shift";
            default:
                throw new IllegalStateException("Unexpected value: " + side);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingCtrl() {
        return isHoldingCtrl(Side.BOTH_OR);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingCtrl(Side side) {
        boolean left = (side != Side.RIGHT) && InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_CONTROL);
        boolean right = (side != Side.LEFT) && InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_CONTROL);
        return side == Side.BOTH_AND ? left && right : left || right;
    }
}
