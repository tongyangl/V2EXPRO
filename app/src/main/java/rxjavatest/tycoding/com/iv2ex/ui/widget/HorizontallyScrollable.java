package rxjavatest.tycoding.com.iv2ex.ui.widget;

/**
 * Created by 佟杨 on 2017/4/9.
 */
public interface HorizontallyScrollable {

    /**
     * Return {@code true} if the component needs to receive right-to-left
     * touch movements.
     *
     * @param origX the raw x coordinate of the initial touch
     * @param origY the raw y coordinate of the initial touch
     */

    public boolean interceptMoveLeft(float origX, float origY);

    /**
     * Return {@code true} if the component needs to receive left-to-right
     * touch movements.
     *
     * @param origX the raw x coordinate of the initial touch
     * @param origY the raw y coordinate of the initial touch
     */
    public boolean interceptMoveRight(float origX, float origY);
}
