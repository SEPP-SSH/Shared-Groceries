# How to run the SSH App
To see the group grocery shopping feature prototype in action:
> **Prerequisites**: You'll need to have Java installed on your machine, and correctly set up with the requisite path variables. You'll also need Android Studio _(the app was built with Android Studio Ladybug | 2024.2.1 Patch 2)_. And you'll also need Docker Desktop installed _and_ running before continuing.
>
> Note that these pre-requisites only cover _running_ the project. To view some of the source files in Android Studio without the linting system becoming frustrated, you'll need to install the Lombok plugin for Android Studio.

1. Clone the repository onto your machine.
2. Load the project located at `./Android` (i.e. located in the root of the repository) in Android Studio.
3. Open the "Terminal" tab in Android studio (or, less conveniently, any terminal at the `Android` directory - wherever that may reside on your system).
![Screenshot of terminal button](../Attachments/how-to-run--terminal.png)
4. If on a UNIX-based machine, run the following command: `cd ../SSH-Cloud/production-orchestration && docker-compose build --no-cache` _(it will take a little while)._ If you're _not_ on a UNIX-based machine, you'll need to run the two commands _separately_ - i.e. `cd ../SSH-Cloud/production-orchestration` and then `docker-compose build --no-cache`.
5. Wait until the command has finished _(like so...)_
![Screenshot of the terminal post-build](../Attachments/how-to-run--post-build.png)
6. Run the following command: `docker-compose up`. Don't be alarmed - this will hang for a short period of time, whilst the SSH Cloud server program waits for the MySQL server to spin up before starting the program server.
7. Wait until you see the following "Javalin" ASCII art:
![Screenshot of the SSH Cloud server having started](../Attachments/how-to-run--server-started.png)
Note that the process started by this command won't terminate, as the server is running in a Docker container, and will continue to do so until the process is killed (`ctl-c`).
8. Now you can start the Android App running in the Android Emulator. The steps for reproducing the results shown are listed below, but _may not_ be necessary if you already have a suitable virtual device emulator on your machine, and a suitable run configuration added by Android Studio.
9.  Add an emulator by clicking the "Device Manager" button in Android Studio _(see below)_.
![Screenshot of the Device Manager](../Attachments/how-to-run--add-emulator.png)
10.  Select "Medium Phone" for the results we show in our documentation.
![Screenshot of the emulator device size selection screen](../Attachments/how-to-run--emulator-hardware.png)
11.  Select the "VanillaIceCream" system image for the results we show in our documentation.
![Screenshot of the emulator device system image selection screen](../Attachments/how-to-run--emulator-image.png)
12.  Click "Next" on the "Verify Configuration" screen _(see below)_, leaving values at their defaults.
![Screenshot of the emulator "Verify Configuration" screen](../Attachments/how-to-run--emulator-hardware.png)
13.  After the wizard exits, and you're returned to the Android Studio editor, verify that the device has been added, as shown in `a.` below, and then add (if one hasn't already been detected) a run configuration by clicking the "Run/Debug Configurations" button, as shown in `b.` below.
![Screenshot of the added emulator and the run configuration button](../Attachments/how-to-run--add-config.png)
14.  Click an "Add new run configuration" button in the wizard.
![Screenshot of the run configuration wizard](../Attachments/how-to-run--new-config-dialogue.png)
15.  Select "Android App" from the drop-down menu. 
![Screenshot of the run configuration options drop-down menu](../Attachments/how-to-run--new-run-config-options.png)
16.  You'll see the default settings like so:
![Screenshot of the default module selected in the new run configuration](../Attachments/how-to-run--run-config-details-pre.png)
17.  Select `SSH.app.main` from the "Module" drop-down menu _(see below)_, and then click "OK". **Note**: If the `SSH.app.main` module doesn't show up in this menu, you may need to load the Gradle project. One way to do this is to return to the Android Studio editor, and click _into_ the `settings.gradle.kts` file, where the IDE will then load up the Gradle project. Do note though, that when doing this for the first time, it will take quite some time.
![Screenshot of the correct module selected in the new run configuration](../Attachments/how-to-run--run-config-details-post.png)
18.  After the wizard exits, and you're returned to the Android Studio editor, click the run button for the new run configuration _(see below)_.
![Screenshot of the Android Studio "Run" button](../Attachments/how-to-run--run-button.png)
19.  Once the emulator loads, the project should build and you should be landed at the "My Home" fragment - as shown below. To see the group grocery shopping feature, click into the "Shopping" fragment via the tab-bar at the bottom of the UI.
![Screenshot of the SSH App running](../Attachments/how-to-run--post-run.png)

---
>To read how the feature works, and to explore some functionality, see the [What you can do]() document.
---

20. To close the running emulation, you can simply kill the emulation process in any way you wish. **However**, to close the SSH Cloud server _properly_, you need to follow these steps:
21. Focus back into the terminal window from earlier, and kill the process - most easily with `ctl-c`
![Screenshot of the server having been killed in the terminal](../Attachments/how-to-run--killing-server.png)
22.  Once the processes have "gracefully stopped", If on a UNIX-based machine, run the following command: `docker-compose down && cd ../../Android`. If you're _not_ on a UNIX-based machine, you'll need to run the two commands _separately_ - i.e. `docker-compose down` and then `cd ../../Android`.
23.  Once that has completed execution, you are _finally_ free to return going about your business!
---
---