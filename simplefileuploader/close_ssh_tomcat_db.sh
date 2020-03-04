#!/bin/sh

pkill -f 5432:localhost:5432
pkill -f 8080:localhost:8080

exit 0
