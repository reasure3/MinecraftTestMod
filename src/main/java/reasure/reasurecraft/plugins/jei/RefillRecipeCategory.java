package reasure.reasurecraft.plugins.jei;

import com.google.common.collect.Lists;
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
import reasure.reasurecraft.item.crafting.recipe.RefillItemRecipe;
import reasure.reasurecraft.util.ModResourceLocation;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RefillRecipeCategory implements IRecipeCategory<RefillItemRecipe> {
    public static final ResourceLocation ID = ModResourceLocation.getId("recipe.category.refill");

    private final IDrawable back;
    private final IDrawable icon;

    public RefillRecipeCategory(IGuiHelper helper) {
        this.back = helper.createBlankDrawable(116, 54);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModItems.TELEPORT_STAFF.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends RefillItemRecipe> getRecipeClass() {
        return RefillItemRecipe.class;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("category." + ReasureCraft.MOD_ID + ".refill").getString();
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
    public void setIngredients(RefillItemRecipe recipe, IIngredients ingredients) {
        ItemStack[] ingredient = recipe.getIngredient().getItems();
        ItemStack refillItem = recipe.getRefillItem();
        ItemStack result = recipe.getRefillItem();
        Random random = new Random();

        int damage = random.nextInt(refillItem.getMaxDamage()) + 1;
        refillItem.setDamageValue(damage);
        result.setDamageValue(damage - 1);

        List<ItemStack> list = Lists.newArrayList(refillItem);
        list.addAll(Arrays.asList(ingredient));

        ingredients.setInputs(VanillaTypes.ITEM, list);
        ingredients.setOutput(VanillaTypes.ITEM, result);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RefillItemRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();

        itemStackGroup.init(0, true, 0, 0);
        itemStackGroup.init(1, true, 0, 36);
        itemStackGroup.init(2, false, 60, 18);

        itemStackGroup.set(ingredients);
    }
}
