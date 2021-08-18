
The following configuration changes have been made to the Apache Tomcat distribution in this directory

apache-tomcat-8.5.69-sl

	- conf\server.xml: Edited
		HTTP port changed from 8080 to 8068
		Shutdown port changed from 8005 to 8069
		ajp port changed from 8009 to 8070
		Access logging commented out to conserve space
		increased tomcatThreadPool maxThreads from 150 to 600
		uncommented SingleSignOn valve
		commented RTViewJNDIRealm section (not used for RTView Core)

	- conf\tomcat-users.xml: Edited
		added demo users  "rtvadmin","rtvuser", and "rtvalertmgr"

	- conf\rtview-roles.txt: Added
		optional LDAP integration (not used for RTView Core)
		
	- lib\rtview-jndirealm.jar, lib\gmsjrtvhistorian.jar: Added 
		optional LDAP integration (not available in RTView Core)

	- bin\setenv.bat, bin\setenv.sh: Added
		define jmxremote port monitoring added for compatibility with RTVMGR, port 9999
		added -Xmx1024M 
		(sh only) added CATALINA_PID variable

	- webapps\examples: Removed

	- README_RTVIEW.txt: copied from servers\README.txt
