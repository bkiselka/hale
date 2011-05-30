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

package eu.esdihumboldt.hale.ui.io.legacy;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.ui.internal.HALEUIPlugin;
import eu.esdihumboldt.hale.ui.internal.Messages;
import eu.esdihumboldt.hale.ui.service.project.ProjectService;
import eu.esdihumboldt.hale.ui.util.ExceptionHelper;

/**
 * TODO Explain the purpose of this type here.
 * 
 * @author Thorsten Reitz
 */
public class SaveAlignmentProjectWizard 
	extends Wizard
	implements IExportWizard {
	
	private SaveAlignmentProjectWizardMainPage mainPage;
	
	private static ALogger _log = ALoggerFactory.getLogger(SaveAlignmentProjectWizard.class);
	
	/**
	 * Default constructor
	 */
	public SaveAlignmentProjectWizard() {
		super();
		this.mainPage = new SaveAlignmentProjectWizardMainPage(
				Messages.SaveAlignmentProjectWizard_SaveAlignmentTitle, Messages.SaveAlignmentProjectWizard_SaveAlignmentDescription); //NON-NLS-1
		super.setWindowTitle(Messages.SaveAlignmentProjectWizard_WindowTitle); //NON-NLS-1
		super.setNeedsProgressMonitor(true);
		
		ProjectService ps = (ProjectService) PlatformUI.getWorkbench().getService(ProjectService.class);
		String name = ps.getProjectName();
		if (name != null) {
			mainPage.setProjectName(name);
		}
	}
	
	/**
	 * @see IWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		String result = this.mainPage.getResult();
		if (result != null) {
			try {
				ProjectService ps = (ProjectService) PlatformUI.getWorkbench().getService(ProjectService.class);
				ps.saveAs(result, mainPage.getProjectName());
			} catch (Exception e) {
				String message = Messages.SaveAlignmentProjectWizard_SaveFaild;
				_log.error(message, e);
				ExceptionHelper.handleException(
						message, HALEUIPlugin.PLUGIN_ID, e);
			}
		}
		return true;
	}

	/**
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// do nothing
	}

	/**
	 * @see IWizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPage(this.mainPage);
	}

	/**
	 * @see IWizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		return this.mainPage.isPageComplete();
	}

}