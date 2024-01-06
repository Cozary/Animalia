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

import com.cozary.animalia.entities.JellyfishEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

import java.util.Arrays;

public class JellyfishModel<T extends JellyfishEntity> extends SegmentedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer[] tentacle = new ModelRenderer[4];
    private final ImmutableList<ModelRenderer> field_228296_f_;

    public JellyfishModel() {
        texWidth = 16;
        texHeight = 16;

        body = new ModelRenderer(this, 0, 0);
        body.y += 8.0F;
        body.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        for (int j = 0; j < this.tentacle.length; ++j) {
            this.tentacle[j] = new ModelRenderer(this, 8, 8);
            double d0 = (double) j * Math.PI * 2.0D / (double) this.tentacle.length;
            float f = (float) Math.cos(d0) * 2.5F;
            float f1 = (float) Math.sin(d0) * 2.5F;
            this.tentacle[j].addBox(-1.0F, 2.0F, -4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
            this.tentacle[j].x = f;
            this.tentacle[j].z = f1;
            this.tentacle[j].y = 6.0F;
            d0 = (double) j * Math.PI * -2.0D / (double) this.tentacle.length + (Math.PI / 2D);
            this.tentacle[j].yRot = (float) d0;
        }

        ImmutableList.Builder<ModelRenderer> builder = ImmutableList.builder();
        builder.add(this.body);
        builder.addAll(Arrays.asList(this.tentacle));
        this.field_228296_f_ = builder.build();
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return this.field_228296_f_;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        for (ModelRenderer modelrenderer : this.tentacle) {
            modelrenderer.xRot = ageInTicks;
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}