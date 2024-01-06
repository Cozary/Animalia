/*
 *
 *  * Copyright (c) 2024 Cozary
 *  *
 *  * This file is part of Animalia, a mod made for Minecraft.
 *  *
 *  * Animalia is free software: you can redistribute it and/or modify it
 *  * under the terms of the GNU General Public License as published
 *  * by the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * Animalia is distributed in the hope that it will be useful, but
 *  * WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * License along with Animalia.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.cozary.animalia.init;

import com.cozary.animalia.Animalia;
import com.cozary.animalia.blocks.BlockItemBase;
import com.cozary.animalia.blocks.BlockItemFoodBase;
import com.cozary.animalia.items.*;
import com.cozary.animalia.util.enums.ModArmorMaterial;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Animalia.MOD_ID);

    //Mud
    public static final RegistryObject<Item> MUD = ITEMS.register("mud", ItemBase::new);
    public static final RegistryObject<Item> MUD_BLOCK_ITEM = ITEMS.register("mud_block_item", () -> new BlockItemBase(ModBlocks.MUD_BLOCK.get()));
    public static final RegistryObject<Item> DECORATIVE_MUD_ITEM = ITEMS.register("decorative_mud_item", () -> new BlockItemBase(ModBlocks.DECORATIVE_MUD.get()));
    public static final RegistryObject<Item> HARDENED_MUD_ITEM = ITEMS.register("hardened_mud_item", () -> new BlockItemBase(ModBlocks.HARDENED_MUD.get()));
    public static final RegistryObject<Item> DECORATIVE_MUD_STAIRS_ITEM = ITEMS.register("decorative_mud_stairs_item", () -> new BlockItemBase(ModBlocks.DECORATIVE_MUD_STAIRS.get()));
    public static final RegistryObject<Item> DECORATIVE_MUD_SLAB_ITEM = ITEMS.register("decorative_mud_slab_item", () -> new BlockItemBase(ModBlocks.DECORATIVE_MUD_SLAB.get()));
    public static final RegistryObject<BucketItem> MUD_BUCKET = ITEMS.register("mud_bucket", () -> new BucketItem(
            ModFluids.MUD_FLUID, (new Item.Properties().tab(Animalia.TAB)).stacksTo(1).craftRemainder(Items.BUCKET)));

    //Snail
    public static final RegistryObject<Item> SHELL_ITEM = ITEMS.register("shell_item", () -> new BlockItemBase(ModBlocks.SHELL.get()));
    public static final RegistryObject<ArmorBase.ShellArmor> SHELL_ARMOR = ITEMS.register("shell_armor", () -> new ArmorBase.ShellArmor(ModArmorMaterial.SHELL, EquipmentSlot.CHEST, new Item.Properties().tab(Animalia.TAB).rarity(Rarity.RARE)));

    //BrownBear
    public static final RegistryObject<Item> BEAR_MEAT = ITEMS.register("bear_meat", () -> new Item(new Item.Properties().tab(Animalia.TAB).food(new FoodProperties.Builder().nutrition(4).meat().saturationMod(0.2f).build())));
    public static final RegistryObject<Item> COOKED_BEAR_MEAT = ITEMS.register("cooked_bear_meat", () -> new Item(new Item.Properties().tab(Animalia.TAB).food(new FoodProperties.Builder().nutrition(10).meat().saturationMod(1.0f).build())));
    public static final RegistryObject<Item> BEAR_LEATHER = ITEMS.register("bear_leather", ItemBase::new);
    public static final RegistryObject<ArmorBase.BearArmor> BEAR_ARMOR = ITEMS.register("bear_head", () -> new ArmorBase.BearArmor(ModArmorMaterial.BEAR, EquipmentSlot.HEAD, new Item.Properties().tab(Animalia.TAB).rarity(Rarity.RARE)));

    //Bull
    public static final RegistryObject<Item> BULL_HORN = ITEMS.register("bull_horn", ItemBase::new);
    public static final RegistryObject<ArmorBase.BullArmor> BULL_HAT = ITEMS.register("bull_hat", () -> new ArmorBase.BullArmor(ModArmorMaterial.BULL, EquipmentSlot.HEAD, new Item.Properties().tab(Animalia.TAB).rarity(Rarity.RARE)));

    //LILYGATOR
    public static final RegistryObject<Item> LILYGATOR_LEATHER = ITEMS.register("lilygator_leather", () -> new UltraFastMeat(new Item.Properties().tab(Animalia.TAB).food(new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.HARM, 1, 0), 0.5F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1), 0.6F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1), 1.0F).nutrition(1).meat().saturationMod(0.2f).alwaysEat().build())));
    public static final RegistryObject<Item> LILIGATOR_MEAT = ITEMS.register("lilygator_meat", () -> new SlowMeat(new Item.Properties().tab(Animalia.TAB).food(new FoodProperties.Builder().nutrition(7).meat().saturationMod(0.5f).build())));
    public static final RegistryObject<Item> COOKED_LILIGATOR_MEAT = ITEMS.register("cooked_lilygator_meat", () -> new SlowMeat(new Item.Properties().tab(Animalia.TAB).food(new FoodProperties.Builder().nutrition(15).meat().saturationMod(2f).build())));

    //Hippopotamus
    public static final RegistryObject<Item> HIPPOPOTAMUS_MEAT = ITEMS.register("hippopotamus_meat", () -> new Item(new Item.Properties().tab(Animalia.TAB).food(new FoodProperties.Builder().nutrition(2).meat().saturationMod(0.1f).fast().build())));
    public static final RegistryObject<Item> COOKED_HIPPOPOTAMUS_MEAT = ITEMS.register("cooked_hippopotamus_meat", () -> new Item(new Item.Properties().tab(Animalia.TAB).food(new FoodProperties.Builder().nutrition(4).meat().saturationMod(0.2f).fast().build())));

    //Jellyfish
    public static final RegistryObject<Item> SPONGE_PIECE = ITEMS.register("sponge_piece", ItemBase::new);
    public static final RegistryObject<Item> JELLY = ITEMS.register("jelly", () -> new Item(new Item.Properties().tab(Animalia.TAB).food(new FoodProperties.Builder().nutrition(2).meat().saturationMod(0.3f).fast().build())));
    public static final RegistryObject<Item> JELLY_BLOCK_ITEM = ITEMS.register("jelly_block_item", () -> new BlockItemFoodBase(ModBlocks.JELLY_BLOCK.get()));
    public static final RegistryObject<JellyFishBucketItem> JELLYFISH_BUCKET = ITEMS.register("jellyfish_bucket", () -> new JellyFishBucketItem(
            ModEntityTypes.JELLYFISH, Fluids.WATER.delegate, (new Item.Properties()).stacksTo(1).tab(Animalia.TAB)));

    //WhiteShark
    public static final RegistryObject<ArmorBase.FinsArmor> FINS = ITEMS.register("fins", () -> new ArmorBase.FinsArmor(ModArmorMaterial.FINS, EquipmentSlot.CHEST, new Item.Properties().tab(Animalia.TAB).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SHARK_SCALE = ITEMS.register("shark_scale", ItemBase::new);

    //Walrus
    public static final RegistryObject<Item> WALRUS_FANG = ITEMS.register("walrus_fang", ItemBase::new);
    public static final RegistryObject<Item> FAT = ITEMS.register("fat", CustomCoal::new);
    public static final RegistryObject<CustomTrident> NORTH_POLE = ITEMS.register("north_pole",
            () -> new CustomTrident(new Item.Properties().tab(Animalia.TAB).durability(250).rarity(Rarity.EPIC)));

    //SpawnEggs
    public static final RegistryObject<ForgeSpawnEggItem> DIRTY_PIG_SPAWN_EGG = ITEMS.register("dirty_pig_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.DIRTY_PIG, 0xc582a3, 0x5f3c00, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> BROWN_BEAR_SPAWN_EGG = ITEMS.register("brown_bear_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.BROWN_BEAR, 0x623a0b, 0x351b05, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> PLATYPUS_SPAWN_EGG = ITEMS.register("platypus_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.PLATYPUS, 0x63ba93, 0xfc9303, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> SNAIL_SPAWN_EGG = ITEMS.register("snail_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.SNAIL, 0x8c95a8, 0xa45021, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> WALRUS_SPAWN_EGG = ITEMS.register("walrus_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.WALRUS, 0x0999b9b, 0x666464, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> LILYGATOR = ITEMS.register("lilygator_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.LILYGATOR, 0x1fcb37, 0x146a21, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> BULL_SPAWN_EGG = ITEMS.register("bull_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.BULL, 0x000000, 0x202020, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> EAGLE_SPAWN_EGG = ITEMS.register("eagle_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.EAGLE, 0x462306, 0xdbdbdb, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> HIPOPPOTAMUS_SPAWN_EGG = ITEMS.register("hippopotamus_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.HIPPOPOTAMUS, 0xbbb595, 0xa8956e, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> JELLYFISH_SPAWN_EGG = ITEMS.register("jellyfish_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.JELLYFISH, 0x531c51, 0x3f163e, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> VULTURE_SPAWN_EGG = ITEMS.register("vulture_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.VULTURE, 0xc261303, 0x432041, new Item.Properties().tab(Animalia.TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> WHITE_SHARK_SPAWN_EGG = ITEMS.register("white_shark_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.WHITE_SHARK, 0xb4b4b4, 0x4a4a4a, new Item.Properties().tab(Animalia.TAB)));

}
