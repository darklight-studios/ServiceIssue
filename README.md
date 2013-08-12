Darklight Nova Core Issue: Services
====================================

Issue for [Darklight Nova Core](https://github.com/darklight-studios/darklight-nova-core)

### Function
The Services issue checks the status (enabled | disabled) of a Windows service

### Use
**Note:** You must make a seperate ServiceIssue for each service you want to monitor

1. Download the latest version from the [releases](https://github.com/darklight-studios/ServiceIssue/releases/) section
2. Add DNCServiceIssue.jar to your DNC build path
3. Create a `ServiceIssue serviceIssue = new ServiceIssue()` variable
4. Set the issue's service name and status: `serviceIssue.setInfo("wuauserv", {ServiceIssue.ENABLED | ServiceIssue.DISABLED});`
5. Add `serviceIssue` to the issue array
6. Start DNC!

### License
[GPLv3](LICENSE)

### Contributors
[@IsaacJG](https://github.com/IsaacJG)
