package reasure.reasurecraft.plugins.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.init.ModItems;
import reasure.reasurecraft.item.crafting.recipe.TossingRecipe;
import reasure.reasurecraft.util.ModResourceLocation;

public class TossingRecipeCategory implements IRecipeCategory<TossingRecipe> {

    public static final ResourceLocation ID = ModResourceLocation.getId("recipe.category.tossing");

    private final IDrawable back;
    private final IDrawable icon;

    public TossingRecipeCategory(IGuiHelper helper) {
        this.back = helper.createBlankDrawable(180, 100);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModItems.SPECIAL_COAL.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends TossingRecipe> getRecipeClass() {
        return TossingRecipe.class;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("category." + ReasureCraft.MOD_ID + ".tossing").getString();
    }

    @Override
    public IDrawable getBackground() {
        return back;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(TossingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, TossingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();

        itemStackGroup.init(0, true, 0, 0);
        itemStackGroup.init(1, true, 0, 36);
        itemStackGroup.init(2, false, 60, 18);

        itemStackGroup.set(ingredients);
    }
}
