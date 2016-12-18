/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.facebook.fresco.samples.showcase;

import android.content.Context;
import android.support.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.fresco.samples.showcase.imageformat.svg.SvgDecoderExample;
import com.facebook.imagepipeline.decoder.ImageDecoderConfig;

/**
 * Helper class to add custom decoders and drawable factories if enabled.
 */
public class CustomImageFormatConfigurator {

  private static final String IMAGE_FORMAT_PREFS = "fresco_image_format_prefs";
  private static final String IMAGE_FORMAT_SVG_KEY = "svg";

  @Nullable
  public static ImageDecoderConfig createImageDecoderConfig(Context context) {
    if (isSvgEnabled(context)) {
      return ImageDecoderConfig.newBuilder()
          .addDecodingCapability(
              SvgDecoderExample.SVG_FORMAT,
              new SvgDecoderExample.SvgFormatChecker(),
              new SvgDecoderExample.SvgDecoder())
          .build();
    }
    return null;
  }

  public static void addCustomDrawableFactories(
      Context context,
      DraweeConfig.Builder draweeConfigBuilder) {
    if (isSvgEnabled(context)) {
      draweeConfigBuilder.addCustomDrawableFactory(new SvgDecoderExample.SvgDrawableFactory());
    }
  }

  public static boolean isSvgEnabled(Context context) {
    return getBoolean(context, IMAGE_FORMAT_SVG_KEY, false);
  }

  public static void setSvgEnabled(Context context, boolean svgEnabled) {
    setBoolean(context, IMAGE_FORMAT_SVG_KEY, svgEnabled);
  }

  private static boolean getBoolean(Context context, String key, boolean defaultValue) {
    return context.getSharedPreferences(IMAGE_FORMAT_PREFS, Context.MODE_PRIVATE)
        .getBoolean(key, defaultValue);
  }

  private static void setBoolean(Context context, String key, boolean value) {
    context.getSharedPreferences(IMAGE_FORMAT_PREFS, Context.MODE_PRIVATE)
        .edit()
        .putBoolean(key, value)
        .apply();
  }
}
