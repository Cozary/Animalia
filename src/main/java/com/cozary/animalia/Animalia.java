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
import com.cozary.animalia.client.proxy.ClientProxy;
import com.cozary.animalia.client.proxy.IProxy;
import com.cozary.animalia.client.proxy.ServerProxy;
import com.cozary.animalia.entities.*;
import com.cozary.animalia.init.*;
import com.cozary.animalia.potions.CommonModEvents;
import com.cozary.animalia.potions.Registrator;
import com.cozary.animalia.util.AnimaliaConfig;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
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
    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final ItemGroup TAB = new ItemGroup("animaliaTab") {
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MUD.get());
        }
    };

    public Animalia() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntityTypes.ENTITY_TYPES.register(eventBus);

        eventBus.addListener(this::doClientStuff);

        eventBus.addGenericListener(Item.class, getRegistrator(CommonModEvents::onRegisterItems));
        eventBus.addGenericListener(Effect.class, getRegistrator(CommonModEvents::onRegisterEffects));

        eventBus.addListener(CommonModEvents::onCommonSetup);

        ModSound.SOUNDS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModFluids.FLUIDS.register(eventBus);
        ModFeatures.FEATURES.register(eventBus);
        ModDecorators.DECORATORS.register(eventBus);

        eventBus.addListener(EventPriority.LOWEST, this::setup);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AnimaliaConfig.spec);
        //DataUtil.registerConfigCondition(MOD_ID, AnimaliaConfig.SPAWN);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AnimaliaConfig.spec);


        MinecraftForge.EVENT_BUS.register(this);
    }

    public static <T extends IForgeRegistryEntry<T>> Consumer<RegistryEvent.Register<T>> getRegistrator(Consumer<Registrator<T>> consumer) {
        return event -> consumer.accept(new Registrator<>(event.getRegistry()));
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.JELLY_BLOCK.get(), RenderType.translucent());
        ModRendererManager.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MudLakeFeature.registerConfiguredFeatures();
            WaterSpringExtraFeature.registerConfiguredFeatures();
        });
        DeferredWorkQueue.runLater(() -> {

            ModFeatures.Configured.registerConfiguredFeatures();

            GlobalEntityTypeAttributes.put(ModEntityTypes.DIRTY_PIG.get(), DirtyPigEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.BROWN_BEAR.get(), BrownBearEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.SNAIL.get(), SnailEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.WALRUS.get(), WalrusEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.PLATYPUS.get(), PlatypusEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.BULL.get(), BullEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.EAGLE.get(), EagleEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.HIPPOPOTAMUS.get(), HippopotamusEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.LILYGATOR.get(), LilygatorEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.JELLYFISH.get(), JellyfishEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.VULTURE.get(), VultureEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.WHITE_SHARK.get(), WhiteSharkEntity.setCustomAttributes().build());
        });
    }


}
