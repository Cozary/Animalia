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

import com.cozary.animalia.entities.WhiteSharkEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class WhiteSharkModel<T extends WhiteSharkEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer finRight;
    private final ModelRenderer cube_r1;
    private final ModelRenderer finRightB;
    private final ModelRenderer cube_r2;
    private final ModelRenderer finLeft;
    private final ModelRenderer cube_r3;
    private final ModelRenderer finLeftB;
    private final ModelRenderer cube_r4;
    private final ModelRenderer tail;
    private final ModelRenderer finUp;

    public WhiteSharkModel() {
        texWidth = 128;
        texHeight = 128;

        body = new ModelRenderer(this);
        body.setPos(-3.0F, 17.0F, -3.0F);
        body.texOffs(0, 0).addBox(-9.0F, -7.0F, -20.0F, 19.0F, 14.0F, 42.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(-3.0F, 17.0F, -23.0F);
        head.texOffs(52, 56).addBox(-7.0F, -5.0F, -8.0F, 15.0F, 11.0F, 8.0F, 0.0F, false);
        head.texOffs(39, 75).addBox(-5.0F, -1.0F, -21.0F, 11.0F, 3.0F, 13.0F, 0.0F, false);
        head.texOffs(0, 80).addBox(-5.0F, 2.0F, -19.0F, 11.0F, 3.0F, 11.0F, 0.0F, false);

        finRight = new ModelRenderer(this);
        finRight.setPos(-12.0F, 24.0F, -15.0F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, -3.0F, 2.0F);
        finRight.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.4363F, 0.0F);
        cube_r1.texOffs(74, 75).addBox(-13.0937F, -1.0F, -0.5774F, 14.0F, 2.0F, 10.0F, 0.0F, false);

        finRightB = new ModelRenderer(this);
        finRightB.setPos(-11.0F, 21.0F, 6.0F);


        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, 0.0F, 0.0F);
        finRightB.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.4363F, -0.1745F);
        cube_r2.texOffs(0, 31).addBox(-10.0F, -2.0F, 1.0F, 10.0F, 3.0F, 8.0F, -2.0F, false);

        finLeft = new ModelRenderer(this);
        finLeft.setPos(7.0F, 24.0F, -15.0F);


        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.0F, -3.0F, 2.0F);
        finLeft.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, -0.4363F, 0.0F);
        cube_r3.texOffs(80, 18).addBox(-1.0F, -1.0F, -1.0F, 14.0F, 2.0F, 10.0F, 0.0F, false);

        finLeftB = new ModelRenderer(this);
        finLeftB.setPos(2.0F, 21.0F, 6.0F);


        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.0F, 0.0F, 0.0F);
        finLeftB.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, -0.4363F, 0.1745F);
        cube_r4.texOffs(0, 20).addBox(3.0F, -2.0F, -1.0F, 10.0F, 3.0F, 8.0F, -2.0F, false);

        tail = new ModelRenderer(this);
        tail.setPos(-3.0F, 18.0F, 19.0F);
        tail.texOffs(0, 56).addBox(-8.0F, -6.0F, 0.0F, 16.0F, 12.0F, 10.0F, 0.0F, false);
        tail.texOffs(80, 0).addBox(-6.0F, -2.0F, 10.0F, 12.0F, 8.0F, 10.0F, 0.0F, false);
        tail.texOffs(40, 91).addBox(-4.0F, 1.0F, 20.0F, 8.0F, 5.0F, 4.0F, 0.0F, false);
        tail.texOffs(6, 94).addBox(-1.0F, -4.0F, 24.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);
        tail.texOffs(0, 94).addBox(-1.0F, -8.0F, 26.0F, 2.0F, 24.0F, 1.0F, 0.0F, false);
        tail.texOffs(36, 0).addBox(-1.0F, -10.0F, 27.0F, 2.0F, 28.0F, 1.0F, 0.0F, false);
        tail.texOffs(64, 91).addBox(-1.0F, -8.0F, 28.0F, 2.0F, 24.0F, 1.0F, 0.0F, false);

        finUp = new ModelRenderer(this);
        finUp.setPos(-2.5F, 11.0F, 0.0F);
        finUp.texOffs(0, 0).addBox(-1.5F, -6.0F, -7.0F, 3.0F, 5.0F, 15.0F, 0.0F, false);
        finUp.texOffs(74, 87).addBox(-1.5F, -7.0F, -5.0F, 3.0F, 1.0F, 13.0F, 0.0F, false);
        finUp.texOffs(80, 30).addBox(-1.5F, -8.0F, -3.0F, 3.0F, 1.0F, 11.0F, 0.0F, false);
        finUp.texOffs(93, 87).addBox(-1.5F, -9.0F, 0.0F, 3.0F, 1.0F, 8.0F, 0.0F, false);
        finUp.texOffs(42, 56).addBox(-1.5F, -10.0F, 3.0F, 3.0F, 1.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.tail.yRot = MathHelper.sin(ageInTicks * 0.2F) * 0.2F;
        this.tail.z = 17F;
        this.finLeft.yRot = MathHelper.sin(ageInTicks * 0.1F) * 0.1F;
        this.finLeftB.yRot = MathHelper.sin(ageInTicks * 0.1F) * 0.1F;
        this.finRight.yRot = MathHelper.sin(ageInTicks * 0.1F) * 0.1F;
        this.finRightB.yRot = MathHelper.sin(ageInTicks * 0.1F) * 0.1F;
        this.finUp.zRot = MathHelper.sin(ageInTicks * 0.1F) * 0.1F;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.finRight, this.finLeft, this.tail, this.finUp, this.finLeftB, this.finRightB);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}