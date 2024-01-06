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

package com.cozary.animalia.client.render.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BearHatModel extends BipedModel<LivingEntity> {

    public BearHatModel(float modelSize) {
        super(modelSize, 0.0F, 64, 64);

        ModelRenderer bearHead = new ModelRenderer(this);
        hat.addChild(bearHead);
        bearHead.setPos(1.0F, -4.0F, 3.0F);
        bearHead.texOffs(0, 0).addBox(-6.0F, -5.0F, -8.0F, 10.0F, 9.0F, 10.0F, 0.0F, false);
        bearHead.texOffs(0, 19).addBox(-5.0F, 0.0F, -12.0F, 8.0F, 4.0F, 5.0F, 0.0F, false);
        bearHead.texOffs(0, 4).addBox(-7.0F, -7.0F, -5.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
        bearHead.texOffs(0, 0).addBox(2.0F, -7.0F, -5.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
