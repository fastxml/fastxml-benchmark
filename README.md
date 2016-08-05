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

**Parse 1565 bytes file 40000 times**

| Xml Parser                        | Average parsing time(ms) | Performance       |
| --------------------------------- | ------------------------:| -----------------:|
| javax.xml.parsers.DocumentBuilder | 0.030252501              | 49.33885306366219 |
| VTD-XML                           | 0.0150150                | 99.50             |
| XmlPull                           | 0.0125525                | 118.92432710563995|
| **FastXml**                       | 0.0066974997             | 223.5955513371957 |

**Parse 41292 bytes file 1200 times**

| Xml Parser                        | Average parsing time(ms) | Performance |
| --------------------------------- | ------------------------:| -----------------:|
| javax.xml.parsers.DocumentBuilder | 0.55083334               | 73.46379774962179 |
| VTD-XML                           | 0.40008336               | 101.17            |
| XmlPull                           | 0.33458334               | 121.09618531795512|
| **FastXml**                       | 0.17175                  | 235.72606947815535|

**Parse 17367391 bytes file 5 times**

| Xml Parser                        | Average parsing time(ms) | Performance        |
| --------------------------------- | ------------------------:| ------------------:|
| javax.xml.parsers.DocumentBuilder | 313.7                    | 54.37601281672108  |
| VTD-XML                           | 185.02                   | 92.17              |
| XmlPull                           | 161.88                   | 105.39133262870044 |
| **FastXml**                       | 74.62                    | 228.58334610353526 |


## Memory

_Multiplying factor = (endMemory - startMemory)/fileLength_

**file size: 17.4MB**

| Xml Parser                        | Memory Use(MB) | Multiplying factor |
| --------------------------------- | --------------:| ------------------:|
| javax.xml.parsers.DocumentBuilder | 144.78         | 8.74               |
| VTD-XML                           | 32.23          | 1.95               |
| XmlPull                           | 35.04          | 2.12               |
| **FastXml**                       | 6.86           | 0.41               |

