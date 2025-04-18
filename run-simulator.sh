#!/bin/bash

# Create output directory if it doesn't exist
if [ ! -d "bin" ]; then
    mkdir bin
    echo "Created bin directory"
fi

# Find all Java files and save them to sources.txt
echo "Finding Java source files..."
find . -name "*.java" > sources.txt

# Compile all Java files placing class files in the bin directory
echo "Compiling Java files..."
javac -d bin @sources.txt
if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

# Run the simulator
echo "Running simulator..."
java -cp bin simulator.Simulator scenario.txt

# Optionally, display simulation output if available
if [ $? -eq 0 ] && [ -f simulation.txt ]; then
    echo -e "\nSimulation output (simulation.txt):"
    cat simulation.txt
fi