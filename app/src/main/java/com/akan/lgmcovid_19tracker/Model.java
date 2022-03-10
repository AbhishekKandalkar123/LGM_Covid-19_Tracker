package com.akan.lgmcovid_19tracker;

public class Model {
    private String sname,dname;
    private long death, cured, active;

    public Model(String sname,String dname, long death, long recovered,
                 long active)
    {
        this.sname = sname;
        this.dname = dname;
        this.death = death;
        this.cured = recovered;
        this.active = active;

    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public long getDeath() {
        return death;
    }

    public void setDeath(long death) {
        this.death = death;
    }

    public long getCured() {
        return cured;
    }

    public void setCured(long cured) {
        this.cured = cured;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }
}
