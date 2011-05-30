/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 */

package eu.esdihumboldt.hale.ui.io.legacy.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.esdihumboldt.hale.ui.internal.Messages;
import eu.esdihumboldt.hale.ui.io.legacy.InstanceDataImportWizard;
import eu.esdihumboldt.hale.ui.service.schema.SchemaService;

/**
 * This Handler delegates to the Instance data loading wizard.
 * 
 * @author Thorsten Reitz
 */
public class LoadInstanceDataHandler 
	extends AbstractHandler {

	/**
	 * @see IHandler#execute(ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SchemaService schemaService = (SchemaService) PlatformUI.getWorkbench().getService(SchemaService.class);
		String schemaNamespace = schemaService.getSourceNameSpace();
		boolean schemaPresent = schemaNamespace != null && !schemaNamespace.isEmpty();
		
		if (schemaPresent) {
			IImportWizard iw = new InstanceDataImportWizard(schemaNamespace);
			Shell shell = HandlerUtil.getActiveShell(event);
			WizardDialog dialog = new WizardDialog(shell, iw);
			dialog.open();
		}
		else {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
				Messages.LoadInstanceDataHandler_0,  //$NON-NLS-1$
				Messages.LoadInstanceDataHandler_1); //$NON-NLS-1$
		}
		
		return null;
	}



}