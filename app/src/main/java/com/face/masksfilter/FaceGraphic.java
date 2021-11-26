/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.face.masksfilter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.gms.vision.face.Face;
import com.face.masksfilter.camera.GraphicOverlay;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {

    private static final int MASK[] = {
            R.drawable.outline1,
            R.drawable.outline2,
            R.drawable.outline3,
            R.drawable.large1,
            R.drawable.large2,
            R.drawable.large3,
            R.drawable.large4,
            R.drawable.large5,
            R.drawable.large6,
            R.drawable.large7,
            R.drawable.large8,
            R.drawable.large9,
            R.drawable.large10,
            R.drawable.small1,
            R.drawable.small2,
            R.drawable.small3,
            R.drawable.small4,
            R.drawable.small5,
            R.drawable.small6,
            R.drawable.small7,
            R.drawable.small8,
            R.drawable.small9,
            R.drawable.small10,
            R.drawable.shield1,
            R.drawable.shield2,
            R.drawable.shield3,
            R.drawable.shield4,
    };

    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;
    private Bitmap bitmap;
    private Bitmap op;

    FaceGraphic(GraphicOverlay overlay,int c) {
        super(overlay);

        bitmap = BitmapFactory.decodeResource(getOverlay().getContext().getResources(),MASK[c]);
        op = bitmap;
    }

    void setId(int id) {
        mFaceId = id;
    }

    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face,int c) {
        mFace = face;
        bitmap = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), MASK[c]);
        op = bitmap;
        op = Bitmap.createScaledBitmap(op, (int) scaleX(face.getWidth()),
                (int) scaleY(((bitmap.getHeight() * face.getWidth()) / bitmap.getWidth())), false);
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if(face == null) return;
        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);

        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;

        canvas.drawBitmap(op, left, top+100, new Paint());
    }

}