/*
 * Week 13+ Coding Assignment - JeepSales.java
 * Edited by: Joseph Falzini
 */

package com.promineotech.jeep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.promineotech.ComponentScanMarker;

@SpringBootApplication(scanBasePackageClasses = { ComponentScanMarker.class })
public class JeepSales {

  public static void main(String[] args) {
	  SpringApplication.run(JeepSales.class, args);
  }
}