package reasure.reasurecraft.init;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import reasure.reasurecraft.item.*;
import reasure.reasurecraft.item.itemgroup.ModItemGroup;
import reasure.reasurecraft.item.material.ModArmorMaterial;
import reasure.reasurecraft.item.material.ModItemTier;
import reasure.reasurecraft.util.ModResourceLocation;

import java.util.function.Supplier;

public class ModItems {
    public static final RegistryObject<HorseArmorItem> SILVER_HORSE_ARMOR = Registration.ITEMS.register("silver_horse_armor", () ->
            new HorseArmorItem(5, ModResourceLocation.getId("textures/entity/horse/armor/horse_armor_silver.png"),
                    defaultProperties().stacksTo(1)));

    //공격력 계산 공식 = 1 + ItemTier 공격력 + 무기 공격력
    //공격 속도 계산 공식 = 4 + 무기 공격 속도
    //공격속도를 0.0F로 설정하면 쿨다운 없어짐
    public static final RegistryObject<SwordItem> SILVER_SWORD = register("silver_sword", () ->
            new SwordItem(ModItemTier.SILVER, 3, -2.4F, defaultProperties()));

    public static final RegistryObject<ShovelItem> SILVER_SHOVEL = register("silver_shovel", () ->
            new ShovelItem(ModItemTier.SILVER, 1.5F, -3.0F, defaultProperties()));

    public static final RegistryObject<PickaxeItem> SILVER_PICKAXE = register("silver_pickaxe", () ->
            new PickaxeItem(ModItemTier.SILVER, 1, -2.8F, defaultProperties()));

    public static final RegistryObject<AxeItem> SILVER_AXE = register("silver_axe", () ->
            new AxeItem(ModItemTier.SILVER, 5.4F, -2.9F, defaultProperties()));

    public static final RegistryObject<HoeItem> SILVER_HOE = register("silver_hoe", () ->
            new HoeItem(ModItemTier.SILVER, -2, 0.3F, defaultProperties()));

    public static final RegistryObject<ArmorItem> SILVER_HELMET = register("silver_helmet", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.HEAD, defaultProperties()));

    public static final RegistryObject<ArmorItem> SILVER_CHESTPLATE = register("silver_chestplate", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.CHEST, defaultProperties()));

    public static final RegistryObject<ArmorItem> SILVER_LEGGINGS = register("silver_leggings", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.LEGS, defaultProperties()));

    public static final RegistryObject<ArmorItem> SILVER_BOOTS = register("silver_boots", () ->
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlotType.FEET, defaultProperties()));

    public static final RegistryObject<Item> SILVER_INGOT = register("silver_ingot", defaultItem());

    public static final RegistryObject<Item> SILVER_NUGGET = register("silver_nugget", defaultItem());

    public static final RegistryObject<Item> SILVER_DUST = register("silver_dust", defaultItem());

    public static final RegistryObject<Item> BLAZE_INGOT = register("blaze_ingot", defaultItem(defaultProperties().fireResistant()));

    public static final RegistryObject<BlazeAndSteel> BLAZE_AND_STEEL = register("blaze_and_steel", () -> new BlazeAndSteel(defaultProperties()));

    public static final RegistryObject<EffectFoodItem> POISON_APPLE = register("poison_apple", () -> new EffectFoodItem(defaultProperties().food(ModFoods.POISON_APPLE)));

    public static final RegistryObject<SpecialCoal> SPECIAL_COAL = register("special_coal", () -> new SpecialCoal(defaultProperties()));

    public static final RegistryObject<BlockNamedItem> PEANUT = register("peanut", () ->
            new BlockNamedItem(ModBlocks.PEANUTS.get(), defaultProperties().food(ModFoods.PEANUT)));

    public static final RegistryObject<BucketItem> RUBBER_BUCKET = register("rubber_bucket", () ->
            new BucketItem(ModFluids.RUBBER, defaultProperties().stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> OBSIDIAN_STICK = register("obsidian_stick", defaultItem());

    public static final RegistryObject<Item> ENDER_STICK = register("ender_stick", defaultItem());

    public static final RegistryObject<TeleportStaff> TELEPORT_STAFF = register("teleport_staff", () -> new TeleportStaff(defaultProperties()));

    public static final RegistryObject<TorchPlacer> TORCH_PLACER = register("torch_placer", () -> new TorchPlacer(defaultProperties()));

    private ModItems() {
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return Registration.ITEMS.register(name, item);
    }

    private static Supplier<Item> defaultItem() {
        return () -> new Item(defaultProperties());
    }

    private static Supplier<Item> defaultItem(Item.Properties properties) {
        return () -> new Item(properties);
    }

    private static Item.Properties defaultProperties() {
        return new Item.Properties().tab(ModItemGroup.reasurecraft);
    }

    static void register() {
    }
}
