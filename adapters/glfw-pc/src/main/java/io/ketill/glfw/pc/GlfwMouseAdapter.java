package io.ketill.glfw.pc;

import io.ketill.FeatureAdapter;
import io.ketill.MappedFeatureRegistry;
import io.ketill.MappingMethod;
import io.ketill.glfw.GlfwDeviceAdapter;
import io.ketill.glfw.WranglerMethod;
import io.ketill.pc.CursorStateZ;
import io.ketill.pc.Mouse;
import io.ketill.pc.MouseButton;
import io.ketill.pc.MouseClickZ;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2fc;

import java.util.Objects;

import static io.ketill.pc.Mouse.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * A {@link Mouse} adapter using GLFW.
 */
public class GlfwMouseAdapter extends GlfwDeviceAdapter<Mouse> {

    /**
     * @param ptr_glfwWindow the GLFW window pointer.
     * @return the wrangled mouse.
     * @throws NullPointerException if {@code ptr_glfwWindow} is a null
     *                              pointer (has a value of zero.)
     */
    @WranglerMethod
    public static @NotNull Mouse wrangle(long ptr_glfwWindow) {
        return new Mouse((d, r) -> new GlfwMouseAdapter(d, r,
                ptr_glfwWindow));
    }

    protected final double[] xPos;
    protected final double[] yPos;
    protected boolean wasCursorVisible;

    /**
     * Constructs a new {@code GlfwMouseAdapter}.
     *
     * @param mouse          the mouse which owns this adapter.
     * @param registry       the mouse's mapped feature registry.
     * @param ptr_glfwWindow the GLFW window pointer.
     * @throws NullPointerException if {@code mouse} or
     *                              {@code registry} are {@code null};
     *                              if {@code ptr_glfwWindow} is a null
     *                              pointer (has a value of zero.)
     */
    public GlfwMouseAdapter(@NotNull Mouse mouse,
                            @NotNull MappedFeatureRegistry registry,
                            long ptr_glfwWindow) {
        super(mouse, registry, ptr_glfwWindow);
        this.xPos = new double[1];
        this.yPos = new double[1];
    }


    /**
     * @param button     the mouse button to map.
     * @param glfwButton the GLFW button to map {@code button} to.
     * @throws NullPointerException     if {@code button} is {@code null}.
     * @throws IllegalArgumentException if {@code glfwButton} is negative.
     * @see #updateButton(MouseClickZ, int)
     */
    @MappingMethod
    protected void mapButton(@NotNull MouseButton button, int glfwButton) {
        Objects.requireNonNull(button, "button");
        if (glfwButton < 0) {
            throw new IllegalArgumentException("glfwButton < 0");
        }
        registry.mapFeature(button, glfwButton, this::updateButton);
    }

    @Override
    @MustBeInvokedByOverriders
    public void initAdapter() {
        this.mapButton(BUTTON_M1, GLFW_MOUSE_BUTTON_1);
        this.mapButton(BUTTON_M2, GLFW_MOUSE_BUTTON_2);
        this.mapButton(BUTTON_M3, GLFW_MOUSE_BUTTON_3);
        this.mapButton(BUTTON_M4, GLFW_MOUSE_BUTTON_4);
        this.mapButton(BUTTON_M5, GLFW_MOUSE_BUTTON_5);
        this.mapButton(BUTTON_M6, GLFW_MOUSE_BUTTON_6);
        this.mapButton(BUTTON_M7, GLFW_MOUSE_BUTTON_7);
        this.mapButton(BUTTON_M8, GLFW_MOUSE_BUTTON_8);

        registry.mapFeature(FEATURE_CURSOR, this::updateCursor);

        CursorStateZ cursor = registry.getInternalState(FEATURE_CURSOR);
        cursor.adapterCanSetVisible = true;
        cursor.adapterCanSetPosition = true;

        this.wasCursorVisible = cursor.visible;
    }

    /**
     * Updater for mouse buttons mapped via
     * {@link #mapButton(MouseButton, int)}.
     *
     * @param state      the button state.
     * @param glfwButton the GLFW button.
     */
    @FeatureAdapter
    protected void updateButton(@NotNull MouseClickZ state, int glfwButton) {
        int status = glfwGetMouseButton(ptr_glfwWindow, glfwButton);
        state.pressed = status >= GLFW_PRESS;
    }

    /**
     * Updater for {@link Mouse#FEATURE_CURSOR}.
     *
     * @param state the cursor state.
     */
    @FeatureAdapter
    protected void updateCursor(@NotNull CursorStateZ state) {
        Vector2fc requested = state.requestedPos;
        state.requestedPos = null;
        if (requested != null) {
            state.currentPos.set(requested);
            glfwSetCursorPos(ptr_glfwWindow, requested.x(), requested.y());
        } else {
            state.currentPos.x = (float) this.xPos[0];
            state.currentPos.y = (float) this.yPos[0];
        }

        if (!wasCursorVisible && state.visible) {
            glfwSetInputMode(ptr_glfwWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            this.wasCursorVisible = true;
        } else if (wasCursorVisible && !state.visible) {
            glfwSetInputMode(ptr_glfwWindow, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            this.wasCursorVisible = false;
        }
    }

    @Override
    @MustBeInvokedByOverriders
    protected void pollDevice() {
        glfwGetCursorPos(ptr_glfwWindow, xPos, yPos);
    }

    @Override
    protected final boolean isDeviceConnected() {
        return true; /* mouse is always connected */
    }

}
