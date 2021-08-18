#!/bin/bash

if [ -f ../lib/*.jar ]; then rm ../lib/*.jar; fi

./make_classes.sh
if [ $? != 0 ]; then echo "Built stopped at make_classes due to error"; exit 1; fi

exit 0
