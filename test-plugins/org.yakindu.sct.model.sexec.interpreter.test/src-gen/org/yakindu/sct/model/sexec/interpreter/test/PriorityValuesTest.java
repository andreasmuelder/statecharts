/**
* Copyright (c) 2012 committers of YAKINDU and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     committers of YAKINDU - initial API and implementation
*/
package org.yakindu.sct.model.sexec.interpreter.test;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yakindu.sct.model.sexec.interpreter.test.util.AbstractExecutionFlowTest;
import org.yakindu.sct.model.sexec.interpreter.test.util.SExecInjectionProvider;
import com.google.inject.Inject;
import org.junit.Before;
import org.yakindu.sct.model.sexec.interpreter.IExecutionFlowInterpreter;
import org.yakindu.sct.model.sexec.ExecutionFlow;
import util.TestModels;	
import static junit.framework.Assert.*;
/**
 *  Unit TestCase for PriorityValues
 */
@RunWith(XtextRunner.class)
@InjectWith(SExecInjectionProvider.class)
public class PriorityValuesTest extends AbstractExecutionFlowTest {
	
	@Inject
	private TestModels models;
	
	@Before
	public void setup() throws Exception{
		ExecutionFlow flow = models.loadExecutionFlowFromResource("PriorityValues.sct");
		initInterpreter(flow);
	}
	@Test
	public void transitionPriority() throws Exception {
		assertTrue(isActive("A"));
		raiseEvent("");
		interpreter.runCycle();
		assertTrue(isActive("C"));
	}
	@Test
	public void regionPriority() throws Exception {
		assertTrue(isActive("A"));
		raiseEvent("");
		interpreter.runCycle();
		assertTrue(isActive("B"));
		assertTrue(!isActive("E"));
	}
}