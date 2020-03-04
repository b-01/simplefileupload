#!/bin/sh

ssh -fNL 5432:localhost:5432 user@192.168.56.101
ssh -fNL 8080:localhost:8080 user@192.168.56.101

exit 0
