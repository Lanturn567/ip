#!/usr/bin/env bash

# Create bin directory if it doesn't exist
if [ ! -d "../bin" ]; then
    mkdir ../bin
fi

# Delete previous output
if [ -e "./ACTUAL.TXT" ]; then
    rm ACTUAL.TXT
fi

# Delete the "data" directory if it exists
if [ -d "data" ]; then
    rm -rf data
fi

# Compile all Java files in src/main/java recursively
if ! javac -d ../bin $(find ../src/main/java -name "*.java"); then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# Run the program with the correct package name
java -cp ../bin duke.main.Lanturn < input.txt > ACTUAL.TXT

# Convert both files to UNIX format to avoid line-ending issues
cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT

# Compare output
if diff ACTUAL.TXT EXPECTED-UNIX.TXT > /dev/null; then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    diff ACTUAL.TXT EXPECTED-UNIX.TXT
    exit 1
fi
