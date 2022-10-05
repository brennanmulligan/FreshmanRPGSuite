#!/usr/bin/env bash

pipeline_id="$1"

database_num="0"$((("$pipeline_id" % 8) + 1))

echo "$database_num" > GameShared/config.txt