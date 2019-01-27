package ca.toropov.api.teamwork.command;

import ca.toropov.api.teamwork.TeamworkAPI;
import ca.toropov.api.teamwork.util.RequestMethod;

/**
 * Author: toropov
 * Date: 1/26/2019
 */
public interface Command {

    String getRequestUrl();

    RequestMethod getRequestMethod();

    default String getJson() {
        return null;
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
