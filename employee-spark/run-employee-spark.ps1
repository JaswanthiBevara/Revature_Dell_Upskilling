
# Set Hadoop home to the folder that contains bin\winutils.exe
$env:HADOOP_HOME = "C:\Hadoop\hadoop-3.3.5"
$env:PATH = "$env:HADOOP_HOME\bin;$env:PATH"

# Confirm the required paths before starting Spark
if (-not (Test-Path "$env:HADOOP_HOME\bin\winutils.exe")) {
    Write-Error "winutils.exe was not found. Check HADOOP_HOME."
    exit 1
}

$jar = ".\target\scala-2.12\employee-spark-assembly-0.1.0.jar"

if (-not (Test-Path $jar)) {
    Write-Error "Assembly JAR not found. Run 'sbt clean test assembly' from the project root."
    exit 1
}

Write-Host "Starting Employee Analysis with spark-submit..."

spark-submit `
    --class com.training.EmployeeAnalysis `
    --master "local[*]" `
    $jar

if ($LASTEXITCODE -ne 0) {
    Write-Error "Spark application failed. Check the messages above."
    exit $LASTEXITCODE
}

Write-Host "Employee Analysis completed successfully."
