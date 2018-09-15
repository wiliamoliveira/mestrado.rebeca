//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.15 at 11:47:51 AM CEST 
//


package br.puc.mestrado.rebeca.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="r" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="sigma" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="sigmaE" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="miE" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="convYield" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="velRev" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="c1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="c2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="tau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="accuracy" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pmax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="E0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Emin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ebarra" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="qMax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="timeInMonth" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="growthRate" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "r",
    "sigma",
    "sigmaE",
    "miE",
    "convYield",
    "velRev",
    "t",
    "c1",
    "c2",
    "tau",
    "accuracy",
    "p0",
    "pmax",
    "e0",
    "emin",
    "ebarra",
    "qMax",
    "timeInMonth",
    "growthRate"
})
@XmlRootElement(name = "config")
public class Config {

    protected double r;
    protected double sigma;
    protected double sigmaE;
    protected double miE;
    protected double convYield;
    protected double velRev;
    @XmlElement(name = "T")
    protected double t;
    protected double c1;
    protected double c2;
    protected double tau;
    protected double accuracy;
    @XmlElement(name = "P0")
    protected double p0;
    @XmlElement(name = "Pmax")
    protected double pmax;
    @XmlElement(name = "E0")
    protected double e0;
    @XmlElement(name = "Emin")
    protected double emin;
    @XmlElement(name = "Ebarra")
    protected double ebarra;
    protected double qMax;
    protected boolean timeInMonth;
    protected double growthRate;

    /**
     * Gets the value of the r property.
     * 
     */
    public double getR() {
        return r;
    }

    /**
     * Sets the value of the r property.
     * 
     */
    public void setR(double value) {
        this.r = value;
    }

    /**
     * Gets the value of the sigma property.
     * 
     */
    public double getSigma() {
        return sigma;
    }

    /**
     * Sets the value of the sigma property.
     * 
     */
    public void setSigma(double value) {
        this.sigma = value;
    }

    /**
     * Gets the value of the sigmaE property.
     * 
     */
    public double getSigmaE() {
        return sigmaE;
    }

    /**
     * Sets the value of the sigmaE property.
     * 
     */
    public void setSigmaE(double value) {
        this.sigmaE = value;
    }

    /**
     * Gets the value of the miE property.
     * 
     */
    public double getMiE() {
        return miE;
    }

    /**
     * Sets the value of the miE property.
     * 
     */
    public void setMiE(double value) {
        this.miE = value;
    }

    /**
     * Gets the value of the convYield property.
     * 
     */
    public double getConvYield() {
        return convYield;
    }

    /**
     * Sets the value of the convYield property.
     * 
     */
    public void setConvYield(double value) {
        this.convYield = value;
    }

    /**
     * Gets the value of the velRev property.
     * 
     */
    public double getVelRev() {
        return velRev;
    }

    /**
     * Sets the value of the velRev property.
     * 
     */
    public void setVelRev(double value) {
        this.velRev = value;
    }

    /**
     * Gets the value of the t property.
     * 
     */
    public double getT() {
        return t;
    }

    /**
     * Sets the value of the t property.
     * 
     */
    public void setT(double value) {
        this.t = value;
    }

    /**
     * Gets the value of the c1 property.
     * 
     */
    public double getC1() {
        return c1;
    }

    /**
     * Sets the value of the c1 property.
     * 
     */
    public void setC1(double value) {
        this.c1 = value;
    }

    /**
     * Gets the value of the c2 property.
     * 
     */
    public double getC2() {
        return c2;
    }

    /**
     * Sets the value of the c2 property.
     * 
     */
    public void setC2(double value) {
        this.c2 = value;
    }

    /**
     * Gets the value of the tau property.
     * 
     */
    public double getTau() {
        return tau;
    }

    /**
     * Sets the value of the tau property.
     * 
     */
    public void setTau(double value) {
        this.tau = value;
    }

    /**
     * Gets the value of the accuracy property.
     * 
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the value of the accuracy property.
     * 
     */
    public void setAccuracy(double value) {
        this.accuracy = value;
    }

    /**
     * Gets the value of the p0 property.
     * 
     */
    public double getP0() {
        return p0;
    }

    /**
     * Sets the value of the p0 property.
     * 
     */
    public void setP0(double value) {
        this.p0 = value;
    }

    /**
     * Gets the value of the pmax property.
     * 
     */
    public double getPmax() {
        return pmax;
    }

    /**
     * Sets the value of the pmax property.
     * 
     */
    public void setPmax(double value) {
        this.pmax = value;
    }

    /**
     * Gets the value of the e0 property.
     * 
     */
    public double getE0() {
        return e0;
    }

    /**
     * Sets the value of the e0 property.
     * 
     */
    public void setE0(double value) {
        this.e0 = value;
    }

    /**
     * Gets the value of the emin property.
     * 
     */
    public double getEmin() {
        return emin;
    }

    /**
     * Sets the value of the emin property.
     * 
     */
    public void setEmin(double value) {
        this.emin = value;
    }

    /**
     * Gets the value of the ebarra property.
     * 
     */
    public double getEbarra() {
        return ebarra;
    }

    /**
     * Sets the value of the ebarra property.
     * 
     */
    public void setEbarra(double value) {
        this.ebarra = value;
    }

    /**
     * Gets the value of the qMax property.
     * 
     */
    public double getQMax() {
        return qMax;
    }

    /**
     * Sets the value of the qMax property.
     * 
     */
    public void setQMax(double value) {
        this.qMax = value;
    }

    /**
     * Gets the value of the timeInMonth property.
     * 
     */
    public boolean isTimeInMonth() {
        return timeInMonth;
    }

    /**
     * Sets the value of the timeInMonth property.
     * 
     */
    public void setTimeInMonth(boolean value) {
        this.timeInMonth = value;
    }

    /**
     * Gets the value of the growthRate property.
     * 
     */
    public double getGrowthRate() {
        return growthRate;
    }

    /**
     * Sets the value of the growthRate property.
     * 
     */
    public void setGrowthRate(double value) {
        this.growthRate = value;
    }

}