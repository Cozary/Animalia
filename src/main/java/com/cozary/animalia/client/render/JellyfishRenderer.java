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
import com.cozary.animalia.client.model.JellyfishModel;
import com.cozary.animalia.entities.JellyfishEntity;
import com.cozary.animalia.util.ClientEventBusSubscriber;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class JellyfishRenderer extends MobRenderer<JellyfishEntity, JellyfishModel<JellyfishEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Animalia.MOD_ID, "textures/entity/jellyfish.png");

    public JellyfishRenderer(EntityRendererProvider.Context context) {
        super(context, new JellyfishModel<>(context.bakeLayer(ClientEventBusSubscriber.JELLYFISH)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(JellyfishEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(JellyfishEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        float f = Mth.lerp(partialTicks, entityLiving.prevSquidPitch, entityLiving.squidPitch);
        float f1 = Mth.lerp(partialTicks, entityLiving.prevSquidYaw, entityLiving.squidYaw);
        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
        matrixStackIn.translate(0.0D, -1.2F, 0.0D);
    }

    @Override
    protected float getBob(JellyfishEntity livingBase, float partialTicks) {
        return Mth.lerp(partialTicks, livingBase.lastTentacleAngle, livingBase.tentacleAngle);
    }

}
