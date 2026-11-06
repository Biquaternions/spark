package me.biquaternions.spark.common;

import me.lucko.spark.common.platform.serverconfig.ConfigParser;
import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Map;

@NullMarked
public interface SparkStyle {

    TextColor getTitleColor();
    TextColor getDataColor();

    TextColor getPrefixColor();
    String getPrefix();

    TextColor getHealthGoodColor();
    TextColor getHealthMediumColor();
    TextColor getHealthBadColor();

    Map<String, ConfigParser> getPlatformConfigs();
    List<String> getPlatformHiddenPaths();

}
