#!/bin/bash

CLIENT=$1
BROWSER=${2:-chrome}
TEST=$3
GROUP=$4

if [ -z "$CLIENT" ]; then
  echo "Usage: ./run.sh clientName [browser] [testClassOrMethod] [group]"
  exit 1
fi

COMMAND="mvn clean test \
  -Dclient=$CLIENT \
  -Dbrowser=$BROWSER"

if [ ! -z "$TEST" ]; then
  COMMAND="$COMMAND -Dtest=$TEST"
fi

if [ ! -z "$GROUP" ]; then
  COMMAND="$COMMAND -Dgroups=$GROUP"
fi

echo "Running: $COMMAND"
eval $COMMAND
