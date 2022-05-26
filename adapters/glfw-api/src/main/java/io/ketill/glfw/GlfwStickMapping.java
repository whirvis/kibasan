package io.ketill.glfw;

import io.ketill.MappingType;
import io.ketill.controller.AnalogStick;

/**
 * A mapping for an {@link AnalogStick} used by {@link GlfwJoystickAdapter}.
 *
 * @see GlfwJoystickAdapter#mapStick(AnalogStick, GlfwStickMapping)
 */
@MappingType
public class GlfwStickMapping {

    /**
     * The GLFW axis for the X-axis.
     */
    public final int glfwXAxis;

    /**
     * The GLFW axis for the Y-axis.
     */
    public final int glfwYAxis;

    /**
     * The GLFW button for the thumb button.
     * <p>
     * <b>Note:</b> If there exists no thumb button for this mapping,
     * the value of this field will be {@code -1}.
     *
     * @see #hasZButton
     */
    public final int glfwZButton;

    /**
     * {@code true} if this mapping has a thumb button,
     * {@code false} otherwise.
     */
    public final boolean hasZButton;

    private GlfwStickMapping(int glfwXAxis, int glfwYAxis, int glfwZButton,
                             boolean hasZButton) {
        if (glfwXAxis < 0) {
            throw new IllegalArgumentException("glfwXAxis < 0");
        } else if (glfwYAxis < 0) {
            throw new IllegalArgumentException("glfwYAxis < 0");
        } else if (glfwZButton < 0 && hasZButton) {
            throw new IllegalArgumentException("glfwZButton < 0");
        }

        this.glfwXAxis = glfwXAxis;
        this.glfwYAxis = glfwYAxis;
        this.glfwZButton = glfwZButton;
        this.hasZButton = hasZButton;
    }

    /**
     * @param glfwXAxis   the GLFW axis for the X-axis.
     * @param glfwYAxis   the GLFW axis for the Y-axis.
     * @param glfwZButton the GLFW button for the thumb button.
     * @throws IllegalArgumentException if {@code glfwXAxis},
     *                                  {@code glfwYAxis}, or
     *                                  {@code glfwZButton} are negative.
     */
    public GlfwStickMapping(int glfwXAxis, int glfwYAxis, int glfwZButton) {
        this(glfwXAxis, glfwYAxis, glfwZButton, true);
    }

    /**
     * @param glfwAxisX the GLFW axis for the X-axis.
     * @param glfwAxisY the GLFW axis for the Y-axis.
     * @throws IllegalArgumentException if {@code glfwXAxis} or
     *                                  {@code glfwYAxis} are negative.
     */
    public GlfwStickMapping(int glfwAxisX, int glfwAxisY) {
        this(glfwAxisX, glfwAxisY, -1, false);
    }

}
