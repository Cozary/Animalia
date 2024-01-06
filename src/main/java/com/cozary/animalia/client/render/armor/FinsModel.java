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

public class FinsModel extends BipedModel<LivingEntity> {

    public FinsModel(float modelSize) {
        super(modelSize, 0.0F, 64, 64);

        ModelRenderer fins = new ModelRenderer(this);
        body.addChild(fins);
        fins.setPos(0.0F, 7.0F, 9.0F);
        fins.texOffs(16, 32).addBox(-1.0F, -4.0F, -7.0F, 1.0F, 9.0F, 1.0F, 0.0F, false);
        fins.texOffs(0, 0).addBox(-1.0F, -3.0F, -6.0F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        fins.texOffs(4, 16).addBox(-1.0F, -2.0F, -5.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        fins.texOffs(28, 0).addBox(-1.0F, -1.0F, -4.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        fins.texOffs(24, 5).addBox(-1.0F, 1.0F, -3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        fins.texOffs(36, 16).addBox(8.0F, -5.0F, -9.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        fins.texOffs(0, 16).addBox(9.0F, -5.0F, -9.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        fins.texOffs(24, 16).addBox(10.0F, -5.0F, -9.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        fins.texOffs(28, 16).addBox(11.0F, -5.0F, -9.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        fins.texOffs(32, 16).addBox(-9.0F, -5.0F, -9.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        fins.texOffs(4, 0).addBox(-10.0F, -5.0F, -9.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        fins.texOffs(24, 0).addBox(-11.0F, -5.0F, -9.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        fins.texOffs(27, 20).addBox(-12.0F, -5.0F, -9.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
