[![Build Status](https://secure.travis-ci.org/Appendium/nachocalendar.png?branch=master)](http://travis-ci.org/Appendium/nachocalendar) [![Stories in Ready](https://badge.waffle.io/Appendium/nachocalendar.png?label=ready)](https://waffle.io/Appendium/nachocalendar)
[![SonarQube](https://sonarcloud.io/api/project_badges/measure?project=net.sf.nachocalendar:nachocalendar-parent&metric=bugs)](https://sonarcloud.io/dashboard/index/net.sf.nachocalendar:nachocalendar-parent) 
[![Sonar Reliability](https://sonarcloud.io/api/project_badges/measure?project=net.sf.nachocalendar:nachocalendar-parent&metric=reliability_rating)](https://sonarcloud.io/dashboard/index/net.sf.nachocalendar:nachocalendar-parent) 
[![Sonar Security](https://sonarcloud.io/api/project_badges/measure?project=net.sf.nachocalendar:nachocalendar-parent&metric=security_rating)](https://sonarcloud.io/dashboard/index/net.sf.nachocalendar:nachocalendar-parent) 
[![Maven Central nachocalendar](https://maven-badges.herokuapp.com/maven-central/net.sf.nachocalendar/nachocalendar/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.sf.nachocalendar/nachocalendar)
![GitHub license](https://img.shields.io/github/license/appendium/nachocalendar.svg?style=flat-square)

[![Quality Gate](https://sonarcloud.io/api/project_badges/quality_gate?project=net.sf.nachocalendar:nachocalendar-parent)](https://sonarcloud.io/dashboard/index/net.sf.nachocalendar:nachocalendar-parent) 
# nachocalendar
Date picker from SF

This project provides a set of Swing calendar components. It has been upgraded to work with Java 21.

## Building from source

To build the project from source, you will need:
*   Java 21 or higher
*   Maven 3.8.0 or higher

Clone the repository and run the following command:
```
mvn clean install
```

## Usage

Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>net.sf.nachocalendar</groupId>
    <artifactId>nachocalendar</artifactId>
    <version>0.26-SNAPSHOT</version>
</dependency>
```

Then, you can use the components in your code, for example:
```java
import net.sf.nachocalendar.components.DateField;

// ...

DateField dateField = new DateField();
// add the component to your panel
```
