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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class EagleModel<T extends EagleEntity> extends AgeableListModel<T> {
    private final ModelPart cabeza;
    private final ModelPart cuerpo;
    private final ModelPart wingRight;
    private final ModelPart wingLeft;
    private final ModelPart legLeft;
    private final ModelPart legRight;

    public EagleModel(ModelPart root) {
        this.cabeza = root.getChild("cabeza");
        this.cuerpo = root.getChild("cuerpo");
        this.wingRight = root.getChild("wingRight");
        this.wingLeft = root.getChild("wingLeft");
        this.legLeft = root.getChild("legLeft");
        this.legRight = root.getChild("legRight");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition cabeza = partdefinition.addOrReplaceChild("cabeza", CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(9, 13).addBox(-0.5F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 18.0F, -4.0F));

        PartDefinition cuerpo = partdefinition.addOrReplaceChild("cuerpo", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -5.0F, 5.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 1.0F));

        PartDefinition wingRight = partdefinition.addOrReplaceChild("wingRight", CubeListBuilder.create().texOffs(5, 5).addBox(0.0F, 0.0F, -3.0F, -6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(0.0F, 0.0F, 0.0F, -5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(0.0F, 0.0F, 1.0F, -4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 5).addBox(-2.0F, 0.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 17.0F, 1.0F));

        PartDefinition wingLeft = partdefinition.addOrReplaceChild("wingLeft", CubeListBuilder.create().texOffs(4, 4).addBox(6.0F, 0.0F, -3.0F, -6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(7, 3).addBox(5.0F, 0.0F, 0.0F, -5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 4).addBox(4.0F, 0.0F, 1.0F, -4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 3).addBox(0.0F, 0.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 17.0F, 1.0F));

        PartDefinition legLeft = partdefinition.addOrReplaceChild("legLeft", CubeListBuilder.create().texOffs(20, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 18.0F, 6.0F));

        PartDefinition legRight = partdefinition.addOrReplaceChild("legRight", CubeListBuilder.create().texOffs(20, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 18.0F, 6.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.cabeza.xRot = headPitch * ((float) Math.PI / 180F);
        this.cabeza.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.wingRight.zRot = Mth.sin(ageInTicks * 0.9F) * 0.5F;
        this.wingLeft.zRot = -this.wingRight.zRot;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.cabeza);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.cuerpo, this.wingRight, this.wingLeft, this.legLeft, this.legRight);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        cabeza.render(poseStack, buffer, packedLight, packedOverlay);
        cuerpo.render(poseStack, buffer, packedLight, packedOverlay);
        wingRight.render(poseStack, buffer, packedLight, packedOverlay);
        wingLeft.render(poseStack, buffer, packedLight, packedOverlay);
        legLeft.render(poseStack, buffer, packedLight, packedOverlay);
        legRight.render(poseStack, buffer, packedLight, packedOverlay);
    }
}