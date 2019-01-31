package ca.toropov.api.teamwork.command;

import ca.toropov.api.teamwork.TeamworkAPI;
import ca.toropov.api.teamwork.util.JsonBuild;
import ca.toropov.api.teamwork.util.JsonBuildClass;
import ca.toropov.api.teamwork.util.Pair;
import ca.toropov.api.teamwork.util.RequestMethod;
import com.grack.nanojson.JsonStringWriter;
import com.grack.nanojson.JsonWriter;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Author: toropov
 * Date: 1/26/2019
 */
public interface Command {

    String getRequestUrl();

    RequestMethod getRequestMethod();

    default String getJson() {
        JsonBuildClass jsonBuildClass = getClass().getDeclaredAnnotation(JsonBuildClass.class);
        if (jsonBuildClass == null) {
            throw new NullPointerException("Missing " + JsonBuild.class.getName() + " annotation");
        }

        JsonStringWriter json = JsonWriter.string()
                .object()
                .object(jsonBuildClass.value());

        Arrays.stream(getClass().getDeclaredFields())
                .flatMap(field -> {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value;
                    try {
                        value = field.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return Stream.empty();
                    }
                    if (value == null) {
                        return Stream.empty();
                    }

                    JsonBuild jsonBuild = field.getDeclaredAnnotation(JsonBuild.class);
                    if (jsonBuild != null) {
                        if (jsonBuild.ignore()) {
                            return Stream.empty();
                        }

                        if (!jsonBuild.value().isEmpty()) {
                            name = jsonBuild.value();
                        }

                        //TODO handle arrays
                    }

                    return Stream.of(Pair.of(name, value));
                })
                .forEach(pair -> json.value(((String) pair.getLeft()), pair.getRight()));
        json.end().end();
        return json.done();
    }

    default void onCallback(String callback) {
    }

    default void onError(String error) {
        System.out.println("[ERROR] TeamWork API:\n" + error);
    }

    default void dispatch() {
        dispatch(TeamworkAPI.getI());
    }

    default void dispatch(TeamworkAPI manager) {
        manager.dispatch(this);
    }
}
