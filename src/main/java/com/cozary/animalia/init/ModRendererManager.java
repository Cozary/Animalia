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

import com.cozary.animalia.client.render.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class ModRendererManager {
    public static void init() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DIRTY_PIG.get(), manager -> new DirtyPigRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BROWN_BEAR.get(), manager -> new BrownBearRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BULL.get(), manager -> new BullRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.EAGLE.get(), manager -> new EagleRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.HIPPOPOTAMUS.get(), manager -> new HippopotamusRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.LILYGATOR.get(), manager -> new LilygatorRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.JELLYFISH.get(), manager -> new JellyfishRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.PLATYPUS.get(), manager -> new PlatypusRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SNAIL.get(), manager -> new SnailRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.VULTURE.get(), manager -> new VultureRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WALRUS.get(), manager -> new WalrusRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WHITE_SHARK.get(), manager -> new WhiteSharkRenderer(manager));

    }

}
