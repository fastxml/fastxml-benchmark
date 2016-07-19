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
_Performance = (fileLength *1000 * total)/((totalTime/10) * (1<<20)))_

**Parse 1515 bytes file 40000 times**

| Xml Parser                        | Average parsing time(ms) | Performance |
| --------------------------------- | ------------------------:| -----------:|
| javax.xml.parsers.DocumentBuilder | 0.0287600                | 50.25       |
| VTD-XML                           | 0.0149875                | 96.48       |
| XmlPull                           | 0.0125675                | 115.12      |
| **FastXml**                       | 0.0095350                | 151.69      |

**Parse 41292 bytes file 1200 times**

| Xml Parser                        | Average parsing time(ms) | Performance |
| --------------------------------- | ------------------------:| -----------:|
| javax.xml.parsers.DocumentBuilder | 0.52091664               | 75.61       |
| VTD-XML                           | 0.38441667               | 102.51      |
| XmlPull                           | 0.31741667               | 124.36      |
| **FastXml**                       | 0.24883333               | 158.57      |

**Parse 17367391 bytes file 5 times**

| Xml Parser                        | Average parsing time(ms) | Performance |
| --------------------------------- | ------------------------:| -----------:|
| javax.xml.parsers.DocumentBuilder | 316.12                   | 52.41       |
| VTD-XML                           | 171.72                   | 96.52       |
| XmlPull                           | 143.96                   | 115.18      |
| **FastXml**                       | 111.10                   | 149.21      |


## Memory

_Multiplying factor = (endMemory - startMemory)/fileLength_

**file size: 17.4MB**

| Xml Parser                        | Memory Use(MB) | Multiplying factor |
| --------------------------------- | --------------:| ------------------:|
| javax.xml.parsers.DocumentBuilder | 144.78         | 8.74               |
| VTD-XML                           | 32.23          | 1.95               |
| XmlPull                           | 35.04          | 2.12               |
| **FastXml**                       | 6.86           | 0.41               |

