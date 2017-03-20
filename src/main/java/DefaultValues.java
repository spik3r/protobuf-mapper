/**
 * Created by kai-tait on 20/03/2017.
 */
public class DefaultValues
{
    public static Object generateValue(final Class<?> type) {
        if (type.isAssignableFrom(byte.class)) {
            return (byte) 0;
        }
        if (type.isAssignableFrom(short.class)) {
            return (short) 0;
        }
        if (type.isAssignableFrom(int.class)) {
            return 0;
        }
        if (type.isAssignableFrom(long.class)) {
            return 0L;
        }
        if (type.isAssignableFrom(float.class)) {
            return 0.0f;
        }
        if (type.isAssignableFrom(double.class)) {
            return 0.0;
        }
        if (type.isAssignableFrom(boolean.class)) {
            return false;
        }
        return null;
    }
}
