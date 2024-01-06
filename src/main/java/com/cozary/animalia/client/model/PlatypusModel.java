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

import com.cozary.animalia.entities.PlatypusEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class PlatypusModel<T extends PlatypusEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer pico;
    private final ModelRenderer legFrontRight;
    private final ModelRenderer legFrontLeft;
    private final ModelRenderer legBackRight;
    private final ModelRenderer legBackLeft;
    private final ModelRenderer tail;
    private final ModelRenderer cube_r1;

    public PlatypusModel() {
        texWidth = 32;
        texHeight = 32;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 22.0F, 0.0F);
        setRotationAngle(body, 0.0F, -1.5708F, 0.0F);
        body.texOffs(0, 0).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 3.0F, 4.0F, 0.0F, false);
        body.texOffs(4, 12).addBox(-5.0F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 12).addBox(-5.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        pico = new ModelRenderer(this);
        pico.setPos(0.0F, 22.0F, -4.0F);
        setRotationAngle(pico, 0.0F, -1.5708F, 0.0F);
        pico.texOffs(0, 7).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.0F, false);
        pico.texOffs(10, 11).addBox(-4.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

        legFrontRight = new ModelRenderer(this);
        legFrontRight.setPos(-1.0F, 23.0F, -2.0F);
        setRotationAngle(legFrontRight, 0.0F, -1.5708F, 0.0F);
        legFrontRight.texOffs(0, 9).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        legFrontLeft = new ModelRenderer(this);
        legFrontLeft.setPos(1.0F, 24.0F, -2.0F);
        setRotationAngle(legFrontLeft, 0.0F, -1.5708F, 0.0F);
        legFrontLeft.texOffs(0, 7).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        legBackRight = new ModelRenderer(this);
        legBackRight.setPos(0.0F, 23.0F, 2.0F);
        setRotationAngle(legBackRight, 0.0F, -1.5708F, 0.0F);
        legBackRight.texOffs(0, 0).addBox(0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        legBackRight.texOffs(7, 8).addBox(-1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F, false);

        legBackLeft = new ModelRenderer(this);
        legBackLeft.setPos(1.0F, 23.0F, 2.0F);
        setRotationAngle(legBackLeft, 0.0F, -1.5708F, 0.0F);
        legBackLeft.texOffs(0, 2).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        legBackLeft.texOffs(7, 7).addBox(-1.0F, 1.0F, -1.0F, 1.0F, 0.0F, 1.0F, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setPos(0.0F, 22.0F, 4.0F);
        setRotationAngle(tail, 0.0F, -1.5708F, 0.0873F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(1.0F, 0.0F, 0.0F);
        tail.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.0873F, 0.0F, 0.0F);
        cube_r1.texOffs(8, 8).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.legBackRight.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legBackLeft.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontRight.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.pico, this.legBackLeft, this.tail, this.legBackRight, this.legFrontLeft, this.legFrontRight);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}