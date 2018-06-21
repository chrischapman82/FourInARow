/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.connectfour.game.state;

import io.riddles.connectfour.game.player.ConnectfourPlayer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.riddles.javainterface.game.state.AbstractStateDeserializer;

/**
 * ConnectfourDeserializer takes a serialised String and returns a ConnectfourState.
 *
 * @author jim
 */
public class ConnectfourDeserializer extends AbstractStateDeserializer<ConnectfourPlayer, ConnectfourState> {

    public ConnectfourDeserializer(ArrayList<ConnectfourPlayer> players) {
        super(players);
    }

    @Override
    public ConnectfourState traverse(String statesString) throws JSONException {
        ConnectfourState state = null;
        Object states = new JSONObject(statesString);

        if (states instanceof JSONArray) {
            JSONArray statesJson = (JSONArray) states;
            for (int i = 0; i < statesJson.length(); i++) {
                JSONObject stateJson = statesJson.getJSONObject(i);
                state = visitState(stateJson, state);
            }
        } else if (states instanceof JSONObject) {
            JSONObject stateJson = (JSONObject) states;
            state = visitState(stateJson, null);
        } else {
            throw new JSONException("Input string is not array and not object");
        }

        return state;
    }

    private ConnectfourState visitState(JSONObject stateJson, ConnectfourState previousState) throws JSONException {
        int roundNumber = stateJson.getInt("round");
        return null;
    }
}
