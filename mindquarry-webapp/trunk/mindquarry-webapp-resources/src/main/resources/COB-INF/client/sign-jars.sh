#!/bin/sh

KEYSTORE="$1"

for file in $( find . -name "*.jar" ); do
	#sh unsigner.sh $file
	echo $file
	#jarsigner -keystore $KEYSTORE -storepass mindquarry $file mindquarry
	#jarsigner -verify -verbose -certs -keystore $KEYSTORE -storepass mindquarry $file mindquarry 
	jarsigner -verify -keystore $KEYSTORE -storepass mindquarry $file mindquarry 
done