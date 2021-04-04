package rodionFX.utils.scripts;

import java.io.File;
import java.io.IOException;

public class RunScript {

    public static void runUpdate() throws IOException {
        String path = new File(".").getCanonicalPath();
        ProcessBuilder procBldr = new ProcessBuilder("C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe",
                "Get-WMIObject",
                "Win32_SerialPort",
                "|",
                "Select-Object",
                "Name,DeviceID,Description,Caption,PNPDeviceID,Status");
        Process proc;
        try {
            procBldr.command(path +"/updater.ps1");
            procBldr.start();
        }
        catch (IOException x) {
            x.printStackTrace();
        }
    }
}
