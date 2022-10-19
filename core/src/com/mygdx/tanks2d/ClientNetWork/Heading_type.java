package com.mygdx.tanks2d.ClientNetWork;

public class Heading_type {
    public static final int MY_TOKKEN = 1;
    public static final int MY_NIK = 2;

    public static final int STATUS_GAME = 3;
    public static final int MY_SHOT = 4;

    public static final int BUTTON_STARTGAME = 5;
    public static final int EXIT_IN_MATH_GAME_PLAYER = 6;

    public static final int PARAMETERS_PLAYER = 7;

    public static final int PARAMETERS_COMMAND = 8;
    public static final int PARAMETERS_MATH = 15;  // счета команд
    public static final int PARAMETERS_MAP = 16;  // счета команд

    public static final int MY_PARAMETERS = 9;
    public static final int MY_COMMAND = 14;

    public static final int HP_PLAYER = 10;

    public static final int DISCONECT_PLAYER = 11;

    public static final int SHELL_RUPTURE = 12; // удаление пули - уничтожение ее

    public static final int BLUE_COMMAND = 44;
    public static final int RED_COMMAND = 45;

    public static final int LIFE_TANK = 47;
    public static final int DEATH_TANK = 48;

    public static final int RESPOWN_TANK_PLAYER = 49; // дать команду на респаун - в пакет нужно поместить номер команды ))

    ////
    public static final int IN_MENU = -1;
    public static final int IN_GAME = -2;

    public static final float SHOT_LIFETIME = 1.5f;
    public static final String DEFULT_NAME = "no_name3812";

    public static final float PAUSE_GAME = 1f;
    public static final float PLAY_GAME = 0f;




}
