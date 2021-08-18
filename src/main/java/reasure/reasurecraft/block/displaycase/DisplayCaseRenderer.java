package reasure.reasurecraft.block.displaycase;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class DisplayCaseRenderer extends TileEntityRenderer<DisplayCaseTileEntity> {
    private float degrees;

    public DisplayCaseRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
        degrees = 0.0f;
    }

    @Override
    public void render(DisplayCaseTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        NonNullList<ItemStack> items = tileEntity.getItems();
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                matrixStack.pushPose();
                float scale = 0.7f;
                matrixStack.scale(scale, scale, scale);
                float curTime = Objects.requireNonNull(tileEntity.getLevel()).getGameTime() + partialTicks;
                matrixStack.translate(0.7D, (Math.sin(Math.PI * curTime / 16) / 7) + 0.9D, 0.7D);
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(degrees++ / 2));
                renderItem(stack, partialTicks, matrixStack, buffer, combinedLight, combinedOverlay);
                matrixStack.popPose();
            }
        }
    }

    @SuppressWarnings("unused")
    private void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemCameraTransforms.TransformType.FIXED, combinedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
    }
}
