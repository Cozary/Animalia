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

package com.cozary.animalia.util;

import com.cozary.animalia.Animalia;
import com.cozary.animalia.client.model.*;
import com.cozary.animalia.client.render.*;
import com.cozary.animalia.client.render.armor.BearHatModel;
import com.cozary.animalia.client.render.armor.BullHatModel;
import com.cozary.animalia.client.render.armor.FinsModel;
import com.cozary.animalia.client.render.armor.ShellModel;
import com.cozary.animalia.init.ModEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Animalia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    //RENDER THINGS
    public static ModelLayerLocation BEAR_ARMOR = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "bear_armor");
    public static ModelLayerLocation BULL_HAT = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "bull_hat");
    public static ModelLayerLocation FINS = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "fins");
    public static ModelLayerLocation SHELL = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "shell");

    public static ModelLayerLocation BROWN_BEAR = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "brown_near"), "brown_near");
    public static ModelLayerLocation BULL = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "bull"), "bull");
    public static ModelLayerLocation DIRTY_PIG = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "dirty_pig"), "dirty_pig");
    public static ModelLayerLocation EAGLE = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "eagle"), "eagle");
    public static ModelLayerLocation HIPPOPOTAMUS = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "hippopotamus"), "hippopotamus");
    public static ModelLayerLocation JELLYFISH = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "jellyfish"), "jellyfish");
    public static ModelLayerLocation LILYGATOR = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "lilygator"), "lilygator");
    public static ModelLayerLocation PLATYPUS = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "platypus"), "platypus");
    public static ModelLayerLocation SNAIL = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "snail"), "snail");
    public static ModelLayerLocation VULTURE = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "vulture"), "vulture");
    public static ModelLayerLocation WALRUS = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "walrus"), "walrus");
    public static ModelLayerLocation WHITE_SHARK = new ModelLayerLocation(new ResourceLocation(Animalia.MOD_ID, "white_shark"), "white_shark");

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.BROWN_BEAR.get(), BrownBearRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BULL.get(), BullRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.DIRTY_PIG.get(), DirtyPigRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.EAGLE.get(), EagleRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.HIPPOPOTAMUS.get(), HippopotamusRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.JELLYFISH.get(), JellyfishRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.PLATYPUS.get(), PlatypusRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SNAIL.get(), SnailRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.VULTURE.get(), VultureRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.WALRUS.get(), WalrusRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.WHITE_SHARK.get(), WhiteSharkRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.LILYGATOR.get(), LilygatorRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BROWN_BEAR, BrownBearModel::createBodyLayer);
        event.registerLayerDefinition(BULL, BullModel::createBodyLayer);
        event.registerLayerDefinition(DIRTY_PIG, DirtyPigModel::createBodyLayer);
        event.registerLayerDefinition(EAGLE, EagleModel::createBodyLayer);
        event.registerLayerDefinition(HIPPOPOTAMUS, HippopotamusModel::createBodyLayer);
        event.registerLayerDefinition(JELLYFISH, JellyfishModel::createBodyLayer);
        event.registerLayerDefinition(LILYGATOR, LilygatorModel::createBodyLayer);
        event.registerLayerDefinition(PLATYPUS, PlatypusModel::createBodyLayer);
        event.registerLayerDefinition(SNAIL, SnailModel::createBodyLayer);
        event.registerLayerDefinition(VULTURE, VultureModel::createBodyLayer);
        event.registerLayerDefinition(WALRUS, WalrusModel::createBodyLayer);
        event.registerLayerDefinition(WHITE_SHARK, WhiteSharkModel::createBodyLayer);


        event.registerLayerDefinition(BEAR_ARMOR, () -> BearHatModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(BULL_HAT, BullHatModel::createBodyLayer);
        event.registerLayerDefinition(FINS, FinsModel::createBodyLayer);
        event.registerLayerDefinition(SHELL, ShellModel::createBodyLayer);
    }
/*
    @SubscribeEvent
    public static void construct(EntityRenderersEvent.AddLayers event)
    {
        addLayerToEntity(event, EntityType.ARMOR_STAND);
        addLayerToEntity(event, EntityType.ZOMBIE);
        addLayerToEntity(event, EntityType.SKELETON);
        addLayerToEntity(event, EntityType.HUSK);
        addLayerToEntity(event, EntityType.DROWNED);
        addLayerToEntity(event, EntityType.STRAY);

        addLayerToPlayerSkin(event, "default");
        addLayerToPlayerSkin(event, "slim");
    }

    private static <T extends LivingEntity, M extends HumanoidModel<T>, R extends LivingEntityRenderer<T, M>> void addLayerToEntity(
            EntityRenderersEvent.AddLayers event, EntityType<? extends T> entityType)
    {
        R renderer = event.getRenderer(entityType);
        if (renderer != null) {
            renderer.addLayer(new BearHatRenderer<>(renderer));
            renderer.addLayer(new BullHatRenderer<>(renderer));
            renderer.addLayer(new FinsRenderer<>(renderer));
            renderer.addLayer(new ShellRenderer<>(renderer));
            }
    }

    private static void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName)
    {
        EntityRenderer<? extends Player> render = event.getSkin(skinName);
        if (render instanceof LivingEntityRenderer livingRenderer)
        {
            livingRenderer.addLayer(new BearHatRenderer(livingRenderer));
            livingRenderer.addLayer(new BullHatRenderer(livingRenderer));
            livingRenderer.addLayer(new FinsRenderer(livingRenderer));
            livingRenderer.addLayer(new ShellRenderer(livingRenderer));
        }
    }*/

}
