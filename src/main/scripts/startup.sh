#!/bin/sh
#export JAVA_HOME=/usr/local/jdk1.6.0_27
#export PATH=/usr/local/jdk1.6.0_27/bin:$PATH

nohup java -server -Xms64m -Xmx256m -XX:MaxPermSize=128m -cp ../conf:littcore-license.jar com.litt.core.security.license.gui.Gui &
