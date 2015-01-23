# noprobLAN Badge Creator

There are some good tools for batch image processing. But they're all about pixels. And I don't like raster graphics when it comes to print media. So I decided to create a one-purpose tool for creating print-ready event badges.

At noprobLAN we therefore creates badges from PDF templates with this Java application. The application consists of two console applications and a GUI which combines them. It's possible to create badges and save them to a PDF all merged onto A4 pages. 

<img src="https://raw.githubusercontent.com/noproblan/npl-badges/master/sample-badges.png" height="200px"/>

## Configuration
Custom configuration like editing the output format, scaling, offsets, etc. has to be done in code for now.

## Known Issues

Does not work on MAC OS X because of a [bug](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=7133484) in Oracles java.
