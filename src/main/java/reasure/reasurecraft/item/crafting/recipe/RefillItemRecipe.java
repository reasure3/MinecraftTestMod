package reasure.reasurecraft.item.crafting.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import reasure.reasurecraft.init.ModRecipes;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RefillItemRecipe implements ICraftingRecipe {
    private final ResourceLocation id;
    private final ItemStack refillItem;
    private final Ingredient ingredient;

    public RefillItemRecipe(ResourceLocation id, ItemStack refillItem, Ingredient ingredient) {
        this.id = id;
        this.refillItem = refillItem;
        this.ingredient = ingredient;
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        if (!refillItem.isDamageableItem()) {
            return false;
        }

        boolean hasIngredient = false;
        List<ItemStack> refillItems = Lists.newArrayList();

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack item = inv.getItem(i);
            if (!item.isEmpty()) {
                if (item.sameItem(refillItem)) {
                    refillItems.add(item);
                } else if (ingredient.test(item)) {
                    hasIngredient = true;
                } else {
                    return false;
                }
            }
        }

        if (refillItems.size() != 1) {
            return false;
        }

        if (!refillItems.get(0).isDamaged()) {
            return false;
        }

        return hasIngredient;
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        if (!refillItem.isDamageableItem()) {
            return ItemStack.EMPTY;
        }

        List<ItemStack> refillItems = Lists.newArrayList();
        List<ItemStack> ingredients = Lists.newArrayList();

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack item = inv.getItem(i);
            if (!item.isEmpty()) {
                if (item.sameItem(refillItem)) {
                    refillItems.add(item);
                } else if (ingredient.test(item)) {
                    ingredients.add(item);
                } else {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (refillItems.size() != 1) {
            return ItemStack.EMPTY;
        }

        if (!refillItems.get(0).isDamaged()) {
            return ItemStack.EMPTY;
        }

        if (ingredients.size() < 1) {
            return ItemStack.EMPTY;
        }

        ItemStack result = refillItems.get(0).copy();
        result.setDamageValue(result.getDamageValue() - ingredients.size());
        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        List<ItemStack> refillItems = Lists.newArrayList();
        List<Tuple<Integer, ItemStack>> ingredients = Lists.newArrayList();

        NonNullList<ItemStack> remainings = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack item = inv.getItem(i);
            if (!item.isEmpty()) {
                if (item.sameItem(refillItem)) {
                    refillItems.add(item);
                } else if (ingredient.test(item)) {
                    ingredients.add(new Tuple<>(i, item));
                } else {
                    return remainings;
                }
            }
        }

        if (refillItems.size() != 1) {
            return remainings;
        }

        if (!refillItems.get(0).isDamaged()) {
            return remainings;
        }

        if (ingredients.size() < 1) {
            return remainings;
        }

        int reduce = refillItems.get(0).getDamageValue();
        for (int j = 0; j < ingredients.size(); ++j) {
            Tuple<Integer, ItemStack> tuple = ingredients.get(j);
            if (j < reduce) {
                if (tuple.getB().hasContainerItem()) {
                    remainings.set(tuple.getA(), tuple.getB().getContainerItem());
                }
            } else {
                ItemStack remain = tuple.getB().copy();
                remain.setCount(1);
                remainings.set(tuple.getA(), remain);
            }
        }

        return remainings;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResultItem() {
        ItemStack item = refillItem.copy();
        if (item.isDamageableItem()) {
            Random random = new Random();
            int reduce = random.nextInt(item.getMaxDamage() - 1) + 1;
            item.setDamageValue(reduce);
        }
        return item;
    }

    public ItemStack getRefillItem() {
        return refillItem.copy();
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(Ingredient.of(this.refillItem.copy()));
        list.add(this.ingredient);
        return list;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.Serializers.REFILL.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RefillItemRecipe> {
        @Override
        public RefillItemRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final ItemStack refillItem = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "refillItem"));

            final JsonElement inputElement = JSONUtils.isArrayNode(json, "ingredient") ? JSONUtils.getAsJsonArray(json, "ingredient")
                    : JSONUtils.getAsJsonObject(json, "ingredient");
            final Ingredient ingredient = Ingredient.fromJson(inputElement);

            return new RefillItemRecipe(recipeId, refillItem, ingredient);
        }

        @Nullable
        @Override
        public RefillItemRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            final ItemStack refillItem = buffer.readItem();
            final Ingredient ingredient = Ingredient.fromNetwork(buffer);

            return new RefillItemRecipe(recipeId, refillItem, ingredient);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, RefillItemRecipe recipe) {
            buffer.writeItem(recipe.refillItem);
            recipe.ingredient.toNetwork(buffer);
        }
    }
}
