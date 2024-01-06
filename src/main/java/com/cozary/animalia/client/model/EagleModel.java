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

package com.cozary.animalia.client.model;

import com.cozary.animalia.entities.EagleEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class EagleModel<T extends EagleEntity> extends AgeableModel<T> {
    private final ModelRenderer cabeza;
    private final ModelRenderer cuerpo;
    private final ModelRenderer wingRight;
    private final ModelRenderer wingLeft;
    private final ModelRenderer legLeft;
    private final ModelRenderer legRight;

    public EagleModel() {
        texWidth = 32;
        texHeight = 32;

        cabeza = new ModelRenderer(this);
        cabeza.setPos(0.5F, 18.0F, -4.0F);
        cabeza.texOffs(0, 13).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
        cabeza.texOffs(9, 13).addBox(-0.5F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        cuerpo = new ModelRenderer(this);
        cuerpo.setPos(0.0F, 19.0F, 1.0F);
        cuerpo.texOffs(0, 0).addBox(-2.0F, -2.0F, -5.0F, 5.0F, 3.0F, 10.0F, 0.0F, false);

        wingRight = new ModelRenderer(this);
        wingRight.setPos(-2.0F, 17.0F, 1.0F);
        wingRight.texOffs(5, 5).addBox(0.0F, 0.0F, -3.0F, -6.0F, 1.0F, 3.0F, 0.0F, false);
        wingRight.texOffs(6, 4).addBox(0.0F, 0.0F, 0.0F, -5.0F, 1.0F, 1.0F, 0.0F, false);
        wingRight.texOffs(6, 4).addBox(0.0F, 0.0F, 1.0F, -4.0F, 1.0F, 1.0F, 0.0F, false);
        wingRight.texOffs(1, 5).addBox(-2.0F, 0.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        wingLeft = new ModelRenderer(this);
        wingLeft.setPos(3.0F, 17.0F, 1.0F);
        wingLeft.texOffs(4, 4).addBox(6.0F, 0.0F, -3.0F, -6.0F, 1.0F, 3.0F, 0.0F, false);
        wingLeft.texOffs(7, 3).addBox(5.0F, 0.0F, 0.0F, -5.0F, 1.0F, 1.0F, 0.0F, false);
        wingLeft.texOffs(17, 4).addBox(4.0F, 0.0F, 1.0F, -4.0F, 1.0F, 1.0F, 0.0F, false);
        wingLeft.texOffs(12, 3).addBox(0.0F, 0.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        legLeft = new ModelRenderer(this);
        legLeft.setPos(2.5F, 18.0F, 6.0F);
        legLeft.texOffs(20, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        legRight = new ModelRenderer(this);
        legRight.setPos(-1.5F, 18.0F, 6.0F);
        legRight.texOffs(20, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.cabeza.xRot = headPitch * ((float) Math.PI / 180F);
        this.cabeza.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.wingRight.zRot = MathHelper.sin(ageInTicks * 0.9F) * 0.5F;
        this.wingLeft.zRot = -this.wingRight.zRot;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.cabeza);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.cuerpo, this.wingRight, this.wingLeft, this.legLeft, this.legRight);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}