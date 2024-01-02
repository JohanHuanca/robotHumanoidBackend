package com.steelroyal.robothumanoid.motion.domain.service;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public interface ImageService {
    void processImage(BufferedImage image);
    void setOnImageProcessed(Consumer<BufferedImage> onImageProcessed);
}
