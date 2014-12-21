Protocol Buffers - Google's data interchange format
Copyright 2008 Google Inc.
http://code.google.com/p/protobuf/

This package contains a precompiled Win32 binary version of the protocol buffer
compiler (protoc).  This binary is intended for Windows users who want to
use Protocol Buffers in Java or Python but do not want to compile protoc
themselves.  To install, simply place this binary somewhere in your PATH.

This binary was built using MinGW, but the output is the same regardless of
the C++ compiler used.

You will still need to download the source code package in order to obtain the
Java or Python runtime libraries.  Get it from:
  http://code.google.com/p/protobuf/downloads/
=====================================end===============================================
=====================================help doc==========================================
@version 2.6.0
@editby:crazyjohn 2014-9-11 关于生成DescriptorProtos.java的步骤
1. 所有的proto to java或者cpp又或者py都是由protoc生成
2. 若要生成java则protoc --java_out=输出路径   proto文件路径