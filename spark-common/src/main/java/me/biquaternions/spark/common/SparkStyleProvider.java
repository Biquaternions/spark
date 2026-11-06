package me.biquaternions.spark.common;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.lucko.spark.common.platform.serverconfig.ConfigParser;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

@NullMarked
public final class SparkStyleProvider {
    private SparkStyleProvider() {
    }

    private static final SparkStyleProvider INSTANCE = new SparkStyleProvider();

    private Supplier<SparkStyle> generator = DefaultStyle::new;
    private @Nullable SparkStyle style = null;
    private final AtomicBoolean registered = new AtomicBoolean(false);


    public static SparkStyleProvider getInstance() {
        return INSTANCE;
    }

    public void register(final Supplier<SparkStyle> styleSupplier) {
        Preconditions.checkNotNull(styleSupplier, "Cannot register a null supplier");
        if (this.registered.compareAndSet(true, true)) {
            throw new IllegalStateException("Already registered");
        }

        this.generator = styleSupplier;
    }

    public SparkStyle getStyle() {
        if (this.style == null) {
            synchronized (this) {
                this.style = this.generator.get();
                this.registered.set(true);
            }
        }
        return this.style;
    }

    private static final class DefaultStyle implements SparkStyle {

        private final Map<String, ConfigParser> emptyMap = ImmutableMap.of();
        private final List<String> emptyList = ImmutableList.of();

        @Override
        public TextColor getTitleColor() {
            return NamedTextColor.GOLD;
        }

        @Override
        public TextColor getDataColor() {
            return NamedTextColor.GREEN;
        }

        @Override
        public TextColor getPrefixColor() {
            return NamedTextColor.YELLOW;
        }

        @Override
        public String getPrefix() {
            return "⚡";
        }

        @Override
        public TextColor getHealthGoodColor() {
            return NamedTextColor.GREEN;
        }

        @Override
        public TextColor getHealthMediumColor() {
            return NamedTextColor.YELLOW;
        }

        @Override
        public TextColor getHealthBadColor() {
            return NamedTextColor.RED;
        }

        @Override
        public Map<String, ConfigParser> getPlatformConfigs() {
            return this.emptyMap;
        }

        @Override
        public List<String> getPlatformHiddenPaths() {
            return this.emptyList;
        }
    }

}
