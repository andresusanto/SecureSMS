package id.ac.itb.securesms.obj;

import java.math.BigInteger;

/**
 *
 * @author Andre
 */
public class Curve {

    // rumus dari kurva eliptik adalah: y^2 = x^3 + ax + b mod p, dimana P = bilangan prima
    public BigInteger a;
    public BigInteger b;
    public BigInteger p;

    public Curve(BigInteger a, BigInteger b, BigInteger p){
        this.a = a;
        this.b = b;
        this.p = p;
    }

    public Curve(Curve other){
        this.a = new BigInteger(other.a.toString());
        this.b = new BigInteger(other.b.toString());
        this.p = new BigInteger(other.p.toString());
    }

}