# FastXml Benchmark

## Objective

This report show how well FastXml fares against some of the well-known XML parsers.

## Hardware

* MacBook Pro
* CPU: 2.5GHz
* Memory = 16GB 1866MHz LPDDR3
* L1 cache = 32768B
* L2 cache = 262144B
* L3 cache = 6291456B

## Software

* Mac OS Version 10.11.5
* JDK 1.7.0_79-b15 64-Bit

# VM options

all benchmark test use following vm options:
    **-server -Xms128m**

## Performance
_Performance = (fileLength *1000 * totalNumber)/((totalTime/10) * (1<<20)))_

***Parse 1565 bytes file 40000 times***

| Xml Parser | Average parsing time(ms) | Performance |
| ---------- | -----------------------: | ----------: |
| javax.xml.parsers.DocumentBuilder | 0.0289 | 51.6883 |
| VTD-XML | 0.015 | 99.6661 |
| XmlPull | 0.0122 | 122.8395 |
| ***FastXml***(for huge files) | 0.0104 | 144.2029 |
| ***FastXml***(for normal files) | 0.0064 | 233.2032 |

***Parse 42432 bytes file 1200 times***

| Xml Parser | Average parsing time(ms) | Performance |
| ---------- | -----------------------: | ----------: |
| javax.xml.parsers.DocumentBuilder | 0.5325 | 75.9931 |
| VTD-XML | 0.3957 | 102.4464 |
| XmlPull | 0.3386 | 119.6049 |
| ***FastXml***(for huge files) | 0.2668 | 151.7487 |
| ***FastXml***(for normal files) | 0.1753 | 231.236 |

***Parse 17880651 bytes file 5 times***

| Xml Parser | Average parsing time(ms) | Performance |
| ---------- | -----------------------: | ----------: |
| javax.xml.parsers.DocumentBuilder | 416.86 | 40.9125 |
| VTD-XML | 175.5 | 97.2196 |
| XmlPull | 150.46 | 113.3798 |
| ***FastXml***(for huge files) | 109.54 | 155.8713 |
| ***FastXml***(for normal files) | 74.8 | 227.9722 |


## Memory

_Multiplying factor = (endMemory - startMemory)/fileLength_

**file size: 17.4MB**

| Xml Parser                        | Memory Use(MB) | Multiplying factor |
| --------------------------------- | --------------:| ------------------:|
| javax.xml.parsers.DocumentBuilder | 144.78         | 8.74               |
| VTD-XML                           | 32.23          | 1.95               |
| XmlPull                           | 35.04          | 2.12               |
| **FastXml**(for huge files)       | 17.89          | 1.05               |
| **FastXml**(for normal files)     | 6.86           | 0.41               |

