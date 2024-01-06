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

package com.cozary.animalia;


import com.cozary.animalia.biomes.core.registry.ModFeatures;
import com.cozary.animalia.biomes.features.ModDecorators;
import com.cozary.animalia.biomes.features.custom.MudLakeFeature;
import com.cozary.animalia.biomes.features.custom.WaterSpringExtraFeature;
import com.cozary.animalia.init.*;
import com.cozary.animalia.potions.CommonModEvents;
import com.cozary.animalia.potions.Registrator;
import com.cozary.animalia.util.AnimaliaConfig;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

@Mod("animalia")
@Mod.EventBusSubscriber(modid = Animalia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Animalia {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "animalia";
    public static final CreativeModeTab TAB = new CreativeModeTab("animaliaTab") {
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MUD.get());
        }
    };

    public Animalia() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::doClientStuff);

        eventBus.addGenericListener(Item.class, getRegistrator(CommonModEvents::onRegisterItems));
        eventBus.addGenericListener(MobEffect.class, getRegistrator(CommonModEvents::onRegisterEffects));

        eventBus.addListener(CommonModEvents::onCommonSetup);

        ModFluids.FLUIDS.register(eventBus);
        ModFluids.BLOCKS.register(eventBus);
        ModEntityTypes.ENTITY_TYPES.register(eventBus);
        ModSound.SOUNDS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModFeatures.FEATURES.register(eventBus);
        ModDecorators.DECORATORS.register(eventBus);


        eventBus.addListener(EventPriority.LOWEST, this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AnimaliaConfig.spec);


        MinecraftForge.EVENT_BUS.register(this);
    }

    public static <T extends IForgeRegistryEntry<T>> Consumer<RegistryEvent.Register<T>> getRegistrator(Consumer<Registrator<T>> consumer) {
        return event -> consumer.accept(new Registrator<>(event.getRegistry()));
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JELLY_BLOCK.get(), RenderType.translucent());
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MudLakeFeature.registerConfiguredFeatures();
            WaterSpringExtraFeature.registerConfiguredFeatures();
            ModFeatures.Configured.registerConfiguredFeatures();
        });
    }

}
