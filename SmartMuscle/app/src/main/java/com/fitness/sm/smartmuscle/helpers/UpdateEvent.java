package com.fitness.sm.smartmuscle.helpers;

public class UpdateEvent {
    private String origin;
    private String params="";

    public UpdateEvent(String origin){this.origin = origin;}

    public UpdateEvent(String origin, String data){
        this.origin = origin;
        this.params = data;
    }

    public String getOrigin(){return origin;}
    public String getData(){if(params!=""){return params;}else{return null;}}
}
