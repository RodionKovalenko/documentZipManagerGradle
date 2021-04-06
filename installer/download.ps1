if (!([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Start-Process PowerShell -Verb RunAs "-NoProfile -ExecutionPolicy Bypass -Command `"cd '$pwd'; & '$PSCommandPath';`"";
    exit;
}


$z = gl

$localDataPath = $env:APPDATA

if (Test-Path -Path $localDataPath\\rodionZipManager -PathType Container) {
    Remove-Item -path $localDataPath\\rodionZipManager -Recurse
    
    "tmp folder removed"
}


Get-ChildItem $z -Filter *.zip | Expand-Archive -DestinationPath $localDataPath\\rodionZipManager\\jdk-15 -Force


"current directory of powershell $z"

"unzipped in powershell $localDataPath"


# copy jdk-15 folder in Program Files\\Java\\jdk-15

$pathToDirectory = "C:\\Program Files\\Java\\jdk-15"

if (![System.IO.Directory]::Exists($pathToDirectory))
{
  Copy-Item -Path $localDataPath\\rodionZipManager\\jdk-15\\* -Destination "C:\\Program Files\\Java\\jdk-15" -Force -Recurse
    
    "jdk-15 copied to Program Files/Java"
}


#remove Folder after copying

if (Test-Path -Path $localDataPath\\rodionZipManager -PathType Container) {
    Remove-Item -path $localDataPath\\rodionZipManager -Recurse
    
    "tmp folder removed"
}


"Checking java version.... Warten Sie bis die Installation abgeschlossen wird"

#$javaver = $out = &"java.exe" -version 2>&1
#if ($javaver) {
#    "installed java version $javaver"
#} else {
#    "installing java runtime"
#   Invoke-Expression "& `".\jre-8.exe`""
#}

#pause


"remove installtion packages"


if (Test-Path -Path $z -PathType Container) {
    Remove-Item -path $z\\jdk-15.zip -Recurse
    
    "tmp folder removed"
}

"finished"

