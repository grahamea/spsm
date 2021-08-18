// *******************************************************************
//
// SL-GMS Enterprise RtView 
// RtvApmCommandHandler: Custom Command Handler
// Copyright (c) 2011 Sherrill-Lubinski Corporation. All Rights Reserved.
// 9 November 2011
// 
// ********************************************************************

package com.sl.rtvapm.custom;

import com.sl.gmsjrt.*;
import com.sl.gmsjrtview.*;

public class RtvApmCommandHandler extends GmsRtViewCustomCommandHandler 
{ 
		// column indexes used in outputAlertNotification
	private int alertLutIndex = -1;
	private int alertTimeIndex = -1;
	private int alertNameIndex = -1;
	private int alertIndexIndex = -1;
	private int alertIdIndex = -1;
	private int alertSevIndex = -1;
	private int alertTextIndex = -1;
	private int clearedReasonIndex = -1;
	private int sourceIndex = -1;

	
	// to send email for alert notifications, set sendEmail to
	// true and fill in the arguments
	private static boolean sendEmail = false;
	private static final String EMAIL_HOST = "123.4.567.890";
	private static final int EMAIL_PORT = 587;
	private static final String EMAIL_TO = "recipient@domain.com";
	private static final String EMAIL_FROM = "sender@domain.com";
	private static final String EMAIL_USER = "";
	private static final String EMAIL_PASSWORD = "";

