
#!/bin/bash
set -e
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out @sources.txt
echo "Build complete. Run: java -cp out Main data/small1.json"
