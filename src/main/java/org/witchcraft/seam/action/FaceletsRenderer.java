//package org.witchcraft.seam.action;
//
//import java.io.IOException;
//
//import org.jboss.seam.ScopeType;
//import org.jboss.seam.annotations.AutoCreate;
//import org.jboss.seam.annotations.Install;
//import org.jboss.seam.annotations.Name;
//import org.jboss.seam.annotations.Scope;
//import org.jboss.seam.annotations.intercept.BypassInterceptors;
//import org.jboss.seam.faces.Renderer;
//
//@Scope(ScopeType.STATELESS)
//@BypassInterceptors
//@Name("org.jboss.seam.faces.renderer")
//@AutoCreate
//@Install(value = true, precedence = Install.APPLICATION)
//// dependency to the evil Sun JSF class is removed:
//// , classDependencies = "com.sun.faces.facelets.Facelet")
//public class FaceletsRenderer extends Renderer {
//	/**
//	 * Render the viewId, anything written to the JSF ResponseWriter is returned
//	 */
//	@Override
//	public String render(final String viewId) {
//		// call our fixed RendererRequest class instead of the original one.
//		RendererRequest rendererRequest = new RendererRequest(viewId);
//		try {
//			rendererRequest.run();
//		} catch (IOException e) {
//			throw new RuntimeException("error rendering " + viewId, e);
//		}
//		return rendererRequest.getOutput();
//	}
//}