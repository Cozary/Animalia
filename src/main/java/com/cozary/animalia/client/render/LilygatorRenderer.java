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

package com.cozary.animalia.client.render;

import com.cozary.animalia.Animalia;
import com.cozary.animalia.client.model.LilygatorModel;
import com.cozary.animalia.entities.LilygatorEntity;
import com.cozary.animalia.util.ClientEventBusSubscriber;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LilygatorRenderer extends MobRenderer<LilygatorEntity, LilygatorModel<LilygatorEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Animalia.MOD_ID, "textures/entity/lilygator.png");

    public LilygatorRenderer(EntityRendererProvider.Context context) {
        super(context, new LilygatorModel<>(context.bakeLayer(ClientEventBusSubscriber.LILYGATOR)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(LilygatorEntity entity) {
        return TEXTURE;
    }

}
