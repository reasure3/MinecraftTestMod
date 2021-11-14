package reasure.reasurecraft.init;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.item.*;
import reasure.reasurecraft.item.itemgroup.ModItemGroup;
import reasure.reasurecraft.item.material.ModArmorMaterial;
import reasure.reasurecraft.item.material.ModItemTier;
import reasure.reasurecraft.util.ModResourceLocation;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ReasureCraft.MOD_ID);

    public static final RegistryObject<HorseArmorItem> SILVER_HORSE_ARMOR = ITEMS.register("silver_horse_armor", () ->
            new HorseArmorItem(5, ModResourceLocation.getId("textures/entity/horse/armor/horse_armor_silver.png"),
                    defaultProperties().stacksTo(1)));

    //공격력 계산 공식 = 1 + ItemTier 공격력 + 무기 공격력
    //공격 속도 계산 공식 = 4 + 무기 공격 속도
    //공격속도를 0.0F로 설정하면 쿨다운 없어짐
    public static final RegistryObject<SwordItem> SILVER_SWORD = ITEMS.register("silver_sword", () ->
            new SwordItem(ModItemTier.SILVER, 3, -2.4F, defaultProperties()));

    public static final RegistryObject<ShovelItem> SILVER_SHOVEL = ITEMS.register("silver_shovel", () ->
            new ShovelItem(ModItemTier.SILVER, 1.5F, -3.0F, defaultProperties()));

    public static final RegistryObject<PickaxeItem> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () ->
            new PickaxeItem(ModItemTier.SILVER, 1, -2.8F, defaultProperties()));

    public static final RegistryObject<AxeItem> SILVER_AXE = ITEMS.register("silver_axe", () ->
            new AxeItem(ModItemTier.SILVER, 5.4F, -2.9F, defaultProperties()));

    public static final RegistryObject<HoeItem> SILVER_HOE = ITEMS.register("silver_hoe", () ->
            new HoeItem(ModItemTier.SILVER, -2, 0.3F, defaultProperties()));

    public static final RegistryObject<ArmorItem> SILVER_HELMET = ITEMS.register("silver_helmet", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.HEAD, defaultProperties()));

    public static final RegistryObject<ArmorItem> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.CHEST, defaultProperties()));

    public static final RegistryObject<ArmorItem> SILVER_LEGGINGS = ITEMS.register("silver_leggings", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.LEGS, defaultProperties()));

    public static final RegistryObject<ArmorItem> SILVER_BOOTS = ITEMS.register("silver_boots", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.FEET, defaultProperties()));

    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", defaultItem());

    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", defaultItem());

    public static final RegistryObject<Item> SILVER_DUST = ITEMS.register("silver_dust", defaultItem());

    public static final RegistryObject<Item> BLAZE_INGOT = ITEMS.register("blaze_ingot", defaultItem(defaultProperties().fireResistant()));

    public static final RegistryObject<BlazeAndSteel> BLAZE_AND_STEEL = ITEMS.register("blaze_and_steel", () -> new BlazeAndSteel(defaultProperties()));

    public static final RegistryObject<EffectFoodItem> POISON_APPLE = ITEMS.register("poison_apple", () -> new EffectFoodItem(defaultProperties().food(ModFoods.POISON_APPLE)));

    public static final RegistryObject<SpecialCoal> SPECIAL_COAL = ITEMS.register("special_coal", () -> new SpecialCoal(defaultProperties()));

    public static final RegistryObject<BlockNamedItem> PEANUT = ITEMS.register("peanut", () ->
            new BlockNamedItem(ModBlocks.PEANUTS.get(), defaultProperties().food(ModFoods.PEANUT)));

    public static final RegistryObject<BucketItem> RUBBER_BUCKET = ITEMS.register("rubber_bucket", () ->
            new BucketItem(ModFluids.RUBBER, defaultProperties().stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> OBSIDIAN_STICK = ITEMS.register("obsidian_stick", defaultItem());

    public static final RegistryObject<Item> ENDER_STICK = ITEMS.register("ender_stick", defaultItem());

    public static final RegistryObject<TeleportStaff> TELEPORT_STAFF = ITEMS.register("teleport_staff", () -> new TeleportStaff(defaultProperties()));

    public static final RegistryObject<TorchPlacer> TORCH_PLACER = ITEMS.register("torch_placer", () -> new TorchPlacer(defaultProperties()));

    public static final RegistryObject<TorchBow> TORCH_BOW = ITEMS.register("torch_bow", () -> new TorchBow(defaultProperties()));

    private static Supplier<Item> defaultItem() {
        return () -> new Item(defaultProperties());
    }

    private static Supplier<Item> defaultItem(Item.Properties properties) {
        return () -> new Item(properties);
    }

    private static Item.Properties defaultProperties() {
        return new Item.Properties().tab(ModItemGroup.reasurecraft);
    }

    public static void registerModelProperties() {
        ItemModelsProperties.register(TORCH_BOW.get(), new ResourceLocation("pull"), (stack, world, player) -> {
            if (player == null) {
                return 0.0F;
            } else {
                return player.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - player.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemModelsProperties.register(TORCH_BOW.get(), new ResourceLocation("pulling"), (stack, world, player) ->
                player != null && player.isUsingItem() && player.getUseItem() == stack ? 1.0F : 0.0F);
    }
}
