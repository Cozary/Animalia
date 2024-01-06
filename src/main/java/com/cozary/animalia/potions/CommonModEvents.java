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

package com.cozary.animalia.potions;

import com.cozary.animalia.Animalia;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonModEvents {

    public static void onRegisterItems(Registrator<Item> reg) {
        reg.register(ResourceLocations.POTION_PARALIZE, new PotionParalizeItem(new Item.Properties().tab(Animalia.TAB)));
        reg.register(ResourceLocations.POTION_HEALTH_HINDER, new PotionHealthHinderItem(new Item.Properties().tab(Animalia.TAB)));
    }

    public static void onRegisterEffects(Registrator<Effect> reg) {
        reg.register(ResourceLocations.PARALIZE, new ParalizeEffect(EffectType.HARMFUL, 0xd4d139).addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -1F, AttributeModifier.Operation.MULTIPLY_TOTAL));
        reg.register(ResourceLocations.HEALTH_HINDER, new HealthHinderEffect(EffectType.HARMFUL, 0xa52437).addAttributeModifier(Attributes.MAX_HEALTH, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }


    public static void onCommonSetup(FMLCommonSetupEvent event) {
        BrewingRecipeRegistry.addRecipe(new PotionParalizeRecipe());
        BrewingRecipeRegistry.addRecipe(new PotionHealthHinderRecipe());
    }
}
