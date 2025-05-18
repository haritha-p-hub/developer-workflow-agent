# PowerShell script to load secrets from application-secrets.properties
$secretsFile = "DoraService/src/main/resources/application-secrets.properties"
if (Test-Path $secretsFile) {
    Get-Content $secretsFile | ForEach-Object {
        $line = $_.Trim()
        if ($line -eq "" -or $line.StartsWith("#")) {
            # Skip comment or empty line
        } elseif ($line -match '^(.*?)=(.*)$') {
            $envVarName = $matches[1].Trim()
            $envValue = $matches[2].Trim()
            Set-Item -Path "Env:$envVarName" -Value $envValue
            Write-Host "Set $envVarName=$envValue" -ForegroundColor Yellow  # Debug output
        }
    }
    Write-Host "Secrets loaded into environment variables for the current session." -ForegroundColor Green
} else {
    Write-Host "Secrets file not found: $secretsFile" -ForegroundColor Red
} 