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

package bot;

import java.util.Random;

import field.Field;

/**
 * bot.BotStarter
 *
 * Magic happens here. You should edit this file, or more specifically
 * the doMove() method to make your bot do more than random moves.
 *
 * @author Jim van Eeden <jim@riddles.io>, Joost de Meij <joost@riddles.io>
 */

public class BotFirst implements Bot{

    
    /**
     * Makes a turn. Edit this method to make your bot smarter.
     * @return The column where the turn was made.
     */
    public int doMove(BotState state) {
        Field field = state.getField();
        int move = new Random().nextInt(field.getWidth());
        return move;
    }

    public static void main(String[] args) {
        BotParser parser = new BotParser(new BotFirst());
        parser.run();
    }
}
