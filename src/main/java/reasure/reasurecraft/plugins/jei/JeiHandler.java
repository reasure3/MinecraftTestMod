package reasure.reasurecraft.plugins.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import reasure.reasurecraft.init.ModRecipes;
import reasure.reasurecraft.util.ModResourceLocation;

import java.util.Collection;
import java.util.stream.Collectors;

@JeiPlugin
public class JeiHandler implements IModPlugin {
    private static final ResourceLocation JEI_ID = ModResourceLocation.getId("jei_plugin");

    private static Collection<?> getRecipes(RecipeManager manager, IRecipeType<?> type) {
        return manager.getRecipes().parallelStream().filter(recipe -> recipe.getType() == type).collect(Collectors.toList());
    }

    private static Collection<?> getRecipes(RecipeManager manager, IRecipeSerializer<?> serializer) {
        return manager.getRecipes().parallelStream().filter(recipe -> recipe.getSerializer() == serializer).collect(Collectors.toList());
    }

    @Override
    public ResourceLocation getPluginUid() {
        return JEI_ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager;
        if (Minecraft.getInstance().level != null) {
            manager = Minecraft.getInstance().level.getRecipeManager();
            registration.addRecipes(getRecipes(manager, ModRecipes.TOSSING_TYPE), TossingRecipeCategory.ID);
            registration.addRecipes(getRecipes(manager, ModRecipes.REFILL_SERIALIZER.get()), RefillRecipeCategory.ID);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new TossingRecipeCategory(helper));
        registration.addRecipeCategories(new RefillRecipeCategory(helper));
    }

}
