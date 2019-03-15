package com.forestry.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

public class Image extends Texture {
    public static int creates,disposes;
    static String na;
    public Image(String internalPath) {
        super(internalPath);
        creates++;
        na = internalPath;
    }

    @Override
    public void dispose() {
        super.dispose();
        disposes++;
    }

    public Image(FileHandle file) {
        super(file);
    }

    public Image(FileHandle file, boolean useMipMaps) {
        super(file, useMipMaps);
    }

    public Image(FileHandle file, Pixmap.Format format, boolean useMipMaps) {
        super(file, format, useMipMaps);
    }

    public Image(Pixmap pixmap) {
        super(pixmap);
    }

    public Image(Pixmap pixmap, boolean useMipMaps) {
        super(pixmap, useMipMaps);
    }

    public Image(Pixmap pixmap, Pixmap.Format format, boolean useMipMaps) {
        super(pixmap, format, useMipMaps);
    }

    public Image(int width, int height, Pixmap.Format format) {
        super(width, height, format);
    }

    public Image(TextureData data) {
        super(data);
    }

    protected Image(int glTarget, int glHandle, TextureData data) {
        super(glTarget, glHandle, data);
    }
}
