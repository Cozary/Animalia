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

import com.cozary.animalia.entities.LilygatorEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class LilygatorModel<T extends LilygatorEntity> extends AgeableModel<T> {
    private final ModelRenderer bocadown;
    private final ModelRenderer bocaup;
    private final ModelRenderer body;
    private final ModelRenderer legfr;
    private final ModelRenderer legfl;
    private final ModelRenderer legbr;
    private final ModelRenderer legbl;
    private final ModelRenderer tails;
    private final ModelRenderer tail;
    private final ModelRenderer tail2;
    private final ModelRenderer tail3;

    public LilygatorModel() {
        texWidth = 128;
        texHeight = 128;

        bocadown = new ModelRenderer(this);
        bocadown.setPos(-1.0F, 20.0F, -11.0F);
        bocadown.texOffs(0, 43).addBox(-4.0F, -1.0F, -8.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
        bocadown.texOffs(24, 43).addBox(-2.0F, -1.0F, -12.0F, 4.0F, 1.0F, 3.0F, 0.0F, false);
        bocadown.texOffs(27, 35).addBox(-3.0F, -1.0F, -9.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(28, 47).addBox(-2.0F, -2.0F, -12.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(35, 43).addBox(-2.0F, -2.0F, -10.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(4, 43).addBox(-4.0F, -2.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(0, 43).addBox(-4.0F, -2.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(41, 32).addBox(-4.0F, -2.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(38, 31).addBox(-4.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(24, 47).addBox(1.0F, -2.0F, -10.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(0, 47).addBox(3.0F, -2.0F, -8.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(4, 45).addBox(3.0F, -2.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(0, 45).addBox(3.0F, -2.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(44, 31).addBox(3.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocadown.texOffs(4, 47).addBox(1.0F, -2.0F, -12.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        bocaup = new ModelRenderer(this);
        bocaup.setPos(-1.0F, 16.0F, -11.0F);
        bocaup.texOffs(0, 22).addBox(-3.0F, 0.0F, -9.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(27, 31).addBox(-2.0F, 0.0F, -12.0F, 4.0F, 1.0F, 3.0F, 0.0F, false);
        bocaup.texOffs(34, 34).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
        bocaup.texOffs(3, 36).addBox(-4.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(0, 35).addBox(-4.0F, 1.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(3, 34).addBox(-4.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(0, 33).addBox(-4.0F, 1.0F, -7.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(3, 32).addBox(-3.0F, 1.0F, -9.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(0, 31).addBox(-2.0F, 1.0F, -11.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(21, 23).addBox(-1.0F, 1.0F, -12.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(20, 16).addBox(0.0F, 1.0F, -12.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(20, 12).addBox(1.0F, 1.0F, -11.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(20, 8).addBox(2.0F, 1.0F, -9.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(20, 3).addBox(3.0F, 1.0F, -7.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(20, 20).addBox(3.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(0, 2).addBox(3.0F, 1.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bocaup.texOffs(0, 0).addBox(3.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 19.0F, 1.0F);
        body.texOffs(0, 0).addBox(-7.0F, -5.0F, -12.0F, 12.0F, 6.0F, 25.0F, 0.0F, false);
        body.texOffs(34, 37).addBox(-5.0F, -7.0F, -10.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        body.texOffs(14, 20).addBox(1.0F, -7.0F, -10.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        body.texOffs(12, 0).addBox(-3.0F, -6.0F, -10.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);

        legfr = new ModelRenderer(this);
        legfr.setPos(-6.0F, 20.0F, -4.0F);
        legfr.texOffs(0, 18).addBox(-6.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);
        legfr.texOffs(14, 16).addBox(-6.0F, 0.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        legfl = new ModelRenderer(this);
        legfl.setPos(4.0F, 20.0F, -4.0F);
        legfl.texOffs(14, 8).addBox(4.0F, 0.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        legfl.texOffs(0, 10).addBox(0.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);

        legbr = new ModelRenderer(this);
        legbr.setPos(-6.0F, 20.0F, 9.0F);
        legbr.texOffs(14, 12).addBox(-6.0F, 0.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        legbr.texOffs(0, 14).addBox(-6.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);

        legbl = new ModelRenderer(this);
        legbl.setPos(4.0F, 20.0F, 9.0F);
        legbl.texOffs(14, 4).addBox(4.0F, 0.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        legbl.texOffs(0, 6).addBox(0.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);

        tails = new ModelRenderer(this);
        tails.setPos(0.0F, 24.0F, 0.0F);


        tail = new ModelRenderer(this);
        tail.setPos(-1.0F, -7.0F, 14.0F);
        tails.addChild(tail);
        tail.texOffs(0, 31).addBox(-5.0F, -2.0F, 0.0F, 10.0F, 5.0F, 7.0F, 0.0F, false);

        tail2 = new ModelRenderer(this);
        tail2.setPos(0.0F, 2.0F, 7.0F);
        tail.addChild(tail2);
        tail2.texOffs(32, 43).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 7.0F, 0.0F, false);

        tail3 = new ModelRenderer(this);
        tail3.setPos(0.0F, 0.0F, 7.0F);
        tail2.addChild(tail3);
        tail3.texOffs(0, 0).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.bocaup.xRot = MathHelper.sin(ageInTicks * 0.1F) * 0.05F;
        this.bocadown.xRot = -MathHelper.sin(ageInTicks * 0.1F) * 0.05F;
        this.legbr.yRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legbl.yRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legfr.yRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legfl.yRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.tails.yRot = MathHelper.sin(ageInTicks * 0.1F) * 0.1F;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.bocadown);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.bocaup, this.tails, this.legfr, this.legfl, this.legbl, this.legbr);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}