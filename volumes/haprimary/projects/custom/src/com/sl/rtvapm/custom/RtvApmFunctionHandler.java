//*******************************************************************
// SL-GMS Enterprise RtView Examples
// RtvApmFunctionHandler: Custom RTVAPM Function Handler
// Copyright (c) 2011 Sherrill-Lubinski Corporation. All Rights Reserved.
// 28 October 2011
//********************************************************************

package com.sl.rtvapm.custom;

import java.net.*;
import java.util.*;

import com.sl.gmsjrt.*;
import com.sl.gmsjrtview.*;
import com.sl.gmsjcacheds.*;

import com.sl.gmsjrtvutils.*;


/* This class contains a Custom Function Handler.
 */

public class RtvApmFunctionHandler extends GmsRtViewCustomFunctionHandler
{

// **************************************************************
// FUNCTION TABLE

public Vector<GmsRtViewFunctionDescriptor> getFunctionDescriptors ()
{
					// vector of function descriptors

	Vector<GmsRtViewFunctionDescriptor> v =
				new Vector<GmsRtViewFunctionDescriptor>();

					// TEST
	{
        GmsRtViewFunctionArgument args[] = {
		new GmsRtViewFunctionArgument("Value", GMS.G_STRING),
		};

        v.addElement(new GmsRtViewFunctionDescriptor("_________CUSTOM_________",
		args,
		GMS.G_STRING, null,
		"This function returns the input string.\n",
		"This function returns the input string.\n" +
		"It is used to test access to custom functions" +
		"It also marks the beginning of custom functions",
		false));
	}

					// ROUND
	{
        GmsRtViewFunctionArgument args[] = {
		new GmsRtViewFunctionArgument("Value", GMS.G_DOUBLE),
		};

	v.addElement(new GmsRtViewFunctionDescriptor("Round", args,
		GMS.G_INTEGER, null,
		"This function rounds the value to the nearest integer.\n",
		"This is the extended Help description.\n",false));
	}
					// MODIFY TABLE COLUMN
	{
	GmsRtViewFunctionArgument args[] = {
		new GmsRtViewFunctionArgument("Input Table", GMS.G_TABLE),
		new GmsRtViewFunctionArgument("Suffix", GMS.G_STRING),
		};

	v.addElement(new GmsRtViewFunctionDescriptor("ModifyTableColumn", args,
		GMS.G_TABLE, null,
		"This function appends a suffix to a table column cells.\n",
		"This function appends a suffix to a table column cells.\n",
		false));
	}

	return v;
}

// **************************************************************
// FUNCTION RESULT METHODS

public String getStringResult (String functionName,
			GmsRtViewFunctionDescriptor functionDesc,
			GmsModelVariables functionIcon)
{
	String ret = "";

	if (functionName.equals("_________CUSTOM_________"))
		return getCustomTest(functionDesc, functionIcon);

	return ret;
}

public int getIntResult (String functionName,
			GmsRtViewFunctionDescriptor functionDesc,
			GmsModelVariables functionIcon)
{
	int ret = 0;

	if (functionName.equals("Round"))
		return getRound(functionDesc, functionIcon);

	return ret;
}

public double getDoubleResult (String functionName,
			GmsRtViewFunctionDescriptor functionDesc,
			GmsModelVariables functionIcon)
{
	double ret = 0.0;
	return ret;
}

public GmsTabularData getTabularResult (String functionName,	
			GmsRtViewFunctionDescriptor functionDesc,
			GmsModelVariables functionIcon)
{
	GmsTabularData resultTable = null;

	if (functionName.equals("ModifyTableColumn"))
		return getModifyTableColumn(functionDesc, functionIcon);

	return resultTable;
}

// ************************************************************
// TEST

private String getCustomTest (
		GmsRtViewFunctionDescriptor functionDesc,
		GmsModelVariables functionIcon)
{
	try {
		return functionDesc.getArgStringValue("Value", functionIcon);
	} catch (Exception ex) {
		System.out.println("ERROR: " + ex);
		ex.printStackTrace();
	}

	return "";
}

// ************************************************************
// ROUND

public int getRound (GmsRtViewFunctionDescriptor functionDesc,
					GmsModelVariables functionIcon)
{
	int ret = 0;

	double num = Double.NaN;

				// get argument value via the descriptor
	try {
		num = functionDesc.getArgDoubleValue("Value", functionIcon);

				// show error message, if argument not found
	} catch (Exception e) {
		System.out.println(e.getMessage());
		return 0;
	}
				// round to the nearest integer
	ret = (int)Math.rint(num);

				// return the result to the caller
	return ret;
}

// ************************************************************
// MODIFY TABLE COLUMN

// Append suffix to table column

public GmsTabularData getModifyTableColumn (
		GmsRtViewFunctionDescriptor functionDesc,
		GmsModelVariables functionIcon)
{
	GmsTabularData resultTable = null;

	try {
		GmsTabularData data =
				functionDesc.getArgTableValue("Input Table",
								functionIcon);
		if (data == null)
			return null;

		resultTable = (GmsTabularData)data.clone();

		String suffix = functionDesc.getArgStringValue("Suffix",
								functionIcon);
		if (suffix == null || suffix.equals(""))
			return resultTable;

		int colIndex = 0;

		for (int i = 0; i < data.getNumRows(); i++) {
			String valueStr = data.getCellValue(i, colIndex);

			valueStr += suffix;

			resultTable.setCellValue(valueStr, i, colIndex);
		}

	} catch (Exception ex) {
		System.out.println("ERROR: " + ex);
		ex.printStackTrace();
	}

	return resultTable;
}

}
