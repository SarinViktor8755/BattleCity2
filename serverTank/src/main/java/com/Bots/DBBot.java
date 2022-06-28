package main.java.com.Bots;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DBBot {

    private int id;


    private String nikName;

    private float timerShoot; // время с последней аттаки

    //////////////////////Target

    private Vector2 target_position;
    private Vector2 target_body_rotation_angle; // направление тела
    private Vector2 target_angle_rotation_tower; // направление башни


    private Vector2 myPosition; // позиция

    private Integer nomTarget;
    private int target_tank;
    private float targetAlign; // целивой угол

    private float time_tackt_attack;
    //////////////////////////////
    private Vector2 globalTarget;
    private int nom_strategy;

    public DBBot(int id) {
        this.id = id;
        target_position = new Vector2(0,0);
        target_body_rotation_angle  = new Vector2(1,0);
        target_angle_rotation_tower  = new Vector2(1,0);
        target_angle_rotation_tower.set(target_body_rotation_angle);

        targetAlign = target_angle_rotation_tower.angleDeg();
        nomTarget = null;
        target_tank = 0;

        time_tackt_attack = 0;
        /////////////////////////
        globalTarget = new Vector2(-1,-1);
        nom_strategy = 0;




    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getNikName() {
        return nikName;
    }

    public void setNikName(String nikName) {
        this.nikName = nikName;
    }

    public float getTimerShoot() {
        return timerShoot;
    }

    public void setTimerShoot(float timerShoot) {
        this.timerShoot = timerShoot;
    }

    public Vector2 getTarget_position() {
        return target_position;
    }

    public void setTarget_position(Vector2 target_position) {
        this.target_position = target_position;
    }

    public Vector2 getTarget_body_rotation_angle() {
        return target_body_rotation_angle;
    }

    public void setTarget_body_rotation_angle(Vector2 target_body_rotation_angle) {
        this.target_body_rotation_angle = target_body_rotation_angle;
    }

    public void setTarget_body_rotation_angle(float l) {

        this.target_body_rotation_angle = target_body_rotation_angle.setAngleDeg(l);
    }

    public boolean isRedyToAttac() {
        if (this.time_tackt_attack >= 1f) {
            time_tackt_attack = 0;
            return true;
        } else return false;
    }

    public void updateTackAttack(float dt){
        this.time_tackt_attack+=dt;
    }

    public Vector2 getTarget_angle_rotation_tower() {
        return target_angle_rotation_tower;
    }

    public void setTarget_angle_rotation_tower(Vector2 target_angle_rotation_tower) {
        this.target_angle_rotation_tower = target_angle_rotation_tower;
    }

    public void setTarget_angle_rotation_tower(float angel) {
        this.target_angle_rotation_tower.setAngleDeg(angel);
    }



    public Vector2 getMyPosition() {
        return myPosition;
    }

    public void setMyPosition(Vector2 myPosition) {
        this.myPosition = myPosition;
    }

    public Integer getNomTarget() {
        return nomTarget;
    }

    public void setNomTarget(Integer nomTarget) {
        this.nomTarget = nomTarget;
    }

    public int getTarget_tank() {
        return target_tank;
    }

    public void setTarget_tank(int target_tank) {
        this.target_tank = target_tank;
    }

    public float getTargetAlign() {
        targetAlign = MathUtils.random(0,350);
        return targetAlign;
    }

    public void setTargetAlign(float targetAlign) {
        this.targetAlign = targetAlign;
    }
}