package io.ketill.nx;

import io.ketill.AdapterSupplier;
import io.ketill.FeaturePresent;
import io.ketill.FeatureState;
import io.ketill.controller.AnalogStick;
import io.ketill.controller.AnalogTrigger;
import io.ketill.controller.Button1bc;
import io.ketill.controller.DeviceButton;
import io.ketill.controller.Trigger1fc;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3fc;

import java.util.Objects;

/**
 * A Nintendo Switch right Joy-Con.
 */
public class NxRightJoyCon extends NxJoyCon {

    /* @formatter:off */
    @FeaturePresent
    public static final @NotNull DeviceButton
            BUTTON_A = new DeviceButton("a"),
            BUTTON_X = new DeviceButton("x"),
            BUTTON_B = new DeviceButton("b"),
            BUTTON_Y = new DeviceButton("y"),
            BUTTON_PLUS = new DeviceButton("plus"),
            BUTTON_R_THUMB = new DeviceButton("r_thumb"),
            BUTTON_HOME = new DeviceButton("home"),
            BUTTON_R = new DeviceButton("r"),
            BUTTON_ZR = new DeviceButton("zr");

    @FeaturePresent
    public static final @NotNull AnalogStick
            STICK_RS = new AnalogStick("rs");

    @FeaturePresent
    public static final @NotNull AnalogTrigger
            TRIGGER_ZR = new AnalogTrigger("zr");
    /* @formatter:on */

    /* @formatter:off */
    @FeatureState
    public final @NotNull Button1bc
            a = this.getState(BUTTON_A),
            x = this.getState(BUTTON_X),
            b = this.getState(BUTTON_B),
            y = this.getState(BUTTON_Y),
            plus = this.getState(BUTTON_PLUS),
            rThumb = this.getState(BUTTON_R_THUMB),
            home = this.getState(BUTTON_HOME),
            r = this.getState(BUTTON_R),
            zr = this.getState(BUTTON_ZR);

    @FeatureState
    public final @NotNull Vector3fc
            rs = Objects.requireNonNull(super.rs);

    @FeatureState
    public final @NotNull Trigger1fc
            rt = Objects.requireNonNull(super.rt);
    /* @formatter:on */

    /**
     * @param adapterSupplier the right Joy-Con adapter supplier.
     * @throws NullPointerException if {@code adapterSupplier} is
     *                              {@code null}; if the adapter given by
     *                              {@code adapterSupplier} is {@code null}.
     */
    public NxRightJoyCon(@NotNull AdapterSupplier<NxRightJoyCon> adapterSupplier) {
        super("nx_joycon_right", adapterSupplier,
                null, STICK_RS, null, TRIGGER_ZR);
    }

}
