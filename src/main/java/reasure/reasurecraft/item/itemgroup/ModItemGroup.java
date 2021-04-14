package reasure.reasurecraft.item.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import reasure.reasurecraft.init.ModItems;

public abstract class ModItemGroup extends ItemGroup {
    private final String label;

    public static final ModItemGroup reasurecraft = new ModItemGroup("reasurecraft") {
        @OnlyIn(Dist.CLIENT)
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SILVER_PICKAXE.get());
        }
    };

    public ModItemGroup(String label) {
        super(label);
        this.label = label;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public abstract ItemStack makeIcon();

    public String getLabel() {
        return this.label;
    }
}
