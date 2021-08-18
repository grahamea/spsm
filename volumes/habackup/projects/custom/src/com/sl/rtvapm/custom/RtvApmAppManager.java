// *******************************************************************
// SL-GMS Enterprise RtView Examples
// RtvApmAppManager: Custom RtView AppManager
// Copyright (c) 2004 Sherrill-Lubinski Corporation. All Rights Reserved.
// 9 November 2011
// ********************************************************************

package com.sl.rtvapm.custom;

import com.sl.gmsjrt.*;
import com.sl.gmsjrtview.*;

/**
 * The RtvApmAppManager class contains example of applicaton customization.
 */

public class RtvApmAppManager extends GmsRtViewCustomRtvAppManager
{

public RtvApmAppManager ()
{
}

// ************************************************************
// INSTANCE METHODS

// Process command line arguments

public void processMainArgument (String arg)
{
					// process custom args

	if (arg.equals("-my_custom_arg")) {
		// Do something
	}
}

public void initialize()
{
	// In RTVAPM, this is never called
}

public void initApplication ()
{
	System.out.println("... Custom AppMgr initApplication");
}

public void startApplication ()
{
	System.out.println("... Custom AppMgr startApplication");
}

}
