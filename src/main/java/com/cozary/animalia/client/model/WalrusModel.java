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

import com.cozary.animalia.entities.WalrusEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class WalrusModel<T extends WalrusEntity> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart legFrontRight;
    private final ModelPart legFrontLeft;

    public WalrusModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.legFrontRight = root.getChild("legFrontRight");
        this.legFrontLeft = root.getChild("legFrontLeft");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 32).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(4, 8).addBox(-2.0F, 0.0F, -6.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(1.0F, 0.0F, -6.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, -9.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.0F, -11.0F, 12.0F, 11.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-3.0F, -4.0F, 9.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 20.0F, 1.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.5F, 8.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 20.5F, 10.0F));

        PartDefinition legFrontRight = partdefinition.addOrReplaceChild("legFrontRight", CubeListBuilder.create().texOffs(44, 0).addBox(-5.0F, -0.5F, -6.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 23.5F, -7.0F));

        PartDefinition legFrontLeft = partdefinition.addOrReplaceChild("legFrontLeft", CubeListBuilder.create().texOffs(26, 44).addBox(0.0F, -0.5F, -6.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 23.5F, -8.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.y = netHeadYaw * ((float) Math.PI / 180F);
        this.legFrontRight.yRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.yRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        if (entityIn.isBaby()) {
            this.head.z = -6F;
            this.head.y = 13;
        }
        if (!entityIn.isBaby()) {
            this.head.z = -9;
            this.head.y = 12;
        }
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.tail, this.legFrontLeft, this.legFrontRight);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        tail.render(poseStack, buffer, packedLight, packedOverlay);
        legFrontRight.render(poseStack, buffer, packedLight, packedOverlay);
        legFrontLeft.render(poseStack, buffer, packedLight, packedOverlay);
    }
}