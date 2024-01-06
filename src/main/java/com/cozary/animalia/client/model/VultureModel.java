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

import com.cozary.animalia.entities.VultureEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class VultureModel<T extends VultureEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer wingLeft;
    private final ModelRenderer wingRight;
    private final ModelRenderer head;
    private final ModelRenderer legLeft;
    private final ModelRenderer legRight;

    public VultureModel() {
        texWidth = 64;
        texHeight = 64;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 15.0F, 0.0F);
        body.texOffs(0, 0).addBox(-4.0F, -1.0F, -6.0F, 8.0F, 4.0F, 12.0F, 0.0F, false);

        wingLeft = new ModelRenderer(this);
        wingLeft.setPos(4.0F, 15.0F, 0.0F);
        wingLeft.texOffs(0, 16).addBox(0.0F, -1.0F, -4.0F, 9.0F, 1.0F, 4.0F, 0.0F, false);
        wingLeft.texOffs(22, 18).addBox(0.0F, -1.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        wingLeft.texOffs(22, 16).addBox(0.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        wingLeft.texOffs(0, 28).addBox(0.0F, -1.0F, 1.0F, 7.0F, 1.0F, 1.0F, 0.0F, false);
        wingLeft.texOffs(28, 2).addBox(0.0F, -1.0F, 2.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        wingLeft.texOffs(0, 0).addBox(0.0F, -1.0F, 3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        wingLeft.texOffs(28, 10).addBox(0.0F, -1.0F, 4.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        wingRight = new ModelRenderer(this);
        wingRight.setPos(-4.0F, 15.0F, 0.0F);
        wingRight.texOffs(0, 21).addBox(-9.0F, -1.0F, -4.0F, 9.0F, 1.0F, 4.0F, 0.0F, false);
        wingRight.texOffs(0, 26).addBox(-8.0F, -1.0F, -5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        wingRight.texOffs(22, 21).addBox(-8.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        wingRight.texOffs(28, 0).addBox(-7.0F, -1.0F, 1.0F, 7.0F, 1.0F, 1.0F, 0.0F, false);
        wingRight.texOffs(28, 4).addBox(-6.0F, -1.0F, 2.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        wingRight.texOffs(0, 2).addBox(-5.0F, -1.0F, 3.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
        wingRight.texOffs(5, 30).addBox(-4.0F, -1.0F, 4.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 15.0F, -6.0F);
        head.texOffs(23, 23).addBox(-2.0F, -3.0F, -8.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
        head.texOffs(15, 27).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);
        head.texOffs(0, 8).addBox(-1.0F, 1.0F, -6.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);
        head.texOffs(0, 4).addBox(-1.0F, 0.0F, -11.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);
        head.texOffs(24, 30).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(30, 30).addBox(-1.0F, 1.0F, -11.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        legLeft = new ModelRenderer(this);
        legLeft.setPos(2.0F, 18.0F, 0.0F);
        legLeft.texOffs(0, 30).addBox(0.0F, 2.0F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        legLeft.texOffs(0, 21).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        legLeft.texOffs(7, 8).addBox(0.0F, 3.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        legRight = new ModelRenderer(this);
        legRight.setPos(-2.0F, 18.0F, 0.0F);
        legRight.texOffs(28, 6).addBox(-1.0F, 2.0F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        legRight.texOffs(0, 16).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        legRight.texOffs(7, 4).addBox(-1.0F, 3.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.wingRight.zRot = MathHelper.sin(ageInTicks * 0.7F) * 0.5F;
        this.wingLeft.zRot = -this.wingRight.zRot;
        this.legRight.yRot = MathHelper.sin(ageInTicks * 0.2F) * 0.2F;
        this.legLeft.yRot = -this.legRight.yRot;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.wingLeft, this.wingRight, this.legLeft, this.legRight);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}