#!/bin/sh

TOTAL_LINES=$(find src/main/java -name *.java | xargs cat | wc -l)
TOTAL_CODE_LINES=$(find src/main/java -name *.java | xargs cat | grep -v ^$ | wc -l)

echo Total Lines: ${TOTAL_LINES} lines
echo Total Codes: ${TOTAL_CODE_LINES} lines
