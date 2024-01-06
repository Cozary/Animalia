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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class VultureModel<T extends VultureEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart wingLeft;
    private final ModelPart wingRight;
    private final ModelPart head;
    private final ModelPart legLeft;
    private final ModelPart legRight;

    public VultureModel(ModelPart root) {
        this.body = root.getChild("body");
        this.wingLeft = root.getChild("wingLeft");
        this.wingRight = root.getChild("wingRight");
        this.head = root.getChild("head");
        this.legLeft = root.getChild("legLeft");
        this.legRight = root.getChild("legRight");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -6.0F, 8.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition wingLeft = partdefinition.addOrReplaceChild("wingLeft", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -1.0F, -4.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 18).addBox(0.0F, -1.0F, -5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 16).addBox(0.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(0.0F, -1.0F, 1.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 2).addBox(0.0F, -1.0F, 2.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, -1.0F, 3.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 10).addBox(0.0F, -1.0F, 4.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, 0.0F));

        PartDefinition wingRight = partdefinition.addOrReplaceChild("wingRight", CubeListBuilder.create().texOffs(0, 21).addBox(-9.0F, -1.0F, -4.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-8.0F, -1.0F, -5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 21).addBox(-8.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(-7.0F, -1.0F, 1.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 4).addBox(-6.0F, -1.0F, 2.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-5.0F, -1.0F, 3.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 30).addBox(-4.0F, -1.0F, 4.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 15.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(23, 23).addBox(-2.0F, -3.0F, -8.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(15, 27).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-1.0F, 1.0F, -6.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-1.0F, 0.0F, -11.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 30).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 30).addBox(-1.0F, 1.0F, -11.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, -6.0F));

        PartDefinition legLeft = partdefinition.addOrReplaceChild("legLeft", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, 2.0F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 8).addBox(0.0F, 3.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 18.0F, 0.0F));

        PartDefinition legRight = partdefinition.addOrReplaceChild("legRight", CubeListBuilder.create().texOffs(28, 6).addBox(-1.0F, 2.0F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 4).addBox(-1.0F, 3.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 18.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.wingRight.zRot = Mth.sin(ageInTicks * 0.7F) * 0.5F;
        this.wingLeft.zRot = -this.wingRight.zRot;
        this.legRight.yRot = Mth.sin(ageInTicks * 0.2F) * 0.2F;
        this.legLeft.yRot = -this.legRight.yRot;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.wingLeft, this.wingRight, this.legLeft, this.legRight);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, buffer, packedLight, packedOverlay);
        wingLeft.render(poseStack, buffer, packedLight, packedOverlay);
        wingRight.render(poseStack, buffer, packedLight, packedOverlay);
        head.render(poseStack, buffer, packedLight, packedOverlay);
        legLeft.render(poseStack, buffer, packedLight, packedOverlay);
        legRight.render(poseStack, buffer, packedLight, packedOverlay);
    }
}