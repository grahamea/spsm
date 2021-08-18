// *******************************************************************
// SL-GMS Enterprise RtView 
// RtvApmRtViewManager: Custom RtView Manager 
// Copyright (c) 2011 Sherrill-Lubinski Corporation. All Rights Reserved.
// 7 November 2011
// ********************************************************************

package com.sl.rtvapm.custom;

import com.sl.gmsjrt.*;
import com.sl.gmsjrtview.*;
import com.sl.gmsjmodels.*;
import com.sl.gmsjjmsadmds.*;

import com.sl.gmsjrtvutils.RtvLayoutUtils;

import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.*;


/** The <B>RtvApmRtViewManager</B> class defines a custom RtView manager.
<P>
It allows the user to intercept the activation and deactivation
of SL-GMS RtViews, in order to perform custom operations.
*/

public class RtvApmRtViewManager extends GmsRtViewCustomRtViewManager
{
	static boolean inited = false;

// ******************************************************************
// CONSTRUCTOR METHODS

/** Create an instance of the custom RtView Manager.
 */

public RtvApmRtViewManager ()
{
	super();
}

// ******************************************************************
// INSTANCE METHODS

public void initialize ()
{
	if (!inited)
		initializeOnce();
}

protected void initializeOnce ()
{
	inited = true;
}

// ******************************************************************
// ACTIVATE METHODS

public void preActivate ()
{
	super.preActivate();

	String rtvName = getRtvName();

	if (rtvName.equals("my_display")) {
		// Do something
	}
}

public void activate ()
{
	super.activate();

	String rtvName = getRtvName();

					// skip internal panels
	if (rtvName == null || rtvName.equals(""))
		return;

	if (rtvName.equals("my_display")) {
		// Do something
	}
}

// ******************************************************************
// UPDATE METHODS

public void update ()
{
	super.update();

	String rtvName = getRtvName();

					// skip internal panels
	if (rtvName == null || rtvName.equals(""))
		return;

	if (rtvName.equals("my_display")) {
		// Do something
	}
}

// ******************************************************************
// DEACTIVATE METHODS

public void deactivate ()
{
	super.deactivate();

	if (isEditMode()) {
		return;
	}

	String rtvName = getRtvName();
	
					// skip internal panels
	if (rtvName == null || rtvName.equals(""))
		return;

	if (rtvName.equals("my_display")) {
		// Do something
	}
}

public void postDeactivate ()
{
	super.postDeactivate();

	if (isEditMode()) {
		return;
	}

	String rtvName = getRtvName();
	
					// skip internal panels
	if (rtvName == null || rtvName.equals(""))
		return;

	if (rtvName.equals("my_display")) {
		// Do something
	}
}

}

