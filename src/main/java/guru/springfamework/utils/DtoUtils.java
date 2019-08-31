package guru.springfamework.utils;

import java.util.function.Consumer;

public class DtoUtils {

    public static <T> void setIfNotNull(final Consumer<T> setter, final T value) {
        if(value != null) {
            setter.accept(value);
        }
    }
}
