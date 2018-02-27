package wang.yobbo.sys.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.Entity;
import java.sql.*;
import java.util.Date;

@Entity
public class TestEntity extends BaseEntity<String> {

    private short a; //smallint
    private int b; //int
    private long c; //
    private float d;
    private double e;
    private boolean f;
    private char h;
    private long g;
    private byte p; //tinyint
    private java.lang.Integer l;
    private java.lang.String i;
    private java.lang.Boolean z;
    private java.lang.Float k;
    private java.lang.Double m;
    private java.util.Date n;
    private java.sql.Timestamp o;
    private byte[] r; //tinyblob
    private java.sql.Date s;//date 只存日期
    private java.sql.Blob t; //longblob
    private java.sql.Time w; //time 只存时间
    private java.sql.Clob q; //logtext

    public short getA() {
        return a;
    }

    public void setA(short a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public long getC() {
        return c;
    }

    public void setC(long c) {
        this.c = c;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public char getH() {
        return h;
    }

    public void setH(char h) {
        this.h = h;
    }

    public long getG() {
        return g;
    }

    public void setG(long g) {
        this.g = g;
    }

    public byte getP() {
        return p;
    }

    public void setP(byte p) {
        this.p = p;
    }

    public Integer getL() {
        return l;
    }

    public void setL(Integer l) {
        this.l = l;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public Boolean getZ() {
        return z;
    }

    public void setZ(Boolean z) {
        this.z = z;
    }

    public Float getK() {
        return k;
    }

    public void setK(Float k) {
        this.k = k;
    }

    public Double getM() {
        return m;
    }

    public void setM(Double m) {
        this.m = m;
    }

    public Date getN() {
        return n;
    }

    public void setN(Date n) {
        this.n = n;
    }

    public Timestamp getO() {
        return o;
    }

    public void setO(Timestamp o) {
        this.o = o;
    }

    public byte[] getR() {
        return r;
    }

    public void setR(byte[] r) {
        this.r = r;
    }

    public java.sql.Date getS() {
        return s;
    }

    public void setS(java.sql.Date s) {
        this.s = s;
    }

    public Blob getT() {
        return t;
    }

    public void setT(Blob t) {
        this.t = t;
    }

    public Time getW() {
        return w;
    }

    public void setW(Time w) {
        this.w = w;
    }

    public Clob getQ() {
        return q;
    }

    public void setQ(Clob q) {
        this.q = q;
    }

    public boolean isNew() {
        return false;
    }
}
