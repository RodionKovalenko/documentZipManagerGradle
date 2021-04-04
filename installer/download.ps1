if (!([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Start-Process PowerShell -Verb RunAs "-NoProfile -ExecutionPolicy Bypass -Command `"cd '$pwd'; & '$PSCommandPath';`"";
    exit;
}


$z = gl

$localDataPath = $env:APPDATA

if (Test-Path -Path $localDataPath\\rodionZipManager -PathType Container) {
    Remove-Item -path $localDataPath\\rodionZipManager -Recurse
    
    "folder removed"
}

pause

Get-ChildItem $z -Filter *.zip | Expand-Archive -DestinationPath $localDataPath\\rodionZipManager\\jdk-15 -Force


"current directory of powershell $z"

"unzipped in powershell $localDataPath"


# copy jdk-15 folder in Program Files\\Java\\jdk-15

$pathToDirectory = "C:\\Program Files\\Java\\jdk-15"

#if (![System.IO.Directory]::Exists($pathToDirectory))
#{
  Copy-Item -Path $localDataPath\\rodionZipManager\\jdk-15\\* -Destination "C:\\Program Files\\Java" -Force -Recurse
    
    "jdk-15 copied to Program Files/Java"
#}

pause


#remove Folder after copying

if (Test-Path -Path $localDataPath\\rodionZipManager -PathType Container) {
    Remove-Item -path $localDataPath\\rodionZipManager -Recurse
    
    "folder removed"
}

pause

