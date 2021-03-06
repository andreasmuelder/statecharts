/**
 * Copyright (c) 2015 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.model.resource;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.yakindu.sct.domain.extension.DomainRegistry;
import org.yakindu.sct.domain.extension.DomainRegistry.DomainDescriptor;
import org.yakindu.sct.model.sgraph.SGraphPackage;
import org.yakindu.sct.model.sgraph.Statechart;

import com.google.inject.Injector;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
public class SCTResourceFactory extends XMIResourceFactoryImpl {

	@Override
	public Resource createResource(URI uri) {
		String domainID = determineDomainID(uri);
		DomainDescriptor domainDescriptor = DomainRegistry.getDomainDescriptor(domainID);
		Injector injector = domainDescriptor.getDomainInjectorProvider().getResourceInjector();
		Resource resource = injector.getInstance(Resource.class);
		ResourceSet set = new ResourceSetImpl();
		set.getResources().add(resource);
		resource.setURI(uri);
		return resource;
	}

	protected String determineDomainID(URI uri) {
		String result = SGraphPackage.Literals.STATECHART__DOMAIN_ID.getDefaultValueLiteral();
		if (URIConverter.INSTANCE.exists(uri, null)) {
			XMIResource resource = new XMIResourceImpl(uri);
			try {
				resource.load(null);
				Statechart statechart = (Statechart) EcoreUtil.getObjectByType(resource.getContents(),
						SGraphPackage.Literals.STATECHART);
				result = statechart.getDomainID();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				resource.unload();
			}
		}
		return result;
	}
}
