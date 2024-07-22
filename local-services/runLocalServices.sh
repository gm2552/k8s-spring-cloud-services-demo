#! /bin/bash

trap "kill 0" EXIT

./run-gateway.sh &
./run-config.sh &

wait