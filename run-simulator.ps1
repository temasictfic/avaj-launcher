# Create output directory if it doesn't exist
if (-not (Test-Path -Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
    Write-Host "Created bin directory"
}

# Find all Java files and save to sources.txt
Write-Host "Finding Java source files..."
Get-ChildItem -Recurse -Filter *.java -File | ForEach-Object { $_.FullName } | Out-File -Encoding ascii sources.txt

# Compile all Java files
Write-Host "Compiling Java files..."
cmd /c "javac -d bin @sources.txt"
if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed!" -ForegroundColor Red
    exit 1
}

# Run the simulator
Write-Host "Running simulator..." -ForegroundColor Green
java -cp "bin" simulator.Simulator scenario.txt

# Show simulation output if successful
if ($LASTEXITCODE -eq 0 -and (Test-Path -Path "simulation.txt")) {
    Write-Host "`nSimulation output (simulation.txt):" -ForegroundColor Cyan
    Get-Content -Path "simulation.txt"
}