	// to send snmp traps for alert notifications, set sendSnmpTrap to
	// true and fill in the arguments
	private static boolean sendSnmpTrap = false;
	private static final int SNMP_TYPE = 1;
	private static final String SNMP_HOST = "localhost";
	private static final int SNMP_PORT = 162;
	private static final String SNMP_COMMUNITY = "public";



public RtvApmCommandHandler ()
{
}

public void initialize ()
{
	super.initialize();
	System.out.println("initializing custom command handler");
	if (sendEmail)
		System.out.println("custom command handler will send emails as follows for alerts: email host: " + 
			EMAIL_HOST + ", port: " + EMAIL_PORT + ", to: " + EMAIL_TO + ", from: "
 			+ EMAIL_FROM + ", user: " + EMAIL_USER + ", pass: " + EMAIL_PASSWORD);
	if (sendSnmpTrap)	
		System.out.println("custom command handler will send snmp traps as follows for alerts: snmp version: " 
			+ (SNMP_TYPE > 0 ? "v2/v3" : "v1") + ", host: " +
			SNMP_HOST + ", port: " + SNMP_PORT + ", community: " + SNMP_COMMUNITY);
}

//***************************************************************************
// INVOKE COMMAND 

/** This is called when a custom command is executed. This method should 
 * return null for commands this class does not handle, otherwise it should
 * return a status.
 * 
 */
public GmsRtViewCommandStatus invokeCommand (GmsRtViewCommand cmd)
{ 
	String commandString = cmd.commandString;
	Object value = cmd.commandArg; 
			// none of these commands support a value of 
			// any type other than string
	//System.out.println("command:" + commandString + ": " + value);

	GmsRtViewCommandStatus sts = new GmsRtViewCommandStatus();
	if (commandString.equals("test_custom")) {
		System.out.println("command test_custom has been invoked: " + value);
		return sts;

	} else if (commandString.startsWith("my_alert_notification.")) {
		sts = outputAlertNotification(commandString, value);
		if (sts.status == GmsRtViewCommandStatus.ERROR)
			System.out.println(sts.message);
		return sts;
	}
	// return null if this class does not support the cmd. this will allow
	// other custom handlers to process it.
	return null;
}

//***************************************************************************
// CUSTOM ALERT NOTIFICATION COMMAND 

/** Process the command and output to the console. Users should replace or 
 * modify this method to do their custom alert notification.
 */
public GmsRtViewCommandStatus outputAlertNotification(String cmdStr, Object value)
{
	// split the cmdStr into arguments. it should be the following:
	// my_alert_notification.$domainName.$alertNotifyType.$alertNotifyCol
	String[] args = cmdStr.split("\\.");
	String domain = args[1];
	String notifyType = args[2];
	
	// check that the command string was properly formatted.
	GmsRtViewCommandStatus sts = checkArgs(cmdStr, args);
	if (sts.status == GmsRtViewCommandStatus.ERROR)
		return sts;
	
	// check that the argument was a GmsTabularData
	checkValue(cmdStr, value, sts);
	if (sts.status == GmsRtViewCommandStatus.ERROR)
		return sts;
	GmsTabularData data = (GmsTabularData)value;

	// check if we've already queried the column indexes
	if (alertNameIndex < 0) 
		initColumnIndexes(data);

	// build a string and print it to the console. users should replace 
	// this section with the code to do their custom alert notification
	StringBuilder sb = new StringBuilder();
	sb.append("Custom alert command executed for ");
	sb.append(notifyType);
	sb.append(": ");
	sb.append(" DOMAINNAME=");
	sb.append(domain);
	sb.append(" ALERTNAME=");
	sb.append(data.getCellValue(0, alertNameIndex));
	sb.append(" ALERTINDEX=");
	sb.append(data.getCellValue(0, alertIndexIndex));
	sb.append(" ALERTID=");
	sb.append(data.getCellValue(0, alertIdIndex));
	sb.append(" ALERTSEVERITY=");
	sb.append(data.getCellValue(0, alertSevIndex));
	if (notifyType.equals("NEW_ALERT") || notifyType.equals("FIRST_SEV_CHANGE") || notifyType.equals("RENOTIFY_ON_TIMER")) {
		sb.append(" ALERTTEXT=");
		sb.append(data.getCellValue(0, alertTextIndex));
	} else if (notifyType.equals("CLEARED_ALERT")) {
		sb.append(" CLEARED REASON=");
		sb.append(data.getCellValue(0, clearedReasonIndex));
	} else if (notifyType.equals("COLUMN_CHANGED")) {
		// the name of the column that changed is in args[3] - use 
		// this to query the value from the table
		sb.append(" CHANGED_COLUMN=");
		sb.append(args[3]);
		sb.append(" CHANGED_VALUE=");
		sb.append(data.getCellValue(0, data.getColumnIndex(args[3])));
	} 
	
	
	if (sendEmail) {
		sts = sendEmail(EMAIL_HOST, EMAIL_PORT, EMAIL_TO, EMAIL_FROM, 
			"Alert Action: " + notifyType + data.getCellValue(0, alertIdIndex), 
			sb.toString(), null, EMAIL_USER, EMAIL_PASSWORD);
		if (sts.status == GmsRtViewCommandStatus.ERROR)
			System.out.println("email notification failed: " + sts.message);
	}

	if (sendSnmpTrap) {
		sts = sendAlertSnmpTrap(SNMP_TYPE, SNMP_HOST, SNMP_PORT, SNMP_COMMUNITY,
			data.getCellValue(0, alertNameIndex), data.getCellValue(0, alertIndexIndex),
			data.getCellValue(0, alertTimeIndex), data.getCellValue(0, alertLutIndex),
			data.getCellValue(0, alertSevIndex), data.getCellValue(0, alertTextIndex),
			data.getCellValue(0, alertIdIndex));
		if (sts.status == GmsRtViewCommandStatus.ERROR)
			System.out.println("snmp notification failed: " + sts.message);
	}

	// output notification to log
	System.out.println(sb.toString());

	// For SolEventModule alerts, optionally get additional information. Uncomment the if statement below, 
	// along with the getEventModuleEvent method at the end of this file. Add 
	// RTVAPM_HOME/rtvapm/solmon/lib/rtvapm_solmon.jar to the CP variable in the appropriate make_classes.bat
	// or make_classes.sh under custom/src and rebuild using the make_all script.
/*
	if (data.getCellValue(0, alertNameIndex).startsWith("SolEventModule")) {
		// In the RTViewSolaceMonitor, there will be no source column as the alert notifications are 
		// executed locally. In Enteprise Monitor, notifications are handled centrally and the Source 
		// column indicates the data server that generated the alert
		String source = null;
		if (sourceIndex >= 0)
			source = data.getCellValue(0, sourceIndex);
		getEventModuleInfo(data.getCellValue(0, alertIndexIndex), source);
	} 
*/

	return sts;

}

// Utility method to check the command string.
private GmsRtViewCommandStatus checkArgs (String cmdStr, String[] args)
{
	GmsRtViewCommandStatus sts = new GmsRtViewCommandStatus();
	String argError = "ERROR: Invalid my_alert_notification command: " + 
		cmdStr +
		". It should be my_alert_notification.$domainName.$alertNotifyType.$alertNotifyCol";
	
	// check that we got at least the notify type, and domain
	if (args.length < 3) {
		sts.status = GmsRtViewCommandStatus.ERROR;
		sts.message = argError;
		return sts;
	}
	
	// args[2] is the notification type. check that it is a valid type.
	if (!"NEW_ALERT".equals(args[2]) && !"CLEARED_ALERT".equals(args[2]) && 
			!"COLUMN_CHANGED".equals(args[2]) && 
			!"RENOTIFY_ON_TIMER".equals(args[2]) && 
			!"FIRST_SEV_CHANGE".equals(args[2])) {
		sts.status = GmsRtViewCommandStatus.ERROR;
		sts.message = argError;
		return sts;
	}
	
	// if the type is COLUMN_CHANGED, check that we got the name of 
	// the column that changed - this will be blank for the other
	// notification types
	if ("COLUMN_CHANGED".equals(args[2]) && args.length < 4) {
		sts.status = GmsRtViewCommandStatus.ERROR;
		sts.message = argError;
		return sts;
	}
	
	return sts;
}

// Utility method to check the command argument.
private void checkValue (String cmdName, Object value, GmsRtViewCommandStatus sts )
{
	if (value == null || !(value instanceof GmsTabularData) || ((GmsTabularData)value).getNumRows() == 0) {
		sts.status = GmsRtViewCommandStatus.ERROR;
		sts.message = "ERROR: " + cmdName + " requires a GmsTabularData argument. It should be set to $alertNotifyTable.";
	}
}

// Utility method to query the columns used in outputAlertNotification.
private void initColumnIndexes (GmsTabularData data)
{
	if (data == null)
		return;

	alertTimeIndex = data.getColumnIndex("Time");
	alertLutIndex = data.getColumnIndex("Last Update Time");
	alertNameIndex = data.getColumnIndex("Alert Name");
	alertIndexIndex = data.getColumnIndex("Alert Index");
	alertIdIndex = data.getColumnIndex("ID");
	alertSevIndex = data.getColumnIndex("Severity");
	alertTextIndex = data.getColumnIndex("Alert Text");
	clearedReasonIndex = data.getColumnIndex("Cleared Reason");
	sourceIndex = data.getColumnIndex("Source");
}

/*
private void getEventModuleInfo (String alertIndex, String source)
{
	// the solEventID is the last token of the alertIndex
	String solEventID = alertIndex.substring(alertIndex.lastIndexOf("~") + 1);
	System.out.println("getting event module info for " + solEventID);


	// returns the (one) row from the SolEventModuleAlertCache that generated this alert
	// arguments are solEventID, connection name for data server that 
	// generated the alert (null or "" means local data server), 
	// and timeout (in seconds) the method should wait for the data to return.
	GmsTabularData extendedAlertData = 
		com.sl.rtvapm.solmon.RtvApmAppManager.getExtendedSolEventAlertInfo(solEventID, source, 1);

	System.out.println("extendedAlertData: " + extendedAlertData);
}
*/

}
