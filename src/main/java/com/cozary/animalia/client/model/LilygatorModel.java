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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class LilygatorModel<T extends LilygatorEntity> extends AgeableListModel<T> {
    private final ModelPart bocadown;
    private final ModelPart bocaup;
    private final ModelPart body;
    private final ModelPart legfr;
    private final ModelPart legfl;
    private final ModelPart legbr;
    private final ModelPart legbl;
    private final ModelPart tails;

    public LilygatorModel(ModelPart root) {
        this.bocadown = root.getChild("bocadown");
        this.bocaup = root.getChild("bocaup");
        this.body = root.getChild("body");
        this.legfr = root.getChild("legfr");
        this.legfl = root.getChild("legfl");
        this.legbr = root.getChild("legbr");
        this.legbl = root.getChild("legbl");
        this.tails = root.getChild("tails");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bocadown = partdefinition.addOrReplaceChild("bocadown", CubeListBuilder.create().texOffs(0, 43).addBox(-4.0F, -1.0F, -8.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 43).addBox(-2.0F, -1.0F, -12.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(27, 35).addBox(-3.0F, -1.0F, -9.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 47).addBox(-2.0F, -2.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 43).addBox(-2.0F, -2.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 43).addBox(-4.0F, -2.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-4.0F, -2.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(41, 32).addBox(-4.0F, -2.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 31).addBox(-4.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 47).addBox(1.0F, -2.0F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 47).addBox(3.0F, -2.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 45).addBox(3.0F, -2.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(3.0F, -2.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 31).addBox(3.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 47).addBox(1.0F, -2.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 20.0F, -11.0F));

        PartDefinition bocaup = partdefinition.addOrReplaceChild("bocaup", CubeListBuilder.create().texOffs(0, 22).addBox(-3.0F, 0.0F, -9.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(27, 31).addBox(-2.0F, 0.0F, -12.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 34).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(3, 36).addBox(-4.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 35).addBox(-4.0F, 1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(3, 34).addBox(-4.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 33).addBox(-4.0F, 1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(3, 32).addBox(-3.0F, 1.0F, -9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-2.0F, 1.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 23).addBox(-1.0F, 1.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 16).addBox(0.0F, 1.0F, -12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 12).addBox(1.0F, 1.0F, -11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 8).addBox(2.0F, 1.0F, -9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 3).addBox(3.0F, 1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 20).addBox(3.0F, 1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(3.0F, 1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(3.0F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 16.0F, -11.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -5.0F, -12.0F, 12.0F, 6.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(34, 37).addBox(-5.0F, -7.0F, -10.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 20).addBox(1.0F, -7.0F, -10.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 0).addBox(-3.0F, -6.0F, -10.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 1.0F));

        PartDefinition legfr = partdefinition.addOrReplaceChild("legfr", CubeListBuilder.create().texOffs(0, 18).addBox(-6.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 16).addBox(-6.0F, 0.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 20.0F, -4.0F));

        PartDefinition legfl = partdefinition.addOrReplaceChild("legfl", CubeListBuilder.create().texOffs(14, 8).addBox(4.0F, 0.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(0.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 20.0F, -4.0F));

        PartDefinition legbr = partdefinition.addOrReplaceChild("legbr", CubeListBuilder.create().texOffs(14, 12).addBox(-6.0F, 0.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-6.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 20.0F, 9.0F));

        PartDefinition legbl = partdefinition.addOrReplaceChild("legbl", CubeListBuilder.create().texOffs(14, 4).addBox(4.0F, 0.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(0.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 20.0F, 9.0F));

        PartDefinition tails = partdefinition.addOrReplaceChild("tails", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tail = tails.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 31).addBox(-5.0F, -2.0F, 0.0F, 10.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -7.0F, 14.0F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(32, 43).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 7.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.bocaup.xRot = Mth.sin(ageInTicks * 0.1F) * 0.05F;
        this.bocadown.xRot = -Mth.sin(ageInTicks * 0.1F) * 0.05F;
        this.legbr.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legbl.yRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legfr.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legfl.yRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.tails.yRot = Mth.sin(ageInTicks * 0.1F) * 0.1F;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.bocadown);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.bocaup, this.tails, this.legfr, this.legfl, this.legbl, this.legbr);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bocadown.render(poseStack, buffer, packedLight, packedOverlay);
        bocaup.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        legfr.render(poseStack, buffer, packedLight, packedOverlay);
        legfl.render(poseStack, buffer, packedLight, packedOverlay);
        legbr.render(poseStack, buffer, packedLight, packedOverlay);
        legbl.render(poseStack, buffer, packedLight, packedOverlay);
        tails.render(poseStack, buffer, packedLight, packedOverlay);
    }
}