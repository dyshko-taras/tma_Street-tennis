package com.game.game.lib.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {
    private static final String PREFERENCES_NAME = "game_settings_streettennis";

    private static final String KEY_MUSIC_ON = "music_on";
    private static final String KEY_SFX_ON = "sfx_on";

    private static Preferences preferences;

    public static void init() {
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
    }


    //music
    public static boolean getMusicOn() {
        return preferences.getBoolean(KEY_MUSIC_ON, true);
    }

    public static void setMusicOn(boolean music) {
        preferences.putBoolean(KEY_MUSIC_ON, music);
        preferences.flush();
    }

    //sfx
    public static boolean getSfxOn() {
        return preferences.getBoolean(KEY_SFX_ON, true);
    }

    public static void setSfxOn(boolean sfx) {
        preferences.putBoolean(KEY_SFX_ON, sfx);
        preferences.flush();
    }
}
