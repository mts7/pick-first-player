#!/bin/bash

keys=(storePassword keyPassword keyAlias storeFile)
filePath=../keystore.properties

if [ -f ${filePath} ]; then
  rm ${filePath}
fi
touch ${filePath}

for key in "${keys[@]}"
do
  echo "${key}=$(vlt secrets get -plaintext "${key}")" >> ${filePath}
done
