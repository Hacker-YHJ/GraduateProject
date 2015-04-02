package com.yanghj.newinputtest;

import java.io.InputStream;

/**
 * Created by yanghj on 3/31/15.
 */
public class InputManager {
    private static InputManager instance;

    public static boolean init() {
        return true;
    }

    public static InputManager getInstance() {
        return instance;
    }

    // 韵母
    public static final String Y_A = "a";
    public static final String Y_E = "e";
    public static final String Y_I = "i";
    public static final String Y_O = "o";
    public static final String Y_U = "u";

    public static final String Y_AI = "ai";
    public static final String Y_AO = "ao";
    public static final String Y_AN = "an";
    public static final String Y_ANG = "ang";

    public static final String Y_EI = "ei";
    public static final String Y_EN = "en";
    public static final String Y_ENG = "eng";

    public static final String Y_IN = "in";
    public static final String Y_ING = "ing";
    public static final String Y_IA = "ia";
    public static final String Y_IAN = "ian";
    public static final String Y_IANG = "iang";
    public static final String Y_IAO = "iao";
    public static final String Y_IE = "ie";
    public static final String Y_IU = "iu";
    public static final String Y_IONG = "iong";

    public static final String Y_OU = "ou";
    public static final String Y_ONG = "ong";

    public static final String Y_UA = "ua";
    public static final String Y_UAN = "uan";
    public static final String Y_UANG = "uang";
    public static final String Y_UAI = "uai";
    public static final String Y_UE = "ue";
    public static final String Y_UI = "ui";
    public static final String Y_UO = "uo";
    public static final String Y_UN = "un";

    public static final String Y_V = "v";

    // 声母
    public static final String S_B = "b";
    public static final String S_P = "p";
    public static final String S_M = "m";
    public static final String S_F = "f";
    public static final String S_D = "d";
    public static final String S_T = "t";
    public static final String S_N = "n";
    public static final String S_L = "l";
    public static final String S_G = "g";
    public static final String S_K = "k";
    public static final String S_H = "h";
    public static final String S_Y = "y";
    public static final String S_J = "j";
    public static final String S_Q = "q";
    public static final String S_X = "x";
    public static final String S_W = "w";
    public static final String S_ZH = "zh";
    public static final String S_CH = "ch";
    public static final String S_SH = "sh";
    public static final String S_R = "r";
    public static final String S_Z = "z";
    public static final String S_C = "c";
    public static final String S_S = "s";

    private String y;
    private String s;
    private String candidate;
    private String[] result;

    public InputManager(InputStream in) {
        this.result = new String[20];

    }

    public void setS(String s) {
        this.s = s;
        if (null != this.y) {
            candidate = this.s + this.y;
        }
    }

    public void setY(String y) {
        this.y = y;
        if (null != this.s) {
            candidate = this.s + this.y;
        }
    }
}
